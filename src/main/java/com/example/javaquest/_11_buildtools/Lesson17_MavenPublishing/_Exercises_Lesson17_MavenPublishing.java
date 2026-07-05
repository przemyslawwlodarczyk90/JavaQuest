package com.example.javaquest._11_buildtools.Lesson17_MavenPublishing;

public class _Exercises_Lesson17_MavenPublishing {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_AddDistributionManagement {
        /*
         * 🧪 Zadanie 1:
         * Utwórz nowy projekt Maven i dodaj sekcję <distributionManagement> z jednym
         * <repository> (id "local-releases", dowolny URL, np. "file:///C:/tmp/repo").
         * Uruchom "mvn deploy" w terminalu i zapisz w komentarzu, czy artefakt pojawił
         * się pod wskazaną ścieżką.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_FileBasedRepositoryDeploy {
        /*
         * 🧪 Zadanie 2:
         * Rozszerz projekt z zadania 1 o <snapshotRepository> (inny lokalny katalog
         * "file://"). Zmień wersję na "1.0.0-SNAPSHOT", uruchom "mvn deploy" i zapisz w
         * komentarzu, do którego z dwóch katalogów trafił artefakt.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ServersIdMatchExplanation {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wypisz na konsoli (System.out.println) wyjaśnienie, dlaczego <id>
         * w sekcji <server> pliku ~/.m2/settings.xml musi być IDENTYCZNE jak <id>
         * repository/snapshotRepository w pom.xml - podaj konkretny przykład dwóch
         * pasujących id.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_LocateOwnSettingsXml {
        /*
         * 🧪 Zadanie 4:
         * Sprawdź w terminalu (np. "mvn help:effective-settings"), czy na Twoim komputerze
         * istnieje plik ~/.m2/settings.xml. Zapisz w komentarzu jego ścieżkę i to, czy
         * zawiera już jakąś sekcję <servers>.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_NeverCommitCredentialsExplanation {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wypisz 3 powody, dla których hasła/tokeny do repozytoriów NIGDY
         * nie powinny trafiać do pom.xml ani do repozytorium Git (historia commitów,
         * publiczne fork'i, dostęp wielu współpracowników).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_SnapshotOverwriteDemo {
        /*
         * 🧪 Zadanie 6:
         * W projekcie z zadania 2 (wersja SNAPSHOT) zmień coś drobnego w kodzie i uruchom
         * "mvn deploy" DRUGI raz. Zapisz w komentarzu, czy Maven pozwolił nadpisać
         * poprzedni artefakt w repozytorium (bez błędu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ReleaseVersionRejectSecondDeploy {
        /*
         * 🧪 Zadanie 7:
         * Zmień wersję projektu z zadania 1 na "1.0.0" (release, bez SNAPSHOT) i uruchom
         * "mvn deploy" dwa razy pod rząd do TEGO SAMEGO repozytorium plikowego. Zapisz w
         * komentarzu, czy drugi deploy się powiódł, czy wystąpił błąd (repozytoria
         * plikowe lokalne mogą zachowywać się inaczej niż Nexus/Artifactory - zanotuj
         * zaobserwowane zachowanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_InstallVsDeployExplanation {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wypisz wyjaśnienie różnicy między fazami "install" i "deploy" -
         * że install kopiuje TYLKO do lokalnego ~/.m2/repository, a deploy wysyła przez
         * sieć do repozytorium zdalnego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_AddSourcesPlugin {
        /*
         * 🧪 Zadanie 9:
         * Dodaj do projektu z zadania 1 maven-source-plugin z egzekucją generującą
         * *-sources.jar. Uruchom "mvn package" i zapisz w komentarzu, czy w target/
         * pojawił się dodatkowy plik *-sources.jar obok głównego .jar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_AddJavadocPlugin {
        /*
         * 🧪 Zadanie 10:
         * Dodaj do tego samego projektu maven-javadoc-plugin z egzekucją generującą
         * *-javadoc.jar. Uruchom "mvn package" w terminalu i zapisz w komentarzu, czy
         * Javadoc wygenerował się bez błędów (np. brakujących komentarzy przy strict
         * ustawieniach).
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_FullMetadataForCentral {
        /*
         * 🧪 Zadanie 11:
         * Uzupełnij pom.xml projektu o pełne metadane wymagane przez Maven Central: <name>,
         * <description>, <url>, <licenses>, <developers>, <scm>. Uruchom "mvn validate" i
         * zapisz w komentarzu, czy Maven zgłosił jakiekolwiek ostrzeżenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GpgKeyGeneration {
        /*
         * 🧪 Zadanie 12 (wymaga zainstalowanego GPG):
         * Wygeneruj lokalnie parę kluczy GPG (np. "gpg --gen-key") jeśli jej nie masz.
         * Zapisz w komentarzu identyfikator klucza (key ID) - albo informację, że
         * pominąłeś zadanie z braku zainstalowanego GPG.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ConfigureGpgPlugin {
        /*
         * 🧪 Zadanie 13:
         * Dodaj do projektu maven-gpg-plugin z egzekucją "sign" podpiętą pod fazę verify
         * (jak w lekcji). Uruchom "mvn verify" i zapisz w komentarzu, czy w target/
         * pojawiły się pliki .asc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_SnapshotVsReleaseUrlSwitch {
        /*
         * 🧪 Zadanie 14:
         * Zbuduj mały skrypt (jako komentarz z opisem kroków, bez realnego uruchamiania)
         * wyjaśniający, jak Maven automatycznie wybiera <repository> albo
         * <snapshotRepository> na podstawie sufiksu "-SNAPSHOT" w wersji - podaj dwa
         * konkretne przykłady wersji i który URL zostanie użyty dla każdej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CentralPublishingPluginSetup {
        /*
         * 🧪 Zadanie 15:
         * Dodaj do pom.xml central-publishing-maven-plugin (jak w lekcji), z
         * <autoPublish>false</autoPublish>. Bez terminala: wypisz w komentarzu, czym
         * różniłoby się zachowanie "mvn deploy" przy autoPublish=false względem true
         * (ręczna weryfikacja przed publikacją).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_MultiModulePublishing {
        /*
         * 🧪 Zadanie 16:
         * W projekcie wielomodułowym z Lesson14 (core/persistence/app) dodaj
         * distributionManagement w parent pom (dziedziczone przez wszystkie moduły).
         * Uruchom "mvn deploy" w katalogu głównym i zapisz w komentarzu, ile artefaktów
         * (ile modułów) zostało opublikowanych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_SkipModuleFromDeploy {
        /*
         * 🧪 Zadanie 17:
         * W projekcie z zadania 16 oznacz JEDEN moduł (np. wewnętrzny moduł testowy) jako
         * <maven.deploy.skip>true</maven.deploy.skip> we właściwościach tego modułu.
         * Uruchom "mvn deploy" i zapisz w komentarzu, że ten moduł został pominięty, a
         * pozostałe opublikowane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ProfileForReleaseSigning {
        /*
         * 🧪 Zadanie 18:
         * Skonfiguruj profil Maven "release" (wzorzec z Lesson14), który AKTYWUJE
         * maven-gpg-plugin TYLKO gdy jest jawnie włączony (żeby zwykłe "mvn deploy" w
         * trakcie codziennej pracy nie wymagało podpisywania). Uruchom "mvn deploy" oraz
         * "mvn deploy -Prelease" i porównaj w komentarzu różnicę w wykonanych goalach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_VersionsPluginBumpVersion {
        /*
         * 🧪 Zadanie 19:
         * Dodaj do projektu versions-maven-plugin i uruchom
         * "mvn versions:set -DnewVersion=1.1.0" w terminalu. Zapisz w komentarzu, jak
         * zmienił się <version> w pom.xml i czy plugin utworzył plik pom.xml.versionsBackup.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DeployDryRunWithFileRepo {
        /*
         * 🧪 Zadanie 20:
         * Wykorzystaj lokalne repozytorium plikowe (file://) jako "próbę generalną" przed
         * prawdziwą publikacją na zdalny serwer - uruchom "mvn deploy" z takim URL i
         * zapisz w komentarzu strukturę katalogów, jaką Maven utworzył (odzwierciedlającą
         * groupId/artifactId/version).
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCentralPublishingPipeline {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny pom.xml gotowy (teoretycznie) do publikacji na Maven Central:
         * pełne metadane (zadanie 11), sources+javadoc (zadania 9-10), gpg signing
         * (zadanie 13), central-publishing-maven-plugin (zadanie 15). Uruchom
         * "mvn verify" (bez realnego deploya na Central) i zapisz w komentarzu, czy
         * wszystkie kroki się powiodły lokalnie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CiCdSecretsExplanation {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: wypisz wyjaśnienie, jak w pipeline CI/CD (np. GitHub Actions)
         * przekazuje się hasła/tokeny do "mvn deploy" BEZ pliku settings.xml w
         * repozytorium - przez zmienne środowiskowe (${env.NEXUS_PASSWORD}) i "sekrety"
         * (GitHub Secrets) wstrzykiwane w runtime.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ArtifactoryVsNexusComparisonPrint {
        /*
         * 🧪 Zadanie 23:
         * Bez terminala: wypisz tabelę porównawczą (min. 3 kryteria) JFrog Artifactory vs
         * Sonatype Nexus Repository - jako dwa popularne, samodzielnie hostowane
         * repozytoria firmowe (w odróżnieniu od publicznego Maven Central).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ReleasePluginWorkflow {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: wypisz opis (min. 4 kroki) działania maven-release-plugin
         * ("mvn release:prepare" + "mvn release:perform") - jak automatyzuje usunięcie
         * sufiksu SNAPSHOT, tag Git, deploy i podbicie wersji do kolejnego SNAPSHOT.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_MirrorConfigurationExplanation {
        /*
         * 🧪 Zadanie 25:
         * Dodaj do settings.xml (przykładowego, nie realnego) sekcję <mirrors> wskazującą
         * na wewnętrzne firmowe repozytorium proxy (np. Nexus jako mirror dla "*").
         * Bez terminala wyjaśnij w komentarzu, po co firmy stawiają taki mirror zamiast
         * łączyć się bezpośrednio z Maven Central.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ChecksumVerificationExplanation {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: wypisz wyjaśnienie, dlaczego Maven Central wymaga PODPISU GPG
         * (a nie tylko sumy kontrolnej SHA-1/MD5, którą Maven generuje automatycznie) -
         * że podpis potwierdza TOŻSAMOŚĆ autora, a suma kontrolna tylko integralność
         * pliku podczas transferu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_RollbackBadReleaseStrategy {
        /*
         * 🧪 Zadanie 27:
         * Bez terminala: wypisz wyjaśnienie, dlaczego NIE MOŻNA "cofnąć" błędnie
         * opublikowanej wersji RELEASE na Maven Central (niezmienność artefaktów) - i
         * jaka jest typowa strategia naprawy (wydanie NOWEJ, poprawionej wersji, np.
         * 1.0.1, zamiast nadpisywania 1.0.0).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_PrivateRegistryForInternalLibs {
        /*
         * 🧪 Zadanie 28:
         * Zaprojektuj (jako komentarz z opisem, bez realnego wdrożenia) strukturę
         * distributionManagement dla firmy z TRZEMA rodzajami repozytoriów: releases,
         * snapshots, oraz "third-party" (ręcznie wgrywane biblioteki spoza Maven Central).
         * Wyjaśnij, po co rozdzielać te trzy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AntIvyPublishComparisonPrint {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: wypisz porównanie (min. 3 punkty) publikowania artefaktów przez
         * Maven (deploy + distributionManagement, ta lekcja) vs Ant+Ivy (rozdział
         * _11_buildtools/Lesson09_AntIvy tego kursu, target "publish" w ivy.xml).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullPublishingCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujące zadanie: zbuduj projekt wielomodułowy (core/persistence/app, jak w
         * Lesson14), z pełnym distributionManagement w parent pom, profilem "release"
         * (gpg signing aktywowany warunkowo), sources+javadoc jar dla każdego modułu, i
         * central-publishing-maven-plugin skonfigurowanym w module "app". Uruchom
         * "mvn clean deploy -Prelease" i napisz w komentarzu krótki raport tekstowy
         * podsumowujący, co dokładnie zostałoby wysłane i dokąd.
         */
        public static void main(String[] args) { }
    }
}
