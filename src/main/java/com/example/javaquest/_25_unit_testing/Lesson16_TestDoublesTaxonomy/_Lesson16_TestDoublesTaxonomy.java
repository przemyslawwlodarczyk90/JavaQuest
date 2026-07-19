package com.example.javaquest._25_unit_testing.Lesson16_TestDoublesTaxonomy;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class _Lesson16_TestDoublesTaxonomy {

    public static void main(String[] args) {

        // Byte Buddy (silnik generujacy bajtkod pod spodem Mockito) OFICJALNIE wspiera
        // Jave TYLKO do wersji 24 - spy(...) NA KONKRETNEJ klasie (w odroznieniu od mock(...)
        // NA interfejsie) wymaga RETRANSFORMACJI bajtkodu tej klasy, co na JDK 25 bez tej
        // flagi konczy sie IllegalArgumentException ("Java 25 is not supported"). Flaga
        // WLACZA eksperymentalne wsparcie - musi byc ustawiona PRZED PIERWSZYM uzyciem
        // Mockito w tym procesie JVM.
        System.setProperty("net.bytebuddy.experimental", "true");

        System.out.println("=== LEKCJA 16: Taksonomia atrap testowych (dummy/fake/stub/spy/mock) ===");

        /*
         * ============================================================
         * 📦 Gerard Meszaros, "xUnit Test Patterns" (2007) - 5 RODZAJOW "test double"
         * ============================================================
         * "Mock" (Lesson13-15) TO TYLKO JEDEN Z 5 rodzajow OBIEKTOW
         * ZASTEPUJACYCH prawdziwa zaleznosc W tescie - W POTOCZNEJ mowie
         * WSZYSTKIE 5 CZESTO nazywa sie (niescisle) "mockami", ALE MAJA
         * ROZNE cele I ROZNA "inteligencje":
         *
         * 1. DUMMY   - obiekt PRZEKAZYWANY, bo API tego WYMAGA, ALE
         *              NIGDY NIE jest FAKTYCZNIE uzywany (np. `null`
         *              LUB PUSTY obiekt jako "wypelniacz" argumentu).
         * 2. FAKE    - DZIALAJACA implementacja, ALE UPROSZCZONA (np.
         *              `HashMap` UDAJACA baze danych - jak H2 in-memory
         *              W `_08_sql`, TYLKO NA MNIEJSZA skale, W PAMIECI
         *              JVM).
         * 3. STUB    - zwraca WCZESNIEJ USTALONE odpowiedzi NA
         *              wywolania, NIE sprawdza JAK zostal uzyty.
         * 4. SPY     - jak STUB, ALE DODATKOWO ZAPAMIETUJE, JAK zostal
         *              wywolany (do POZNIEJSZEJ weryfikacji) - Mockito
         *              `spy(...)` DODATKOWO OWIJA PRAWDZIWY obiekt
         *              (WYWOLANIA leca NAPRAWDE, chyba ze
         *              JAWNIE zastubowane).
         * 5. MOCK    - PROGRAMOWANE Z GORY OCZEKIWANIA CO DO wywolan,
         *              test JAWNIE `verify(...)`-uje interakcje
         *              (Lesson13-15).
         */
        System.out.println("Dummy=wypelniacz. Fake=uproszczona dzialajaca implementacja. Stub=zwraca ustalone odpowiedzi. Spy=stub+zapamietuje wywolania. Mock=programowane oczekiwania+verify.");

        runAndReport(TestDoublesShowcaseTest.class);

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - DUMMY - "wypelniacz" argumentu, NIGDY faktycznie uzywany.
         * - FAKE - DZIALAJACA, ALE UPROSZCZONA implementacja (np.
         *   `InMemoryUserRepository` zamiast prawdziwej bazy).
         * - STUB - Mockito `mock(...)` + `when(...).thenReturn(...)`,
         *   BEZ `verify(...)` (test sprawdza TYLKO WYNIK).
         * - SPY - Mockito `spy(prawdziwyObiekt)` - PRAWDZIWE metody
         *   DZIALAJA, chyba ze JAWNIE zastubowane (`when(spy.x())
         *   .thenReturn(...)` WYMAGA `doReturn(...).when(spy).x()` DLA
         *   metod Z EFEKTAMI UBOCZNYMI - ROZNICA warta zapamietania).
         * - MOCK - Mockito `mock(...)` + `verify(...)` (test sprawdza
         *   TEZ INTERAKCJE, NIE TYLKO wynik).
         * - W PRAKTYCE granica FAKE/STUB/SPY/MOCK bywa PLYNNA -
         *   WAZNIEJSZE OD ETYKIETY jest ROZUMIENIE, CO DOKLADNIE test
         *   SPRAWDZA (WYNIK czy INTERAKCJE) I DLACZEGO.
         */
        System.out.println("\n=== KONIEC LEKCJI 16 ===");
    }

    record User(String id, String email) {
    }

    interface UserRepository {
        User findById(String id);

        void save(User user);
    }

    interface AuditLogger {
        void log(String message);
    }

    static class UserRegistrationService {
        private final UserRepository repository;
        private final AuditLogger auditLogger;

        UserRegistrationService(UserRepository repository, AuditLogger auditLogger) {
            this.repository = repository;
            this.auditLogger = auditLogger;
        }

        void register(String id, String email) {
            User user = new User(id, email);
            repository.save(user);
            auditLogger.log("Zarejestrowano uzytkownika: " + id);
        }
    }

    // ============================================================
    // FAKE - dzialajaca, ale UPROSZCZONA implementacja (HashMap zamiast bazy)
    // ============================================================
    static class InMemoryUserRepository implements UserRepository {
        private final Map<String, User> storage = new HashMap<>();

        @Override
        public User findById(String id) {
            return storage.get(id);
        }

        @Override
        public void save(User user) {
            storage.put(user.id(), user);
        }
    }

    static class TestDoublesShowcaseTest {

        @Test
        void dummyIsPassedButNeverActuallyUsed() {
            // DUMMY - AuditLogger jest WYMAGANY przez konstruktor, ale W TEJ metodzie
            // (findByIdOnly) W OGOLE nie jest wywolywany - dummy MOZE byc nawet `null`,
            // jesli metoda GO nie dotyka, LUB "pusty" mock bez zadnego stubowania.
            AuditLogger dummyLogger = mock(AuditLogger.class);
            InMemoryUserRepository fakeRepository = new InMemoryUserRepository();
            fakeRepository.save(new User("U1", "a@example.com"));

            UserRegistrationService service = new UserRegistrationService(fakeRepository, dummyLogger);
            User found = fakeRepository.findById("U1");

            assertThat(found.email()).isEqualTo("a@example.com");
            // dummyLogger NIGDY nie zostal uzyty w tym tescie - CELOWO.
        }

        @Test
        void fakeRepositoryReallyStoresAndRetrievesData() {
            // FAKE - InMemoryUserRepository NAPRAWDE dziala (zapisuje/odczytuje),
            // tylko W UPROSZCZONY sposob (HashMap zamiast prawdziwej bazy z _08_sql).
            InMemoryUserRepository fakeRepository = new InMemoryUserRepository();
            AuditLogger dummyLogger = mock(AuditLogger.class);
            UserRegistrationService service = new UserRegistrationService(fakeRepository, dummyLogger);

            service.register("U2", "b@example.com");

            User stored = fakeRepository.findById("U2");
            assertThat(stored).isNotNull();
            assertThat(stored.email()).isEqualTo("b@example.com");
        }

        @Test
        void stubReturnsPreProgrammedAnswerWithoutVerifyingInteractions() {
            // STUB - mock zaprogramowany `when(...).thenReturn(...)`, test sprawdza
            // TYLKO WYNIK dzialania, BEZ zadnego `verify(...)` na koncu.
            UserRepository stubRepository = mock(UserRepository.class);
            when(stubRepository.findById("U3")).thenReturn(new User("U3", "c@example.com"));

            User result = stubRepository.findById("U3");

            assertThat(result.email()).isEqualTo("c@example.com");
            // BRAK verify(...) - to jest wlasnie ROZNICA miedzy STUBEM a MOCKIEM.
        }

        @Test
        void spyWrapsRealObjectAndRecordsRealCalls() {
            // SPY - owija PRAWDZIWY obiekt (InMemoryUserRepository), wywolania
            // LECA NAPRAWDE, ale Mockito DODATKOWO zapamietuje je do pozniejszej weryfikacji.
            InMemoryUserRepository realRepository = new InMemoryUserRepository();
            UserRepository spyRepository = spy(realRepository);

            spyRepository.save(new User("U4", "d@example.com"));
            User found = spyRepository.findById("U4");

            // PRAWDZIWA logika HashMap zadzialala - dane FAKTYCZNIE sie zapisaly.
            assertThat(found.email()).isEqualTo("d@example.com");
            // ORAZ spy ZAPAMIETAL wywolania - MOZNA je zweryfikowac jak na mocku.
            verify(spyRepository, times(1)).save(new User("U4", "d@example.com"));
            verify(spyRepository, times(1)).findById("U4");
        }

        @Test
        void mockVerifiesInteractionsNotJustResult() {
            // MOCK - test sprawdza, ZE auditLogger.log(...) ZOSTAL wywolany Z
            // OCZEKIWANA trescia - to jest sprawdzenie INTERAKCJI, nie tylko wyniku.
            UserRepository repositoryMock = mock(UserRepository.class);
            AuditLogger loggerMock = mock(AuditLogger.class);
            UserRegistrationService service = new UserRegistrationService(repositoryMock, loggerMock);

            service.register("U5", "e@example.com");

            verify(repositoryMock).save(new User("U5", "e@example.com"));
            verify(loggerMock).log("Zarejestrowano uzytkownika: U5");
        }
    }

    private static void runAndReport(Class<?> testClass) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(testClass))
                .build();
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        summary.printFailuresTo(new PrintWriter(System.out));
        System.out.println(testClass.getSimpleName() + " -> udane: " + summary.getTestsSucceededCount() + "/" + summary.getTestsFoundCount());
    }
}
