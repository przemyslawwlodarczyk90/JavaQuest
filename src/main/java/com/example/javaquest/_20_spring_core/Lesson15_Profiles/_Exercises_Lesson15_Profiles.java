package com.example.javaquest._20_spring_core.Lesson15_Profiles;

public class _Exercises_Lesson15_Profiles {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ExplainWhatProfileAnnotationDoes {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wyjasnij wlasnymi slowami, co robi `@Profile`
         * na beanie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ImplementTwoProfileSpecificImplementations {
        /*
         * 🧪 Zadanie 2:
         * Zaimplementuj WLASNY interfejs z 2 implementacjami - jedna
         * `@Profile("dev")`, druga `@Profile("prod")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ActivateDevProfileAndVerify {
        /*
         * 🧪 Zadanie 3:
         * Aktywuj profil "dev" i zweryfikuj, ze pobrany bean to
         * WLASCIWA implementacja z Zadania 2.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ActivateProdProfileAndVerify {
        /*
         * 🧪 Zadanie 4:
         * To samo dla profilu "prod".
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_TryGetBeanWithNoProfileActiveAndNoDefault {
        /*
         * 🧪 Zadanie 5:
         * NIE aktywuj zadnego profilu i NIE dodawaj beana `@Profile
         * ("default")` - zaobserwuj i zapisz blad przy `getBean()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddDefaultProfileBeanAsFallback {
        /*
         * 🧪 Zadanie 6:
         * Napraw Zadanie 5, dodajac bean `@Profile("default")`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_ExplainWhenDefaultProfileIsUsed {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wyjasnij DOKLADNIE, kiedy Spring uzywa profilu
         * "default" - czy to dziala, gdy aktywujesz INNY profil?
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ActivateMultipleProfilesAtOnce {
        /*
         * 🧪 Zadanie 8:
         * Aktywuj 2 profile naraz (`setActiveProfiles("a", "b")`) -
         * zweryfikuj, ze beany OBU profili powstaly.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_CheckActiveProfilesFromEnvironment {
        /*
         * 🧪 Zadanie 9:
         * Odczytaj `context.getEnvironment().getActiveProfiles()` -
         * wypisz WSZYSTKIE aktywne profile jako tablice.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_CompareProfileWithConditionalOnPropertyConceptually {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: porownaj `@Profile` z `@ConditionalOnProperty`
         * (zapowiedz `_21_spring_boot`) - czym sie roznia koncepcyjnie?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_ImplementNegatedProfileExpression {
        /*
         * 🧪 Zadanie 11:
         * Uzyj `@Profile("!prod")` (negacja) - zweryfikuj, ze bean
         * powstaje w KAZDYM profilu OPROCZ "prod".
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ImplementComplexProfileExpression {
        /*
         * 🧪 Zadanie 12:
         * Uzyj zlozonego wyrazenia `@Profile("dev & !test")` - przetestuj
         * WSZYSTKIE kombinacje aktywnych profili.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementProfileOnConfigurationClassLevel {
        /*
         * 🧪 Zadanie 13:
         * Zastosuj `@Profile` NA CALEJ klasie `@Configuration` (nie na
         * pojedynczym beanie) - zweryfikuj, ze WSZYSTKIE jej beany sa
         * warunkowe naraz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementProfileSpecificDataSourceSimulation {
        /*
         * 🧪 Zadanie 14:
         * Zasymuluj (bez prawdziwej bazy) konfiguracje "zrodla danych" -
         * `@Profile("dev")` = H2 in-memory (String opisujacy), `@Profile
         * ("prod")` = PostgreSQL (String opisujacy).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ReadActiveProfilesFromSystemProperty {
        /*
         * 🧪 Zadanie 15:
         * Ustaw profil przez `System.setProperty("spring.profiles.active",
         * "dev")` PRZED utworzeniem kontekstu (zamiast
         * `setActiveProfiles`) - zweryfikuj, ze TEZ dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ImplementProfileGuardedComponentScanFiltering {
        /*
         * 🧪 Zadanie 16:
         * Polacz `@Profile` z filtrem component-scanningu - zweryfikuj,
         * ze klasy `@Component` OZNACZONE niepasujacym profilem NIE sa
         * tworzone, mimo ze technicznie sa "widoczne" dla skanera.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_CompareProfileActivationMethodsAcrossContexts {
        /*
         * 🧪 Zadanie 17:
         * Bez terminala: porownaj 3 sposoby aktywacji profilu (Java API
         * `setActiveProfiles`, zmienna srodowiskowa, `application.yml` w
         * Boot) - kiedy uzyc ktorego?
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ImplementProfileSpecificLoggingLevelSimulation {
        /*
         * 🧪 Zadanie 18:
         * Zasymuluj poziom logowania zalezny od profilu (DEBUG dla dev,
         * WARN dla prod) - jako prosty String zwracany przez profile-owy
         * bean.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_TestAllProfileCombinationsSystematically {
        /*
         * 🧪 Zadanie 19:
         * Napisz petle testujaca WSZYSTKIE sensowne kombinacje aktywnych
         * profili (dev/prod/qa/brak) - wypisz, KTORE beany powstaja w
         * KAZDEJ kombinacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildProfileDecisionMatrix {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj (jako liste rekordow) macierz decyzyjna - dla danej
         * kombinacji aktywnych profili, KTORE beany istnieja w
         * kontekscie.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ImplementCustomConditionAsAlternativeToProfile {
        /*
         * 🧪 Zadanie 21:
         * Zaimplementuj WLASNY `Condition` (interfejs Springa, podstawa
         * `@Profile`/`@ConditionalOnProperty`) - warunkuj rejestracje
         * beana WLASNA logika (np. dzien tygodnia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_AnalyzeProfileAnnotationImplementationViaCondition {
        /*
         * 🧪 Zadanie 22:
         * Sprawdz przez reflekcje/dokumentacje, jak `@Profile` jest
         * ZAIMPLEMENTOWANY WEWNETRZNIE (jaka klase `Condition` uzywa
         * pod spodem) - powiaz z Zadaniem 21.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementEnvironmentSpecificBeanGraphWithSharedBase {
        /*
         * 🧪 Zadanie 23:
         * Zbuduj graf beanow, gdzie WSPOLNA baza (bez profilu) zalezy od
         * profile-owej implementacji (wstrzykniete PRZEZ interfejs) -
         * zweryfikuj poprawne dzialanie w KAZDYM profilu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_SimulateCiCdPipelineActivatingDifferentProfilesPerStage {
        /*
         * 🧪 Zadanie 24:
         * Zasymuluj (przez petle) pipeline CI/CD aktywujacy RÓZNE profile
         * na RÓZNYCH etapach (build/test/staging/prod) - zweryfikuj
         * poprawnosc konfiguracji na KAZDYM etapie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementProfileValidationPreventingInvalidCombinations {
        /*
         * 🧪 Zadanie 25:
         * Zaimplementuj walidacje ODRZUCAJACA NIEDOZWOLONA kombinacje
         * profili (np. "dev" i "prod" naraz to BLAD konfiguracji) - PRZED
         * startem kontekstu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_CompareProfileBasedConfigWithFeatureFlags {
        /*
         * 🧪 Zadanie 26:
         * Bez terminala: porownaj `@Profile` (srodowiskowe) z "feature
         * flagami" (per-funkcja, moga byc WLACZANE/WYLACZANE w runtime
         * bez restartu) - kiedy uzyc ktorego mechanizmu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementProfileAwareHealthCheckSimulation {
        /*
         * 🧪 Zadanie 27:
         * Zasymuluj "health check" zwracajacy RÓZNE informacje w
         * zaleznosci od aktywnego profilu (dev = pelne szczegoly, prod =
         * minimalne, bezpieczne dane) - powiaz z
         * `_21_spring_boot/Lesson12_SpringBootActuator`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildProfileAuditToolForLargeConfiguration {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj narzedzie skanujace WSZYSTKIE `BeanDefinition` w
         * kontekscie i wypisujace, KTORE profile SA wykorzystywane w
         * konfiguracji (metadane `@Profile` per bean).
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementGracefulProfileFallbackChain {
        /*
         * 🧪 Zadanie 29:
         * Zaimplementuj LANCUCH fallbackow profili - jesli "prod-eu" nie
         * jest dostepny, sprobuj "prod", potem "default" - z czytelnym
         * logiem KAZDEJ proby.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteMultiEnvironmentApplicationCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj kompletna, mala aplikacje z konfiguracja dla 3+
         * srodowisk (dev/qa/prod) - RÓZNE zrodlo danych, RÓZNY poziom
         * logowania, RÓZNE limity - zweryfikuj poprawne dzialanie w
         * KAZDYM z nich osobno.
         */
        public static void main(String[] args) { }
    }
}
