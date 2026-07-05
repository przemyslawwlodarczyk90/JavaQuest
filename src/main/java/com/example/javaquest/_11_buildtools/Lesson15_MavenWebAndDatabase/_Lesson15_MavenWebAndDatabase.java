package com.example.javaquest._11_buildtools.Lesson15_MavenWebAndDatabase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class _Lesson15_MavenWebAndDatabase {

    public static void main(String[] args) throws IOException {

        System.out.println("=== LEKCJA 15: MAVEN - PROJEKTY WEBOWE I BAZODANOWE ===");

        Path tempDir = Files.createTempDirectory("javaquest-lesson15-maven");

        /*
         * ============================================================
         * 📦 MAVEN I PROJEKT SERVLETOWY (packaging=war)
         * ============================================================
         * Aplikacja webowa oparta na Servlet API (patrz rozdział
         * _07_servlets tego kursu) w Mavenie ma packaging="war" - Maven
         * pakuje ją do pliku .war (Web Application Archive), gotowego
         * do wdrożenia na kontenerze servletów (Tomcat, Jetty).
         *
         * Kluczowa zależność: jakarta.servlet-api ze scope="provided".
         * DLACZEGO provided, a nie compile? Bo servlet API jest
         * DOSTARCZANE przez sam KONTENER w runtime - Tomcat/Jetty MAJĄ
         * już te klasy na swoim classpath, kiedy odpalają Twoją
         * aplikację. Jeśli spakujesz servlet-api DO SWOJEGO war-a (scope
         * compile), miałbyś DWIE kopie tych samych klas w classpath w
         * runtime (Twoją + kontenera) - potencjalny konflikt.
         *
         * To DOKŁADNIE ten sam servlet API (jakarta.servlet.*), którego
         * używaliśmy w rozdziale _07_servlets - tam jednak NIE
         * deklarowaliśmy go w pom.xml jako osobnej zależności, bo
         * przychodzi TRANSYTYWNIE przez spring-boot-starter-web (razem z
         * tomcat-embed-core - patrz Lesson12 o zależnościach przechodnich).
         * Tutaj, w SAMODZIELNYM projekcie war (bez Spring Boota), musisz
         * go zadeklarować JAWNIE i ręcznie ustawić scope=provided.
         */

        String servletPom = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0">
                    <modelVersion>4.0.0</modelVersion>

                    <groupId>com.example</groupId>
                    <artifactId>servlet-webapp-demo</artifactId>
                    <version>1.0.0</version>
                    <packaging>war</packaging>

                    <properties>
                        <maven.compiler.release>21</maven.compiler.release>
                    </properties>

                    <dependencies>
                        <!-- provided: kontener (Tomcat/Jetty) dostarczy te klasy sam w runtime -
                             nie pakujemy ich do wlasnego war-a -->
                        <dependency>
                            <groupId>jakarta.servlet</groupId>
                            <artifactId>jakarta.servlet-api</artifactId>
                            <version>6.0.0</version>
                            <scope>provided</scope>
                        </dependency>
                    </dependencies>

                    <build>
                        <plugins>
                            <plugin>
                                <groupId>org.apache.maven.plugins</groupId>
                                <artifactId>maven-war-plugin</artifactId>
                                <configuration>
                                    <failOnMissingWebXml>false</failOnMissingWebXml>
                                </configuration>
                            </plugin>
                        </plugins>
                    </build>
                </project>
                """;

        Path servletPomPath = tempDir.resolve("servlet-pom.xml");
        Files.writeString(servletPomPath, servletPom);

        System.out.println("\n=== 1) MAVEN + SERVLET PROJECT (packaging=war) ===");
        System.out.println("Sciezka: " + servletPomPath);
        System.out.println(servletPom);
        System.out.println("Powiazanie z _07_servlets: TEN SAM jakarta.servlet-api, ktorego uzywalismy tam");
        System.out.println("(przychodzil TRANSYTYWNIE przez spring-boot-starter-web) - tu deklarujemy go");
        System.out.println("JAWNIE, bo to samodzielny projekt war, bez Spring Boota.");

        /*
         * ============================================================
         * 🔹 MAVEN I JDBC - STEROWNIKI BAZ DANYCH + POOLING
         * ============================================================
         * Typowa aplikacja bazodanowa potrzebuje:
         * - sterownika JDBC konkretnej bazy (PostgreSQL, MySQL, H2...) -
         *   zwykle scope="runtime", bo kod aplikacji odwołuje się tylko
         *   do standardowych interfejsów java.sql.* (Connection,
         *   Statement...), NIE do klas samego sterownika - sterownik
         *   jest ładowany dynamicznie przez JDBC (patrz rozdział
         *   _09_jdbc tego kursu).
         * - puli połączeń (connection pool), np. HikariCP - zarządza
         *   gotowymi, wielokrotnie używanymi połączeniami, żeby nie
         *   otwierać nowego połączenia TCP przy każdym zapytaniu.
         *
         * 💡 Ten kurs SAM UŻYWA dokładnie tych zależności do rozdziałów
         * _08_sql/_09_jdbc/_10_dao - zobacz pom.xml w korzeniu repo:
         *   com.h2database:h2            (sterownik + silnik bazy H2)
         *   com.zaxxer:HikariCP          (pula połączeń)
         *   org.flywaydb:flyway-core     (migracje - patrz niżej)
         * H2 akurat pełni TU rolę zarówno "sterownika", jak i całej
         * bazy (bo działa in-memory, w tej samej JVM - patrz
         * _08_sql/Lesson01_DatabaseIntroduction).
         */

        String jdbcPom = """
                <dependencies>
                    <!-- Sterownik PostgreSQL - scope runtime: kod uzywa tylko java.sql.*,
                         sterownik jest ladowany dynamicznie przez JDBC -->
                    <dependency>
                        <groupId>org.postgresql</groupId>
                        <artifactId>postgresql</artifactId>
                        <version>42.7.3</version>
                        <scope>runtime</scope>
                    </dependency>

                    <!-- HikariCP - pula polaczen (connection pool) -->
                    <dependency>
                        <groupId>com.zaxxer</groupId>
                        <artifactId>HikariCP</artifactId>
                        <version>5.1.0</version>
                    </dependency>

                    <!-- H2 - uzywane W TYM KURSIE (zobacz pom.xml w korzeniu repo) jako
                         baza in-memory dla rozdzialow _08_sql / _09_jdbc / _10_dao -->
                    <dependency>
                        <groupId>com.h2database</groupId>
                        <artifactId>h2</artifactId>
                        <version>2.3.232</version>
                    </dependency>
                </dependencies>
                """;

        Path jdbcPomPath = tempDir.resolve("jdbc-snippet.xml");
        Files.writeString(jdbcPomPath, jdbcPom);

        System.out.println("\n=== 2) MAVEN + JDBC (sterownik + HikariCP) ===");
        System.out.println("Sciezka: " + jdbcPomPath);
        System.out.println(jdbcPom);
        System.out.println("Ten kurs SAM uzywa h2 + HikariCP + flyway-core (zobacz pom.xml w korzeniu repo) -");
        System.out.println("dokladnie do rozdzialow _08_sql, _09_jdbc, _10_dao.");

        /*
         * ============================================================
         * 🔍 MAVEN I HIBERNATE (ORM) - ZAJRZYJ W PRZOD
         * ============================================================
         * Hibernate to biblioteka ORM (Object-Relational Mapping) -
         * mapuje obiekty Java na wiersze tabel SQL automatycznie,
         * eliminując ręczne pisanie SQL dla podstawowych operacji CRUD
         * (to zobaczysz w przyszłym rozdziale o Hibernate - ten kurs
         * jeszcze go nie ma, ale zależności wyglądałyby tak):
         *
         *   hibernate-core           -> silnik ORM
         *   jakarta.persistence-api  -> standardowe API JPA (adnotacje
         *                               @Entity, @Id, @Column...),
         *                               Hibernate jest jedną z
         *                               implementacji tego standardu
         *   + sterownik bazy danych (np. postgresql, jak wyżej)
         *   + odpowiednia konfiguracja logowania SQL (np.
         *     <property name="hibernate.show_sql">true</property> w
         *     persistence.xml/application.properties)
         */

        String hibernatePom = """
                <dependencies>
                    <!-- Silnik ORM Hibernate -->
                    <dependency>
                        <groupId>org.hibernate.orm</groupId>
                        <artifactId>hibernate-core</artifactId>
                        <version>6.5.2.Final</version>
                    </dependency>

                    <!-- Standardowe API JPA (adnotacje @Entity, @Id...) -
                         Hibernate to jedna z implementacji tego standardu -->
                    <dependency>
                        <groupId>jakarta.persistence</groupId>
                        <artifactId>jakarta.persistence-api</artifactId>
                        <version>3.1.0</version>
                    </dependency>

                    <!-- + sterownik bazy danych, np. postgresql (jak w sekcji JDBC wyzej) -->
                </dependencies>
                """;

        Path hibernatePomPath = tempDir.resolve("hibernate-snippet.xml");
        Files.writeString(hibernatePomPath, hibernatePom);

        System.out.println("\n=== 3) MAVEN + HIBERNATE (zajrzyj w przod - przyszly rozdzial) ===");
        System.out.println("Sciezka: " + hibernatePomPath);
        System.out.println(hibernatePom);
        System.out.println("Hibernate + JPA to zobaczysz w PRZYSZLYM rozdziale o Hibernate -");
        System.out.println("ten kurs go jeszcze nie ma, to tylko zapowiedz zaleznosci.");

        /*
         * ============================================================
         * 🔹 MAVEN I MIGRACJE BAZY DANYCH (Flyway / Liquibase)
         * ============================================================
         * Migracje wersjonują SCHEMAT bazy danych podobnie jak Git
         * wersjonuje kod - każda zmiana schematu to osobny, numerowany
         * skrypt SQL (np. V1__create_table.sql), a narzędzie migracyjne
         * pilnuje, które migracje już zostały zastosowane (tabela
         * "flyway_schema_history" w przypadku Flyway).
         *
         * Ten kurs UŻYWA flyway-core w rozdziale _10_dao/Lesson25 -
         * zobacz pom.xml w korzeniu repo. Alternatywą jest Liquibase
         * (podobna idea, ale migracje mogą być też w XML/YAML/JSON, nie
         * tylko SQL).
         *
         * Oba narzędzia mają też PLUGINY MAVEN pozwalające odpalać
         * migracje bezpośrednio z linii komend, bez pisania kodu Javy:
         *   mvn flyway:migrate
         *   mvn liquibase:update
         */

        String migrationsPom = """
                <dependencies>
                    <!-- Flyway - uzywane w TYM kursie, rozdzial _10_dao/Lesson25 -->
                    <dependency>
                        <groupId>org.flywaydb</groupId>
                        <artifactId>flyway-core</artifactId>
                        <version>10.14.0</version>
                    </dependency>
                </dependencies>

                <build>
                    <plugins>
                        <!-- Plugin Flyway - pozwala odpalac migracje z linii komend: mvn flyway:migrate -->
                        <plugin>
                            <groupId>org.flywaydb</groupId>
                            <artifactId>flyway-maven-plugin</artifactId>
                            <version>10.14.0</version>
                            <configuration>
                                <url>jdbc:postgresql://localhost:5432/app</url>
                                <user>app_user</user>
                                <locations>
                                    <location>classpath:db/migration</location>
                                </locations>
                            </configuration>
                        </plugin>
                    </plugins>
                </build>
                """;

        Path migrationsPomPath = tempDir.resolve("migrations-snippet.xml");
        Files.writeString(migrationsPomPath, migrationsPom);

        System.out.println("\n=== 4) MAVEN + MIGRACJE (Flyway/Liquibase) ===");
        System.out.println("Sciezka: " + migrationsPomPath);
        System.out.println(migrationsPom);
        System.out.println("Flyway jest juz uzywany w tym kursie: rozdzial _10_dao/Lesson25_DatabaseMigrations.");
        System.out.println("Liquibase to popularna alternatywa (migracje moga byc SQL/XML/YAML/JSON).");

        /*
         * ============================================================
         * 📌 PODSUMOWANIE
         * ============================================================
         * - Projekt servletowy: packaging=war + jakarta.servlet-api ze
         *   scope=provided (kontener - Tomcat/Jetty - dostarcza te
         *   klasy sam w runtime; patrz rozdzial _07_servlets, gdzie
         *   ten sam servlet API przychodzil TRANSYTYWNIE przez
         *   spring-boot-starter-web).
         * - JDBC: sterownik bazy (scope zwykle runtime - kod uzywa
         *   tylko java.sql.*) + pula polaczen (HikariCP). Ten kurs
         *   uzywa dokladnie h2 + HikariCP (zobacz pom.xml w korzeniu
         *   repo, rozdzialy _08_sql/_09_jdbc/_10_dao).
         * - Hibernate (ORM): hibernate-core + jakarta.persistence-api +
         *   sterownik bazy - zapowiedz PRZYSZLEGO rozdzialu o Hibernate,
         *   ktorego ten kurs jeszcze nie ma.
         * - Migracje (Flyway/Liquibase): wersjonuja SCHEMAT bazy jak Git
         *   wersjonuje kod. Ten kurs uzywa flyway-core w _10_dao/Lesson25.
         *   Oba narzedzia maja pluginy Maven do odpalania migracji z
         *   linii komend (mvn flyway:migrate / mvn liquibase:update).
         */

        Files.deleteIfExists(servletPomPath);
        Files.deleteIfExists(jdbcPomPath);
        Files.deleteIfExists(hibernatePomPath);
        Files.deleteIfExists(migrationsPomPath);
        Files.deleteIfExists(tempDir);

        System.out.println("\n=== KONIEC LEKCJI 15 ===");
    }
}
