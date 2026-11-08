const {lesson,nodeSafeSetup}=require('./practice_kit.cjs');
const types=String.raw`export type TicketStatus="OPEN"|"IN_PROGRESS"|"CLOSED";
export type Ticket={id:number;title:string;status:TicketStatus;assignee:string|null};`;
const schema=String.raw`import {z} from "zod";

export const ticketSchema=z.object({
  id:z.number().int(),
  title:z.string().min(1),
  status:z.enum(["OPEN","IN_PROGRESS","CLOSED"]),
  assignee:z.string().nullable(),
});

// Jedno źródło: typ wynika ze schematu, więc nie rozjedzie się z walidacją.
export type TicketFromApi=z.infer<typeof ticketSchema>;

export function parseTickets(value:unknown):TicketFromApi[]{
  return z.array(ticketSchema).parse(value);
}`;
const row=String.raw`import type {Ticket} from "./types";

type TicketRowProps={
  ticket:Ticket;
  selected:boolean;
  onSelect:(id:number)=>void;
};

/** Wiersz tabeli ticketów. */
export function TicketRow({ticket,selected,onSelect}:TicketRowProps){
  return (
    <tr className={selected?"row row--selected":"row"} onClick={()=>onSelect(ticket.id)}>
      <td>{ticket.id}</td>
      <td>{ticket.title}</td>
      <td>{ticket.status}</td>
      <td>{ticket.assignee??"—"}</td>
    </tr>
  );
}`;
const fixes=String.raw`import {useRef,useState,type ChangeEvent} from "react";

type Ticket={id:number;title:string};

export function isTicket(value:unknown):value is Ticket{
  if(typeof value!=="object"||value===null)return false;
  const candidate=value as Record<string,unknown>;
  return typeof candidate.id==="number"&&typeof candidate.title==="string";
}

export function parseStored(raw:string):Ticket[]{
  let parsed:unknown;
  try{parsed=JSON.parse(raw);}catch{return [];}
  return Array.isArray(parsed)?parsed.filter(isTicket):[];
}

export function MigrationFixes({onSave}:{onSave:(title:string)=>void}){
  const [tickets,setTickets]=useState<Ticket[]>([]);
  const [selectedId,setSelectedId]=useState<number|null>(null);
  const inputRef=useRef<HTMLInputElement>(null);

  function handleChange(event:ChangeEvent<HTMLInputElement>){
    setSelectedId(Number(event.target.value)||null);
  }

  const labels:Record<string,string>={OPEN:"Otwarte",CLOSED:"Zamknięte"};

  return (
    <div>
      <input ref={inputRef} onChange={handleChange} aria-label="ID"/>
      <button type="button" onClick={()=>inputRef.current?.focus()}>Fokus</button>
      <button type="button" onClick={()=>setTickets(parseStored('[{"id":1,"title":"A"},{"id":"x"}]'))}>Wczytaj</button>
      <button type="button" onClick={()=>onSave(labels.OPEN)}>Zapisz</button>
      <p>{tickets.length} / {selectedId??"—"}</p>
    </div>
  );
}`;
const stats=String.raw`export type SourceFile={path:string;text:string};

export function isScript(path:string){
  return /\.(js|jsx|ts|tsx)$/.test(path)&&!/\.d\.ts$/.test(path);
}

// Prosty raport postępu. Wzorce tekstowe nie zastępują reguł ESLint, a wskaźniki mają tylko maleć.
export function migrationStats(files:readonly SourceFile[]){
  const scripts=files.filter(file=>isScript(file.path));
  const js=scripts.filter(file=>/\.jsx?$/.test(file.path)).length;
  const ts=scripts.length-js;
  const sum=(pattern:RegExp)=>scripts.reduce((total,file)=>total+(file.text.match(pattern)?.length??0),0);
  return {
    js,
    ts,
    percent:scripts.length?Math.round(ts/scripts.length*100):100,
    any:sum(/(?::|\bas|<)\s*any\b/g),
    tsIgnore:sum(/@ts-ignore/g),
    undescribedExpectError:sum(/@ts-expect-error[ \t]*(?=\r?\n|$)/g),
  };
}

export type Stats=ReturnType<typeof migrationStats>;

export function regressions(before:Stats,after:Stats){
  const problems:string[]=[];
  if(after.js>before.js)problems.push("przybyło plików JS");
  if(after.any>before.any)problems.push("przybyło any");
  if(after.tsIgnore>0)problems.push("@ts-ignore w kodzie");
  if(after.undescribedExpectError>0)problems.push("@ts-expect-error bez opisu");
  return problems;
}

export function canDisableAllowJs(stats:Stats){
  return stats.js===0;
}

const ORDER=["model","api","logic","shared","feature"] as const;
export type Layer=typeof ORDER[number];

export function migrationOrder(layer:Layer){
  return ORDER.indexOf(layer);
}

export function nextLayer(done:readonly Layer[]):Layer|null{
  return ORDER.find(layer=>!done.includes(layer))??null;
}`;
const tsc=String.raw`import ts from "typescript";

type Options={strict?:boolean;allowJs?:boolean;checkJs?:boolean;noImplicitAny?:boolean;strictNullChecks?:boolean};

// Prawdziwy kompilator w pamięci: wynik pochodzi z tsc, a nie z założenia autora lekcji.
export function diagnose(source:string,options:Options={},fileName="plik.ts"){
  const compilerOptions:ts.CompilerOptions={noEmit:true,target:ts.ScriptTarget.ES2022,lib:["lib.es2023.d.ts"],skipLibCheck:true,types:[],...options};
  const host=ts.createCompilerHost(compilerOptions);
  const read=host.readFile.bind(host),exists=host.fileExists.bind(host),get=host.getSourceFile.bind(host);
  host.fileExists=file=>file===fileName||exists(file);
  host.readFile=file=>file===fileName?source:read(file);
  host.getSourceFile=(file,version)=>file===fileName?ts.createSourceFile(file,source,version):get(file,version);
  const program=ts.createProgram([fileName],compilerOptions,host);
  return ts.getPreEmitDiagnostics(program).map(item=>"TS"+item.code);
}`;
const tsconfig=`// tsconfig.json
{
  "compilerOptions": {
    "target": "ES2022",
    "lib": ["ES2023", "DOM", "DOM.Iterable"],
    "module": "ESNext",
    "moduleResolution": "bundler",
    "jsx": "react-jsx",
    "allowJs": true,
    "checkJs": false,
    "strict": true,
    "noEmit": true,
    "isolatedModules": true,
    "skipLibCheck": true,
    "baseUrl": ".",
    "paths": { "@/*": ["src/*"] },
    "types": ["vite/client"]
  },
  "include": ["src"]
}`;
const vendor=`// types/vendor.d.ts
declare module "stara-biblioteka" {
  export function formatTicket(id: number): string;
}`;
const config=String.raw`import {defineConfig} from "vitest/config";

export default defineConfig({
  test:{environment:"jsdom",include:["src/**/*.test.ts","src/**/*.test.tsx"],setupFiles:["./src/setup.ts"],testTimeout:30000},
});`;
const tests=String.raw`import {expect,test,vi} from "vitest";
import {fireEvent,render,screen} from "@testing-library/react";
import {canDisableAllowJs,isScript,migrationOrder,migrationStats,nextLayer,regressions} from "./migrationStats";
import {isTicket,MigrationFixes,parseStored} from "./MigrationFixes";
import {parseTickets} from "./ticketSchema";
import {TicketRow} from "./TicketRow";
import {diagnose} from "./tscLab";

const ticket={id:1,title:"Awaria",status:"OPEN" as const,assignee:null};

test("raport liczy pliki i słabe miejsca",()=>{
  const stats=migrationStats([
    {path:"src/a.js",text:""},
    {path:"src/b.jsx",text:""},
    {path:"src/c.ts",text:"const x: any = 1; const y = z as any;\n// @ts-ignore\nf();"},
    {path:"src/d.tsx",text:"// @ts-expect-error\nf();\n// @ts-expect-error opis biblioteki\ng();"},
    {path:"src/e.d.ts",text:"declare const q: any;"},
    {path:"src/f.css",text:"any"},
  ]);
  expect(stats).toEqual({js:2,ts:2,percent:50,any:2,tsIgnore:1,undescribedExpectError:1});
  expect(isScript("a.d.ts")).toBe(false);
  expect(migrationStats([]).percent).toBe(100);
});

test("wskaźniki mogą tylko maleć",()=>{
  const base={js:5,ts:5,percent:50,any:3,tsIgnore:0,undescribedExpectError:0};
  expect(regressions(base,{...base,js:4,any:2})).toEqual([]);
  expect(regressions(base,{...base,js:6})).toEqual(["przybyło plików JS"]);
  expect(regressions(base,{...base,any:4})).toEqual(["przybyło any"]);
  expect(regressions(base,{...base,tsIgnore:1})).toEqual(["@ts-ignore w kodzie"]);
  expect(regressions(base,{...base,undescribedExpectError:1})).toEqual(["@ts-expect-error bez opisu"]);
  expect(canDisableAllowJs({...base,js:0})).toBe(true);
  expect(canDisableAllowJs(base)).toBe(false);
});

test("kolejność warstw od danych do widoków",()=>{
  expect(migrationOrder("model")).toBeLessThan(migrationOrder("api"));
  expect(migrationOrder("api")).toBeLessThan(migrationOrder("feature"));
  expect(nextLayer([])).toBe("model");
  expect(nextLayer(["model","api"])).toBe("logic");
  expect(nextLayer(["model","api","logic","shared","feature"])).toBeNull();
});

test("strażnik typu i niepewny JSON",()=>{
  expect(isTicket({id:1,title:"A"})).toBe(true);
  expect(isTicket({id:"1",title:"A"})).toBe(false);
  expect(isTicket(null)).toBe(false);
  expect(parseStored('[{"id":1,"title":"A"},{"id":2}]')).toEqual([{id:1,title:"A"}]);
  expect(parseStored("nie json")).toEqual([]);
  expect(parseStored('{"id":1}')).toEqual([]);
});

test("schemat daje dane i odrzuca niezgodną odpowiedź",()=>{
  expect(parseTickets([ticket])).toEqual([ticket]);
  expect(()=>parseTickets([{...ticket,status:"NOWY"}])).toThrow();
  expect(()=>parseTickets([{...ticket,assignee:undefined}])).toThrow();
  expect(()=>parseTickets({})).toThrow();
});

test("wiersz obsługuje null z API",()=>{
  const onSelect=vi.fn();
  render(<table><tbody><TicketRow ticket={ticket} selected onSelect={onSelect}/></tbody></table>);
  expect(screen.getByRole("cell",{name:"—"})).toBeVisible();
  expect(screen.getByRole("row")).toHaveClass("row--selected");
  fireEvent.click(screen.getByRole("row"));
  expect(onSelect).toHaveBeenCalledWith(1);
});

test("komponent po migracji działa bez zmiany zachowania",()=>{
  const onSave=vi.fn();
  render(<MigrationFixes onSave={onSave}/>);
  expect(screen.getByText("0 / —")).toBeVisible();
  fireEvent.change(screen.getByRole("textbox",{name:"ID"}),{target:{value:"42"}});
  expect(screen.getByText("0 / 42")).toBeVisible();
  fireEvent.click(screen.getByRole("button",{name:"Wczytaj"}));
  expect(screen.getByText("1 / 42")).toBeVisible();
  fireEvent.click(screen.getByRole("button",{name:"Fokus"}));
  expect(screen.getByRole("textbox")).toHaveFocus();
  fireEvent.click(screen.getByRole("button",{name:"Zapisz"}));
  expect(onSave).toHaveBeenCalledWith("Otwarte");
});

test("prawdziwy kompilator: strict wykrywa null i niejawne any",()=>{
  expect(diagnose("function f(x){return x;}",{strict:false})).toEqual([]);
  expect(diagnose("function f(x){return x;}",{strict:true})).toEqual(["TS7006"]);
  expect(diagnose("const n:string=null;",{strict:false})).toEqual([]);
  expect(diagnose("const n:string=null;",{strict:true})).toEqual(["TS2322"]);
});

test("prawdziwy kompilator: checkJs i JSDoc w pliku JS",()=>{
  const code="/** @type {number} */\nconst a='tekst';\nexport {a};";
  expect(diagnose(code,{allowJs:true,checkJs:false},"plik.js")).toEqual([]);
  expect(diagnose(code,{allowJs:true,checkJs:true},"plik.js")).toEqual(["TS2322"]);
});

test("prawdziwy kompilator: expect-error przypomina o usunięciu, a ignore milczy",()=>{
  expect(diagnose("// @ts-expect-error powód\nconst a:string=1;\nexport {a};")).toEqual([]);
  expect(diagnose("// @ts-expect-error powód\nconst a:string='ok';\nexport {a};")).toEqual(["TS2578"]);
  expect(diagnose("// @ts-ignore\nconst a:string='ok';\nexport {a};")).toEqual([]);
});`;
const exercises=[
['Dodaj TypeScript do działającego projektu JS.','allowJs pozwala na oba rodzaje plików.',tsconfig],
['Ustaw strict dla nowych plików.','Nowy kod od razu w trybie ścisłym.',tsconfig],
['Zaplanuj kolejność migracji.','Od danych do widoków.','1. Modele danych. 2. Warstwa API z Zod lub OpenAPI. 3. Czyste funkcje, hooki, reducery. 4. shared/ui. 5. Funkcje i strony, od najczęściej zmienianych. Każdy krok to mały pull request z działającą aplikacją.'],
['Zamień statusy na unię.','Literówka staje się błędem kompilacji.',types],
['Zapisz nullable pola zgodnie z API.','assignee może być null.',types],
['Wyprowadź typ ze schematu.','z.infer daje jedno źródło.',schema],
['Waliduj odpowiedź API.','Typ nie sprawdza danych w czasie działania.',schema],
['Przenieś wiersz tabeli na TSX.','Zmiana rozszerzenia i typ props.',row],
['Obsłuż null wskazany przez kompilator.','To prawdziwy błąd, który wcześniej działał przypadkiem.',row],
['Popraw useState([]).','Podaj typ elementów.',fixes],
['Popraw useState(null).','Podaj unię.',fixes],
['Popraw useRef(null) do elementu.','Typ elementu i sprawdzanie null.',fixes],
['Dodaj typ zdarzenia.','ChangeEvent<HTMLInputElement>.',fixes],
['Zamień dane z JSON.parse na unknown.','Strażnik typu zamiast rzutowania.',fixes],
['Napisz strażnik typu.','value is Ticket.',fixes],
['Zastąp {} słownikiem Record.','Indeksowanie tekstem wymaga typu.',fixes],
['Napisz raport postępu migracji.','Pliki JS, any, @ts-ignore.',stats],
['Zablokuj regresje wskaźników w CI.','Mają tylko maleć.',stats],
['Sprawdź warunek wyłączenia allowJs.','Brak plików JS.',stats],
['Wykaż działanie strict prawdziwym kompilatorem.','API typescript w pamięci.',tsc],
['Wykaż działanie checkJs z JSDoc.','Plik .js też może być sprawdzany.',tests],
['Porównaj @ts-expect-error z @ts-ignore.','Który przypomni o usunięciu?',tests],
['Włącz reguły lintera.','no-explicit-any i ban-ts-comment.','// eslint.config.js\n{rules:{"@typescript-eslint/no-explicit-any":"warn","@typescript-eslint/ban-ts-comment":["error",{"ts-expect-error":"allow-with-description","ts-ignore":true}]}}\n// W nowych plikach no-explicit-any jako błąd.'],
['Dodaj deklarację biblioteki bez typów.','Najpierw sprawdź pakiet @types.',vendor],
['Rozstrzygnij każdy błąd kompilatora.','Trzy możliwe reakcje.','Prawdziwy błąd: popraw kod. Brakujący typ: dopisz. Ograniczenie biblioteki: @ts-expect-error z opisem. Nie używaj any jako szybkiej odpowiedzi: ukrywa wszystkie trzy przypadki i zaraża kod zależny.'],
['Zastąp any właściwym typem.','any zaraża wszystko, co z niego korzysta.','// Źle: const data:any=await res.json(); const x=data.items; (x też jest any)\n// Dobrze: const data:unknown=await res.json(); const items=parseTickets(data);\n// unknown wymusza sprawdzenie przed użyciem.'],
['Popraw rzutowanie na dane z sieci.','as jest obietnicą, nie sprawdzeniem.','as Ticket[] nie sprawdza niczego w czasie działania. Serwer może zwrócić inny kształt. Użyj schematu parseTickets lub strażnika typu. Rzutowanie zostaw do sytuacji, które wiesz lepiej niż kompilator i potrafisz uzasadnić.'],
['Podziel migrację na małe pull requesty.','Bez zmian logiki.','Pull request „TS” tylko dodaje typy i naprawia realne błędy. Refaktoryzacja to osobny PR. Dzięki temu przegląd jest szybki, a regresję łatwo namierzyć. Zasada zespołu: nowe pliki tylko w TS, a JS, który i tak zmieniasz, migrujesz przy okazji.'],
['Wskaż, co w kursie dało najwięcej w migracji.','Pięć praktycznych zysków.','Unie literałów, strictNullChecks, typy danych z jednego źródła, typy props współdzielonych komponentów i generyki. Najmniej dają adnotacje rzeczy oczywistych, np. const count:number=0: tym zajmuje się inferencja.'],
['Sprawdź kryterium akceptacji lekcji.','Małe PR, tsc, brak ignore, any maleje, allowJs off.','Migracja w małych pull requestach z działającą aplikacją po każdym. tsc --noEmit zielone przy strict. Brak @ts-ignore, a każde @ts-expect-error ma opis. Liczba any jest raportowana i maleje. Na końcu allowJs jest wyłączone.'],
];
const concepts=[
['Co robi allowJs?','Pozwala mieszać pliki JS i TS','Sprawdza JS','Wyłącza TS','Kompiluje CSS','Import działa w obie strony.'],
['Co robi checkJs?','Sprawdza też pliki JS','Pozwala importować JS','Usuwa JS','Generuje typy','Etap pośredni z JSDoc.'],
['Jak wyłączyć sprawdzanie jednego pliku JS?','// @ts-nocheck','// @ts-ignore-all','/* no-ts */','use nocheck','Przydatne przy etapie checkJs.'],
['Jak włączyć sprawdzanie jednego pliku JS?','// @ts-check','// @ts-on','use strict','/* check */','Działa także bez checkJs.'],
['Co obejmuje strict?','Zestaw sprawdzeń, m.in. strictNullChecks','Tylko formatowanie','Tylko CSS','Tylko ESLint','Najważniejsze flagi naraz.'],
['Co robi noEmit?','tsc tylko sprawdza, nie tworzy plików','Wyłącza sprawdzanie','Blokuje Vite','Usuwa dist','Budowanie robi Vite.'],
['Po co isolatedModules?','Pilnuje, by każdy plik dał się skompilować osobno','Szybszy CSS','Wyłącza importy','Wymusza CommonJS','Vite kompiluje plik po pliku.'],
['Czy zmiana rozszerzenia wymaga zmian w Vite?','Nie','Tak','Tylko w CSS','Tylko w Dockerze','Obsługuje TS od razu.'],
['Czy Vite sprawdzi typy po migracji?','Nie, robi to tsc','Tak','Tak, w build','Tak, w dev','Stąd krok w CI.'],
['Od czego zacząć migrację?','Od modeli danych','Od stron','Od CSS','Od testów E2E','Wszystko inne z nich korzysta.'],
['Co migrować po modelach?','Warstwę API','Strony','Style','README','Dane wchodzą do aplikacji tutaj.'],
['Co migrować na końcu?','Funkcje i strony','Modele','Unie','API','Korzystają z wcześniejszych warstw.'],
['Czemu migrować od dołu?','Kolejne warstwy od razu korzystają z typów','Bo tak szybciej kompiluje','Dla CSS','Dla SEO','Korzyść rośnie z każdym krokiem.'],
['Co daje z.infer?','Typ ze schematu','Walidację sam w sobie','Mock','Klucz','Jedno źródło prawdy.'],
['Co dają typy z OpenAPI?','Kontrakt z backendem','Testy E2E','Walidację w działaniu sam w sobie','Lepszy CSS','Generowanie zmniejsza rozjazd.'],
['Czy typy sprawdzają dane w działaniu?','Nie','Tak','Tak, w strict','Tak, z as','Dlatego potrzebny jest schemat.'],
['Co oznacza „nowe pliki tylko w TS”?','JS przestaje przybywać','Trzeba przepisać wszystko','Nie można edytować JS','Brak allowJs','Prosta zasada zespołu.'],
['Co zrobić ze starym JS zmienianym w zadaniu?','Migrować przy okazji, jeśli to mały koszt','Nigdy nie ruszać','Usunąć','Zamienić na CSS','Migracja idzie tam, gdzie jest ruch.'],
['Co powinien zawierać PR migracyjny?','Typy i realne poprawki z kompilatora','Też duże refaktoryzacje','Nową funkcję','Zmianę bibliotek','Łatwy przegląd.'],
['Jaki typ daje useState([]) w TS?','never[] w typowym zapisie','any[]','unknown[]','string[]','Stąd useState<Ticket[]>([]).'],
['Jaki typ daje useState(null)?','null','any','undefined','unknown','Stąd unia z null.'],
['Jak zapisać ref do pola?','useRef<HTMLInputElement>(null)','useRef()','useRef<any>()','useRef(document)','Dostęp przez current?.'],
['Jaki typ ma zdarzenie zmiany?','ChangeEvent<HTMLInputElement>','Event','any','MouseEvent','Typ z React.'],
['Co zrobić z parametrem bez typu?','Dodać właściwy typ','Dodać any','Wyłączyć strict','Usunąć funkcję','Błąd TS7006 w strict.'],
['Jaki kod ma niejawne any?','TS7006','TS2322','TS2578','TS1005','Sprawdzamy to w lekcji kompilatorem.'],
['Co oznacza TS2322?','Niezgodność typu przy przypisaniu','Brak modułu','Nieużyty expect-error','Niejawne any','Np. string=null w strict.'],
['Co oznacza TS2578?','Nieużyty @ts-expect-error','Niejawne any','Brak typu','Brak pliku','Błąd zniknął, więc usuń komentarz.'],
['Co oznacza TS2307?','Brak modułu lub deklaracji','Złe przypisanie','Nieużyty komentarz','Złe JSX','Typowe dla bibliotek bez typów.'],
['Czym różni się unknown od any?','unknown wymaga sprawdzenia przed użyciem','Niczym','any jest bezpieczniejsze','unknown wyłącza TS','Bezpieczna wersja „nie wiem”.'],
['Co oznacza, że any zaraża?','Wartości z niego też są any','Spowalnia kompilację','Psuje CSS','Jest wirusem','Sprawdzanie znika dalej w kodzie.'],
['Czym jest as Ticket?','Obietnicą dla kompilatora','Sprawdzeniem','Konwersją','Walidacją','W działaniu nie robi nic.'],
['Czym jest strażnik typu?','Funkcją z wynikiem value is T','Klasą','Pakietem','Rolą ARIA','Zawęża typ po sprawdzeniu.'],
['Kiedy @ts-expect-error?','Znany problem, np. ograniczenie biblioteki','Zawsze zamiast typów','Do błędów logiki','Nigdy','Z opisem przyczyny.'],
['Czemu nie @ts-ignore?','Zostaje nawet po zniknięciu błędu','Nie działa','Jest wolniejsze','Dotyczy CSS','expect-error przypomni o usunięciu.'],
['Skąd typy biblioteki?','Z pakietu lub @types/nazwa','Z CSS','Z testów','Z Reacta','Sprawdź to najpierw.'],
['Co gdy nie ma @types?','declare module z minimalną deklaracją','any wszędzie','Usunąć TS','Skopiować bibliotekę','Rozbudowuj w miarę potrzeb.'],
['Gdzie umieścić taką deklarację?','types/vendor.d.ts objęty tsconfig','W dist','W public','W .env','Plik musi być w include.'],
['Co oznacza „mierz postęp”?','Liczba plików JS i any w CI','Liczba commitów','Czas pracy','Rozmiar CSS','Oba wskaźniki mają maleć.'],
['Kiedy wyłączyć allowJs?','Gdy nie ma plików JS w src','Na początku','Po pierwszym PR','Nigdy','Domknięcie migracji.'],
['Jak traktować strict przy dużym projekcie?','Stopniowo lub od razu tylko dla nowych plików','Wyłączyć na zawsze','Włączyć bez planu i dodać any','Bez znaczenia','Dobór zależy od skali.'],
['Co robi typescript-strict-plugin?','Pozwala stosować strict dla wybranych plików','Kompiluje JS','Sprawdza CSS','Generuje OpenAPI','Jedno z rozwiązań przejściowych.'],
['Co daje strictNullChecks w migracji?','Pokazuje miejsca, gdzie null wysypie render','Szybszy build','Lepszy CSS','Brak testów','Jeden z największych zysków.'],
['Co dają unie literałów?','Literówki w statusach stają się błędami','Mniejszą paczkę','Lepszy kontrast','Automatyczne testy','Działają też w reducerach.'],
['Co dają typy props w shared/ui?','Dokumentację i podpowiedzi dla zespołu','Wyższy Lighthouse','Lepszy cache','Brak przeglądu','Każde użycie jest sprawdzane.'],
['Co dają generyki w hookach?','Jedną implementację z poprawnymi typami','Większy kod zawsze','Wolniejszy render','Brak użycia w testach','np. useLocalStorage<T>.'],
['Co daje najmniej?','Adnotacje rzeczy oczywistych','Unie','Null checks','Typy API','Inferencja robi to sama.'],
['Co zrobić, gdy testy po migracji padają?','Sprawdzić zmianę zachowania','Wyłączyć testy','Dodać any','Zignorować','Migracja nie powinna zmieniać działania.'],
['Czemu testy powinny przejść bez zmian?','Testują zachowanie, a nie składnię typów','Bo TS je usuwa','Bo są w JS','Bez powodu','Dobry sprawdzian przeprowadzki.'],
['Czy pliki testów też migrować?','Tak, ale nie muszą iść pierwsze','Nigdy','Tylko E2E','Tylko CSS','Mogą zostać JS przez pewien czas.'],
['Co oznacza Record<string,string>?','Słownik tekst do tekstu','Tablicę','Unię','Klasę','Zamiast typu {}.'],
['Czemu {} jest złym typem słownika?','Oznacza „cokolwiek bez null”, nie pusty obiekt','Jest wolny','Nie działa w TS','Jest przestarzały','Częste nieporozumienie.'],
['Jak sprawdzić JSON z localStorage?','Parse w try/catch, unknown i strażnik','Rzutowanie','any','Bez sprawdzania','Dane mogą być stare lub uszkodzone.'],
['Co dzieje się z JSDoc po migracji?','Typy wchodzą w kod, opisy zostają','Musi zniknąć','Psuje build','Zmienia działanie','Komentarz nadal zasila dokumentację.'],
['Czy można używać JSDoc zamiast migracji?','Tak, część zespołów tak robi, kosztem wygody','Nie','Tylko w Node','Tylko z CSS','checkJs daje sporo korzyści.'],
['Co pokaże kompilator po dodaniu typu Ticket?','Miejsca działające przypadkiem','Tylko CSS','Nic','Tylko testy','np. brak obsługi null.'],
['Co oznacza „ograniczenie biblioteki”?','Typy nie pozwalają na poprawny przypadek','Biblioteka jest zła','Brak internetu','Stara wersja Node','Wtedy expect-error z opisem.'],
['Jak zgłosić problem z typami biblioteki?','Issue lub PR do projektu albo DefinitelyTyped','Nie da się','Mailem do TC39','Przez Sentry','Ułatwia późniejsze usunięcie obejścia.'],
['Co jest głównym ryzykiem migracji bez planu?','Utknięcie w połowie z wieloma any','Zbyt mało plików','Mniejszy kod','Lepszy cache','Dlatego wskaźniki i zasady.'],
['Czy TS zastępuje testy?','Nie','Tak','Tak, w strict','Tak, z Zod','Sprawdza typy, a nie logikę biznesową.'],
['Co oznacza „od najczęściej zmienianych”?','Korzyść pojawia się szybciej tam, gdzie jest ruch','Dla SEO','Najstarsze najpierw','Najmniejsze najpierw zawsze','Pliki bez zmian mogą poczekać.'],
];
const header=String.raw`import {canDisableAllowJs,isScript,migrationOrder,migrationStats,nextLayer,regressions} from "./migrationStats";
import {isTicket,parseStored} from "./MigrationFixes";
import {parseTickets} from "./ticketSchema";
import {diagnose} from "./tscLab";
const base={js:5,ts:5,percent:50,any:3,tsIgnore:0,undescribedExpectError:0};
const code=(text:string)=>diagnose(text).join()||"brak";
const strict=(text:string)=>diagnose(text,{strict:true}).join()||"brak";
const loose=(text:string)=>diagnose(text,{strict:false}).join()||"brak";
const ok={id:1,title:"A",status:"OPEN",assignee:null};`;
const TS=['TS2322','TS7006','TS2578','brak'];
const probeRows=[
['Czy a.ts jest skryptem?','isScript("a.ts")',true,'Liczony w raporcie.'],
['Czy a.d.ts jest skryptem?','isScript("a.d.ts")',false,'Deklaracje nie liczą się do migracji.'],
['Czy a.css jest skryptem?','isScript("a.css")',false,'To nie skrypt.'],
['Procent TS przy 1 JS i 3 TS','migrationStats([{path:"a.js",text:""},{path:"b.ts",text:""},{path:"c.ts",text:""},{path:"d.tsx",text:""}]).percent',75,'Trzy z czterech.'],
['Procent pustego projektu','migrationStats([]).percent',100,'Brak plików do migracji.'],
['Liczba any po dwukropku','migrationStats([{path:"a.ts",text:"const x: any=1"}]).any',1,'Adnotacja.'],
['Liczba any po as','migrationStats([{path:"a.ts",text:"y as any"}]).any',1,'Rzutowanie.'],
['Liczba any w słowie „many”','migrationStats([{path:"a.ts",text:"const many=1;"}]).any',0,'Wzorzec wymaga kontekstu typu.'],
['Liczba any w pliku .js','migrationStats([{path:"a.js",text:"const x: any"}]).any',1,'Plik też jest skryptem. Prosty wzorzec tekstowy nie rozumie składni.'],
['Liczba @ts-ignore','migrationStats([{path:"a.ts",text:"// @ts-ignore\\nf();"}]).tsIgnore',1,'Do usunięcia.'],
['Expect-error bez opisu','migrationStats([{path:"a.ts",text:"// @ts-expect-error\\nf();"}]).undescribedExpectError',1,'Brak powodu.'],
['Expect-error z opisem','migrationStats([{path:"a.ts",text:"// @ts-expect-error powód\\nf();"}]).undescribedExpectError',0,'Opis wyjaśnia przyczynę.'],
['Regresje przy spadku','regressions(base,{...base,js:4,any:2}).length',0,'Dobry kierunek.'],
['Regresja plików JS','regressions(base,{...base,js:6})[0]','przybyło plików JS','Nowe pliki tylko w TS.',['przybyło any','@ts-ignore w kodzie','brak']],
['Regresja any','regressions(base,{...base,any:9})[0]','przybyło any','Wskaźnik ma maleć.',['przybyło plików JS','@ts-ignore w kodzie','brak']],
['allowJs przy 0 plikach JS','canDisableAllowJs({...base,js:0})',true,'Można domknąć migrację.'],
['allowJs przy 1 pliku JS','canDisableAllowJs({...base,js:1})',false,'Jeszcze nie.'],
['Pierwsza warstwa','nextLayer([])','model','Od danych.',['api','feature','shared']],
['Po modelach','nextLayer(["model"])','api','Dane wchodzą przez API.',['logic','feature','shared']],
['Po wszystkich','String(nextLayer(["model","api","logic","shared","feature"]))','null','Koniec.',['feature','model','undefined']],
['Pozycja funkcji','migrationOrder("feature")',4,'Na końcu.'],
['Strażnik: dobry','isTicket({id:1,title:"A"})',true,'Oba pola.'],
['Strażnik: bez tytułu','isTicket({id:1})',false,'Brak pola.'],
['Strażnik: tablica','isTicket([])',false,'Brak pól.'],
['Niepewny JSON: odfiltrowanie','parseStored(\'[{"id":1,"title":"A"},{"id":2}]\').length',1,'Zły element wypada.'],
['Niepewny JSON: błąd składni','parseStored("{").length',0,'Bez wyjątku.'],
['Schemat: poprawny','parseTickets([ok]).length',1,'Przechodzi.'],
['Schemat: zły status','(()=>{try{parseTickets([{...ok,status:"X"}]);return "przeszło";}catch{return "odrzucone";}})()','odrzucone','Unia działa w czasie działania.',['przeszło','X','OPEN']],
['Schemat: pusty tytuł','(()=>{try{parseTickets([{...ok,title:""}]);return "przeszło";}catch{return "odrzucone";}})()','odrzucone','min(1).',['przeszło','pusty tekst','A']],
['Schemat: null assignee','parseTickets([ok])[0].assignee','null','Dozwolony przez nullable.',['undefined','pusty tekst','A']],
['tsc strict: parametr bez typu','strict("function f(x){return x;}\\nexport {f};")','TS7006','Niejawne any.',TS],
['tsc bez strict: parametr bez typu','loose("function f(x){return x;}\\nexport {f};")','brak','Stary kod przechodzi.',TS],
['tsc strict: string=null','strict("const a:string=null;\\nexport {a};")','TS2322','strictNullChecks.',TS],
['tsc bez strict: string=null','loose("const a:string=null;\\nexport {a};")','brak','null pasuje do wszystkiego.',TS],
['tsc: dostęp do unknown','strict("const a:unknown=1;\\nexport const b=(a as {x:number}).x;")','brak','Rzutowanie przekonuje kompilator, ale niczego nie sprawdza.',TS],
['tsc: any przechodzi','strict("const a:any=1;\\nexport const b:string=a;")','brak','any wyłącza sprawdzanie.',TS],
['tsc: unknown nie przechodzi','strict("const a:unknown=1;\\nexport const b:string=a;")','TS2322','unknown wymaga sprawdzenia.',TS],
['tsc: zbędny expect-error','code("// @ts-expect-error powód\\nexport const a:string=\'x\';")','TS2578','Przypomina o usunięciu.',TS],
['tsc: potrzebny expect-error','code("// @ts-expect-error powód\\nexport const a:string=1;")','brak','Komentarz spełnił zadanie.',TS],
['tsc: zbędny ts-ignore','code("// @ts-ignore\\nexport const a:string=\'x\';")','brak','Milczy i zostaje na zawsze.',TS],
];
const project={
'vitest.config.ts':config,'src/setup.ts':nodeSafeSetup,'src/types.ts':types,'src/ticketSchema.ts':schema,'src/TicketRow.tsx':row,'src/MigrationFixes.tsx':fixes,'src/migrationStats.ts':stats,'src/tscLab.ts':tsc,'src/migration.test.tsx':tests,
'tsconfig.migration.jsonc':tsconfig,'types/vendor.d.ts.txt':vendor,
'README.md':'DevDesk: migracja na TypeScript. npm install, npm test. tscLab uruchamia prawdziwy kompilator w pamięci, więc odpowiedzi o strict, checkJs i komentarzach pochodzą z tsc. Raport postępu jest prostym filtrem tekstowym: nie rozumie składni, dlatego w projekcie uzupełnij go regułami ESLint.\n',
};
const mutants=[
{file:'src/migrationStats.ts',before:'&&!/\\.d\\.ts$/.test(path)',after:''},
{file:'src/migrationStats.ts',before:'if(after.any>before.any)problems.push("przybyło any");',after:''},
{file:'src/migrationStats.ts',before:'return stats.js===0;',after:'return stats.percent>0;'},
{file:'src/MigrationFixes.tsx',before:'typeof candidate.id==="number"&&',after:''},
{file:'src/MigrationFixes.tsx',before:'try{parsed=JSON.parse(raw);}catch{return [];}',after:'parsed=JSON.parse(raw);'},
{file:'src/MigrationFixes.tsx',before:'parsed.filter(isTicket)',after:'parsed'},
{file:'src/ticketSchema.ts',before:'.nullable()',after:'.nullish()'},
{file:'src/TicketRow.tsx',before:'{ticket.assignee??"—"}',after:'{ticket.assignee}'},
{file:'src/tscLab.ts',before:'...options}',after:'strict:false}'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
