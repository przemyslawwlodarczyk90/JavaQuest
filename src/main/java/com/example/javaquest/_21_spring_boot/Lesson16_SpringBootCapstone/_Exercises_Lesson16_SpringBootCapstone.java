package com.example.javaquest._21_spring_boot.Lesson16_SpringBootCapstone;

public class _Exercises_Lesson16_SpringBootCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_ListAllMechanismsUsedInCapstone {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: wypisz WSZYSTKIE mechanizmy Springa Boota uzyte
         * w kapsztonie (min. 6) - dla kazdego podaj lekcje, w ktorej byl
         * wprowadzony.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddDeleteNoteEndpoint {
        /*
         * 🧪 Zadanie 2:
         * Dodaj endpoint `DELETE /notes/{id}` usuwajacy notatke z
         * `NotesRepository`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddPostNoteEndpoint {
        /*
         * 🧪 Zadanie 3:
         * Dodaj endpoint `POST /notes` przyjmujacy nowa notatke w ciele
         * zadania.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TriggerMaxNotesLimitAndObserveException {
        /*
         * 🧪 Zadanie 4:
         * Ustaw `javaquest.notes.max-notes=1` - zweryfikuj wyjatek przy
         * probie dodania 2. notatki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VerifyHealthGoesDownWhenLimitReached {
        /*
         * 🧪 Zadanie 5:
         * Zweryfikuj, ze `/actuator/health` pokazuje status DOWN, gdy
         * limit notatek jest OSIAGNIETY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddThirdProfileForTesting {
        /*
         * 🧪 Zadanie 6:
         * Dodaj 3. profil "test" z WLASNYM `CommandLineRunner`
         * (symulacja danych testowych, roznych od "dev").
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_CustomizeWelcomeMessagePerProfile {
        /*
         * 🧪 Zadanie 7:
         * Zweryfikuj, ze `welcomeMessage` jest RÓZNY w KAZDYM z 3
         * profili (dev/prod/test).
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddLoggingToEachRequestHandler {
        /*
         * 🧪 Zadanie 8:
         * Dodaj logowanie (Lesson10) DO KAZDEGO endpointu - zweryfikuj
         * poprawne wpisy w konsoli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ExposeAdditionalActuatorEndpoint {
        /*
         * 🧪 Zadanie 9:
         * Wystaw DODATKOWY endpoint Actuatora (np. `/actuator/info`) -
         * dodaj WLASNE metadane aplikacji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_TraceFullRequestFlowThroughAllLayers {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: narysuj (tekst/ASCII) PELNY przeplyw `GET
         * /notes` - kontroler -> repozytorium -> odpowiedz JSON.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – SREDNI (11-20)

    static class Exercise11_AddValidationToNoteCreation {
        /*
         * 🧪 Zadanie 11:
         * Dodaj Bean Validation (powiaz z `_19_security_basics/Lesson17`)
         * do endpointu POST z Zadania 3 - odrzuc PUSTA tresc notatki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_AddCustomMetricForNotesCreated {
        /*
         * 🧪 Zadanie 12:
         * Dodaj WLASNA metryke (Lesson13) LICZACA utworzone notatki -
         * zweryfikuj przez `/actuator/metrics`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ImplementSearchEndpointWithQueryParam {
        /*
         * 🧪 Zadanie 13:
         * Dodaj `GET /notes/search?query=...` filtrujacy notatki po
         * tresci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_AddSecondConfigurationPropertiesClass {
        /*
         * 🧪 Zadanie 14:
         * Dodaj DRUGA klase `@ConfigurationProperties` (np. dla
         * ustawien "wyswietlania") - zarejestruj OBOK istniejacej.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ImplementCustomExceptionHandlerForNotesLimitExceeded {
        /*
         * 🧪 Zadanie 15:
         * Powiaz z `Lesson11_ErrorHandlingBasics` - zwroc CZYTELNY,
         * WLASNY JSON bledu (nie domyslny Boota) przy przekroczeniu
         * limitu notatek.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AddPersistenceSimulationWithDelay {
        /*
         * 🧪 Zadanie 16:
         * Zasymuluj "opoznienie zapisu do bazy" (`Thread.sleep`) w
         * `NotesRepository` - zmierz WPLYW na Timer z Lesson13.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementNotesCountLimitPerProfile {
        /*
         * 🧪 Zadanie 17:
         * Ustaw RÓZNY `max-notes` per profil (przez
         * `application-{profil}.properties`, powiaz z Lesson06) -
         * zweryfikuj RÓZNE limity.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddIntegrationTestWithoutFramework {
        /*
         * 🧪 Zadanie 18:
         * Napisz "test integracyjny" (zwykla metoda `main`, BEZ
         * frameworka) URUCHAMIAJACY PELNY kapszton i WERYFIKUJACY 5+
         * asercji na odpowiedziach HTTP.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementGracefulShutdownForCapstone {
        /*
         * 🧪 Zadanie 19:
         * Skonfiguruj `server.shutdown=graceful` (powiaz z Lesson12,
         * Zadanie 22) DLA kapsztonu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BuildFullSystemHealthReport {
        /*
         * 🧪 Zadanie 20:
         * Zbuduj raport "stanu systemu" wypisywany na koniec KAZDEGO
         * scenariusza - liczba notatek, aktywny profil, status health.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_RefactorCapstoneToUseCustomAutoConfiguration {
        /*
         * 🧪 Zadanie 21:
         * Przeksztalc konfiguracje `NotesApp` (Lesson15) na WLASNA klase
         * `@AutoConfiguration` - zweryfikuj, ze CALY kapszton DALEJ
         * dziala.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ImplementFullAopAuditLoggingForAllEndpoints {
        /*
         * 🧪 Zadanie 22:
         * Powiaz z `_20_spring_core/Lesson21-22` - dodaj `@Aspect`
         * AUDYTUJACY WSZYSTKIE wywolania kontrolera (czas, argumenty,
         * wynik).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementEventDrivenNotificationOnNoteCreated {
        /*
         * 🧪 Zadanie 23:
         * Powiaz z `_20_spring_core/Lesson20` - publikuj zdarzenie
         * `NoteCreatedEvent` PO KAZDYM dodaniu notatki, z OSOBNYM
         * listenerem "powiadomien".
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementRateLimitingForNotesApi {
        /*
         * 🧪 Zadanie 24:
         * Powiaz z `_18_rest_api/Lesson16` - dodaj rate limiting dla
         * `POST /notes` (max N zadan/minute).
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementCircuitBreakerForSimulatedExternalCall {
        /*
         * 🧪 Zadanie 25:
         * Dodaj symulowane wywolanie ZEWNETRZNEGO serwisu (np.
         * "sprawdzenie pisowni") z circuit breaker (powiaz z
         * `Lesson11`, Zadanie 25).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MeasureFullEndToEndPerformanceUnderLoad {
        /*
         * 🧪 Zadanie 26:
         * Wyslij 1000 zadan RÓWNOLEGLE (`_05_multithreading`) do
         * dzialajacego kapsztonu - zmierz PRZEPUSTOWOSC i BLEDY.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementFullConfigurationPropertiesValidationSuite {
        /*
         * 🧪 Zadanie 27:
         * Dodaj PELNA walidacje (powiaz z Lesson08, Zadanie 5) do
         * `NotesProperties` - odrzuc NIEROZSADNE wartosci (np.
         * `max-notes=0`).
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_BuildAndRunAsRealExecutableJar {
        /*
         * 🧪 Zadanie 28:
         * Powiaz z Lesson14 - zbuduj kapszton jako REALNY, wykonywalny
         * jar i URUCHOM go PRZEZ `java -jar`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementMultiTenantNotesIsolation {
        /*
         * 🧪 Zadanie 29:
         * Rozbuduj system o izolacje "per uzytkownik" (WLASNA lista
         * notatek per "tenant ID" w naglowku zadania).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildCompleteProductionReadyNotesServiceCapstone {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj OSTATECZNA, GOTOWA DO PRODUKCJI wersje serwisu notatek -
         * WALIDACJA + METRYKI + AUDYT + RATE LIMITING + WLASNA obsluga
         * bledow + PELNE testy - podsumowanie CALEGO rozdzialu
         * `_21_spring_boot` w JEDNYM projekcie.
         */
        public static void main(String[] args) { }
    }
}
