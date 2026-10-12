// Uruchom: java -cp KLASSPATH_Z_MAVEN ConfigFixtureProbe.java
// Wlasne fikcyjne pliki, bez aplikacji sklepu, sieci i systemd.
import java.nio.file.*;
import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

public class ConfigFixtureProbe {
    @Configuration(proxyBeanMethods=false)
    static class Fixture {}
    static ConfigurableApplicationContext start(Path directory, String... extra) {
        var application=new SpringApplication(Fixture.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.setLogStartupInfo(false);
        var args=new ArrayList<String>(List.of("--spring.config.location="+directory.toUri(),
                "--spring.main.banner-mode=off", "--logging.level.root=ERROR"));
        args.addAll(List.of(extra));
        return application.run(args.toArray(String[]::new));
    }
    static void equal(Object actual,Object expected) {
        if(!Objects.equals(actual,expected))throw new AssertionError(actual+" != "+expected);
    }
    public static void main(String[] args) throws Exception {
        Path directory=Files.createTempDirectory("javaquest-config-");
        Path secrets=Files.createDirectory(directory.resolve("secrets"));
        Path password=secrets.resolve("probe.password");
        Path properties=directory.resolve("application.properties");
        Path yaml=directory.resolve("application.yaml");
        Path prod=directory.resolve("application-prod.properties");
        Path monitoring=directory.resolve("application-monitoring.properties");
        try {
            Files.writeString(password,"fixture-secret-only");
            String importTree="spring.config.import=optional:configtree:"+secrets.toString().replace('\\','/')+"/\n";
            Files.writeString(properties,"probe.mode=properties\n"+importTree);
            Files.writeString(yaml,"probe:\n  mode: yaml\n");
            Files.writeString(prod,"probe.mode=prod\n");
            Files.writeString(monitoring,"probe.mode=monitoring\n");
            try(var context=start(directory)) {
                equal(context.getEnvironment().getProperty("probe.mode"),"properties");
                equal(context.getEnvironment().getProperty("probe.password"),"fixture-secret-only");
            }
            try(var context=start(directory,"--spring.profiles.active=prod,monitoring")) {
                equal(context.getEnvironment().getProperty("probe.mode"),"monitoring");
            }
            try(var context=start(directory,"--spring.profiles.active=prod,monitoring","--probe.mode=command")) {
                equal(context.getEnvironment().getProperty("probe.mode"),"command");
            }
            Files.delete(password);
            Files.delete(secrets);
            try(var context=start(directory)) {
                equal(context.getEnvironment().getProperty("probe.password"),null);
                equal(context.getEnvironment().getProperty("probe.mode"),"properties");
            }
            System.out.println("OK: properties/YAML, configtree, profile order, CLI override and optional missing tree");
        } finally {
            for(Path file:List.of(password,properties,yaml,prod,monitoring))Files.deleteIfExists(file);
            Files.deleteIfExists(secrets);
            Files.delete(directory);
        }
    }
}
