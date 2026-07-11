package com.example.javaquest._19_security_basics.Lesson20_DependencyAndSupplyChainSecurity;

public class _Exercises_Lesson20_DependencyAndSupplyChainSecurity {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatSoftwareSupplyChainMeans {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, czym jest "lancuch dostaw
         * oprogramowania" i dlaczego to osobna kategoria bezpieczenstwa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunMavenDependencyTreeAndAnalyzeOutput {
        /*
         * 🧪 Zadanie 2:
         * W terminale uruchom `mvnw.cmd dependency:tree` w katalogu tego
         * projektu - zidentyfikuj co najmniej 3 zaleznosci TRANZYTYWNE
         * (nie dodane bezposrednio w pom.xml).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ExplainDifferenceBetweenDirectAndTransitiveDependency {
        /*
         * 🧪 Zadanie 3:
         * Bez terminala: wyjasnij roznice miedzy zaleznoscia BEZPOSREDNIA
         * a TRANZYTYWNA, na przykladzie z tego projektu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ParsePomXmlAndListDependencies {
        /*
         * 🧪 Zadanie 4:
         * Wzorem teorii, sparsuj `pom.xml` tego projektu i wypisz
         * WSZYSTKIE bezposrednie zaleznosci (groupId:artifactId).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_ExplainWhatSbomMeans {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala: wyjasnij, czym jest SBOM (Software Bill of
         * Materials) i do czego sluzy w praktyce (np. reakcja na nowe CVE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyTyposquattedArtifactName {
        /*
         * 🧪 Zadanie 6:
         * Majac liste 5 nazw artefaktow Maven (mieszanka prawdziwych i
         * "podejrzanie podobnych"), wskaz, ktore wygladaja na
         * typosquatting.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainVersionPinningBenefit {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij, dlaczego pinowanie DOKLADNEJ wersji
         * zaleznosci jest bezpieczniejsze niz zakres wersji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ListKnownDependencyScanningTools {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wymien co najmniej 3 narzedzia/uslugi skanujace
         * zaleznosci pod katem znanych podatnosci (CVE).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExtractGroupIdAndArtifactIdFromCoordinateString {
        /*
         * 🧪 Zadanie 9:
         * Napisz metode rozbijajaca String `"groupId:artifactId:version"`
         * na 3 skladowe - przetestuj na 3 przykladach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainWhyPrivateRepositoryMattersForInternalPackages {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij, dlaczego wewnetrzne pakiety firmowe
         * powinny byc publikowane WYLACZNIE do prywatnego repozytorium.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_GenerateSimpleTextSbomFromParsedDependencies {
        /*
         * 🧪 Zadanie 11:
         * Rozbuduj parser z Zadania 4 o generowanie mini-SBOM w formacie
         * tekstowym (jedna linia = jedna zaleznosc, ze scope).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_GenerateSimpleJsonSbomFromParsedDependencies {
        /*
         * 🧪 Zadanie 12:
         * Wygeneruj mini-SBOM jako String w formacie JSON (recznie, bez
         * biblioteki) - lista obiektow `{groupId, artifactId, version}`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_SimulateDependencyConfusionScenario {
        /*
         * 🧪 Zadanie 13:
         * Zasymuluj (na Mapach udajacych 2 repozytoria: "prywatne" i
         * "publiczne") atak dependency confusion - pokaz, ktora wersja
         * zostalaby pobrana przy zlej konfiguracji priorytetow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementSimpleTyposquattingDetector {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj metode wykrywajaca "podejrzanie bliskie" nazwy
         * artefaktow (np. odleglosc Levenshteina <= 2 od znanej, zaufanej
         * nazwy) - przetestuj na kilku przykladach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_CompareLockedVersionsAcrossTwoBuilds {
        /*
         * 🧪 Zadanie 15:
         * Zasymuluj 2 "buildy" (Mapy artifactId->version) tego samego
         * projektu w roznym czasie - wykryj, KTORE zaleznosci zmienily
         * wersje miedzy nimi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementFakeVulnerabilityDatabaseLookup {
        /*
         * 🧪 Zadanie 16:
         * Zaimplementuj prosta "baze CVE" (Mapa artifactId:version ->
         * lista znanych podatnosci) i metode sprawdzajaca liste zaleznosci
         * projektu wobec niej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ExplainDifferenceBetweenCycloneDxAndSpdx {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: krotko opisz 2 popularne formaty SBOM
         * (CycloneDX, SPDX) - nie trzeba znac szczegolow, wystarczy
         * ogolne przeznaczenie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ValidateArtifactChecksumSimulation {
        /*
         * 🧪 Zadanie 18:
         * Zasymuluj weryfikacje sumy kontrolnej pobranego artefaktu
         * (oblicz SHA-256 Stringa "artefaktu" i porownaj z oczekiwana
         * wartoscia) - pokaz przypadek zgodny i NIEZGODNY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_AuditPomXmlForMissingVersionPins {
        /*
         * 🧪 Zadanie 19:
         * Rozbuduj parser z Zadania 4 o wykrywanie zaleznosci BEZ jawnie
         * podanej wersji (zarzadzanych przez BOM) - wypisz je osobno.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainReproducibleBuildsConceptually {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wyjasnij pojecie "reprodukowalnego builda"
         * (reproducible build) i jego zwiazek z bezpieczenstwem lancucha
         * dostaw.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildDependencyRiskScoringSystem {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj prosty system "punktacji ryzyka" zaleznosci (np.
         * +1 za brak pinowanej wersji, +2 za obecnosc w bazie CVE z
         * Zadania 16, +1 za scope "compile" zamiast "test") - posortuj
         * zaleznosci wedlug ryzyka.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_CompareTransitiveDependencyTreesAcrossTwoVersions {
        /*
         * 🧪 Zadanie 22:
         * Zasymuluj drzewo zaleznosci tranzytywnych dla 2 wersji tej samej
         * biblioteki - wykryj NOWE zaleznosci wprowadzone miedzy wersjami
         * (potencjalne nowe ryzyko).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementPolicyBasedDependencyGate {
        /*
         * 🧪 Zadanie 23:
         * Zaimplementuj "brame" (gate) odrzucajaca build, jesli
         * jakakolwiek zaleznosc ma znana podatnosc KRYTYCZNA (symulacja
         * kroku w CI/CD) - z czytelnym komunikatem, KTORA zaleznosc
         * zablokowala build.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SimulateSupplyChainAttackViaCompromisedTransitiveDep {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj (bez realnego kodu zlosliwego) scenariusz, w ktorym
         * TRANZYTYWNA zaleznosc "publikuje" nowa, zainfekowana wersje -
         * pokaz, jak latwo taka zmiana przechodzi NIEZAUWAZONA bez
         * dependency:tree/skanera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementSbomDiffBetweenTwoReleases {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj porownanie 2 mini-SBOM-ow (Zadanie 11) dwoch
         * wydan aplikacji - wypisz DODANE, USUNIETE i ZMIENIONE (wersja)
         * zaleznosci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_ExplainSlsaFrameworkConceptually {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: krotko opisz ogolna ideee frameworku SLSA
         * (Supply-chain Levels for Software Artifacts) - poziomy
         * zaufania do procesu builda.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementAllowlistOfTrustedGroupIds {
        /*
         * 🧪 Zadanie 27:
         * Zaimplementuj allowlist zaufanych `groupId` (np. tylko
         * `org.apache.*`, `com.fasterxml.*`, wlasna grupa firmowa) -
         * oznacz zaleznosci z pom.xml SPOZA tej listy jako wymagajace
         * dodatkowej weryfikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildAutomatedDependencyUpdateProposal {
        /*
         * 🧪 Zadanie 28:
         * Zasymuluj dzialanie Dependabota - majac Mape "aktualna wersja"
         * i Mape "najnowsza dostepna wersja", wygeneruj liste
         * proponowanych aktualizacji z rozroznieniem na PATCH/MINOR/MAJOR
         * (semantic versioning).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_CorrelateDependencyRiskWithArchitectureLayer {
        /*
         * 🧪 Zadanie 29:
         * Polacz ryzyko zaleznosci (Zadanie 21) z warstwa architektury,
         * w ktorej jest uzywana (patrz `_17_architecture`) - wyjasnij,
         * dlaczego podatna zaleznosc w warstwie obslugujacej dane
         * wejsciowe uzytkownika jest GROZNIEJSZA niz w narzedziu
         * deweloperskim.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteSupplyChainSecurityReport {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny raport bezpieczenstwa lancucha dostaw laczacy:
         * realny mini-SBOM z pom.xml (Zadanie 4/11), punktacje ryzyka
         * (Zadanie 21), wykrywanie typosquattingu (Zadanie 14), brame
         * polityki (Zadanie 23) - dla co najmniej 5 zaleznosci z czytelnym
         * podsumowaniem.
         */
        public static void main(String[] args) { }
    }
}
