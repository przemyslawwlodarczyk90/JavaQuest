package com.example.javaquest._12_hibernate.Lesson13_ManyToManyAssociation;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class _Lesson13_ManyToManyAssociation {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: RELACJA WIELE-DO-WIELU: @ManyToMany ===");

        /*
         * ============================================================
         * 📦 TABELA POŚREDNICZĄCA (JOIN TABLE) - @JoinTable
         * ============================================================
         * SQL nie ma natywnego pojęcia "wiele-do-wielu" - modeluje się
         * je przez TRZECIĄ, pomocniczą tabelę z dwoma kluczami obcymi
         * (po jednym do każdej z powiązanych tabel). @ManyToMany +
         * @JoinTable pozwala Hibernate wygenerować i zarządzać tą
         * tabelą AUTOMATYCZNIE - nie tworzysz dla niej osobnej encji.
         */
        System.out.println("\n=== @JoinTable ===");
        System.out.println("Automatycznie generowana tabela posrednicza z 2 kluczami obcymi - bez osobnej encji.");

        /*
         * ============================================================
         * 🔹 PROBLEM: BRAK MIEJSCA NA DODATKOWE ATRYBUTY RELACJI
         * ============================================================
         * Czysty @ManyToMany działa świetnie, DOPÓKI relacja nie
         * potrzebuje WŁASNYCH danych - np. "kiedy student zapisał się
         * na kurs" albo "jaką ocenę dostał z tego konkretnego kursu".
         * Automatycznie generowana tabela pośrednicząca ma TYLKO dwie
         * kolumny FK - nie ma gdzie dodać "enrollment_date" czy "grade".
         */
        System.out.println("\n=== PROBLEM: brak miejsca na atrybuty relacji ===");
        System.out.println("Automatyczna tabela posrednicza ma TYLKO 2 kolumny FK - nie ma gdzie dodac np. daty zapisu.");

        /*
         * ============================================================
         * 🔍 ROZWIĄZANIE: JAWNA ENCJA POŚREDNICZĄCA
         * ============================================================
         * Rekomendowane w praktyce podejście: zamiast @ManyToMany,
         * tworzysz JAWNĄ encję (np. Enrollment) reprezentującą SAM
         * fakt powiązania - z DWOMA polami @ManyToOne (do Student i do
         * Course) ORAZ dodatkowymi polami WŁASNYMI tej relacji
         * (enrollmentDate, grade). To w praktyce ROZBIJA jedno
         * @ManyToMany na DWA @OneToMany/@ManyToOne (Lesson12) - i daje
         * pełną elastyczność.
         */
        System.out.println("\n=== ROZWIAZANIE: jawna encja posrednicza (np. Enrollment) ===");
        System.out.println("Rozbicie @ManyToMany na dwa @ManyToOne (do Student i Course) + wlasne pola relacji.");
        System.out.println("REKOMENDOWANE w praktyce, gdy relacja potrzebuje choc jednego dodatkowego atrybutu.");

        /*
         * ============================================================
         * 🔹 STRONA WŁAŚCICIELSKA @ManyToMany - UNIKANIE DUPLIKATÓW
         * ============================================================
         * W relacji dwukierunkowej @ManyToMany JEDNA strona musi być
         * "właścicielska" (deklaruje @JoinTable), a druga używa
         * mappedBy - DOKŁADNIE jak w @OneToMany/@ManyToOne (Lesson12).
         * Jeśli OBIE strony zadeklarowałyby własne @JoinTable, Hibernate
         * stworzyłby DWIE OSOBNE tabele pośredniczące dla TEJ SAMEJ
         * relacji - duplikacja danych. Używanie Set (zamiast List) po
         * obu stronach dodatkowo pomaga uniknąć przypadkowych duplikatów
         * par (student, course) w pamięci.
         */
        System.out.println("\n=== STRONA WLASCICIELSKA @ManyToMany ===");
        System.out.println("TYLKO jedna strona deklaruje @JoinTable, druga uzywa mappedBy - inaczej DWIE tabele posredniczace!");

        /*
         * ============================================================
         * 🔹 PEŁNY PRZYKŁAD: Student I Course PRZEZ @ManyToMany
         * ============================================================
         */
        SessionFactory sessionFactory = buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Course javaCourse = new Course();
            javaCourse.setName("Programowanie w Javie");
            session.persist(javaCourse);

            Course sql = new Course();
            sql.setName("Bazy danych SQL");
            session.persist(sql);

            Student alice = new Student();
            alice.setName("Alicja Nowak");
            alice.getCourses().add(javaCourse);
            alice.getCourses().add(sql);
            session.persist(alice);

            transaction.commit();
            session.close();

            Session readSession = sessionFactory.openSession();
            Student loadedStudent = readSession.find(Student.class, alice.getId());
            System.out.println("\n=== ODCZYT Student + kursy (@ManyToMany) ===");
            System.out.println(loadedStudent.getName() + " jest zapisana na " + loadedStudent.getCourses().size() + " kursy:");
            loadedStudent.getCourses().forEach(c -> System.out.println(" - " + c.getName()));
            readSession.close();
        }

        /*
         * ============================================================
         * 🔍 REFAKTORYZACJA: PRZEJŚCIE NA Enrollment (Z DATĄ ZAPISU)
         * ============================================================
         * Poniżej ta sama relacja Student-Course, ale przez JAWNĄ
         * encję Enrollment z dodatkowym polem "enrollmentDate" - czego
         * czysty @ManyToMany powyżej NIE był w stanie wyrazić.
         */
        SessionFactory enrollmentFactory = buildEnrollmentSessionFactory();
        try (enrollmentFactory) {
            Session session = enrollmentFactory.openSession();
            Transaction transaction = session.beginTransaction();

            StudentV2 bob = new StudentV2();
            bob.setName("Bartek Zielinski");
            session.persist(bob);

            CourseV2 python = new CourseV2();
            python.setName("Podstawy Pythona");
            session.persist(python);

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(bob);
            enrollment.setCourse(python);
            enrollment.setEnrollmentDate(LocalDate.of(2026, 2, 1));
            session.persist(enrollment);

            transaction.commit();
            session.close();

            System.out.println("\n=== PO REFAKTORYZACJI: jawna encja Enrollment ===");
            System.out.println(bob.getName() + " zapisany na '" + python.getName() + "' dnia " + enrollment.getEnrollmentDate());
        }

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - @ManyToMany + @JoinTable - automatyczna tabela pośrednicząca,
         *   proste, dopóki relacja nie potrzebuje WŁASNYCH atrybutów.
         * - Gdy relacja potrzebuje dodatkowych pól (data, ocena) -
         *   rozbij na jawną encję pośredniczącą (np. Enrollment) z
         *   dwoma @ManyToOne - podejście REKOMENDOWANE w praktyce.
         * - TYLKO jedna strona @ManyToMany deklaruje @JoinTable (strona
         *   właścicielska), druga używa mappedBy.
         */

        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson13manytomany;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Course.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static SessionFactory buildEnrollmentSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:lesson13enrollment;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(StudentV2.class);
        configuration.addAnnotatedClass(CourseV2.class);
        configuration.addAnnotatedClass(Enrollment.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Entity(name = "Student")
    @Table(name = "student")
    public static class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "student_course",
                joinColumns = @JoinColumn(name = "student_id"),
                inverseJoinColumns = @JoinColumn(name = "course_id")
        )
        private Set<Course> courses = new HashSet<>();

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<Course> getCourses() {
            return courses;
        }
    }

    @Entity(name = "Course")
    @Table(name = "course")
    public static class Course {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity(name = "StudentV2")
    @Table(name = "student_v2")
    public static class StudentV2 {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @OneToMany(mappedBy = "student")
        private List<Enrollment> enrollments = new ArrayList<>();

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity(name = "CourseV2")
    @Table(name = "course_v2")
    public static class CourseV2 {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Entity(name = "Enrollment")
    @Table(name = "enrollment")
    public static class Enrollment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "student_id")
        private StudentV2 student;

        @ManyToOne
        @JoinColumn(name = "course_id")
        private CourseV2 course;

        private LocalDate enrollmentDate;

        public Long getId() {
            return id;
        }

        public void setStudent(StudentV2 student) {
            this.student = student;
        }

        public void setCourse(CourseV2 course) {
            this.course = course;
        }

        public LocalDate getEnrollmentDate() {
            return enrollmentDate;
        }

        public void setEnrollmentDate(LocalDate enrollmentDate) {
            this.enrollmentDate = enrollmentDate;
        }
    }
}
