// Lekcja 15/05: batching, harmonogram i priorytety. Zachowanie sprawdza prawdziwy React 19 w Vitest (jsdom).
const batching=String.raw`import {useState} from "react";
// wait wstrzykiwany z zewnątrz — test kontroluje moment odpowiedzi; onRender liczy rendery.
export function BatchingDemo({wait,onRender}:{wait:()=>Promise<void>;onRender:()=>void}){
  const [items,setItems]=useState<string[]>([]);
  const [loading,setLoading]=useState(false);
  const [error,setError]=useState<string|null>(null);
  onRender();
  async function load(){
    setLoading(true);
    setError(null);
    await wait();
    // trzy aktualizacje po await — jeden render (React 18+)
    setItems(["A","B","C"]);
    setLoading(false);
    setError(null);
  }
  return <div>
    <button type="button" onClick={()=>void load()}>Wczytaj</button>
    <p>{loading?"Ładowanie…":items.join(", ")} {error}</p>
  </div>;
}`;
const union=String.raw`import {useState} from "react";
export type Load<T>={kind:"idle"}|{kind:"loading"}|{kind:"success";data:T}|{kind:"error";message:string};
// Jedna zmienna stanu jako unia: nie ma pośrednich kombinacji (loading=false i brak danych), więc nie ma czego grupować.
export function UnionDemo({fetchItems}:{fetchItems:()=>Promise<string[]>}){
  const [state,setState]=useState<Load<string[]>>({kind:"idle"});
  async function load(){
    setState({kind:"loading"});
    try{setState({kind:"success",data:await fetchItems()});}
    catch{setState({kind:"error",message:"Nie udało się wczytać."});}
  }
  return <div>
    <button type="button" onClick={()=>void load()}>Wczytaj</button>
    {state.kind==="loading"&&<p>Ładowanie…</p>}
    {state.kind==="success"&&<p>{state.data.join(", ")}</p>}
    {state.kind==="error"&&<p role="alert">{state.message}</p>}
  </div>;
}`;
const split=String.raw`import {startTransition,useState} from "react";
export function useSplitPriority(){
  const [input,setInput]=useState("");   // pilne: to, co widzi użytkownik w polu
  const [filter,setFilter]=useState(""); // niepilne: kosztowne filtrowanie listy
  function handleChange(value:string){
    setInput(value);
    startTransition(()=>setFilter(value));
  }
  return {input,filter,handleChange};
}
export function FilteredList({items}:{items:string[]}){
  const {input,filter,handleChange}=useSplitPriority();
  const visible=items.filter(item=>item.toLowerCase().includes(filter.toLowerCase()));
  return <div>
    <input aria-label="Szukaj" value={input} onChange={e=>handleChange(e.target.value)}/>
    <ul>{visible.map(item=><li key={item}>{item}</li>)}</ul>
  </div>;
}`;
const tabs=String.raw`import {Suspense,use,useState,useTransition} from "react";
export type TabLoader=(tab:string)=>Promise<string>;
const cache=new Map<string,Promise<string>>();
export function resetTabCache(){cache.clear();}
function load(tab:string,loader:TabLoader){
  if(!cache.has(tab))cache.set(tab,loader(tab));
  return cache.get(tab)!;
}
function TabContent({tab,loader}:{tab:string;loader:TabLoader}){
  return <p>{use(load(tab,loader))}</p>;
}
// Zmiana zakładki jako przejście: React trzyma stary widok i pokazuje isPending zamiast fallbacku.
export function TicketTabs({loader}:{loader:TabLoader}){
  const [tab,setTab]=useState("szczegóły");
  const [note,setNote]=useState("");
  const [isPending,startTransition]=useTransition();
  function select(next:string){startTransition(()=>setTab(next));}
  return <div>
    <div role="tablist">{["szczegóły","historia"].map(name=><button key={name} role="tab" aria-selected={tab===name} type="button" onClick={()=>select(name)}>{name}</button>)}</div>
    {isPending&&<small>Wczytywanie zakładki…</small>}
    <input aria-label="Notatka" value={note} onChange={e=>setNote(e.target.value)}/>
    <Suspense fallback={<p>Ładowanie zakładki…</p>}><TabContent tab={tab} loader={loader}/></Suspense>
  </div>;
}`;
const tagAdder=String.raw`import {useRef,useState} from "react";
import {flushSync} from "react-dom";
export function TagAdder(){
  const [tags,setTags]=useState<string[]>([]);
  const listRef=useRef<HTMLUListElement>(null);
  function addTag(tag:string){
    flushSync(()=>setTags(prev=>[...prev,tag])); // DOM zaktualizowany przed następną linią
    listRef.current?.lastElementChild?.scrollIntoView({block:"nearest"});
  }
  return <>
    <button type="button" onClick={()=>addTag("tag-"+(tags.length+1))}>Dodaj tag</button>
    <ul ref={listRef}>{tags.map(t=><li key={t}>{t}</li>)}</ul>
  </>;
}`;
const batchTests=String.raw`import {act,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {afterEach,expect,test,vi} from "vitest";
import {BatchingDemo} from "./BatchingDemo";
import {FilteredList} from "./FilteredList";
import {TagAdder} from "./TagAdder";
import {UnionDemo} from "./UnionDemo";
afterEach(()=>{delete (Element.prototype as {scrollIntoView?:unknown}).scrollIntoView;});
test("kliknięcie Wczytaj daje dwa rendery: przed await i po await",async()=>{
  let renders=0,release!:()=>void;
  const gate=new Promise<void>(resolve=>{release=resolve;});
  const user=userEvent.setup();
  render(<BatchingDemo wait={()=>gate} onRender={()=>{renders+=1;}}/>);
  expect(renders).toBe(1);
  await user.click(screen.getByRole("button",{name:"Wczytaj"}));
  expect(renders).toBe(2);
  expect(screen.getByText("Ładowanie…")).toBeInTheDocument();
  await act(async()=>{release();await gate;});
  expect(renders).toBe(3);
  expect(screen.getByText("A, B, C")).toBeInTheDocument();
});
test("unia stanu przechodzi loading → success bez stanów pośrednich",async()=>{
  const user=userEvent.setup();
  let resolve!:(items:string[])=>void;
  render(<UnionDemo fetchItems={()=>new Promise(r=>{resolve=r;})}/>);
  await user.click(screen.getByRole("button",{name:"Wczytaj"}));
  expect(screen.getByText("Ładowanie…")).toBeInTheDocument();
  await act(async()=>{resolve(["A","B"]);});
  expect(screen.getByText("A, B")).toBeInTheDocument();
  expect(screen.queryByText("Ładowanie…")).not.toBeInTheDocument();
});
test("unia stanu pokazuje błąd zamiast ładowania",async()=>{
  const user=userEvent.setup();
  render(<UnionDemo fetchItems={()=>Promise.reject(new Error("500"))}/>);
  await user.click(screen.getByRole("button",{name:"Wczytaj"}));
  expect(await screen.findByRole("alert")).toHaveTextContent("Nie udało się wczytać.");
});
test("pole jest pilne, filtr listy przychodzi z przejścia",async()=>{
  const user=userEvent.setup();
  render(<FilteredList items={["API timeout","Logowanie SSO","Eksport CSV"]}/>);
  await user.type(screen.getByLabelText("Szukaj"),"api");
  expect(screen.getByLabelText("Szukaj")).toHaveValue("api");
  expect(screen.getAllByRole("listitem").map(li=>li.textContent)).toEqual(["API timeout"]);
});
test("flushSync pozwala przewinąć do właśnie dodanego tagu",async()=>{
  const targets:string[]=[];
  Element.prototype.scrollIntoView=vi.fn(function(this:Element){targets.push(this.textContent??"");});
  const user=userEvent.setup();
  render(<TagAdder/>);
  await user.click(screen.getByRole("button",{name:"Dodaj tag"}));
  await user.click(screen.getByRole("button",{name:"Dodaj tag"}));
  expect(targets).toEqual(["tag-1","tag-2"]);
});`;
const tabTests=String.raw`import {act,fireEvent,render,screen} from "@testing-library/react";
import {beforeEach,expect,test} from "vitest";
import {resetTabCache,TicketTabs,type TabLoader} from "./TicketTabs";
beforeEach(()=>resetTabCache());
function controlledLoader(){
  const pending=new Map<string,{resolve:(value:string)=>void;promise:Promise<string>}>();
  const loader:TabLoader=tab=>{
    if(tab==="szczegóły")return Promise.resolve("Treść: szczegóły");
    let resolve!:(value:string)=>void;
    const promise=new Promise<string>(r=>{resolve=r;});
    pending.set(tab,{resolve,promise});
    return promise;
  };
  // Rozwiązanie i odczekanie obietnicy w tym samym act pozwala Reactowi dokończyć przejście.
  return {loader,resolve:async(tab:string)=>{const entry=pending.get(tab)!;entry.resolve("Treść: "+tab);await entry.promise;}};
}
test("przejście trzyma stary widok i pokazuje isPending zamiast fallbacku",async()=>{
  const {loader,resolve}=controlledLoader();
  await act(async()=>{render(<TicketTabs loader={loader}/>);});
  expect(screen.getByText("Treść: szczegóły")).toBeInTheDocument();
  // Kliknięcie przez await act: przejście zawieszone w tym zakresie zostanie dokończone po rozwiązaniu obietnicy.
  await act(async()=>{screen.getByRole("tab",{name:"historia"}).click();});
  expect(screen.getByText("Wczytywanie zakładki…")).toBeInTheDocument();
  expect(screen.getByText("Treść: szczegóły")).toBeInTheDocument();
  expect(screen.queryByText("Ładowanie zakładki…")).not.toBeInTheDocument();
  await act(async()=>{await resolve("historia");});
  expect(screen.getByText("Treść: historia")).toBeInTheDocument();
  expect(screen.queryByText("Wczytywanie zakładki…")).not.toBeInTheDocument();
});
test("pilne pisanie działa, gdy przejście wciąż czeka",async()=>{
  const {loader,resolve}=controlledLoader();
  await act(async()=>{render(<TicketTabs loader={loader}/>);});
  await act(async()=>{screen.getByRole("tab",{name:"historia"}).click();});
  // Pilna zmiana pola w trakcie czekającego przejścia; await act, bo React ponawia przy niej zawieszone przejście.
  await act(async()=>{fireEvent.change(screen.getByLabelText("Notatka"),{target:{value:"pilne"}});});
  expect(screen.getByLabelText("Notatka")).toHaveValue("pilne");
  expect(screen.getByText("Wczytywanie zakładki…")).toBeInTheDocument();
  expect(screen.getByRole("tab",{name:"szczegóły"})).toHaveAttribute("aria-selected","true");
  await act(async()=>{await resolve("historia");});
  expect(screen.getByRole("tab",{name:"historia"})).toHaveAttribute("aria-selected","true");
});`;
const header=String.raw`import {act,startTransition,useState,useTransition,Suspense,use,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
import {flushSync} from "react-dom";
let renders=0;
function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);act(()=>root.render(ui));return {host,root};}
const sleep=(ms:number)=>new Promise<void>(resolve=>setTimeout(resolve,ms));
void startTransition;void useTransition;void Suspense;void use;void flushSync;void sleep;
`;
const exercises=[
  ['Zbuduj BatchingDemo z trzema zmiennymi stanu i aktualizacjami przed i po await.','Wstrzyknij funkcję wait i licznik renderów przez props, żeby test kontrolował czas.',batching],
  ['Udowodnij testem, że kliknięcie daje dwa rendery, a nie cztery.','Bramka Promise zatrzymuje kod po await do chwili jej zwolnienia.',batchTests],
  ['Przepisz BatchingDemo na jedną zmienną stanu jako unię.','idle | loading | success | error.',union],
  ['Przetestuj przejście unii loading → success.','Po rozwiązaniu obietnicy nie ma już Ładowanie….',batchTests],
  ['Przetestuj przejście unii do błędu.','Odrzucona obietnica daje komunikat w roli alert.',batchTests],
  ['Napisz hook useSplitPriority: pilne pole i niepilny filtr.','setInput poza przejściem, setFilter w startTransition.',split],
  ['Zbuduj listę filtrowaną przez niepilny filtr z hooka.','Lista czyta filter, pole — input.',split],
  ['Przetestuj, że pole i przefiltrowana lista są zgodne po wpisaniu.','user.type wpisuje znak po znaku.',batchTests],
  ['Zbuduj zakładki, w których zmiana zakładki jest przejściem, a treść ładuje się przez use().','Cache obietnic według zakładki zapobiega nowym obietnicom w każdym renderze.',tabs],
  ['Przetestuj, że przejście trzyma stary widok i isPending zamiast fallbacku.','Zawieszony render przejścia nie zastępuje widocznej treści.',tabTests],
  ['Przetestuj, że pilne pisanie działa, gdy przejście czeka.','Pole Notatka aktualizuje się mimo niezakończonego przejścia.',tabTests],
  ['Zbuduj TagAdder z flushSync i przewinięciem do nowego tagu.','Po flushSync ostatni element listy to nowy tag.',tagAdder],
  ['Przetestuj, że przewinięcie trafia w właśnie dodany tag.','Zamockuj scrollIntoView i zapisz tekst elementu.',batchTests],
  ['Pokaż grupowanie aktualizacji w setTimeout.','Od React 18 setTimeout też jest grupowany.',String.raw`import {useState} from "react";
export function TimeoutBatch({onRender}:{onRender:()=>void}){
  const [a,setA]=useState(0),[b,setB]=useState(0);
  onRender();
  return <button type="button" onClick={()=>setTimeout(()=>{setA(x=>x+1);setB(x=>x+1);},0)}>{a}/{b}</button>;
}`],
  ['Pokaż grupowanie w natywnym nasłuchu addEventListener.','Batching obejmuje też zdarzenia spoza systemu zdarzeń Reacta.',String.raw`import {useEffect,useRef,useState} from "react";
export function NativeBatch({onRender}:{onRender:()=>void}){
  const [a,setA]=useState(0),[b,setB]=useState(0);
  const ref=useRef<HTMLButtonElement>(null);
  onRender();
  useEffect(()=>{
    const button=ref.current;
    const handler=()=>{setA(x=>x+1);setB(x=>x+1);};
    button?.addEventListener("click",handler);
    return ()=>button?.removeEventListener("click",handler);
  },[]);
  return <button ref={ref} type="button">{a}/{b}</button>;
}`],
  ['Zastąp unstable_batchedUpdates ze starego kodu zwykłymi setterami.','W React 18+ batching jest automatyczny.',String.raw`import {useState} from "react";
// React 17: unstable_batchedUpdates(() => { setA(1); setB(2); }) w setTimeout.
// React 18+: zwykłe wywołania są grupowane automatycznie.
export function Migrated(){
  const [a,setA]=useState(0),[b,setB]=useState(0);
  return <button type="button" onClick={()=>setTimeout(()=>{setA(1);setB(2);},0)}>{a}+{b}</button>;
}`],
  ['Wyjaśnij, dlaczego wartości pola nie umieszcza się w startTransition.','Pole może nie nadążać za pisaniem.',String.raw`// Wartość kontrolowanego pola jest zawsze pilna — użytkownik widzi każdy znak.
// startTransition(() => setInput(value)) pozwala Reactowi odłożyć tę aktualizację,
// więc pole może zostać w tyle za pisaniem, a kursor — skakać.
// W przejściu umieszczasz tylko kosztowne skutki: filtrowanie, wykresy, przeliczenia.
export {};`],
  ['Wypisz pasy priorytetów od najwyższego do najniższego.','Sync, ciągłe, Default, Transition, Idle.',String.raw`// SyncLane (zdarzenia dyskretne: kliknięcie, klawisz, wpisanie tekstu)
// → zdarzenia ciągłe (przewijanie, ruch myszy, przeciąganie)
// → DefaultLane (aktualizacje spoza zdarzeń: odpowiedź sieci, setTimeout)
// → TransitionLanes (startTransition / useTransition)
// → IdleLane (praca w tle)
export {};`],
  ['Pokaż wskaźnik isPending przy filtrowaniu przez useTransition.','isPending pokazuje, że niepilny render jeszcze trwa.',String.raw`import {useState,useTransition} from "react";
export function PendingFilter({items}:{items:string[]}){
  const [input,setInput]=useState(""),[filter,setFilter]=useState("");
  const [isPending,startTransition]=useTransition();
  return <div>
    <input aria-label="Szukaj" value={input} onChange={e=>{setInput(e.target.value);startTransition(()=>setFilter(e.target.value));}}/>
    {isPending&&<small>Filtrowanie…</small>}
    <ul style={{opacity:isPending?0.6:1}}>{items.filter(i=>i.includes(filter)).map(i=><li key={i}>{i}</li>)}</ul>
  </div>;
}`],
  ['Pokaż, że funkcyjne aktualizacje w jednym batchu liczą od poprzedniej wartości.','setN(x => x + 1) dwa razy daje +2.',String.raw`import {useState} from "react";
export function DoubleIncrement(){
  const [n,setN]=useState(0);
  return <button type="button" onClick={()=>{setN(x=>x+1);setN(x=>x+1);}}>{n}</button>;
}`],
  ['Pokaż, że setN(n+1) dwa razy w jednym batchu daje +1.','Oba wywołania widzą tę samą migawkę n.',String.raw`import {useState} from "react";
export function SnapshotIncrement(){
  const [n,setN]=useState(0);
  return <button type="button" onClick={()=>{setN(n+1);setN(n+1);}}>{n}</button>;
}`],
  ['Napisz test liczby renderów dla aktualizacji w setTimeout.','Użyj await act z oczekiwaniem na timer.',String.raw`import {useState} from "react";
import {act,render,screen} from "@testing-library/react";
import {expect,test} from "vitest";
test("setTimeout grupuje",async()=>{
  let renders=0;
  function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;return <button type="button" onClick={()=>setTimeout(()=>{setA(1);setB(1);},0)}>{a+b}</button>;}
  render(<C/>);
  await act(async()=>{screen.getByRole("button").click();await new Promise(r=>setTimeout(r,5));});
  expect(renders).toBe(2);
  expect(screen.getByRole("button")).toHaveTextContent("2");
});`],
  ['Napisz test, że flushSync w środku batcha tworzy osobny render.','Licznik renderów rośnie o 2 zamiast 1.',String.raw`import {useState} from "react";
import {flushSync} from "react-dom";
import {act,render,screen} from "@testing-library/react";
import {expect,test} from "vitest";
test("flushSync rozdziela render",()=>{
  let renders=0;
  function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;return <button type="button" onClick={()=>{flushSync(()=>setA(1));setB(1);}}>{a+b}</button>;}
  render(<C/>);
  act(()=>screen.getByRole("button").click());
  expect(renders).toBe(3);
});`],
  ['Zbuduj cache obietnic dla use(), żeby render nie tworzył nowej obietnicy.','Nowa obietnica w każdym renderze = nieskończone zawieszanie.',tabs],
  ['Wyjaśnij, co robi React, gdy pilna aktualizacja przyjdzie w trakcie niepilnego renderu.','Przerywa, renderuje pilną, potem wznawia lub powtarza.',String.raw`// Niepilny render (przejście) jest przerywany przy nadejściu aktualizacji z wyższego pasa.
// React renderuje i commituje pilną zmianę (np. znak w polu), a potem zaczyna przejście od nowa
// z najnowszym stanem (np. filtr "ap" zamiast "a"). Rozpoczęty WIP przejścia jest porzucany.
export {};`],
  ['Przenieś kosztowny wykres do przejścia, zostawiając suwak pilnym.','Suwak (wartość wejścia) pilny, wykres niepilny.',String.raw`import {useState,useTransition} from "react";
export function RangeChart({compute}:{compute:(days:number)=>number[]}){
  const [days,setDays]=useState(7),[chartDays,setChartDays]=useState(7);
  const [,startTransition]=useTransition();
  const points=compute(chartDays);
  return <div>
    <input type="range" aria-label="Dni" min={1} max={90} value={days} onChange={e=>{const v=Number(e.target.value);setDays(v);startTransition(()=>setChartDays(v));}}/>
    <p>Punktów: {points.length}</p>
  </div>;
}`],
  ['Opisz różnicę między startTransition a setTimeout dla niepilnej pracy.','Przejście jest przerywalne i zna priorytety; setTimeout tylko opóźnia.',String.raw`// setTimeout tylko odkłada start pracy — gdy render ruszy, jest tak samo nieprzerywalny i blokujący.
// startTransition oznacza aktualizację niskim pasem: render może zostać przerwany przez pilną zmianę,
// porzucony i wznowiony, a isPending daje informację o trwającej pracy. Nie dodaje sztucznego opóźnienia.
export {};`],
  ['Napisz test, że startTransition bez zawieszenia kończy się w jednym act.','W środowisku act przejście zostaje dokończone.',String.raw`import {startTransition,useState} from "react";
import {act,render,screen} from "@testing-library/react";
import {expect,test} from "vitest";
test("przejście w act",()=>{
  let set!:(v:string)=>void;
  function C(){const [v,setV]=useState("a");set=setV;return <p>{v}</p>;}
  render(<C/>);
  act(()=>startTransition(()=>set("b")));
  expect(screen.getByText("b")).toBeInTheDocument();
});`],
  ['Zastanów się, gdzie w DevDesk rozdzielić priorytety.','Pola wejściowe pilne; tabela 5000 ticketów, wykresy — w przejściu.',String.raw`// Pilne: wartości pól wyszukiwania i filtrów, przełączniki, przyciski.
// Niepilne (startTransition): przefiltrowana tabela 5000 ticketów, przeliczone statystyki, wykres SLA,
// zmiana zakładki z danymi ładowanymi przez Suspense (stary widok zostaje do czasu gotowości).
export {};`],
  ['Pokaż, że po zakończeniu zawieszonego przejścia znika isPending i zmienia się aria-selected.','Rozwiąż obietnicę zakładki w await act.',tabTests],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Co oznacza, że aktualizacje „trafiają do tego samego renderu”?','React stosuje je wszystkie i renderuje komponent raz z końcowym stanem','React renderuje po każdej','React pomija niektóre','React renderuje w osobnych wątkach','Użytkownik nie widzi stanów pośrednich.');
q('Co jest typowym objawem braku rozdzielenia priorytetów?','Pole tekstowe „zacina się” przy pisaniu w dużej liście','Błąd 500','Brak stylów','Wyciek pamięci','Każdy znak wymusza pilny, ciężki render.');
q('Kiedy wybrać flushSync zamiast efektu?','Gdy w tym samym handlerze, tuż po aktualizacji, potrzebny jest zaktualizowany DOM','Do każdego ładowania danych','Do animacji','Do grupowania','W pozostałych przypadkach efekt jest czytelniejszy.');
q('Czym jest automatyczny batching?','Grupowaniem aktualizacji z jednego taktu w jeden render','Renderowaniem w osobnym wątku','Opóźnianiem renderu o sekundę','Cache’owaniem komponentów','Od React 18 obejmuje także setTimeout, obietnice i zdarzenia natywne.');
q('Od której wersji batching działa po await i w setTimeout?','React 18','React 16','React 17','React 19','Wcześniej tylko w handlerach zdarzeń Reacta.');
q('Ile renderów dadzą trzy settery po await w React 18+?','Jeden','Trzy','Dwa','Zero','Wszystkie są w tym samym takcie.');
q('Ile renderów daje kliknięcie „Wczytaj” w BatchingDemo (poza montowaniem)?','Dwa: przed await i po await','Cztery','Jeden','Pięć','Await dzieli handler na dwa takty.');
q('Dlaczego await dzieli aktualizacje na dwa rendery?','Kod po await wykonuje się w innym takcie (mikrozadaniu)','Bo await wyłącza batching','Bo React czeka na sieć','Bo setLoading jest synchroniczny','Aktualizacje przed await renderują się wcześniej.');
q('Czym było unstable_batchedUpdates?','Ręcznym grupowaniem aktualizacji poza handlerami w React 17','Nowym API React 19','Hookiem do danych','Narzędziem DevTools','W React 18+ niepotrzebne.');
q('Czy batching obejmuje natywny addEventListener?','Tak, od React 18','Nie','Tylko w Chrome','Tylko z flushSync','Automatyczny batching nie zależy od źródła zdarzenia.');
q('Co daje stan jako unia zamiast trzech zmiennych?','Brak pośrednich, niespójnych kombinacji stanu','Więcej renderów','Szybszy fetch','Brak potrzeby TypeScriptu','Z jedną zmienną nie ma czego grupować.');
q('Czy TypeScript widzi batching i priorytety?','Nie, to zachowanie środowiska uruchomieniowego','Tak, przez typy hooków','Tak, w trybie strict','Tylko dla useTransition','Typy pomagają pośrednio — np. unią stanu.');
q('Czym jest lane w Reakcie?','Priorytetem aktualizacji','Wątkiem','Kolejką DOM','Typem komponentu','Harmonogram najpierw renderuje pas o najwyższym priorytecie.');
q('Jaki priorytet ma kliknięcie lub wpisanie znaku?','Najwyższy (zdarzenia dyskretne)','Niski','Transition','Idle','Użytkownik oczekuje natychmiastowej reakcji.');
q('Jaki priorytet mają przewijanie i ruch myszy?','Wysoki (zdarzenia ciągłe)','Najwyższy','Idle','Transition','Zdarzenia ciągłe są pilne, ale mogą być łączone.');
q('Jaki pas dostaje aktualizacja po odpowiedzi sieci w setTimeout?','DefaultLane','SyncLane','IdleLane','TransitionLane','Aktualizacje spoza zdarzeń mają domyślny priorytet.');
q('Jaki pas dostaje aktualizacja w startTransition?','TransitionLane','SyncLane','DefaultLane','IdleLane','Niepilna, przerywalna.');
q('Co robi harmonogram z niepilnym renderem, gdy przyjdzie pilna aktualizacja?','Przerywa go, renderuje pilną zmianę, potem wznawia lub powtarza przejście','Kończy przejście, potem pilną','Odrzuca pilną aktualizację','Łączy oba w jeden commit natychmiast','Pilne zawsze mają pierwszeństwo.');
q('Dlaczego pisanie z transition jest płynne przy ciężkiej tabeli?','Render tabeli jest przerywany przez każdy znak, a pole renderuje się szybko','Bo tabela renderuje się w Workerze','Bo React pomija tabelę','Bo przeglądarka przyspiesza','Kosztowna praca nie blokuje pilnych aktualizacji.');
q('Czego nie umieszczać w startTransition?','Wartości pola, które użytkownik edytuje','Filtrowania listy','Przeliczenia wykresu','Zmiany zakładki','Pole mogłoby nie nadążać za pisaniem.');
q('Co zwraca useTransition?','[isPending, startTransition]','[startTransition, isPending]','Promise','Stan i setter','isPending informuje o trwającym przejściu.');
q('Czym różni się startTransition (funkcja) od useTransition?','Funkcja nie daje isPending; hook daje isPending','Niczym','Funkcja działa tylko w klasach','Hook nie oznacza aktualizacji jako niepilnej','Funkcja przydaje się poza komponentami.');
q('Co robi React, gdy przejście zawiesza się (Suspense)?','Trzyma stary widok i isPending zamiast pokazywać fallback','Pokazuje fallback od razu','Rzuca błąd','Czyści ekran','To kluczowa różnica wobec pilnej aktualizacji.');
q('Co pokaże zmiana zakładki bez transition, gdy nowa treść się zawiesi?','Fallback Suspense zamiast starej treści','Starą treść','Pusty ekran bez fallbacku','Błąd','Pilna aktualizacja nie może czekać na dane.');
q('Czy w trakcie czekającego przejścia działa pisanie w polu?','Tak, pilne aktualizacje są renderowane od razu','Nie, UI jest zablokowane','Tylko po zakończeniu przejścia','Tylko z flushSync','Przejście nie blokuje reszty interfejsu.');
q('Co robi flushSync w kontekście harmonogramu?','Wymusza natychmiastowy, synchroniczny render i commit','Oznacza aktualizację jako niepilną','Grupuje aktualizacje','Opóźnia render','To przeciwieństwo przejścia.');
q('Dlaczego scrollIntoView bez flushSync trafia w przedostatni element?','DOM nie zawiera jeszcze nowego elementu w chwili wywołania','Bo scrollIntoView jest asynchroniczny','Bo lista jest odwrócona','Bo ref wskazuje pierwszy element','Render nastąpi po zakończeniu handlera.');
q('Ile renderów dadzą setA i setB w jednym handlerze?','Jeden','Dwa','Zero','Trzy','Grupowanie w handlerze.');
q('Ile renderów da flushSync(setA) i zwykłe setB w jednym handlerze?','Dwa','Jeden','Trzy','Zero','flushSync wymusza osobny render.');
q('Ile wyniesie n po setN(n+1) wywołanym dwa razy w jednym handlerze (start 0)?','1','2','0','Błąd','Oba wywołania używają tej samej migawki n.');
q('Ile wyniesie n po setN(x=>x+1) wywołanym dwa razy (start 0)?','2','1','0','Błąd','Funkcyjna aktualizacja dostaje poprzednią wartość z kolejki.');
q('Gdzie React kolejkuje aktualizacje stanu?','W kolejce aktualizacji hooka we fiberze','W DOM','W elemencie','W localStorage','Kolejka jest przetwarzana w następnym renderze.');
q('Czy startTransition dodaje sztuczne opóźnienie?','Nie, tylko obniża priorytet i pozwala przerwać render','Tak, 300 ms','Tak, do następnej klatki','Tak, sekundę','To nie jest debounce.');
q('Czym różni się transition od debounce?','Transition nie opóźnia startu, tylko pozwala przerwać render; debounce czeka określony czas','Niczym','Debounce jest przerywalny','Transition czeka 300 ms','Można je łączyć, ale rozwiązują różne problemy.');
q('Kiedy setTimeout nie pomoże przy ciężkim renderze?','Gdy render ruszy — jest nieprzerywalny i blokuje tak samo','Zawsze pomoże','Gdy timeout jest dłuższy niż 1 s','Gdy używasz TSX','setTimeout tylko przesuwa moment blokady.');
q('Co mierzysz w Profilerze przy porównaniu wersji z transition i bez?','Długość commitów i czas renderu przy pisaniu','Liczbę żądań HTTP','Rozmiar paczki','Kontrast','Profiler pokazuje, które rendery są drogie.');
q('Jak spowolnić CPU w DevTools, żeby odczuć problem?','Ustawić CPU throttling (np. ×4) w zakładce Performance','Wyłączyć cache','Włączyć tryb offline','Zmienić motyw','Słabsze urządzenia ujawniają zacinanie.');
q('Dlaczego cache obietnic jest potrzebny przy use() w zakładkach?','Nowa obietnica w każdym renderze zawieszałaby komponent w nieskończoność','Bo use() nie przyjmuje obietnic','Bo cache przyspiesza CSS','Bo Suspense tego wymaga składniowo','Obietnica musi być stabilna między renderami.');
q('Czy przejście może zostać porzucone?','Tak, gdy przyjdzie nowsza aktualizacja tego samego stanu lub pilniejsza praca','Nie','Tylko w StrictMode','Tylko w produkcji','Porzucony render nie zostawia śladu.');
q('Co widzi użytkownik, gdy przejście zostanie porzucone?','Nic niepokojącego — ekran pokazuje ostatni zatwierdzony stan','Pusty ekran','Błąd','Fallback','Stąd wymóg czystości renderu.');
q('Dlaczego w teście jeden asynchroniczny act może połączyć aktualizacje sprzed i po await w jeden render?','act kolejkuje pracę do końca swojego wywołania, więc trzeba go rozdzielić, by zobaczyć dwa rendery jak w przeglądarce','Bo batching działa inaczej w produkcji','Bo await nie działa w act','Bo jsdom nie ma mikrozadań','Liczenie renderów w testach wymaga świadomości granic act.');
q('Czy w środowisku testowym act kończy przejścia?','Tak, act dokańcza całą zaplanowaną pracę','Nie, przejścia są pomijane','Tylko z fake timers','Tylko w jsdom 20+','Dlatego przerwania trudno obserwować w testach; zawieszenie przez Suspense — łatwo.');
q('Dlaczego zawieszenie w act wymaga await?','React musi poczekać na rozwiązanie obietnic, by dokończyć render','Bo act jest zawsze asynchroniczny','Bo inaczej testy są wolne','Bo jsdom tego wymaga','React ostrzega, gdy act z zawieszeniem nie jest awaitowany.');
q('Jakie zjawisko eliminuje batching dla użytkownika?','Migające pośrednie stany UI między setterami','Błędy sieci','Brak kluczy','Wycieki pamięci','Użytkownik widzi tylko spójny wynik.');
q('Co jest lepszym rozwiązaniem niż liczenie na batching przy wielu powiązanych zmiennych?','Jedna zmienna stanu (unia) albo reducer','flushSync','Więcej efektów','Klasy','Model danych bez sprzecznych kombinacji.');
q('Czy zdarzenia ciągłe (scroll) mogą przerwać przejście?','Tak, mają wyższy priorytet','Nie','Tylko w Firefox','Tylko z flushSync','Każdy wyższy pas przerywa niższy.');
q('Co robi React z kilkoma aktualizacjami przejścia, gdy pierwsza jeszcze się renderuje?','Może je połączyć i renderować najnowszy stan','Renderuje każdą po kolei w całości','Odrzuca nowe','Rzuca błąd','Liczy się końcowy stan, nie każdy krok pośredni.');
q('Co jest wspólne dla useTransition i useDeferredValue?','Oba korzystają z niepilnego pasa przejścia','Oba blokują UI','Oba wymagają Suspense','Oba są przestarzałe','useDeferredValue opóźnia wartość zamiast aktualizacji (rozdział 16).');
q('Czy batching zmienia kolejność aktualizacji tego samego stanu?','Nie, aktualizacje są stosowane w kolejności wywołania','Tak, odwraca je','Tak, sortuje według priorytetu zawsze','Losowo','Kolejka zachowuje porządek.');
q('Co jest „taktem” w kontekście batchingu?','Synchroniczny fragment kodu przed oddaniem sterowania (np. do await)','Klatka animacji','Sekunda','Jeden render','Aktualizacje z jednego fragmentu trafiają do jednego renderu.');
q('Czy dwa kliknięcia użytkownika są grupowane w jeden render?','Nie, każde zdarzenie dyskretne jest renderowane osobno','Tak','Tylko szybkie','Tylko w transition','Użytkownik musi zobaczyć efekt każdego kliknięcia.');
q('Co robi aria-selected w zakładkach podczas czekającego przejścia?','Wskazuje starą zakładkę, dopóki przejście się nie zakończy','Od razu wskazuje nową','Znika','Wskazuje obie','Stan zakładki zmienia się w przejściu.');
q('Jak pokazać użytkownikowi, że przejście trwa?','Wskaźnikiem z isPending lub przyciemnieniem starej treści','Fallbackiem Suspense','alertem','Przeładowaniem strony','Stara treść zostaje czytelna.');
q('Czy flushSync można wywołać w renderze?','Nie, React ostrzega — flushSync jest dla handlerów i kodu spoza renderu','Tak','Tylko w useMemo','Tylko w StrictMode','W renderze nie można wymuszać commitu.');
q('Jaki problem rozwiązuje harmonogram Reacta w DevDesk?','Płynność pisania mimo dużych porcji danych i ciężkich renderów','Bezpieczeństwo API','Kolejność migracji bazy','Rozmiar paczki','Priorytety decydują, co renderować najpierw.');
q('Czy SSE/WebSocket aktualizujące tabelę może być przejściem?','Tak, gdy aktualizacja widoku nie jest pilna dla użytkownika','Nie, nigdy','Tylko w klasach','Tylko z flushSync','Pozwala nie przerywać pisania.');
q('Dlaczego licznik renderów w module jest tylko do obserwacji?','Mutacja zmiennej w renderze łamie czystość','Bo jest wolny','Bo TypeScript go zabrania','Bo React go resetuje','W testach przekazujemy funkcję onRender.');
q('Co testujesz, przekazując onRender przez props zamiast zmiennej modułowej?','Liczbę renderów bez współdzielenia stanu między testami','Szybkość renderu','Typy','DOM','Każdy test ma własny licznik.');
q('Co jest prawdą o kolejności: pilna aktualizacja a przejście w tym samym handlerze?','Pilna jest commitowana najpierw, przejście później','Przejście najpierw','Równocześnie zawsze','Losowo','Dlatego pole reaguje natychmiast.');
q('Czym jest akcja (Action) w React 19 w kontekście przejść?','Funkcją asynchroniczną w startTransition, której stan oczekiwania śledzi React','Akcją Reduxa','Zdarzeniem DOM','Efektem','Szczegóły w rozdziale 17.');
q('Czy startTransition przyjmuje funkcję asynchroniczną w React 19?','Tak, jako akcję','Nie','Tylko w klasach','Tylko na serwerze','isPending obejmuje cały czas trwania akcji.');
q('Co się stanie z aktualizacją stanu po await wewnątrz akcji w startTransition?','Trzeba ją ponownie opakować w startTransition, żeby była przejściem','Automatycznie jest przejściem','Jest pomijana','Rzuca błąd','Kontekst przejścia nie przechodzi przez await (ograniczenie JS).');
q('Która aktualizacja w DevDesk powinna zostać pilna?','Wartość pola wyszukiwania','Przefiltrowana tabela','Wykres statystyk','Lista 5000 ticketów','Bezpośredni efekt działania użytkownika.');
q('Co mierzy liczba commitów pola tekstowego w Profilerze?','Ile razy React zatwierdził zmiany przy wpisywaniu','Liczbę żądań','Rozmiar DOM','Liczbę efektów','Krótsze commity pola = płynniejsze pisanie.');
q('Czy transition pomaga, gdy pojedynczy komponent renderuje się 2 sekundy w jednym kawałku?','Tylko częściowo — React przerywa między komponentami, nie w środku jednej funkcji','Tak, całkowicie','Nie, nigdy','Tylko z flushSync','Długi render jednego komponentu trzeba też zoptymalizować.');
q('Gdzie React może oddać sterowanie w trakcie renderu?','Między jednostkami pracy (fiberami)','W środku funkcji komponentu','W środku pętli while użytkownika','Tylko po commicie','Funkcja komponentu wykonuje się w całości.');
const probes=[
  ['Rendery po dwóch setterach w jednym act','2','3','1','4','renders=0;let bump!:()=>void;function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;bump=()=>{setA(1);setB(1);};return <p>{a+b}</p>;}mount(<C/>);act(()=>bump());console.log(renders);'],
  ['Rendery po trzech setterach po await','2','4','3','1','renders=0;let load!:()=>Promise<void>;function C(){const [a,setA]=useState(0),[b,setB]=useState(0),[c,setC]=useState(0);renders+=1;load=async()=>{await sleep(1);setA(1);setB(1);setC(1);};return <p>{a+b+c}</p>;}mount(<C/>);await act(async()=>{await load();});console.log(renders);'],
  ['Rendery: przed await i po await','3','2','4','5','renders=0;let load!:()=>Promise<void>;function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;load=async()=>{setA(1);await sleep(1);setB(1);};return <p>{a+b}</p>;}mount(<C/>);let pending!:Promise<void>;act(()=>{pending=load();});await act(async()=>{await pending;});console.log(renders);'],
  ['Rendery w setTimeout z dwoma setterami','2','3','1','4','renders=0;let start!:()=>void;function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;start=()=>{setTimeout(()=>{setA(1);setB(1);},0);};return <p>{a+b}</p>;}mount(<C/>);await act(async()=>{start();await sleep(5);});console.log(renders);'],
  ['Rendery po natywnym kliknięciu z dwoma setterami','2','3','1','4','renders=0;function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;return <button type="button" onClick={()=>{setA(1);setB(1);}}>{a+b}</button>;}const v=mount(<C/>);act(()=>{v.host.querySelector("button")!.click();});console.log(renders);'],
  ['Rendery z flushSync i zwykłym setterem','3','2','4','1','renders=0;let bump!:()=>void;function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;bump=()=>{flushSync(()=>setA(1));setB(1);};return <p>{a+b}</p>;}mount(<C/>);act(()=>bump());console.log(renders);'],
  ['Wartość po setN(n+1) dwa razy','1','2','0','3','let bump!:()=>void;function C(){const [n,setN]=useState(0);bump=()=>{setN(n+1);setN(n+1);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>bump());console.log(v.host.textContent);'],
  ['Wartość po setN(x=>x+1) dwa razy','2','1','0','3','let bump!:()=>void;function C(){const [n,setN]=useState(0);bump=()=>{setN(x=>x+1);setN(x=>x+1);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>bump());console.log(v.host.textContent);'],
  ['Wartość po mieszanych aktualizacjach','6','5','1','2','let bump!:()=>void;function C(){const [n,setN]=useState(0);bump=()=>{setN(5);setN(x=>x+1);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>bump());console.log(v.host.textContent);'],
  ['Wartość po funkcji, a potem stałej','5','6','1','2','let bump!:()=>void;function C(){const [n,setN]=useState(0);bump=()=>{setN(x=>x+1);setN(5);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>bump());console.log(v.host.textContent);'],
  ['Wynik startTransition w act','b','a','pusty','undefined','let set!:(v:string)=>void;function C(){const [v,setV]=useState("a");set=setV;return <p>{v}</p>;}const view=mount(<C/>);act(()=>startTransition(()=>set("b")));console.log(view.host.textContent);'],
  ['isPending po zakończonym przejściu','false','true','undefined','null','let pending:boolean|null=null;let go!:()=>void;function C(){const [v,setV]=useState(0);const [isPending,start]=useTransition();pending=isPending;go=()=>start(()=>setV(1));return <p>{v}</p>;}mount(<C/>);act(()=>go());console.log(String(pending));'],
  ['Przejście z zawieszeniem: widoczna treść','stara','nowa','Ładowanie…','pusty','const never=new Promise<string>(()=>{});let go!:()=>void;function Content({tab}:{tab:string}){return tab==="nowa"?<p>{use(never)}</p>:<p>stara</p>;}function C(){const [tab,setTab]=useState("stara");go=()=>startTransition(()=>setTab("nowa"));return <Suspense fallback={<p>Ładowanie…</p>}><Content tab={tab}/></Suspense>;}const v=mount(<C/>);await act(async()=>{go();});console.log(v.host.textContent);'],
  ['Pilna zmiana z zawieszeniem: widoczna treść','Ładowanie…','stara','nowa','pusty','const never=new Promise<string>(()=>{});let go!:()=>void;function Content({tab}:{tab:string}){return tab==="nowa"?<p>{use(never)}</p>:<p>stara</p>;}function C(){const [tab,setTab]=useState("stara");go=()=>setTab("nowa");return <Suspense fallback={<p>Ładowanie…</p>}><Content tab={tab}/></Suspense>;}const v=mount(<C/>);await act(async()=>{go();});console.log([...v.host.querySelectorAll("p")].filter(p=>p.style.display!=="none").map(p=>p.textContent).join(""));'],
  ['Stara treść przy pilnym zawieszeniu','ukryta w DOM','usunięta z DOM','widoczna','zastąpiona tekstem nowej','const never=new Promise<string>(()=>{});let go!:()=>void;function Content({tab}:{tab:string}){return tab==="nowa"?<p>{use(never)}</p>:<p>stara</p>;}function C(){const [tab,setTab]=useState("stara");go=()=>setTab("nowa");return <Suspense fallback={<p>Ładowanie…</p>}><Content tab={tab}/></Suspense>;}const v=mount(<C/>);await act(async()=>{go();});const old=[...v.host.querySelectorAll("p")].find(p=>p.textContent==="stara");console.log(!old?"usunięta z DOM":old.style.display==="none"?"ukryta w DOM":"widoczna");'],
  ['isPending przy zawieszonym przejściu (z DOM)','true','false','undefined','null','const never=new Promise<string>(()=>{});let go!:()=>void;function Content({tab}:{tab:string}){return tab==="nowa"?<p>{use(never)}</p>:<p>stara</p>;}function C(){const [tab,setTab]=useState("stara");const [isPending,start]=useTransition();go=()=>start(()=>setTab("nowa"));return <div><i>{String(isPending)}</i><Suspense fallback={<p>…</p>}><Content tab={tab}/></Suspense></div>;}const v=mount(<C/>);await act(async()=>{go();});console.log(v.host.querySelector("i")?.textContent);'],
  ['isPending zapisany w zmiennej w renderze (render bez commitu)','false','true','undefined','null','const never=new Promise<string>(()=>{});let go!:()=>void;let pending=true;function Content({tab}:{tab:string}){return tab==="nowa"?<p>{use(never)}</p>:<p>stara</p>;}function C(){const [tab,setTab]=useState("stara");const [isPending,start]=useTransition();pending=isPending;go=()=>start(()=>setTab("nowa"));return <Suspense fallback={<p>…</p>}><Content tab={tab}/></Suspense>;}mount(<C/>);await act(async()=>{go();});console.log(String(pending));'],
  ['Pole podczas czekającego przejścia','abc','pusty','a','stara','const never=new Promise<string>(()=>{});let go!:()=>void;let type!:(v:string)=>void;function Content({tab}:{tab:string}){return tab==="nowa"?<p>{use(never)}</p>:<p>stara</p>;}function C(){const [tab,setTab]=useState("stara"),[text,setText]=useState("");type=setText;go=()=>startTransition(()=>setTab("nowa"));return <div><span>{text||"pusty"}</span><Suspense fallback={<p>…</p>}><Content tab={tab}/></Suspense></div>;}const v=mount(<C/>);await act(async()=>{go();});act(()=>type("abc"));console.log(v.host.querySelector("span")?.textContent);'],
  ['Rendery po jednym setterze z tą samą wartością w batchu z inną zmianą','2','3','1','4','renders=0;let bump!:()=>void;function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;bump=()=>{setA(0);setB(1);};return <p>{a+b}</p>;}mount(<C/>);act(()=>bump());console.log(renders);'],
  ['DOM w handlerze po zwykłym setterze','0','1','pusty','błąd','let seen="";let bump!:()=>void;function C(){const [n,setN]=useState(0);bump=()=>{setN(1);seen=document.querySelector("#licznik")?.textContent??"";};return <p id="licznik">{n}</p>;}mount(<C/>);act(()=>bump());console.log(seen);'],
  ['DOM w handlerze po flushSync','1','0','pusty','błąd','let seen="";let bump!:()=>void;function C(){const [n,setN]=useState(0);bump=()=>{flushSync(()=>setN(1));seen=document.querySelector("#licznik2")?.textContent??"";};return <p id="licznik2">{n}</p>;}mount(<C/>);act(()=>bump());console.log(seen);'],
  ['Dwa przejścia tego samego stanu w jednym act','c','b','a','b,c','let set!:(v:string)=>void;function C(){const [v,setV]=useState("a");set=setV;return <p>{v}</p>;}const view=mount(<C/>);act(()=>{startTransition(()=>set("b"));startTransition(()=>set("c"));});console.log(view.host.textContent);'],
  ['Rendery po dwóch przejściach w jednym act','2','3','1','4','renders=0;let set!:(v:string)=>void;function C(){const [v,setV]=useState("a");set=setV;renders+=1;return <p>{v}</p>;}mount(<C/>);act(()=>{startTransition(()=>set("b"));startTransition(()=>set("c"));});console.log(renders);'],
  ['Pilna i niepilna zmiana w jednym act: wynik','x:b','x:a','pusty:b','pusty:a','let go!:()=>void;function C(){const [text,setText]=useState("pusty"),[v,setV]=useState("a");go=()=>{setText("x");startTransition(()=>setV("b"));};return <p>{text}:{v}</p>;}const view=mount(<C/>);act(()=>go());console.log(view.host.textContent);'],
  ['Unia stanu: tekst po sukcesie','A,B','Ładowanie…','idle','błąd','type S={kind:"idle"}|{kind:"loading"}|{kind:"success";data:string[]};let load!:()=>Promise<void>;function C(){const [s,setS]=useState<S>({kind:"idle"});load=async()=>{setS({kind:"loading"});await sleep(1);setS({kind:"success",data:["A","B"]});};return <p>{s.kind==="success"?s.data.join(","):s.kind==="loading"?"Ładowanie…":"idle"}</p>;}const v=mount(<C/>);await act(async()=>{await load();});console.log(v.host.textContent);'],
  ['Unia stanu: rendery całego ładowania','3','2','4','5','renders=0;type S={kind:"idle"}|{kind:"loading"}|{kind:"success"};let load!:()=>Promise<void>;function C(){const [s,setS]=useState<S>({kind:"idle"});renders+=1;load=async()=>{setS({kind:"loading"});await sleep(1);setS({kind:"success"});};return <p>{s.kind}</p>;}mount(<C/>);let pending!:Promise<void>;act(()=>{pending=load();});await act(async()=>{await pending;});console.log(renders);'],
  ['Kolejność stosowania aktualizacji','abc','cba','c','a','let go!:()=>void;function C(){const [v,setV]=useState("");go=()=>{setV(x=>x+"a");setV(x=>x+"b");setV(x=>x+"c");};return <p>{v}</p>;}const view=mount(<C/>);act(()=>go());console.log(view.host.textContent);'],
  ['Rendery przy aktualizacji w Promise.then','2','3','1','4','renders=0;let go!:()=>Promise<void>;function C(){const [a,setA]=useState(0),[b,setB]=useState(0);renders+=1;go=()=>Promise.resolve().then(()=>{setA(1);setB(1);});return <p>{a+b}</p>;}mount(<C/>);await act(async()=>{await go();});console.log(renders);'],
  ['Rendery przy dwóch osobnych act','3','2','1','4','renders=0;let a!:()=>void,b!:()=>void;function C(){const [x,setX]=useState(0),[y,setY]=useState(0);renders+=1;a=()=>setX(1);b=()=>setY(1);return <p>{x+y}</p>;}mount(<C/>);act(()=>a());act(()=>b());console.log(renders);'],
  ['Fallback przy pierwszym montowaniu z zawieszeniem w przejściu','…','pusty','treść','błąd','const never=new Promise<string>(()=>{});function Content(){return <p>{use(never)}</p>;}const host=document.createElement("div");const root=createRoot(host);await act(async()=>{startTransition(()=>root.render(<Suspense fallback={<p>…</p>}><Content/></Suspense>));});console.log(host.textContent||"pusty");'],
  ['Liczba renderów przy setterze z tą samą wartością w nowym act','1','2','0','3','renders=0;let same!:()=>void;function C(){const [v,setV]=useState(5);renders+=1;same=()=>setV(5);return <p>{v}</p>;}mount(<C/>);act(()=>same());console.log(renders);'],
  ['Wartość po trzech funkcyjnych aktualizacjach i flushSync na końcu','3','1','2','0','let go!:()=>void;function C(){const [n,setN]=useState(0);go=()=>{setN(x=>x+1);setN(x=>x+1);flushSync(()=>setN(x=>x+1));};return <p>{n}</p>;}const v=mount(<C/>);act(()=>go());console.log(v.host.textContent);'],
  ['Rendery przy trzech funkcyjnych aktualizacjach z flushSync na końcu','2','3','4','1','renders=0;let go!:()=>void;function C(){const [n,setN]=useState(0);renders+=1;go=()=>{setN(x=>x+1);setN(x=>x+1);flushSync(()=>setN(x=>x+1));};return <p>{n}</p>;}mount(<C/>);act(()=>go());console.log(renders);'],
  ['Tekst po przejściu w setTimeout','b','a','pusty','undefined','let go!:()=>void;function C(){const [v,setV]=useState("a");go=()=>{setTimeout(()=>startTransition(()=>setV("b")),0);};return <p>{v}</p>;}const view=mount(<C/>);await act(async()=>{go();await sleep(5);});console.log(view.host.textContent);'],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 w Vitest (createRoot + act w jsdom, bez StrictMode).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/BatchingDemo.tsx':batching,'src/UnionDemo.tsx':union,'src/FilteredList.tsx':split,'src/TicketTabs.tsx':tabs,'src/TagAdder.tsx':tagAdder,'src/batching.test.tsx':batchTests,'src/tabs.test.tsx':tabTests};
const mutants=[
  {file:'src/BatchingDemo.tsx',before:'    setItems(["A","B","C"]);\n    setLoading(false);',after:'    (await import("react-dom")).flushSync(()=>setItems(["A","B","C"]));\n    setLoading(false);'},
  {file:'src/TicketTabs.tsx',before:'function select(next:string){startTransition(()=>setTab(next));}',after:'function select(next:string){setTab(next);}'},
  {file:'src/TagAdder.tsx',before:'flushSync(()=>setTags(prev=>[...prev,tag]));',after:'setTags(prev=>[...prev,tag]);'},
  {file:'src/FilteredList.tsx',before:'startTransition(()=>setFilter(value));',after:'startTransition(()=>setFilter(value.slice(0,1)));'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
