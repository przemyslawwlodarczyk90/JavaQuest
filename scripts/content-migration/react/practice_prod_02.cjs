const {lesson,nodeSafeSetup}=require('./practice_kit.cjs');
const config=String.raw`import {z} from "zod";

const schema=z.object({
  VITE_APP_NAME:z.string().default("DevDesk"),
  VITE_API_URL:z.string({error:"Brak VITE_API_URL"}).min(1,"Brak VITE_API_URL"),
  VITE_SENTRY_DSN:z.url().optional(),
  VITE_FEATURE_BOARD:z.enum(["true","false"]).default("false").transform(value=>value==="true"),
});

export type EnvSource=Record<string,unknown>&{MODE?:string;PROD?:boolean};

export function parseConfig(env:EnvSource){
  const parsed=schema.safeParse(env);
  if(!parsed.success){
    const lines=parsed.error.issues.map(issue=>"- "+issue.path.join(".")+": "+issue.message);
    throw new Error("Błędna konfiguracja aplikacji:\n"+lines.join("\n"));
  }
  return Object.freeze({
    appName:parsed.data.VITE_APP_NAME,
    apiUrl:parsed.data.VITE_API_URL,
    sentryDsn:parsed.data.VITE_SENTRY_DSN,
    features:Object.freeze({board:parsed.data.VITE_FEATURE_BOARD}),
    mode:env.MODE??"unknown",
    isProd:env.PROD===true,
  });
}

export type AppConfig=ReturnType<typeof parseConfig>;`;
const runtime=String.raw`import {z} from "zod";

const schema=z.object({
  apiUrl:z.string().min(1),
  sentryDsn:z.string().optional().transform(value=>value||undefined),
});

export type RuntimeConfig=z.output<typeof schema>;

// Dane z pliku są unknown: plik może być pusty, stary albo mieć literówkę.
export function parseRuntime(value:unknown):RuntimeConfig{
  const parsed=schema.safeParse(value);
  if(!parsed.success)throw new Error("Błędny config.json: "+parsed.error.issues.map(issue=>issue.path.join(".")).join(", "));
  return parsed.data;
}

export async function loadRuntime(fetcher:typeof fetch=fetch){
  const response=await fetcher("/config.json",{cache:"no-store"});
  if(!response.ok)throw new Error("Brak config.json: "+response.status);
  return parseRuntime(await response.json() as unknown);
}`;
const scan=String.raw`const PUBLIC=/^VITE_(APP_NAME|API_URL|SENTRY_DSN|FEATURE_[A-Z_]+|APP_VERSION)$/;
const SECRET_NAME=/(SECRET|PASSWORD|PRIVATE|TOKEN|API_KEY)/;

// Lista VITE_ to decyzja o publikacji. Każda nowa zmienna wymaga świadomego wpisu.
export function reviewEnvNames(names:readonly string[]){
  return names.flatMap(name=>{
    if(!name.startsWith("VITE_"))return [];
    if(SECRET_NAME.test(name))return [name+": sekret w paczce klienta"];
    if(!PUBLIC.test(name))return [name+": nieznana zmienna publiczna"];
    return [];
  });
}

export function parseEnvFile(text:string){
  const entries:Record<string,string>=Object.create(null);
  for(const line of text.split(/\r?\n/)){
    const trimmed=line.trim();
    if(!trimmed||trimmed.startsWith("#"))continue;
    const at=trimmed.indexOf("=");
    if(at<1)continue;
    entries[trimmed.slice(0,at).trim()]=trimmed.slice(at+1).trim();
  }
  return entries;
}

// Tylko identyfikatory: plik przykładowy nie ma prawdziwych wartości, a produkcyjne nie trafiają do testu.
export function missingFromExample(example:string,used:readonly string[]){
  const known=new Set(Object.keys(parseEnvFile(example)));
  return used.filter(name=>!known.has(name));
}`;
const appMain=String.raw`/// <reference types="vite/client" />
const declared=[
  import.meta.env.MODE,
  import.meta.env.VITE_API_URL,
  import.meta.env.VITE_FEATURE_BOARD,
  import.meta.env.DB_PASSWORD,
];
document.title=declared.join("|");
export {};`;
const appIndex=String.raw`<!doctype html><html lang="pl"><head><meta charset="utf-8"/><title>DevDesk</title></head><body><div id="root"></div><script type="module" src="/main.ts"></script></body></html>`;
const envFiles={
'fixture/.env':'VITE_APP_NAME=DevDesk\nVITE_FEATURE_BOARD=false\nDB_PASSWORD=haslo-bazy-lokalnej\n',
'fixture/.env.staging':'VITE_API_URL=https://api.staging.devdesk.example\nVITE_FEATURE_BOARD=true\n',
'fixture/.env.production':'VITE_API_URL=https://api.devdesk.example\n',
};
const envExample=`# .env.example: lista zmiennych bez prawdziwych wartości
VITE_APP_NAME=DevDesk
# Adres API, wymagany. Lokalnie /api przez proxy Vite.
VITE_API_URL=
# Publiczny DSN Sentry, opcjonalny.
VITE_SENTRY_DSN=
# Flaga tablicy Kanban: true albo false.
VITE_FEATURE_BOARD=false`;
const declaration=String.raw`// src/vite-env.d.ts
/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_APP_NAME?:string;
  readonly VITE_API_URL:string;
  readonly VITE_SENTRY_DSN?:string;
  readonly VITE_FEATURE_BOARD?:"true"|"false";
}

interface ImportMeta {
  readonly env:ImportMetaEnv;
}`;
const flagUse=String.raw`import type {AppConfig} from "./config";

export function navigation(config:AppConfig){
  return [
    {to:"/tickets",label:"Tickety"},
    ...(config.features.board?[{to:"/board",label:"Tablica"}]:[]),
  ];
}`;
const tests=String.raw`import {expect,test,vi} from "vitest";
import {parseConfig} from "./config";
import {loadRuntime,parseRuntime} from "./runtimeConfig";
import {missingFromExample,parseEnvFile,reviewEnvNames} from "./envReview";
import {navigation} from "./navigation";

const base={VITE_API_URL:"/api",MODE:"development",PROD:false};

test("konfiguracja ma typy i wartości domyślne",()=>{
  const config=parseConfig(base);
  expect(config).toMatchObject({appName:"DevDesk",apiUrl:"/api",mode:"development",isProd:false,features:{board:false}});
  expect(config.sentryDsn).toBeUndefined();
});

test("flaga tekstowa zamienia się na boolean",()=>{
  expect(parseConfig({...base,VITE_FEATURE_BOARD:"true"}).features.board).toBe(true);
  expect(parseConfig({...base,VITE_FEATURE_BOARD:"false"}).features.board).toBe(false);
  expect(()=>parseConfig({...base,VITE_FEATURE_BOARD:"tak"})).toThrow("VITE_FEATURE_BOARD");
});

test("brak adresu API zatrzymuje aplikację czytelnym błędem",()=>{
  expect(()=>parseConfig({})).toThrow("Brak VITE_API_URL");
  expect(()=>parseConfig({VITE_API_URL:""})).toThrow("Brak VITE_API_URL");
  expect(()=>parseConfig({})).toThrow("Błędna konfiguracja aplikacji");
});

test("niepoprawny DSN jest odrzucany, a poprawny przechodzi",()=>{
  expect(()=>parseConfig({...base,VITE_SENTRY_DSN:"nie-adres"})).toThrow("VITE_SENTRY_DSN");
  expect(parseConfig({...base,VITE_SENTRY_DSN:"https://k@o1.sentry.example/1"}).sentryDsn).toContain("sentry");
});

test("obiekt konfiguracji jest zamrożony",()=>{
  const config=parseConfig(base);
  expect(Object.isFrozen(config)).toBe(true);
  expect(Object.isFrozen(config.features)).toBe(true);
  expect(config.isProd).toBe(false);
  expect(parseConfig({...base,PROD:true}).isProd).toBe(true);
});

test("flaga steruje nawigacją",()=>{
  expect(navigation(parseConfig(base)).map(item=>item.to)).toEqual(["/tickets"]);
  expect(navigation(parseConfig({...base,VITE_FEATURE_BOARD:"true"})).map(item=>item.to)).toEqual(["/tickets","/board"]);
});

test("konfiguracja czasu działania jest walidowana",async()=>{
  expect(parseRuntime({apiUrl:"/api",sentryDsn:""})).toEqual({apiUrl:"/api",sentryDsn:undefined});
  expect(()=>parseRuntime({})).toThrow("apiUrl");
  expect(()=>parseRuntime(null)).toThrow("Błędny config.json");
  const ok=vi.fn(async()=>new Response(JSON.stringify({apiUrl:"/inne"})));
  await expect(loadRuntime(ok as unknown as typeof fetch)).resolves.toMatchObject({apiUrl:"/inne"});
  expect(ok.mock.calls[0]).toEqual(["/config.json",{cache:"no-store"}]);
  const bad=vi.fn(async()=>new Response(null,{status:404}));
  await expect(loadRuntime(bad as unknown as typeof fetch)).rejects.toThrow("404");
});

test("przegląd nazw wykrywa sekret w zmiennej VITE",()=>{
  expect(reviewEnvNames(["VITE_API_URL","DB_PASSWORD"])).toEqual([]);
  expect(reviewEnvNames(["VITE_PAYMENTS_SECRET"])).toEqual(["VITE_PAYMENTS_SECRET: sekret w paczce klienta"]);
  expect(reviewEnvNames(["VITE_GITHUB_TOKEN"])).toEqual(["VITE_GITHUB_TOKEN: sekret w paczce klienta"]);
  expect(reviewEnvNames(["VITE_X"])).toEqual(["VITE_X: nieznana zmienna publiczna"]);
  expect(reviewEnvNames(["VITE_FEATURE_BOARD"])).toEqual([]);
});

test("plik env: komentarze, puste wiersze i znak równości w wartości",()=>{
  const entries=parseEnvFile("# opis\n\nA=1\nB=x=y\r\nZLE\n=bez\n");
  expect({...entries}).toEqual({A:"1",B:"x=y"});
  expect(missingFromExample("A=1\n",["A","B"])).toEqual(["B"]);
  expect(parseEnvFile("__proto__=x").__proto__).toBe("x");
});`;
const buildTest=String.raw`// @vitest-environment node
import {existsSync,mkdtempSync,readdirSync,readFileSync,rmSync} from "node:fs";
import {join,resolve} from "node:path";
import {tmpdir} from "node:os";
import {build} from "vite";
import {afterAll,beforeAll,expect,test} from "vitest";

const root=resolve(process.cwd(),"fixture");
const outputs:Record<string,string>={};
const dirs:string[]=[];

async function buildMode(mode:string){
  const outDir=mkdtempSync(join(tmpdir(),"devdesk-"+mode+"-"));
  dirs.push(outDir);
  const previous=process.env.NODE_ENV;
  await build({root,mode,logLevel:"silent",configFile:false,build:{outDir,emptyOutDir:true,minify:false}});
  process.env.NODE_ENV=previous;
  const assets=join(outDir,"assets");
  outputs[mode]=readdirSync(assets).filter(name=>name.endsWith(".js")).map(name=>readFileSync(join(assets,name),"utf8")).join("\n");
}

beforeAll(async()=>{
  if(process.env.LESSON_MUTANT==="1")return;
  await buildMode("staging");
  await buildMode("production");
},120000);
afterAll(()=>{for(const dir of dirs)if(existsSync(dir))rmSync(dir,{recursive:true,force:true});});

test.skipIf(process.env.LESSON_MUTANT==="1")("prawdziwe buildy różnią się adresem API",()=>{
  expect(outputs.staging).toContain("https://api.staging.devdesk.example");
  expect(outputs.production).toContain("https://api.devdesk.example");
  expect(outputs.production).not.toContain("staging");
});

test.skipIf(process.env.LESSON_MUTANT==="1")("tryb wybiera plik .env.[tryb], który nadpisuje .env",()=>{
  expect(outputs.staging).toContain('"true"');
  expect(outputs.production).toContain('"false"');
  expect(outputs.staging).toContain('"staging"');
});

test.skipIf(process.env.LESSON_MUTANT==="1")("zmienna bez prefiksu VITE_ nie trafia do paczki",()=>{
  expect(outputs.production).not.toContain("haslo-bazy-lokalnej");
  expect(outputs.staging).not.toContain("haslo-bazy-lokalnej");
});`;
const exercises=[
['Znajdź w kodzie wartości wpisane na sztywno.','Adresy, klucze, flagi.','grep -rn "localhost\\|http\\|sk_" src/\nKażde trafienie oceń: adres API i flagi przenieś do zmiennych, a klucz prywatny przenieś na backend. Sam grep nie jest listą błędów: dokumentacyjne adresy w komentarzach możesz zostawić.'],
['Rozpisz pliki .env dla trzech środowisk.','Wspólne, tryb i lokalne nadpisania.','.env: wspólne domyślne, w repozytorium. .env.development: VITE_API_URL=/api przez proxy. .env.staging i .env.production: osobne adresy. .env.local i .env.*.local: lokalne, w .gitignore. Wartości publiczne mogą być w repozytorium, sekrety nigdy.'],
['Napisz moduł konfiguracji ze schematem Zod.','Reszta aplikacji importuje config, a nie import.meta.env.',config],
['Zamień flagę tekstową na boolean.','Tekst "false" jest w warunku prawdą.',config],
['Zatrzymaj aplikację czytelnym błędem konfiguracji.','Wypisz wszystkie problemy naraz.',config],
['Zamroź obiekt konfiguracji.','Konfiguracja nie powinna zmieniać się w trakcie działania.',config],
['Wyprowadź typ konfiguracji ze schematu.','Typ nie może rozjechać się z walidacją.',config],
['Zadeklaruj zmienne w ImportMetaEnv.','Chroni przed literówką w kodzie.',declaration],
['Wyjaśnij różnicę między deklaracją typów a walidacją.','Co sprawdza kompilator, a co działa na starcie?','vite-env.d.ts chroni przed literówką w kodzie, ale nie sprawdza, czy zmienna istnieje w pliku .env ani czy ma poprawną wartość. Schemat sprawdza wartości przy starcie w obu wersjach. W JSX to jedyna ochrona.'],
['Ukryj tablicę Kanban za flagą.','Flaga działa w menu i w routerze.',flagUse],
['Dodaj skrypt build:staging.','Tryb wybiera plik .env.staging.','"scripts":{"build":"vite build","build:staging":"vite build --mode staging"}\nZbuduj oba i porównaj adresy w dist. --mode nie zmienia NODE_ENV automatycznie: tryb staging nadal buduje wersję produkcyjną kodu, dopóki tego nie zmienisz.'],
['Sprawdź prawdziwym buildem, że adresy się różnią.','Test uruchamia vite build dla obu trybów.',buildTest],
['Sprawdź, że zmienna bez prefiksu nie trafia do paczki.','DB_PASSWORD jest tylko w środowisku budowania.',buildTest],
['Odróżnij, co wolno, a czego nie wolno umieszczać w kliencie.','Publiczne identyfikatory a klucze z uprawnieniami.','Wolno: adres API, DSN Sentry, publiczny klucz płatności, ID analityki, flagi. Nie wolno: hasła do baz, prywatne klucze API, sekrety JWT, tokeny repozytorium lub chmury. Funkcja wymagająca sekretu działa na backendzie.'],
['Przenieś wywołanie płatnego API na backend.','Przeglądarka woła tylko Spring Boot.','Przeglądarka wysyła POST /api/payments do Spring Boot, a ten woła dostawcę z kluczem ze zmiennej środowiskowej serwera lub menedżera sekretów. Klient nigdy nie widzi klucza. Backend sprawdza też uprawnienia użytkownika i limity.'],
['Napisz przegląd nazw zmiennych VITE.','Lista dozwolonych, a nie zabronionych.',scan],
['Wczytaj plik .env bez przypadkowych prototypów.','Object.create(null) chroni przed __proto__.',scan],
['Sprawdź, czy .env.example zawiera wszystkie zmienne.','Porównuj nazwy, nie wartości.',scan],
['Przygotuj .env.example.','Bez prawdziwych wartości, z opisami.',envExample],
['Przeszukaj gotową paczkę pod kątem sekretów.','Sprawdzaj dist, a nie tylko src.','grep -rE "secret|password|sk_live" dist/\nWynik ma być pusty, a fałszywe trafienia oceń ręcznie. Lepiej uruchomić to w CI. Włącz też GitHub secret scanning na repozytorium, bo chroni historię, której grep w dist nie obejmie.'],
['Reaguj na wyciek klucza z paczki.','Usunięcie z kodu nie wystarczy.','Unieważnij klucz i wygeneruj nowy. Kopie zostają w cache przeglądarek, CDN i historii git. Sprawdź logi użycia klucza, przenieś wywołanie na backend, dodaj skanowanie i opisz incydent. Samo przepisanie historii niczego nie cofnie.'],
['Napisz parser konfiguracji czasu działania.','Dane z zewnątrz są unknown.',runtime],
['Wczytaj config.json przed renderem.','cache:"no-store" chroni przed starą wartością.',runtime],
['Uzupełnij nagłówki pliku config.json.','Plik musi być zawsze świeży.','location = /config.json { add_header Cache-Control "no-cache"; }\nPlik nie ma hasha w nazwie, więc przy długim cache użytkownik dostałby stary adres API. Pobranie przy starcie to mały koszt, a wartość można też wstrzyknąć do index.html.'],
['Porównaj konfigurację z budowania i z czasu działania.','Jeden build na środowisko czy jeden obraz na wszystkie.','Wartości VITE_* są zapieczone, więc jeden build obsługuje jedno środowisko. Jest to proste i nie ma dodatkowego żądania. Config.json pozwala użyć jednego obrazu w wielu środowiskach: zmiana to restart kontenera, kosztem żądania przy starcie i obsługi jego błędu.'],
['Przetestuj błędy pliku config.json.','Pusty obiekt, null i 404.',tests],
['Przetestuj flagę tekstową.','Wartość spoza listy to błąd.',tests],
['Zaproponuj zasady dla nowej zmiennej w zespole.','Lista kroków do przeglądu.','1. Czy może być publiczna? 2. Dodaj do schematu, deklaracji i .env.example. 3. Podaj wartość dla każdego środowiska. 4. Uruchom build i skan. 5. Dla sekretu użyj backendu. Taką listę warto dopisać do opisu pull requesta.'],
['Znajdź błędy w tej konfiguracji.','Sprawdź prefiks, typ i miejsce przechowywania.','VITE_PAYMENTS_SECRET publikuje sekret. if(import.meta.env.VITE_FLAG) jest zawsze prawdą dla tekstu "false". .env.local w repozytorium ujawnia dane. Surowe import.meta.env rozsiane po komponentach utrudniają walidację i testy.'],
['Sprawdź kryterium akceptacji lekcji.','Sześć warunków.','Nie ma adresów ani kluczy na sztywno. Build staging i production różnią się adresem. Brak zmiennej zatrzymuje aplikację czytelnym błędem. Tablica działa przez flagę. Skan dist nic nie znajduje. Jest .env.example, a nie ma .env*.local.'],
];
const concepts=[
['Skąd Vite czyta zmienne?','Z plików .env w katalogu projektu','Z bazy danych','Z localStorage','Z cookie','Także z rzeczywistego środowiska procesu.'],
['Które zmienne trafią do kodu klienta?','Tylko z prefiksem VITE_','Wszystkie','Tylko bez prefiksu','Tylko z NODE_','Prefiks zabezpiecza przed przypadkowym ujawnieniem.'],
['Co oznacza prefiks VITE_?','Zgodę na publikację w paczce','Szyfrowanie','Ukrycie','Tylko tryb dev','To odwrotność ukrycia.'],
['Kiedy wartości są wstawiane do kodu?','Podczas budowania','Przy każdym żądaniu','Po wczytaniu strony','W Nginx','W pliku JS stoi tekst wartości.'],
['Co wymaga zmiana VITE_API_URL?','Ponownego builda','Restartu przeglądarki','Wyczyszczenia cookie','Niczego','Wartość jest zapieczona.'],
['Który plik jest lokalny i poza repozytorium?','.env.local','.env','.env.production','.env.example','Dodaj .env*.local do .gitignore.'],
['Który plik ma najwyższy priorytet dla trybu?','.env.[tryb].local','.env','.env.local','.env.[tryb]','Lokalne nadpisanie trybu wygrywa.'],
['Co robi flaga --mode staging?','Wczytuje .env.staging','Uruchamia serwer','Zmienia gałąź','Wyłącza minifikację','Wybiera zestaw plików.'],
['Jaki tryb ma npm run dev?','development','production','test','staging','Domyślny dla serwera deweloperskiego.'],
['Jaki tryb ma npm run build?','production','development','preview','staging','Domyślny dla budowania.'],
['Czym jest import.meta.env.PROD?','Wartością logiczną budowy produkcyjnej','Adresem produkcji','Tekstem','Trybem CI','Oraz DEV jako przeciwieństwo.'],
['Czym jest import.meta.env.MODE?','Nazwą trybu','Numerem wersji','Nazwą gałęzi','Typem pliku','np. staging.'],
['Czym jest BASE_URL?','Bazową ścieżką aplikacji','Adresem API','Adresem CDN zawsze','Adresem repozytorium','Wynika z opcji base.'],
['Czy --mode staging zmienia NODE_ENV?','Nie domyślnie','Tak zawsze','Tak, na test','Tak, na staging','Tryb i NODE_ENV to osobne pojęcia.'],
['Jaki typ mają zmienne środowiskowe?','Tekst','Boolean','Liczba','Dowolny JSON','„false” w warunku jest prawdą.'],
['Dlaczego if(import.meta.env.VITE_FLAG) jest pułapką?','Tekst "false" jest prawdziwy','Bo to błąd składni','Bo tak działa TS','Bo Vite usuwa flagi','Trzeba porównać lub transformować.'],
['Po co jeden moduł config?','Walidacja i typy w jednym miejscu','Mniejszy bundle zawsze','Żeby Vite działał','Dla CSS','Reszta aplikacji nie zna import.meta.env.'],
['Po co walidacja przy starcie?','Błąd wychodzi od razu i czytelnie','Żeby spowolnić','Żeby ukryć sekrety','Żeby zastąpić testy','Nie czekasz na awarię w połowie ścieżki.'],
['Co daje safeParse?','Wynik zamiast wyjątku','Automatyczną naprawę','Szyfrowanie','Cache','Pozwala złożyć własny komunikat.'],
['Co daje transform w Zod?','Zamienia wartość po walidacji','Usuwa błąd','Wyłącza typy','Zmienia plik env','Typ wyjścia to boolean.'],
['Co chroni ImportMetaEnv?','Przed literówką w kodzie','Przed brakiem zmiennej w pliku','Przed złą wartością','Przed wyciekiem','Tylko warstwa kompilacji.'],
['Gdzie deklarujesz ImportMetaEnv?','W vite-env.d.ts','W .env','W package.json','W main.tsx jako zmienna','Plik jest w zakresie tsconfig.'],
['Co jest publiczne?','Wszystko w paczce JS','Tylko pliki .env','Tylko README','Tylko index.html','DevTools pokazuje każdy plik.'],
['Czy minifikacja ukrywa wartości?','Nie','Tak','Tylko liczby','Tylko w produkcji','Tekst nadal stoi w pliku.'],
['Czy można trzymać DSN Sentry w kliencie?','Tak, to publiczny identyfikator','Nie','Tylko zaszyfrowany','Tylko w cookie','Ogranicz dozwolone domeny.'],
['Czy można trzymać klucz prywatny płatności w kliencie?','Nie','Tak, w VITE_','Tak, po minifikacji','Tak, w .env','Wywołanie robi backend.'],
['Czy publiczny klucz „publishable” może być w kliencie?','Tak, tak jest zaprojektowany','Nie','Tylko po szyfrowaniu','Tylko w cookie','Prywatny pozostaje na serwerze.'],
['Gdzie żyje sekret?','W zmiennych serwera lub menedżerze sekretów','W repozytorium','W localStorage','W JS','Nigdy w repozytorium frontendu.'],
['Co zrobić po wycieku klucza?','Unieważnić i wygenerować nowy','Tylko usunąć z kodu','Zmienić nazwę','Zminifikować ponownie','Kopie są w cache i historii.'],
['Czy przepisanie historii git usuwa wyciek?','Nie, klucz trzeba uznać za ujawniony','Tak','Tak, po force push','Tak, w prywatnym repo','Mogły istnieć kopie.'],
['Co daje secret scanning?','Wykrywa sekrety w repozytorium','Szyfruje .env','Zastępuje backend','Blokuje build zawsze','Warstwa kontrolna, nie gwarancja.'],
['Po co .env.example?','Lista zmiennych dla zespołu bez wartości','Dla Vite wymagany','Dla sekretów','Do uruchomienia produkcji','Nowa osoba wie, co ustawić.'],
['Czy .env.example może zawierać prawdziwy sekret?','Nie','Tak','Tak, w prywatnym repo','Tak, zakodowany base64','Base64 nie jest szyfrowaniem.'],
['Kiedy wybrać config.json?','Jeden obraz na wiele środowisk','Zawsze','Gdy brak serwera','Gdy jest jedna wersja','Kosztem dodatkowego żądania.'],
['Jaki nagłówek dla config.json?','Cache-Control: no-cache','max-age=31536000','immutable','Brak nagłówków','Plik zmienia się bez zmiany nazwy.'],
['Jak traktować wynik res.json() konfiguracji?','Jako unknown i walidować','Jako pewny typ','Jako any','Jako string','Plik może być nieaktualny.'],
['Co zrobić, gdy config.json się nie wczyta?','Pokazać jasny błąd startu','Użyć localhost','Zignorować','Odświeżać w pętli','Domyślny localhost w produkcji to pułapka.'],
['Czym różni się staging od produkcji?','Innym API i danymi, ale tym samym sposobem budowy','Innym językiem','Innym frameworkiem','Brakiem testów','Staging ma przypominać produkcję.'],
['Czy można dodać flagę bez usuwania kodu?','Tak, ale kod nadal trafia do paczki','Nie','Tak, i jest niewidoczny dla każdego','Tylko w Next','Flaga nie ukrywa kodu przed odczytem.'],
['Co zrobi tree shaking z if(false) po wstawieniu stałej?','Może usunąć martwy kod','Zawsze zostawi','Rzuci błąd','Zapisze do env','Wartość znana przy budowaniu to umożliwia.'],
['Czy flaga jest kontrolą uprawnień?','Nie, kontrolę robi backend','Tak','Tak, w TSX','Tak, po minifikacji','Klient można obejść.'],
['Skąd proxy Vite w trybie dev?','Opcja server.proxy, np. /api do Spring Boot','Z .env zawsze','Z przeglądarki','Z Nginx','Bez CORS podczas pracy.'],
['Dlaczego VITE_API_URL=/api lokalnie?','Korzysta z proxy i jednego źródła','Bo tak szybciej','Bo Vite tego wymaga','Bo CSS','Ciasteczka działają bez CORS.'],
['Jak uniknąć localhost w produkcji?','Wymagany schemat i osobne pliki trybów','Komentarz w kodzie','Pamięć zespołu','Ręczna podmiana przed wdrożeniem','Problem z lekcji to właśnie ręczna podmiana.'],
['Kto powinien nadawać zmienne w CI?','Sekrety platformy i ustawienia środowiska','Plik w repozytorium','E-mail','Komentarz w PR','Wartość publiczna też może być w CI.'],
['Co zrobić ze zmienną nieużywaną?','Usunąć z plików i schematu','Zostawić na zapas','Dodać drugą','Ukryć','Martwa konfiguracja myli.'],
['Czym jest loadEnv w vite.config?','Funkcją czytającą zmienne w konfiguracji','Komponentem','Zapytaniem HTTP','Pluginem CSS','Pozwala użyć także zmiennych bez prefiksu w samej konfiguracji.'],
['Czy zmienna bez prefiksu jest widoczna w vite.config?','Tak, przez loadEnv','Nie','Tylko w CI','Tylko po prefiksie','Ale nie trafia do klienta sama.'],
['Co oznacza envPrefix?','Opcja zmieniająca prefiks','Nazwę trybu','Nazwę pliku','Nagłówek HTTP','Rozszerzanie jej ryzykuje wyciek.'],
['Czym grozi envPrefix:""?','Wszystkie zmienne mogą trafić do paczki','Niczym','Wolniejszym buildem','Błędem kompilacji','Vite ostrzega przed tym ustawieniem.'],
['Jak test sprawdza prawdziwy efekt builda?','Uruchamia vite build i czyta dist','Mockuje Vite','Czyta tylko .env','Sprawdza typy','To dowód, a nie założenie.'],
['Dlaczego testy czytają dist po buildzie?','Tam jest to, co dostanie użytkownik','Bo src nie istnieje','Bo Vitest tak wymaga','Bo CSS','Liczy się publikowany plik.'],
['Co oznacza Object.freeze na configu?','Blokuje przypisanie na najwyższym poziomie','Szyfruje','Kopiuje głęboko','Usuwa typy','Zagnieżdżony obiekt zamroź osobno.'],
['Czemu Object.create(null) w parserze env?','Brak dziedziczonych pól jak __proto__','Szybszy zapis','Wymóg TS','Bez powodu','Dane z pliku nie zmienią prototypu.'],
['Co zrobić z pustym VITE_SENTRY_DSN w config.json?','Zamienić pusty tekst na undefined','Zostawić jako adres','Rzucić zawsze','Dodać localhost','Szablon może dać pustą wartość.'],
['Czym różni się optional od default w Zod?','optional daje undefined, default wstawia wartość','Niczym','default usuwa pole','optional wymusza pole','Każde ma inne przeznaczenie.'],
['Gdzie sprawdzisz typ uzyskany ze schematu?','Przez z.output lub ReturnType','W CSS','W DevTools','W .env','Typ wyjścia uwzględnia transform.'],
['Czy można mieć różne DSN dla staging i produkcji?','Tak, plikami trybów lub CI','Nie','Tylko w Next','Tylko jeden globalny','Rozdzielenie ułatwia filtrowanie.'],
['Co jest najlepszym testem flagi?','Sprawdzić obie wartości i wartość błędną','Tylko true','Tylko typ','Nic','Tekst „tak” też powinien zostać odrzucony.'],
['Jakie jest główne ryzyko flag długożyjących?','Rozgałęzienia i martwy kod','Większy CSS','Brak HMR','Brak testów z definicji','Usuwaj flagi po wdrożeniu funkcji.'],
];
const header=String.raw`import {parseConfig} from "./config";
import {parseRuntime} from "./runtimeConfig";
import {missingFromExample,parseEnvFile,reviewEnvNames} from "./envReview";
import {navigation} from "./navigation";
const base={VITE_API_URL:"/api"};
const err=(work:()=>unknown)=>{try{work();return "brak";}catch(error){return (error as Error).message.split("\n")[1]??(error as Error).message;}};`;
const probeRows=[
['Jaka jest domyślna nazwa aplikacji?','parseConfig(base).appName','DevDesk','Wartość domyślna schematu.',['undefined','VITE_APP_NAME','brak']],
['Jaki adres API po odczycie?','parseConfig(base).apiUrl','/api','Bez zmian.',['undefined','/','http://localhost']],
['Jaki tryb bez MODE?','parseConfig(base).mode','unknown','Model nie zgaduje trybu.',['development','production','undefined']],
['Czy isProd bez PROD jest fałszem?','parseConfig(base).isProd',false,'Tylko jawne true.'],
['Czy tekst "true" dla PROD daje isProd?','parseConfig({...base,PROD:"true" as unknown as boolean}).isProd',false,'Wymagamy wartości logicznej.'],
['Domyślna flaga tablicy','parseConfig(base).features.board',false,'Funkcja domyślnie wyłączona.'],
['Flaga "true"','parseConfig({...base,VITE_FEATURE_BOARD:"true"}).features.board',true,'Transform daje boolean.'],
['Flaga "false"','parseConfig({...base,VITE_FEATURE_BOARD:"false"}).features.board',false,'To nie jest prawda tekstowa.'],
['Typ wartości flagi','typeof parseConfig(base).features.board','boolean','Nie tekst.',['string','undefined','object']],
['Tekst "false" w warunku JS','Boolean("false")',true,'Niepusty tekst jest prawdą. Stąd transform.'],
['Błąd dla braku API','err(()=>parseConfig({}))','- VITE_API_URL: Brak VITE_API_URL','Błąd wskazuje zmienną.',['brak','Brak','VITE_API_URL']],
['Błąd dla pustego API','err(()=>parseConfig({VITE_API_URL:""}))','- VITE_API_URL: Brak VITE_API_URL','Pusty tekst jest tak samo zły.',['brak','Brak','VITE_API_URL']],
['Czy poprawny DSN przechodzi?','err(()=>parseConfig({...base,VITE_SENTRY_DSN:"https://k@o1.sentry.example/1"}))','brak','To poprawny adres.',['- VITE_SENTRY_DSN: Invalid','Brak','VITE_SENTRY_DSN']],
['Czy niepoprawny DSN przechodzi?','err(()=>parseConfig({...base,VITE_SENTRY_DSN:"x"}))==="brak"',false,'Zły adres wyjdzie przy starcie.'],
['Czy config jest zamrożony?','Object.isFrozen(parseConfig(base))',true,'Nikt nie zmieni go w trakcie.'],
['Czy zagnieżdżone features są zamrożone?','Object.isFrozen(parseConfig(base).features)',true,'Freeze jest płytki, więc wołamy go osobno.'],
['Czy zapis do zamrożonego pola rzuci w module?','(()=>{const c=parseConfig(base) as {apiUrl:string};try{c.apiUrl="x";}catch{return "wyjątek";}return c.apiUrl;})()','wyjątek','Moduły ES są w trybie ścisłym.',['x','/api','undefined']],
['Ile pozycji menu bez flagi?','navigation(parseConfig(base)).length',1,'Bez tablicy.'],
['Ile pozycji menu z flagą?','navigation(parseConfig({...base,VITE_FEATURE_BOARD:"true"})).length',2,'Z tablicą.'],
['Adres drugiej pozycji','navigation(parseConfig({...base,VITE_FEATURE_BOARD:"true"}))[1].to','/board','Flaga działa w menu.',['/tickets','undefined','/']],
['Adres w config.json','parseRuntime({apiUrl:"/x"}).apiUrl','/x','Prosty odczyt.',['undefined','/','null']],
['Pusty DSN w config.json','String(parseRuntime({apiUrl:"/x",sentryDsn:""}).sentryDsn)','undefined','Szablon może dać pustą wartość.',['pusty tekst','null','false']],
['Czy pusty obiekt jest błędem?','err(()=>parseRuntime({}))==="brak"',false,'apiUrl jest wymagany.'],
['Czy null jest błędem?','err(()=>parseRuntime(null))==="brak"',false,'Plik może być pusty.'],
['Czy tablica jest błędem?','err(()=>parseRuntime([]))==="brak"',false,'To nie ten kształt.'],
['Czy nadmiarowe pole przeszkadza?','err(()=>parseRuntime({apiUrl:"/x",inne:1}))','brak','z.object domyślnie je usuwa.',['Błędny config.json: inne','inne','wyjątek']],
['Przegląd VITE_API_URL','reviewEnvNames(["VITE_API_URL"]).length',0,'Znana zmienna.'],
['Przegląd DB_PASSWORD','reviewEnvNames(["DB_PASSWORD"]).length',0,'Bez prefiksu nie trafia do klienta.'],
['Przegląd VITE_DB_PASSWORD','reviewEnvNames(["VITE_DB_PASSWORD"])[0]','VITE_DB_PASSWORD: sekret w paczce klienta','Prefiks publikuje wartość.',['brak','VITE_DB_PASSWORD: nieznana zmienna publiczna','DB_PASSWORD']],
['Przegląd VITE_STRIPE_API_KEY','reviewEnvNames(["VITE_STRIPE_API_KEY"])[0]','VITE_STRIPE_API_KEY: sekret w paczce klienta','Klucz API.',['brak','VITE_STRIPE_API_KEY: nieznana zmienna publiczna','STRIPE']],
['Przegląd VITE_NEW_THING','reviewEnvNames(["VITE_NEW_THING"])[0]','VITE_NEW_THING: nieznana zmienna publiczna','Lista dozwolonych wymaga decyzji.',['brak','VITE_NEW_THING: sekret w paczce klienta','NEW_THING']],
['Przegląd VITE_FEATURE_BOARD','reviewEnvNames(["VITE_FEATURE_BOARD"]).length',0,'Flagi mają wzorzec.'],
['Odczyt prostej pary','parseEnvFile("A=1").A','1','Wartość to tekst.',['A','undefined','A=1']],
['Znak równości w wartości','parseEnvFile("URL=a=b").URL','a=b','Dzielimy na pierwszym znaku.',['a','b','URL']],
['Komentarz jest pomijany','Object.keys(parseEnvFile("# A=1")).length',0,'Wiersz zaczyna się od #.'],
['Pusty wiersz jest pomijany','Object.keys(parseEnvFile("\\n\\n")).length',0,'Brak danych.'],
['Wiersz bez = jest pomijany','Object.keys(parseEnvFile("ABC")).length',0,'Niepoprawny wpis.'],
['Spacje wokół są usuwane','parseEnvFile(" A = 1 ").A','1','Trim obu części.',[' 1 ','1 ','undefined']],
['Brak w przykładzie','missingFromExample("A=\\n",["A","B"])[0]','B','Porównanie nazw.',['A','brak','undefined']],
['Komplet w przykładzie','missingFromExample("A=\\nB=\\n",["A","B"]).length',0,'Brak braków.'],
];
const project={
'src/setup.ts':nodeSafeSetup,'src/config.ts':config,'src/runtimeConfig.ts':runtime,'src/envReview.ts':scan,'src/navigation.ts':flagUse,'src/config.test.ts':tests,'src/build.test.ts':buildTest,
'fixture/index.html':appIndex,'fixture/main.ts':appMain,...envFiles,'.env.example':envExample,
'README.md':'DevDesk: konfiguracja. npm install, npm test. build.test.ts uruchamia prawdziwy vite build dla trybów staging i production i czyta wynik. Wartości w katalogu fixture są przykładowe i nie są prawdziwymi danymi.\n',
};
const mutants=[
{file:'src/config.ts',before:'.transform(value=>value==="true")',after:'.transform(value=>Boolean(value))'},
{file:'src/config.ts',before:'.min(1,"Brak VITE_API_URL")',after:''},
{file:'src/config.ts',before:'features:Object.freeze({board:parsed.data.VITE_FEATURE_BOARD})',after:'features:{board:parsed.data.VITE_FEATURE_BOARD}'},
{file:'src/config.ts',before:'isProd:env.PROD===true',after:'isProd:Boolean(env.PROD)||true'},
{file:'src/runtimeConfig.ts',before:'.transform(value=>value||undefined)',after:''},
{file:'src/runtimeConfig.ts',before:'if(!response.ok)throw new Error("Brak config.json: "+response.status);',after:''},
{file:'src/envReview.ts',before:'if(SECRET_NAME.test(name))return [name+": sekret w paczce klienta"];',after:''},
{file:'src/envReview.ts',before:'if(!PUBLIC.test(name))return [name+": nieznana zmienna publiczna"];',after:''},
{file:'src/envReview.ts',before:'Object.create(null)',after:'{}'},
{file:'src/navigation.ts',before:'config.features.board?',after:'true?'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
