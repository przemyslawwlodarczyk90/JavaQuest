package com.example.javaquest._21_spring_boot.Lesson14_BuildingExecutableJarAndNativeImage;

public class _Exercises_Lesson14_BuildingExecutableJarAndNativeImage {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainFatJarConcept {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij, czym jest "wykonywalny jar" i
         * dlaczego rozni sie od zwyklego jara.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_RunMvnPackageAndInspectJar {
        /*
         * 🧪 Zadanie 2:
         * W terminalu uruchom `mvnw.cmd package -DskipTests` - otworz
         * wynikowy `.jar` (np. `jar tf target/*.jar`) i znajdz
         * `BOOT-INF/classes`/`BOOT-INF/lib`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RunTheExecutableJar {
        /*
         * 🧪 Zadanie 3:
         * Uruchom wygenerowany jar (`java -jar target/*.jar`) - jesli
         * aplikacja NIE ma glownej klasy uruchomieniowej, wyjasnij
         * DLACZEGO (ten kurs ma WIELE `main()`, nie 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ExplainBootInfStructure {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: narysuj (tekst) strukture katalogow WEWNATRZ
         * wykonywalnego jara Boota.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_CompareWithUberJarApproach {
        /*
         * 🧪 Zadanie 5:
         * Bez terminala (powiaz z `_11_buildtools/Lesson07`): porownaj
         * layout Boota (zagniezdzone jary) z klasycznym "uber jarem"
         * (splaszczonym) - jakie problemy rozwiazuje layout Boota?
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_VerifyDevToolsExclusionFromJar {
        /*
         * 🧪 Zadanie 6:
         * (Jesli dodales DevTools z Lesson09) zweryfikuj, ze `.jar` NIE
         * zawiera klas DevTools.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CheckNativeImageAvailabilityOnOwnMachine {
        /*
         * 🧪 Zadanie 7:
         * Odtworz sprawdzenie `native-image --version` z teorii na
         * WLASNEJ maszynie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExplainAotProcessingRole {
        /*
         * 🧪 Zadanie 8:
         * Bez terminala: wyjasnij role Spring AOT (Ahead-Of-Time)
         * processing w kompilacji natywnej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ListNativeImageLimitations {
        /*
         * 🧪 Zadanie 9:
         * Bez terminala: wymien 3 ograniczenia natywnego obrazu (np.
         * brak dynamicznego ladowania klas).
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareStartupTimeJvmVsNativeConceptually {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala (dokumentacja/artykuly): porownaj TYPOWY czas
         * startu aplikacji Boot na JVM vs jako natywny obraz.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_CustomizeMainClassInPlugin {
        /*
         * 🧪 Zadanie 11:
         * Bez terminala (dokumentacja): jak WSKAZAC KONKRETNA glowna
         * klase (`<mainClass>`) w konfiguracji `spring-boot-maven-plugin`,
         * jesli projekt ma WIELE klas z `main()` (jak TEN kurs)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_BuildJarForSpecificLessonMainClass {
        /*
         * 🧪 Zadanie 12:
         * Skonfiguruj plugin (Zadanie 11) DLA KONKRETNEJ lekcji tego
         * kursu - zbuduj i URUCHOM wynikowy jar.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementLayeredJarConfiguration {
        /*
         * 🧪 Zadanie 13:
         * Bez terminala (dokumentacja): sprawdz "layered jar" -
         * `spring-boot-maven-plugin` moze DZIELIC jar na WARSTWY
         * (dependencies/application) - PO CO (Docker layer caching)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ExtractLayeredJarLayers {
        /*
         * 🧪 Zadanie 14:
         * Jesli masz layered jar, wyodrebnij WARSTWY
         * (`java -Djarmode=layertools -jar app.jar extract`) - zbadaj
         * strukture wynikowa.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ExplainDockerfileForSpringBootApp {
        /*
         * 🧪 Zadanie 15:
         * Bez terminala: napisz (jako tekst) prosty `Dockerfile` dla
         * aplikacji Boot - WYKORZYSTAJ layered jar (Zadanie 13-14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_CompareBuildTimeJvmVsNative {
        /*
         * 🧪 Zadanie 16:
         * (Jesli masz GraalVM) zmierz czas `mvnw.cmd package` (zwykly
         * jar) vs `mvnw.cmd -Pnative native:compile` (natywny obraz) -
         * porownaj.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MeasureNativeImageStartupTime {
        /*
         * 🧪 Zadanie 17:
         * (Jesli masz GraalVM) zmierz CZAS STARTU natywnego obrazu vs
         * JVM dla TEJ SAMEJ aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementRuntimeHintsForCustomReflection {
        /*
         * 🧪 Zadanie 18:
         * Bez terminala (dokumentacja): sprawdz interfejs
         * `RuntimeHintsRegistrar` - jak RECZNIE zarejestrowac refleksje
         * dla WLASNEJ klasy uzywanej dynamicznie?
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_CompareNativeImageMemoryUsage {
        /*
         * 🧪 Zadanie 19:
         * (Jesli masz GraalVM) zmierz zuzycie pamieci (RSS procesu)
         * natywnego obrazu vs JVM DLA TEJ SAMEJ aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildJarVsNativeComparisonTable {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) tabele porownawcza zwykly jar vs
         * natywny obraz - kolumny: czas budowania, czas startu, pamiec,
         * ograniczenia.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomLauncherClass {
        /*
         * 🧪 Zadanie 21:
         * Zbadaj (dokumentacja/zrodla) `JarLauncher`/`PropertiesLauncher` -
         * jak DOKLADNIE Boot uruchamia kod z BOOT-INF struktury (skoro
         * to NIE standardowy classpath)?
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementCustomBuildScriptInvokingPackageProgrammatically {
        /*
         * 🧪 Zadanie 22:
         * Zaimplementuj (wzorem `_11_buildtools`) `ProcessBuilder`
         * wywolujacy `mvnw.cmd package` PROGRAMOWO - zweryfikuj sukces
         * budowania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_InspectJarContentsProgrammatically {
        /*
         * 🧪 Zadanie 23:
         * Uzyj `java.util.jar.JarFile`, zeby PROGRAMOWO zbadac WYNIKOWY
         * jar (Zadanie 2) - policz WPISY w `BOOT-INF/lib`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementMultiStageDockerBuildForNativeImage {
        /*
         * 🧪 Zadanie 24:
         * Napisz (jako tekst) WIELOETAPOWY `Dockerfile` budujacy natywny
         * obraz (etap 1: kompilacja GraalVM, etap 2: minimalny obraz
         * uruchomieniowy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCustomSpringAotProcessorExtension {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala (zaawansowana dokumentacja): sprawdz, jak
         * ROZSZERZYC Spring AOT processing WLASNYM
         * `BeanFactoryInitializationAotProcessor`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareNativeImageWithJvmClassDataSharing {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala (dokumentacja): porownaj natywny obraz GraalVM z
         * technika "Class Data Sharing" (CDS) - CZY to ALTERNATYWNE
         * podejscie do PRZYSPIESZENIA startu JVM?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementCiPipelineBuildingBothArtifacts {
        /*
         * 🧪 Zadanie 27:
         * Zaprojektuj (opisz) pipeline CI/CD budujacy OBA artefakty
         * (zwykly jar + natywny obraz) I publikujacy OBA do rejestru.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ImplementSizeOptimizationForFatJar {
        /*
         * 🧪 Zadanie 28:
         * Zbadaj (dokumentacja) techniki ZMNIEJSZANIA rozmiaru
         * wykonywalnego jara (wykluczanie niepotrzebnych zaleznosci,
         * `<excludes>`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementReproducibleBuildVerification {
        /*
         * 🧪 Zadanie 29:
         * Powiaz z `_19_security_basics/Lesson20` - zweryfikuj (przez
         * SHA-256, `_19_security_basics/Lesson18` wzorem), czy 2
         * KOLEJNE budowania TEGO SAMEGO kodu daja IDENTYCZNY jar
         * (reprodukowalnosc).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteDeploymentArtifactPipelineCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletny "pipeline" tworzenia artefaktu wdrożeniowego -
         * budowanie (Zadanie 22) + inspekcja (Zadanie 23) + weryfikacja
         * (Zadanie 29) + raport gotowosci do wdrozenia.
         */
        public static void main(String[] args) { }
    }
}
