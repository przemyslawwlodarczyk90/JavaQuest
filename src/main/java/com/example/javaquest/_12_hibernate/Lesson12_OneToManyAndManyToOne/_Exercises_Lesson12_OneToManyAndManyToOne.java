package com.example.javaquest._12_hibernate.Lesson12_OneToManyAndManyToOne;

public class _Exercises_Lesson12_OneToManyAndManyToOne {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_BasicOneToManyBidirectional {
        /*
         * 🧪 Zadanie 1:
         * Utworz Department (id, name, @OneToMany(mappedBy="department") List<Employee>)
         * i Employee (id, name, @ManyToOne @JoinColumn department) na H2
         * ("jdbc:h2:mem:l12ex01;DB_CLOSE_DELAY=-1"). Zapisz dzial z 2 pracownikami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_AddEmployeeHelperMethod {
        /*
         * 🧪 Zadanie 2:
         * Dodaj do Department metode addEmployee(Employee) ustawiajaca OBIE strony
         * relacji (jak w lekcji). Uzyj jej zamiast recznego setDepartment().
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_ForgottenOtherSidePitfall {
        /*
         * 🧪 Zadanie 3:
         * Dodaj Employee TYLKO do listy department.getEmployees().add(emp) BEZ
         * wywolania emp.setDepartment(department). Zapisz i sprawdz w NOWEJ Session,
         * ze employee.getDepartment() jest null (dowod pulapki z lekcji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ManyToOneWithoutCollectionSide {
        /*
         * 🧪 Zadanie 4:
         * Zbuduj TYLKO stronie @ManyToOne (Employee -> Department), BEZ kolekcji po
         * stronie Department (unidirectional). Zapisz 3 pracownikow tego samego dzialu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_QueryChildrenByParentId {
        /*
         * 🧪 Zadanie 5:
         * Uzyj HQL (podglad przed Lesson18) "from Employee e where e.department.id =
         * :deptId" zeby znalezc wszystkich pracownikow konkretnego dzialu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_EmptyCollectionNotNull {
        /*
         * 🧪 Zadanie 6:
         * Zapisz Department BEZ zadnego pracownika. Odczytaj go i zweryfikuj, ze
         * getEmployees() zwraca PUSTA liste (nie null).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RemoveEmployeeHelperMethod {
        /*
         * 🧪 Zadanie 7:
         * Dodaj do Department metode removeEmployee(Employee) (jak w lekcji).
         * Usun jednego z 2 pracownikow i zweryfikuj w NOWEJ Session, ze pozostal
         * TYLKO jeden.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_OrphanRemovalDeletesFromDatabase {
        /*
         * 🧪 Zadanie 8:
         * Z orphanRemoval=true: wywolaj removeEmployee() i zweryfikuj recznym SQL
         * (nie tylko przez Hibernate), ze wiersz pracownika ZOSTAL FIZYCZNIE usuniety
         * z tabeli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_ManyToOneJoinColumnCustomName {
        /*
         * 🧪 Zadanie 9:
         * Ustaw @JoinColumn(name = "dept_fk") (inna nazwa niz domyslna). Sprawdz
         * recznym SQL, ze kolumna w tabeli employee nazywa sie dokladnie tak.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_ChangeEmployeeDepartment {
        /*
         * 🧪 Zadanie 10:
         * Przenies pracownika z jednego Department do INNEGO (uzywajac
         * removeEmployee na starym i addEmployee na nowym). Zweryfikuj w NOWEJ
         * Session, ze pracownik nalezy TERAZ do nowego dzialu.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_LazyLoadingCollectionDemo {
        /*
         * 🧪 Zadanie 11:
         * Zapisz Department z 3 pracownikami. Odczytaj go w NOWEJ Session i wywolaj
         * getEmployees().size() DOPIERO PO wypisaniu "przed dostepem" - zaobserwuj w
         * show_sql, ze zapytanie o pracownikow wykonuje sie DOPIERO przy tym dostepie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_LazyInitializationExceptionOutsideSession {
        /*
         * 🧪 Zadanie 12:
         * Odczytaj Department, ZAMKNIJ Session, a POTEM sprobuj wywolac
         * getEmployees().size() na juz zamknietej Session. Zapisz w komentarzu
         * wyjatek LazyInitializationException.
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_CascadePersistNewEmployees {
        /*
         * 🧪 Zadanie 13:
         * Z cascade = CascadeType.PERSIST: zapisz Department z 3 NOWYMI (jeszcze
         * niezapisanymi) pracownikami JEDNYM session.persist(department) - bez
         * recznego persist() na kazdym pracowniku.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_CascadeRemoveDeletesAllChildren {
        /*
         * 🧪 Zadanie 14:
         * Z cascade = CascadeType.REMOVE: usun Department z 3 pracownikami. Zweryfikuj
         * recznym SQL, ze WSZYSCY 3 pracownicy TEZ zostali usunieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_ThreeLevelHierarchy {
        /*
         * 🧪 Zadanie 15:
         * Zbuduj TRZYPOZIOMOWA hierarchie: Company -> Department -> Employee
         * (dwa zagniezdzone @OneToMany/@ManyToOne). Zapisz pelna strukture i
         * odczytaj wszystkich pracownikow WSZYSTKICH dzialow jednej firmy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_SetInsteadOfListForCollection {
        /*
         * 🧪 Zadanie 16:
         * Przebuduj Department.employees na Set<Employee> (zamiast List). Zapisz w
         * komentarzu, dlaczego Set moze byc lepszym wyborem dla kolekcji @OneToMany
         * (unikanie duplikatow, brak gwarancji kolejnosci).
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_OrderByOnCollection {
        /*
         * 🧪 Zadanie 17:
         * Dodaj @OrderBy("name ASC") na kolekcji employees. Zapisz pracownikow w
         * losowej kolejnosci i zweryfikuj, ze przy odczycie sa POSORTOWANI po imieniu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_CountChildrenWithoutLoadingAll {
        /*
         * 🧪 Zadanie 18:
         * Napisz zapytanie HQL "select count(e) from Employee e where e.department.id
         * = :deptId" zwracajace TYLKO liczbe pracownikow (Long), BEZ ladowania
         * calej kolekcji encji do pamieci.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_ManyToOneNullableFalseRequired {
        /*
         * 🧪 Zadanie 19:
         * Ustaw @JoinColumn(nullable = false) na Employee.department. Sprobuj
         * zapisac pracownika BEZ przypisanego dzialu i zapisz w komentarzu blad.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_BulkReassignAllEmployeesToNewDepartment {
        /*
         * 🧪 Zadanie 20:
         * Napisz metode przenoszaca WSZYSTKICH pracownikow z jednego Department do
         * drugiego (petla po kopii listy + removeEmployee/addEmployee dla kazdego) -
         * zweryfikuj koncowy stan obu dzialow.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullCompanyDepartmentEmployeeSystem {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj KOMPLETNY system z Zadania 15 (Company -> Department -> Employee) z
         * metodami pomocniczymi na KAZDYM poziomie (addDepartment/removeDepartment,
         * addEmployee/removeEmployee) i orphanRemoval na obu poziomach hierarchii.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_ReportAggregatingAcrossHierarchy {
        /*
         * 🧪 Zadanie 22:
         * Napisz raport HQL (podglad przed Lesson19) zliczajacy liczbe pracownikow W
         * KAZDYM dziale KAZDEJ firmy naraz (group by na dwoch poziomach zagniezdzenia).
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_PreventOrphanedEmployeeAtApiLevel {
        /*
         * 🧪 Zadanie 23:
         * Zaprojektuj Employee tak, aby konstruktor WYMAGAL Department jako
         * parametru (nie ma sposobu utworzyc Employee bez dzialu na poziomie API
         * Javy) - dodatkowa warstwa ochrony NAD nullable=false z bazy.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_PerformanceNPlusOneWithoutFetch {
        /*
         * 🧪 Zadanie 24:
         * Zapisz 20 dzialow po 5 pracownikow. Odczytaj WSZYSTKIE dzialy i w petli
         * wywolaj getEmployees().size() dla kazdego (N+1, jak w Lesson15) - policz w
         * show_sql dokladna liczbe wykonanych zapytan SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_ConcurrentModificationOfSameParentCollection {
        /*
         * 🧪 Zadanie 25:
         * Uruchom DWA watki (jak w _05_multithreading), kazdy w OSOBNEJ Session
         * dodajacy INNEGO pracownika do TEGO SAMEGO Department jednoczesnie -
         * zweryfikuj, ze OBAJ pracownicy koncowo naleza do dzialu (bez utraty zapisu).
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_MigrateUnidirectionalToBidirectional {
        /*
         * 🧪 Zadanie 26:
         * Zacznij od relacji unidirectional (tylko @ManyToOne, Zadanie 4), a potem
         * "zrefaktoryzuj" ja na bidirectional (dodaj kolekcje @OneToMany po stronie
         * rodzica) - napisz w komentarzu, co dokladnie trzeba dodac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_BusinessRuleMaxEmployeesPerDepartment {
        /*
         * 🧪 Zadanie 27:
         * Dodaj do metody addEmployee() WALIDACJE biznesowa: dzial moze miec
         * MAKSYMALNIE 10 pracownikow - rzuc wyjatek przy probie przekroczenia limitu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_TransferEmployeeAtomically {
        /*
         * 🧪 Zadanie 28:
         * Zaimplementuj "atomowy" transfer pracownika miedzy dzialami W JEDNEJ
         * transakcji (Lesson08) - jesli walidacja limitu (Zadanie 27) w NOWYM dziale
         * zawiedzie, transfer NIE powinien czesciowo sie wykonac.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_FullReportOnBidirectionalConsistencyRisks {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: napisz raport tekstowy (min. 5 zdan, w komentarzu)
         * podsumowujacy WLASNYMI slowami, jakie ryzyka niesie relacja dwukierunkowa
         * BEZ metod pomocniczych - z odniesieniem do konkretnego bledu, ktory
         * napotkales w cwiczeniach tej lekcji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullOneToManyCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujace zadanie: zbuduj KOMPLETNY system Company->Department->Employee
         * (Zadanie 21) z: walidacja limitu (Zadanie 27), atomowym transferem
         * (Zadanie 28), raportem agregujacym (Zadanie 22) i orphanRemoval na obu
         * poziomach - zademonstruj pelny scenariusz reorganizacji firmy.
         */
        public static void main(String[] args) { }
    }
}
