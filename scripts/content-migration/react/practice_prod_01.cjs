const {lesson}=require('./practice_kit.cjs');
const types=String.raw`export type TicketStatus="OPEN"|"IN_PROGRESS"|"CLOSED";
export type Ticket={id:number;title:string;status:TicketStatus};`;
const sorting=String.raw`import type {Ticket} from "./types";

export function sortByTitle(tickets:readonly Ticket[]){
  return tickets.toSorted((a,b)=>a.title.localeCompare(b.title,"pl"));
}`;
const ticketsApi=String.raw`import type {Ticket} from "../model/types";

export async function loadTickets(fetcher:typeof fetch=fetch):Promise<Ticket[]>{
  const response=await fetcher("/api/tickets");
  if(!response.ok)throw Error("Serwer zwrócił "+response.status);
  return response.json();
}`;
const statusBadge=String.raw`import {Badge} from "@/shared/ui/Badge";
import type {TicketStatus} from "../model/types";

const LABEL:Record<TicketStatus,string>={OPEN:"Otwarty",IN_PROGRESS:"W toku",CLOSED:"Zamknięty"};

export function StatusBadge({status}:{status:TicketStatus}){
  return <Badge>{LABEL[status]}</Badge>;
}`;
const ticketTable=String.raw`import {StatusBadge} from "./StatusBadge";
import {sortByTitle} from "../model/sorting";
import type {Ticket} from "../model/types";

export function TicketTable({tickets}:{tickets:readonly Ticket[]}){
  return (
    <ul>
      {sortByTitle(tickets).map(ticket=>(
        <li key={ticket.id}>{ticket.title} <StatusBadge status={ticket.status}/></li>
      ))}
    </ul>
  );
}`;
const ticketsIndex=String.raw`// Publiczne API: jedyne drzwi do modułu. TicketTable i loadTickets są szczegółami.
export {TicketsPage} from "./pages/TicketsPage";
export {StatusBadge} from "./components/StatusBadge";
export type {Ticket,TicketStatus} from "./model/types";`;
const ticketsPage=String.raw`import {useEffect,useState} from "react";
import {loadTickets} from "../api/ticketsApi";
import {TicketTable} from "../components/TicketTable";
import type {Ticket} from "../model/types";

export function TicketsPage(){
  const [tickets,setTickets]=useState<Ticket[]|null>(null);
  useEffect(()=>{
    let active=true;
    loadTickets().then(list=>{if(active)setTickets(list);},()=>{if(active)setTickets([]);});
    return ()=>{active=false;};
  },[]);
  if(!tickets)return <p role="status">Ładowanie…</p>;
  return <TicketTable tickets={tickets}/>;
}`;
const boardIndex=String.raw`export {BoardCard} from "./BoardCard";`;
const boardCard=String.raw`import {StatusBadge,type Ticket} from "@/features/tickets";

// import type i publiczne API: tablica nie zna wnętrza modułu ticketów.
export function BoardCard({ticket}:{ticket:Ticket}){
  return <article>{ticket.title} <StatusBadge status={ticket.status}/></article>;
}`;
const badge=String.raw`import type {ReactNode} from "react";

export function Badge({children}:{children:ReactNode}){
  return <span className="badge">{children}</span>;
}`;
const appFile=String.raw`import {TicketsPage} from "@/features/tickets";

export function App(){
  return <main><h1>DevDesk</h1><TicketsPage/></main>;
}`;
const rules=String.raw`export type Violation={file:string;specifier:string;rule:string};

function layerOf(path:string){
  const match=/^src\/(app|features|shared)(?:\/([^/]+))?/.exec(path);
  return match?{layer:match[1],feature:match[1]==="features"?match[2]:undefined}:null;
}

function normalize(from:string,specifier:string){
  if(specifier.startsWith("@/"))return "src/"+specifier.slice(2);
  if(!specifier.startsWith("."))return null;
  const parts=from.split("/").slice(0,-1);
  for(const part of specifier.split("/")){
    if(part===".")continue;
    if(part==="..")parts.pop();
    else parts.push(part);
  }
  return parts.join("/");
}

export function checkImport(file:string,specifier:string):Violation|null{
  const from=layerOf(file);
  const target=normalize(file,specifier);
  if(!from||!target)return null;
  const to=layerOf(target);
  if(!to)return null;
  if(from.layer==="shared"&&to.layer!=="shared")return {file,specifier,rule:"shared nie zależy od features ani app"};
  if(from.layer==="features"&&to.layer==="app")return {file,specifier,rule:"features nie zależą od app"};
  if(to.layer==="features"&&from.feature!==to.feature){
    const deep=target.split("/").length>3;
    if(deep)return {file,specifier,rule:"import tylko z publicznego API funkcji"};
  }
  return null;
}

// Parser na potrzeby lekcji: pomija import type, bo nie tworzy zależności w czasie działania.
export function runtimeImports(source:string){
  const found:string[]=[];
  for(const match of source.matchAll(/^\s*(?:import|export)\s+(type\s+)?[^;'"]*?from\s+["']([^"']+)["']/gm)){
    if(!match[1])found.push(match[2]);
  }
  for(const match of source.matchAll(/^\s*import\s+["']([^"']+)["']/gm))found.push(match[1]);
  return found;
}

export type Graph=Record<string,readonly string[]>;

export function findCycles(graph:Graph){
  const cycles:string[][]=[];
  const seen=new Set<string>();
  function visit(node:string,path:string[]){
    const at=path.indexOf(node);
    if(at>=0){cycles.push([...path.slice(at),node]);return;}
    if(seen.has(node))return;
    seen.add(node);
    for(const next of graph[node]??[])visit(next,[...path,node]);
  }
  for(const node of Object.keys(graph))visit(node,[]);
  return cycles;
}`;
const config=String.raw`import {defineConfig} from "vitest/config";
import {fileURLToPath} from "node:url";

export default defineConfig({
  resolve:{alias:{"@":fileURLToPath(new URL("./src",import.meta.url))}},
  test:{environment:"jsdom",include:["src/**/*.test.ts","src/**/*.test.tsx"],setupFiles:["./src/setup.ts"]},
});`;
const viteConfig=String.raw`// vite.config.ts
import react from "@vitejs/plugin-react";
import {fileURLToPath,URL} from "node:url";
import {defineConfig} from "vite";

export default defineConfig({
  plugins:[react()],
  resolve:{alias:{"@":fileURLToPath(new URL("./src",import.meta.url))}},
});

// tsconfig.app.json:
// {"compilerOptions":{"baseUrl":".","paths":{"@/*":["src/*"]}}}`;
const eslint=String.raw`// eslint.config.js
export default [
  {
    files:["src/**/*.{js,jsx,ts,tsx}"],
    rules:{"no-restricted-imports":["error",{patterns:[{group:["@/features/*/*"],message:"Importuj z publicznego API funkcji."}]}]},
  },
  {
    files:["src/shared/**/*"],
    rules:{"no-restricted-imports":["error",{patterns:[{group:["@/features/*","@/app/*"],message:"shared nie może zależeć od features ani app."}]}]},
  },
];`;
const tests=String.raw`// @vitest-environment jsdom
import {readdirSync,readFileSync,statSync} from "node:fs";
import {join,relative} from "node:path";
import {afterEach,expect,test,vi} from "vitest";
import {render,screen} from "@testing-library/react";
import {checkImport,findCycles,runtimeImports,type Graph} from "./architecture";
import {sortByTitle} from "./features/tickets/model/sorting";
import {loadTickets} from "./features/tickets/api/ticketsApi";
import {App} from "./app/App";
import {BoardCard} from "@/features/board";
import type {Ticket} from "@/features/tickets";

const root=process.cwd();
function sourceFiles(dir:string):string[]{
  return readdirSync(dir).flatMap(name=>{
    const path=join(dir,name);
    return statSync(path).isDirectory()?sourceFiles(path):/\.tsx?$/.test(name)&&!/\.test\./.test(name)?[path]:[];
  });
}
const files=["app","features","shared"].flatMap(area=>sourceFiles(join(root,"src",area))).map(path=>relative(root,path).replaceAll("\\","/"));
const known=new Set(files);
function resolveFile(file:string,specifier:string){
  const base=specifier.startsWith("@/")?"src/"+specifier.slice(2):new URL(specifier,"file:///"+file).pathname.slice(1);
  return [base+".ts",base+".tsx",base+"/index.ts",base+"/index.tsx"].find(path=>known.has(path));
}

afterEach(()=>vi.unstubAllGlobals());

test("cały kod lekcji przestrzega granic modułów",()=>{
  const violations=files.flatMap(file=>runtimeImports(readFileSync(join(root,file),"utf8")).map(specifier=>checkImport(file,specifier)).filter(Boolean));
  expect(violations).toEqual([]);
  expect(files.length).toBeGreaterThan(8);
});

test("rzeczywisty graf importów nie ma cykli",()=>{
  const graph:Graph=Object.fromEntries(files.map(file=>[file,runtimeImports(readFileSync(join(root,file),"utf8")).flatMap(specifier=>{
    const resolved=resolveFile(file,specifier);
    return resolved?[resolved]:[];
  })]));
  expect(Object.values(graph).flat().length).toBeGreaterThan(5);
  expect(findCycles(graph)).toEqual([]);
});

test("reguły zgłaszają złamanie granic",()=>{
  expect(checkImport("src/features/board/BoardCard.tsx","@/features/tickets/components/TicketTable")?.rule).toBe("import tylko z publicznego API funkcji");
  expect(checkImport("src/features/board/BoardCard.tsx","@/features/tickets")).toBeNull();
  expect(checkImport("src/shared/ui/Badge.tsx","@/features/tickets")?.rule).toBe("shared nie zależy od features ani app");
  expect(checkImport("src/shared/ui/Badge.tsx","@/app/App")?.rule).toBe("shared nie zależy od features ani app");
  expect(checkImport("src/features/tickets/index.ts","@/app/App")?.rule).toBe("features nie zależą od app");
  expect(checkImport("src/app/App.tsx","@/features/tickets/components/TicketTable")?.rule).toBe("import tylko z publicznego API funkcji");
});

test("funkcja może importować z własnego wnętrza i z bibliotek",()=>{
  expect(checkImport("src/features/tickets/components/TicketTable.tsx","../model/sorting")).toBeNull();
  expect(checkImport("src/features/tickets/components/TicketTable.tsx","react")).toBeNull();
  expect(checkImport("src/features/tickets/pages/TicketsPage.tsx","@/shared/ui/Badge")).toBeNull();
  expect(checkImport("src/app/App.tsx","@/shared/ui/Badge")).toBeNull();
});

test("wykrywanie cykli i import type",()=>{
  expect(findCycles({a:["b"],b:["a"]})).toEqual([["a","b","a"]]);
  expect(findCycles({a:["b"],b:["c"],c:[]})).toEqual([]);
  expect(findCycles({a:["a"]})).toEqual([["a","a"]]);
  expect(runtimeImports('import type {T} from "./t";\nimport {x} from "./x";\nexport {y} from "./y";\nimport "./style.css";')).toEqual(["./x","./y","./style.css"]);
});

test("model działa bez Reacta i bez mutacji",()=>{
  const tickets:Ticket[]=[{id:2,title:"Żółty",status:"OPEN"},{id:1,title:"Awaria",status:"OPEN"}];
  expect(sortByTitle(tickets).map(ticket=>ticket.id)).toEqual([1,2]);
  expect(tickets[0].id).toBe(2);
});

test("warstwa api sprawdza odpowiedź",async()=>{
  const ok=vi.fn(async()=>new Response(JSON.stringify([{id:1}])));
  await expect(loadTickets(ok as unknown as typeof fetch)).resolves.toEqual([{id:1}]);
  const bad=vi.fn(async()=>new Response(null,{status:500}));
  await expect(loadTickets(bad as unknown as typeof fetch)).rejects.toThrow("500");
});

test("aplikacja składa się przez publiczne API i alias",async()=>{
  vi.stubGlobal("fetch",vi.fn(async()=>new Response(JSON.stringify([{id:1,title:"Awaria",status:"OPEN"}]))));
  render(<App/>);
  expect(await screen.findByText("Otwarty")).toBeVisible();
  expect(screen.getByRole("heading",{name:"DevDesk"})).toBeVisible();
  const view=render(<BoardCard ticket={{id:2,title:"Karta",status:"CLOSED"}}/>);
  expect(view.container).toHaveTextContent("Karta Zamknięty");
});`;
const exercises=[
['Rozstrzygnij strukturę DevDesk: według typu czy funkcji.','Co zmienia się razem?','Do około 20 komponentów płaska struktura wystarcza. DevDesk ma kilka obszarów biznesowych, więc podział app/, features/ i shared/ grupuje razem to, co zmienia się razem. Nie projektuj tego na zapas w małym projekcie.'],
['Wypisz, co należy do app, features i shared.','shared nie wie nic o domenie.','app: start, providery, router, globalne style. features: tickets, board, auth, dashboard, razem z testami. shared: Button, Modal, formatowanie dat, hooki ogólne. StatusBadge zna statusy, więc należy do features/tickets.'],
['Zdefiniuj model ticketów niezależny od Reacta.','Typy i czyste funkcje łatwo przetestować.',types],
['Napisz czystą funkcję sortowania w model.','Bez mutacji i bez importów Reacta.','// src/features/tickets/model/sorting.ts\n'+sorting],
['Przenieś fetch do warstwy api.','Komponent nie woła fetch bezpośrednio.','// src/features/tickets/api/ticketsApi.ts\n'+ticketsApi],
['Napisz publiczne API funkcji tickets.','Jedyne drzwi do modułu.',ticketsIndex],
['Eksportuj typy przez publiczne API.','export type znika przy kompilacji.',ticketsIndex],
['Złóż stronę w warstwie pages.','Strona łączy api i komponenty.','// src/features/tickets/pages/TicketsPage.tsx\n'+ticketsPage],
['Użyj komponentu shared/ui w funkcji.','Zależność płynie w jedną stronę.','// src/features/tickets/components/StatusBadge.tsx\n'+statusBadge],
['Zaimportuj typ i komponent z innej funkcji przez API.','Nie sięgaj do wnętrza modułu.','// src/features/board/BoardCard.tsx\n'+boardCard],
['Dodaj alias @ w Vite i tsconfig.','Kompilator i bundler muszą znać alias.',viteConfig],
['Dodaj alias do konfiguracji testów.','Vitest też rozwiązuje importy.',config],
['Dodaj reguły no-restricted-imports.','Zasady bez automatu łamią się po tygodniu.',eslint],
['Napisz funkcję sprawdzającą jeden import.','Warstwa pliku i warstwa celu.',rules],
['Rozwiąż ścieżkę względną i alias do jednej postaci.','Bez tego reguły są zbyt łatwe do obejścia.',rules],
['Znajdź importy czasu działania i pomiń import type.','Typ nie tworzy cyklu w czasie działania.',rules],
['Napisz wykrywanie cykli.','Stos odwiedzin ujawnia powrót do węzła.',rules],
['Sprawdź zasady na prawdziwych plikach projektu.','Test czyta źródła z dysku.',tests],
['Zbuduj graf importów i sprawdź brak cykli.','Rozwiąż specyfikator do pliku.',tests],
['Porównaj własny skaner z madge i regułami ESLint.','Własny skaner na tej lekcji pokazuje zasadę.','Skaner tekstowy jest prosty i nie rozumie dynamicznych importów ani całej składni. W projekcie użyj madge --circular src, eslint-plugin-boundaries lub dependency-cruiser. Te narzędzia korzystają z parsera i są utrzymywane przez innych.'],
['Rozwiąż cykl między tickets i board.','Trzy typowe wyjścia.','Przenieś wspólny kod do shared, jeśli nie ma wiedzy domenowej, albo do trzeciego modułu. Importuj tylko typy przez import type. Odwróć zależność: board dostaje dane ticketu przez props lub kontekst i nie importuje hooków ticketów.'],
['Rozpoznaj objawy importu cyklicznego.','Objawy bywają dziwne.','undefined is not a function przy starcie, komponent „nie istnieje” tylko w buildzie, HMR przeładowuje całą stronę. Kolejność inicjalizacji modułów zależy od punktu wejścia, dlatego błąd może się pojawiać nieregularnie.'],
['Oceń plik-beczkę w shared/ui.','Jeden index na granicy funkcji wystarczy.','Re-eksport wszystkiego ze wszystkiego utrudnia tree shaking, spowalnia narzędzia i sprzyja cyklom. W shared/ui importuj bezpośrednio @/shared/ui/Button. Publiczne API funkcji to świadoma granica, a nie zbiór wszystkiego.'],
['Zaplanuj przeprowadzkę bez zmiany zachowania.','Małe kroki i testy po każdym.','1. Utwórz katalogi. 2. Przenieś providery i router. 3. Przenieś generyczne komponenty. 4. Przenoś jedną funkcję naraz z testami. 5. Napisz index. 6. Dodaj alias. 7. Dodaj reguły. 8. Po każdym kroku testy, tsc --noEmit i build. Każdy krok to osobny commit.'],
['Określ, gdzie trafia nowy plik „obserwowanie ticketu”.','Dotyczy jednej funkcji.','Do features/tickets: hook i api w tej funkcji, a przycisk w components. Jeżeli inna funkcja potrzebuje przycisku, eksportuj go przez publiczne API. Do shared trafia wyłącznie element bez wiedzy o ticketach, np. ogólny ToggleButton.'],
['Dobierz poziom struktury do zespołu.','Przy 100 komponentach i 2 osobach.','Features, shared, app i publiczne API. Reguły lintera już się opłacają, CODEOWNERS na razie nie. Monorepo dopiero przy kilku aplikacjach współdzielących kod lub przy dużym zespole. Więcej struktury niż potrzeba spowalnia pracę.'],
['Zastosuj CODEOWNERS.','Właściciel katalogu widzi zmiany.','src/features/tickets/ @zespol-tickets\nsrc/shared/ @zespol-platforma\nPull request zmieniający te ścieżki prosi o przegląd właściciela. To narzędzie dla dużego zespołu: pomaga, gdy naruszenie granicy to także decyzja organizacyjna.'],
['Wyjaśnij, dlaczego testy leżą obok plików.','Czy przeniesienie modułu zgubi test?','TicketTable.test.tsx obok TicketTable.tsx przenosi się razem z nim i od razu widać brak testu. Osobny katalog tests wymaga odtwarzania struktury. Te same reguły dotyczą plików stylów modułu.'],
['Znajdź błędy w tych importach.','Sprawdź kierunek i publiczne API.','shared/ui/Button importuje @/features/auth: błąd kierunku. features/board importuje @/features/tickets/components/TicketTable: omija publiczne API. features/tickets importuje @/app/router: błąd kierunku. Popraw przez props, API funkcji lub przeniesienie do shared.'],
['Sprawdź kryterium akceptacji lekcji.','Lint, shared, cykle, build.','Żaden plik spoza funkcji nie importuje z jej wnętrza; shared nie zależy od features; madge --circular zwraca pustą listę; testy, sprawdzanie typów i build przechodzą bez zmian w działaniu aplikacji.'],
];
const concepts=[
['Co grupuje podział według funkcji?','To, co zmienia się razem','Pliki tego samego typu','Pliki o podobnej nazwie','Pliki według autora','Jedna funkcja to jeden katalog.'],
['Kiedy płaska struktura wystarcza?','Do około 20 komponentów','Zawsze','Nigdy','Przy 500 komponentach','Nie projektuj na zapas.'],
['Co należy do app?','Start, providery, router','Hooki ticketów','Button','Formatowanie dat','To warstwa składająca aplikację.'],
['Co należy do shared?','Kod bez wiedzy o domenie','Status ticketu','Strona logowania','Router','Button i formatowanie dat to przykłady.'],
['Czy shared może importować z features?','Nie','Tak','Tak, gdy jest potrzeba','Tak, przez index','Zależności płyną w jedną stronę.'],
['Czy features mogą importować z app?','Nie','Tak','Tylko typy','Tylko router','app jest na szczycie zależności.'],
['Czy app może importować z features?','Tak, przez publiczne API','Nie','Tylko typy','Tylko z wnętrza','To app składa funkcje razem.'],
['Czym jest publiczne API funkcji?','Plik index z tym, czego potrzebuje reszta aplikacji','Endpoint REST','Lista testów','Plik .env','Reszta to szczegół implementacji.'],
['Co zyskujesz na publicznym API?','Wnętrze można zmieniać bez wpływu na inne moduły','Mniejszą paczkę zawsze','Brak testów','Automatyczne typy','Granica stabilizuje zależności.'],
['Czy komponent powinien wołać fetch?','Lepiej przez warstwę api i hook','Zawsze','Tylko w useEffect bez hooka','Tylko w teście','Kierunek: komponenty, hooki, api i model.'],
['Czy model powinien zależeć od Reacta?','Nie','Tak','Tylko typy Reacta','Tylko hooki','Czyste funkcje testujesz bez renderowania.'],
['Gdzie trzymać test komponentu?','Obok komponentu','W osobnym repozytorium','W dist','W public','Przenosi się razem z kodem.'],
['Co znika po kompilacji?','import type i export type','Każdy import','Publiczne API','Kod funkcji','Nie tworzą zależności w czasie działania.'],
['Czy import type może utworzyć cykl w działaniu?','Nie','Tak','Tylko w JSX','Tylko w Vite','Znika przed uruchomieniem.'],
['Co pokaże w TSX zmiana typu Ticket?','Wszystkie miejsca do poprawy w modułach','Nic','Tylko testy','Tylko CSS','Typy są kontraktem.'],
['Jak opisać kontrakt w JSX?','JSDoc @typedef lub testy','Nie da się w żaden sposób','Przez CSS','Przez .env','Rozjazd wyjdzie później niż w TSX.'],
['Gdzie musi być alias w projekcie TS?','W tsconfig i konfiguracji Vite','Tylko w tsconfig','Tylko w package.json','W .env','Albo wtyczka vite-tsconfig-paths.'],
['Co robi vite-tsconfig-paths?','Czyta ścieżki z tsconfig','Tworzy katalogi','Sprawdza typy','Buduje Dockera','Nie powtarzasz aliasów.'],
['Co jest potrzebne w testach przy aliasie?','Alias w konfiguracji Vitest lub Vite','Nic','Serwer','Przeglądarka','Testy rozwiązują importy tak samo.'],
['Co robi no-restricted-imports?','Zabrania wskazanych wzorców importu','Wyszukuje cykle','Ładuje moduły','Sprawdza kontrast','Pilnuje granic bez przeglądu ręcznego.'],
['Który wzorzec blokuje import z głębi funkcji?','@/features/*/*','@/features','@/shared','react','Dozwolone jest samo @/features/nazwa.'],
['Jaki wzorzec daje reguła dla shared?','@/features/* i @/app/*','react','@/shared/*','./*','Shared nie może sięgać wyżej.'],
['Jakie narzędzia pilnują granic szerzej?','eslint-plugin-boundaries, dependency-cruiser','Prettier','Sentry','DOMPurify','Dla dużych projektów.'],
['Co wykrywa madge --circular?','Cykle importów','Martwy kod','Sekrety','Błędy typów','Wynik powinien być pusty.'],
['Jaką regułą ESLint wykryjesz cykl?','import/no-cycle','no-console','react/no-danger','no-var','Działa, choć bywa wolna.'],
['Jaki jest objaw cyklu?','undefined is not a function przy starcie','Zły kolor','Dłuższy CSS','Brak .env','Kolejność inicjalizacji się psuje.'],
['Co przenieść, gdy dwie funkcje dzielą kod bez domeny?','Do shared','Do jednej z nich','Do dist','Do README','Wspólny kod bez wiedzy domenowej.'],
['Co zrobić, gdy wspólny kod zna domenę?','Stworzyć trzeci moduł','Dać do shared mimo to','Skopiować','Usunąć','shared nie może znać domeny.'],
['Jak odwrócić zależność board od tickets?','Przekazać dane przez props lub kontekst','Zaimportować z głębi','Dodać globalną zmienną','Skopiować hook','Moduł przestaje znać konkretną implementację.'],
['Dlaczego pliki-beczki w shared są ryzykowne?','Utrudniają tree shaking i sprzyjają cyklom','Są zabronione','Nie działają w Vite','Blokują TS','Jeden index na granicy funkcji wystarczy.'],
['Ile index-ów powinna mieć funkcja?','Jeden na granicy','Jeden na każdy katalog','Żadnego','Po jednym na plik','Wewnątrz wystarczą bezpośrednie importy.'],
['Czym grozi eksport wszystkiego przez index?','Znika granica modułu','Niczym','Szybszy build','Lepszy cache','Wtedy wnętrze i tak jest publiczne.'],
['Co oznacza „drzwi do modułu”?','Plik index jako jedyne wejście','Plik CSS','Trasa routera','Port serwera','Metafora publicznego API.'],
['Co jest celem przeprowadzki?','Brak zmiany zachowania','Nowe funkcje','Nowy wygląd','Więcej plików','Refaktoryzacja nie zmienia działania.'],
['Co uruchamiać po przeprowadzce?','Testy, tsc --noEmit i build','Tylko dev','Tylko Lighthouse','Tylko Prettier','Build wykrywa problemy aliasów.'],
['W jakich krokach przenosić?','Funkcja po funkcji z commitami','Wszystko jednym commitem','Bez testów','Tylko w nocy','Małe kroki łatwo cofnąć.'],
['Czym są CODEOWNERS?','Właściciele ścieżek w repozytorium','Klucze API','Role ARIA','Zmienne CI','Pomagają w dużym zespole.'],
['Kiedy rozważyć monorepo?','Kilka aplikacji współdzieli kod','Zawsze','Przy jednym komponencie','Dla każdego MVP','Ma koszt narzędzi.'],
['Jakie narzędzia wspierają monorepo?','pnpm workspaces, Turborepo','Sentry, Motion','Recharts, Zod','DOMPurify, Vite PWA','Współdzielone pakiety i cache budowania.'],
['Gdzie trafia StatusBadge?','Do funkcji tickets, bo zna statusy','Do shared','Do app','Do public','Zna domenę.'],
['Gdzie trafia Badge?','Do shared/ui','Do features/tickets','Do app','Do dist','Nie zna domeny.'],
['Gdzie trafia formatDate?','Do shared/lib','Do features/board','Do public','Do testów','Nie zna domeny.'],
['Gdzie trafia useTicketFilters?','features/tickets/hooks','shared/ui','app','public','Zna filtry ticketów.'],
['Gdzie trafia schemat Zod ticketu?','features/tickets/model','shared/ui','app/router','dist','Zna kształt ticketu.'],
['Gdzie trafia konfiguracja providerów?','app','features','shared/ui','model','Składa aplikację.'],
['Co zrobić, gdy shared potrzebuje danych o użytkowniku?','Przyjąć je przez parametr lub props','Zaimportować z auth','Czytać cookie w Button','Dodać cykl','Wiedzę o domenie przekazujesz z góry.'],
['Dlaczego reguły lintera są lepsze od dokumentu?','Działają automatycznie przy każdej zmianie','Są dłuższe','Zastępują testy','Działają bez CI','Zasady bez automatu się rozjeżdżają.'],
['Czy lint wykryje każde naruszenie architektury?','Nie, ale wykryje zapisane wzorce','Tak','Tak, z TS','Tylko w JS','Dynamiczne przypadki wymagają przeglądu.'],
['Czy import dynamiczny też jest zależnością?','Tak, choć ładowaną później','Nie','Tylko w produkcji','Tylko w teście','Skanery muszą go uwzględnić.'],
['Co oznacza zależność w jedną stronę?','Wyższa warstwa zna niższą, ale nie odwrotnie','Tylko jeden import','Brak importów','Importy po alfabecie','Ułatwia zmiany.'],
['Co jest ryzykiem funkcji importujących się nawzajem?','Cykl i trudne testowanie','Większy CSS','Brak aliasów','Wolniejszy Git','Najlepiej w ogóle unikać.'],
['Co oznacza „szczegół implementacji”?','Coś, co można zmienić bez wpływu na innych','Coś bez testów','Plik CSS','Stała','Nie eksportuj tego przez index.'],
['Czy Button powinien znać router?','Raczej nie: przyjmuje onClick lub to jako props','Tak','Tak, zawsze','Tylko w TSX','shared nie zna app.'],
['Czy można podzielić funkcję na podkatalogi?','Tak, gdy plików jest dużo','Nie','Zawsze od razu','Tylko w shared','Struktura rośnie razem z potrzebą.'],
['Co oznacza layer w tym kontekście?','Warstwa z określonym kierunkiem zależności','Warstwa CSS','Warstwa Dockera','Warstwa canvas','Różne znaczenia słowa warto rozróżniać.'],
['Jak zapisać wyjątek od reguły?','Komentarz z uzasadnieniem i przegląd','Wyłączyć regułę globalnie','Usunąć CI','Nie zapisywać','Wyjątek ma być widoczny.'],
['Co sprawdzić przy nowym module?','Czy potrzebuje publicznego API i komu','Tylko nazwę katalogu','Tylko rozmiar','Tylko kolor','Najpierw granica, potem pliki.'],
['Czy strony należą do funkcji?','Zwykle tak, a router importuje je przez API','Nie','Tylko do shared','Tylko do public','Router w app zna tylko publiczne API.'],
['Jak zachować lazy przy publicznym API?','Dynamiczny import ścieżki strony lub osobny punkt wejścia','Nie da się','Tylko w Next','Importem statycznym','Plik index z wieloma eksportami może osłabić podział.'],
['Co jest głównym sygnałem, że struktura zawodzi?','Zmiana jednej funkcji wymaga szukania w wielu miejscach','Plików jest dużo','CI trwa minutę','Są testy','To właśnie opisuje problem lekcji.'],
];
const header=String.raw`import {checkImport,findCycles,runtimeImports} from "./architecture";
const rule=(file:string,specifier:string)=>checkImport(file,specifier)?.rule??"brak";`;
const API='import tylko z publicznego API funkcji',SHARED='shared nie zależy od features ani app',APP='features nie zależą od app';
const opts=[API,SHARED,APP,'brak'];
const r=(file,spec,out,why)=>[`Wynik sprawdzenia importu „${spec}” w pliku ${file}`,`rule("${file}","${spec}")`,out,why,opts];
const probeRows=[
r('src/features/board/A.tsx','@/features/tickets/components/T',API,'Sięga do wnętrza innej funkcji.'),
r('src/features/board/A.tsx','@/features/tickets','brak','Publiczne API jest dozwolone.'),
r('src/features/tickets/a/B.tsx','../c/D','brak','Własne wnętrze funkcji.'),
r('src/features/tickets/a/B.tsx','../../board/x/Y',API,'Ścieżka względna też może przekroczyć granicę.'),
r('src/features/tickets/a/B.tsx','../../board','brak','Względny import publicznego API innej funkcji przechodzi ten model.'),
r('src/shared/ui/B.tsx','@/features/tickets',SHARED,'Zły kierunek.'),
r('src/shared/ui/B.tsx','@/app/App',SHARED,'Zły kierunek.'),
r('src/shared/ui/B.tsx','../lib/x','brak','Wnętrze shared.'),
r('src/shared/ui/B.tsx','../../features/tickets',SHARED,'Ścieżka względna nie omija reguły.'),
r('src/features/x/A.tsx','@/app/router',APP,'Funkcje nie znają aplikacji.'),
r('src/features/x/A.tsx','@/shared/ui/B','brak','Dozwolony kierunek.'),
r('src/app/App.tsx','@/features/tickets','brak','App składa funkcje.'),
r('src/app/App.tsx','@/features/tickets/pages/P',API,'App też używa publicznego API.'),
r('src/app/App.tsx','@/shared/ui/B','brak','Dozwolony kierunek.'),
r('src/features/x/A.tsx','react','brak','Biblioteki są poza regułami.'),
r('src/features/x/A.tsx','zod','brak','Biblioteki są poza regułami.'),
r('lib/inny.ts','@/features/x/y','brak','Plik poza warstwami nie jest oceniany.'),
['Ile importów czasu działania ma ten kod?','runtimeImports(\'import {a} from "./a";\\nimport {b} from "./b";\').length',2,'Dwa zwykłe importy.'],
['Czy import type jest liczony?','runtimeImports(\'import type {T} from "./t";\').length',0,'Znika przy kompilacji.'],
['Czy export type jest liczony?','runtimeImports(\'export type {T} from "./t";\').length',0,'Też znika.'],
['Czy re-eksport jest liczony?','runtimeImports(\'export {x} from "./x";\').length',1,'Re-eksport tworzy zależność.'],
['Czy import z samym efektem jest liczony?','runtimeImports(\'import "./s.css";\').length',1,'Style też są zależnością.'],
['Czy import mieszany z type jest liczony?','runtimeImports(\'import {a,type T} from "./a";\').length',1,'Wartość a zostaje w działaniu.'],
['Jaki specyfikator ma pierwszy import?','runtimeImports(\'import {a} from "@/x";\')[0]','@/x','Alias jest zachowany.',['x','./x','brak']],
['Czy tekst w środku linii jest importem?','runtimeImports(\'const s="import x from y";\').length',0,'Dopasowanie zaczyna się od początku linii.'],
['Ile cykli ma a→b→a?','findCycles({a:["b"],b:["a"]}).length',1,'Prosty cykl.'],
['Jak wygląda ten cykl?','findCycles({a:["b"],b:["a"]})[0].join(">")','a>b>a','Ścieżka wraca do początku.',['a>b','b>a','a>a']],
['Ile cykli ma łańcuch bez powrotu?','findCycles({a:["b"],b:["c"],c:[]}).length',0,'Brak powrotu.'],
['Ile cykli ma samoimport?','findCycles({a:["a"]}).length',1,'Moduł importuje sam siebie.'],
['Ile cykli ma pusty graf?','findCycles({}).length',0,'Brak węzłów.'],
['Czy węzeł bez wpisu jest bezpieczny?','findCycles({a:["brak"]}).length',0,'Biblioteka zewnętrzna nie ma wpisu.'],
['Ile cykli ma romb bez powrotu?','findCycles({a:["b","c"],b:["d"],c:["d"],d:[]}).length',0,'Wspólna zależność to nie cykl.'],
['Długość ścieżki trójkąta','findCycles({a:["b"],b:["c"],c:["a"]})[0].length',4,'a,b,c,a.'],
['Czy cykl zaczynający się niżej jest wykryty?','findCycles({s:["a"],a:["b"],b:["a"]})[0].join(">")','a>b>a','Ścieżka pomija węzeł wejściowy.',['s>a>b>a','s>a','a>b']],
['Czy importy poza warstwą app są oceniane w test.ts?','rule("src/app/a.test.tsx","@/features/x/y/z")',API,'Testy też podlegają regule, choć można dodać wyjątek.',opts],
['Czy dwupoziomowy import funkcji jest głęboki?','rule("src/app/A.tsx","@/features/x/index")',API,'Ścieżka do pliku index omija granicę w tym modelu.',opts],
['Czy można importować shared z głębi?','rule("src/app/A.tsx","@/shared/ui/deep/Thing")','brak','Reguła dotyczy tylko features.',opts],
['Czy funkcja może zaimportować sama siebie przez alias?','rule("src/features/x/A.tsx","@/features/x/b/C")','brak','To jej własne wnętrze.',opts],
['Czy ścieżka względna do app jest wykryta z funkcji?','rule("src/features/x/A.tsx","../../app/R")',APP,'Normalizacja obejmuje oba style.',opts],
['Ile importów ma pusty plik?','runtimeImports("").length',0,'Brak importów.'],
];
const project={
'vitest.config.ts':config,
'src/features/tickets/model/types.ts':types,'src/features/tickets/model/sorting.ts':sorting,'src/features/tickets/api/ticketsApi.ts':ticketsApi,'src/features/tickets/components/StatusBadge.tsx':statusBadge,'src/features/tickets/components/TicketTable.tsx':ticketTable,'src/features/tickets/pages/TicketsPage.tsx':ticketsPage,'src/features/tickets/index.ts':ticketsIndex,
'src/features/board/BoardCard.tsx':boardCard,'src/features/board/index.ts':boardIndex,'src/shared/ui/Badge.tsx':badge,'src/app/App.tsx':appFile,'src/architecture.ts':rules,'src/architecture.test.tsx':tests,
'README.md':'DevDesk: architektura. npm install, npm test. Test czyta rzeczywiste pliki src/app, src/features i src/shared, sprawdza kierunek zależności i brak cykli. Skaner jest przykładem do nauki, a nie parserem TS: w projekcie użyj ESLint z regułami granic i madge --circular src.\n',
};
const mutants=[
{file:'src/architecture.ts',before:'if(from.layer==="shared"&&to.layer!=="shared")',after:'if(false)'},
{file:'src/architecture.ts',before:'if(from.layer==="features"&&to.layer==="app")',after:'if(false)'},
{file:'src/architecture.ts',before:'from.feature!==to.feature',after:'true'},
{file:'src/architecture.ts',before:'if(!match[1])found.push(match[2]);',after:'found.push(match[2]);'},
{file:'src/architecture.ts',before:'if(at>=0){cycles.push([...path.slice(at),node]);return;}',after:'if(at>=0)return;'},
{file:'src/features/board/BoardCard.tsx',before:'import {StatusBadge,type Ticket} from "@/features/tickets";',after:'import {StatusBadge} from "@/features/tickets/components/StatusBadge";import type {Ticket} from "@/features/tickets";'},
{file:'src/shared/ui/Badge.tsx',before:'import type {ReactNode} from "react";',after:'import type {ReactNode} from "react";\nimport "@/features/tickets";'},
{file:'src/features/tickets/model/sorting.ts',before:'tickets.toSorted(',after:'(tickets as Ticket[]).sort('},
{file:'src/features/tickets/api/ticketsApi.ts',before:'if(!response.ok)throw Error("Serwer zwrócił "+response.status);',after:''},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
