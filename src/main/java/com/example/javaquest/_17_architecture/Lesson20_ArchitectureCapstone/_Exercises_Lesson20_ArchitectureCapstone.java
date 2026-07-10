package com.example.javaquest._17_architecture.Lesson20_ArchitectureCapstone;

public class _Exercises_Lesson20_ArchitectureCapstone {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_MapEachLessonToCodeSectionInCapstone {
        /*
         * 🧪 Zadanie 1:
         * Bez terminala: przejdz przez kod `_Lesson20_ArchitectureCapstone` i
         * w komentarzu wypisz, KTORA klasa/metoda odpowiada KTOREJ z lekcji
         * 1-19 (min. 10 dopasowan).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddThirdCourseWithDifferentCapacity {
        /*
         * 🧪 Zadanie 2:
         * Dodaj do kapsztonu (skopiowanego lub zaimportowanego) TRZECI kurs
         * (`publishNewCourse`) z INNA pojemnoscia - zademonstruj zapis 2
         * studentow na niego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_AddThirdSubscriberToEnrollmentEvent {
        /*
         * 🧪 Zadanie 3:
         * Dodaj TRZECIEGO subskrybenta zdarzenia `EnrollmentCreated` (np.
         * modul "Analytics" zliczajacy zapisy) - zademonstruj, ze modul
         * Enrollments NIE wymagal zadnej zmiany.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_TriggerEachValidationLevelExplicitly {
        /*
         * 🧪 Zadanie 4:
         * Wywolaj kontroler z danymi wyzwalajacymi KAZDY z 3 poziomow
         * walidacji (Lesson15) osobno - potwierdz kody bledow (400/404/409).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_VerifyCacheHitOnSecondCourseLookup {
        /*
         * 🧪 Zadanie 5:
         * Dodaj tymczasowy `println` w `InMemoryCourseRepositoryAdapter.
         * findByCode` (lub podobny licznik) - zademonstruj, ze DRUGIE
         * wyszukanie TEGO SAMEGO kursu NIE trafia do "prawdziwego" adaptera
         * (obsluzone przez cache, Lesson14).
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_IdentifyWhichClassIsThePortAndWhichIsTheAdapter {
        /*
         * 🧪 Zadanie 6:
         * Bez terminala: w komentarzu wskaz w kodzie kapsztonu WSZYSTKIE
         * porty (interfejsy) i dla kazdego - WSZYSTKIE jego adaptery
         * (implementacje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_IdentifyTransactionBoundaryInEnrollUseCase {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wskaz w kodzie `EnrollStudentUseCase.enroll(...)`,
         * GDZIE dokladnie zaczyna sie i konczy granica transakcji (Lesson13) -
         * wyjasnij, dlaczego NIE obejmuje ona publikacji zdarzenia.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_AddNewErrorTypeToBoundaryHandler {
        /*
         * 🧪 Zadanie 8:
         * Dodaj NOWY wyjatek domenowy (np. `DuplicateEnrollmentException`) i
         * jego mapowanie w `BoundaryErrorHandler` - zademonstruj nowy
         * scenariusz bledu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_WriteAdrForCapstoneArchitectureChoice {
        /*
         * 🧪 Zadanie 9:
         * Napisz WLASNY, PELNY ADR (`Lesson02`) dla 1 decyzji architektonicznej
         * widocznej w kapsztonie (np. "dlaczego CoursesModuleApi, a nie
         * bezposredni dostep do CourseRepositoryPort z modulu Enrollments").
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ListAllNineteenLessonsAppliedInCapstoneFromMemory {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz w komentarzu (bez podgladania kodu) WSZYSTKIE
         * 19 wczesniejszych lekcji rozdzialu i dla kazdej - 1 zdanie o tym,
         * GDZIE w kapsztonie ja rozpoznajesz.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_AddThirdModuleForCertificates {
        /*
         * 🧪 Zadanie 11:
         * Dodaj TRZECI modul "Certificates" (bounded context, Lesson06) -
         * publiczne API z metoda `issueCertificate(String studentEmail,
         * String courseCode)`, subskrybujacy WLASNE zdarzenie (np. gdy
         * "kurs zostal ukonczony" - mozesz symulowac to zdarzenie recznie).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_RefactorCoursesModuleToExposeSecondUseCase {
        /*
         * 🧪 Zadanie 12:
         * Dodaj DRUGI przypadek uzycia do modulu Courses (np.
         * "increaseCapacity(String code, int extraSeats)") - zademonstruj
         * uzycie z modulu Enrollments (lub nowego kontrolera), zachowujac
         * granice modulu (Lesson17).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_AddApiVersioningToEnrollmentDto {
        /*
         * 🧪 Zadanie 13:
         * Dodaj DRUGA wersje `EnrollRequestDtoV2` z DODATKOWYM, opcjonalnym
         * polem (np. "promoCode") - zaimplementuj mapper akceptujacy OBIE
         * wersje i konwertujacy do WSPOLNEGO wewnetrznego ksztaltu (Lesson08).
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_ImplementDirectCallAlternativeToEventForComparison {
        /*
         * 🧪 Zadanie 14:
         * Zaimplementuj ALTERNATYWNA wersje `EnrollStudentUseCase`, ktora
         * zamiast publikowac zdarzenie, woluje `Notifications` BEZPOSREDNIO
         * (Lesson17 styl) - w komentarzu porownaj sprzezenie obu wersji
         * (Lesson18).
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_DesignAdrForEventDrivenVsDirectCallChoiceInCapstone {
        /*
         * 🧪 Zadanie 15:
         * Napisz PELNY ADR uzasadniajacy WYBOR komunikacji zdarzeniowej (nie
         * bezposredniego wywolania) miedzy Enrollments a Notifications w tym
         * konkretnym systemie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_AddSecondCacheDecoratorForEnrollmentLookups {
        /*
         * 🧪 Zadanie 16:
         * Dodaj metode `findByStudentEmail` do `EnrollmentRepositoryPort` i
         * dekorator cache'ujacy dla niej (Lesson14) - zademonstruj cache hit
         * przy powtorzonym wyszukiwaniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ImplementFullRollbackSimulationForFailedEnrollment {
        /*
         * 🧪 Zadanie 17:
         * Rozszerz `EnrollStudentUseCase`, by w razie awarii zapisu
         * `Enrollment` (symuluj wyjatek PO rezerwacji miejsca) COFAL
         * rezerwacje miejsca w module Courses (kompensacja, por. Lesson13
         * Zadanie 23) - zademonstruj pelny scenariusz.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_AddSecondControllerForCliStyleEnrollment {
        /*
         * 🧪 Zadanie 18:
         * Dodaj DRUGI adapter driving (Lesson11: "CLI-podobny") dla TEGO
         * SAMEGO `EnrollStudentUseCase` - zademonstruj identyczne dzialanie
         * regul biznesowych z obu adapterow.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_MeasureCouplingBeforeAndAfterIntroducingPublicApi {
        /*
         * 🧪 Zadanie 19:
         * W komentarzu porownaj (min. 3 zdania), jak wygladalby modul
         * Enrollments, GDYBY (zamiast `CoursesModuleApi`) siegal
         * BEZPOSREDNIO do `CourseRepositoryPort` modulu Courses - jakie
         * zasady zostalyby naruszone (Lesson17: wlasnosc danych/API).
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_AuditCapstoneAgainstValidationArchitectureChecklist {
        /*
         * 🧪 Zadanie 20:
         * Uzywajac checklisty z `_17_architecture/Lesson15` (Zadanie 29),
         * przeprowadz PELNY audyt architektury walidacji w kapsztonie - w
         * komentarzu potwierdz zgodnosc z KAZDYM punktem lub wskaz braki.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_ExtendCapstoneWithFullPaymentModule {
        /*
         * 🧪 Zadanie 21:
         * Dodaj kompletny, CZWARTY modul "Payments" (bounded context) z
         * WLASNYM publicznym API, portem/adapterem, subskrypcja zdarzenia
         * `EnrollmentCreated` (pobranie platnosci za kurs) - PUBLIKUJACY
         * WLASNE zdarzenie `PaymentCaptured` po sukcesie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ChainNotificationsToReactToPaymentCapturedEvent {
        /*
         * 🧪 Zadanie 22:
         * Zmien `Notifications`, by wysylal e-mail potwierdzajacy dopiero PO
         * `PaymentCaptured` (Zadanie 21) zamiast po `EnrollmentCreated` -
         * zademonstruj PELNY lancuch zdarzen: Enrollment -> Payment ->
         * Notification.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_ImplementSagaCompensationAcrossThreeModules {
        /*
         * 🧪 Zadanie 23:
         * Dla systemu z Zadania 21-22, zaimplementuj PELNA Sage: jesli
         * `Payments` zawiedzie, publikuje `PaymentFailed`, na ktore
         * `Enrollments` reaguje COFNIECIEM zapisu, a `Courses` ZWALNIA
         * zarezerwowane miejsce - wszystko przez zdarzenia (Lesson13/18).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_RefactorCapstoneToRespectPackageByFeatureLiterally {
        /*
         * 🧪 Zadanie 24:
         * Bez terminala: opisz (jako drzewo tekstowe, Lesson09), jak
         * wygladalby ten kapszton, gdyby KAZDY modul byl PRAWDZIWYM,
         * osobnym pakietem Javy (nie zagniezdzona klasa w 1 pliku) - z
         * `package-private` na wewnetrznych klasach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_DesignMigrationOfCoursesModuleToStandaloneServicePreview {
        /*
         * 🧪 Zadanie 25:
         * Korzystajac z `_17_architecture/Lesson19` (Zadanie 21-22), rozpisz
         * PELNY plan migracji modulu Courses do osobnego mikroserwisu -
         * zaimplementuj adapter sieciowy (symulowany, z timeout/retry)
         * ZASTEPUJACY `CoursesModuleApi` w module Enrollments, zachowujac
         * TEN SAM interfejs.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_BuildFullArchitectureAuditReportForCapstone {
        /*
         * 🧪 Zadanie 26:
         * Napisz "raport audytu architektury" (seria wypisan na konsole)
         * przechodzacy PRZEZ WSZYSTKIE 19 zasad z lekcji 1-19 i
         * potwierdzajacy (lub NIE) zgodnosc kapsztonu z KAZDA z nich -
         * dla KAZDEJ niezgodnosci zaproponuj poprawke.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ImplementSecondBoundedContextWithConflictingTermReusingLesson06 {
        /*
         * 🧪 Zadanie 27:
         * Dodaj modul "Marketing" z WLASNYM pojeciem "Student" (rozne pola
         * niz `Enrollments.Enrollment` - np. preferencje newslettera) -
         * zademonstruj (Lesson06), ze OBA moduly maja WLASNE, niezalezne
         * modele tego samego pojecia biznesowego.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_DesignCompleteAdrLogForEntireCapstoneEvolution {
        /*
         * 🧪 Zadanie 28:
         * Zbuduj `AdrLog` (`_17_architecture/Lesson02`) z MIN. 6 ADR-ami
         * dokumentujacymi WSZYSTKIE wieksze decyzje podjete w tej lekcji
         * (Zadania 9/15/21-25) - wypisz PELNA, chronologiczna historie
         * decyzji architektonicznych calego kapsztonu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_BuildComprehensiveFinalArchitectureReviewChecklist {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz w komentarzu OSTATECZNA, najbardziej
         * kompletna checkliste (min. 15 punktow) laczaca WSZYSTKIE 20 lekcji
         * tego rozdzialu - to Twoj wlasny, gotowy do uzycia "przewodnik
         * architektury" na przyszlosc.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_BuildYourOwnCapstoneFromScratchForDifferentDomain {
        /*
         * 🧪 Zadanie 30:
         * FINALNE zadanie CALEGO ROZDZIALU: zaprojektuj i zaimplementuj OD
         * ZERA WLASNY, kompletny modularny monolit dla INNEJ dziedziny (np.
         * system wypozyczalni sprzetu sportowego, platforma streamingowa,
         * system zarzadzania biblioteka) - swiadomie stosujac WSZYSTKIE 19
         * zasad z tego rozdzialu naraz: min. 3 moduly z bounded context
         * (Lesson06), publiczne API + prywatne wnetrze (Lesson09/17), bogate
         * modele domenowe (Lesson05), DTO/Mapper na granicy (Lesson07),
         * porty/adaptery (Lesson11-12), granice transakcji (Lesson13), cache
         * tam gdzie uzasadnione (Lesson14), 3-poziomowa walidacje
         * (Lesson15), centralna obsluge bledow (Lesson16), oraz komunikacje
         * zdarzeniowa miedzy MIN. 2 modulami (Lesson18). Napisz TOWARZYSZACY
         * ADR (Lesson02) uzasadniajacy wybor modularnego monolitu zamiast
         * mikroserwisow (Lesson19). Zademonstruj PELNY, dzialajacy system.
         */
        public static void main(String[] args) { }
    }
}
