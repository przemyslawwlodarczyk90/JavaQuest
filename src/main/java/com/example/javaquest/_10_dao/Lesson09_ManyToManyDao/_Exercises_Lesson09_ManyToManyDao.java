package com.example.javaquest._10_dao.Lesson09_ManyToManyDao;

public class _Exercises_Lesson09_ManyToManyDao {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateManyToManySchema {
        /*
         * 🧪 Zadanie 1:
         * Na bazie "jdbc:h2:mem:l09ex01;DB_CLOSE_DELAY=-1" utwórz tabele students
         * (id, name), courses (id, title) i tabelę pośrednią students_courses
         * (student_id, course_id, PRIMARY KEY(student_id, course_id)). Wstaw 1
         * studenta i 2 kursy zwykłym Statement, bez żadnego powiązania jeszcze.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddAssociationRow {
        /*
         * 🧪 Zadanie 2:
         * Napisz StudentCourseDao.enroll(Long studentId, Long courseId) - INSERT
         * do students_courses. Zapisz powiązanie studenta z jednym kursem i
         * zweryfikuj przez SELECT COUNT(*) na tabeli pośredniej (powinno być 1).
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_RemoveAssociationRow {
        /*
         * 🧪 Zadanie 3:
         * Napisz unenroll(Long studentId, Long courseId) - DELETE z
         * students_courses. Zapisz powiązanie, usuń je i sprawdź, że sam student
         * i sam kurs WCIĄŻ istnieją w swoich tabelach (SELECT COUNT(*) na obu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_FindCoursesForStudent {
        /*
         * 🧪 Zadanie 4:
         * Napisz findCoursesForStudent(Long studentId) - JOIN przez tabelę
         * pośrednią, zwracający List<String> tytułów kursów. Zapisz studenta na
         * 2 kursy i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_FindStudentsForCourse {
        /*
         * 🧪 Zadanie 5:
         * Napisz "odwrotną" metodę findStudentsForCourse(Long courseId) - JOIN w
         * DRUGĄ stronę relacji, zwracający List<String> imion studentów. Zapisz
         * 3 studentów na jeden kurs i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_StudentWithNoCourses {
        /*
         * 🧪 Zadanie 6:
         * Wstaw studenta BEZ żadnego powiązania z kursem. Wywołaj
         * findCoursesForStudent dla niego i sprawdź, że wynik to PUSTA lista (nie
         * null, bez wyjątku).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_DuplicateAssociationRejected {
        /*
         * 🧪 Zadanie 7:
         * Zapisz powiązanie student-kurs, a następnie spróbuj zapisać TE SAME id
         * jeszcze raz (ten sam student_id + course_id) - dzięki PRIMARY KEY
         * (student_id, course_id) baza rzuci SQLException. Złap go i wypisz
         * czytelny komunikat "student jest juz zapisany na ten kurs".
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_CountAssociationsForStudent {
        /*
         * 🧪 Zadanie 8:
         * Napisz countCoursesForStudent(Long studentId) - SELECT COUNT(*) FROM
         * students_courses WHERE student_id = ?. Zapisz studenta na 3 kursy i
         * sprawdź, że wynik zgadza się z findCoursesForStudent(studentId).size().
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ProductCategoryVariant {
        /*
         * 🧪 Zadanie 9:
         * Odtwórz na nowej bazie schemat products/categories/products_categories
         * z lekcji (inne dane niż w przykładzie: 3 produkty, 2 kategorie). Powiąż
         * każdy produkt z co najmniej jedną kategorią i wypisz kategorie dla
         * każdego produktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_JunctionTableHasNoOwnData {
        /*
         * 🧪 Zadanie 10:
         * Sprawdź strukturę tabeli students_courses - w komentarzu wyjaśnij,
         * czemu NIE ma sensu dodawać do niej kolumny np. "grade" na tym etapie
         * (bo tabela pośrednia z lekcji przechowuje TYLKO powiązania). W main
         * zademonstruj, że tabela ma DOKŁADNIE dwie kolumny (klucze obce) plus
         * złożony PRIMARY KEY.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_BulkEnrollMultipleCourses {
        /*
         * 🧪 Zadanie 11:
         * Napisz enrollInMultipleCourses(Long studentId, List<Long> courseIds)
         * wywołującą enroll() dla każdego id z listy. Zapisz jednego studenta na
         * 4 kursy jednym wywołaniem i zweryfikuj przez findCoursesForStudent.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_ReplaceAllAssociationsForStudent {
        /*
         * 🧪 Zadanie 12:
         * Napisz replaceCoursesForStudent(Long studentId, List<Long> newCourseIds)
         * - najpierw DELETE FROM students_courses WHERE student_id = ? (usuwa
         * WSZYSTKIE stare powiązania), potem INSERT nowych. Przetestuj: student
         * zapisany na 2 kursy, potem "zamiana" na inne 3 kursy, weryfikacja przez
         * findCoursesForStudent.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CountStudentsPerCourseReport {
        /*
         * 🧪 Zadanie 13:
         * Napisz countStudentsPerCourse() - GROUP BY po tabeli pośredniej JOIN
         * courses, zwracającą Map<String, Long> (tytuł kursu -> liczba
         * studentów). Zapisz 5 studentów na różne kombinacje 3 kursów i wypisz
         * raport.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FindStudentsNotEnrolledInAnyCourse {
        /*
         * 🧪 Zadanie 14:
         * Napisz findStudentsWithoutCourses() - SELECT ze students, których id
         * NIE występuje w students_courses (LEFT JOIN + WHERE course_id IS NULL,
         * albo NOT IN). Zapisz 4 studentów, zapisz 2 na kursy, wypisz pozostałych
         * 2 bez żadnego kursu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FindCommonCoursesBetweenTwoStudents {
        /*
         * 🧪 Zadanie 15:
         * Napisz findCommonCourses(Long studentId1, Long studentId2) zwracającą
         * kursy, na które zapisani są OBAJ studenci (JOIN tabeli pośredniej z
         * samą sobą albo dwa zapytania + Set.retainAll w Javie). Przetestuj na 2
         * studentach z częściowo pokrywającymi się kursami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_RemoveAllAssociationsForCourse {
        /*
         * 🧪 Zadanie 16:
         * Napisz removeAllStudentsFromCourse(Long courseId) - DELETE FROM
         * students_courses WHERE course_id = ? (np. przy odwołaniu kursu). Zapisz
         * 3 studentów na kurs, usuń wszystkie powiązania z tym kursem i sprawdź,
         * że findStudentsForCourse zwraca pustą listę, a sam kurs wciąż istnieje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_ManyToManyWithAdditionalFilterOnOtherSide {
        /*
         * 🧪 Zadanie 17:
         * Dodaj do courses kolumnę credits INT. Napisz findCoursesForStudentWithMinCredits
         * (studentId, int minCredits) - JOIN przez tabelę pośrednią + WHERE
         * credits >= ?. Zapisz studenta na 3 kursy o różnej liczbie punktów i
         * przetestuj filtr.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ToggleAssociationExistsOrNot {
        /*
         * 🧪 Zadanie 18:
         * Napisz toggleEnrollment(Long studentId, Long courseId) - jeśli
         * powiązanie już istnieje (osobne existsEnrollment sprawdzenie), usuń je
         * (unenroll); jeśli nie istnieje, dodaj je (enroll). Wywołaj dwukrotnie
         * dla tej samej pary id i pokaż, że stan się "przełącza".
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ManyToManyRoleUserPattern {
        /*
         * 🧪 Zadanie 19:
         * Odtwórz wzorzec User<->Role (tabele users, roles, users_roles). Napisz
         * assignRole(userId, roleId) i findRolesForUser(userId). Przypisz
         * jednemu użytkownikowi role "ADMIN" i "EDITOR" i wypisz jego role.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_ManyToManyCascadeAwareDeleteOfMainEntity {
        /*
         * 🧪 Zadanie 20:
         * Zbadaj zachowanie DELETE FROM students WHERE id = ? dla studenta
         * MAJĄCEGO powiązania w students_courses (klucz obcy w tabeli pośredniej
         * wskazujący na students). Złap SQLException (naruszenie klucza obcego)
         * i pokaż PRAWIDŁOWĄ kolejność: najpierw usuń powiązania studenta
         * (removeAllCoursesForStudent), potem samego studenta.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullEnrollmentServiceWithBusinessRules {
        /*
         * 🧪 Zadanie 21:
         * Napisz EnrollmentService.enrollStudentInCourse(studentId, courseId) z
         * DWIEMA regułami biznesowymi PRZED wywołaniem DAO: student i kurs muszą
         * istnieć (osobne existsById na obu DAO) oraz student nie może być już
         * zapisany na ten kurs (sprawdzenie przez tabelę pośrednią). Przetestuj
         * na 4 przypadkach naruszających różne reguły.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ManyToManyMatrixReport {
        /*
         * 🧪 Zadanie 22:
         * Dla 4 studentów i 3 kursów z częściowymi powiązaniami, wygeneruj
         * "matrycę" tekstową (wiersze = studenci, kolumny = kursy, "X" gdy
         * zapisany, "-" gdy nie) na podstawie danych z tabeli pośredniej
         * pobranych JEDNYM zapytaniem (bez N zapytań per student).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_TransactionalBulkEnrollmentWithRollback {
        /*
         * 🧪 Zadanie 23:
         * W JEDNEJ transakcji (setAutoCommit(false)) zapisz studenta na LISTĘ 5
         * kursów, gdzie jeden z course_id jest nieprawidłowy (nieistniejący,
         * naruszy REFERENCES). Złap wyjątek, wywołaj rollback() i sprawdź, że
         * ŻADNE z 5 powiązań nie zostało zapisane (all-or-nothing).
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_ManyToManyWithExtraAttributeOnJunctionTable {
        /*
         * 🧪 Zadanie 24:
         * Dodaj do students_courses kolumnę enrollment_date DATE. Zaktualizuj
         * enroll(), by przyjmowała i zapisywała datę (LocalDate). Napisz
         * findCoursesForStudentSince(studentId, LocalDate from) filtrującą po tej
         * dacie. Przetestuj na kursach zapisanych w różnych terminach.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_RecommendCoursesBasedOnSharedEnrollments {
        /*
         * 🧪 Zadanie 25:
         * Napisz recommendCoursesFor(Long studentId) - znajdź studentów
         * podobnych (zapisanych na przynajmniej jeden wspólny kurs), a następnie
         * zwróć kursy, na które są zapisani TAMCI studenci, ale NIE nasz student
         * (typowa logika "inni studenci też wybrali..."). Przetestuj na scenariuszu
         * z 4 studentami i 4 kursami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_SynchronizeAssociationsToMatchGivenSet {
        /*
         * 🧪 Zadanie 26:
         * Napisz syncCoursesForStudent(Long studentId, Set<Long> desiredCourseIds)
         * porównującą OBECNE powiązania z "pożądanym" zbiorem i wykonującą TYLKO
         * różnicę: usuwa powiązania, których NIE MA w desiredCourseIds, dodaje
         * te, których jeszcze NIE MA w bazie (bez usuwania i odtwarzania
         * wszystkiego na nowo, w odróżnieniu od Zadania 12). Przetestuj na
         * scenariuszu z częściowym pokryciem.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_PerformanceOfJunctionQueryWithManyRows {
        /*
         * 🧪 Zadanie 27:
         * Wstaw 100 studentów i 20 kursów, z losowymi (ale deterministycznymi,
         * np. modulo) powiązaniami - łącznie kilkaset wierszy w tabeli pośredniej.
         * Zmierz (System.nanoTime()) czas wywołania countStudentsPerCourse()
         * (Zadanie 13) na tak dużym zbiorze i wypisz wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_ManyToManySelfReferencingFriendship {
        /*
         * 🧪 Zadanie 28:
         * Tabela users (id, name) i tabela pośrednia friendships (user_id,
         * friend_id) - relacja wiele-do-wielu DO SIEBIE SAMEJ (znajomości).
         * Napisz addFriendship(userId, friendId) wstawiającą powiązanie W OBIE
         * STRONY (dwa INSERT-y: (A,B) i (B,A)) oraz findFriendsOf(userId).
         * Przetestuj na 3 użytkownikach z różnymi znajomościami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_AuditLogForJunctionTableChanges {
        /*
         * 🧪 Zadanie 29:
         * Dodaj tabelę enrollment_audit (id, student_id, course_id, action). Za
         * każdym wywołaniem enroll()/unenroll() wstaw osobny wpis do audytu
         * ("ENROLL"/"UNENROLL"). Wykonaj sekwencję 5 operacji na tabeli pośredniej
         * i wypisz cały audyt na końcu, porównując go z finalnym stanem
         * students_courses.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullManyToManyMiniEnrollmentSystem {
        /*
         * 🧪 Zadanie 30:
         * Złóż mini-system zapisów na kursy: StudentDao, CourseDao,
         * StudentCourseDao (enroll, unenroll, findCoursesForStudent,
         * findStudentsForCourse, countStudentsPerCourse). EnrollmentService z
         * regułami biznesowymi (limit max 3 kursów na studenta, brak duplikatów).
         * Zasymuluj 6 studentów zapisujących się na różne kombinacje 4 kursów
         * (część prób odrzucona przez limit) i wypisz raport końcowy: pełną
         * matrycę zapisów + liczbę odrzuconych prób z podziałem na powód.
         */
        public static void main(String[] args) { }
    }
}
