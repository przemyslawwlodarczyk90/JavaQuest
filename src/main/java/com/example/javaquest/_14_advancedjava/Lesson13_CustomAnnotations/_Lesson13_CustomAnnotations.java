package com.example.javaquest._14_advancedjava.Lesson13_CustomAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class _Lesson13_CustomAnnotations {

    public static void main(String[] args) {

        System.out.println("=== LEKCJA 13: WŁASNE ADNOTACJE (@interface) ===\n");

        /*
         * ============================================================
         * 📦 DEFINIOWANIE WŁASNEJ ADNOTACJI: słowo kluczowe @interface
         * ============================================================
         * Własną adnotację definiuje się przez "@interface NazwaAdnotacji",
         * a jej ELEMENTY (odpowiedniki "atrybutów") deklaruje się jako
         * metody BEZ CIAŁA, opcjonalnie z wartością domyślną przez "default":
         *
         *   public @interface Todo {
         *       String value();
         *       String author() default "unknown";
         *   }
         *
         * Reguły elementów adnotacji:
         *  - typ zwracany może być: prymitywem, Stringiem, Class, enumem,
         *    inną adnotacją, albo TABLICĄ jednego z powyższych - NIC
         *    WIĘCEJ (żadnych dowolnych obiektów jak List<String>),
         *  - element o nazwie "value" jest SZCZEGÓLNY - pozwala na
         *    SINGLE-VALUE ANNOTATION (@Todo("napraw bug") zamiast
         *    @Todo(value = "napraw bug")), pod warunkiem że jest to
         *    JEDYNY element bez wartości domyślnej,
         *  - element bez "default" jest OBOWIĄZKOWY przy każdym użyciu
         *    adnotacji.
         *
         * Poniżej zdefiniowana jest adnotacja @Todo (na końcu pliku, jako
         * zagnieżdżony @interface) - użyjmy jej:
         */
        printTodos(TaskExample.class);

        /*
         * ============================================================
         * 🔹 4 KLASYCZNE META-ADNOTACJE
         * ============================================================
         * Meta-adnotacja to adnotacja UŻYWANA NA INNEJ ADNOTACJI - opisuje
         * JAK dana adnotacja ma się zachowywać. Cztery klasyczne (z pakietu
         * java.lang.annotation) to: @Target, @Retention, @Documented,
         * @Inherited. Omówimy je po kolei.
         */

        /*
         * ------------------------------------------------------------
         * 🔹 @Target - GDZIE wolno użyć adnotacji
         * ------------------------------------------------------------
         * @Target(ElementType[]) ogranicza, na jakich RODZAJACH elementów
         * kodu adnotacja jest dozwolona. Bez @Target adnotacja jest
         * dozwolona WSZĘDZIE. Najważniejsze wartości ElementType:
         *  - TYPE            - klasa, interfejs, enum, rekord, adnotacja,
         *  - METHOD           - metoda,
         *  - FIELD            - pole,
         *  - PARAMETER        - parametr metody/konstruktora,
         *  - CONSTRUCTOR      - konstruktor,
         *  - LOCAL_VARIABLE   - zmienna lokalna,
         *  - ANNOTATION_TYPE  - inna adnotacja (dla meta-adnotacji),
         *  - TYPE_USE         - DOWOLNE miejsce użycia typu (od Javy 8;
         *                        np. wewnątrz generyków: List<@NonNull String>).
         *
         * Nasza adnotacja @Range (zdefiniowana niżej w pliku) ma:
         *   @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
         * - wolno jej użyć na polu, metodzie i parametrze, ale NIE np. na
         * całej klasie - próba taka byłaby błędem KOMPILACJI.
         */
        System.out.println("\n--- @Target: @Range dozwolony na FIELD/METHOD/PARAMETER ---");
        RangedBox box = new RangedBox(75);
        System.out.println("RangedBox.value = " + box.getValue());

        /*
         * ------------------------------------------------------------
         * 🔹 @Retention - JAK DŁUGO adnotacja "żyje"
         * ------------------------------------------------------------
         * @Retention(RetentionPolicy) określa, na którym etapie cyklu
         * życia programu adnotacja jest DOSTĘPNA. To jest TYLKO PODGLĄD -
         * pełną głębię (SOURCE vs CLASS vs RUNTIME, realne demo refleksji
         * i przetwarzania w czasie kompilacji) poznamy w następnej,
         * 14 lekcji. Tutaj wystarczy wiedzieć, że istnieją TRZY wartości:
         *  - RetentionPolicy.SOURCE  - adnotacja znika już przy kompilacji
         *                               (np. @Override, @SuppressWarnings),
         *  - RetentionPolicy.CLASS   - domyślna, jeśli @Retention pominięto;
         *                               trafia do pliku .class, ale JVM jej
         *                               NIE ładuje w runtime,
         *  - RetentionPolicy.RUNTIME - dostępna w runtime przez REFLEKSJĘ
         *                               (tak działają Lombok, MapStruct,
         *                               Hibernate @Entity, JUnit @Test -
         *                               wszystkie znane z wcześniejszych
         *                               rozdziałów kursu).
         *
         * Nasza @Todo i @Range mają @Retention(RetentionPolicy.RUNTIME) -
         * właśnie dlatego printTodos() mógł je odczytać przez refleksję
         * (getAnnotation(...)) chwilę wcześniej.
         */
        System.out.println("\n--- @Retention: podgląd - pełny temat w Lekcji 14 ---");

        /*
         * ------------------------------------------------------------
         * 🔹 @Documented - czy adnotacja trafia do Javadoc
         * ------------------------------------------------------------
         * @Documented to MARKER meta-adnotacja - jeśli nią oznaczymy
         * naszą adnotację, to gdy wygenerujemy Javadoc dla elementu z tą
         * adnotacją, SAMA ADNOTACJA (i jej wartości) pojawi się w
         * wygenerowanej dokumentacji HTML. Bez @Documented adnotacja
         * jest "niewidoczna" w Javadoc, mimo że nadal działa normalnie
         * w kodzie/refleksji. Czysto kosmetyczna sprawa dla czytelników
         * dokumentacji - nie wpływa na działanie programu.
         */
        System.out.println("--- @Documented: @Todo ma tę meta-adnotację - pojawi się w wygenerowanym Javadoc ---");

        /*
         * ------------------------------------------------------------
         * 🔹 @Inherited - czy podklasy DZIEDZICZĄ adnotację z klasy bazowej
         * ------------------------------------------------------------
         * @Inherited działa TYLKO dla adnotacji na KLASACH (nie na
         * metodach/polach!) - jeśli klasa bazowa ma adnotację oznaczoną
         * @Inherited, to podklasa, która jej NIE oznaczy WŁASNĄ kopią tej
         * adnotacji, i tak "zobaczy" ją przy odpytaniu przez refleksję
         * (getAnnotation() szuka też w hierarchii nadklas). Bez @Inherited
         * adnotacje na klasach NIE są dziedziczone.
         */
        System.out.println("\n--- @Inherited demo (dwie klasy: bazowa oznaczona, podklasa NIE) ---");
        AuditableEntity annotationFromBase = BaseEntity.class.getAnnotation(AuditableEntity.class);
        AuditableEntity annotationFromSub = SubEntity.class.getAnnotation(AuditableEntity.class);
        System.out.println("BaseEntity.class.getAnnotation(AuditableEntity.class) = " + annotationFromBase);
        System.out.println("SubEntity.class.getAnnotation(AuditableEntity.class)  = " + annotationFromSub
                + "  (SubEntity NIE ma własnego @AuditableEntity, ale DZIEDZICZY je z BaseEntity dzięki @Inherited)");

        /*
         * ============================================================
         * 🔍 @Repeatable (Java 8+) - ta sama adnotacja WIELE razy
         * ============================================================
         * Domyślnie adnotacji NIE wolno powtórzyć dwa razy na tym samym
         * elemencie - to błąd kompilacji. @Repeatable pozwala to
         * obejść, ale wymaga DODATKOWEJ adnotacji-KONTENERA, która
         * "opakowuje" tablicę powtórzeń. Wzorzec:
         *
         *   @Repeatable(Schedules.class)
         *   public @interface Schedule {
         *       String day();
         *   }
         *
         *   public @interface Schedules {
         *       Schedule[] value();
         *   }
         *
         * Kompilator automatycznie "zwija" wielokrotne @Schedule w JEDNĄ
         * @Schedules przy odczycie przez refleksję - my w kodzie źródłowym
         * wciąż piszemy po prostu kilka razy @Schedule(...), NIGDY
         * bezpośrednio @Schedules(...) (choć technicznie też by zadziałało).
         */
        System.out.println("\n--- @Repeatable: @Schedule powtórzony 3 razy na jednej metodzie ---");
        Schedule[] schedules = MeetingPlanner.getWeeklySyncSchedules(); // pomocnicza metoda niżej, patrz komentarz przy niej
        for (Schedule schedule : schedules) {
            System.out.println("  spotkanie w dzień: " + schedule.day() + ", godz. " + schedule.hour());
        }

        /*
         * ============================================================
         * 🔍 ZAPOWIEDŹ: Lekcje 15/16 (Reflection) odczytają te adnotacje
         * ============================================================
         * W tej lekcji odczytywaliśmy adnotacje przez refleksję TYLKO
         * "przy okazji" (getAnnotation, getAnnotationsByType), żeby
         * pokazać, że w ogóle DZIAŁAJĄ. W Lekcji 15_ReflectionBasics i
         * 16_ReflectionUseCasesAndRisks poznamy dokładnie MECHANIZM
         * refleksji (Class, Method, Field, Constructor) - a w Lekcji 14,
         * zaraz po tej, zobaczymy DOKŁADNIE różnicę między trzema
         * poziomami @Retention (SOURCE/CLASS/RUNTIME) oraz PRAWDZIWE
         * przetwarzanie adnotacji W CZASIE KOMPILACJI (annotation
         * processing) - dokładnie ten mechanizm, na którym opiera się
         * MapStruct (rozdział _13_libraries) czy Lombok.
         */

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Własną adnotację definiuje się przez "@interface Nazwa", z
         *   elementami jako metodami bez ciała (opcjonalnie z "default").
         *   Element "value" pozwala na skróconą, single-value składnię.
         * - @Target(ElementType...) - GDZIE wolno użyć adnotacji (TYPE,
         *   METHOD, FIELD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,
         *   TYPE_USE, ...) - próba użycia poza dozwolonym miejscem to
         *   błąd kompilacji.
         * - @Retention(RetentionPolicy) - SOURCE (znika po kompilacji),
         *   CLASS (domyślna, w .class, ale nie w runtime), RUNTIME
         *   (dostępna przez refleksję) - pełny temat w Lekcji 14.
         * - @Documented - adnotacja pojawia się w wygenerowanym Javadoc.
         * - @Inherited - działa TYLKO dla adnotacji na klasach; podklasa
         *   "dziedziczy" adnotację nadklasy przy odpytaniu przez refleksję,
         *   nawet bez własnej kopii.
         * - @Repeatable(Container.class) - pozwala powtórzyć tę samą
         *   adnotację wiele razy na jednym elemencie; wymaga osobnej
         *   adnotacji-kontenera z elementem Adnotacja[] value().
         * - Dalej: Lekcja 14 pogłębia @Retention i pokazuje realne
         *   przetwarzanie adnotacji w czasie kompilacji, a Lekcje 15-16
         *   uczą mechanizmu refleksji od podstaw.
         */

        System.out.println("\n=== KONIEC LEKCJI 13 ===");
    }

    /** Odczytuje i wypisuje adnotacje @Todo z każdej metody podanej klasy (przez refleksję). */
    private static void printTodos(Class<?> type) {
        System.out.println("--- Zadania @Todo znalezione w " + type.getSimpleName() + " ---");
        for (java.lang.reflect.Method method : type.getDeclaredMethods()) {
            Todo todo = method.getAnnotation(Todo.class);
            if (todo != null) {
                System.out.println("  [" + method.getName() + "] " + todo.value() + " (autor: " + todo.author() + ")");
            }
        }
    }

    /** Klasa z przykładowymi metodami oznaczonymi własną adnotacją @Todo. */
    static class TaskExample {

        @Todo("dodać walidację wejścia")
        public void parse(String input) {
            // ...
        }

        @Todo(value = "obsłużyć wyjątek sieciowy", author = "Ola")
        public void fetch() {
            // ...
        }

        public void alreadyDone() {
            // brak @Todo - ta metoda jest gotowa
        }
    }

    /** Klasa demonstrująca użycie @Range (SINGLE-VALUE by nie zadziałało - dwa obowiązkowe elementy). */
    static class RangedBox {

        @Range(min = 0, max = 100)
        private final int value;

        RangedBox(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /** Klasa bazowa oznaczona @AuditableEntity (@Inherited) - patrz demo w main(). */
    @AuditableEntity(system = "javaQuest-demo")
    static class BaseEntity {
    }

    /** Podklasa BEZ własnej @AuditableEntity - mimo to "dziedziczy" ją dzięki @Inherited. */
    static class SubEntity extends BaseEntity {
    }

    /** Klasa z METODĄ, na której @Schedule powtórzone jest TRZY razy (@Repeatable). */
    static class MeetingPlanner {

        @Schedule(day = "poniedziałek", hour = "10:00")
        @Schedule(day = "środa", hour = "14:00")
        @Schedule(day = "piątek", hour = "09:00")
        public void weeklySync() {
            // ...
        }

        /**
         * Pomocnicza metoda "programowa" zwracająca powtórzone @Schedule z
         * weeklySync() - refleksyjne getAnnotationsByType(Schedule.class)
         * automatycznie "rozpakowuje" kontener @Schedules z powrotem w
         * tablicę Schedule[], niezależnie od tego, czy adnotacja została
         * powtórzona raz czy wiele razy.
         */
        static Schedule[] getWeeklySyncSchedules() {
            try {
                java.lang.reflect.Method method = MeetingPlanner.class.getDeclaredMethod("weeklySync");
                return method.getAnnotationsByType(Schedule.class);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    // ================================================================
    // WŁASNE ADNOTACJE (zdefiniowane w tym pliku, jako zagnieżdżone @interface)
    // ================================================================

    /** Adnotacja-znacznik zadania do zrobienia - element "value" (opis) + "author" (domyślnie "unknown"). */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Todo {
        String value();

        String author() default "unknown";
    }

    /** Adnotacja opisująca dozwolony zakres wartości liczbowej - dwa OBOWIĄZKOWE elementy (bez "default"). */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @interface Range {
        int min();

        int max();
    }

    /** Adnotacja na poziomie klasy, dziedziczona przez podklasy dzięki @Inherited. */
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface AuditableEntity {
        String system();
    }

    /** Adnotacja-kontener dla powtórzeń @Schedule - wymagana przez mechanizm @Repeatable. */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Schedules {
        Schedule[] value();
    }

    /** Adnotacja powtarzalna - jedno spotkanie cykliczne; @Repeatable wskazuje kontener Schedules. */
    @Repeatable(Schedules.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Schedule {
        String day();

        String hour() default "12:00";
    }
}
