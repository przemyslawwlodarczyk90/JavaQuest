const {lesson,nodeSafeSetup}=require('./practice_kit.cjs');
const strategies=String.raw`export type Fetcher=(url:string)=>Promise<string>;
export type Store=Map<string,string>;

const timeout=(ms:number)=>new Promise<never>((_,reject)=>setTimeout(()=>reject(new Error("timeout")),ms));

export async function networkFirst(url:string,store:Store,fetcher:Fetcher,limitMs=4000){
  try{
    const value=await Promise.race([fetcher(url),timeout(limitMs)]);
    store.set(url,value);
    return {value,source:"sieć"};
  }catch(error){
    const cached=store.get(url);
    if(cached===undefined)throw error;
    return {value:cached,source:"cache"};
  }
}

export async function cacheFirst(url:string,store:Store,fetcher:Fetcher){
  const cached=store.get(url);
  if(cached!==undefined)return {value:cached,source:"cache"};
  const value=await fetcher(url);
  store.set(url,value);
  return {value,source:"sieć"};
}

export function staleWhileRevalidate(url:string,store:Store,fetcher:Fetcher){
  const refresh=fetcher(url).then(value=>{store.set(url,value);return value;});
  const cached=store.get(url);
  return {value:cached??refresh,refresh,source:cached===undefined?"sieć":"cache"};
}

export type Strategy="NetworkFirst"|"CacheFirst"|"StaleWhileRevalidate"|"NetworkOnly";

export function strategyFor({method,pathname,destination}:{method:string;pathname:string;destination?:string}):Strategy{
  if(method!=="GET")return "NetworkOnly";
  if(pathname.startsWith("/api/session"))return "NetworkOnly";
  if(pathname.startsWith("/api/dictionaries"))return "StaleWhileRevalidate";
  if(pathname.startsWith("/api/"))return "NetworkFirst";
  if(destination==="image"||destination==="font")return "CacheFirst";
  return "NetworkOnly";
}`;
const manifest=String.raw`export type WebManifest={
  name?:string;short_name?:string;start_url?:string;display?:string;theme_color?:string;
  icons?:readonly {src:string;sizes:string;type?:string;purpose?:string}[];
};

export function auditManifest(manifest:WebManifest){
  const problems:string[]=[];
  if(!manifest.name)problems.push("brak name");
  if(!manifest.start_url)problems.push("brak start_url");
  if(!["standalone","fullscreen","minimal-ui"].includes(manifest.display??""))problems.push("display bez trybu aplikacji");
  const sizes=(manifest.icons??[]).map(icon=>icon.sizes);
  for(const size of ["192x192","512x512"])if(!sizes.includes(size))problems.push("brak ikony "+size);
  if(!(manifest.icons??[]).some(icon=>icon.purpose?.includes("maskable")))problems.push("brak ikony maskable");
  if((manifest.short_name?.length??0)>12)problems.push("short_name zbyt długi");
  return problems;
}`;
const online=String.raw`import {useSyncExternalStore} from "react";

function subscribe(callback:()=>void):()=>void{
  window.addEventListener("online",callback);
  window.addEventListener("offline",callback);
  return ()=>{
    window.removeEventListener("online",callback);
    window.removeEventListener("offline",callback);
  };
}

export function useOnlineStatus():boolean{
  return useSyncExternalStore(subscribe,()=>navigator.onLine,()=>true);
}

export function OfflineBanner(){
  const online=useOnlineStatus();
  if(online)return null;
  return <div role="status">Brak połączenia. Pokazujemy ostatnio pobrane dane, a zmiany wyślemy po powrocie sieci.</div>;
}`;
const banner=String.raw`type Props={needRefresh:boolean;offlineReady:boolean;onRefresh:()=>void;onClose:()=>void};

export function UpdateBanner({needRefresh,offlineReady,onRefresh,onClose}:Props){
  if(!needRefresh&&!offlineReady)return null;
  return (
    <div role="status">
      {needRefresh?"Dostępna jest nowa wersja DevDesk.":"DevDesk działa teraz także bez internetu."}
      {needRefresh&&<button type="button" onClick={onRefresh}>Odśwież</button>}
      <button type="button" onClick={onClose}>Zamknij</button>
    </div>
  );
}`;
const prompt=String.raw`// app/UpdatePrompt.tsx (moduł wirtualny istnieje tylko z wtyczką vite-plugin-pwa)
import {useRegisterSW} from "virtual:pwa-register/react";
import {UpdateBanner} from "./UpdateBanner";

export function UpdatePrompt(){
  const {needRefresh:[needRefresh,setNeedRefresh],offlineReady:[offlineReady,setOfflineReady],updateServiceWorker}=useRegisterSW({
    onRegisteredSW(_url:string,registration:ServiceWorkerRegistration|undefined){
      if(registration)setInterval(()=>registration.update(),60*60*1000);
    },
  });
  return (
    <UpdateBanner
      needRefresh={needRefresh}
      offlineReady={offlineReady}
      onRefresh={()=>updateServiceWorker(true)}
      onClose={()=>{setNeedRefresh(false);setOfflineReady(false);}}
    />
  );
}`;
const queue=String.raw`export type Change={id:string;ticketId:number;text:string;version:number};
export type SendResult="ok"|"conflict"|"retry";

// Kolejka zapisów: kolejność zachowana, a konflikt nie znika po cichu.
export function createOfflineQueue(send:(change:Change)=>Promise<SendResult>,initial:readonly Change[]=[]){
  let pending=[...initial];
  const conflicts:Change[]=[];
  return {
    add(change:Change){pending.push(change);},
    pending:()=>[...pending],
    conflicts:()=>[...conflicts],
    async flush(){
      while(pending.length){
        let result:SendResult;
        try{result=await send(pending[0]);}catch{result="retry";}
        if(result==="retry")return false;
        const [done,...rest]=pending;
        pending=rest;
        if(result==="conflict")conflicts.push(done);
      }
      return true;
    },
  };
}

export async function clearUserCaches(store:Pick<CacheStorage,"keys"|"delete">,prefix="api-"){
  const names=await store.keys();
  const removed=names.filter(name=>name.startsWith(prefix));
  await Promise.all(removed.map(name=>store.delete(name)));
  return removed;
}

export function isStaleVersion(serverVersion:number,clientVersion:number){
  return serverVersion!==clientVersion;
}`;
const viteConfig=String.raw`// vite.config.ts
import react from "@vitejs/plugin-react";
import {defineConfig} from "vite";
import {VitePWA} from "vite-plugin-pwa";

export default defineConfig({
  plugins:[
    react(),
    VitePWA({
      registerType:"prompt",
      manifest:{
        name:"DevDesk: panel zgłoszeń",
        short_name:"DevDesk",
        start_url:"/",
        display:"standalone",
        theme_color:"#0e7490",
        icons:[
          {src:"pwa-192.png",sizes:"192x192",type:"image/png"},
          {src:"pwa-512.png",sizes:"512x512",type:"image/png"},
          {src:"pwa-512-maskable.png",sizes:"512x512",type:"image/png",purpose:"maskable"},
        ],
      },
      workbox:{
        navigateFallback:"/index.html",
        navigateFallbackDenylist:[/^\/api\//],
        runtimeCaching:[
          {urlPattern:({url})=>url.pathname.startsWith("/api/tickets"),handler:"NetworkFirst",options:{cacheName:"api-tickets",networkTimeoutSeconds:4,expiration:{maxEntries:200,maxAgeSeconds:86_400}}},
          {urlPattern:({request})=>request.destination==="image",handler:"CacheFirst",options:{cacheName:"images",expiration:{maxEntries:100,maxAgeSeconds:2_592_000}}},
        ],
      },
    }),
  ],
});`;
const tests=String.raw`import {afterEach,beforeEach,expect,test,vi} from "vitest";
import {act,fireEvent,render,screen} from "@testing-library/react";
import {cacheFirst,networkFirst,staleWhileRevalidate,strategyFor} from "./cacheStrategies";
import {auditManifest} from "./manifestAudit";
import {OfflineBanner} from "./useOnlineStatus";
import {UpdateBanner} from "./UpdateBanner";
import {clearUserCaches,createOfflineQueue,isStaleVersion,type Change} from "./offlineQueue";

const change=(id:string):Change=>({id,ticketId:1,text:"Notatka",version:1});

test("NetworkFirst: świeże dane, a przy braku sieci ostatnie znane",async()=>{
  const store=new Map<string,string>();
  expect(await networkFirst("/a",store,async()=>"świeże")).toEqual({value:"świeże",source:"sieć"});
  expect(await networkFirst("/a",store,async()=>{throw new TypeError("offline");})).toEqual({value:"świeże",source:"cache"});
  await expect(networkFirst("/b",store,async()=>{throw new TypeError("offline");})).rejects.toThrow("offline");
});

test("NetworkFirst: wolna sieć po limicie czasu wraca do cache",async()=>{
  const store=new Map([["/a","stare"]]);
  const never=()=>new Promise<string>(()=>{});
  expect(await networkFirst("/a",store,never,5)).toEqual({value:"stare",source:"cache"});
});

test("CacheFirst nie pyta sieci, gdy ma wpis",async()=>{
  const store=new Map<string,string>(),fetcher=vi.fn(async()=>"obraz");
  expect((await cacheFirst("/i",store,fetcher)).source).toBe("sieć");
  expect((await cacheFirst("/i",store,fetcher)).source).toBe("cache");
  expect(fetcher).toHaveBeenCalledOnce();
});

test("StaleWhileRevalidate daje stare od razu i odświeża w tle",async()=>{
  const store=new Map([["/d","stare"]]);
  const result=staleWhileRevalidate("/d",store,async()=>"nowe");
  expect(result.value).toBe("stare");
  expect(result.source).toBe("cache");
  await result.refresh;
  expect(store.get("/d")).toBe("nowe");
});

test("wybór strategii",()=>{
  expect(strategyFor({method:"POST",pathname:"/api/tickets"})).toBe("NetworkOnly");
  expect(strategyFor({method:"GET",pathname:"/api/tickets"})).toBe("NetworkFirst");
  expect(strategyFor({method:"GET",pathname:"/api/session"})).toBe("NetworkOnly");
  expect(strategyFor({method:"GET",pathname:"/api/dictionaries/status"})).toBe("StaleWhileRevalidate");
  expect(strategyFor({method:"GET",pathname:"/a.png",destination:"image"})).toBe("CacheFirst");
});

test("przegląd manifestu",()=>{
  const good={name:"DevDesk",short_name:"DevDesk",start_url:"/",display:"standalone",icons:[{src:"a",sizes:"192x192"},{src:"b",sizes:"512x512"},{src:"c",sizes:"512x512",purpose:"maskable"}]};
  expect(auditManifest(good)).toEqual([]);
  expect(auditManifest({})).toEqual(["brak name","brak start_url","display bez trybu aplikacji","brak ikony 192x192","brak ikony 512x512","brak ikony maskable"]);
  expect(auditManifest({...good,short_name:"Bardzo długa nazwa aplikacji"})).toEqual(["short_name zbyt długi"]);
});

test("wskaźnik sieci reaguje na zdarzenia przeglądarki",()=>{
  const online=vi.spyOn(navigator,"onLine","get");
  online.mockReturnValue(true);
  render(<OfflineBanner/>);
  expect(screen.queryByRole("status")).toBeNull();
  online.mockReturnValue(false);
  act(()=>{window.dispatchEvent(new Event("offline"));});
  expect(screen.getByRole("status")).toHaveTextContent("Brak połączenia");
  online.mockReturnValue(true);
  act(()=>{window.dispatchEvent(new Event("online"));});
  expect(screen.queryByRole("status")).toBeNull();
  online.mockRestore();
});

test("baner nowej wersji i gotowości offline",()=>{
  const onRefresh=vi.fn(),onClose=vi.fn();
  const view=render(<UpdateBanner needRefresh offlineReady={false} onRefresh={onRefresh} onClose={onClose}/>);
  fireEvent.click(screen.getByRole("button",{name:"Odśwież"}));
  expect(onRefresh).toHaveBeenCalledOnce();
  fireEvent.click(screen.getByRole("button",{name:"Zamknij"}));
  expect(onClose).toHaveBeenCalledOnce();
  view.rerender(<UpdateBanner needRefresh={false} offlineReady onRefresh={onRefresh} onClose={onClose}/>);
  expect(screen.getByRole("status")).toHaveTextContent("także bez internetu");
  expect(screen.queryByRole("button",{name:"Odśwież"})).toBeNull();
  view.rerender(<UpdateBanner needRefresh={false} offlineReady={false} onRefresh={onRefresh} onClose={onClose}/>);
  expect(screen.queryByRole("status")).toBeNull();
});

test("kolejka wysyła w kolejności i zatrzymuje się bez sieci",async()=>{
  const sent:string[]=[];
  let online=false;
  const queue=createOfflineQueue(async item=>{
    if(!online)throw new TypeError("offline");
    sent.push(item.id);
    return "ok";
  });
  queue.add(change("a"));
  queue.add(change("b"));
  expect(await queue.flush()).toBe(false);
  expect(queue.pending().map(item=>item.id)).toEqual(["a","b"]);
  online=true;
  expect(await queue.flush()).toBe(true);
  expect(sent).toEqual(["a","b"]);
  expect(queue.pending()).toEqual([]);
});

test("konflikt jest odnotowany, a nie nadpisany po cichu",async()=>{
  const queue=createOfflineQueue(async item=>item.id==="a"?"conflict":"ok",[change("a"),change("b")]);
  await queue.flush();
  expect(queue.conflicts().map(item=>item.id)).toEqual(["a"]);
  expect(queue.pending()).toEqual([]);
  expect(isStaleVersion(2,1)).toBe(true);
  expect(isStaleVersion(1,1)).toBe(false);
});

test("wylogowanie usuwa tylko pamięć z danymi użytkownika",async()=>{
  const names=new Set(["api-tickets","api-users","images","workbox-precache-v2"]);
  const store={keys:async()=>[...names],delete:async(name:string)=>names.delete(name)};
  expect(await clearUserCaches(store)).toEqual(["api-tickets","api-users"]);
  expect([...names]).toEqual(["images","workbox-precache-v2"]);
});

beforeEach(()=>{});
afterEach(()=>{vi.restoreAllMocks();});`;
const buildTest=String.raw`// @vitest-environment node
import {existsSync,mkdtempSync,readFileSync,rmSync} from "node:fs";
import {join,resolve} from "node:path";
import {tmpdir} from "node:os";
import {build} from "vite";
import {VitePWA} from "vite-plugin-pwa";
import {afterAll,beforeAll,expect,test} from "vitest";
import {auditManifest} from "./manifestAudit";

const skip=process.env.LESSON_MUTANT==="1";
const outDir=mkdtempSync(join(tmpdir(),"devdesk-pwa-"));

beforeAll(async()=>{
  if(skip)return;
  const previous=process.env.NODE_ENV;
  await build({
    root:resolve(process.cwd(),"fixture"),
    logLevel:"silent",
    configFile:false,
    plugins:[VitePWA({
      registerType:"prompt",
      manifest:{name:"DevDesk: panel zgłoszeń",short_name:"DevDesk",start_url:"/",display:"standalone",theme_color:"#0e7490",icons:[{src:"pwa-192.png",sizes:"192x192",type:"image/png"},{src:"pwa-512.png",sizes:"512x512",type:"image/png"},{src:"pwa-512-maskable.png",sizes:"512x512",type:"image/png",purpose:"maskable"}]},
      workbox:{navigateFallback:"/index.html",navigateFallbackDenylist:[/^\/api\//]},
    })],
    build:{outDir,emptyOutDir:true},
  });
  process.env.NODE_ENV=previous;
},180000);
afterAll(()=>{rmSync(outDir,{recursive:true,force:true});});

test.skipIf(skip)("prawdziwy build tworzy service worker i manifest",()=>{
  expect(existsSync(join(outDir,"sw.js"))).toBe(true);
  expect(existsSync(join(outDir,"manifest.webmanifest"))).toBe(true);
});

test.skipIf(skip)("wygenerowany manifest przechodzi przegląd",()=>{
  const manifest=JSON.parse(readFileSync(join(outDir,"manifest.webmanifest"),"utf8"));
  expect(auditManifest(manifest)).toEqual([]);
  expect(manifest.display).toBe("standalone");
});

test.skipIf(skip)("service worker ma precache pliku index.html i fallback bez API",()=>{
  const worker=readFileSync(join(outDir,"sw.js"),"utf8");
  expect(worker).toContain("index.html");
  expect(worker).toContain("NavigationRoute");
  expect(worker).toContain("api");
});

test.skipIf(skip)("index.html dostaje odnośnik do manifestu",()=>{
  expect(readFileSync(join(outDir,"index.html"),"utf8")).toContain("manifest.webmanifest");
});`;
const appIndex=String.raw`<!doctype html><html lang="pl"><head><meta charset="utf-8"/><title>DevDesk</title></head><body><div id="root"></div><script type="module" src="/main.ts"></script></body></html>`;
const exercises=[
['Skonfiguruj manifest i service worker przez wtyczkę.','registerType prompt i ikony 192, 512, maskable.',viteConfig],
['Dodaj fallback nawigacji bez API.','navigateFallbackDenylist chroni /api/.',viteConfig],
['Wybierz strategie dla ticketów i obrazów.','NetworkFirst i CacheFirst.',viteConfig],
['Zaimplementuj NetworkFirst, aby zobaczyć mechanizm.','Sieć, zapis, a przy błędzie cache.',strategies],
['Dodaj limit czasu sieci.','Słaby zasięg bywa gorszy niż brak sieci.',strategies],
['Zaimplementuj CacheFirst.','Sieć tylko przy braku wpisu.',strategies],
['Zaimplementuj StaleWhileRevalidate.','Stara wartość od razu, nowa na później.',strategies],
['Napisz wybór strategii dla żądania.','Zapisy zawsze NetworkOnly.',strategies],
['Wyłącz cache danych sesji.','Dane wrażliwe nie powinny trafiać do cache.',strategies],
['Sprawdź manifest kodem.','Nazwa, adres, tryb i ikony.',manifest],
['Sprawdź prawdziwy build PWA.','Test czyta sw.js i manifest z dist.',buildTest],
['Dodaj komunikat o nowej wersji.','useRegisterSW z modułu wirtualnego.',prompt],
['Wydziel widok banera.','Widok testujesz bez modułu wirtualnego.',banner],
['Dodaj typy modułu wirtualnego.','Referencja w vite-env.d.ts.','// src/vite-env.d.ts\n/// <reference types="vite-plugin-pwa/react" />\n// Bez tego TSX zgłosi Cannot find module "virtual:pwa-register/react". W JSX import działa bez deklaracji.'],
['Dodaj okresowe sprawdzanie aktualizacji.','Aplikacja bywa otwarta cały dzień.',prompt],
['Napisz wskaźnik braku sieci.','useSyncExternalStore i zdarzenia online oraz offline.',online],
['Przetestuj zdarzenia sieci.','navigator.onLine trzeba ustawić atrapą.',tests],
['Napisz kolejkę zapisów offline.','Kolejność zachowana, retry zatrzymuje.',queue],
['Obsłuż konflikt bez cichego nadpisania.','Serwer wykrywa konflikt, a interfejs go pokazuje.',queue],
['Wykryj konflikt po numerze wersji.','If-Match z ETag to wariant HTTP.','Klient wysyła wersję, którą widział: If-Match:"7". Serwer przy innej wersji odpowiada 412 i nie zapisuje zmiany. Interfejs pokazuje obie treści i prosi o decyzję. Ostatni zapis nie może po cichu wygrywać.'],
['Użyj mutacji offlineFirst w TanStack Query.','Wstrzymanie i wznowienie bez własnej kolejki.','useMutation({mutationFn:addNote,networkMode:"offlineFirst"}). Aktualizacja optymistyczna pokazuje notatkę z oznaczeniem „oczekuje na wysłanie”. persistQueryClient z IndexedDB zachowa kolejkę po zamknięciu karty.'],
['Wyczyść dane przy wylogowaniu.','Precache zostaje, dane użytkownika nie.',queue],
['Ustaw nagłówki sw.js i manifestu.','Inaczej nowa wersja nie dotrze.','location = /sw.js { add_header Cache-Control "no-cache"; }\nlocation = /manifest.webmanifest { add_header Cache-Control "no-cache"; }\nBez tego stary service worker może działać tygodniami.'],
['Przygotuj wyłącznik service workera.','Plan na wypadek awarii.','Opublikuj sw.js, który przy aktywacji wyrejestrowuje się i czyści cache:\nself.addEventListener("activate",async()=>{for(const name of await caches.keys())await caches.delete(name);await self.registration.unregister();});\nZachowaj go jako procedurę, a nie jako kod używany na co dzień.'],
['Wyjaśnij etap oczekiwania.','Dlaczego po wdrożeniu widać starą wersję?','Nowy service worker instaluje się i czeka, aż znikną wszystkie karty ze starym. Dlatego odświeżenie jednej karty nie wystarcza. updateServiceWorker(true) prosi nową wersję o przejęcie kontroli, a użytkownik decyduje o momencie.'],
['Sprawdź aplikację w DevTools.','Application i Network.','Application, Service Workers: status i Update on reload. Cache Storage: zawartość. Network, Offline: start aplikacji i ostatnie dane. Zainstaluj z paska adresu i uruchom bez sieci. Te rzeczy wymagają prawdziwej przeglądarki.'],
['Oceń, czy cache nie miesza użytkowników.','Wspólny komputer to realny przypadek.','Cache odpowiedzi API bez rozróżnienia użytkowników pokaże dane poprzedniej osoby. Czyść cache przy wylogowaniu, nie zapisuj danych wrażliwych, a prywatne odpowiedzi oznaczaj Cache-Control: private lub no-store, jeśli nie mają być w cache.'],
['Porównaj Background Sync i kolejkę w aplikacji.','Co działa po zamknięciu karty?','Background Sync pozwala service workerowi ponowić żądanie po zamknięciu aplikacji, ale obsługa zależy od przeglądarki. Kolejka w aplikacji działa wszędzie, ale potrzebuje otwartej strony lub zapisanego stanu. Przy braku obsługi miej zapasową drogę.'],
['Znajdź błędy w tej konfiguracji.','autoUpdate, cache sw.js i cache POST.','registerType:"autoUpdate" bez przeładowania zostawia stary kod w otwartej karcie. Długi cache na sw.js blokuje aktualizacje. POST nie powinien być cachowany. Brak denylisty dla /api/ daje HTML zamiast błędu API.'],
['Sprawdź kryterium akceptacji lekcji.','Instalacja, offline, kolejka, aktualizacja, wylogowanie.','Instaluje się i startuje bez sieci z ostatnią listą. Brak sieci jest sygnalizowany. Notatka offline wysyła się po powrocie. Po wdrożeniu jest propozycja odświeżenia. Po wylogowaniu nie ma danych w Cache Storage. Lighthouse nie zgłasza problemów instalowalności.'],
];
const concepts=[
['Co zmienia PWA?','Dodaje manifest i service worker do zwykłej aplikacji','Przepisuje na natywną','Wymaga Reacta Native','Usuwa przeglądarkę','Nadal to aplikacja webowa.'],
['Do czego służy manifest?','Nazwa, ikony, adres startowy, tryb wyświetlania','Do cache','Do testów','Do tras','Umożliwia instalację.'],
['Co oznacza display: standalone?','Okno bez paska adresu','Pełny ekran bez opcji wyjścia','Tryb druku','Nowa karta','Wygląda jak aplikacja.'],
['Co jest wymagane do instalacji?','Manifest i HTTPS','Sklep z aplikacjami','Node','Docker','localhost to wyjątek do testów.'],
['Czym jest ikona maskable?','Ikona z bezpiecznym obszarem do przycięcia','Ikona przezroczysta','Ikona 16 px','Ikona SVG tylko','System może przyciąć ją do kształtu.'],
['Czym jest service worker?','Skrypt działający w tle i przechwytujący żądania','Komponent React','Plik CSS','Web Worker do obliczeń bez sieci','Działa poza stroną.'],
['Czy service worker ma dostęp do DOM?','Nie','Tak','Tylko w dev','Tylko przez ref','Komunikuje się przez wiadomości.'],
['Wymaga HTTPS?','Tak, poza localhost','Nie','Tylko Chrome','Tylko w produkcji Vercel','Ze względu na jego uprawnienia.'],
['Etap instalacji to?','Precache plików aplikacji','Aktywacja','Usunięcie starego cache','Rejestracja DNS','Pliki trafiają do cache.'],
['Etap oczekiwania to?','Nowa wersja czeka na zamknięcie kart starej','Pobieranie Reacta','Sprawdzanie CSS','Logowanie','Źródło „widzę starą wersję”.'],
['Etap aktywacji to?','Przejęcie kontroli i usunięcie starego cache','Pobranie manifestu','Build','Test','Dopiero wtedy nowa wersja działa.'],
['Co robi registerType:"prompt"?','Pyta użytkownika o aktualizację','Aktualizuje bez pytania','Wyłącza SW','Wyłącza manifest','Chroni niezapisane formularze.'],
['Czym ryzykuje autoUpdate?','Nowy i stary kod mogą się rozjechać w otwartej karcie','Niczym','Brakiem cache','Brakiem manifestu','Wymaga przemyślanego przeładowania.'],
['Na czym opiera się vite-plugin-pwa?','Workbox','Redux','Motion','MSW','Generuje service worker.'],
['Co obejmuje precache?','Pliki aplikacji z dist','Odpowiedzi API','Dane użytkownika','Wszystkie obrazy z sieci','Aplikacja startuje offline.'],
['Co robi navigateFallback?','Daje index.html dla nawigacji offline','Przekierowuje do API','Czyści cache','Ładuje CSS','Fallback SPA po stronie SW.'],
['Po co navigateFallbackDenylist z /api/?','API nie może dostać HTML','Dla CSS','Dla ikon','Dla Vite','Taki sam błąd jak na serwerze.'],
['Kiedy NetworkFirst?','Dane aktualne, a offline ostatnie znane','Obrazy','Czcionki','POST','Lista ticketów.'],
['Kiedy CacheFirst?','Rzadko zmieniane zasoby','Lista ticketów','Zapisy','Sesja','Obrazy i czcionki.'],
['Kiedy StaleWhileRevalidate?','Lekko nieaktualna wartość jest w porządku','Dane wrażliwe','POST','Płatności','Słowniki i ustawienia.'],
['Kiedy NetworkOnly?','Zapisy i dane wrażliwe','Obrazy','CSS','Precache','Bez kopii w cache.'],
['Po co networkTimeoutSeconds?','Słaba sieć nie blokuje widoku','Przyspiesza serwer','Wyłącza cache','Ustawia TTL','Po limicie wchodzi cache.'],
['Po co expiration w cache?','Ogranicza rozmiar i wiek wpisów','Szyfruje','Blokuje sieć','Zmienia nazwę','Pamięć urządzenia jest ograniczona.'],
['Czy dane w cache przetrwają wylogowanie?','Tak, trzeba je usunąć','Nie','Tylko obrazy','Tylko w Safari','Stąd caches.delete.'],
['Co jest ryzykiem API z autoryzacją w cache?','Kolejny użytkownik zobaczy dane poprzedniego','Wolniejszy start','Brak ikon','Błąd składni','Szczególnie na wspólnych komputerach.'],
['Jaki nagłówek dla sw.js?','no-cache','immutable','max-age rok','Brak','Inaczej nowa wersja nie dotrze.'],
['Jaki nagłówek dla manifestu?','no-cache','immutable','max-age rok','no-store obowiązkowo','Nazwa nie zmienia się przy wdrożeniu.'],
['Czym jest moduł wirtualny?','Tworzony przez wtyczkę przy budowaniu','Plik w node_modules','Zdalny plik','Komponent','Nie istnieje na dysku.'],
['Skąd typy virtual:pwa-register/react?','Z referencji vite-plugin-pwa/react','Z @types/react','Z tsc automatycznie','Z CSS','Bez tego TSX zgłosi błąd.'],
['Co zwraca useRegisterSW?','needRefresh, offlineReady i updateServiceWorker','Tylko ref','Tablicę tras','Cache','Stany mają pary wartość i setter.'],
['Co zwraca navigator.onLine?','Wskazówkę o stanie sieci','Pewność dostępu do internetu','Adres IP','Prędkość','true nie gwarantuje działającego API.'],
['Czemu useSyncExternalStore dla stanu sieci?','To zewnętrzne źródło z subskrypcją','Bo useState nie działa','Dla CSS','Dla SW','Unika rozjazdów przy równoległym renderze.'],
['Co zwraca hook na serwerze?','true z trzeciego argumentu','false','undefined','wyjątek','Na serwerze nie ma navigator.'],
['Czym różni się odczyt od zapisu offline?','Odczyt daje cache, zapis wymaga kolejki','Niczym','Zapis działa sam','Odczyt nie działa','POST bez sieci po prostu zawodzi.'],
['Co robi networkMode:"offlineFirst"?','Mutacja jest wstrzymywana i wznawiana','Wyłącza sieć','Pomija cache','Blokuje UI','Z TanStack Query.'],
['Co daje persistQueryClient?','Zachowanie cache i kolejki po zamknięciu karty','Szyfrowanie','Precache','Instalację','Zapis w IndexedDB.'],
['Czym jest Background Sync?','SW ponawia żądanie po powrocie sieci','Synchronizacja git','Migracja bazy','Cache CSS','Obsługa zależy od przeglądarki.'],
['Dlaczego kolejka musi zachować kolejność?','Późniejsze zmiany mogą zależeć od wcześniejszych','Dla kolorów','Dla CSS','Bez powodu','Komentarz nie powinien wyprzedzić utworzenia.'],
['Co zrobić po błędzie retry w kolejce?','Zatrzymać i spróbować później','Pominąć element','Usunąć wszystko','Zapętlić bez przerwy','Inaczej gubisz lub mieszasz kolejność.'],
['Kto wykrywa konflikt?','Serwer, np. przez wersję lub ETag','Tylko klient','CSS','SW bez API','Klient nie zna cudzych zmian.'],
['Jaki status przy niezgodnym If-Match?','412','200','404','301','Warunek wstępny nie jest spełniony.'],
['Co pokazać przy konflikcie?','Obie wersje i decyzję użytkownika','Nic','Tylko log','Ostatni zapis po cichu','Ciche nadpisanie gubi dane.'],
['Co pokazać przy zmianie oczekującej?','Oznaczenie „oczekuje na wysłanie”','Nic','Błąd krytyczny','Pustą listę','Użytkownik wie, co się dzieje.'],
['Jak zaproponować aktualizację?','Baner z przyciskiem Odśwież','Przeładować bez pytania','Alert co minutę','Wylogować','Bez utraty niezapisanych danych.'],
['Po co registration.update() okresowo?','Aplikacja bywa otwarta cały dzień','Dla CSS','Dla manifestu','Dla Docker','Przeglądarka sprawdza rzadziej.'],
['Czym jest wyłącznik service workera?','Wersja wyrejestrowująca się i czyszcząca cache','Ustawienie Nginx','Przycisk Reacta','Plik README','Plan na katastrofę.'],
['Co pokazuje Application w DevTools?','Service workery i Cache Storage','Tylko CSS','Tylko sieć','Tylko Reacta','Podstawowe narzędzie diagnozy.'],
['Co sprawdza Lighthouse w PWA?','Instalowalność i wymagania manifestu','Poprawność biznesową','Testy jednostkowe','Bazę','Nie sprawdza całego scenariusza offline.'],
['Czy test jsdom sprawdza service workera?','Nie, potrzebna przeglądarka','Tak','Tak, z Vitest','Tak, po buildzie','Sprawdzamy model i wynik budowania.'],
['Co sprawdza test wyniku budowania?','Że sw.js i manifest powstały poprawnie','Działanie offline','Instalację','Czytnik','Dowód częściowy.'],
['Czym różni się Cache Storage od cache HTTP?','Cache Storage jest sterowany przez kod','Niczym','HTTP jest w JS','Storage tylko CSS','Różne warstwy.'],
['Czy Cache Storage ma limit?','Tak, zależny od przeglądarki','Nie','10 MB zawsze','1 GB zawsze','Stąd expiration.'],
['Czym jest IndexedDB?','Asynchroniczną bazą w przeglądarce','Plikiem CSS','Tabelą SQL na serwerze','Ciasteczkiem','Dobre dla kolejek i dużych danych.'],
['Czemu nie localStorage dla kolejki z plikami?','Mały limit i synchroniczny dostęp','Nie działa','Jest szyfrowany','Blokuje SW','SW też nie ma do niego dostępu.'],
['Czy service worker widzi localStorage?','Nie','Tak','Tylko w Chrome','Tylko w dev','Dostępne są Cache i IndexedDB.'],
['Jaki jest główny koszt PWA?','Złożoność aktualizacji i spójności','Licencja','Wymóg sklepu','Brak TS','Wymaga przemyślenia wersji i cache.'],
['Kiedy PWA się nie opłaca?','Gdy offline i instalacja nie mają wartości','Zawsze','Nigdy','Przy HTTPS','Dodaje pracę i ryzyko.'],
['Co dać technikowi w piwnicy?','Ostatnie dane, kolejkę i jasny komunikat','Biały ekran','Dinozaura','Reload w pętli','To jest problem tej lekcji.'],
['Co zrobić z formularzem przy aktualizacji?','Poczekać na decyzję użytkownika','Przeładować od razu','Usunąć dane','Wylogować','Niezapisane dane są ważniejsze.'],
['Co testować po aktualizacji SW?','Pełny scenariusz: build, wdrożenie, baner, odświeżenie','Tylko typy','Tylko kolor','Tylko manifest','Sam kod nie pokazuje cyklu życia.'],
];
const header=String.raw`import {cacheFirst,networkFirst,staleWhileRevalidate,strategyFor} from "./cacheStrategies";
import {auditManifest} from "./manifestAudit";
import {clearUserCaches,createOfflineQueue,isStaleVersion,type Change} from "./offlineQueue";
const offline=async():Promise<string>=>{throw new TypeError("offline");};
const c=(id:string):Change=>({id,ticketId:1,text:"N",version:1});
const good={name:"D",short_name:"D",start_url:"/",display:"standalone",icons:[{src:"a",sizes:"192x192"},{src:"b",sizes:"512x512"},{src:"c",sizes:"512x512",purpose:"maskable"}]};`;
const S=['NetworkFirst','CacheFirst','StaleWhileRevalidate','NetworkOnly'];
const probeRows=[
['Strategia POST','strategyFor({method:"POST",pathname:"/api/tickets"})','NetworkOnly','Zapis nie idzie do cache.',S],
['Strategia PATCH','strategyFor({method:"PATCH",pathname:"/api/tickets/1"})','NetworkOnly','Tak samo.',S],
['Strategia GET ticketów','strategyFor({method:"GET",pathname:"/api/tickets"})','NetworkFirst','Świeże dane z zapasem.',S],
['Strategia sesji','strategyFor({method:"GET",pathname:"/api/session"})','NetworkOnly','Dane wrażliwe.',S],
['Strategia słownika','strategyFor({method:"GET",pathname:"/api/dictionaries/x"})','StaleWhileRevalidate','Nieaktualna wartość nie szkodzi.',S],
['Strategia obrazu','strategyFor({method:"GET",pathname:"/a.png",destination:"image"})','CacheFirst','Rzadko się zmienia.',S],
['Strategia czcionki','strategyFor({method:"GET",pathname:"/a.woff2",destination:"font"})','CacheFirst','Rzadko się zmienia.',S],
['Strategia innego GET','strategyFor({method:"GET",pathname:"/inne"})','NetworkOnly','Domyślnie bez kopii.',S],
['Źródło NetworkFirst z siecią','(await networkFirst("/a",new Map(),async()=>"x")).source','sieć','Sieć działa.',['cache','brak','offline']],
['Czy NetworkFirst zapisuje wynik?','await (async()=>{const m=new Map<string,string>();await networkFirst("/a",m,async()=>"x");return m.get("/a");})()','x','Na później.',['undefined','/a','pusty tekst']],
['Źródło NetworkFirst bez sieci z wpisem','(await networkFirst("/a",new Map([["/a","y"]]),offline)).source','cache','Ostatnie znane dane.',['sieć','brak','offline']],
['Wartość NetworkFirst bez sieci z wpisem','(await networkFirst("/a",new Map([["/a","y"]]),offline)).value','y','Ostatnie znane dane.',['undefined','x','offline']],
['NetworkFirst bez sieci i wpisu','await networkFirst("/a",new Map(),offline).then(()=>"ok",e=>e.message)','offline','Brak zapasu: błąd idzie dalej.',['ok','cache','undefined']],
['Źródło CacheFirst z wpisem','(await cacheFirst("/a",new Map([["/a","y"]]),offline)).source','cache','Sieć nawet nie jest pytana.',['sieć','brak','offline']],
['Źródło CacheFirst bez wpisu','(await cacheFirst("/a",new Map(),async()=>"x")).source','sieć','Pierwszy raz z sieci.',['cache','brak','offline']],
['SWR z wpisem: wartość','staleWhileRevalidate("/a",new Map([["/a","stare"]]),async()=>"nowe").value','stare','Od razu z cache.',['nowe','undefined','[object Promise]']],
['SWR z wpisem: źródło','staleWhileRevalidate("/a",new Map([["/a","stare"]]),async()=>"nowe").source','cache','Sieć działa w tle.',['sieć','brak','offline']],
['SWR bez wpisu: źródło','staleWhileRevalidate("/a",new Map(),async()=>"nowe").source','sieć','Trzeba poczekać.',['cache','brak','offline']],
['SWR po odświeżeniu','await (async()=>{const m=new Map([["/a","stare"]]);await staleWhileRevalidate("/a",m,async()=>"nowe").refresh;return m.get("/a");})()','nowe','Na następny raz.',['stare','undefined','pusty tekst']],
['Problemy poprawnego manifestu','auditManifest(good).length',0,'Spełnia reguły.'],
['Problemy pustego manifestu','auditManifest({}).length',6,'Wszystkie wymagane pola.'],
['Pierwszy problem pustego','auditManifest({})[0]','brak name','Kolejność reguł.',['brak start_url','brak ikony 192x192','brak']],
['Tryb browser','auditManifest({...good,display:"browser"})[0]','display bez trybu aplikacji','To nie jest okno aplikacji.',['brak name','brak','brak ikony 192x192']],
['Tryb minimal-ui','auditManifest({...good,display:"minimal-ui"}).length',0,'Dozwolony.'],
['Bez maskable','auditManifest({...good,icons:good.icons.slice(0,2)})[0]','brak ikony maskable','System przytnie ikonę źle.',['brak ikony 512x512','brak','brak name']],
['Bez 192','auditManifest({...good,icons:good.icons.slice(1)})[0]','brak ikony 192x192','Wymagany rozmiar.',['brak ikony 512x512','brak','brak ikony maskable']],
['Długi short_name','auditManifest({...good,short_name:"1234567890123"})[0]','short_name zbyt długi','Etykieta na ekranie jest krótka.',['brak name','brak','brak start_url']],
['Short_name 12 znaków','auditManifest({...good,short_name:"123456789012"}).length',0,'Jeszcze dopuszczalny.'],
['Kolejka: stan po add','(()=>{const q=createOfflineQueue(async()=>"ok");q.add(c("a"));return q.pending().length;})()',1,'Czeka na wysłanie.'],
['Kolejka: flush przy sukcesie','await createOfflineQueue(async()=>"ok",[c("a")]).flush()',true,'Wszystko wysłane.'],
['Kolejka: flush przy retry','await createOfflineQueue(async()=>"retry",[c("a")]).flush()',false,'Spróbuje później.'],
['Kolejka: pusta','await createOfflineQueue(async()=>"ok").flush()',true,'Nic do zrobienia.'],
['Kolejka: elementy po retry','await (async()=>{const q=createOfflineQueue(async()=>"retry",[c("a"),c("b")]);await q.flush();return q.pending().length;})()',2,'Nic nie ginie.'],
['Kolejka: wyjątek jak retry','await (async()=>{const q=createOfflineQueue(async()=>{throw new TypeError("x");},[c("a")]);await q.flush();return q.pending().length;})()',1,'Brak sieci to nie utrata danych.'],
['Kolejka: konflikt usuwa z oczekujących','await (async()=>{const q=createOfflineQueue(async()=>"conflict",[c("a")]);await q.flush();return q.pending().length;})()',0,'Trafia do osobnej listy.'],
['Kolejka: liczba konfliktów','await (async()=>{const q=createOfflineQueue(async()=>"conflict",[c("a"),c("b")]);await q.flush();return q.conflicts().length;})()',2,'Nie znikają po cichu.'],
['Czy pending daje kopię?','(()=>{const q=createOfflineQueue(async()=>"ok",[c("a")]);q.pending().pop();return q.pending().length;})()',1,'Stan wewnętrzny jest chroniony.'],
['Wersja różna','isStaleVersion(2,1)',true,'Ktoś zmienił ticket.'],
['Wersja ta sama','isStaleVersion(2,2)',false,'Można zapisać.'],
['Usuwane cache','await clearUserCaches({keys:async()=>["api-a","images"],delete:async()=>true}).then(x=>x.join())','api-a','Tylko dane użytkownika.',['images','api-a,images','pusto']],
];
const project={
'src/setup.ts':nodeSafeSetup,'src/cacheStrategies.ts':strategies,'src/manifestAudit.ts':manifest,'src/useOnlineStatus.tsx':online,'src/UpdateBanner.tsx':banner,'src/offlineQueue.ts':queue,'src/pwa.test.tsx':tests,'src/build.test.ts':buildTest,
'fixture/index.html':appIndex,'fixture/main.ts':'document.title="DevDesk";\nexport {};\n',
'README.md':'DevDesk: PWA. npm install, npm test. build.test.ts uruchamia prawdziwy vite build z vite-plugin-pwa i czyta sw.js oraz manifest. Strategie cache są modelem pokazującym zasadę, bo Workbox ma własną implementację. Instalację, działanie offline i cykl życia service workera sprawdź w przeglądarce (Application, Network: Offline).\n',
};
const mutants=[
{file:'src/cacheStrategies.ts',before:'if(method!=="GET")return "NetworkOnly";',after:''},
{file:'src/cacheStrategies.ts',before:'if(pathname.startsWith("/api/session"))return "NetworkOnly";',after:''},
{file:'src/cacheStrategies.ts',before:'Promise.race([fetcher(url),timeout(limitMs)])',after:'fetcher(url)'},
{file:'src/cacheStrategies.ts',before:'return {value:cached??refresh,refresh,source:cached===undefined?"sieć":"cache"};',after:'return {value:refresh,refresh,source:"sieć"};'},
{file:'src/manifestAudit.ts',before:'if(!(manifest.icons??[]).some(icon=>icon.purpose?.includes("maskable")))problems.push("brak ikony maskable");',after:''},
{file:'src/useOnlineStatus.tsx',before:'window.addEventListener("offline",callback);',after:''},
{file:'src/UpdateBanner.tsx',before:'{needRefresh&&<button type="button" onClick={onRefresh}>Odśwież</button>}',after:'<button type="button" onClick={onRefresh}>Odśwież</button>'},
{file:'src/offlineQueue.ts',before:'if(result==="retry")return false;',after:''},
{file:'src/offlineQueue.ts',before:'if(result==="conflict")conflicts.push(done);',after:''},
{file:'src/offlineQueue.ts',before:'names.filter(name=>name.startsWith(prefix))',after:'names'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
