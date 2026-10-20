import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 * Weryfikator pytan toru OCP (uruchamiany przez ocp_verify.cjs, nie bezposrednio).
 * Kazdy podkatalog katalogu z argumentu to jeden przypadek: pliki .java, plik "mode"
 * ("compile" albo "run") i opcjonalny "main" (prosta nazwa klasy z metoda main).
 * Kompiluje z --release 21 i wypisuje jedna linie wyniku na przypadek:
 *   id CE linie | id COMPILED | id OK base64(stdout) | id EXC NazwaWyjatku base64(stdout) | id TIMEOUT
 */
public class OcpVerify {

    public static void main(String[] args) throws Exception {
        // Pytania zakladaja domyslna lokalizacje en-US i strefe UTC (jak w lekcji wprowadzajacej).
        java.util.Locale.setDefault(java.util.Locale.US);
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        PrintStream realOut = System.out;
        PrintStream sink = new PrintStream(new ByteArrayOutputStream());
        System.setErr(sink);
        List<Path> cases;
        try (Stream<Path> s = Files.list(Path.of(args[0]))) {
            cases = s.filter(Files::isDirectory).sorted().toList();
        }
        for (Path dir : cases) {
            realOut.println(dir.getFileName() + "\t" + runCase(compiler, dir, realOut));
            realOut.flush();
        }
        System.exit(0);
    }

    private static String runCase(JavaCompiler compiler, Path dir, PrintStream realOut) throws Exception {
        List<Path> sources;
        try (Stream<Path> s = Files.list(dir)) {
            sources = s.filter(p -> p.toString().endsWith(".java")).toList();
        }
        Path out = Files.createDirectories(dir.resolve("classes"));
        DiagnosticCollector<JavaFileObject> diags = new DiagnosticCollector<>();
        boolean ok;
        try (StandardJavaFileManager fm = compiler.getStandardFileManager(diags, null, StandardCharsets.UTF_8)) {
            ok = compiler.getTask(null, fm, diags, List.of("--release", "21", "-proc:none", "-Xlint:none", "-nowarn", "-d", out.toString()),
                    null, fm.getJavaFileObjectsFromPaths(sources)).call();
        }
        if (!ok) {
            TreeSet<Long> lines = new TreeSet<>();
            StringBuilder msg = new StringBuilder();
            for (Diagnostic<? extends JavaFileObject> d : diags.getDiagnostics()) {
                if (d.getKind() == Diagnostic.Kind.ERROR) {
                    lines.add(d.getLineNumber());
                    msg.append(d.getMessage(null).replaceAll("\\s+", " ")).append(" | ");
                }
            }
            return "CE\t" + join(lines) + "\t" + b64(msg.toString());
        }
        if (Files.readString(dir.resolve("mode")).trim().equals("compile")) {
            return "COMPILED";
        }
        String hint = Files.exists(dir.resolve("main")) ? Files.readString(dir.resolve("main")).trim() : null;
        URLClassLoader loader = new URLClassLoader(new URL[]{out.toUri().toURL()}, OcpVerify.class.getClassLoader());
        Method main = findMain(loader, out, hint);
        if (main == null) {
            return "NOMAIN";
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        AtomicReference<Throwable> thrown = new AtomicReference<>();
        Thread runner = new Thread(() -> {
            try {
                main.invoke(null, (Object) new String[0]);
            } catch (InvocationTargetException e) {
                thrown.set(e.getCause());
            } catch (Throwable t) {
                thrown.set(t);
            }
        }, "main");
        System.setOut(capture);
        try {
            runner.setContextClassLoader(loader);
            runner.start();
            runner.join(10_000);
        } finally {
            System.setOut(realOut);
        }
        if (runner.isAlive()) {
            return "TIMEOUT\t" + b64(buffer.toString(StandardCharsets.UTF_8));
        }
        String printed = b64(buffer.toString(StandardCharsets.UTF_8));
        Throwable t = thrown.get();
        return t == null ? "OK\t" + printed : "EXC\t" + t.getClass().getSimpleName() + "\t" + printed;
    }

    private static Method findMain(ClassLoader loader, Path out, String hint) throws Exception {
        List<Method> found = new ArrayList<>();
        List<Path> classes;
        try (Stream<Path> s = Files.walk(out)) {
            classes = s.filter(p -> p.toString().endsWith(".class")).toList();
        }
        for (Path p : classes) {
            String name = out.relativize(p).toString().replace('\\', '/').replace('/', '.').replaceAll("\\.class$", "");
            if (name.equals("module-info")) {
                continue;
            }
            Class<?> c = Class.forName(name, false, loader);
            try {
                Method m = c.getDeclaredMethod("main", String[].class);
                if (Modifier.isStatic(m.getModifiers())) {
                    m.setAccessible(true);
                    found.add(m);
                }
            } catch (NoSuchMethodException ignored) {
                // brak main w tej klasie
            }
        }
        if (found.size() == 1) {
            return found.get(0);
        }
        return found.stream().filter(m -> m.getDeclaringClass().getSimpleName().equals(hint)).findFirst().orElse(null);
    }

    private static String join(TreeSet<Long> lines) {
        return String.join(",", lines.stream().map(String::valueOf).toList());
    }

    private static String b64(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }
}
