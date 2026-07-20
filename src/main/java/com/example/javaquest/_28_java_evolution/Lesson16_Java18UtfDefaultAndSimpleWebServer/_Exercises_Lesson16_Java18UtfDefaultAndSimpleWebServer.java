package com.example.javaquest._28_java_evolution.Lesson16_Java18UtfDefaultAndSimpleWebServer;

public class _Exercises_Lesson16_Java18UtfDefaultAndSimpleWebServer {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_PrintDefaultCharsetAndConfirmItIsUtf8 {
        /* 🧪 Zadanie 1: Wypisz `Charset.defaultCharset()` I potwierdz, ze TO UTF-8. */
        public static void main(String[] args) { }
    }

    static class Exercise02_WriteAndReadFileWithPolishCharactersWithoutExplicitCharset {
        /* 🧪 Zadanie 2: Zapisz I odczytaj plik Z polskimi znakami BEZ jawnego podawania charsetu. */
        public static void main(String[] args) { }
    }

    static class Exercise03_ConvertBytesToStringWithoutExplicitCharsetAndVerifyUtf8 {
        /* 🧪 Zadanie 3: Skonwertuj bajty NA String BEZ jawnego charsetu I zweryfikuj, ze UZYTO UTF-8. */
        public static void main(String[] args) { }
    }

    static class Exercise04_StartSimpleFileServerServingSingleHtmlFile {
        /* 🧪 Zadanie 4: Uruchom `SimpleFileServer` SERWUJACY 1 plik HTML. */
        public static void main(String[] args) { }
    }

    static class Exercise05_SendGetRequestToSimpleFileServerAndReadResponse {
        /* 🧪 Zadanie 5: Wyslij zadanie GET DO uruchomionego `SimpleFileServer` I odczytaj odpowiedz. */
        public static void main(String[] args) { }
    }

    static class Exercise06_StopSimpleFileServerGracefully {
        /* 🧪 Zadanie 6: Zatrzymaj `SimpleFileServer` W bloku `finally`. */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhyUtf8DefaultMattersForCrossPlatformProjects {
        /* 🧪 Zadanie 7: Bez terminala - wyjasnij, DLACZEGO domyslny UTF-8 jest WAZNY DLA projektow CROSS-PLATFORM. */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainJwebserverCommandLineEquivalent {
        /* 🧪 Zadanie 8: Bez terminala - opisz, JAKIEJ komendy `jwebserver` uzylbys ZAMIAST kodu Javy. */
        public static void main(String[] args) { }
    }

    static class Exercise09_CompareOldPlatformDependentDefaultCharsetBehaviorConceptually {
        /* 🧪 Zadanie 9: Bez terminala - porownaj (koncepcyjnie) STARE zachowanie (charset ZALEZNY OD platformy) Z NOWYM. */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListFilesServedBySimpleFileServerInDirectory {
        /* 🧪 Zadanie 10: Zbuduj katalog Z KILKOMA plikami I wypisz JAKIE sciezki SERWUJE `SimpleFileServer`. */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_ServeMultipleFilesAndFetchEachWithHttpClient {
        /* 🧪 Zadanie 11: Serwuj WIELE plikow (HTML/JSON/tekst) I POBIERZ KAZDY Z NICH przez `HttpClient`. */
        public static void main(String[] args) { }
    }

    static class Exercise12_ConfigureSimpleFileServerOutputLevelAndObserveLogging {
        /* 🧪 Zadanie 12: Skonfiguruj `SimpleFileServer.OutputLevel` NA `VERBOSE` I zaobserwuj logowanie zadan. */
        public static void main(String[] args) { }
    }

    static class Exercise13_TestFileNotFoundBehaviorOfSimpleFileServer {
        /* 🧪 Zadanie 13: Sprawdz zachowanie `SimpleFileServer` PRZY zadaniu O NIEISTNIEJACY plik (404). */
        public static void main(String[] args) { }
    }

    static class Exercise14_CombineSimpleFileServerWithCustomHttpHandlerContext {
        /* 🧪 Zadanie 14: POLACZ `SimpleFileServer` (statyczne pliki) Z WLASNYM `HttpHandler` NA INNEJ sciezce. */
        public static void main(String[] args) { }
    }

    static class Exercise15_WriteFileWithNonAsciiCharactersFromMultipleLanguagesAndVerifyRoundTrip {
        /* 🧪 Zadanie 15: Zapisz plik ZE znakami Z KILKU jezykow (polski/niemiecki/japonski) I zweryfikuj poprawny ROUND-TRIP. */
        public static void main(String[] args) { }
    }

    static class Exercise16_ExplicitlySpecifyLegacyCharsetAndCompareWithDefaultUtf8 {
        /* 🧪 Zadanie 16: JAWNIE podaj STARY charset (np. `ISO-8859-1`) I porownaj wynik Z domyslnym UTF-8. */
        public static void main(String[] args) { }
    }

    static class Exercise17_BuildStaticDocumentationSiteServedBySimpleFileServer {
        /* 🧪 Zadanie 17: Zbuduj PROSTA, statyczna strone dokumentacji (kilka plikow HTML) SERWOWANA przez `SimpleFileServer`. */
        public static void main(String[] args) { }
    }

    static class Exercise18_MeasureStartupTimeOfSimpleFileServerVsEmbeddedTomcat {
        /* 🧪 Zadanie 18: Powiaz z `_07_servlets` - porownaj CZAS startu `SimpleFileServer` Z embedded Tomcatem. */
        public static void main(String[] args) { }
    }

    static class Exercise19_ServeGeneratedOpenApiYamlFileUsingSimpleFileServer {
        /* 🧪 Zadanie 19: Powiaz z `_18_rest_api/Lesson18` - wygeneruj plik OpenAPI YAML I SERWUJ go przez `SimpleFileServer`. */
        public static void main(String[] args) { }
    }

    static class Exercise20_ExplainWhenSimpleFileServerIsAppropriateVsFullWebServer {
        /* 🧪 Zadanie 20: Bez terminala - wyjasnij, KIEDY `SimpleFileServer` WYSTARCZY, A KIEDY POTRZEBNY PELNY serwer WWW. */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_BuildLocalDevelopmentPreviewServerForGeneratedReports {
        /* 🧪 Zadanie 21: Zbuduj LOKALNY serwer podgladu DLA wygenerowanych raportow HTML (powiazanie Z `_16_clean_code`/`_25_unit_testing` coverage). */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomFilterWrappingSimpleFileServerForAccessLogging {
        /* 🧪 Zadanie 22: Zaimplementuj WLASNY `Filter` OPAKOWUJACY `SimpleFileServer` DO logowania dostepu. */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildCharsetMigrationCheckerScanningFilesForNonUtf8Bytes {
        /* 🧪 Zadanie 23: Zbuduj PROSTY "skaner" WYKRYWAJACY pliki Z bajtami NIEZGODNYMI Z UTF-8 (przydatne PRZY migracji legacy projektu). */
        public static void main(String[] args) { }
    }

    static class Exercise24_ServeMultiModuleStaticSiteWithDifferentContextPaths {
        /* 🧪 Zadanie 24: Serwuj WIELOMODULOWA statyczna strone (rozne sciezki KONTEKSTOWE) przez WIELE `SimpleFileServer` NA roznych portach. */
        public static void main(String[] args) { }
    }

    static class Exercise25_BuildDeploymentPreviewToolCombiningJarBuildAndSimpleFileServer {
        /* 🧪 Zadanie 25: Powiaz z `_11_buildtools` - zbuduj JAR, WYPAKUJ zasoby statyczne, I SERWUJ je przez `SimpleFileServer`. */
        public static void main(String[] args) { }
    }

    static class Exercise26_ImplementGracefulShutdownHookForSimpleFileServerOnJvmExit {
        /* 🧪 Zadanie 26: Zaimplementuj `Runtime.getRuntime().addShutdownHook(...)` ZATRZYMUJACY `SimpleFileServer` PRZY zamknieciu JVM. */
        public static void main(String[] args) { }
    }

    static class Exercise27_DesignMigrationChecklistForLegacyProjectsWithHardcodedCharsets {
        /* 🧪 Zadanie 27: Zaprojektuj LISTE kontrolna MIGRACJI legacy projektu Z ZAHARDKODOWANYMI charsetami platformowymi NA jawny UTF-8. */
        public static void main(String[] args) { }
    }

    static class Exercise28_BenchmarkThroughputOfSimpleFileServerUnderConcurrentRequests {
        /* 🧪 Zadanie 28: Zmierz PRZEPUSTOWOSC `SimpleFileServer` PRZY WIELU rownoleglych zadaniach (powiazanie Z `_05_multithreading`). */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildLocalCiArtifactPreviewPipelineUsingSimpleFileServer {
        /* 🧪 Zadanie 29: Zaprojektuj (koncepcyjnie) LOKALNY pipeline podgladu artefaktow CI (powiazanie Z `_11_buildtools/Lesson27`) uzywajac `SimpleFileServer`. */
        public static void main(String[] args) { }
    }

    static class Exercise30_DesignFullCharsetAndStaticServingStrategyForCourseWebsite {
        /* 🧪 Zadanie 30: Zaprojektuj PELNA strategie kodowania znakow I SERWOWANIA statycznych zasobow DLA hipotetycznej strony kursu online. */
        public static void main(String[] args) { }
    }
}
