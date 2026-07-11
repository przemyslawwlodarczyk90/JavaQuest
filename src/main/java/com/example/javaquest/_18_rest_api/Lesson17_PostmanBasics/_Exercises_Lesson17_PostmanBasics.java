package com.example.javaquest._18_rest_api.Lesson17_PostmanBasics;

public class _Exercises_Lesson17_PostmanBasics {

    /*
     * UWAGA: ta lekcja jest HYBRYDOWA (jak lekcje Maven/Gradle w
     * `_11_buildtools`) - Postman to zewnetrzne narzedzie GUI, nie da
     * sie go wywolac z `main()`. Wiekszosc zadan ponizej prosi o
     * WYKONANIE realnych krokow w Postmanie (zainstaluj z
     * https://www.postman.com/downloads/, uruchom lekcje teoretyczna
     * TEGO pakietu - `_Lesson17_PostmanBasics.java` - zeby dostac
     * dzialajacy lokalny URL do przetestowania). `main()` w kazdym
     * zadaniu zostaje PUSTY zgodnie z konwencja kursu.
     */

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_InstallPostmanAndCreateAccount {
        /*
         * 🧪 Zadanie 1:
         * Zainstaluj Postmana (desktop lub web) i utworz darmowe konto -
         * bez terminala: opisz w komentarzu, co widzisz w glownym oknie
         * po pierwszym uruchomieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_CreateFirstGetRequest {
        /*
         * 🧪 Zadanie 2:
         * Uruchom `_Lesson17_PostmanBasics.main()`, sprawdz wypisany port,
         * i w Postmanie utworz oraz wyslij GET do
         * http://localhost:<port>/api/articles - zapisz odpowiedz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_CreatePostRequestWithJsonBody {
        /*
         * 🧪 Zadanie 3:
         * W Postmanie utworz POST do /api/articles z cialem JSON (zakladka
         * Body -> raw -> JSON) - zweryfikuj status 201 w odpowiedzi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_AddCustomHeaderInPostman {
        /*
         * 🧪 Zadanie 4:
         * W dowolnym requescie z Zadania 2/3 dodaj naglowek "X-Client-Name:
         * JavaQuest" w zakladce Headers - zweryfikuj, ze zostal wyslany
         * (mozesz to sprawdzic np. przez lekcje echo z Lesson01).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_SaveRequestsToCollection {
        /*
         * 🧪 Zadanie 5:
         * Zapisz oba requesty (GET i POST) do NOWEJ kolekcji "JavaQuest
         * Lesson17" - zweryfikuj, ze widzisz je pogrupowane w panelu bocznym.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_CreateEnvironmentWithBaseUrlVariable {
        /*
         * 🧪 Zadanie 6:
         * Utworz srodowisko "Lokalne" ze zmienna "baseUrl" ustawiona na
         * http://localhost:<port> - podmien URL w obu requestach na
         * {{baseUrl}}/api/articles.
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_SwitchBetweenTwoEnvironments {
        /*
         * 🧪 Zadanie 7:
         * Utworz 2. srodowisko "Testowe" z INNYM (nieistniejacym) baseUrl -
         * przelacz sie na nie i zaobserwuj blad polaczenia - wroc do
         * "Lokalne" i zweryfikuj sukces.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_ExportCollectionToJsonFile {
        /*
         * 🧪 Zadanie 8:
         * Wyeksportuj kolekcje z Zadania 5 do pliku .json (Postman:
         * przycisk "..." przy kolekcji -> Export) - otworz plik w
         * edytorze tekstu i opisz w komentarzu jego strukture.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ImportExportedCollectionBack {
        /*
         * 🧪 Zadanie 9:
         * Usun kolekcje z Postmana, a nastepnie ZAIMPORTUJ z powrotem
         * plik z Zadania 8 - zweryfikuj, ze wszystkie requesty wrocily
         * BEZ zmian.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ExplainCollectionVsEnvironmentDifference {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wyjasnij wlasnymi slowami roznice miedzy
         * "kolekcja" a "srodowisko" w Postmanie - co przechowuje kazde z nich?
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WriteTestScriptCheckingStatusCode {
        /*
         * 🧪 Zadanie 11:
         * W zakladce "Tests" requestu GET dodaj skrypt sprawdzajacy, ze
         * `pm.response.code === 200` - uzyj `pm.test(...)` - zweryfikuj
         * zielony wynik testu po wyslaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_WriteTestScriptCheckingResponseBodyField {
        /*
         * 🧪 Zadanie 12:
         * Rozszerz test z Zadania 11 o sprawdzenie konkretnego pola w
         * ciele odpowiedzi (np. czy tablica NIE jest pusta) - uzyj
         * `pm.response.json()`.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_ExtractValueToEnvironmentVariable {
        /*
         * 🧪 Zadanie 13:
         * W tescie POST (Zadanie 3) zapisz "id" z odpowiedzi do zmiennej
         * srodowiskowej (`pm.environment.set(...)`) - uzyj tej zmiennej w
         * URL KOLEJNEGO requestu (np. GET /api/articles/{{lastCreatedId}}).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementPreRequestScriptGeneratingTimestamp {
        /*
         * 🧪 Zadanie 14:
         * Dodaj Pre-request Script generujacy aktualny timestamp i
         * zapisujacy go do zmiennej - uzyj tej zmiennej jako naglowek
         * "X-Request-Time" w requescie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ChainThreeRequestsWithSharedState {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj lancuch 3 requestow w kolekcji, gdzie KAZDY kolejny
         * korzysta ze zmiennej ustawionej przez POPRZEDNI (np. utworz
         * artykul -> pobierz go po zwroconym ID -> "usun" go) - uruchom
         * WSZYSTKIE 3 naraz przez Collection Runner.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_UseCollectionRunnerForBatchExecution {
        /*
         * 🧪 Zadanie 16:
         * Uzyj wbudowanego "Collection Runner" (lub "Run collection") do
         * wykonania CALEJ kolekcji naraz - opisz w komentarzu, co widzisz
         * w podsumowaniu wynikow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementNegativeTestCase {
        /*
         * 🧪 Zadanie 17:
         * Dodaj request do NIEISTNIEJACEGO endpointu (np.
         * /api/articles/999) z testem sprawdzajacym status 404 - to
         * przypomnienie, ze testy NEGATYWNE sa rownie wazne jak pozytywne.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_UsePostmanConsoleForDebugging {
        /*
         * 🧪 Zadanie 18:
         * Otworz konsole Postmana (View -> Show Postman Console) i
         * przeanalizuj SUROWY request/odpowiedz (naglowki, cialo) dla
         * dowolnego wyslanego requestu - opisz w komentarzu, co tam widac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ImplementVariableScopePrecedence {
        /*
         * 🧪 Zadanie 19:
         * Zdefiniuj TA SAMA zmienna (np. "baseUrl") na poziomie kolekcji
         * ORAZ srodowiska z ROZNYMI wartosciami - zweryfikuj, KTORA
         * "wygrywa" (kolejnosc pierwszenstwa zmiennych w Postmanie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_DocumentRequestsWithDescriptions {
        /*
         * 🧪 Zadanie 20:
         * Dodaj opisy (zakladka Documentation) do kazdego requestu w
         * kolekcji, wyjasniajace jego przeznaczenie - opublikuj (lub
         * podejrzyj) automatycznie wygenerowana dokumentacje kolekcji.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_InstallAndRunNewmanCli {
        /*
         * 🧪 Zadanie 21:
         * Zainstaluj Newman (CLI runner Postmana, `npm install -g newman`)
         * i uruchom wyeksportowana kolekcje z Zadania 8 z terminala BEZ
         * otwierania Postmana - opisz wynik w komentarzu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_IntegrateNewmanIntoCiPipelineConcept {
        /*
         * 🧪 Zadanie 22:
         * Bez terminala: zaprojektuj (opisz kroki) integracje uruchamiania
         * kolekcji Newman jako czesc pipeline'u CI/CD (nawiazanie do
         * `_11_buildtools`) - kiedy testy API powinny sie uruchamiac?
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_BuildMockServerFromCollection {
        /*
         * 🧪 Zadanie 23:
         * Uzyj funkcji Postman "Mock Server" - stworz mock zwracajacy
         * przykladowe odpowiedzi BEZ dzialajacego serwera JavaQuest -
         * opisz, jak to moze pomoc zespolowi frontendowemu pracowac
         * ROWNOLEGLE z backendem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ImplementOAuth2FlowInPostman {
        /*
         * 🧪 Zadanie 24:
         * Skonfiguruj autoryzacje typu "OAuth 2.0" w zakladce Authorization
         * requestu (mozesz uzyc dowolnego publicznego, testowego endpointu
         * OAuth2 do eksperymentu) - opisz w komentarzu przeplyw, jaki widziales.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ImplementDynamicVariablesWithFakerLibrary {
        /*
         * 🧪 Zadanie 25:
         * Uzyj wbudowanych dynamicznych zmiennych Postmana (np.
         * `{{$randomEmail}}`, `{{$timestamp}}`) w ciele requestu POST -
         * zweryfikuj, ze KAZDE wyslanie generuje INNE dane.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildComprehensiveTestSuiteForFullApi {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj kompletna kolekcje testujaca WSZYSTKIE endpointy z
         * Lesson04 (`Lesson04_HttpMethods`) - min. 8 requestow z testami
         * sprawdzajacymi status i ksztalt odpowiedzi dla kazdego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementDataDrivenTestingWithCsvFile {
        /*
         * 🧪 Zadanie 27:
         * Przygotuj plik CSV z wieloma zestawami danych testowych - uzyj
         * go jako zrodla danych w Collection Runner (data-driven testing) -
         * uruchom TEN SAM request wielokrotnie z ROZNYMI danymi.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_GenerateCodeSnippetFromPostmanRequest {
        /*
         * 🧪 Zadanie 28:
         * Uzyj funkcji Postmana "Code" (generuje kod klienta w wybranym
         * jezyku) dla dowolnego requestu - wybierz "Java - OkHttp" lub
         * podobny i porownaj wygenerowany kod z tym, co napisales(-as)
         * recznie w poprzednich lekcjach tego rozdzialu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_ImplementCiGateBlockingOnFailedApiTests {
        /*
         * 🧪 Zadanie 29:
         * Zaprojektuj (opisz konfiguracje) bramke jakosci w CI, ktora
         * BLOKUJE wdrozenie, jesli kolekcja Newman (Zadanie 21) zwroci
         * KTORYKOLWIEK nieudany test - jaki kod wyjscia zwraca Newman
         * przy niepowodzeniu?
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildFullPostmanWorkflowForJavaQuestApi {
        /*
         * 🧪 Zadanie 30:
         * Zbuduj KOMPLETNY workflow Postmana dla mini-API z tego
         * rozdzialu (min. 6 endpointow z roznych lekcji) - kolekcja +
         * 2 srodowiska (dev/prod-symulowane) + testy dla kazdego requestu +
         * lancuchowanie zmiennych + uruchomienie calosci przez Newman -
         * opisz KAZDY krok w komentarzu.
         */
        public static void main(String[] args) { }
    }
}
