const {lesson,nodeSafeSetup}=require('./practice_kit.cjs');
const policy=String.raw`const HASHED=/^\/assets\/.+-[A-Za-z0-9_-]{6,}\.[a-z0-9]+$/;

export function isHashedAsset(path:string){
  return HASHED.test(path);
}

export function cacheControl(path:string){
  if(isHashedAsset(path))return "public, max-age=31536000, immutable";
  if(path==="/"||path.endsWith(".html"))return "no-cache";
  if(path==="/version.json"||path==="/config.json"||path==="/sw.js")return "no-cache";
  return "public, max-age=3600";
}

export type Decision=
  |{kind:"file";path:string}
  |{kind:"spa"}
  |{kind:"proxy"}
  |{kind:"notFound"};

// Kolejność jest istotna: API, potem prawdziwe pliki, a na końcu fallback dla tras aplikacji.
export function decide(path:string,files:ReadonlySet<string>):Decision{
  if(path==="/api"||path.startsWith("/api/"))return {kind:"proxy"};
  if(files.has(path))return {kind:"file",path};
  if(path.startsWith("/assets/"))return {kind:"notFound"};
  if(/\.[a-z0-9]+$/i.test(path))return {kind:"notFound"};
  return {kind:"spa"};
}

export function normalizeBase(base:string){
  const withStart=base.startsWith("/")?base:"/"+base;
  return withStart.endsWith("/")?withStart:withStart+"/";
}

export function assetUrl(base:string,file:string){
  return normalizeBase(base)+file.replace(/^\/+/,"");
}`;
const server=String.raw`import {createServer,type Server} from "node:http";
import {existsSync,readFileSync,readdirSync,statSync} from "node:fs";
import {extname,join,normalize,relative,sep} from "node:path";
import {cacheControl,decide} from "./deployPolicy";

const TYPES:Record<string,string>={".html":"text/html; charset=utf-8",".js":"text/javascript",".css":"text/css",".json":"application/json",".svg":"image/svg+xml"};

function listFiles(root:string,dir=root):string[]{
  return readdirSync(dir).flatMap(name=>{
    const path=join(dir,name);
    return statSync(path).isDirectory()?listFiles(root,path):["/"+relative(root,path).split(sep).join("/")];
  });
}

// Mały serwer do lekcji pokazuje zasadę. Na produkcji tę rolę ma Nginx lub platforma.
export function serveDist(root:string):Server{
  const files=new Set(listFiles(root));
  return createServer((request,response)=>{
    const path=decodeURIComponent(new URL(request.url??"/","http://x").pathname);
    const decision=decide(path,files);
    if(decision.kind==="proxy"){
      response.writeHead(502,{"Content-Type":"application/json"});
      response.end(JSON.stringify({error:"Brak backendu w tej lekcji"}));
      return;
    }
    if(decision.kind==="notFound"){
      response.writeHead(404,{"Content-Type":"text/plain; charset=utf-8"});
      response.end("Nie znaleziono");
      return;
    }
    const target=decision.kind==="file"?decision.path:"/index.html";
    const full=normalize(join(root,target));
    if(!full.startsWith(normalize(root))||!existsSync(full)){
      response.writeHead(404);
      response.end();
      return;
    }
    response.writeHead(200,{
      "Content-Type":TYPES[extname(full)]??"application/octet-stream",
      "Cache-Control":cacheControl(target),
      "X-Content-Type-Options":"nosniff",
    });
    response.end(readFileSync(full));
  });
}`;
const redirects=String.raw`/api/*   https://api.devdesk.example/api/:splat   200
/*       /index.html                              200`;
const headersFile=String.raw`/assets/*
  Cache-Control: public, max-age=31536000, immutable
/index.html
  Cache-Control: no-cache
/*
  X-Content-Type-Options: nosniff
  Referrer-Policy: strict-origin-when-cross-origin`;
const vercel=JSON.stringify({rewrites:[{source:"/((?!api/).*)",destination:"/index.html"}],headers:[{source:"/assets/(.*)",headers:[{key:"Cache-Control",value:"public, max-age=31536000, immutable"}]}]},null,2);
const chunk=String.raw`export const RELOAD_KEY="devdesk:chunk-reload";
export const RELOAD_WINDOW=10_000;

type Deps={storage:Pick<Storage,"getItem"|"setItem">;reload:()=>void;now?:()=>number};

// Przeładowanie to ostatnia linia obrony: ochrona przed pętlą przekazuje drugi błąd do granicy.
export function handleChunkError(event:Pick<Event,"preventDefault">,{storage,reload,now=Date.now}:Deps){
  const last=Number(storage.getItem(RELOAD_KEY)??0);
  if(now()-last<RELOAD_WINDOW)return false;
  event.preventDefault();
  storage.setItem(RELOAD_KEY,String(now()));
  reload();
  return true;
}

export function installChunkHandler(deps:Deps,target:Pick<Window,"addEventListener"|"removeEventListener">=window){
  const listener=(event:Event)=>{handleChunkError(event,deps);};
  target.addEventListener("vite:preloadError",listener);
  return ()=>target.removeEventListener("vite:preloadError",listener);
}`;
const banner=String.raw`import {useEffect,useState} from "react";

export function isNewer(current:string|undefined,remote:unknown){
  return Boolean(current)&&typeof remote==="string"&&remote!==current;
}

type Props={current?:string;interval?:number;onReload?:()=>void;fetcher?:typeof fetch};

export function NewVersionBanner({current,interval=5*60_000,onReload=()=>window.location.reload(),fetcher=fetch}:Props){
  const [available,setAvailable]=useState(false);

  useEffect(()=>{
    if(!current)return;
    const id=setInterval(async()=>{
      try{
        const response=await fetcher("/version.json",{cache:"no-store"});
        const body=await response.json() as {version?:unknown};
        if(isNewer(current,body.version))setAvailable(true);
      }catch{
        // Brak sieci: spróbujemy przy następnym sprawdzeniu.
      }
    },interval);
    return ()=>clearInterval(id);
  },[current,interval,fetcher]);

  if(!available)return null;
  return (
    <div role="status">
      Dostępna jest nowa wersja DevDesk.
      <button type="button" onClick={onReload}>Odśwież</button>
    </div>
  );
}`;
const appMain=String.raw`const page=await import("./lazyPage");
document.body.append(page.message);
export {};`;
const appPage=String.raw`export const message="Ekran ładowany osobno";`;
const appIndex=String.raw`<!doctype html><html lang="pl"><head><meta charset="utf-8"/><title>DevDesk</title></head><body><div id="root"></div><script type="module" src="/main.ts"></script></body></html>`;
const tests=String.raw`import {afterEach,beforeEach,expect,test,vi} from "vitest";
import {act,fireEvent,render,screen} from "@testing-library/react";
import {assetUrl,cacheControl,decide,isHashedAsset,normalizeBase} from "./deployPolicy";
import {handleChunkError,installChunkHandler,RELOAD_KEY} from "./chunkErrors";
import {isNewer,NewVersionBanner} from "./NewVersionBanner";

const files=new Set(["/index.html","/assets/index-3f2a9cd1.js","/favicon.svg"]);

function memoryStorage(){
  const map=new Map<string,string>();
  return {getItem:(key:string)=>map.get(key)??null,setItem:(key:string,value:string)=>{map.set(key,value);}};
}

test("nagłówki cache: hash na rok, index bez cache",()=>{
  expect(cacheControl("/assets/index-3f2a9cd1.js")).toBe("public, max-age=31536000, immutable");
  expect(cacheControl("/index.html")).toBe("no-cache");
  expect(cacheControl("/")).toBe("no-cache");
  expect(cacheControl("/version.json")).toBe("no-cache");
  expect(cacheControl("/favicon.svg")).toBe("public, max-age=3600");
  expect(isHashedAsset("/assets/logo.svg")).toBe(false);
});

test("decyzja serwera dla plików, API i tras",()=>{
  expect(decide("/assets/index-3f2a9cd1.js",files)).toEqual({kind:"file",path:"/assets/index-3f2a9cd1.js"});
  expect(decide("/tickets/42",files)).toEqual({kind:"spa"});
  expect(decide("/api/tickets",files)).toEqual({kind:"proxy"});
  expect(decide("/assets/stare-aaaaaaaa.js",files)).toEqual({kind:"notFound"});
  expect(decide("/brak.png",files)).toEqual({kind:"notFound"});
  expect(decide("/assets/bez-rozszerzenia",files)).toEqual({kind:"notFound"});
  expect(decide("/apis",files)).toEqual({kind:"spa"});
});

test("base i adresy zasobów",()=>{
  expect(normalizeBase("devdesk")).toBe("/devdesk/");
  expect(normalizeBase("/devdesk/")).toBe("/devdesk/");
  expect(assetUrl("/devdesk/","/logo.svg")).toBe("/devdesk/logo.svg");
  expect(assetUrl("/","logo.svg")).toBe("/logo.svg");
});

test("błąd kawałka kodu przeładowuje raz i chroni przed pętlą",()=>{
  const storage=memoryStorage(),reload=vi.fn(),event={preventDefault:vi.fn()};
  let time=100_000;
  const deps={storage,reload,now:()=>time};
  expect(handleChunkError(event,deps)).toBe(true);
  expect(reload).toHaveBeenCalledOnce();
  expect(event.preventDefault).toHaveBeenCalledOnce();
  expect(storage.getItem(RELOAD_KEY)).toBe("100000");
  time+=5_000;
  expect(handleChunkError(event,deps)).toBe(false);
  expect(reload).toHaveBeenCalledOnce();
  time+=10_000;
  expect(handleChunkError(event,deps)).toBe(true);
  expect(reload).toHaveBeenCalledTimes(2);
});

test("nasłuch reaguje na zdarzenie Vite i daje się odłączyć",()=>{
  const reload=vi.fn();
  let time=100_000;
  const stop=installChunkHandler({storage:memoryStorage(),reload,now:()=>time});
  window.dispatchEvent(new Event("vite:preloadError"));
  expect(reload).toHaveBeenCalledOnce();
  stop();
  time+=60_000; // poza oknem ochrony: gdyby nasłuch został, wywołałby reload
  window.dispatchEvent(new Event("vite:preloadError"));
  expect(reload).toHaveBeenCalledOnce();
});

describe_banner();
function describe_banner(){
  beforeEach(()=>{vi.useFakeTimers();});
  afterEach(()=>{vi.useRealTimers();});

  const remote=(version:unknown)=>vi.fn(async()=>new Response(JSON.stringify({version}))) as unknown as typeof fetch;

  test("baner pojawia się dopiero po wykryciu innej wersji",async()=>{
    const fetcher=remote("2.0");
    render(<NewVersionBanner current="1.0" interval={1000} fetcher={fetcher}/>);
    expect(screen.queryByRole("status")).toBeNull();
    await act(async()=>{await vi.advanceTimersByTimeAsync(1000);});
    expect(screen.getByRole("status")).toHaveTextContent("nowa wersja");
    expect(fetcher).toHaveBeenCalledWith("/version.json",{cache:"no-store"});
  });

  test("ta sama wersja i błąd sieci nie pokazują banera",async()=>{
    const same=render(<NewVersionBanner current="1.0" interval={1000} fetcher={remote("1.0")}/>);
    await act(async()=>{await vi.advanceTimersByTimeAsync(1000);});
    expect(screen.queryByRole("status")).toBeNull();
    same.unmount();
    const failing=vi.fn(async()=>{throw new TypeError("offline");}) as unknown as typeof fetch;
    render(<NewVersionBanner current="1.0" interval={1000} fetcher={failing}/>);
    await act(async()=>{await vi.advanceTimersByTimeAsync(1000);});
    expect(screen.queryByRole("status")).toBeNull();
    expect(failing).toHaveBeenCalled();
  });

  test("baner nie przeładowuje sam, tylko po kliknięciu",async()=>{
    const onReload=vi.fn();
    render(<NewVersionBanner current="1.0" interval={1000} fetcher={remote("2.0")} onReload={onReload}/>);
    await act(async()=>{await vi.advanceTimersByTimeAsync(1000);});
    expect(onReload).not.toHaveBeenCalled();
    fireEvent.click(screen.getByRole("button",{name:"Odśwież"}));
    expect(onReload).toHaveBeenCalledOnce();
  });

  test("brak wersji bieżącej wyłącza sprawdzanie, a odmontowanie czyści licznik",async()=>{
    const fetcher=remote("2.0");
    const none=render(<NewVersionBanner interval={1000} fetcher={fetcher}/>);
    await act(async()=>{await vi.advanceTimersByTimeAsync(5000);});
    expect(fetcher).not.toHaveBeenCalled();
    none.unmount();
    const view=render(<NewVersionBanner current="1.0" interval={1000} fetcher={fetcher}/>);
    view.unmount();
    await vi.advanceTimersByTimeAsync(5000);
    expect(fetcher).not.toHaveBeenCalled();
  });

  test("porównanie wersji",()=>{
    expect(isNewer("1.0","2.0")).toBe(true);
    expect(isNewer("1.0","1.0")).toBe(false);
    expect(isNewer(undefined,"2.0")).toBe(false);
    expect(isNewer("1.0",undefined)).toBe(false);
  });
}`;
const distTest=String.raw`// @vitest-environment node
import {mkdtempSync,readdirSync,readFileSync,rmSync,writeFileSync} from "node:fs";
import {join,resolve} from "node:path";
import {tmpdir} from "node:os";
import type {AddressInfo} from "node:net";
import type {Server} from "node:http";
import {build} from "vite";
import {afterAll,beforeAll,expect,test} from "vitest";
import {serveDist} from "./serveDist";
import {isHashedAsset} from "./deployPolicy";

const skip=process.env.LESSON_MUTANT==="1";
const outDir=mkdtempSync(join(tmpdir(),"devdesk-dist-"));
const baseDir=mkdtempSync(join(tmpdir(),"devdesk-base-"));
let server:Server,origin="",js:string[]=[];

beforeAll(async()=>{
  if(skip)return;
  const previous=process.env.NODE_ENV;
  await build({root:resolve(process.cwd(),"fixture"),logLevel:"silent",configFile:false,build:{outDir,emptyOutDir:true,target:"es2022"}});
  await build({root:resolve(process.cwd(),"fixture"),base:"/devdesk/",logLevel:"silent",configFile:false,build:{outDir:baseDir,emptyOutDir:true,target:"es2022"}});
  process.env.NODE_ENV=previous;
  writeFileSync(join(outDir,"version.json"),JSON.stringify({version:"1.0"}));
  js=readdirSync(join(outDir,"assets")).filter(name=>name.endsWith(".js"));
  server=serveDist(outDir);
  await new Promise<void>(done=>server.listen(0,"127.0.0.1",done));
  origin="http://127.0.0.1:"+(server.address() as AddressInfo).port;
},120000);

afterAll(async()=>{
  if(!skip)await new Promise(done=>server.close(done));
  rmSync(outDir,{recursive:true,force:true});
  rmSync(baseDir,{recursive:true,force:true});
});

test.skipIf(skip)("build tworzy pliki z hashem i osobny kawałek",()=>{
  expect(js.length).toBeGreaterThanOrEqual(2);
  for(const name of js)expect(isHashedAsset("/assets/"+name)).toBe(true);
  expect(readFileSync(join(outDir,"index.html"),"utf8")).toContain("/assets/");
});

test.skipIf(skip)("base dodaje prefiks ścieżek w index.html",()=>{
  expect(readFileSync(join(baseDir,"index.html"),"utf8")).toContain('src="/devdesk/assets/');
});

test.skipIf(skip)("odświeżenie trasy zwraca index.html bez cache",async()=>{
  const response=await fetch(origin+"/tickets/42");
  expect(response.status).toBe(200);
  expect(response.headers.get("cache-control")).toBe("no-cache");
  expect(await response.text()).toContain('<div id="root">');
});

test.skipIf(skip)("plik z hashem ma cache na rok",async()=>{
  const response=await fetch(origin+"/assets/"+js[0]);
  expect(response.status).toBe(200);
  expect(response.headers.get("cache-control")).toContain("immutable");
  expect(response.headers.get("content-type")).toContain("javascript");
});

test.skipIf(skip)("brakujący plik i API nie dostają index.html",async()=>{
  const missing=await fetch(origin+"/assets/stare-aaaaaaaa.js");
  expect(missing.status).toBe(404);
  expect(await missing.text()).not.toContain("<html");
  const api=await fetch(origin+"/api/tickets");
  expect(api.status).toBe(502);
  expect(await api.text()).not.toContain("<html");
});

test.skipIf(skip)("version.json jest świeży",async()=>{
  const response=await fetch(origin+"/version.json");
  expect(response.headers.get("cache-control")).toBe("no-cache");
  expect(await response.json()).toEqual({version:"1.0"});
});`;
const exercises=[
['Obejrzyj wynik vite build i opisz, co powstało.','index.html, assets z hashem i public.','dist/index.html wskazuje aktualne pliki. assets/index-3f2a9c.js i assets/TicketDetails-8b1e.js mają hash treści, więc zmiana kodu to nowa nazwa. Obrazy importowane w kodzie też dostają hash, a zawartość public/ jest kopiowana bez zmian.'],
['Wyjaśnij 404 przy odświeżeniu /tickets/42.','Kto obsługuje adres przy kliknięciu, a kto przy odświeżeniu?','Kliknięcie linku zmienia adres w przeglądarce bez pytania serwera. Odświeżenie wysyła GET /tickets/42, a takiego pliku w dist nie ma. Serwer musi dla tras aplikacji zwrócić index.html ze statusem 200, a router wybierze ekran.'],
['Napisz decyzję serwera dla żądania.','Kolejność: API, plik, assets, fallback.',policy],
['Zwróć prawdziwe 404 dla brakującego pliku z assets.','index.html zamiast JS daje „Unexpected token <”.',policy],
['Napisz politykę nagłówków cache.','Hash na rok, reszta zależnie od typu.',policy],
['Rozpoznaj plik z hashem w nazwie.','Tylko te pliki mogą być immutable.',policy],
['Dodaj _redirects dla Cloudflare Pages lub Netlify.','API przed fallbackiem.',redirects],
['Dodaj _headers dla tych platform.','Reguły dla assets i index.html.',headersFile],
['Skonfiguruj Vercel przez vercel.json.','Wykluczenie api/ z przepisywania.',vercel],
['Przetestuj prawdziwy build z serwerem HTTP.','Test wykonuje żądania i czyta nagłówki.',distTest],
['Napisz mały serwer plików dla testu.','Pokazuje zasadę, a nie zastępuje Nginx.',server],
['Zabezpiecz serwer przed wyjściem poza katalog.','Sprawdź, czy pełna ścieżka zaczyna się od katalogu dist.',server],
['Ustaw base dla aplikacji pod podścieżką.','Vite dodaje prefiks w index.html.','// vite.config.ts\nexport default defineConfig({base:"/devdesk/"});\n// Router: createBrowserRouter(routes,{basename:import.meta.env.BASE_URL});\n// Zasoby z public: import.meta.env.BASE_URL+"logo.svg", a nie "/logo.svg".'],
['Napisz normalizację base i adresu zasobu.','Ukośniki łatwo pomylić.',policy],
['Obsłuż vite:preloadError.','Stary kawałek zniknął po wdrożeniu.',chunk],
['Chroń przeładowanie przed pętlą.','Drugi błąd w krótkim czasie trafia do granicy.',chunk],
['Przetestuj ochronę przed pętlą z kontrolowanym czasem.','Przekaż funkcję now w zależnościach.',tests],
['Zaproponuj strategię plików po wdrożeniu.','Stare assets nie muszą znikać od razu.','Zostaw stare pliki assets przez kilka dni: otwarte karty nadal je pobiorą. Platformy statyczne robią to automatycznie, a przy własnym serwerze kopiuj nowe pliki bez usuwania starych. Przeładowanie to tylko ostatnia linia obrony.'],
['Dodaj baner nowej wersji.','Nie przeładowuj bez pytania, bo formularz może stracić dane.',banner],
['Wygeneruj version.json w skrypcie builda.','Plik musi mieć tę samą wersję co aplikacja.','"build":"vite build && node scripts/write-version.mjs"\n// write-version.mjs zapisuje dist/version.json z {version:process.env.VITE_APP_VERSION}.\n// Wartość VITE_APP_VERSION pochodzi np. z commita w CI i trafia też do kodu klienta.'],
['Przetestuj baner bez czekania pięciu minut.','Fałszywe liczniki i atrapa fetch.',tests],
['Sprawdź, że błąd sieci nie psuje banera.','catch czeka na następną próbę.',tests],
['Połącz repozytorium z platformą statyczną.','Komenda, katalog, wersja Node, zmienne.','Komenda: npm run build. Katalog: dist. Wersja Node zgodna z lokalną. Zmienne VITE_* osobno dla produkcji i podglądów: podgląd łączy się z API testowym. Każdy pull request dostanie własny adres, a każde wdrożenie jest niezmienne.'],
['Wykonaj i opisz rollback.','Sprawdź to, zanim będzie potrzebne.','Wybierz poprzednie wdrożenie w panelu i ustaw je jako produkcyjne, potem wróć. Sprawdź wersję w version.json. Zmiana kontraktu API może utrudnić powrót, więc backend powinien przez pewien czas obsługiwać obie wersje.'],
['Sprawdź nagłówki w DevTools.','Network, kolumna Cache-Control.','Otwórz Network przy twardym odświeżeniu. Sprawdź assets/*: immutable, oraz index.html: no-cache. Status 200 (from disk cache) dla assets po zwykłym odświeżeniu potwierdza działanie. Przy CDN sprawdź też nagłówek wskazujący trafienie w cache.'],
['Popraw nagłówki z no-cache na wszystkim.','Jaki jest koszt?','Każde wejście pobiera wtedy megabajty kodu albo przynajmniej waliduje każdy plik. Ustaw immutable dla plików z hashem i zostaw no-cache dla index.html oraz plików bez hasha. Podobnie zły jest długi cache dla index.html.'],
['Wyjaśnij skutki cache na index.html.','Co wskazuje stary index?','Stary index.html wskazuje stare pliki JS. Najlepszy przypadek to stara wersja, a najgorszy biały ekran po usunięciu tych plików. Użytkownicy zgłaszają różne wersje naraz, a przyczyna jest trudna do powtórzenia bez czyszczenia cache.'],
['Sprawdź podgląd przed wdrożeniem.','npm run preview serwuje dist.','npm run build, potem npm run preview. Wejdź na /tickets/42 i odśwież, sprawdź konsolę i Network. Preview nie zastępuje konfiguracji produkcyjnej serwera, bo ma własny fallback: reguły platformy sprawdź w pull requeście.'],
['Dodaj testy dymne po wdrożeniu.','Kilka żądań po każdym wdrożeniu.','GET / zwraca 200 i HTML. GET /tickets/42 też. GET /api/health zwraca JSON. Plik assets z index.html ma 200 i immutable. Nieistniejący /assets/x.js ma 404. Taki skrypt w CI wykryje złe reguły przed użytkownikami.'],
['Sprawdź kryterium akceptacji lekcji.','Odświeżanie, API, cache, podgląd, wersja.','Odświeżenie dowolnej trasy działa. /api/* nie zwraca index.html. Nagłówki spełniają zasadę hash na rok, index no-cache. Każdy pull request ma podgląd. Użytkownik starej wersji dostaje propozycję odświeżenia zamiast białego ekranu.'],
];
const concepts=[
['Co tworzy vite build?','Statyczny katalog dist','Serwer Node','Obraz Dockera','Bazę danych','Pliki może serwować dowolny serwer HTTP.'],
['Po co hash w nazwie pliku?','Zmiana treści zmienia nazwę, więc można długo cache’ować','Dla bezpieczeństwa','Dla SEO','Dla typów','Stary plik nigdy się nie zmienia.'],
['Co musi być pobierane świeżo?','index.html','Pliki z hashem','Czcionki z hashem','Mapy źródeł','To on wskazuje aktualne nazwy.'],
['Co robi npm run preview?','Serwuje dist lokalnie','Publikuje aplikację','Buduje obraz','Uruchamia testy','Sprawdzasz build przed wdrożeniem.'],
['Czy preview zastępuje konfigurację produkcyjną?','Nie','Tak','Tak, z base','Tak, w CI','Ma własny serwer i zachowanie.'],
['Skąd 404 po odświeżeniu trasy SPA?','Serwer nie ma takiego pliku','Router jest zepsuty','Brakuje hasha','Brakuje CSS','Trasa istnieje tylko w przeglądarce.'],
['Co zwraca fallback SPA?','index.html ze statusem 200','404','Przekierowanie 302 na /','Pustą odpowiedź','Router wybierze ekran lub własne 404.'],
['Kto pokazuje stronę 404 dla złej trasy SPA?','Router aplikacji','Nginx','CDN','Sentry','Serwer nie zna tras aplikacji.'],
['Co musi być obsłużone przed fallbackiem?','Prawdziwe pliki i API','Tylko CSS','Tylko obrazki','Nic','Inaczej API dostaje HTML.'],
['Co oznacza „Unexpected token <”?','Zamiast JS przyszedł HTML','Błąd CSS','Błąd TS','Brak klucza','Klasyczny objaw złego fallbacku.'],
['Jaki nagłówek dla pliku z hashem?','public, max-age=31536000, immutable','no-cache','no-store','max-age=0','Plik nigdy się nie zmieni.'],
['Co oznacza immutable?','Przeglądarka nie musi nawet walidować pliku','Plik jest szyfrowany','Plik nie może być pobrany','Plik jest w RAM','Oszczędza żądania warunkowe.'],
['Co oznacza no-cache?','Trzeba zwalidować przed użyciem','Nie wolno zapisywać','Plik jest prywatny','Plik wygasa za rok','Do zakazu zapisu służy no-store.'],
['Jaki nagłówek ma version.json?','no-cache','immutable','max-age=31536000','Nie ma znaczenia','Plik bez hasha, który się zmienia.'],
['Jaki problem daje długi cache na index.html?','Użytkownik dostaje stare wskazania plików','Szybsze wdrożenie','Lepszy LCP','Nic','Może skończyć się białym ekranem.'],
['Jaki problem daje no-cache na assets?','Niepotrzebne walidacje lub pobrania','Biały ekran','Błąd CORS','Brak hasha','Cache nie przynosi wtedy pełnej korzyści.'],
['Gdzie umieścić _redirects?','W public, aby trafił do dist','W src','W node_modules','W .env','Platforma czyta go z wyniku budowania.'],
['Czemu w _redirects API stoi przed /*?','Reguły działają w kolejności','Dla czytelności','Bez znaczenia','Dla Vite','Inaczej fallback przechwyci API.'],
['Co robi 200 w regule _redirects?','Przepisuje bez przekierowania przeglądarki','Przekierowuje 302','Zwraca błąd','Blokuje','Adres w pasku zostaje.'],
['Do czego służy base?','Podścieżka aplikacji','Adres API','Katalog dist','Nazwa pliku','np. /devdesk/.'],
['Co musi znać prefiks oprócz Vite?','Router przez basename','Baza danych','Sentry','MSW','Inaczej trasy się nie dopasują.'],
['Jak odwołać się do zasobu z public przy base?','import.meta.env.BASE_URL+"logo.svg"','Zawsze /logo.svg','public/logo.svg','../logo.svg','/logo.svg ominie prefiks.'],
['Kiedy pojawia się „Failed to fetch dynamically imported module”?','Stary kawałek zniknął po wdrożeniu','Gdy brak CSS','Gdy zły TS','Gdy brak hasha','Otwarta karta odwołuje się do starej nazwy.'],
['Czym jest vite:preloadError?','Zdarzeniem Vite przy nieudanym ładowaniu kawałka','Standardem DOM','Błędem CSS','Zdarzeniem React','Pozwala zareagować centralnie.'],
['Po co ochrona przed pętlą przeładowania?','Stały błąd nie może odświeżać strony bez końca','Dla SEO','Dla typów','Dla CDN','Drugi błąd trafia do granicy.'],
['Gdzie zapisać czas przeładowania?','sessionStorage','localStorage na zawsze','Cookie HttpOnly','Baza','Dotyczy tej karty i sesji.'],
['Dlaczego zostawić stare assets?','Otwarte karty nadal je potrzebują','Dla SEO','Bo dist tego wymaga','Bo Vite ich nie usuwa','Platformy statyczne robią to same.'],
['Dlaczego baner zamiast przeładowania?','Niezapisany formularz nie przepadnie','Bo reload nie działa','Dla kontrastu','Bo to szybsze','Użytkownik decyduje.'],
['Jak często sprawdzać wersję?','Co kilka minut','Co sekundę','Nigdy','Przy każdym renderze','Plik jest mały, ale żądania się sumują.'],
['Jak pobrać version.json?','Z cache:"no-store"','Z długim cache','Z POST','Z CORS','Chcesz prawdziwej aktualnej wartości.'],
['Co zrobić przy braku sieci w sprawdzaniu?','Spróbować przy następnym razie','Pokazać błąd krytyczny','Przeładować','Wyłączyć aplikację','To nie jest problem użytkownika.'],
['Co oznaczają niezmienne wdrożenia?','Każde ma swój adres i nie zmienia się','Nie można wdrażać','Brak rollbacku','Jeden plik','Rollback to wskazanie starego.'],
['Czym jest podgląd pull requesta?','Osobnym adresem z wersją z gałęzi','Komentarzem','Plikiem ZIP','Testem E2E','Przegląd bez uruchamiania projektu.'],
['Z jakim API łączy się podgląd?','Testowym','Produkcyjnym','Żadnym','Lokalnym','Podgląd nie powinien zmieniać danych produkcji.'],
['Co podać platformie?','Komendę, katalog, wersję Node, zmienne','Hasło do bazy','Klucz SSH do serwera','Plik .env.local','Sekrety frontendu nie istnieją.'],
['Co trzeba przećwiczyć przed awarią?','Rollback','Tylko dev','Tylko CSS','Zmianę logo','Wiesz, jak to zrobić pod presją.'],
['Czy rollback zawsze wystarcza?','Nie, API mogło się zmienić','Tak','Tak, w CDN','Tak, w Vercel','Zgodność wersji też się liczy.'],
['Czy public jest przetwarzany przez Vite?','Nie, kopiowany bez zmian','Tak, z hashem','Tak, minifikowany','Tylko CSS','Stąd brak hasha.'],
['Kiedy importować obraz w kodzie zamiast public?','Gdy chcesz hash i wykrywanie braku','Nigdy','Tylko SVG','Tylko PNG','Build zgłosi brakujący plik.'],
['Co jest ryzykiem /* do index.html bez wyjątków?','Brakujące pliki i API dostają HTML','Nic','Wolniejszy build','Brak gzipa','Monitoring nie widzi 404.'],
['Co pokazuje test z realnym serwerem?','Status i nagłówki faktycznej odpowiedzi','Tylko typy','Tylko klasy','Tylko CSS','Dowód, a nie założenie.'],
['Jak sprawdzić, że zasób ma hash?','Porównać nazwę z dist','Czytać README','Sprawdzić kolor','Zapytać CDN','Test iteruje po plikach.'],
['Czemu sprawdzać dekodowanie ścieżki w serwerze?','Aby nie wyjść poza katalog','Dla SEO','Dla CSS','Bez powodu','Path traversal to klasyczny błąd.'],
['Czy własny serwer do produkcji to dobry pomysł?','Lepiej Nginx lub platforma','Tak, zawsze','Tak, bo krótki','Tak, z TS','Kompresja, TLS i bezpieczeństwo wymagają dojrzałego rozwiązania.'],
['Czym jest kompresja brotli?','Mniejszy transfer tekstu niż często gzip','Format obrazu','Rodzaj cache','Hash','Serwer lub CDN wybiera format.'],
['Jaki nagłówek chroni przed zgadywaniem typu?','X-Content-Type-Options: nosniff','Cache-Control','ETag','Vary','Przeglądarka trzyma się Content-Type.'],
['Co ustawia Referrer-Policy?','Jakie dane o źródle są wysyłane','Cache','CORS','Kompresję','strict-origin-when-cross-origin to rozsądny wybór.'],
['Co daje ETag?','Walidację pliku bez pełnego pobrania','Hash nazwy','Sesję','Wersję Reacta','Serwer może odpowiedzieć 304.'],
['Czym jest status 304?','Plik się nie zmienił','Błąd serwera','Przekierowanie stałe','Brak uprawnień','Oszczędza transfer.'],
['Co to chunk w dist?','Kawałek kodu ładowany osobno','Plik CSS tylko','Baza danych','Fragment DOM','Podział wynika z importów dynamicznych.'],
['Jak zobaczyć rozmiary kawałków?','Wizualizacja paczki z rozdziału 16','DevTools Elements','npm audit','MSW','Pomaga wykryć, co rośnie.'],
['Czy mapy źródeł powinny być publiczne?','Zwykle nie: do monitoringu','Zawsze tak','Tak, w public','Tak, z hashem','Zobacz lekcję o monitoringu.'],
['Kiedy CDN pomaga najbardziej?','Dla plików z hashem','Dla API POST','Dla baz danych','Dla logów','Treść jest niezmienna i blisko użytkownika.'],
['Co zrobić z cache CDN po wdrożeniu?','index.html bez cache lub unieważnić','Zawsze czyścić wszystko','Nic, nawet z długim cache','Usunąć CDN','Pliki z hashem nie wymagają czyszczenia.'],
['Co jest głównym celem testu dymnego?','Szybko wykryć złe wdrożenie','Pełne pokrycie','Sprawdzić kolory','Wyszukać typy','Kilka kluczowych żądań.'],
['Czym jest deploy preview vs production?','Inny adres i inne zmienne','Inny kod zawsze','Inny framework','Nie ma różnicy','Kod ten sam dla gałęzi, konfiguracja inna.'],
['Dlaczego wersję trzymać w version.json i w kodzie?','Aby je porównać','Dla CSS','Dla SEO','Bo Vite wymaga','Różnica oznacza nowe wdrożenie.'],
['Co trafia do VITE_APP_VERSION?','Wersja lub commit z CI','Hasło','Adres bazy','Nazwa użytkownika','Wartość publiczna.'],
['Jak obsłużyć aplikację bez current?','Nie sprawdzać wersji','Rzucić wyjątek','Przeładować','Pobierać co sekundę','Tryb deweloperski często nie ma wersji.'],
['Co oznacza 502 w tym serwerze lekcji?','API nie ma tu backendu','Brak pliku','Błąd TS','Sukces','Ważne, że nie jest to HTML ze statusem 200.'],
];
const header=String.raw`import {assetUrl,cacheControl,decide,isHashedAsset,normalizeBase} from "./deployPolicy";
import {handleChunkError} from "./chunkErrors";
import {isNewer} from "./NewVersionBanner";
const files=new Set(["/index.html","/assets/index-3f2a9cd1.js","/favicon.svg"]);
const kind=(path:string)=>decide(path,files).kind;
const storage=(value?:string)=>({getItem:()=>value??null,setItem:()=>{}});
const event={preventDefault:()=>{}};`;
const K=['file','spa','proxy','notFound'];
const YEAR='public, max-age=31536000, immutable';
const C=[YEAR,'no-cache','public, max-age=3600','no-store'];
const probeRows=[
['Decyzja dla /index.html','kind("/index.html")','file','Istniejący plik.',K],
['Decyzja dla /tickets/42','kind("/tickets/42")','spa','Trasa aplikacji.',K],
['Decyzja dla /','kind("/")','spa','Katalog główny obsłuży index.',K],
['Decyzja dla /api/tickets','kind("/api/tickets")','proxy','API trafia do backendu.',K],
['Decyzja dla /api','kind("/api")','proxy','Sam prefiks też.',K],
['Decyzja dla /apis','kind("/apis")','spa','To nie jest prefiks API.',K],
['Decyzja dla istniejącego assetu','kind("/assets/index-3f2a9cd1.js")','file','Plik istnieje.',K],
['Decyzja dla starego assetu','kind("/assets/stary-aaaaaaaa.js")','notFound','Prawdziwe 404, a nie HTML.',K],
['Decyzja dla /favicon.svg','kind("/favicon.svg")','file','Plik z public.',K],
['Decyzja dla /brak.png','kind("/brak.png")','notFound','Ścieżka z rozszerzeniem to plik.',K],
['Decyzja dla /tickets/42.5','kind("/tickets/42.5")','notFound','Prosta reguła traktuje to jak plik. To znane ograniczenie.',K],
['Decyzja dla /ustawienia/profil','kind("/ustawienia/profil")','spa','Zagnieżdżona trasa.',K],
['Cache dla pliku z hashem','cacheControl("/assets/index-3f2a9cd1.js")',YEAR,'Niezmienna treść.',C],
['Cache dla index.html','cacheControl("/index.html")','no-cache','Musi wskazywać aktualne pliki.',C],
['Cache dla /','cacheControl("/")','no-cache','To też index.',C],
['Cache dla version.json','cacheControl("/version.json")','no-cache','Plik bez hasha, który się zmienia.',C],
['Cache dla config.json','cacheControl("/config.json")','no-cache','Zmiana bez nowej nazwy.',C],
['Cache dla sw.js','cacheControl("/sw.js")','no-cache','Service worker musi się aktualizować.',C],
['Cache dla pliku z public','cacheControl("/favicon.svg")','public, max-age=3600','Bez hasha: krótki cache.',C],
['Cache dla assetu bez hasha','cacheControl("/assets/logo.svg")','public, max-age=3600','Brak hasha nie daje immutable.',C],
['Czy index-3f2a9cd1.js ma hash?','isHashedAsset("/assets/index-3f2a9cd1.js")',true,'Sufiks po myślniku.'],
['Czy logo.svg ma hash?','isHashedAsset("/assets/logo.svg")',false,'Brak sufiksu.'],
['Czy plik poza assets ma hash?','isHashedAsset("/index-3f2a9cd1.js")',false,'Sprawdzamy też katalog.'],
['Czy zbyt krótki sufiks to hash?','isHashedAsset("/assets/a-12.js")',false,'Minimum sześć znaków.'],
['Normalizacja devdesk','normalizeBase("devdesk")','/devdesk/','Dodaje oba ukośniki.',['devdesk/','/devdesk','devdesk']],
['Normalizacja /','normalizeBase("/")','/','Katalog główny bez zmian.',['//','pusty tekst','./']],
['Adres pod podścieżką','assetUrl("/devdesk/","logo.svg")','/devdesk/logo.svg','Prefiks z base.',['/logo.svg','devdesk/logo.svg','/devdesk//logo.svg']],
['Adres z początkowym ukośnikiem','assetUrl("/devdesk/","/logo.svg")','/devdesk/logo.svg','Brak podwójnego ukośnika.',['/logo.svg','/devdesk//logo.svg','devdesk/logo.svg']],
['Adres w katalogu głównym','assetUrl("/","logo.svg")','/logo.svg','Bez prefiksu.',['logo.svg','//logo.svg','/devdesk/logo.svg']],
['Przeładowanie bez historii','handleChunkError(event,{storage:storage(),reload:()=>{},now:()=>100000})',true,'Pierwszy błąd.'],
['Przeładowanie 5 s po poprzednim','handleChunkError(event,{storage:storage("95000"),reload:()=>{},now:()=>100000})',false,'Ochrona przed pętlą.'],
['Przeładowanie 10 s po poprzednim','handleChunkError(event,{storage:storage("90000"),reload:()=>{},now:()=>100000})',true,'Granica okna.'],
['Przeładowanie przy złej wartości','handleChunkError(event,{storage:storage("zzz"),reload:()=>{},now:()=>100000})',true,'Różnica daje NaN, a porównanie z NaN jest fałszywe, więc przeładowanie przechodzi. Uszkodzony zapis nie blokuje naprawy.'],
['Czy inna wersja jest nowsza?','isNewer("1","2")',true,'Różnica wystarcza.'],
['Czy starsza zdalna też jest inna?','isNewer("2","1")',true,'Rollback też wymaga odświeżenia.'],
['Czy ta sama wersja daje baner?','isNewer("1","1")',false,'Bez zmian.'],
['Czy brak bieżącej wersji daje baner?','isNewer(undefined,"1")',false,'Tryb deweloperski.'],
['Czy zły typ zdalnej wersji daje baner?','isNewer("1",2)',false,'Dane z sieci nie są zaufane.'],
['Czy undefined zdalnie daje baner?','isNewer("1",undefined)',false,'Plik mógł być niepełny.'],
['Czy pusty bieżący daje baner?','isNewer("","1")',false,'Pusty tekst to brak wersji.'],
];
const project={
'src/setup.ts':nodeSafeSetup,'src/deployPolicy.ts':policy,'src/serveDist.ts':server,'src/chunkErrors.ts':chunk,'src/NewVersionBanner.tsx':banner,'src/deploy.test.tsx':tests,'src/dist.test.ts':distTest,
'public/_redirects':redirects,'public/_headers':headersFile,'vercel.json':vercel,
'fixture/index.html':appIndex,'fixture/main.ts':appMain,'fixture/lazyPage.ts':appPage,
'README.md':'DevDesk: wdrożenie. npm install, npm test. dist.test.ts uruchamia prawdziwy vite build i prawdziwy serwer HTTP, więc sprawdza statusy i nagłówki. Pliki _redirects, _headers i vercel.json są wzorem dla platform i nie były wdrażane z tego projektu. Podgląd pull requesta i rollback sprawdź na własnym koncie platformy.\n',
};
const mutants=[
{file:'src/deployPolicy.ts',before:'if(path.startsWith("/assets/"))return {kind:"notFound"};',after:''},
{file:'src/deployPolicy.ts',before:'if(/\\.[a-z0-9]+$/i.test(path))return {kind:"notFound"};',after:''},
{file:'src/deployPolicy.ts',before:'path==="/api"||path.startsWith("/api/")',after:'path.startsWith("/api")'},
{file:'src/deployPolicy.ts',before:'if(isHashedAsset(path))return "public, max-age=31536000, immutable";',after:''},
{file:'src/deployPolicy.ts',before:'if(path==="/"||path.endsWith(".html"))return "no-cache";',after:''},
{file:'src/deployPolicy.ts',before:'file.replace(/^\\/+/,"")',after:'file'},
{file:'src/chunkErrors.ts',before:'if(now()-last<RELOAD_WINDOW)return false;',after:''},
{file:'src/chunkErrors.ts',before:'storage.setItem(RELOAD_KEY,String(now()));',after:''},
{file:'src/chunkErrors.ts',before:'return ()=>target.removeEventListener("vite:preloadError",listener);',after:'return ()=>{};'},
{file:'src/NewVersionBanner.tsx',before:'if(!current)return;',after:''},
{file:'src/NewVersionBanner.tsx',before:'return ()=>clearInterval(id);',after:''},
{file:'src/NewVersionBanner.tsx',before:'remote!==current',after:'remote===current'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
