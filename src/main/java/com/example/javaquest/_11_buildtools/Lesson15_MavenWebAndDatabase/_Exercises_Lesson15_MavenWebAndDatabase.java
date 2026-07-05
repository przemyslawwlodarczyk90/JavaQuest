package com.example.javaquest._11_buildtools.Lesson15_MavenWebAndDatabase;

public class _Exercises_Lesson15_MavenWebAndDatabase {

    // 🟢 POZIOM 1 – PODSTAWY (1-10)

    static class Exercise01_CreateWarProject {
        /*
         * 🧪 Zadanie 1:
         * Utwórz nowy projekt Maven z packaging="war" i dodaj zależność
         * jakarta.servlet-api w scope="provided". Uruchom "mvn compile" w terminalu i
         * zapisz w komentarzu, czy kompilacja przechodzi (mimo że servlet-api ma
         * scope=provided, jest dostępny przy kompilacji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise02_ProvidedNotInPackage {
        /*
         * 🧪 Zadanie 2:
         * W projekcie z zadania 1 uruchom "mvn package" i rozpakuj (np. "jar tf") powstały
         * .war z target/. Zapisz w komentarzu, że jakarta.servlet-api NIE znajduje się
         * wewnątrz WEB-INF/lib/ - dowód, że scope=provided nie trafia do artefaktu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise03_SimpleServletInWar {
        /*
         * 🧪 Zadanie 3:
         * Dodaj do projektu z zadania 1 prostą klasę serwletu (rozszerzającą HttpServlet,
         * z adnotacją @WebServlet - wzorzec z rozdziału _07_servlets tego kursu). Uruchom
         * "mvn package" w terminalu i zapisz w komentarzu, że klasa .class znalazła się w
         * WEB-INF/classes/ wewnątrz .war.
         */
        public static void main(String[] args) { }
    }

    static class Exercise04_ProvidedScopeExplanationPrint {
        /*
         * 🧪 Zadanie 4:
         * Bez terminala: wypisz na konsoli (System.out.println) wyjaśnienie, dlaczego
         * jakarta.servlet-api ma scope=provided, z bezpośrednim odniesieniem do
         * embedded Tomcata z rozdziału _07_servlets tego kursu (który sam dostarcza te
         * klasy w runtime).
         */
        public static void main(String[] args) { }
    }

    static class Exercise05_AddH2Dependency {
        /*
         * 🧪 Zadanie 5:
         * Utwórz nowy, prosty projekt Maven i dodaj zależność com.h2database:h2 (wersja
         * 2.3.232 - taka jak w pom.xml tego kursu). Napisz klasę main() łączącą się z bazą
         * H2 in-memory i wykonującą proste zapytanie (wzorzec z _08_sql/Lesson01). Uruchom
         * "mvn compile exec:java" (z exec-maven-plugin z Lesson13) w terminalu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise06_AddHikariCpDependency {
        /*
         * 🧪 Zadanie 6:
         * Dodaj do projektu z zadania 5 zależność com.zaxxer:HikariCP. Uruchom
         * "mvn dependency:tree" w terminalu i zapisz w komentarzu, jakie zależności
         * transytywne HikariCP ściągnęło (jeśli jakieś).
         */
        public static void main(String[] args) { }
    }

    static class Exercise07_RuntimeScopeForDriver {
        /*
         * 🧪 Zadanie 7:
         * Bez terminala: wypisz wyjaśnienie, czemu sterownik JDBC (np. org.postgresql)
         * typowo dostaje scope="runtime", a nie "compile" - że kod aplikacji odwołuje się
         * tylko do interfejsów java.sql.*, nigdy do konkretnych klas sterownika.
         */
        public static void main(String[] args) { }
    }

    static class Exercise08_RootPomAudit {
        /*
         * 🧪 Zadanie 8:
         * Otwórz pom.xml w korzeniu repozytorium tego kursu. Bez terminala wypisz na
         * konsoli 3 zależności bazodanowe, jakie tam znajdziesz (h2, HikariCP,
         * flyway-core), razem z ich rolą (baza / pooling / migracje).
         */
        public static void main(String[] args) { }
    }

    static class Exercise09_FlywayDependencyOnly {
        /*
         * 🧪 Zadanie 9:
         * Utwórz nowy projekt Maven i dodaj TYLKO zależność org.flywaydb:flyway-core (bez
         * pluginu). Uruchom "mvn compile" w terminalu i zapisz w komentarzu, że sama
         * zależność Java (API Flyway) jest dostępna, choć nie masz jeszcze żadnych plików
         * migracji.
         */
        public static void main(String[] args) { }
    }

    static class Exercise10_HibernateForwardLookingSnippet {
        /*
         * 🧪 Zadanie 10:
         * Bez terminala: wypisz na konsoli fragment pom.xml (jako String, tak jak w
         * lekcji) z zależnościami hibernate-core i jakarta.persistence-api - z komentarzem,
         * że to zapowiedź przyszłego rozdziału o Hibernate w tym kursie.
         */
        public static void main(String[] args) { }
    }

    // 🟡 POZIOM 2 – ŚREDNI (11-20)

    static class Exercise11_WarWithServletAndWebXml {
        /*
         * 🧪 Zadanie 11:
         * Zbuduj kompletny projekt war: serwlet (jak w zadaniu 3) + src/main/webapp/
         * WEB-INF/web.xml z mapowaniem serwletu. Uruchom "mvn package" w terminalu.
         * Rozpakuj gotowy .war i zweryfikuj w komentarzu strukturę (WEB-INF/classes,
         * WEB-INF/web.xml).
         */
        public static void main(String[] args) { }
    }

    static class Exercise12_DeployWarToLocalTomcat {
        /*
         * 🧪 Zadanie 12 (opcjonalne, wymaga zainstalowanego Tomcat):
         * Jeśli masz lokalnie zainstalowanego Apache Tomcat, skopiuj .war z zadania 11 do
         * katalogu webapps/, odpal Tomcat i sprawdź w przeglądarce, czy serwlet
         * odpowiada. Zapisz w komentarzu adres URL i wynik (albo informację, że
         * pominąłeś zadanie z braku lokalnego Tomcata).
         */
        public static void main(String[] args) { }
    }

    static class Exercise13_JdbcAppWithHikari {
        /*
         * 🧪 Zadanie 13:
         * Napisz działającą (w terminalu, przez "mvn exec:java") aplikację używającą
         * HikariCP do zarządzania połączeniami do bazy H2 in-memory - skonfiguruj
         * HikariConfig z jdbcUrl, zbuduj HikariDataSource, wykonaj proste zapytanie SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise14_FlywayMigrationFiles {
        /*
         * 🧪 Zadanie 14:
         * Utwórz katalog src/main/resources/db/migration z dwoma plikami migracji
         * (V1__create_users.sql, V2__add_email_column.sql) - wzorzec z _10_dao/Lesson25
         * tego kursu. Dodaj zależność flyway-core i napisz kod main() wywołujący
         * Flyway.configure().dataSource(...).load().migrate(). Uruchom "mvn exec:java" w
         * terminalu.
         */
        public static void main(String[] args) { }
    }

    static class Exercise15_FlywayMavenPluginCommand {
        /*
         * 🧪 Zadanie 15:
         * Skonfiguruj flyway-maven-plugin (jak w lekcji, wskazując URL bazy H2/PostgreSQL).
         * Uruchom "mvn flyway:migrate" w terminalu (bez pisania kodu Javy!) i zapisz w
         * komentarzu output pluginu (liczbę zastosowanych migracji).
         */
        public static void main(String[] args) { }
    }

    static class Exercise16_ProvidedVsCompileServletDemo {
        /*
         * 🧪 Zadanie 16:
         * Zmień scope jakarta.servlet-api z "provided" na "compile" w projekcie z zadania
         * 1. Uruchom "mvn package", rozpakuj .war i zapisz w komentarzu różnicę - teraz
         * servlet-api TRAFIA do WEB-INF/lib/, co w realnym Tomcacie mogłoby powodować
         * konflikt z API dostarczanym przez sam kontener.
         */
        public static void main(String[] args) { }
    }

    static class Exercise17_MultipleDbDriversProfiles {
        /*
         * 🧪 Zadanie 17:
         * Zbuduj projekt z DWOMA profilami Maven ("h2-profile" i "postgres-profile" -
         * podobnie jak w Lesson14), każdy dodający inny sterownik JDBC. Uruchom
         * "mvn dependency:tree -Ph2-profile" i "-Ppostgres-profile" w terminalu, porównaj
         * wyniki.
         */
        public static void main(String[] args) { }
    }

    static class Exercise18_ThisRepoDependencyTreeForDb {
        /*
         * 🧪 Zadanie 18:
         * W KORZENIU repozytorium tego kursu uruchom w terminalu
         * "mvnw.cmd dependency:tree -Dincludes=com.h2database:*,com.zaxxer:*,org.flywaydb:*"
         * (Windows) i zapisz w komentarzu pełny wynik - jakie zależności transytywne
         * przyciągnęły h2/HikariCP/flyway-core w TYM konkretnym projekcie.
         */
        public static void main(String[] args) { }
    }

    static class Exercise19_HibernatePersistenceXmlSketch {
        /*
         * 🧪 Zadanie 19:
         * Bez terminala: napisz (jako String w kodzie, tylko do wypisania na konsolę) szkic
         * pliku persistence.xml (META-INF/persistence.xml) z jednostką persystencji
         * używającą hibernate.dialect i hibernate.show_sql - z komentarzem, że to
         * materiał na przyszły rozdział o Hibernate.
         */
        public static void main(String[] args) { }
    }

    static class Exercise20_LiquibaseVsFlywayComparisonPrint {
        /*
         * 🧪 Zadanie 20:
         * Bez terminala: wypisz tabelę porównawczą (min. 3 kryteria) Flyway vs Liquibase -
         * np. format migracji (SQL vs SQL/XML/YAML/JSON), sposób śledzenia zastosowanych
         * migracji, popularność w ekosystemie Spring Boot.
         */
        public static void main(String[] args) { }
    }

    // 🔴 POZIOM 3 – ZAAWANSOWANY (21-30)

    static class Exercise21_FullWebAppWithJdbcServlet {
        /*
         * 🧪 Zadanie 21:
         * Zbuduj kompletny projekt war z serwletem, który W METODZIE doGet() łączy się z
         * bazą H2 in-memory (uwaga: scope h2 powinien być compile/runtime, NIE provided -
         * to Twoja aplikacja, nie kontener, dostarcza bazę) i wypisuje wynik zapytania w
         * odpowiedzi HTTP. Uruchom "mvn package" w terminalu i zweryfikuj zawartość .war.
         */
        public static void main(String[] args) { }
    }

    static class Exercise22_MultiModuleWebPersistenceDb {
        /*
         * 🧪 Zadanie 22:
         * Rozszerz projekt wielomodułowy z Lesson14 (core/persistence/app) tak, żeby
         * moduł "persistence" miał zależność H2+HikariCP, a moduł "web"/"app" miał
         * packaging=war z jakarta.servlet-api (provided) i zależnością na "persistence".
         * Uruchom "mvn install" w terminalu w katalogu głównym i zapisz w komentarzu wynik.
         */
        public static void main(String[] args) { }
    }

    static class Exercise23_FlywayCallbackAndBaseline {
        /*
         * 🧪 Zadanie 23:
         * W projekcie z migracjami Flyway (jak w zadaniu 14/15) dodaj do konfiguracji
         * pluginu flyway-maven-plugin opcję <baselineOnMigrate>true</baselineOnMigrate>.
         * Uruchom "mvn flyway:baseline" a potem "mvn flyway:migrate" w terminalu - zapisz
         * w komentarzu, do czego służy "baseline" przy istniejącej już bazie danych.
         */
        public static void main(String[] args) { }
    }

    static class Exercise24_HikariPoolTuning {
        /*
         * 🧪 Zadanie 24:
         * W aplikacji z zadania 13 skonfiguruj HikariConfig z minimumIdle, maximumPoolSize
         * i connectionTimeout. Napisz mały test obciążeniowy (wiele wątków proszących o
         * połączenie naraz - wzorzec z rozdziału _05_multithreading tego kursu) i zapisz w
         * komentarzu zaobserwowane zachowanie puli.
         */
        public static void main(String[] args) { }
    }

    static class Exercise25_WarPluginOverlay {
        /*
         * 🧪 Zadanie 25:
         * Bez terminala: wypisz wyjaśnienie mechanizmu "overlay" w maven-war-plugin -
         * możliwości nałożenia jednego projektu war na inny (dependency z type=war),
         * używanego np. do współdzielenia wspólnych zasobów webowych między kilkoma
         * aplikacjami.
         */
        public static void main(String[] args) { }
    }

    static class Exercise26_FullStackDependencyTreeAnalysis {
        /*
         * 🧪 Zadanie 26:
         * Zbuduj projekt z WSZYSTKIMI zależnościami z tej lekcji naraz: servlet-api
         * (provided), h2+HikariCP (compile/runtime), flyway-core, hibernate-core +
         * jakarta.persistence-api. Uruchom "mvn dependency:tree" w terminalu i zapisz w
         * komentarzu, ile zależności transytywnych łącznie ściągnął taki "pełny" stos.
         */
        public static void main(String[] args) { }
    }

    static class Exercise27_ProfileSwitchedFlywayLocations {
        /*
         * 🧪 Zadanie 27:
         * Skonfiguruj flyway-maven-plugin z <locations> różnymi w profilu "dev"
         * (classpath:db/migration/dev) i "prod" (classpath:db/migration/prod). Utwórz
         * oba katalogi z osobnymi plikami migracji. Uruchom "mvn flyway:migrate -Pdev" i
         * "-Pprod" w terminalu, porównaj zastosowane migracje.
         */
        public static void main(String[] args) { }
    }

    static class Exercise28_HibernateVsPlainJdbcComparisonPrint {
        /*
         * 🧪 Zadanie 28:
         * Bez terminala: wypisz porównanie (min. 4 punkty) czystego JDBC (rozdział
         * _09_jdbc tego kursu) vs Hibernate/JPA - ile kodu SQL trzeba pisać ręcznie,
         * automatyczne mapowanie obiektów, krzywa uczenia, kontrola nad wygenerowanym SQL.
         */
        public static void main(String[] args) { }
    }

    static class Exercise29_MigrationRollbackStrategy {
        /*
         * 🧪 Zadanie 29:
         * Bez terminala: wypisz wyjaśnienie, dlaczego Flyway (w wersji darmowej/Community)
         * NIE wspiera automatycznego "rollbacku" migracji (w przeciwieństwie do Liquibase,
         * które ma <rollback> w changesecie) - i jaka jest typowa strategia radzenia sobie
         * z tym w praktyce (migracja "naprawcza" do przodu, nie w tył).
         */
        public static void main(String[] args) { }
    }

    static class Exercise30_FullStackWebDbCapstone {
        /*
         * 🧪 Zadanie 30:
         * Podsumowujące zadanie na koniec rozdziału o Mavenie: zbuduj kompletny projekt
         * war z: serwletem korzystającym z HikariCP+H2, migracjami Flyway odpalanymi
         * automatycznie przy starcie aplikacji (kod w kontekście serwletu/listenera - patrz
         * rozdział _07_servlets), i zależnością servlet-api w scope=provided. Uruchom
         * "mvn clean package" w terminalu. Napisz krótki raport tekstowy (w komentarzu w
         * kodzie) podsumowujący WSZYSTKIE scope'y użyte w projekcie i ich uzasadnienie.
         */
        public static void main(String[] args) { }
    }
}
