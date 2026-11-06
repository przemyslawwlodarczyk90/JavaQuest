// Lekcja 16/06: useTransition i useDeferredValue. Kolejność commitów (pilny → przejście) sprawdza React 19 w Vitest (jsdom).
const types=String.raw`export type Ticket={id:number;title:string};
export function makeTickets(count:number):Ticket[]{
  const words=["API timeout","Logowanie SSO","Eksport CSV","Błąd płatności","Wolny raport"];
  return Array.from({length:count},(_,i)=>({id:i+1,title:words[i%words.length]+" #"+(i+1)}));
}`;
const table=String.raw`import {memo} from "react";
import type {Ticket} from "./types";
// memo jest warunkiem: pilny render (pole tekstowe) nie może renderować ciężkiej tabeli.
export const FilteredTable=memo(function FilteredTable({tickets,query,onRender}:{tickets:readonly Ticket[];query:string;onRender?:(query:string)=>void}){
  onRender?.(query);
  const q=query.trim().toLowerCase();
  const rows=q?tickets.filter(t=>t.title.toLowerCase().includes(q)):tickets;
  return <table><tbody>{rows.map(t=><tr key={t.id}><td>{t.id}</td><td>{t.title}</td></tr>)}</tbody></table>;
});`;
const search=String.raw`import {useLayoutEffect,useState,useTransition} from "react";
import {FilteredTable} from "./FilteredTable";
import type {Ticket} from "./types";
export type Commit={input:string;filter:string;pending:boolean};
// Dwa stany: input (pilny) i filter (niepilny, w startTransition).
export function TicketSearch({tickets,onCommit,onTableRender}:{tickets:readonly Ticket[];onCommit?:(c:Commit)=>void;onTableRender?:(q:string)=>void}){
  const [input,setInput]=useState("");
  const [filter,setFilter]=useState("");
  const [isPending,startTransition]=useTransition();
  useLayoutEffect(()=>{onCommit?.({input,filter,pending:isPending});});
  return <section>
    <input aria-label="Szukaj ticketów" value={input} onChange={e=>{
      setInput(e.target.value);
      startTransition(()=>setFilter(e.target.value));
    }}/>
    <div style={{opacity:isPending?0.6:1}} aria-busy={isPending} data-testid="tabela">
      <FilteredTable tickets={tickets} query={filter} onRender={onTableRender}/>
    </div>
  </section>;
}`;
const deferred=String.raw`import {useDeferredValue,useLayoutEffect} from "react";
import {FilteredTable} from "./FilteredTable";
import type {Ticket} from "./types";
// Wartość przychodzi z zewnątrz (props) — nie mamy settera, więc odraczamy samą wartość.
export function DeferredResults({tickets,query,onCommit,onTableRender}:{tickets:readonly Ticket[];query:string;onCommit?:(c:{query:string;deferred:string})=>void;onTableRender?:(q:string)=>void}){
  const deferredQuery=useDeferredValue(query);
  const stale=query!==deferredQuery;
  useLayoutEffect(()=>{onCommit?.({query,deferred:deferredQuery});});
  return <div aria-busy={stale} className={stale?"is-refreshing":undefined} data-testid="wyniki">
    <FilteredTable tickets={tickets} query={deferredQuery} onRender={onTableRender}/>
  </div>;
}`;
const tabs=String.raw`import {lazy,Suspense,useState,useTransition,type ComponentType} from "react";
// Zakładki z leniwą treścią: przełączenie w przejściu zostawia starą zakładkę zamiast fallbacku.
export function createTabs(loaders:Record<string,()=>Promise<{default:ComponentType}>>){
  const panels=Object.fromEntries(Object.entries(loaders).map(([name,load])=>[name,lazy(load)]));
  const names=Object.keys(loaders);
  return function Tabs(){
    const [active,setActive]=useState(names[0]);
    const [isPending,startTransition]=useTransition();
    const Panel=panels[active];
    return <div>
      <div role="tablist">{names.map(name=><button key={name} role="tab" type="button" aria-selected={name===active} onClick={()=>startTransition(()=>setActive(name))}>{name}</button>)}</div>
      {isPending&&<small>Przełączanie…</small>}
      <Suspense fallback={<p>Ładowanie zakładki…</p>}><Panel/></Suspense>
    </div>;
  };
}`;
const debounce=String.raw`import {useDeferredValue,useEffect,useState} from "react";
// Przejście nie zmniejsza liczby żądań — do zapytań API nadal potrzebny jest debounce.
export function useDebouncedValue<T>(value:T,delay:number):T{
  const [debounced,setDebounced]=useState(value);
  useEffect(()=>{const id=setTimeout(()=>setDebounced(value),delay);return ()=>clearTimeout(id);},[value,delay]);
  return debounced;
}
export function ApiSearch({search}:{search:(q:string)=>void}){
  const [input,setInput]=useState("");
  const query=useDebouncedValue(input,300); // mniej żądań
  const shown=useDeferredValue(input);       // płynny render podpowiedzi
  useEffect(()=>{if(query)search(query);},[query,search]);
  return <div>
    <input aria-label="Szukaj w API" value={input} onChange={e=>setInput(e.target.value)}/>
    <p>Podpowiedź dla: {shown}</p>
  </div>;
}`;
const chart=String.raw`import {memo,useDeferredValue} from "react";
const HeavyChart=memo(function HeavyChart({range,onRender}:{range:string;onRender?:(range:string)=>void}){
  onRender?.(range);
  return <figure aria-label={"Wykres: "+range}>Wykres dla zakresu {range}</figure>;
});
// Zakres zmienia się szybko przy klikaniu; wykres dostaje wartość odroczoną.
export function ReportsChart({range,onChartRender}:{range:string;onChartRender?:(range:string)=>void}){
  const deferredRange=useDeferredValue(range);
  return <div aria-busy={range!==deferredRange}><HeavyChart range={deferredRange} onRender={onChartRender}/></div>;
}`;
const tests=String.raw`import {act,fireEvent,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {startTransition,useState,type ComponentType} from "react";
import {afterEach,expect,test,vi} from "vitest";
import {ApiSearch,useDebouncedValue} from "./ApiSearch";
import {DeferredResults} from "./DeferredResults";
import {ReportsChart} from "./ReportsChart";
import {createTabs} from "./Tabs";
import {TicketSearch,type Commit} from "./TicketSearch";
import {makeTickets} from "./types";
afterEach(()=>vi.useRealTimers());
const tickets=makeTickets(50);
const fmt=(c:Commit)=>c.input+"|"+c.filter+"|"+c.pending;
test("przejście: najpierw pilny commit pola z isPending, potem commit tabeli",()=>{
  const commits:string[]=[];
  render(<TicketSearch tickets={tickets} onCommit={c=>commits.push(fmt(c))}/>);
  commits.length=0;
  fireEvent.change(screen.getByLabelText("Szukaj ticketów"),{target:{value:"api"}});
  expect(commits).toEqual(["api||true","api|api|false"]);
  expect(screen.getAllByRole("row")).toHaveLength(10);
  expect(screen.getByTestId("tabela")).toHaveAttribute("aria-busy","false");
});
test("memo: tabela renderuje się tylko w commicie przejścia",()=>{
  const tableRenders:string[]=[];
  render(<TicketSearch tickets={tickets} onTableRender={q=>tableRenders.push(q)}/>);
  tableRenders.length=0;
  fireEvent.change(screen.getByLabelText("Szukaj ticketów"),{target:{value:"sso"}});
  expect(tableRenders).toEqual(["sso"]);
});
test("useDeferredValue: najpierw render ze starą wartością, potem z nową",()=>{
  const commits:string[]=[],tableRenders:string[]=[];
  // stabilne funkcje: nowe strzałki przy rerender zmieniłyby props i memo nie pominęłoby renderu
  const onCommit=(c:{query:string;deferred:string})=>{commits.push(c.query+"|"+c.deferred);};
  const onTableRender=(q:string)=>{tableRenders.push(q);};
  const view=render(<DeferredResults tickets={tickets} query="" onCommit={onCommit} onTableRender={onTableRender}/>);
  commits.length=0;tableRenders.length=0;
  view.rerender(<DeferredResults tickets={tickets} query="csv" onCommit={onCommit} onTableRender={onTableRender}/>);
  expect(commits).toEqual(["csv|","csv|csv"]);
  expect(tableRenders).toEqual(["csv"]);
  expect(screen.getByTestId("wyniki")).toHaveAttribute("aria-busy","false");
});
test("useDeferredValue w aktualizacji będącej przejściem nie opóźnia wartości",()=>{
  const commits:string[]=[];
  let setQuery!:(q:string)=>void;
  function Host(){const [q,setQ]=useState("");setQuery=setQ;return <DeferredResults tickets={tickets} query={q} onCommit={c=>commits.push(c.query+"|"+c.deferred)}/>;}
  render(<Host/>);
  commits.length=0;
  act(()=>{startTransition(()=>setQuery("csv"));});
  expect(commits).toEqual(["csv|csv"]);
});
test("ReportsChart: wykres renderuje się z odroczonym zakresem",()=>{
  const renders:string[]=[];
  const onChartRender=(r:string)=>{renders.push(r);};
  const view=render(<ReportsChart range="30d" onChartRender={onChartRender}/>);
  view.rerender(<ReportsChart range="7d" onChartRender={onChartRender}/>);
  expect(renders).toEqual(["30d","7d"]);
  expect(screen.getByRole("figure",{name:"Wykres: 7d"})).toBeInTheDocument();
});
function deferredModule(){let resolve!:(m:{default:ComponentType})=>void;const promise=new Promise<{default:ComponentType}>(r=>{resolve=r;});return {promise,resolve};}
test("zakładki: przełączenie w przejściu zostawia starą treść i pokazuje isPending",async()=>{
  const history=deferredModule();
  const Tabs=createTabs({szczegóły:async()=>({default:()=><p>Treść szczegółów</p>}),historia:()=>history.promise});
  render(<Tabs/>);
  expect(await screen.findByText("Treść szczegółów")).toBeInTheDocument();
  await act(async()=>{screen.getByRole("tab",{name:"historia"}).click();});
  expect(screen.getByText("Treść szczegółów")).toBeVisible();
  expect(screen.getByText("Przełączanie…")).toBeInTheDocument();
  expect(screen.queryByText("Ładowanie zakładki…")).not.toBeInTheDocument();
  await act(async()=>{history.resolve({default:()=><p>Treść historii</p>});await history.promise;});
  expect(screen.getByText("Treść historii")).toBeInTheDocument();
  expect(screen.getByRole("tab",{name:"historia"})).toHaveAttribute("aria-selected","true");
  expect(screen.queryByText("Przełączanie…")).not.toBeInTheDocument();
});
test("useDebouncedValue oddaje wartość dopiero po ciszy",()=>{
  vi.useFakeTimers();
  const seen:string[]=[];
  function Probe({value}:{value:string}){const d=useDebouncedValue(value,300);seen.push(d);return null;}
  const view=render(<Probe value="a"/>);
  view.rerender(<Probe value="ab"/>);
  act(()=>{vi.advanceTimersByTime(200);});
  view.rerender(<Probe value="abc"/>);
  act(()=>{vi.advanceTimersByTime(299);});
  expect(seen.at(-1)).toBe("a");
  act(()=>{vi.advanceTimersByTime(1);});
  expect(seen.at(-1)).toBe("abc");
});
test("ApiSearch: trzy znaki to jedno zapytanie, a podpowiedź aktualizuje się od razu",()=>{
  vi.useFakeTimers();
  const search=vi.fn();
  render(<ApiSearch search={search}/>);
  for(const value of ["a","ap","api"])fireEvent.change(screen.getByLabelText("Szukaj w API"),{target:{value}});
  expect(screen.getByText("Podpowiedź dla: api")).toBeInTheDocument();
  expect(search).not.toHaveBeenCalled();
  act(()=>{vi.advanceTimersByTime(300);});
  expect(search).toHaveBeenCalledTimes(1);
  expect(search).toHaveBeenCalledWith("api");
});`;
const header=String.raw`import {act,lazy,memo,startTransition,Suspense,useDeferredValue,useLayoutEffect,useEffect,useState,useTransition,type ComponentType,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
const log:string[]=[];
async function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);await act(async()=>root.render(ui));return {host,root,update:async(next:ReactNode)=>{await act(async()=>root.render(next));}};}
void lazy;void memo;void startTransition;void Suspense;void useDeferredValue;void useLayoutEffect;void useEffect;void useState;void useTransition;void log;
`;
const exercises=[
  ['Rozdziel stan wyszukiwarki na pilny input i niepilny filter ustawiany w startTransition.','Dwa setery w onChange.',search],
  ['Owiń ciężką tabelę w memo, żeby pilny render jej nie dotykał.','Bez memo przejście nic nie da.',table],
  ['Przyciemnij tabelę, gdy isPending jest true, i ustaw aria-busy.','opacity i aria-busy.',search],
  ['Sprawdź testem kolejność commitów: pilny z isPending, potem przejście.','useLayoutEffect bez zależności zapisuje każdy commit.',tests],
  ['Sprawdź testem, że tabela renderuje się tylko w commicie przejścia.','Licznik renderów przez props onRender.',tests],
  ['Użyj useDeferredValue dla wartości przychodzącej z props.','Nie masz settera — odraczasz wartość.',deferred],
  ['Oznacz nieaktualne wyniki, gdy query !== deferredQuery.','aria-busy i klasa is-refreshing.',deferred],
  ['Sprawdź testem, że wartość odroczona najpierw jest stara, potem nowa.','Dwa commity po rerender.',tests],
  ['Pokaż, że w aktualizacji będącej przejściem wartość odroczona nie opóźnia się.','startTransition wokół settera rodzica.',tests],
  ['Odrocz zakres wykresu z teorii.','HeavyChart w memo dostaje deferredRange.',chart],
  ['Sprawdź testem, że wykres dostaje odroczony zakres.','Rendery wykresu: stary, potem nowy zakres.',tests],
  ['Zbuduj zakładki z leniwą treścią przełączane w przejściu.','Fabryka tworzy lazy raz na zakładkę.',tabs],
  ['Sprawdź testem, że przełączenie zostawia starą treść i pokazuje „Przełączanie…”.','Kontrolowana obietnica modułu.',tests],
  ['Napisz useDebouncedValue z setTimeout i sprzątaniem.','Nowa wartość anuluje poprzedni licznik.',debounce],
  ['Połącz debounce dla zapytań API z useDeferredValue dla renderu.','Dwa różne problemy, dwa narzędzia.',debounce],
  ['Sprawdź na sztucznych zegarach, że trzy znaki dają jedno zapytanie.','fireEvent.change i vi.advanceTimersByTime w act.',tests],
  ['Sprawdź testem, kiedy useDebouncedValue oddaje wartość.','Przesuń czas o 299 i 1 ms.',tests],
  ['Użyj samodzielnego startTransition bez hooka, gdy nie potrzebujesz isPending.','Import z react.',String.raw`import {startTransition,useState} from "react";
export function SortToggle({onSort}:{onSort:(key:"id"|"title")=>void}){
  const [key,setKey]=useState<"id"|"title">("id");
  return <button type="button" onClick={()=>{const next=key==="id"?"title":"id";setKey(next);startTransition(()=>onSort(next));}}>Sortuj: {key}</button>;
}`],
  ['Pokaż useDeferredValue z wartością początkową (React 19).','Pierwszy render z initialValue, potem z właściwą.',String.raw`import {useDeferredValue} from "react";
export function Suggestions({query}:{query:string}){
  // przy montowaniu najpierw szybki render z "", potem w tle z query
  const deferred=useDeferredValue(query,"");
  return <p>Podpowiedzi dla: {deferred||"…"}</p>;
}`],
  ['Wyjaśnij, kiedy wybrać useTransition, a kiedy useDeferredValue.','Masz setter czy dostajesz wartość?',String.raw`// useTransition: kontrolujesz setter kosztownej zmiany (startTransition(() => setFilter(v))) i chcesz isPending.
// useDeferredValue: wartość przychodzi z zewnątrz (props, URL, stan rodzica) — odraczasz samą wartość.
// Oba nie przyspieszają renderu — sprawiają, że ciężki render nie blokuje interakcji.
export {};`],
  ['Wyjaśnij, dlaczego przejście nie zastępuje debounce.','Liczba renderów i żądań się nie zmienia.',String.raw`// startTransition oznacza render jako przerywalny, ale każdy znak nadal uruchamia render,
// a efekt z zapytaniem — żądanie. Debounce zmniejsza liczbę operacji, przejście — blokowanie interakcji.
export {};`],
  ['Napisz wyszukiwarkę, w której zapytanie wysyła się z wartością odroczoną w kluczu zapytania.','Klucz zapytania z deferred.',String.raw`import {useDeferredValue,useEffect,useState} from "react";
export function KeyedSearch({fetchTickets}:{fetchTickets:(q:string,signal:AbortSignal)=>Promise<string[]>}){
  const [input,setInput]=useState("");
  const query=useDeferredValue(input);
  const [results,setResults]=useState<string[]>([]);
  useEffect(()=>{
    const controller=new AbortController();
    fetchTickets(query,controller.signal).then(setResults).catch(()=>{});
    return ()=>controller.abort(); // poprzednie żądanie anulowane przy nowym query
  },[query,fetchTickets]);
  return <div><input aria-label="Szukaj" value={input} onChange={e=>setInput(e.target.value)}/><ul>{results.map(r=><li key={r}>{r}</li>)}</ul></div>;
}`],
  ['Pokaż, że bez memo tabela renderuje się dwa razy na znak.','Pilny render rodzica renderuje dzieci.',String.raw`import {useState,useTransition} from "react";
function Table({query}:{query:string}){return <p>Wyniki dla: {query}</p>;} // bez memo
export function NoMemoSearch(){
  const [input,setInput]=useState("");
  const [filter,setFilter]=useState("");
  const [,startTransition]=useTransition();
  // pilny render (input) też renderuje Table ze starym filter — przejście traci sens przy ciężkiej tabeli
  return <div><input aria-label="Szukaj" value={input} onChange={e=>{setInput(e.target.value);startTransition(()=>setFilter(e.target.value));}}/><Table query={filter}/></div>;
}`],
  ['Zmierz płynność pisania przy spowolnionym CPU.','DevTools → Performance → CPU 4× slowdown.',String.raw`// 1. Performance → CPU: 4× slowdown, 5000 ticketów.
// 2. Bez przejścia: każdy znak to długie zadanie > 50 ms, pole „zacina się”.
// 3. Z przejściem i memo: pilne commity dotyczą tylko pola; tabela odświeża się w tle (przyciemniona).
// 4. Profiler: osobne commity pilne i przejścia — zapisz ich czasy.
export {};`],
  ['Wyjaśnij, co oznacza przerwanie przejścia przy kolejnym znaku.','Render w tle jest porzucany.',String.raw`// Gdy w trakcie renderu przejścia (filtr "ap") przychodzi pilna zmiana (znak "i"),
// React przerywa render "ap", obsługuje pole i zaczyna nowe przejście dla "api".
// Pośredni wynik "ap" może nigdy nie trafić na ekran — i to jest zamierzone.
export {};`],
  ['Dodaj komunikat dla czytników ekranu o odświeżaniu wyników.','role="status" z tekstem przy isPending.',String.raw`export function RefreshStatus({pending,count}:{pending:boolean;count:number}){
  return <p role="status">{pending?"Odświeżanie wyników…":"Znaleziono: "+count}</p>;
}`],
  ['Pokaż, że kilka setterów w jednym startTransition daje jeden commit.','Grupowanie w przejściu.',String.raw`import {useState,useTransition} from "react";
export function FilterPanel(){
  const [status,setStatus]=useState("OPEN");
  const [assignee,setAssignee]=useState("");
  const [,startTransition]=useTransition();
  return <button type="button" onClick={()=>startTransition(()=>{setStatus("CLOSED");setAssignee("Ala");})}>Pokaż zamknięte Ali ({status}, {assignee||"wszyscy"})</button>;
}`],
  ['Zastąp kontrolowane pole w startTransition prawidłowym podziałem.','Wartość pola nie może być w przejściu.',String.raw`import {useState,useTransition} from "react";
// ŹLE: startTransition(() => setInput(e.target.value)) — pole reagowałoby z opóźnieniem lub gubiło znaki.
export function Search({onFilter}:{onFilter:(q:string)=>void}){
  const [input,setInput]=useState("");
  const [,startTransition]=useTransition();
  return <input aria-label="Szukaj" value={input} onChange={e=>{setInput(e.target.value);startTransition(()=>onFilter(e.target.value));}}/>;
}`],
  ['Wyjaśnij, dlaczego nawigacja routera nie miga fallbackiem.','Router realizuje nawigację jako przejście.',String.raw`// React Router aktualizuje swój stan w startTransition, więc zawieszony nowy ekran
// (lazy, dane) nie zastępuje starego fallbackiem — stary widok zostaje do czasu gotowości nowego.
// Skutek uboczny: useDeferredValue wartości z URL nie opóźnia się w renderze przejścia.
export {};`],
  ['Zapisz kryterium akceptacji płynnego wyszukiwania.','Pilne commity tylko dla pola, przyciemnienie, zakładki bez fallbacku.',String.raw`// 1. 5000 ticketów, CPU 4×: pisanie płynne, pilne commity dotyczą tylko pola.
// 2. Tabela przyciemniona (isPending / stale) podczas odświeżania.
// 3. Zakładki z leniwą treścią przełączane bez fallbacku.
// 4. Zapytania API z debounce, nie z samym przejściem.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Co oznacza startTransition(() => setFilter(v))?','Aktualizacja jest niepilna — pilne zmiany renderują się najpierw','Aktualizacja jest pomijana','Render jest szybszy','Aktualizacja jest synchroniczna','Kosztowny render dzieje się w tle.');
q('Co zwraca useTransition()?','[isPending, startTransition]','[value, setValue]','[startTransition]','Obietnicę','isPending to boolean.');
q('Kiedy isPending jest true?','Gdy przejście czeka na zakończenie','Zawsze po kliknięciu','Gdy trwa pobieranie sieci zawsze','Nigdy w React 19','Można przyciemnić tabelę.');
q('Kiedy używać useTransition?','Gdy masz dostęp do settera kosztownej zmiany','Gdy wartość przychodzi z props','Do debounce','Do pobierania danych','Dla wartości z zewnątrz — useDeferredValue.');
q('Co zwraca useDeferredValue(value)?','Opóźnioną kopię wartości','Obietnicę','Funkcję','isPending','Najpierw stara, potem nowa wartość.');
q('Kiedy używać useDeferredValue?','Gdy wartość przychodzi z zewnątrz i nie kontrolujesz settera','Gdy masz setter','Do anulowania żądań','Do animacji','Np. props, URL, stan rodzica.');
q('Co oznacza value !== deferredValue?','Trwa odświeżanie w tle','Błąd','Brak danych','Koniec renderu','Można pokazać aria-busy.');
q('Czy przejścia przyspieszają render?','Nie — sprawiają, że ciężki render nie blokuje interakcji','Tak, dwukrotnie','Tylko w produkcji','Tylko z memo','Render trwa tyle samo.');
q('Dlaczego ciężka tabela musi być w memo przy przejściu?','Pilny render (pole) nie może jej renderować ze starym filtrem','Bo przejścia wymagają memo technicznie','Bo memo przyspiesza przejście','Nie musi','Bez memo każdy znak i tak renderuje tabelę.');
q('Ile commitów daje jeden znak w TicketSearch z przejściem?','Dwa: pilny (pole, isPending) i przejście (tabela)','Jeden','Trzy','Zero','Test lekcji zapisuje oba.');
q('Jaki stan ma isPending w pilnym commicie po wpisaniu znaku?','true','false','undefined','null','W commicie przejścia wraca do false.');
q('Czy wartość pola tekstowego powinna być ustawiana w startTransition?','Nie — pole musi reagować natychmiast','Tak','Tylko dla długich tekstów','Tylko w TSX','Rozdziel input i filter.');
q('Co React robi, gdy w trakcie renderu przejścia przychodzi kolejny znak?','Przerywa render przejścia i zaczyna od nowa z nową wartością','Kończy stary render','Ignoruje znak','Rzuca błąd','Pośredni wynik może nie trafić na ekran.');
q('Co pokazuje React przy przejściu, które powoduje zawieszenie (Suspense)?','Poprzedni widok zamiast fallbacku','Zawsze fallback','Pusty ekran','Błąd','Dlatego zakładki nie migają.');
q('Jak React Router realizuje nawigację?','Jako przejście','Synchronicznie z flushSync','W workerze','Przez Suspense bez przejścia','Stary ekran zostaje podczas ładowania.');
q('Czy useDeferredValue opóźnia wartość w renderze będącym przejściem?','Nie — w przejściu zwraca od razu nową wartość','Tak, zawsze','Tylko w dev','Tylko dla obiektów','Opóźnienie dotyczy pilnych aktualizacji.');
q('Co daje drugi argument useDeferredValue(value, initialValue) w React 19?','Przy montowaniu najpierw render z initialValue, potem z value','Domyślne opóźnienie w ms','Wartość przy błędzie','Nic','Szybki pierwszy render.');
q('Czy przejście zmniejsza liczbę żądań sieciowych?','Nie — potrzebny jest debounce','Tak','Tylko z memo','Tylko w produkcji','Przejście dotyczy renderu.');
q('Jaki problem rozwiązuje debounce, a jaki przejście?','Debounce: za dużo operacji; przejście: blokowanie interakcji przez render','Oba to samo','Debounce: render; przejście: sieć','Żaden','Dwa narzędzia, dwa problemy.');
q('Gdzie umieścić debounce dla zapytania API?','W wartości używanej w kluczu/efekcie zapytania','W polu tekstowym','W memo tabeli','W startTransition','Pole aktualizuje się natychmiast.');
q('Ile zapytań wyśle ApiSearch po szybkim wpisaniu „api” i 300 ms ciszy?','Jedno','Trzy','Zero','Dwa','Debounce 300 ms.');
q('Czy podpowiedź w ApiSearch czeka na debounce?','Nie — używa wartości odroczonej, aktualizuje się od razu','Tak','Tylko przy 3 znakach','Tylko w produkcji','Render podpowiedzi jest płynny.');
q('Czy można wywołać startTransition bez hooka?','Tak — import { startTransition } from "react", ale bez isPending','Nie','Tylko w klasach','Tylko w React 17','Przydatne poza komponentem.');
q('Czy kilka setterów w jednym startTransition daje jeden commit przejścia?','Tak','Nie, każdy osobno','Tylko z flushSync','Tylko w produkcji','Grupowanie działa także w przejściach.');
q('Czy startTransition z funkcją ma stałą tożsamość z useTransition?','Tak, jest stabilny','Nie','Tylko w TSX','Tylko z memo','Można go pomijać w zależnościach.');
q('Jak oznaczyć odświeżanie dla czytników ekranu?','aria-busy na kontenerze i komunikat w role="status"','Tylko opacity','alert()','Nie trzeba','Przyciemnienie jest tylko wizualne.');
q('Co mierzysz w Profilerze przy płynnym wyszukiwaniu?','Osobne commity pilne (pole) i przejścia (tabela)','Rozmiar bundla','Sieć','Liczbę hooków','Pilne commity powinny być krótkie.');
q('Jak zasymulować wolne urządzenie w przeglądarce?','Performance → CPU throttling (np. 4×)','Network → Offline','Lighthouse → Desktop','Nie da się','Ujawnia zacinanie przy pisaniu.');
q('Co wydarzy się bez przejścia przy 5000 wierszach i wolnym CPU?','Każdy znak blokuje wątek, pole „zacina się”','Nic','Tabela się nie odświeży','Przeglądarka się wyłączy','Długie zadania > 50 ms.');
q('Co przekazuje się do startTransition?','Funkcję z aktualizacjami stanu','Wartość','Obietnicę zawsze','Komponent','W React 19 może być async (akcja).');
q('Co dzieje się z isPending podczas ładowania leniwej zakładki w przejściu?','Jest true do czasu załadowania','Od razu false','Undefined','Zawsze true','Można pokazać „Przełączanie…”.');
q('Czy zakładki z przejściem pokazują fallback Suspense przy pierwszym wyświetleniu ekranu?','Tak — pierwsze montowanie nie ma starego widoku','Nie, nigdy','Tylko w dev','Tylko w TSX','Przejście pomaga przy przełączaniu.');
q('Dlaczego przy zwykłej aktualizacji React pokazuje fallback zawieszonej treści?','Pilna aktualizacja musi coś pokazać od razu — ukrywa starą treść i pokazuje fallback','Bo nie ma Suspense','Bo lazy tego wymaga','Nie pokazuje','Przejście pozwala czekać.');
q('Gdzie trzymać wartość filtra z URL przy ciężkim wykresie?','W URL, a wykres renderować z wartością odroczoną','W localStorage','W refie','W kontekście globalnym','Klikanie filtrów zostaje płynne.');
q('Czy useDeferredValue wymaga memo na ciężkim dziecku?','Tak — inaczej pilny render z nową wartością rodzica i tak wyrenderuje dziecko','Nie','Tylko w produkcji','Tylko dla tablic','Dziecko dostaje deferred, ale musi umieć pominąć render.');
q('Co renderuje się w pilnym commicie po zmianie query w DeferredResults?','Kontener z aria-busy, tabela pomija render (te same props)','Tabela z nowym query','Nic','Tylko tabela','Tabela renderuje się w drugim commicie.');
q('Ile razy renderuje się memo-tabela po zmianie query w DeferredResults?','Raz — z nową wartością odroczoną','Dwa razy','Zero','Trzy','Pierwszy commit ją pomija.');
q('Co jest typowym błędem przy useTransition?','Ustawianie wartości kontrolowanego pola w startTransition','Używanie isPending','Memo na tabeli','Dwa stany','Pole gubi płynność.');
q('Czy startTransition może zawierać setTimeout z setState w środku?','Aktualizacja w setTimeout nie będzie przejściem — trzeba ją ponownie owinąć','Tak, wszystko w środku jest przejściem','Rzuci błąd','Zablokuje render','Przejście obejmuje synchroniczne wywołania (oraz akcje async w React 19 do pierwszego await).');
q('Co to jest „akcja” w React 19?','Funkcja async przekazana do startTransition','Handler onClick','Reducer','Effect','isPending trwa do zakończenia akcji.');
q('Czy przejście może zostać porzucone?','Tak — gdy nadejdzie nowsza aktualizacja','Nie','Tylko z Suspense','Tylko w dev','Wynik pośredni nie zostanie pokazany.');
q('Co jest pilną aktualizacją?','Odpowiedź na bezpośrednią interakcję: pisanie, klik, nacisk klawisza','Każda zmiana stanu','Pobranie danych','Render listy','Musi być widoczna natychmiast.');
q('Jakie lanes odpowiadają przejściom?','Pasy przejść (Transition lanes) o niższym priorytecie','SyncLane','InputContinuousLane','IdleLane zawsze','Lekcja 15/05.');
q('Czy można mieć kilka niezależnych useTransition w komponencie?','Tak, każdy z własnym isPending','Nie','Tylko jeden na aplikację','Tylko w klasach','Np. osobno dla sortowania i filtra.');
q('Co pokaże test „przejście: najpierw pilny commit”?','["api||true", "api|api|false"]','["api|api|false"]','["|api|true"]','[]','Pole przed tabelą.');
q('Jak testować przejścia w Vitest, skoro act kończy je od razu?','Zapisywać każdy commit (useLayoutEffect) i sprawdzić ich kolejność','Nie da się','Mierzyć czas','Używać setTimeout','Przerwania nie widać, kolejność — tak.');
q('Dlaczego useLayoutEffect bez zależności nadaje się do zapisu commitów?','Uruchamia się po każdym commicie komponentu','Bo jest szybszy','Bo działa w renderze','Bo blokuje przejścia','Render bez commitu nie zostawia wpisu.');
q('Czy przejście wpływa na grupowanie (batching) pilnych aktualizacji?','Nie — pilne są grupowane osobno od przejść','Tak, wyłącza je','Łączy je w jeden commit','Tylko w dev','Stąd dwa commity.');
q('Co się stanie, gdy w przejściu zmienisz stan, który nie powoduje ciężkiego renderu?','Nic złego, ale przejście jest zbędne','Błąd','Fallback','Utrata stanu','Używaj tam, gdzie render jest kosztowny.');
q('Jak połączyć przejście z pobieraniem danych w React 19?','Akcja async w startTransition albo dane przez use z Suspense','setTimeout','useLayoutEffect','Nie da się','Rozdział 17.');
q('Czy isPending z useTransition obejmuje inne przejścia w aplikacji?','Nie — tylko przejścia uruchomione tym startTransition','Tak, wszystkie','Tylko routera','Tylko Suspense','Każdy hook ma własny stan.');
q('Co robi „is-refreshing” w przykładzie z wykresem?','Oznacza wizualnie, że wykres pokazuje jeszcze stary zakres','Wymusza render','Anuluje przejście','Nic','Klasa dla CSS.');
q('Dlaczego HeavyChart jest w memo?','Pilny render ze zmienionym range rodzica nie może renderować ciężkiego wykresu','Bo tak wymaga useDeferredValue technicznie','Bo przyspiesza memo','Nie jest potrzebne','Wykres dostaje deferredRange.');
q('Co oznacza „long task” w Performance?','Zadanie JavaScriptu trwające ponad 50 ms','Każde zadanie','Żądanie sieciowe','Render CSS','Blokuje interakcje.');
q('Jak przejścia wpływają na INP?','Krótsze pilne commity poprawiają czas do następnego malowania po interakcji','Pogarszają','Nie wpływają','Mierzą INP','Lekcja 9.');
q('Czy przejścia działają w React 17?','Nie — pojawiły się w React 18','Tak','Tylko z flagą','Tylko w Next.js','React 19 dodał akcje async.');
q('Co zwróci useDeferredValue przy pierwszym renderze bez initialValue?','Bieżącą wartość','undefined','null','Pusty tekst','Opóźnienie dotyczy kolejnych zmian.');
q('Czy kolejny znak anuluje zaplanowany debounce?','Tak — sprzątanie efektu czyści timeout','Nie','Tylko w TSX','Tylko z przejściem','clearTimeout w cleanup.');
q('Co robi AbortController w wyszukiwarce z kluczem zapytania?','Anuluje poprzednie żądanie przy nowej wartości','Przyspiesza żądanie','Blokuje render','Nic','Brak wyścigu odpowiedzi.');
q('Co zrobić, gdy przejście nie poprawiło płynności?','Sprawdzić, czy ciężki komponent jest w memo i czy pilny render go nie dotyka','Dodać więcej przejść','Usunąć memo','Wyłączyć StrictMode','Profiler pokaże pilny commit.');
q('Dlaczego test zakładek sprawdza toBeVisible starej treści?','React przy zwykłej aktualizacji ukrywa starą treść stylem display: none','Bo tekst znika z DOM','Bez powodu','Bo jsdom nie obsługuje CSS','Przejście jej nie ukrywa.');
q('Co jest kryterium akceptacji lekcji?','Płynne pisanie przy 5000 ticketach, przyciemnienie przy odświeżaniu, zakładki bez fallbacku','Brak renderów tabeli','Usunięcie memo','Kompilator w trybie all','Potwierdzone Profilerem.');
q('Czy można używać useTransition w hooku własnym?','Tak, jak każdego hooka','Nie','Tylko w komponencie klasowym','Tylko z memo','Np. useFilteredTickets zwracający isPending.');
q('Co robi kontrolowane pole, gdy setInput jest w przejściu, a render jest ciężki?','Może opóźniać lub gubić znaki','Działa płynniej','Nic się nie zmienia','Rzuca błąd','Pole zawsze pilne.');
q('Czy przejście ma sens dla małej listy 20 elementów?','Zwykle nie — render jest tani','Tak, zawsze','Tylko w produkcji','Tylko w TSX','Mierz przed zmianą.');
q('Która technika z poprzednich lekcji jest warunkiem skuteczności przejść?','Memoizacja ciężkiego komponentu (memo lub kompilator)','Lazy','Kontekst','Klucze','Pilny render musi być tani.');
q('Co robi React z przestarzałym renderem przejścia po porzuceniu?','Odrzuca go bez commitu','Commituje go','Zapisuje do cache','Pokazuje fallback','Render bez commitu.');
q('Gdzie w praktyce pojawia się useDeferredValue z URL?','Filtry raportów w searchParams i ciężki wykres','Formularz logowania','Pole hasła','Nawigacja','Teoria: ReportsChart.');
q('Dlaczego w teście ReportsChart wykres renderuje się dwa razy, a nie trzy?','Pilny render ze starym zakresem jest pomijany przez memo','Bo test jest błędny','Bo deferred nie działa','Bo React grupuje wszystko','Montowanie i render z nowym zakresem.');
q('Co jest wynikiem pilnego commitu przy useDeferredValue w teście?','"csv|" — query nowe, deferred stare','"csv|csv"','"|csv"','Brak commitu','Drugi commit wyrównuje wartości.');
q('Jak userEvent współpracuje ze sztucznymi zegarami?','userEvent.setup({ advanceTimers: vi.advanceTimersByTime })','Nie współpracuje','Wymaga realnych zegarów zawsze','Przez act','Inaczej opóźnienia userEvent zawiesiłyby test.');
q('Co sprawdza test useDebouncedValue po 299 ms?','Że wartość nadal jest stara','Że jest nowa','Że jest pusta','Że rzuca błąd','Po kolejnej 1 ms — nowa.');
q('Czy debounce opóźnia aktualizację pola tekstowego?','Nie — pole używa input, debounce dotyczy zapytania','Tak','Tylko na mobile','Tylko w TSX','Dwie różne wartości.');
q('Czy przejście może pokazać pośredni wynik tabeli dla każdego znaku?','Nie musi — przy szybkim pisaniu pośrednie rendery są porzucane','Zawsze pokazuje','Nigdy nie pokazuje','Tylko w produkcji','W jsdom z act zobaczysz każdy, bo act kończy pracę.');
q('Jak nazywa się hook React 19 do formularzy powiązany z akcjami i przejściami?','useActionState','useFormTransition','useSubmit','useAsync','Rozdział 17.');
q('Czy startTransition może opakować navigate() z React Router?','Tak, ale router i tak realizuje nawigację jako przejście','Nie','Tylko w v5','Tylko z flushSync','Domyślnie nie trzeba.');
q('Jak przejście wpływa na kolejność efektów?','Efekty commitu przejścia uruchamiają się po tym commicie, osobno od pilnego','Uruchamia efekty przed renderem','Wyłącza efekty','Nie ma efektów','Każdy commit ma swoje efekty.');
q('Co jest przewagą useTransition nad useDeferredValue?','Daje jawny isPending i kontrolę, która aktualizacja jest niepilna','Działa bez settera','Jest szybszy','Nie wymaga memo','useDeferredValue daje stale przez porównanie.');
q('Co jest przewagą useDeferredValue nad useTransition?','Działa z wartością z zewnątrz, bez dostępu do settera','Daje isPending','Zmniejsza liczbę żądań','Przyspiesza render','Np. komponent biblioteki dostaje props.');
const probes=[
  ['Commity po zmianie w przejściu (input|filter|isPending)','a||true,a|a|false','a|a|false','a||false,a|a|true','a||true',String.raw`let type!:(v:string)=>void;function C(){const [input,setInput]=useState("");const [filter,setFilter]=useState("");const [isPending,start]=useTransition();type=v=>{setInput(v);start(()=>setFilter(v));};useLayoutEffect(()=>{log.push(input+"|"+filter+"|"+isPending);});return null;}await mount(<C/>);log.length=0;await act(async()=>type("a"));console.log(log.join(","));`],
  ['Commity bez przejścia (oba settery pilne)','a|a|false','a||true,a|a|false','a||false,a|a|false','a||true',String.raw`let type!:(v:string)=>void;function C(){const [input,setInput]=useState("");const [filter,setFilter]=useState("");const [isPending]=useTransition();type=v=>{setInput(v);setFilter(v);};useLayoutEffect(()=>{log.push(input+"|"+filter+"|"+isPending);});return null;}await mount(<C/>);log.length=0;await act(async()=>type("a"));console.log(log.join(","));`],
  ['useDeferredValue: commity po pilnej zmianie (value|deferred)','a|,a|a','a|a','|a,a|a','a|',String.raw`let set!:(v:string)=>void;function C(){const [v,setV]=useState("");set=setV;const d=useDeferredValue(v);useLayoutEffect(()=>{log.push(v+"|"+d);});return null;}await mount(<C/>);log.length=0;await act(async()=>set("a"));console.log(log.join(","));`],
  ['useDeferredValue w aktualizacji będącej przejściem','a|a','a|,a|a','|a','a|',String.raw`let set!:(v:string)=>void;function C(){const [v,setV]=useState("");set=setV;const d=useDeferredValue(v);useLayoutEffect(()=>{log.push(v+"|"+d);});return null;}await mount(<C/>);log.length=0;await act(async()=>startTransition(()=>set("a")));console.log(log.join(","));`],
  ['useDeferredValue z wartością początkową przy montowaniu','pusty,abc','abc','abc,abc','pusty',String.raw`log.length=0;function C({q}:{q:string}){const d=useDeferredValue(q,"");useLayoutEffect(()=>{log.push(d||"pusty");});return null;}await mount(<C q="abc"/>);console.log(log.join(","));`],
  ['useDeferredValue bez wartości początkowej przy montowaniu','abc','pusty,abc','abc,abc','pusty',String.raw`log.length=0;function C({q}:{q:string}){const d=useDeferredValue(q);useLayoutEffect(()=>{log.push(d||"pusty");});return null;}await mount(<C q="abc"/>);console.log(log.join(","));`],
  ['isPending przy montowaniu','false','true','undefined','null',String.raw`let seen="";function C(){const [isPending]=useTransition();seen=String(isPending);return null;}await mount(<C/>);console.log(seen);`],
  ['Samodzielny startTransition: wynik w DOM','gotowe','start','pusty','błąd',String.raw`let set!:(v:string)=>void;function C(){const [v,setV]=useState("start");set=setV;return <p>{v}</p>;}const view=await mount(<C/>);await act(async()=>startTransition(()=>set("gotowe")));console.log(view.host.textContent);`],
  ['Rendery tabeli w memo przy przejściu','1','2','0','3',String.raw`let renders=0;const Table=memo(function Table({q}:{q:string}){renders+=1;return <p>{q}</p>;});let type!:(v:string)=>void;function C(){const [input,setInput]=useState("");const [filter,setFilter]=useState("");const [,start]=useTransition();type=v=>{setInput(v);start(()=>setFilter(v));};return <div data-i={input}><Table q={filter}/></div>;}await mount(<C/>);renders=0;await act(async()=>type("a"));console.log(renders);`],
  ['Rendery tabeli bez memo przy przejściu','2','1','0','3',String.raw`let renders=0;function Table({q}:{q:string}){renders+=1;return <p>{q}</p>;}let type!:(v:string)=>void;function C(){const [input,setInput]=useState("");const [filter,setFilter]=useState("");const [,start]=useTransition();type=v=>{setInput(v);start(()=>setFilter(v));};return <div data-i={input}><Table q={filter}/></div>;}await mount(<C/>);renders=0;await act(async()=>type("a"));console.log(renders);`],
  ['Rendery memo-dziecka z wartością odroczoną','1','2','0','3',String.raw`let renders=0;const Table=memo(function Table({q}:{q:string}){renders+=1;return <p>{q}</p>;});let set!:(v:string)=>void;function C(){const [v,setV]=useState("");set=setV;const d=useDeferredValue(v);return <div data-v={v}><Table q={d}/></div>;}await mount(<C/>);renders=0;await act(async()=>set("a"));console.log(renders);`],
  ['Rendery zwykłego dziecka z wartością odroczoną','2','1','0','3',String.raw`let renders=0;function Table({q}:{q:string}){renders+=1;return <p>{q}</p>;}let set!:(v:string)=>void;function C(){const [v,setV]=useState("");set=setV;const d=useDeferredValue(v);return <div data-v={v}><Table q={d}/></div>;}await mount(<C/>);renders=0;await act(async()=>set("a"));console.log(renders);`],
  ['Dwa settery w jednym przejściu z useTransition: liczba commitów','2','1','0','3',String.raw`let commits=0;let run!:()=>void;function C(){const [a,setA]=useState(0);const [b,setB]=useState(0);const [,start]=useTransition();run=()=>start(()=>{setA(1);setB(1);});useLayoutEffect(()=>{commits+=1;});return <p>{a+b}</p>;}await mount(<C/>);commits=0;await act(async()=>run());console.log(commits);`],
  ['Dwa przejścia w jednym act: wartość wyrenderowana w tabeli','ab','a','a,ab','pusty',String.raw`const Table=memo(function Table({q}:{q:string}){log.push(q||"pusty");return null;});let run!:()=>void;function C(){const [f,setF]=useState("");const [,start]=useTransition();run=()=>{start(()=>setF("a"));start(()=>setF("ab"));};return <Table q={f}/>;}await mount(<C/>);log.length=0;await act(async()=>run());console.log(log.join(","));`],
  ['Tożsamość startTransition między renderami','true','false','undefined','błąd',String.raw`const fns:unknown[]=[];function C({n}:{n:number}){const [,start]=useTransition();fns.push(start);return <p>{n}</p>;}const view=await mount(<C n={1}/>);await view.update(<C n={2}/>);console.log(String(fns[0]===fns[1]));`],
  ['isPending podczas ładowania leniwej zakładki','true','false','undefined','błąd',String.raw`let resolve!:(m:{default:ComponentType})=>void;const Tab=lazy(()=>new Promise<{default:ComponentType}>(r=>{resolve=r;}));let go!:()=>void;let pending="";function C(){const [t,setT]=useState("a");const [isPending,start]=useTransition();useLayoutEffect(()=>{pending=String(isPending);});go=()=>start(()=>setT("b"));return <Suspense fallback={<p>…</p>}>{t==="a"?<p>A</p>:<Tab/>}</Suspense>;}await mount(<C/>);await act(async()=>go());console.log(pending);void resolve;`],
  ['isPending po załadowaniu leniwej zakładki','false','true','undefined','błąd',String.raw`let resolve!:(m:{default:ComponentType})=>void;const promise=new Promise<{default:ComponentType}>(r=>{resolve=r;});const Tab=lazy(()=>promise);let go!:()=>void;let pending="";function C(){const [t,setT]=useState("a");const [isPending,start]=useTransition();useLayoutEffect(()=>{pending=String(isPending);});go=()=>start(()=>setT("b"));return <Suspense fallback={<p>…</p>}>{t==="a"?<p>A</p>:<Tab/>}</Suspense>;}await mount(<C/>);await act(async()=>go());await act(async()=>{resolve({default:()=><p>B</p>});await promise;});console.log(pending);`],
  ['Zakładka w przejściu: widoczna treść w trakcie ładowania','A','…','pusty','B',String.raw`const Tab=lazy(()=>new Promise<{default:ComponentType}>(()=>{}));let go!:()=>void;function C(){const [t,setT]=useState("a");const [,start]=useTransition();go=()=>start(()=>setT("b"));return <Suspense fallback={<p>…</p>}>{t==="a"?<p>A</p>:<Tab/>}</Suspense>;}const view=await mount(<C/>);await act(async()=>go());console.log([...view.host.children].filter(e=>getComputedStyle(e).display!=="none").map(e=>e.textContent).join("|")||"pusty");`],
  ['Zakładka bez przejścia: widoczna treść w trakcie ładowania','…','A','pusty','B',String.raw`const Tab=lazy(()=>new Promise<{default:ComponentType}>(()=>{}));let go!:()=>void;function C(){const [t,setT]=useState("a");go=()=>setT("b");return <Suspense fallback={<p>…</p>}>{t==="a"?<p>A</p>:<Tab/>}</Suspense>;}const view=await mount(<C/>);await act(async()=>go());console.log([...view.host.children].filter(e=>getComputedStyle(e).display!=="none").map(e=>e.textContent).join("|")||"pusty");`],
  ['Żądania z przejściem po trzech znakach (osobne act)','3','1','0','2',String.raw`let requests=0;let type!:(v:string)=>void;function C(){const [input,setInput]=useState("");const [q,setQ]=useState("");const [,start]=useTransition();type=v=>{setInput(v);start(()=>setQ(v));};useEffect(()=>{if(q)requests+=1;},[q]);return <p>{input}</p>;}await mount(<C/>);for(const v of ["a","ap","api"])await act(async()=>type(v));console.log(requests);`],
  ['Żądania z debounce 20 ms po trzech szybkich znakach','1','3','0','2',String.raw`let requests=0;let type!:(v:string)=>void;function useDebounced(v:string,ms:number){const [d,setD]=useState(v);useEffect(()=>{const id=setTimeout(()=>setD(v),ms);return ()=>clearTimeout(id);},[v,ms]);return d;}function C(){const [input,setInput]=useState("");type=setInput;const q=useDebounced(input,20);useEffect(()=>{if(q)requests+=1;},[q]);return <p>{input}</p>;}await mount(<C/>);for(const v of ["a","ap","api"])await act(async()=>type(v));await act(async()=>{await new Promise(r=>setTimeout(r,60));});console.log(requests);`],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 w Vitest (createRoot + act w jsdom, bez StrictMode).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/types.ts':types,'src/FilteredTable.tsx':table,'src/TicketSearch.tsx':search,'src/DeferredResults.tsx':deferred,'src/Tabs.tsx':tabs,'src/ApiSearch.tsx':debounce,'src/ReportsChart.tsx':chart,'src/transitions.test.tsx':tests};
const mutants=[
  {file:'src/TicketSearch.tsx',before:'startTransition(()=>setFilter(e.target.value));',after:'setFilter(e.target.value);'},
  {file:'src/FilteredTable.tsx',before:'export const FilteredTable=memo(function FilteredTable',after:'export const FilteredTable=(function FilteredTable'},
  {file:'src/DeferredResults.tsx',before:'const deferredQuery=useDeferredValue(query);',after:'const deferredQuery=query;'},
  {file:'src/Tabs.tsx',before:'onClick={()=>startTransition(()=>setActive(name))}',after:'onClick={()=>setActive(name)}'},
  {file:'src/ApiSearch.tsx',before:'return ()=>clearTimeout(id);',after:'return undefined;'},
  {file:'src/ReportsChart.tsx',before:'const HeavyChart=memo(function HeavyChart',after:'const HeavyChart=(function HeavyChart'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
