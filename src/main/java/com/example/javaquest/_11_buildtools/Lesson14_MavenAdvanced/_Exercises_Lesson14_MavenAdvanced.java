package com.example.javaquest._11_buildtools.Lesson14_MavenAdvanced;

public class _Exercises_Lesson14_MavenAdvanced {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExtractHardcodedVersionToProperty {
        /*
         * 🧪 Zadanie 1:
         * W realnym projekcie Maven z zależnością gson zapisaną z wersją "na twardo"
         * (<version>2.11.0</version>) wynieś tę wersję do <properties><gson.version>2.11.0
         * </gson.version></properties> i odwołaj się do niej przez ${gson.version}.
         * Uruchom "mvn compile" w terminalu i zapisz w komentarzu, że wynik jest ten sam.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateDevProfile {
        /*
         * 🧪 Zadanie 2:
         * Dodaj do pom.xml jeden profil "dev" z activeByDefault=true i jedną właściwością
         * (np. db.url). Uruchom "mvn help:active-profiles" (jeśli masz help-plugin) albo
         * po prostu "mvn compile -X" i zapisz w komentarzu, gdzie w logach widać, że
         * profil "dev" jest aktywny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ActivateProfileExplicitly {
        /*
         * 🧪 Zadanie 3:
         * Dodaj DRUGI profil "prod" (bez activeByDefault) z inną wartością tej samej
         * właściwości co w zadaniu 2. Uruchom "mvn compile -Pprod" w terminalu i sprawdź
         * (np. przez wypisanie właściwości w prostym pluginie albo logi -X), że to profil
         * "prod" jest teraz aktywny.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParentPomExplanationPrint {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: otwórz pom.xml w korzeniu tego kursu i wypisz na konsoli
         * (System.out.println) jego <parent> (groupId, artifactId, version) razem z
         * jednozdaniowym wyjaśnieniem, co ten parent daje projektowi (BOM z wersjami
         * zależności Spring Boot).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_DependencyManagementVsDependenciesPrint {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wypisz na konsoli różnicę między dependencyManagement a
         * dependencies jednym zdaniem każde, plus przykład ("cennik" vs "zamówienie").
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateTwoModuleParentStructure {
        /*
         * 🧪 Zadanie 6:
         * Utwórz ręcznie w terminalu strukturę katalogów: parent-demo/, parent-demo/core/,
         * parent-demo/app/. W parent-demo/pom.xml napisz minimalny parent POM z
         * packaging="pom" i <modules>core, app</modules>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ChildPomWithParentReference {
        /*
         * 🧪 Zadanie 7:
         * W parent-demo/core/pom.xml napisz pom.xml z sekcją <parent> wskazującą na projekt
         * z zadania 6 (relativePath="../pom.xml"). Uruchom "mvn validate" w katalogu core/
         * w terminalu i zapisz w komentarzu, czy walidacja przeszła.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RunReactorBuild {
        /*
         * 🧪 Zadanie 8:
         * Dodaj analogiczny pom.xml (jak w zadaniu 7) do parent-demo/app/. Uruchom
         * "mvn install" w KATALOGU GŁÓWNYM (parent-demo/) - zapisz w komentarzu, w jakiej
         * kolejności Maven zbudował moduły core i app (tzw. "reactor build order").
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ModuleDependencyDeclaration {
        /*
         * 🧪 Zadanie 9:
         * W parent-demo/app/pom.xml dodaj zależność na moduł core (groupId/artifactId
         * takie samo jak w core/pom.xml, wersja odziedziczona przez dependencyManagement
         * z parenta). Uruchom "mvn install" w terminalu i zapisz w komentarzu, czy build
         * się powiódł.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_PluginManagementBasicExample {
        /*
         * 🧪 Zadanie 10:
         * Dodaj do parent-demo/pom.xml sekcję <build><pluginManagement><plugins> z
         * deklaracją wersji maven-compiler-plugin (np. 3.13.0), bez konfiguracji. W
         * core/pom.xml wymień ten plugin BEZ podawania wersji. Uruchom "mvn compile" w
         * terminalu i zapisz w komentarzu, że użyta została wersja z pluginManagement.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_MultiEnvironmentProfiles {
        /*
         * 🧪 Zadanie 11:
         * Rozszerz projekt z zadania 2-3 o TRZECI profil "test" z własnymi właściwościami
         * (dev/test/prod - jak w brief-u lekcji). Uruchom po kolei "mvn compile -Pdev",
         * "-Ptest", "-Pprod" w terminalu i zapisz w komentarzu różnice w aktywnych
         * właściwościach dla każdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_PropertyActivatedProfile {
        /*
         * 🧪 Zadanie 12:
         * Zmień aktywację jednego z profili z <activeByDefault> na
         * <activation><property><name>env</name><value>ci</value></property></activation>.
         * Uruchom "mvn compile -Denv=ci" w terminalu i zapisz w komentarzu, że profil się
         * aktywował dzięki właściwości systemowej, nie fladze -P.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CentralizedVersionAcrossModules {
        /*
         * 🧪 Zadanie 13:
         * W projekcie wielomodułowym z zadań 6-9 dodaj DRUGĄ zależność zewnętrzną (np.
         * gson) do dependencyManagement w parencie i użyj jej w OBU modułach (core i app)
         * bez podawania wersji. Uruchom "mvn install" i zapisz w komentarzu, że obie
         * używają tej samej, jednej wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_VersionMismatchWithoutManagement {
        /*
         * 🧪 Zadanie 14:
         * Usuń tymczasowo dependencyManagement z parenta i w KAŻDYM module (core, app)
         * zadeklaruj gson z RÓŻNĄ wersją (np. 2.10.0 i 2.11.0). Uruchom "mvn dependency:tree"
         * w app/ w terminalu i zapisz w komentarzu, jaką wersję finalnie wybrał Maven
         * (dependency mediation) - a potem przywróć dependencyManagement.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ThirdModulePersistence {
        /*
         * 🧪 Zadanie 15:
         * Dodaj TRZECI moduł "persistence" do projektu z zadań 6-9 (analogicznie do core).
         * Zaktualizuj <modules> w parencie. Zrób "app" zależnym od "core" I "persistence".
         * Uruchom "mvn install" w terminalu i zapisz w komentarzu kolejność budowania
         * 3 modułów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ProfileWithDifferentDependency {
        /*
         * 🧪 Zadanie 16:
         * Dodaj profil "h2" z zależnością na com.h2database:h2 i profil "postgres" z
         * zależnością na org.postgresql:postgresql - żeby projekt mógł działać z różną
         * bazą w zależności od aktywnego profilu. Uruchom "mvn dependency:tree -Ph2" i
         * "-Dpostgres" w terminalu, porównaj wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_PluginManagementWithConfiguration {
        /*
         * 🧪 Zadanie 17:
         * Rozszerz pluginManagement z zadania 10 o pełną <configuration> (release=21) dla
         * maven-compiler-plugin. W module app/pom.xml wymień ten plugin BEZ żadnej
         * konfiguracji - zapisz w komentarzu (po sprawdzeniu "mvn compile -X" w terminalu),
         * że konfiguracja z pluginManagement została odziedziczona.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_RelativePathExplanationPrint {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala: wypisz wyjaśnienie różnicy między <relativePath/> (puste - "szukaj
         * parenta w repozytorium Maven") a <relativePath>../pom.xml</relativePath>
         * (konkretna ścieżka - "parent to plik na dysku, część tego samego
         * multi-module projektu").
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_BuildSingleModuleWithAmL {
        /*
         * 🧪 Zadanie 19:
         * W projekcie z zadań 6-15 uruchom w terminalu "mvn install -pl app -am" (buduje
         * TYLKO app i jego zależności - "also make"). Zapisz w komentarzu, czym różni się
         * to od pełnego "mvn install" bez flag (które budowałoby WSZYSTKIE moduły).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_PropertiesInheritedVsOverridden {
        /*
         * 🧪 Zadanie 20:
         * W parencie ustaw właściwość <encoding>UTF-8</encoding>. W module core NADPISZ
         * tę samą właściwość inną wartością. Uruchom "mvn help:evaluate
         * -Dexpression=encoding" w KAŻDYM module (parent i core) w terminalu i zapisz w
         * komentarzu, że core widzi swoją własną (nadpisaną) wartość.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BomImportFromExternalProject {
        /*
         * 🧪 Zadanie 21:
         * Bez posiadania własnego parenta (usuń <parent> na chwilę), dodaj do
         * dependencyManagement <dependency> z <type>pom</type> i <scope>import</scope>
         * wskazującą na spring-boot-dependencies (odpowiednia wersja). Uruchom "mvn
         * dependency:tree" w terminalu i zapisz w komentarzu, że wersje bibliotek Spring
         * są teraz zarządzane przez zaimportowany BOM, mimo braku dziedziczenia po
         * parencie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_FourModuleLayeredArchitecture {
        /*
         * 🧪 Zadanie 22:
         * Zbuduj realny 4-modułowy projekt: parent (pom) + core + persistence (zależny od
         * core) + web (zależny od core i persistence). Uruchom "mvn install" w terminalu
         * i zapisz w komentarzu PEŁNĄ kolejność budowania (reactor order) - powinna
         * respektować zależności między modułami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ProfileActivatedByOs {
        /*
         * 🧪 Zadanie 23:
         * Dodaj profil aktywowany automatycznie na podstawie systemu operacyjnego
         * (<activation><os><family>windows</family></os></activation>). Uruchom "mvn
         * compile -X" w terminalu na swojej maszynie i zapisz w komentarzu, czy profil
         * aktywował się automatycznie bez żadnej flagi -P.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_CircularDependencyDetection {
        /*
         * 🧪 Zadanie 24:
         * Celowo wprowadź CYKLICZNĄ zależność między dwoma modułami (core zależy od app,
         * a app zależy od core). Uruchom "mvn install" w terminalu i zapisz w komentarzu
         * dokładny komunikat błędu Maven-a o cyklu w grafie modułów - a następnie napraw
         * (usuń jedną z zależności).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ModuleSpecificProfile {
        /*
         * 🧪 Zadanie 25:
         * Dodaj profil zdefiniowany TYLKO w module app/pom.xml (nie w parencie), np. profil
         * dodający dodatkową zależność testową. Uruchom "mvn dependency:tree -Pprofil-app"
         * w KATALOGU core (gdzie profil NIE istnieje) i w app (gdzie istnieje) - zapisz w
         * komentarzu różnicę w zachowaniu/ostrzeżeniach Maven-a.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_VersionRangeInDependencyManagement {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: napisz w komentarzu wyjaśnienie zakresów wersji Maven (version
         * ranges), np. "[2.10.0,2.12.0)" - jakie wersje spełniają taki zakres i czy
         * dependencyManagement może z nich korzystać (i dlaczego w praktyce rzadko się
         * je stosuje - nieprzewidywalność budowań).
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ParentVersionBumpPropagation {
        /*
         * 🧪 Zadanie 27:
         * Zmień <version> w parent/pom.xml z 1.0.0 na 1.1.0. Zaktualizuj odpowiednio
         * <parent><version> w core/pom.xml i app/pom.xml. Uruchom "mvn install" w
         * terminalu i zapisz w komentarzu, że NOWA wersja 1.1.0 pojawiła się w
         * ~/.m2/repository dla wszystkich modułów.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_MavenHelpEffectivePom {
        /*
         * 🧪 Zadanie 28:
         * W module core/ uruchom w terminalu "mvn help:effective-pom" (wymaga
         * maven-help-plugin, dostępny bez dodatkowej konfiguracji przez pełną
         * współrzędną, jeśli nie masz go w pom.xml). Zapisz w komentarzu, że wynikowy
         * "efektywny" POM zawiera WSZYSTKIE właściwości/zależności odziedziczone z
         * parenta, mimo że w core/pom.xml ich nie widać jawnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_DependencyManagementConflictResolution {
        /*
         * 🧪 Zadanie 29:
         * W parencie zadeklaruj w dependencyManagement bibliotekę X w wersji 1.0. W
         * module core zadeklaruj TĘ SAMĄ bibliotekę w <dependencies> z JAWNĄ, inną wersją
         * (np. 2.0) - jawna deklaracja w module PRZEBIJA dependencyManagement z parenta.
         * Uruchom "mvn dependency:tree" w core/ w terminalu i zweryfikuj, która wersja
         * ostatecznie wygrała.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullMultiModuleCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujące zadanie: zbuduj kompletny 3-modułowy projekt (core/persistence/web -
         * jak w opisie sylabusu tej lekcji) z: properties centralizującymi wersję Javy,
         * co najmniej jednym profilem dev/prod, dependencyManagement centralizującym
         * wersję min. 2 bibliotek zewnętrznych, i pluginManagement dla
         * maven-compiler-plugin. Uruchom "mvn clean install" jednym poleceniem w
         * terminalu i zapisz w komentarzu pełny raport: kolejność budowania modułów,
         * czy build przeszedł, i finalną lokalizację artefaktów w ~/.m2/repository.
         */
        public static void main(String[] args) { }
    }
}
