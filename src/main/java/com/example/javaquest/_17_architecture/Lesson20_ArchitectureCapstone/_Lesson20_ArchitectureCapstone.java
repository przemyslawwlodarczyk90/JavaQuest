package com.example.javaquest._17_architecture.Lesson20_ArchitectureCapstone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * KAPSZTON ROZDZIALU: "Platforma Zapisow na Kursy" - modularny monolit
 * laczacy WSZYSTKIE 19 poprzednich lekcji `_17_architecture` w 1 spojnym,
 * dzialajacym systemie. Kazda sekcja kodu jest oznaczona komentarzem
 * wskazujacym, KTOREJ lekcji dotyczy.
 */
public class _Lesson20_ArchitectureCapstone {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 20: PROJEKT KAPSZTONOWY - PLATFORMA ZAPISOW NA KURSY ===");

        /*
         * ============================================================
         * 📦 ARCHITEKTURA CALEGO SYSTEMU (MAPA LEKCJI 1-19)
         * ============================================================
         * - Lesson01 (Why): granice modulow ponizej to SWIADOME decyzje,
         *   kosztowne do zmiany - stad caly ten projekt jest jednym,
         *   udokumentowanym "case study".
         * - Lesson02 (ADR): decyzja "modularny monolit, nie mikroserwisy"
         *   (Lesson19) jest udokumentowana jako komentarz-ADR ponizej.
         * - Lesson03 (Layered) + Lesson09 (Package-by-feature): 2 moduly
         *   (`Courses`, `Enrollments`) - KAZDY z wlasna warstwa
         *   prezentacji/logiki/danych.
         * - Lesson04 (Controller/Service/Repository), Lesson05 (Rich
         *   Domain Model), Lesson06 (Bounded Context), Lesson07 (DTO/
         *   Mapper), Lesson10-12 (Dependency Rule/Hexagonal/Porty),
         *   Lesson13 (Transakcje), Lesson14 (Cache), Lesson15
         *   (Walidacja), Lesson16 (Obsluga bledow), Lesson17 (Modularny
         *   monolit), Lesson18 (Zdarzenia) - WSZYSTKIE ponizej, oznaczone
         *   komentarzami.
         */
        System.out.println(ARCHITECTURE_DECISION_RECORD_SUMMARY); // Lesson02: ADR

        InMemoryEventBus eventBus = new InMemoryEventBus(); // Lesson18: szyna zdarzen

        // Lesson17: modul "Courses" - publiczne API + prywatne wnetrze (symulowane).
        Courses.CourseRepositoryPort realCourseRepository = new Courses.InMemoryCourseRepositoryAdapter();
        Courses.CourseRepositoryPort cachedCourseRepository = new Courses.CachedCourseRepositoryAdapter(realCourseRepository); // Lesson14
        Courses.CoursesModuleApi coursesModule = new Courses.CoursesModuleApi(cachedCourseRepository);
        coursesModule.publishNewCourse("JAVA-101", "Podstawy Javy", 2);

        // Lesson18: modul "Notifications" subskrybuje zdarzenia z modulu Enrollments - BEZ jego wiedzy.
        Notifications.subscribeToEnrollmentEvents(eventBus);

        // Lesson17: modul "Enrollments" - korzysta z Courses WYLACZNIE przez CoursesModuleApi (publiczne API).
        Enrollments.EnrollmentRepositoryPort enrollmentRepository = new Enrollments.InMemoryEnrollmentRepositoryAdapter();
        Enrollments.EnrollStudentUseCase useCase = new Enrollments.EnrollStudentUseCase(coursesModule, enrollmentRepository, eventBus);
        Enrollments.EnrollmentController controller = new Enrollments.EnrollmentController(useCase); // Lesson04/11: adapter driving

        System.out.println("\n=== SCENARIUSZ 1: UDANY ZAPIS ===");
        System.out.println(controller.handleEnrollRequest("student-1@example.com", "JAVA-101"));

        System.out.println("\n=== SCENARIUSZ 2: DRUGI UDANY ZAPIS (WYPELNIA LIMIT 2 MIEJSC) ===");
        System.out.println(controller.handleEnrollRequest("student-2@example.com", "JAVA-101"));

        System.out.println("\n=== SCENARIUSZ 3: BRAK MIEJSC (REGULA BIZNESOWA, LESSON15 POZIOM 3) ===");
        System.out.println(controller.handleEnrollRequest("student-3@example.com", "JAVA-101"));

        System.out.println("\n=== SCENARIUSZ 4: NIEPOPRAWNY FORMAT (LESSON15 POZIOM 1) ===");
        System.out.println(controller.handleEnrollRequest("zly-email", "JAVA-101"));

        System.out.println("\n=== SCENARIUSZ 5: NIEISTNIEJACY KURS (ZNANY WYJATEK DOMENOWY, LESSON16) ===");
        System.out.println(controller.handleEnrollRequest("student-4@example.com", "NIEISTNIEJACY-KURS"));

        System.out.println("\n=== SCENARIUSZ 6: NIEOCZEKIWANY BLAD (LESSON16: BEZPIECZNY FALLBACK) ===");
        System.out.println(controller.handleUnexpectedErrorDemo());

        /*
         * ============================================================
         * 📌 PODSUMOWANIE CALEGO ROZDZIALU
         * ============================================================
         * - 20 lekcji `_17_architecture` to 1 spojny zestaw narzedzi:
         *   od pytania "po co architektura" (Lesson01), przez
         *   dokumentowanie decyzji (Lesson02), warstwy i ich role
         *   (Lesson03-05), granice na poziomie API (Lesson07-08) i kodu
         *   (Lesson06/09-12), przekroje poprzeczne (Lesson13-16), po
         *   organizacje calych systemow (Lesson17-19).
         * - GRATULACJE - to byla OSTATNIA lekcja rozdzialu "Architektura
         *   aplikacji Java"!
         */
        System.out.println("\n=== KONIEC LEKCJI 20 (KONIEC ROZDZIALU _17_architecture) ===");
    }

    private static final String ARCHITECTURE_DECISION_RECORD_SUMMARY = """

            [ADR-0001, Lesson02] Modularny monolit zamiast mikroserwisow (Lesson19)
              Kontekst: maly zespol, brak dowodu na potrzebe niezaleznego skalowania.
              Decyzja: 2 moduly (Courses, Enrollments) w 1 procesie, komunikacja
                       przez publiczne API (Lesson17) i zdarzenia (Lesson18).
              Konsekwencje: prostota operacyjna teraz; granice modulow gotowe
                       do ewentualnej migracji na serwisy w przyszlosci (Lesson19).
            """;

    /** Lesson18: szyna zdarzen w pamieci - moduly publikujace NIE znaja subskrybentow. */
    static class InMemoryEventBus {
        private final Map<Class<?>, List<Consumer<Object>>> subscribers = new HashMap<>();

        @SuppressWarnings("unchecked")
        <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
            subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add((Consumer<Object>) handler);
        }

        void publish(Object event) {
            for (Consumer<Object> handler : subscribers.getOrDefault(event.getClass(), List.of())) {
                handler.accept(event);
            }
        }
    }

    // ================================================================
    // MODUL "COURSES" (Lesson06: bounded context, Lesson17: modul z publicznym API)
    // ================================================================
    static class Courses {

        /** Lesson05: bogata encja - CHRONI wlasny niezmiennik (dostepnosc miejsc). */
        static class Course {
            private final String code;
            private final String title;
            private final int capacity;
            private int enrolledCount = 0;

            Course(String code, String title, int capacity) {
                if (capacity <= 0) {
                    throw new IllegalArgumentException("Pojemnosc kursu musi byc > 0"); // Lesson15 poziom 2: niezmiennik
                }
                this.code = code;
                this.title = title;
                this.capacity = capacity;
            }

            boolean hasAvailableSeat() {
                return enrolledCount < capacity;
            }

            void takeSeat() {
                if (!hasAvailableSeat()) {
                    throw new CourseFullException("Kurs " + code + " nie ma wolnych miejsc (limit " + capacity + ")");
                }
                enrolledCount++;
            }

            String getCode() {
                return code;
            }
        }

        static class CourseFullException extends RuntimeException {
            CourseFullException(String message) {
                super(message);
            }
        }

        /** Lesson11-12: port driven - definiowany WEWNATRZ modulu, implementowany przez adaptery. */
        interface CourseRepositoryPort {
            Course findByCode(String code);

            void save(Course course);
        }

        static class InMemoryCourseRepositoryAdapter implements CourseRepositoryPort {
            private final Map<String, Course> storage = new HashMap<>();

            @Override
            public Course findByCode(String code) {
                Course course = storage.get(code);
                if (course == null) {
                    throw new CourseNotFoundException("Nie znaleziono kursu: " + code);
                }
                return course;
            }

            @Override
            public void save(Course course) {
                storage.put(course.getCode(), course);
            }
        }

        static class CourseNotFoundException extends RuntimeException {
            CourseNotFoundException(String message) {
                super(message);
            }
        }

        /** Lesson14: dekorator cache'ujacy - TEN SAM port co prawdziwy adapter. */
        static class CachedCourseRepositoryAdapter implements CourseRepositoryPort {
            private final CourseRepositoryPort delegate;
            private final Map<String, Course> cache = new HashMap<>();

            CachedCourseRepositoryAdapter(CourseRepositoryPort delegate) {
                this.delegate = delegate;
            }

            @Override
            public Course findByCode(String code) {
                return cache.computeIfAbsent(code, delegate::findByCode);
            }

            @Override
            public void save(Course course) {
                delegate.save(course);
                cache.remove(course.getCode()); // Lesson14: inwalidacja przy zapisie
            }
        }

        /** Lesson17: PUBLICZNE API modulu Courses - JEDYNE, czego modul Enrollments moze uzyc. */
        static class CoursesModuleApi {
            private final CourseRepositoryPort repository;

            CoursesModuleApi(CourseRepositoryPort repository) {
                this.repository = repository;
            }

            void publishNewCourse(String code, String title, int capacity) {
                repository.save(new Course(code, title, capacity));
            }

            /** Lesson15 poziom 3: regula biznesowa wymagajaca stanu systemu (rezerwacja miejsca). */
            void reserveSeat(String courseCode) {
                Course course = repository.findByCode(courseCode);
                course.takeSeat();
                repository.save(course);
            }
        }
    }

    // ================================================================
    // MODUL "ENROLLMENTS" (Lesson06: osobny bounded context)
    // ================================================================
    static class Enrollments {

        /** Lesson07: DTO wejsciowe - kontrakt niezalezny od modelu domenowego. */
        record EnrollRequestDto(String studentEmail, String courseCode) {
        }

        /** Lesson05: bogata encja Enrollments - wlasny, prosty niezmiennik. */
        record Enrollment(String studentEmail, String courseCode) {
            Enrollment {
                if (studentEmail == null || !studentEmail.contains("@")) {
                    throw new IllegalArgumentException("Niepoprawny e-mail studenta: " + studentEmail);
                }
            }
        }

        /** Lesson18: zdarzenie domenowe - fakt, ktory sie JUZ wydarzyl. */
        record EnrollmentCreated(String studentEmail, String courseCode) {
        }

        interface EnrollmentRepositoryPort {
            void save(Enrollment enrollment);
        }

        static class InMemoryEnrollmentRepositoryAdapter implements EnrollmentRepositoryPort {
            private final List<Enrollment> storage = new ArrayList<>();

            @Override
            public void save(Enrollment enrollment) {
                storage.add(enrollment);
            }
        }

        /** Lesson16: spojny ksztalt bledu zwracany na granicy systemu. */
        record ErrorResponse(int status, String code, String message) {
            @Override
            public String toString() {
                return "[" + status + " " + code + "] " + message;
            }
        }

        static class ValidationException extends RuntimeException {
            ValidationException(String message) {
                super(message);
            }
        }

        /** Lesson04/11: Use Case - orkiestruje WSZYSTKIE poziomy walidacji (Lesson15) i granice transakcji (Lesson13). */
        static class EnrollStudentUseCase {
            private final Courses.CoursesModuleApi coursesModule;
            private final EnrollmentRepositoryPort enrollmentRepository;
            private final InMemoryEventBus eventBus;

            EnrollStudentUseCase(Courses.CoursesModuleApi coursesModule, EnrollmentRepositoryPort enrollmentRepository, InMemoryEventBus eventBus) {
                this.coursesModule = coursesModule;
                this.enrollmentRepository = enrollmentRepository;
                this.eventBus = eventBus;
            }

            void enroll(EnrollRequestDto request) {
                // Lesson15 poziom 1: format (tanie, brak I/O) - NAJPIERW.
                if (request.studentEmail() == null || !request.studentEmail().contains("@")) {
                    throw new ValidationException("Niepoprawny format e-maila: " + request.studentEmail());
                }

                // Lesson13: GRANICA TRANSAKCJI = CALA ta metoda (1 przypadek uzycia = 1 transakcja).
                // Lesson15 poziom 3: regula biznesowa (WYMAGA stanu innego modulu przez jego publiczne API).
                coursesModule.reserveSeat(request.courseCode());

                // Lesson15 poziom 2: niezmiennik domenowy egzekwowany w konstruktorze rekordu.
                Enrollment enrollment = new Enrollment(request.studentEmail(), request.courseCode());
                enrollmentRepository.save(enrollment);

                // Lesson18: publikacja zdarzenia PO zakonczeniu WLASNEJ operacji - bez oczekiwania na subskrybentow.
                eventBus.publish(new EnrollmentCreated(enrollment.studentEmail(), enrollment.courseCode()));
            }
        }

        /** Lesson16: centralny tlumacz bledow na granicy - JEDYNE miejsce mapujace wyjatki na ErrorResponse. */
        static class BoundaryErrorHandler {
            ErrorResponse translate(RuntimeException exception) {
                if (exception instanceof ValidationException) {
                    return new ErrorResponse(400, "VALIDATION_ERROR", exception.getMessage());
                }
                if (exception instanceof Courses.CourseFullException) {
                    return new ErrorResponse(409, "COURSE_FULL", exception.getMessage());
                }
                if (exception instanceof Courses.CourseNotFoundException) {
                    return new ErrorResponse(404, "COURSE_NOT_FOUND", exception.getMessage());
                }
                System.out.println("  [LOG WEWNETRZNY] " + exception.getClass().getName() + ": " + exception.getMessage());
                return new ErrorResponse(500, "INTERNAL_ERROR", "Nieoczekiwany blad - ID incydentu: INC-CAP-01");
            }
        }

        /** Lesson04/11: adapter driving - "chudy" kontroler, tlumaczy format wejscia/wyjscia, ZERO logiki biznesowej. */
        static class EnrollmentController {
            private final EnrollStudentUseCase useCase;
            private final BoundaryErrorHandler errorHandler = new BoundaryErrorHandler();

            EnrollmentController(EnrollStudentUseCase useCase) {
                this.useCase = useCase;
            }

            String handleEnrollRequest(String studentEmail, String courseCode) {
                try {
                    useCase.enroll(new EnrollRequestDto(studentEmail, courseCode));
                    return "201 Created - zapisano " + studentEmail + " na " + courseCode;
                } catch (RuntimeException e) {
                    return errorHandler.translate(e).toString();
                }
            }

            /** Demo: symulacja NIEOCZEKIWANEGO bledu (blad programisty), by pokazac bezpieczny fallback z Lesson16. */
            String handleUnexpectedErrorDemo() {
                try {
                    String nullReference = null;
                    nullReference.length(); // celowy NullPointerException - "blad programisty", nie wyjatek domenowy
                    return "nieosiagalne";
                } catch (RuntimeException e) {
                    return errorHandler.translate(e).toString();
                }
            }
        }
    }

    // ================================================================
    // MODUL "NOTIFICATIONS" (Lesson18: subskrybent, BEZ wiedzy Enrollments o jego istnieniu)
    // ================================================================
    static class Notifications {
        static void subscribeToEnrollmentEvents(InMemoryEventBus eventBus) {
            eventBus.subscribe(Enrollments.EnrollmentCreated.class, event ->
                    System.out.println("  [Notifications] Wysylam e-mail powitalny do " + event.studentEmail()
                            + " (kurs: " + event.courseCode() + ")"));
        }
    }
}
