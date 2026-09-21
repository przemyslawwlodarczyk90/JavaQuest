package com.example.javaquest.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Prawdziwy, dzialajacy punkt wejscia aplikacji JavaQuest - serwuje frontend React
 * zbudowany w katalogu "frontend/" (ktorego "npm run build" laduje wynik do
 * src/main/resources/static) oraz REST API platformy edukacyjnej
 * ({@code com.example.javaquest.platform.*}). Patrz EDU_PLATFORM_PLAN.md.
 *
 * <p>UMIESZCZONA CELOWO w podpakiecie "com.example.javaquest.web", NIE w pakiecie
 * najwyzszego poziomu "com.example.javaquest" - domyslny component-scan/auto-
 * konfiguracja {@code @SpringBootApplication} obejmuje TYLKO pakiet klasy, w ktorej
 * jest zadeklarowana, i jego podpakiety. Gdyby ta klasa zyla w "com.example.javaquest"
 * (jak w oryginalnym szablonie Spring Initializr), skanowalaby WSZYSTKIE 31 rozdzialow
 * kursu naraz - a te sa PELNE wlasnych, izolowanych klas @Configuration/@Component/
 * @Entity zaprojektowanych do dzialania KAZDA W WLASNYM, ODDZIELNYM kontekscie Springa
 * (uruchamianym z main() danej lekcji), nie w jednym wspolnym kontekscie. Zweryfikowane
 * empirycznie: proba uruchomienia z pakietu najwyzszego poziomu konczyla sie
 * natychmiastowym BeanDefinitionOverrideException.
 *
 * <p>Domena platformy ({@code com.example.javaquest.platform.*}) zyje jako pakiet
 * SIOSTRZANY do "web" (nie podpakiet), zeby "web" zostal czysto infrastrukturalny
 * (bootstrap Springa) a "platform" czysto domenowy - dlatego component-scan/entity-scan/
 * repository-scan sa tutaj JAWNIE rozszerzone o oba pakiety zamiast polegac na
 * domyslnym zachowaniu (ktore objelby TYLKO "web").
 *
 * <p><b>Pulapka znaleziona i naprawiona przy pisaniu Fazy 1</b>: bez jawnego
 * {@code exclude = R2dbcAutoConfiguration.class}, {@code entityManagerFactory} NIE
 * powstawal WCALE (blad "required a bean named 'entityManagerFactory' that could not
 * be found", bez zadnej wzmianki o DataSource w logu) - `spring-boot-starter-data-r2dbc`
 * (dodany dla `_29_spring_reactive/Lesson13_R2dbcIntro`) auto-konfiguruje globalny bean
 * `ConnectionFactory` (R2DBC), a `DataSourceAutoConfiguration` ma
 * `@ConditionalOnMissingBean(ConnectionFactory.class)` - CELOWO rezygnuje z tworzenia
 * zwyklego JDBC `DataSource`, gdy istnieje juz reaktywny `ConnectionFactory` (zalozenie
 * Boota: "albo JDBC, albo R2DBC", nie oba naraz automatycznie). Zdiagnozowane przez
 * `--debug` (raport warunkow auto-konfiguracji) - jawny `spring.datasource.url` NIC nie
 * dawal, bo `DataSourceAutoConfiguration` w ogole sie nie uruchamial, niezaleznie od
 * jakichkolwiek wlasciwosci datasource. Wykluczenie przez ATRYBUT adnotacji (nie przez
 * `spring.autoconfigure.exclude` we `.properties(...)`) jest tu KONIECZNE, bo globalny
 * `application.properties` JUZ ustawia ten klucz (Security/Rabbit/ActiveMQ) - `.properties(...)`
 * ma nizszy priorytet i zostalby CALKOWICIE nadpisany, nie zmergowany (ta sama zasada co
 * przy `_24_spring_security`). Adnotacyjny `exclude` DZIALA ADDYTYWNIE wzgledem
 * property-based excludes, wiec nie koliduje z globalna lista.
 *
 * <p>{@code ArtemisAutoConfiguration} wykluczony z tego samego powodu, ale mniej
 * krytycznego: `spring-boot-starter-artemis` (z `_30_spring_messaging_and_async/Lesson07`)
 * probowal automatycznie odpalic embedded broker JMS przy KAZDYM starcie tej appki -
 * niepotrzebne (platforma nie uzywa JMS) i konczylo sie widocznym w logu (ale
 * NIEFATALNYM) bledem `AMQ224000: Failure in initialisation /
 * UnsupportedOperationException: getSubject is not supported` (niezgodnosc Artemis-owego
 * kodu natywnego z JAAS `Subject` API na nowszych JDK). Wykluczenie usuwa ten szum i
 * skraca start o kilka sekund.
 *
 * <p><b>Domyslny port 8082, NIE 8080</b>: na tej konkretnej maszynie deweloperskiej port
 * 8080 jest TRWALE zajety przez systemowy proces "AgentService" (prawdopodobnie
 * agent bezpieczenstwa/antywirus, PID staly miedzy restartami, nie da sie go zabic -
 * proba `taskkill` konczy sie "Odmowa dostepu") - zweryfikowane empirycznie zarowno
 * PRZED pierwszym uruchomieniem tej appki (Faza 0), jak i wielokrotnie pozniej.
 *
 * <p><b>Dlaczego port jest ustawiany przez {@code System.setProperty}, NIE przez
 * {@code .properties(...)} ponizej ani przez globalny {@code application.properties}</b>:
 * pierwsza proba (`server.port=8081` w `.properties(...)`) PO CICHU nie zadzialala -
 * appka i tak probowala 8082, bo ktos/cos wczesniej dopisal `server.port = 8082`
 * BEZPOSREDNIO do globalnego `application.properties` (WYZSZY priorytet niz
 * `.properties(...)`, dokladnie ta sama pulapka co z `spring.autoconfigure.exclude`
 * opisana nizej). Docelowo TA linia zostala USUNIETA z globalnego pliku (zbyt
 * ryzykowna - `server.port` w globalnym pliku nadpisalby "server.port=0" ustawiane
 * WEWNATRZ kazdej z 31 lekcji kursu, psujac je WSZYSTKIE naraz). Zamiast tego port
 * jest ustawiany przez {@code System.setProperty("server.port", "8082")} PRZED
 * {@code .run()} - System properties maja WYZSZY priorytet niz classpath'owy
 * `application.properties`, wiec to JEDYNY niezawodny sposob nadpisania czegokolwiek,
 * co (teraz lub w przyszlosci) mogloby zostac ustawione globalnie. Nadal mozna
 * nadpisac port z linii polecen: `--server.port=X` (argumenty CLI maja NAJWYZSZY
 * priorytet ze wszystkich).
 */
@SpringBootApplication(
        scanBasePackages = {"com.example.javaquest.web", "com.example.javaquest.platform"},
        exclude = {R2dbcAutoConfiguration.class, ArtemisAutoConfiguration.class}
)
@EntityScan("com.example.javaquest.platform")
@EnableJpaRepositories("com.example.javaquest.platform")
public class JavaQuestApplication {

    // Globalny application.properties WYKLUCZA auto-konfiguracje Spring Security dla calego repo
    // (zeby lekcje kursu nie dostawaly 401 - patrz komentarze tamze). Platforma potrzebuje
    // prawdziwego Spring Security (logowanie JWT, ochrona /api/**), wiec ta lista jest tu
    // nadpisana System property (jedyny niezawodny sposob - patrz javadoc klasy) - IDENTYCZNA jak
    // globalna, ale BEZ SecurityAutoConfiguration i SecurityFilterAutoConfiguration (ta druga
    // rejestruje filtr springSecurityFilterChain w Tomcacie - bez niej nasz SecurityFilterChain
    // istnialby, ale nie dostawalby zadnych zadan). Zostaja wykluczone: UserDetailsService
    // (nie uzywamy - filtr JWT sam czyta uzytkownikow), Management*, reaktywne i pozostale
    // (Rabbit/ActiveMQ/JMS/Zipkin). Dopisujac cos do globalnej listy, dopisz to takze tutaj.
    private static final String PLATFORM_AUTOCONFIGURE_EXCLUDE = String.join(",",
            "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration",
            "org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration",
            "org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration",
            "org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration",
            "org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration",
            "org.springframework.boot.actuate.autoconfigure.amqp.RabbitHealthContributorAutoConfiguration",
            "org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration",
            "org.springframework.boot.actuate.autoconfigure.jms.JmsHealthContributorAutoConfiguration",
            "org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration");

    public static void main(String[] args) {
        // Patrz javadoc klasy - System property (nie .properties() ponizej, nie globalny
        // application.properties) to jedyny niezawodny sposob ustawienia domyslnego portu.
        System.setProperty("server.port", "8082");
        System.setProperty("spring.autoconfigure.exclude", PLATFORM_AUTOCONFIGURE_EXCLUDE);

        new SpringApplicationBuilder(JavaQuestApplication.class)
                // Flyway na classpath (uzywany przez inne rozdzialy kursu, np. _10_dao/_23_spring_data_jpa)
                // wylaczylby domyslne tworzenie schematu przez Hibernate (ddl-auto=none) i probowalby
                // zaaplikowac NIEZWIAZANE migracje z src/main/resources/db/migration na naszej bazie -
                // ta platforma ma WLASNY schemat tworzony przez Hibernate (ddl-auto=update).
                // Baza, Hibernate, SMTP i JWT: javaquest-platform.properties (PlatformPropertiesConfig).
                .properties("spring.flyway.enabled=false")
                .run(args);
    }

}
