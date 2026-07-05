package com.example.javaquest._11_buildtools.Lesson08_AntAdvanced;

public class _Exercises_Lesson08_AntAdvanced {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MacrodefBanner {
        /*
         * 🧪 Zadanie 1:
         * Zdefiniuj macrodef "printBanner" z atrybutem "title", ktory wypisuje (echo)
         * ozdobny naglowek zawierajacy @{title} otoczony liniami "====". Wywolaj go 3 razy
         * z roznymi tytulami ("Start", "Kompilacja", "Koniec").
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_MacrodefCopyWithLabel {
        /*
         * 🧪 Zadanie 2:
         * Zdefiniuj macrodef "copyWithLabel" z atrybutami "from" i "to", ktory kopiuje
         * plik (<copy file="@{from}" tofile="@{to}"/>) i po skopiowaniu wypisuje echo
         * "Skopiowano: @{from} -> @{to}". Uzyj go do skopiowania DWOCH roznych plikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ConditionJavaVersion {
        /*
         * 🧪 Zadanie 3:
         * Uzyj <condition property="isJava21"><equals arg1="${ant.java.version}" arg2="21"/>
         * </condition>, a nastepnie <echo> wypisujace wartosc property isJava21 dla JVM,
         * na ktorej faktycznie dziala ten program.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ConditionAvailableFile {
        /*
         * 🧪 Zadanie 4:
         * Uzyj <condition property="markerExists"><available file="marker.txt"/></condition>.
         * Wykonaj target DWA razy: raz gdy plik marker.txt na dysku NIE istnieje (spodziewaj
         * sie brakujacej property), raz po jego utworzeniu (spodziewaj sie markerExists=true).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AntcallThreeLevels {
        /*
         * 🧪 Zadanie 5:
         * Zdefiniuj target "logMessage" wypisujacy echo "[${level}] ${text}". Wywolaj go
         * trzykrotnie przez <antcall> z parametrami level=INFO/WARN/ERROR i innym tekstem
         * "text" kazdorazowo.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_FilesetCopyOnlyCsv {
        /*
         * 🧪 Zadanie 6:
         * Wygeneruj 6 plikow w jednym katalogu: 2 pliki .csv, 2 pliki .json, 2 pliki .txt.
         * Skopiuj TYLKO pliki .csv do katalogu docelowego uzywajac <fileset><include
         * name="*.csv"/></fileset>. Wypisz zawartosc katalogu docelowego i potwierdz, ze
         * sa tam wylacznie pliki .csv.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_FiltersetGreetingTemplate {
        /*
         * 🧪 Zadanie 7:
         * Stworz szablon "greeting.txt" z tokenami @NAME@ i @DAY@. Za pomoca DWOCH
         * niezaleznych operacji <copy><filterset>...</filterset></copy> wygeneruj dwa
         * pliki wynikowe dla dwoch osob/dni (np. Ala/poniedzialek, Bartek/piatek). Wypisz
         * zawartosc obu wygenerowanych plikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ImportSharedStampTarget {
        /*
         * 🧪 Zadanie 8:
         * W build-shared.xml zdefiniuj target "stamp" wypisujacy echo z aktualnym
         * znacznikiem czasu (property ustawiona z Javy przed executeTarget). Zaimportuj
         * go w glownym build.xml i wywolaj z DWOCH innych targetow przez depends="stamp".
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_MacrodefOptionalAttribute {
        /*
         * 🧪 Zadanie 9:
         * Zdefiniuj macrodef "sayHello" z atrybutem "lang" majacym default="pl". Wewnatrz
         * <sequential> uzyj <echo> zaleznego od @{lang} (mozesz uzyc prostego tekstu
         * zawierajacego @{lang}). Wywolaj makro raz PODAJAC atrybut lang="en" i raz BEZ
         * niego (korzystajac z wartosci domyslnej), porownujac wypisany tekst.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ConditionPlusAntcallBranch {
        /*
         * 🧪 Zadanie 10:
         * Ustaw na sztywno property "feature.enabled" na "true". Uzyj <condition
         * property="feature.on"><equals arg1="${feature.enabled}" arg2="true"/></condition>,
         * a potem <antcall> do jednego z dwoch targetow ("feature-on-message" albo
         * "feature-off-message") w zaleznosci od wartosci feature.on. Zmien wartosc
         * feature.enabled na "false" w drugim, niezaleznym przebiegu i porownaj wynik.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_TwoMacrodefsFromImport {
        /*
         * 🧪 Zadanie 11:
         * W build-common.xml zdefiniuj DWA macrodefy: "compileModule" (attribute "srcdir",
         * "destdir") i "packageModule" (attribute "classesdir", "jarfile"). Zaimportuj je
         * do glownego build.xml i uzyj obu dla DWOCH fikcyjnych "modulow" (dwa rozne
         * katalogi zrodlowe), produkujac dwa niezalezne JAR-y.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_FilesetNestedIncludeExclude {
        /*
         * 🧪 Zadanie 12:
         * Wygeneruj co najmniej 8 plikow rozlozonych w 2 podkatalogach (np. reports/2024/
         * i reports/2025/), z mieszanka nazw zawierajacych i niezawierajacych "draft".
         * Skopiuj tylko pliki .txt bez "draft" w nazwie z OBU podkatalogow na raz (jeden
         * fileset z <include name="**&#47;*.txt"/> i <exclude name="**&#47;*draft*"/>).
         * Wypisz i zweryfikuj kompletna liste skopiowanych plikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_FiltersetMultipleTokens {
        /*
         * 🧪 Zadanie 13:
         * Stworz szablon z TRZEMA tokenami: @ENV@, @DB_HOST@, @DB_PORT@. Wygeneruj DWA
         * pliki konfiguracyjne (dev: localhost/5432, prod: prod-db.firma.local/5432) przy
         * pomocy dwoch niezaleznych <filterset>. Wypisz zawartosc obu wygenerowanych plikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AntcallParamDrivenBehavior {
        /*
         * 🧪 Zadanie 14:
         * Zdefiniuj target "process", ktory uzywa <condition> na podstawie property
         * przekazanej przez antcall (np. "mode") i w zaleznosci od jej wartosci ("fast"
         * albo "safe") wypisuje inny komunikat. Wywolaj go DWA razy przez antcall z innymi
         * wartosciami "mode" i porownaj oba wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_MacrodefWithElement {
        /*
         * 🧪 Zadanie 15:
         * Zdefiniuj macrodef "withLogging" majacy jeden <element name="body"/> (nie
         * attribute!) pozwalajacy wywolujacemu wstrzyknac WLASNE taski do wykonania
         * pomiedzy <echo message="START"/> i <echo message="KONIEC"/>. Uzyj tego makra,
         * przekazujac jako "body" prosty <echo message="praca w srodku"/>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ConditionOsFamilies {
        /*
         * 🧪 Zadanie 16:
         * Uzyj DWOCH niezaleznych <condition>: jednego sprawdzajacego <os family="windows"/>
         * do property "is.win", drugiego <os family="unix"/> do property "is.unix". Wypisz
         * obie property i skomentuj, ktora rodzina faktycznie odpowiada maszynie, na ktorej
         * dziala ten program.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_TwoImportedCommonFiles {
        /*
         * 🧪 Zadanie 17:
         * Stworz DWA osobne pliki: build-common-a.xml (macrodef "stepA") i
         * build-common-b.xml (macrodef "stepB"). Zaimportuj OBA do glownego build.xml i
         * wywolaj je razem w jednym targecie, jedno po drugim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_GuardedAntcall {
        /*
         * 🧪 Zadanie 18:
         * Zdefiniuj target "process-if-ready", ktory uzywa <available file="ready.flag"
         * property="isReady"/>, a nastepnie warunkowo (if="isReady") wywoluje przez
         * <antcall> target "process". Uruchom scenariusz DWA razy: raz bez pliku
         * ready.flag (process pominiety), raz po jego utworzeniu (process wykonany).
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_FlattenNestedDirectoryCopy {
        /*
         * 🧪 Zadanie 19:
         * Wygeneruj pliki w strukturze 2 poziomow zagniezdzenia (np. data/a/x.txt,
         * data/b/y.txt). Skopiuj je do JEDNEGO plaskiego katalogu docelowego uzywajac
         * <copy flatten="true">. Wypisz zawartosc katalogu docelowego, dowodzac, ze
         * wszystkie pliki wyladowaly bez podkatalogow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ListAllTargetNames {
        /*
         * 🧪 Zadanie 20:
         * Skonfiguruj Project na bazie build.xml z co najmniej 5 targetami (mozesz
         * odtworzyc build.xml z lekcji). Po ProjectHelper.configureProject wywolaj
         * project.getTargets().keySet(), posortuj i wypisz wszystkie nazwy targetow -
         * bez uruchamiania zadnego z nich.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_PluginLikeStepSystem {
        /*
         * 🧪 Zadanie 21:
         * W build-common.xml zdefiniuj macrodef "runStep" z atrybutem "stepName" i
         * elementem "body" (patrz Zadanie 15). W glownym build.xml zdefiniuj 4 "kroki"
         * (clean/compile/package/report-stub), kazdy wywolany przez runStep z innym
         * stepName i innym body, po kolei w jednym targecie. Wypisz log przebiegu kazdego
         * kroku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AntcallPropertyScopeCollision {
        /*
         * 🧪 Zadanie 22:
         * Ustaw property "name" GLOBALNIE (np. project.setProperty("name","Globalna") w
         * Javie przed executeTarget). Zdefiniuj target "print-name" wypisujacy ${name}.
         * Wywolaj go DWA razy przez <antcall><param name="name" value="Lokalna1"/></antcall>
         * i z innym param "Lokalna2". Wypisz, jaka WARTOSC name faktycznie zostala
         * wypisana w kazdym wywolaniu i wyjasnij komentarzem, dlaczego (Ant property sa
         * niemutowalne po pierwszym ustawieniu - antcall param NIE nadpisuje juz
         * istniejacej globalnej property).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_EnvironmentPromotionPipeline {
        /*
         * 🧪 Zadanie 23:
         * Uzywajac JEDNEGO szablonu (filterset) i TRZECH wywolan <antcall target="deploy">
         * z parametrem env=dev/test/prod, wygeneruj trzy osobne pliki konfiguracyjne.
         * Nastepnie jednym <copy><fileset .../></copy> zbierz wszystkie trzy do katalogu
         * "release/". Wypisz zawartosc katalogu release/.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_MacrodefInternalCondition {
        /*
         * 🧪 Zadanie 24:
         * Zdefiniuj macrodef "validate" z atrybutem "mode". Wewnatrz uzyj <condition> i
         * <echo> tak, aby przy mode="strict" wypisac inny komunikat niz przy jakiejkolwiek
         * innej wartosci. Wywolaj makro dwa razy z mode="strict" i mode="relaxed".
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_CircularDependsFailure {
        /*
         * 🧪 Zadanie 25:
         * Zdefiniuj target "a" depends="b" i target "b" depends="a" (cykl!). Sprobuj
         * executeTarget("a") w bloku try/catch(BuildException) i wypisz PRAWDZIWY
         * komunikat Anta o wykrytym cyklu zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MacrodefMissingRequiredAttribute {
        /*
         * 🧪 Zadanie 26:
         * Zdefiniuj macrodef z WYMAGANYM atrybutem (bez default). Wywolaj go BEZ podania
         * tego atrybutu i zlap prawdziwy BuildException, wypisujac jego komunikat.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ReleasePackagingCombo {
        /*
         * 🧪 Zadanie 27:
         * Polacz import + macrodef + fileset + filterset w JEDNYM realistycznym scenariuszu:
         * makro "packageRelease" (z build-common.xml) filtruje i kopiuje zestaw plikow
         * konfiguracyjnych, a nastepnie pakuje je do ZIP-a. Wywolaj je dla DWOCH "srodowisk"
         * i zweryfikuj oba powstale archiwa przez ZipFile.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_JavaInjectedPropertiesSteerBuild {
        /*
         * 🧪 Zadanie 28:
         * Uzywajac TEGO SAMEGO, niezmienionego build.xml (z <condition> zaleznym od
         * property "env"), uruchom go na DWOCH niezaleznych obiektach Project, ustawiajac
         * project.setProperty("env", ...) na inna wartosc PRZED configureProject/
         * executeTarget w kazdym z nich. Wypisz oba wyniki, dowodzac, ze mozna "kierowac"
         * buildem z Javy bez modyfikowania XML-a.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CustomBuildListenerEventCounter {
        /*
         * 🧪 Zadanie 29:
         * Napisz minimalna klase implementujaca org.apache.tools.ant.BuildListener, ktora
         * tylko liczy wywolania targetStarted i taskStarted (ignorujac reszte metod
         * interfejsu). Dodaj ja do Project OBOK DefaultLoggera i uruchom nia
         * wielotargetowy build.xml z lekcji. Po wykonaniu wypisz oba zliczone liczniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_WelcomePackageCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zaprojektuj build.xml uzywajacy WSZYSTKICH mechanizmow z lekcji: importowany
         * plik z co najmniej jednym macrodef, jeden <condition>, jeden <antcall>, jedno
         * filtrowane <copy> (fileset) i jedno podmieniajace <copy> (filterset). Zbuduj
         * "pakiet powitalny" (spersonalizowany plik tekstowy + 2 przefiltrowane zasoby)
         * dla DWOCH fikcyjnych uzytkownikow/srodowisk w jednym przebiegu i zweryfikuj
         * istnienie obu wynikowych pakietow na dysku.
         */
        public static void main(String[] args) { }
    }
}
