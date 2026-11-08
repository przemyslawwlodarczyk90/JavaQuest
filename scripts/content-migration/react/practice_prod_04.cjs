const {lesson,nodeSafeSetup}=require('./practice_kit.cjs');
const dockerfile=String.raw`# Dockerfile
# --- etap 1: budowanie ---
FROM node:22-alpine AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci
COPY . .
ARG VITE_APP_VERSION=dev
ENV VITE_APP_VERSION=$VITE_APP_VERSION
RUN npm run build

# --- etap 2: serwowanie ---
FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
COPY docker/config.template.json /etc/nginx/templates/config.json.template
# Bez tego wynik szablonu trafia do /etc/nginx/conf.d i nie jest serwowany.
ENV NGINX_ENVSUBST_OUTPUT_DIR=/usr/share/nginx/runtime
RUN mkdir -p /usr/share/nginx/runtime
EXPOSE 80
HEALTHCHECK CMD wget -qO- http://127.0.0.1/healthz || exit 1
`;
const ignore=`node_modules
dist
.git
.env*.local
coverage
`;
const nginx=String.raw`# nginx.conf
server {
  listen 80;
  root /usr/share/nginx/html;
  index index.html;

  gzip on;
  gzip_types text/css application/javascript application/json image/svg+xml;
  gzip_min_length 1024;

  location /assets/ {
    add_header Cache-Control "public, max-age=31536000, immutable";
    add_header X-Content-Type-Options "nosniff";
    try_files $uri =404;
  }

  location /api/ {
    proxy_pass http://backend:8080;
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
  }

  location = /healthz {
    access_log off;
    default_type text/plain;
    return 200 "ok";
  }

  location = /config.json {
    add_header Cache-Control "no-cache";
    root /usr/share/nginx/runtime;
  }

  location / {
    add_header Cache-Control "no-cache";
    add_header X-Content-Type-Options "nosniff";
    add_header Referrer-Policy "strict-origin-when-cross-origin";
    try_files $uri $uri/ /index.html;
  }
}
`;
const template=`{ "apiUrl": "\${API_URL}", "sentryDsn": "\${SENTRY_DSN}" }
`;
const compose=`# compose.yaml
services:
  frontend:
    build:
      context: ./frontend
      args:
        VITE_APP_VERSION: \${APP_VERSION:-dev}
    ports:
      - "8081:80"
    environment:
      API_URL: /api
      SENTRY_DSN: \${SENTRY_DSN:-}
    depends_on:
      - backend

  backend:
    build: ./backend
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/devdesk
      SPRING_DATASOURCE_USERNAME: devdesk
      SPRING_DATASOURCE_PASSWORD: \${DB_PASSWORD}
    depends_on:
      - db

  db:
    image: postgres:17-alpine
    environment:
      POSTGRES_DB: devdesk
      POSTGRES_USER: devdesk
      POSTGRES_PASSWORD: \${DB_PASSWORD}
    volumes:
      - db-data:/var/lib/postgresql/data

volumes:
  db-data:
`;
const spa=String.raw`// SpaConfig.java (pakiet config; klasy z org.springframework.web.servlet.config.annotation, .resource, .core.io, .http)
@Configuration
public class SpaConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)).cachePublic().immutable());

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.noCache())
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requested = location.createRelative(resourcePath);
                        if (requested.exists() && requested.isReadable()) return requested;
                        if (resourcePath.startsWith("api/")) return null; // brakujący endpoint = prawdziwe 404
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}`;
const main=String.raw`// main.tsx
import {createRoot} from "react-dom/client";
import {loadRuntime} from "./runtime";

declare function App():React.JSX.Element;

async function start():Promise<void>{
  const config=await loadRuntime();
  console.info("API:",config.apiUrl);
  createRoot(document.getElementById("root")!).render(<App/>);
}

void start();`;
const audit=String.raw`export type Stage={base:string;name?:string;lines:string[]};

export function parseStages(dockerfile:string){
  const stages:Stage[]=[];
  for(const raw of dockerfile.split(/\r?\n/)){
    const line=raw.trim();
    if(!line||line.startsWith("#"))continue;
    const from=/^FROM\s+(\S+)(?:\s+AS\s+(\S+))?/i.exec(line);
    if(from)stages.push({base:from[1],name:from[2],lines:[]});
    else stages.at(-1)?.lines.push(line);
  }
  return stages;
}

export function auditDockerfile(dockerfile:string,dockerignore:string){
  const problems:string[]=[];
  const stages=parseStages(dockerfile);
  const runtime=stages.at(-1);
  if(stages.length<2)problems.push("brak budowania wieloetapowego");
  if(runtime&&/^node(:|$)/.test(runtime.base))problems.push("obraz końcowy zawiera Node");
  if(runtime&&!runtime.lines.some(line=>/^COPY\s+--from=/.test(line)))problems.push("etap końcowy nie kopiuje wyniku budowania");
  for(const stage of stages){
    const copyAll=stage.lines.findIndex(line=>/^COPY\s+\.\s+\./.test(line));
    const install=stage.lines.findIndex(line=>/^RUN\s+npm\s+(ci|install)/.test(line));
    if(copyAll>=0&&install>copyAll)problems.push("COPY . . przed instalacją psuje cache warstw");
    if(stage.lines.some(line=>/^RUN\s+npm\s+install/.test(line)))problems.push("npm install zamiast npm ci");
  }
  const ignored=dockerignore.split(/\r?\n/).map(line=>line.trim());
  for(const required of ["node_modules",".env*.local"])if(!ignored.includes(required))problems.push(".dockerignore bez "+required);
  return problems;
}

export type Location={match:string;body:string};

export function parseLocations(conf:string):Location[]{
  const found:Location[]=[];
  const pattern=/location\s+([^{]+?)\s*\{([^}]*)\}/g;
  for(const match of conf.matchAll(pattern))found.push({match:match[1].trim(),body:match[2]});
  return found;
}

export function addHeaders(body:string){
  return [...body.matchAll(/add_header\s+(\S+)/g)].map(match=>match[1]);
}

// add_header w bloku location zastępuje nagłówki odziedziczone z bloku server.
export function effectiveHeaders(serverHeaders:readonly string[],locationBody:string){
  const own=addHeaders(locationBody);
  return own.length?own:[...serverHeaders];
}

export function auditNginx(conf:string){
  const problems:string[]=[];
  const locations=parseLocations(conf);
  const find=(match:string)=>locations.find(location=>location.match===match);
  const assets=find("/assets/"),api=find("/api/"),root=find("/");
  if(!root||!/try_files[^;]*\/index\.html/.test(root.body))problems.push("brak fallbacku SPA");
  if(!assets)problems.push("brak osobnego bloku assets");
  else{
    if(!/=404/.test(assets.body))problems.push("assets bez prawdziwego 404");
    if(!/immutable/.test(assets.body))problems.push("assets bez immutable");
  }
  if(!api||!/proxy_pass/.test(api.body))problems.push("api bez proxy do backendu");
  if(root&&!/no-cache/.test(root.body))problems.push("index.html bez no-cache");
  if(!/gzip\s+on/.test(conf))problems.push("brak kompresji");
  return problems;
}

// Odpowiednik envsubst dla szablonu config.json: brak zmiennej daje pusty tekst, jak w kontenerze.
export function renderTemplate(template:string,env:Record<string,string|undefined>){
  return template.replace(/\$\{([A-Z_][A-Z0-9_]*)\}/g,(_all,name:string)=>env[name]??"");
}

export type Variant="platforma"|"nginx"|"jar";

export function chooseVariant({ownInfrastructure,sharedRelease}:{ownInfrastructure:boolean;sharedRelease:boolean}):Variant{
  if(sharedRelease)return "jar";
  return ownInfrastructure?"nginx":"platforma";
}`;
const runtime=String.raw`import {z} from "zod";

const schema=z.object({apiUrl:z.string().min(1),sentryDsn:z.string().optional()});

export type RuntimeConfig=z.output<typeof schema>;

export async function loadRuntime(fetcher:typeof fetch=fetch):Promise<RuntimeConfig>{
  const response=await fetcher("/config.json",{cache:"no-store"});
  const value:unknown=response.ok?await response.json():{};
  const parsed=schema.safeParse(value);
  if(!parsed.success)throw new Error("Błędny config.json");
  return parsed.data;
}`;
const tests=String.raw`import {readFileSync} from "node:fs";
import {resolve} from "node:path";
import {parse} from "yaml";
import {expect,test,vi} from "vitest";
import {addHeaders,auditDockerfile,auditNginx,chooseVariant,effectiveHeaders,parseLocations,parseStages,renderTemplate} from "./deployAudit";
import {loadRuntime} from "./runtime";

const read=(file:string)=>readFileSync(resolve(process.cwd(),file),"utf8");

test("Dockerfile lekcji przechodzi przegląd",()=>{
  expect(auditDockerfile(read("Dockerfile"),read(".dockerignore"))).toEqual([]);
  const stages=parseStages(read("Dockerfile"));
  expect(stages.map(stage=>stage.base)).toEqual(["node:22-alpine","nginx:1.27-alpine"]);
  expect(stages[0].name).toBe("build");
});

test("przegląd wykrywa typowe błędy Dockerfile",()=>{
  const ok=".dockerignore\nnode_modules\n.env*.local\n";
  expect(auditDockerfile("FROM node:22\nCOPY . .\nRUN npm ci\nRUN npm run build\n",ok)).toEqual(expect.arrayContaining(["brak budowania wieloetapowego","obraz końcowy zawiera Node","COPY . . przed instalacją psuje cache warstw"]));
  expect(auditDockerfile("FROM node:22 AS b\nRUN npm install\nFROM nginx\nCOPY --from=b a b\n",ok)).toEqual(["npm install zamiast npm ci"]);
  expect(auditDockerfile("FROM node:22 AS b\nRUN npm ci\nFROM nginx\n",ok)).toEqual(["etap końcowy nie kopiuje wyniku budowania"]);
  expect(auditDockerfile("FROM node:22 AS b\nFROM nginx\nCOPY --from=b a b\n","")).toEqual([".dockerignore bez node_modules",".dockerignore bez .env*.local"]);
});

test("konfiguracja Nginx lekcji przechodzi przegląd",()=>{
  const conf=read("nginx.conf");
  expect(auditNginx(conf)).toEqual([]);
  expect(parseLocations(conf).map(location=>location.match)).toEqual(["/assets/","/api/","= /healthz","= /config.json","/"]);
});

test("przegląd wykrywa fallback bez osobnych bloków",()=>{
  const conf="server { location / { try_files $uri /index.html; } }";
  expect(auditNginx(conf)).toEqual(["brak osobnego bloku assets","api bez proxy do backendu","index.html bez no-cache","brak kompresji"]);
  expect(auditNginx("server { gzip on; location /assets/ { try_files $uri /index.html; } }")).toContain("assets bez prawdziwego 404");
  expect(auditNginx("server { gzip on; }")).toContain("brak fallbacku SPA");
});

test("add_header w location zastępuje nagłówki z bloku server",()=>{
  expect(addHeaders('add_header A "1"; add_header B "2";')).toEqual(["A","B"]);
  expect(effectiveHeaders(["X-Frame-Options"],'add_header Cache-Control "no-cache";')).toEqual(["Cache-Control"]);
  expect(effectiveHeaders(["X-Frame-Options"],"try_files $uri =404;")).toEqual(["X-Frame-Options"]);
});

test("szablon konfiguracji ze zmiennych kontenera",()=>{
  expect(JSON.parse(renderTemplate(read("docker/config.template.json"),{API_URL:"/inne",SENTRY_DSN:""}))).toEqual({apiUrl:"/inne",sentryDsn:""});
  expect(renderTemplate("\${A}-\${B}",{A:"1"})).toBe("1-");
  expect(renderTemplate("$A \${a}",{A:"1"})).toBe("$A \${a}");
});

test("compose: usługi, zależności i brak sekretów wpisanych na stałe",()=>{
  const config=parse(read("compose.yaml")) as {services:Record<string,{environment?:Record<string,string>;depends_on?:string[];ports?:string[]}>;volumes:object};
  expect(Object.keys(config.services)).toEqual(["frontend","backend","db"]);
  expect(config.services.frontend.depends_on).toEqual(["backend"]);
  expect(config.services.frontend.ports).toEqual(["8081:80"]);
  expect(config.services.db.environment?.POSTGRES_PASSWORD).toBe("\${DB_PASSWORD}");
  expect(config.services.backend.environment?.SPRING_DATASOURCE_PASSWORD).toBe("\${DB_PASSWORD}");
  expect(config.services.backend.ports).toBeUndefined();
  expect(Object.keys(config.volumes)).toEqual(["db-data"]);
});

test("wybór wariantu wdrożenia",()=>{
  expect(chooseVariant({ownInfrastructure:false,sharedRelease:false})).toBe("platforma");
  expect(chooseVariant({ownInfrastructure:true,sharedRelease:false})).toBe("nginx");
  expect(chooseVariant({ownInfrastructure:true,sharedRelease:true})).toBe("jar");
});

test("konfiguracja czasu działania jest walidowana",async()=>{
  const ok=vi.fn(async()=>new Response(JSON.stringify({apiUrl:"/api"})));
  await expect(loadRuntime(ok as unknown as typeof fetch)).resolves.toEqual({apiUrl:"/api"});
  const empty=vi.fn(async()=>new Response(JSON.stringify({apiUrl:""})));
  await expect(loadRuntime(empty as unknown as typeof fetch)).rejects.toThrow("Błędny config.json");
  const missing=vi.fn(async()=>new Response(null,{status:404}));
  await expect(loadRuntime(missing as unknown as typeof fetch)).rejects.toThrow("Błędny config.json");
});`;
const exercises=[
['Napisz wieloetapowy Dockerfile.','Node do budowania, Nginx do serwowania.',dockerfile],
['Ustaw kolejność kroków dla cache warstw.','package*.json i npm ci przed kodem.',dockerfile],
['Przekaż wersję aplikacji jako ARG.','Wartość trafia do builda przez ENV.',dockerfile],
['Dodaj HEALTHCHECK.','Sprawdza działający serwer, a nie samo uruchomienie kontenera.',dockerfile],
['Napisz .dockerignore.','node_modules i lokalne .env nie mogą trafić do kontekstu.',ignore],
['Napisz konfigurację Nginx z fallbackiem SPA.','Istniejący plik albo index.html.',nginx],
['Zwracaj prawdziwe 404 dla assets.','try_files $uri =404.',nginx],
['Ustaw cache na rok dla plików z hashem.','Oraz no-cache dla index.html.',nginx],
['Włącz kompresję dla tekstu.','Obrazy i tak są skompresowane.',nginx],
['Dodaj proxy /api/ do backendu.','Nazwa usługi z compose.',nginx],
['Przekaż prawdziwy adres i protokół klienta.','Nagłówki X-Forwarded-*.',nginx],
['Powtórz nagłówki w blokach location.','add_header nie sumuje się z blokiem nadrzędnym.',nginx],
['Dodaj endpoint /healthz.','Bez logowania i z typem tekstowym.',nginx],
['Złóż compose z frontendem, backendem i bazą.','Backend bez publikacji portu.',compose],
['Podaj hasło przez zmienną, nie w pliku.','${DB_PASSWORD} pochodzi ze środowiska.',compose],
['Wyjaśnij, czemu proxy eliminuje CORS.','Ile źródeł widzi przeglądarka?','Nginx serwuje frontend i przekazuje /api/, więc przeglądarka widzi jedno źródło. Nie ma żądań cross-origin, ciasteczka HttpOnly i SameSite=Lax działają bez dodatkowych ustawień. Spring Boot czyta X-Forwarded-* po ustawieniu server.forward-headers-strategy=framework.'],
['Napisz szablon config.json i ustaw katalog wyjściowy.','Obraz nginx podstawia zmienne z katalogu templates, a wynik domyślnie trafia do conf.d.',template+'\n# Dockerfile: ENV NGINX_ENVSUBST_OUTPUT_DIR=/usr/share/nginx/runtime oraz RUN mkdir -p /usr/share/nginx/runtime\n# nginx.conf: location = /config.json { root /usr/share/nginx/runtime; }'],
['Wczytaj konfigurację przed renderem.','unknown i walidacja schematem.',runtime],
['Użyj void przy start().','Linter wie, że świadomie nie czekasz na Promise.',main],
['Uruchom ten sam obraz z innym API.','Zmiana to restart, bez budowania.','docker run -e API_URL=https://api.staging.example -e SENTRY_DSN= -p 8081:80 devdesk-frontend\nSprawdź /config.json. Ta sama operacja z innym API_URL daje inną konfigurację w tym samym obrazie.'],
['Skonfiguruj fallback SPA w Spring Boot.','Brakujący endpoint ma dać prawdziwe 404.',spa],
['Zbuduj jeden JAR z frontendem.','Wtyczka Maven uruchamia build, a zasoby trafiają do static.','frontend-maven-plugin instaluje Node i uruchamia npm ci oraz npm run build w fazie generate-resources. maven-resources-plugin kopiuje dist do target/classes/static. mvn package daje JAR z API i frontendem.'],
['Napisz przegląd Dockerfile.','Reguły można sprawdzić na tekście.',audit],
['Napisz przegląd konfiguracji Nginx.','Ten prosty parser obsługuje konfigurację bez zagnieżdżeń.',audit],
['Wylicz nagłówki efektywne dla bloku location.','Własny add_header kasuje odziedziczone.',audit],
['Napisz odpowiednik envsubst do testów.','Brak zmiennej daje pusty tekst.',audit],
['Przetestuj compose przez parser YAML.','Sprawdzaj znaczenie, a nie wcięcia.',tests],
['Porównaj trzy warianty wdrożenia.','Infrastruktura i cykl wydań.','Platforma statyczna: najmniej pracy, CDN i podglądy. Kontener Nginx: własna infrastruktura, pełna kontrola i jeden obraz dla środowisk. JAR Spring Boot: mały zespół i jeden artefakt, kosztem wspólnego cyklu wydań frontendu i backendu.'],
['Sprawdź w DevTools wynik w kontenerze.','Trzy żądania i nagłówki.','/tickets/42: 200 i no-cache. Plik z assets: immutable. /assets/brak.js: 404. /api/nie-ma: odpowiedź backendu. Sprawdź też rozmiar obrazu przez docker images oraz logowanie, które potwierdza działanie ciasteczka bez CORS.'],
['Sprawdź kryterium akceptacji lekcji.','Obraz, compose, 404, zmienna, JAR.','Obraz ma kilkadziesiąt MB i nie zawiera źródeł. docker compose up uruchamia DevDesk z logowaniem. Odświeżenie tras działa, a brakujący plik i endpoint zwracają 404. Ten sam obraz łączy się z różnymi API. Wariant JAR serwuje aplikację i API z jednego procesu.'],
];
const concepts=[
['Co daje budowanie wieloetapowe?','Mały obraz końcowy bez Node i źródeł','Szybszy Nginx','Szyfrowanie','Automatyczne testy','Kopiujesz tylko wynik poprzedniego etapu.'],
['Co zawiera końcowy obraz?','Nginx, dist i konfigurację','Node i node_modules','Kod źródłowy','Bazę danych','Pozostałe kroki zostają w etapie build.'],
['Czemu kopiować package.json przed kodem?','Zmiana komponentu nie powoduje ponownej instalacji','Dla bezpieczeństwa','Dla Vite','Bez znaczenia','Warstwa npm ci zostaje w cache.'],
['Czym npm ci różni się od npm install?','Instaluje dokładnie według lockfile','Niczym','Jest wolniejszy z założenia','Nie używa lockfile','Daje powtarzalny wynik.'],
['Co oznacza COPY --from=build?','Kopiuje z poprzedniego etapu','Kopiuje z internetu','Kopiuje z kontenera działającego','Kopiuje do hosta','Podstawa wieloetapowości.'],
['Po co .dockerignore?','Zmniejsza kontekst i chroni przed lokalnymi plikami','Blokuje obraz','Zastępuje .gitignore','Ustawia port','node_modules i .env*.local nie wchodzą do kontekstu.'],
['Czym różni się ARG od ENV?','ARG istnieje przy budowaniu, ENV także w działaniu obrazu','Niczym','ENV tylko przy budowaniu','ARG w działaniu','Sekretów nie przekazuj przez ARG.'],
['Czy sekret w ARG jest bezpieczny?','Nie, może zostać w historii obrazu','Tak','Tak, po COPY','Tak, po npm ci','Użyj BuildKit secrets.'],
['Co robi EXPOSE 80?','Dokumentuje port, nie publikuje go','Publikuje port','Otwiera firewall','Uruchamia Nginx','Publikacja to -p lub ports.'],
['Co robi HEALTHCHECK?','Sprawdza działanie usługi w kontenerze','Buduje obraz','Pobiera aktualizacje','Restartuje hosta','Status healthy lub unhealthy.'],
['Co robi try_files $uri $uri/ /index.html?','Plik, katalog albo fallback','Zawsze index','Zawsze 404','Proxy','Klasyczny zapis dla SPA.'],
['Co robi try_files $uri =404?','Zwraca plik albo prawdziwe 404','Fallback do index','Przekierowuje','Włącza proxy','Właściwe dla assets.'],
['Skąd problem z add_header w location?','Zastępuje nagłówki z bloku nadrzędnego','Dodaje je podwójnie','Nie działa w alpine','Blokuje gzip','Powtórz nagłówki lub użyj include.'],
['Co zawiera proxy_set_header Host $host?','Oryginalny host z żądania','Host backendu','Adres IP klienta','Nazwę pliku','Aplikacja buduje poprawne adresy.'],
['Co przekazuje X-Forwarded-Proto?','Protokół klienta','Wersję HTTP','Adres IP','Czas żądania','Spring wie, że było HTTPS.'],
['Co musi ustawić Spring Boot dla X-Forwarded-*?','server.forward-headers-strategy=framework','spring.web.cors','server.port','Nic','Inaczej nagłówki są ignorowane.'],
['Czym jest backend w proxy_pass?','Nazwą usługi z sieci compose','Domeną publiczną','Plikiem','Zmienną Vite','DNS sieci Dockera rozwiązuje nazwę.'],
['Co się stanie, gdy upstream nie istnieje przy starcie Nginx?','Nginx nie wystartuje z błędem host not found','Działa normalnie','Zwróci 404','Pominie blok','Hosty w proxy_pass są rozwiązywane przy starcie.'],
['Po co gzip_min_length?','Nie kompresuje bardzo małych odpowiedzi','Ogranicza plik','Włącza brotli','Wyłącza gzip','Mały plik może nawet urosnąć.'],
['Czy kompresować JPG przez gzip?','Nie, jest już skompresowany','Tak','Zawsze','Tylko w dev','Dlatego gzip_types wskazuje tekst.'],
['Czy Nginx potrzebuje CORS przy proxy?','Nie, jedno źródło','Tak','Tak, zawsze','Tylko dla GET','To jedna z głównych zalet.'],
['Czy ciasteczko SameSite=Lax działa przy proxy?','Tak','Nie','Tylko po HTTPS','Tylko w Chrome','To jedno źródło.'],
['Skąd podobieństwo do trybu dev?','Proxy Vite też daje jedno źródło','Bez powodu','Bo Vite to Nginx','Bo CORS wyłączono','Ten sam układ.'],
['Po co backend bez publikacji portu?','Dostęp tylko przez sieć usług','Dla szybkości','Dla CSS','Bez znaczenia','Mniejsza powierzchnia ataku.'],
['Dlaczego ${DB_PASSWORD} zamiast wartości?','Sekret nie trafia do repozytorium','Dla składni','Dla YAML','Bez powodu','Compose czyta go ze środowiska.'],
['Co to jest db-data?','Nazwany wolumin danych bazy','Plik CSS','Sieć','Obraz','Dane przetrwają usunięcie kontenera.'],
['Co oznacza depends_on?','Kolejność startu, nie gotowość','Pełną gotowość usługi','Restart','Kompilację','Gotowość sprawdza healthcheck z warunkiem.'],
['Co oznacza ${APP_VERSION:-dev}?','Wartość domyślna dev','Odejmowanie','Wymaganie zmiennej','Komentarz','Składnia podstawiania compose.'],
['Co robi katalog /etc/nginx/templates?','Obraz nginx podstawia zmienne przy starcie','Trzyma logi','Zawiera certyfikaty','Nic','Skrypt startowy używa envsubst.'],
['Dokąd domyślnie trafia wynik szablonu nginx?','Do /etc/nginx/conf.d','Do html','Do /tmp','Do /var/log','Stąd NGINX_ENVSUBST_OUTPUT_DIR.'],
['Co daje NGINX_ENVSUBST_OUTPUT_DIR?','Zmienia katalog wyniku szablonów, który musi istnieć','Włącza gzip','Zmienia port','Wyłącza logi','Bez tego config.json trafia do conf.d i nie jest serwowany. Sprawdziło to dopiero uruchomienie kontenera.'],
['Co jest ryzykiem envsubst w całym pliku conf?','Podmienia też $uri, jeśli nie ograniczysz zmiennych','Nic','Brak gzipa','Większy obraz','Obraz nginx podstawia tylko zdefiniowane zmienne, ale warto o tym wiedzieć.'],
['Jak traktować wynik pliku config.json w TSX?','Jako unknown','Rzutować na typ','Jako any','Jako string','Walidacja to wyłapie.'],
['Po co void start()?','Świadomie nie czekasz na Promise','Blokuje start','Zmienia typ na void w runtime','Dla CSS','Reguła no-floating-promises.'],
['Co oznacza jeden obraz dla wielu środowisk?','Ten sam artefakt i inna konfiguracja','Wiele buildów','Jeden serwer','Jedna baza','To, co przetestowane, trafia dalej.'],
['Skąd Spring Boot serwuje pliki?','classpath:/static/','src/test','Z /tmp','Z bazy','Domyślna lokalizacja zasobów.'],
['Co robi frontend-maven-plugin?','Uruchamia Node i npm w Maven','Publikuje JAR','Tworzy bazę','Instaluje Javę','Jeden build dla obu części.'],
['W której fazie zbudować frontend?','generate-resources','deploy','clean','site','Przed pakowaniem JAR.'],
['Dlaczego resolver wyklucza api/?','Brakujący endpoint ma dawać 404','Dla CORS','Dla cache','Bez powodu','Inaczej klient dostanie HTML.'],
['Jaka jest wada JAR z frontendem?','Wspólny cykl wydań','Brak API','CORS','Duży Nginx','Zmiana w UI wymaga wdrożenia backendu.'],
['Jaka jest zaleta JAR z frontendem?','Jeden artefakt i jedno źródło','Niezależne wydania','CDN z definicji','Mniej Javy','Dobry dla małego zespołu.'],
['Kiedy platforma statyczna jest najlepsza?','Gdy nie masz własnej infrastruktury','Gdy wymagasz serwerów firmy','Zawsze','Nigdy','Najmniej pracy, ale inne ograniczenia.'],
['Kiedy kontener Nginx?','Własna infrastruktura i pełna kontrola','Mały hobby','Zawsze','Tylko Windows','Kubernetes lub serwery firmy.'],
['Czy nginx:alpine zawiera Node?','Nie','Tak','Tylko 22','Tylko w dev','Dlatego obraz jest mały.'],
['Jak sprawdzić rozmiar obrazu?','docker images','npm ls','curl','tsc','Wynik: kilkadziesiąt MB.'],
['Jak sprawdzić, co jest w obrazie?','docker run z sh lub docker history','npm audit','Lighthouse','Sentry','Upewnij się, że nie ma źródeł.'],
['Czym jest warstwa obrazu?','Wynikiem kroku Dockerfile, cache’owanym','Plikiem CSS','Kolumną bazy','Kontenerem','Kolejność kroków wpływa na czas budowania.'],
['Co usuwa cache warstwy?','Zmiana wejścia kroku','Restart Dockera zawsze','Zmiana portu','Komentarz w YAML','Kolejne kroki też się przeliczają.'],
['Czemu nie uruchamiać Node do serwowania dist?','Niepotrzebny koszt i powierzchnia ataku','Nie da się','Wymaga JVM','Dla SEO','Pliki statyczne obsłuży Nginx.'],
['Co pokazuje status 200 dla brakującego JS?','Błędny fallback','Sukces','Działający cache','Poprawną konfigurację','Prowadzi do „Unexpected token <”.'],
['Dlaczego monitoring nie widzi problemów przy złym fallbacku?','Wszystko ma status 200','Brak internetu','Sentry nie działa','CSS','Prawdziwe 404 da się zmierzyć.'],
['Co należy sprawdzić po uruchomieniu compose?','Logowanie i ciasteczko bez CORS','Tylko stronę główną','Tylko logi bazy','Tylko typy','Pełna ścieżka przez proxy.'],
['Co robi location = /healthz?','Dokładne dopasowanie ścieżki','Prefiks','Wyrażenie regularne','Proxy','= ma najwyższy priorytet.'],
['Jak Nginx wybiera prefiksy location?','Najdłuższy pasujący','Pierwszy w pliku','Ostatni','Losowo','Dlatego /assets/ wygrywa z /.'],
['Czy kolejność prefiksów w pliku ma znaczenie?','Dla zwykłych prefiksów nie','Tak, zawsze','Tak, odwrotnie','Tylko w alpine','Dla wyrażeń regularnych już tak.'],
['Czemu default_type w healthz?','Odpowiedź return ma jasny typ','Dla cache','Dla gzip','Bez powodu','Inaczej byłby domyślny typ binarny.'],
['Czy test tekstowy zastępuje uruchomienie Nginx?','Nie, trzeba też sprawdzić prawdziwy serwer','Tak','Tak, w CI','Tak, z TS','Przegląd wyłapuje tylko zapisane reguły.'],
['Co sprawdza nginx -t?','Składnię konfiguracji','Testy E2E','Wydajność','Certyfikat zewnętrzny','Dobry krok przed wdrożeniem.'],
['Co oznacza nazwana sieć w compose?','Usługi widzą się po nazwie, bez publikowania portów','Dostęp z internetu','Wspólny dysk','Wspólny proces','Domyślna sieć projektu daje też DNS po nazwach usług.'],
['Jak nie zgubić nagłówków bezpieczeństwa?','include wspólnego pliku w każdym bloku','Dodać raz w http','Dodać w HTML','Nie ustawiać','Z uwagi na dziedziczenie add_header.'],
];
const header=String.raw`import {addHeaders,auditDockerfile,auditNginx,chooseVariant,effectiveHeaders,parseLocations,parseStages,renderTemplate} from "./deployAudit";
const ok="node_modules\n.env*.local\n";
const good="FROM node:22 AS b\nRUN npm ci\nFROM nginx\nCOPY --from=b a b\n";`;
const probeRows=[
['Ile etapów ma poprawny plik?','parseStages(good).length',2,'Dwa FROM.'],
['Jaka jest baza pierwszego etapu?','parseStages(good)[0].base','node:22','Parser czyta FROM.',['nginx','b','node']],
['Jak nazywa się pierwszy etap?','parseStages(good)[0].name','b','Nazwa po AS.',['node:22','build','undefined']],
['Czy etap końcowy ma nazwę?','String(parseStages(good)[1].name)','undefined','AS jest opcjonalne.',['nginx','b','null']],
['Czy komentarz jest pomijany?','parseStages("# FROM x\\nFROM y\\n").length',1,'Komentarz to nie krok.'],
['Ile problemów ma poprawny plik?','auditDockerfile(good,ok).length',0,'Spełnia wszystkie reguły.'],
['Pierwszy problem jednego etapu','auditDockerfile("FROM nginx\\nCOPY --from=b a b\\n",ok)[0]','brak budowania wieloetapowego','Jeden FROM.',['obraz końcowy zawiera Node','npm install zamiast npm ci','brak']],
['Problem Node na końcu','auditDockerfile("FROM nginx AS b\\nFROM node:22\\nCOPY --from=b a b\\n",ok)[0]','obraz końcowy zawiera Node','Node nie powinien trafić do obrazu.',['brak budowania wieloetapowego','npm install zamiast npm ci','brak']],
['Problem braku COPY --from','auditDockerfile("FROM node AS b\\nFROM nginx\\n",ok)[0]','etap końcowy nie kopiuje wyniku budowania','Bez tego obraz jest pusty.',['brak budowania wieloetapowego','obraz końcowy zawiera Node','brak']],
['Problem npm install','auditDockerfile("FROM node AS b\\nRUN npm install\\nFROM nginx\\nCOPY --from=b a b\\n",ok)[0]','npm install zamiast npm ci','Build ma być powtarzalny.',['etap końcowy nie kopiuje wyniku budowania','brak','obraz końcowy zawiera Node']],
['Problem kolejności COPY','auditDockerfile("FROM node AS b\\nCOPY . .\\nRUN npm ci\\nFROM nginx\\nCOPY --from=b a b\\n",ok)[0]','COPY . . przed instalacją psuje cache warstw','Każda zmiana kodu reinstaluje zależności.',['npm install zamiast npm ci','brak','brak budowania wieloetapowego']],
['Czy COPY . . po npm ci jest dobre?','auditDockerfile("FROM node AS b\\nRUN npm ci\\nCOPY . .\\nFROM nginx\\nCOPY --from=b a b\\n",ok).length',0,'Właściwa kolejność.'],
['Problem pustego .dockerignore','auditDockerfile(good,"")[0]','.dockerignore bez node_modules','Kontekst byłby ogromny.',['.dockerignore bez .env*.local','brak','brak budowania wieloetapowego']],
['Liczba problemów pustego .dockerignore','auditDockerfile(good,"").length',2,'node_modules i .env*.local.'],
['Ile bloków location ma ta konfiguracja?','parseLocations("location /a { x; } location = /b { y; }").length',2,'Dwa bloki.'],
['Jaki jest match z równością?','parseLocations("location = /b { y; }")[0].match','= /b','Razem z modyfikatorem.',['/b','=','b']],
['Nagłówki bloku','addHeaders(\'add_header A "1"; add_header B "2";\').join()','A,B','Lista nazw.',['A','B','pusto']],
['Nagłówki bez add_header','addHeaders("try_files $uri =404;").length',0,'Brak własnych.'],
['Efektywne bez własnych','effectiveHeaders(["X"],"try_files;").join()','X','Dziedziczone z bloku nadrzędnego.',['pusto','Y','X,Y']],
['Efektywne z własnym','effectiveHeaders(["X"],\'add_header Y "1";\').join()','Y','Własny zastępuje, a X znika.',['X,Y','X','pusto']],
['Efektywne z własnym i powtórzonym','effectiveHeaders(["X"],\'add_header Y "1"; add_header X "2";\').join()','Y,X','Dlatego powtarzasz nagłówek.',['X,Y','X','Y']],
['Pełna poprawna konfiguracja: problemy','auditNginx("gzip on; location /assets/ { try_files $uri =404; add_header C immutable; } location /api/ { proxy_pass http://b; } location / { add_header C no-cache; try_files $uri /index.html; }").length',0,'Wszystkie reguły spełnione.'],
['Problem braku fallbacku','auditNginx("gzip on;")[0]','brak fallbacku SPA','Bez niego odświeżenie daje 404.',['brak kompresji','brak osobnego bloku assets','brak']],
['Problem assets z fallbackiem','auditNginx("location /assets/ { try_files $uri /index.html; add_header C immutable; }").includes("assets bez prawdziwego 404")',true,'Stary plik dostałby HTML.'],
['Problem assets bez immutable','auditNginx("location /assets/ { try_files $uri =404; }").includes("assets bez immutable")',true,'Plik z hashem ma być trwały.'],
['Problem API bez proxy','auditNginx("location /api/ { root x; }").includes("api bez proxy do backendu")',true,'Trafiłoby do plików.'],
['Problem braku kompresji','auditNginx("").includes("brak kompresji")',true,'Reguła jest prosta.'],
['Podstawienie zmiennej','renderTemplate("${API_URL}",{API_URL:"/x"})','/x','Tak jak envsubst.',['${API_URL}','pusty tekst','API_URL']],
['Brak zmiennej daje','renderTemplate("a${X}b",{})','ab','Pusty tekst, jak w envsubst.',['a${X}b','aundefinedb','a b']],
['Mała litera w nazwie','renderTemplate("${a}",{a:"1"})','${a}','Wzorzec przyjmuje wielkie litery.',['1','pusty tekst','a']],
['Zapis bez klamer','renderTemplate("$API",{API:"1"})','$API','Nie dotyka $uri ani podobnych.',['1','pusty tekst','API']],
['Dwa podstawienia','renderTemplate("${A}/${B}",{A:"1",B:"2"})','1/2','Wszystkie wystąpienia.',['1/${B}','${A}/2','1']],
['Poprawny JSON po podstawieniu','JSON.parse(renderTemplate(\'{"a":"${A}"}\',{A:"x"})).a','x','Szablon daje poprawny plik.',['${A}','pusty tekst','undefined']],
['Czy wartość z cudzysłowem psuje JSON?','(()=>{try{JSON.parse(renderTemplate(\'{"a":"${A}"}\',{A:\'x"y\'}));return "poprawny";}catch{return "błędny";}})()','błędny','Envsubst nie escapuje wartości, dlatego walidacja przy starcie ma znaczenie.',['poprawny','x"y','undefined']],
['Wariant: własna infrastruktura','chooseVariant({ownInfrastructure:true,sharedRelease:false})','nginx','Pełna kontrola.',['platforma','jar','brak']],
['Wariant: bez infrastruktury','chooseVariant({ownInfrastructure:false,sharedRelease:false})','platforma','Najmniej pracy.',['nginx','jar','brak']],
['Wariant: wspólne wydania','chooseVariant({ownInfrastructure:false,sharedRelease:true})','jar','Jeden artefakt.',['nginx','platforma','brak']],
['Wariant: oba warunki','chooseVariant({ownInfrastructure:true,sharedRelease:true})','jar','Wspólny cykl przeważa w tym modelu.',['nginx','platforma','brak']],
['Ile linii ma etap po FROM?','parseStages("FROM a\\nRUN b\\nRUN c\\n")[0].lines.length',2,'Linie trafiają do etapu.'],
['Czy puste wiersze są pomijane?','parseStages("FROM a\\n\\n\\nRUN b\\n")[0].lines.length',1,'Tylko kroki.'],
];
const project={
'src/setup.ts':nodeSafeSetup,'src/deployAudit.ts':audit,'src/runtime.ts':runtime,'src/main.runtime.tsx':main,'src/deploy.test.ts':tests,
'Dockerfile':dockerfile,'.dockerignore':ignore,'nginx.conf':nginx,'compose.yaml':compose,'docker/config.template.json':template,'backend/SpaConfig.java.txt':spa,
'README.md':'DevDesk: kontenery. npm install, npm test. Vitest sprawdza pliki tekstowo i parsuje compose. Obraz, nginx.conf i szablon config.json sprawdza dodatkowo skrypt verify_docker_real.cjs w prawdziwym kontenerze Nginx (bez backendu, bazy i Spring Boot). SpaConfig jest wzorem do wklejenia w projekcie Spring Boot i nie jest kompilowany w tej lekcji.\n',
};
const mutants=[
{file:'src/deployAudit.ts',before:'if(stages.length<2)problems.push("brak budowania wieloetapowego");',after:''},
{file:'src/deployAudit.ts',before:'if(copyAll>=0&&install>copyAll)',after:'if(false)'},
{file:'src/deployAudit.ts',before:'return own.length?own:[...serverHeaders];',after:'return [...serverHeaders,...own];'},
{file:'src/deployAudit.ts',before:'if(!/=404/.test(assets.body))problems.push("assets bez prawdziwego 404");',after:''},
{file:'src/deployAudit.ts',before:'env[name]??""',after:'env[name]??"undefined"'},
{file:'src/deployAudit.ts',before:'if(sharedRelease)return "jar";',after:''},
{file:'nginx.conf',before:'try_files $uri =404;',after:'try_files $uri /index.html;'},
{file:'Dockerfile',before:'COPY --from=build /app/dist /usr/share/nginx/html',after:''},
{file:'compose.yaml',before:'SPRING_DATASOURCE_PASSWORD: ${DB_PASSWORD}',after:'SPRING_DATASOURCE_PASSWORD: tajne'},
{file:'src/runtime.ts',before:'.min(1)',after:''},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
