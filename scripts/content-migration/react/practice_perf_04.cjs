// Lekcja 16/04: trening memoizacji. Rozwiązania 12 zadań sprawdza React 19 w Vitest (jsdom);
// pliki *.compiled.tsx przechodzą przez prawdziwy React Compiler (zadanie 11).
const config=require('./practice_perf_03.cjs').project['vitest.config.ts'];
const products=String.raw`import {memo,Profiler,useCallback,useState,type ProfilerOnRenderCallback} from "react";
export type Product={id:number;name:string};
export function makeProducts(count:number):Product[]{return Array.from({length:count},(_,i)=>({id:i+1,name:"Produkt "+(i+1)}));}
type RowProps={product:Product;onAdd:(id:number)=>void;onRowCommit?:ProfilerOnRenderCallback};
const noop:ProfilerOnRenderCallback=()=>{};
// Profiler w wierszu zgłasza commit tylko wtedy, gdy wiersz faktycznie się renderował.
const ProductRow=memo(function ProductRow({product,onAdd,onRowCommit=noop}:RowProps){
  return <Profiler id={"wiersz-"+product.id} onRender={onRowCommit}>
    <li>{product.name} <button type="button" onClick={()=>onAdd(product.id)}>Do koszyka</button></li>
  </Profiler>;
});
export function ProductList({products,onRowCommit}:{products:readonly Product[];onRowCommit?:ProfilerOnRenderCallback}){
  const [query,setQuery]=useState("");
  const [cart,setCart]=useState<number[]>([]);
  // forma funkcyjna nie czyta cart, setter jest stabilny → pusta tablica zależności
  const onAdd=useCallback((id:number)=>setCart(c=>[...c,id]),[]);
  const visible=products.filter(p=>p.name.toLowerCase().includes(query.toLowerCase()));
  return <div>
    <input aria-label="Szukaj" value={query} onChange={e=>setQuery(e.target.value)}/>
    <p>W koszyku: {cart.length}</p>
    <ul>{visible.map(p=><ProductRow key={p.id} product={p} onAdd={onAdd} onRowCommit={onRowCommit}/>)}</ul>
  </div>;
}`;
const productsBare=String.raw`import {Profiler,useState,type ProfilerOnRenderCallback} from "react";
import type {Product} from "./ProductList";
type RowProps={product:Product;onAdd:(id:number)=>void;onRowCommit?:ProfilerOnRenderCallback};
const noop:ProfilerOnRenderCallback=()=>{};
// Zadanie 11: ten sam kod bez memo i useCallback — memoizację dodaje kompilator.
function ProductRow({product,onAdd,onRowCommit=noop}:RowProps){
  return <Profiler id={"wiersz-"+product.id} onRender={onRowCommit}>
    <li>{product.name} <button type="button" onClick={()=>onAdd(product.id)}>Do koszyka</button></li>
  </Profiler>;
}
export function ProductList({products,onRowCommit}:{products:readonly Product[];onRowCommit?:ProfilerOnRenderCallback}){
  const [query,setQuery]=useState("");
  const [cart,setCart]=useState<number[]>([]);
  const onAdd=(id:number)=>setCart(c=>[...c,id]);
  const visible=products.filter(p=>p.name.toLowerCase().includes(query.toLowerCase()));
  return <div>
    <input aria-label="Szukaj" value={query} onChange={e=>setQuery(e.target.value)}/>
    <p>W koszyku: {cart.length}</p>
    <ul>{visible.map(p=><ProductRow key={p.id} product={p} onAdd={onAdd} onRowCommit={onRowCommit}/>)}</ul>
  </div>;
}`;
const board=String.raw`import {useMemo,useState} from "react";
export type Ticket={id:number;title:string;priority:number};
export type SortKey="id"|"priority";
export function filterAndSort(tickets:readonly Ticket[],query:string,sortKey:SortKey):Ticket[]{
  const q=query.toLowerCase();
  return tickets.filter(t=>t.title.toLowerCase().includes(q)).toSorted((a,b)=>a[sortKey]-b[sortKey]);
}
// Zadanie 4: kosztowne filtrowanie zapamiętane — przełącznik motywu nie liczy go od nowa.
export function TicketBoard({tickets,compute=filterAndSort}:{tickets:readonly Ticket[];compute?:typeof filterAndSort}){
  const [query,setQuery]=useState("");
  const [sortKey,setSortKey]=useState<SortKey>("id");
  const [dark,setDark]=useState(false);
  const visible=useMemo(()=>compute(tickets,query,sortKey),[tickets,query,sortKey,compute]);
  return <section className={dark?"dark":"light"}>
    <input aria-label="Filtr" value={query} onChange={e=>setQuery(e.target.value)}/>
    <button type="button" onClick={()=>setDark(d=>!d)}>Motyw</button>
    <button type="button" onClick={()=>setSortKey(k=>k==="id"?"priority":"id")}>Sortuj</button>
    <ol>{visible.map(t=><li key={t.id}>{t.title}</li>)}</ol>
  </section>;
}`;
const live=String.raw`import {useEffect} from "react";
export type Filters={status:string;assignee:string};
// Zadanie 5 (b): zależności od pól prostych — wywołujący może przekazać literał obiektu.
export function useLiveTickets(filters:Filters,connect:(filters:Filters)=>()=>void){
  const {status,assignee}=filters;
  useEffect(()=>connect({status,assignee}),[status,assignee,connect]);
}`;
const note=String.raw`import {memo,useCallback,useState} from "react";
// Zadanie 6: prawdziwe zależności. SaveButton i tak renderuje się przy każdym znaku — memo nic tu nie daje.
export const SaveButton=memo(function SaveButton({onClick,onRender}:{onClick:()=>void;onRender?:()=>void}){
  onRender?.();
  return <button type="button" onClick={onClick}>Zapisz</button>;
});
export function NoteEditor({onSave,onButtonRender}:{onSave:(text:string)=>void;onButtonRender?:()=>void}){
  const [text,setText]=useState("");
  const handleSave=useCallback(()=>onSave(text),[text,onSave]);
  return <div>
    <textarea aria-label="Notatka" value={text} onChange={e=>setText(e.target.value)}/>
    <SaveButton onClick={handleSave} onRender={onButtonRender}/>
  </div>;
}`;
const dashboard=String.raw`import {useState,type ReactNode} from "react";
// Zadanie 8: zero memoizacji — stan w dół i treść jako children.
export function ExpensiveChart({onRender}:{onRender:()=>void}){
  onRender();
  return <svg role="img" aria-label="Wykres"/>;
}
function SearchPanel(){
  const [query,setQuery]=useState("");
  return <input aria-label="Szukaj" value={query} onChange={e=>setQuery(e.target.value)}/>;
}
export function ScrollTracker({children}:{children:ReactNode}){
  const [y,setY]=useState(0);
  return <div data-testid="przewijanie" onScroll={e=>setY(e.currentTarget.scrollTop)}>
    <p>Pozycja: {y}</p>
    {children}
  </div>;
}
export function Dashboard({onChartRender}:{onChartRender:()=>void}){
  return <main>
    <SearchPanel/>
    <ScrollTracker><ExpensiveChart onRender={onChartRender}/></ScrollTracker>
  </main>;
}`;
const spark=String.raw`import {memo} from "react";
export type SparklineProps={points:readonly number[];color:string;onHover?:(index:number)=>void;onRender?:()=>void};
// Zadanie 9: porównanie po wartościach — ale WSZYSTKIE props, także funkcje.
export function arePropsEqual(a:SparklineProps,b:SparklineProps):boolean{
  return a.color===b.color&&a.onHover===b.onHover&&a.onRender===b.onRender&&
    a.points.length===b.points.length&&a.points.every((p,i)=>Object.is(p,b.points[i]));
}
export const Sparkline=memo(function Sparkline({points,color,onHover,onRender}:SparklineProps){
  onRender?.();
  const max=Math.max(1,...points);
  return <svg role="img" aria-label="Sparkline" data-points={points.length}>
    <polyline stroke={color} fill="none" points={points.map((p,i)=>i*4+","+(20-20*p/max)).join(" ")} onMouseEnter={()=>onHover?.(points.length-1)}/>
  </svg>;
},arePropsEqual);`;
const provider=String.raw`import {createContext,memo,use,useCallback,useEffect,useMemo,useState,type ReactNode} from "react";
import type {Ticket} from "./TicketBoard";
type TicketsApi={tickets:readonly Ticket[];selectedId:number|null;select:(id:number)=>void;remove:(id:number)=>void};
const TicketsContext=createContext<TicketsApi|null>(null);
// Zadanie 10: stabilne funkcje i stabilna wartość — lastSync nie renderuje konsumentów.
export function TicketsProvider({initial,children}:{initial:readonly Ticket[];children:ReactNode}){
  const [tickets,setTickets]=useState(initial);
  const [selectedId,setSelectedId]=useState<number|null>(null);
  const [lastSync,setLastSync]=useState(0);
  const select=useCallback((id:number)=>setSelectedId(id),[]);
  const remove=useCallback((id:number)=>setTickets(ts=>ts.filter(t=>t.id!==id)),[]);
  const value=useMemo(()=>({tickets,selectedId,select,remove}),[tickets,selectedId,select,remove]);
  useEffect(()=>{const timer=setInterval(()=>setLastSync(s=>s+1),10000);return ()=>clearInterval(timer);},[]);
  return <TicketsContext value={value}><p>Synchronizacje: {lastSync}</p>{children}</TicketsContext>;
}
export function useTickets(){const api=use(TicketsContext);if(!api)throw new Error("useTickets poza TicketsProvider");return api;}
export const TicketCount=memo(function TicketCount({onRender}:{onRender?:()=>void}){
  const {tickets,remove}=useTickets();
  onRender?.();
  return <div><p>Ticketów: {tickets.length}</p><button type="button" onClick={()=>remove(tickets[0]?.id??-1)}>Usuń pierwszy</button></div>;
});`;
const stats=String.raw`import {Profiler,useCallback,useRef,type ProfilerOnRenderCallback,type ReactNode} from "react";
export type Commit={id:string;actualDuration:number};
// Zadanie 12: zbiorcze liczby zamiast przeczucia.
export function summarizeCommits(records:readonly Commit[]){
  const total=records.reduce((sum,r)=>sum+r.actualDuration,0);
  return {commits:records.length,total,average:records.length?total/records.length:0};
}
export function MeasuredList({id,onSummary,children}:{id:string;onSummary:(summary:ReturnType<typeof summarizeCommits>)=>void;children:ReactNode}){
  const records=useRef<Commit[]>([]);
  const onRender=useCallback<ProfilerOnRenderCallback>((profilerId,_phase,actualDuration)=>{records.current.push({id:profilerId,actualDuration});},[]);
  return <div>
    <Profiler id={id} onRender={onRender}>{children}</Profiler>
    <button type="button" onClick={()=>onSummary(summarizeCommits(records.current))}>Pokaż statystyki</button>
  </div>;
}`;
const tests=String.raw`import {act,fireEvent,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {afterEach,expect,test,vi,type Mock} from "vitest";
import {Dashboard} from "./Dashboard";
import {useLiveTickets,type Filters} from "./useLiveTickets";
import {NoteEditor} from "./NoteEditor";
import {makeProducts,ProductList} from "./ProductList";
import {ProductList as CompiledList} from "./ProductList.compiled";
import {ProductList as PlainList} from "./ProductList.plain";
import {Sparkline,arePropsEqual} from "./Sparkline";
import {TicketBoard,filterAndSort,type Ticket} from "./TicketBoard";
import {TicketCount,TicketsProvider} from "./TicketsProvider";
import {MeasuredList,summarizeCommits} from "./MeasuredList";
import {useState} from "react";
afterEach(()=>vi.useRealTimers());
const products=makeProducts(12);
function rowCommits(){const ids:string[]=[];return {ids,onRowCommit:(id:string,phase:string)=>{if(phase!=="mount")ids.push(id);}};}
test.each([["ręczne memo + useCallback",ProductList,0],["React Compiler",CompiledList,0],["bez optymalizacji",PlainList,12]] as const)("%s: dodanie do koszyka — liczba renderów wierszy",async(_name,List,expected)=>{
  const {ids,onRowCommit}=rowCommits();
  const user=userEvent.setup();
  render(<List products={products} onRowCommit={onRowCommit}/>);
  await user.click(screen.getAllByRole("button",{name:"Do koszyka"})[2]);
  expect(screen.getByText("W koszyku: 1")).toBeInTheDocument();
  expect(ids).toHaveLength(expected);
});
test("ręczne memo: wpisywanie nie renderuje wierszy, które zostają",async()=>{
  const {ids,onRowCommit}=rowCommits();
  const user=userEvent.setup();
  render(<ProductList products={products} onRowCommit={onRowCommit}/>);
  await user.type(screen.getByLabelText("Szukaj"),"1");
  expect(screen.getAllByRole("listitem")).toHaveLength(4);
  expect(ids).toEqual([]);
});
const tickets:Ticket[]=[{id:1,title:"API timeout",priority:3},{id:2,title:"Logowanie SSO",priority:1},{id:3,title:"API limit",priority:2}];
test("filterAndSort filtruje i sortuje bez mutacji wejścia",()=>{
  expect(filterAndSort(tickets,"api","priority").map(t=>t.id)).toEqual([3,1]);
  expect(tickets.map(t=>t.id)).toEqual([1,2,3]);
});
test("useMemo: motyw nie liczy filtra od nowa, zmiana sortowania — tak",async()=>{
  const compute=vi.fn(filterAndSort);
  const user=userEvent.setup();
  render(<TicketBoard tickets={tickets} compute={compute}/>);
  await user.click(screen.getByRole("button",{name:"Motyw"}));
  await user.click(screen.getByRole("button",{name:"Motyw"}));
  expect(compute).toHaveBeenCalledTimes(1);
  await user.click(screen.getByRole("button",{name:"Sortuj"}));
  expect(compute).toHaveBeenCalledTimes(2);
  expect(screen.getAllByRole("listitem").map(li=>li.textContent)).toEqual(["Logowanie SSO","API limit","API timeout"]);
});
test("useLiveTickets: literał obiektu nie restartuje połączenia",async()=>{
  const log:string[]=[];
  const connect=(f:Filters)=>{log.push("subscribe "+f.status);return ()=>{log.push("unsubscribe "+f.status);};};
  function Live(){const [n,setN]=useState(0);const [status,setStatus]=useState("OPEN");useLiveTickets({status,assignee:"Ala"},connect);
    return <div><button type="button" onClick={()=>setN(n+1)}>Licznik {n}</button><button type="button" onClick={()=>setStatus("CLOSED")}>Zamknięte</button></div>;}
  const user=userEvent.setup();
  render(<Live/>);
  await user.click(screen.getByRole("button",{name:"Licznik 0"}));
  expect(log).toEqual(["subscribe OPEN"]);
  await user.click(screen.getByRole("button",{name:"Zamknięte"}));
  expect(log).toEqual(["subscribe OPEN","unsubscribe OPEN","subscribe CLOSED"]);
});
test("NoteEditor zapisuje aktualny tekst, a SaveButton renderuje się przy każdym znaku",()=>{
  const onSave=vi.fn(),buttonRender=vi.fn();
  render(<NoteEditor onSave={onSave} onButtonRender={buttonRender}/>);
  fireEvent.change(screen.getByLabelText("Notatka"),{target:{value:"a"}});
  fireEvent.change(screen.getByLabelText("Notatka"),{target:{value:"ab"}});
  fireEvent.click(screen.getByRole("button",{name:"Zapisz"}));
  expect(onSave).toHaveBeenCalledWith("ab");
  expect(buttonRender).toHaveBeenCalledTimes(3);
});
test("Dashboard: pisanie i przewijanie nie renderują wykresu",async()=>{
  const chart=vi.fn();
  const user=userEvent.setup();
  render(<Dashboard onChartRender={chart}/>);
  await user.type(screen.getByLabelText("Szukaj"),"abc");
  const scroller=screen.getByTestId("przewijanie");
  scroller.scrollTop=120;
  fireEvent.scroll(scroller);
  expect(screen.getByText("Pozycja: 120")).toBeInTheDocument();
  expect(chart).toHaveBeenCalledTimes(1);
});
test("arePropsEqual porównuje punkty po wartościach i wszystkie pozostałe props",()=>{
  const hover=()=>{};
  expect(arePropsEqual({points:[1,2],color:"red"},{points:[1,2],color:"red"})).toBe(true);
  expect(arePropsEqual({points:[1,2],color:"red"},{points:[1,2,3],color:"red"})).toBe(false);
  expect(arePropsEqual({points:[1,2],color:"red",onHover:hover},{points:[1,2],color:"red",onHover:()=>{}})).toBe(false);
});
test("Sparkline: nowa tablica z tymi samymi wartościami nie renderuje wykresu",()=>{
  const renders=vi.fn();
  const view=render(<Sparkline points={[1,2,3]} color="teal" onRender={renders}/>);
  view.rerender(<Sparkline points={[1,2,3]} color="teal" onRender={renders}/>);
  view.rerender(<Sparkline points={[1,2,3]} color="teal" onRender={renders}/>);
  expect(renders).toHaveBeenCalledTimes(1);
  view.rerender(<Sparkline points={[1,2,3,4]} color="teal" onRender={renders}/>);
  expect(renders).toHaveBeenCalledTimes(2);
  expect(screen.getByRole("img",{name:"Sparkline"})).toHaveAttribute("data-points","4");
});
test("TicketsProvider: lastSync nie renderuje konsumenta, usunięcie — tak",()=>{
  vi.useFakeTimers();
  const renders:Mock<()=>void>=vi.fn();
  render(<TicketsProvider initial={tickets}><TicketCount onRender={renders}/></TicketsProvider>);
  act(()=>{vi.advanceTimersByTime(20000);});
  expect(screen.getByText("Synchronizacje: 2")).toBeInTheDocument();
  expect(renders).toHaveBeenCalledTimes(1);
  fireEvent.click(screen.getByRole("button",{name:"Usuń pierwszy"}));
  expect(screen.getByText("Ticketów: 2")).toBeInTheDocument();
  expect(renders).toHaveBeenCalledTimes(2);
});
test("summarizeCommits i MeasuredList liczą commity listy",async()=>{
  expect(summarizeCommits([{id:"a",actualDuration:2},{id:"a",actualDuration:4}])).toEqual({commits:2,total:6,average:3});
  expect(summarizeCommits([])).toEqual({commits:0,total:0,average:0});
  const onSummary=vi.fn();
  const user=userEvent.setup();
  render(<MeasuredList id="products" onSummary={onSummary}><ProductList products={products}/></MeasuredList>);
  for(let i=0;i<3;i++)await user.click(screen.getAllByRole("button",{name:"Do koszyka"})[0]);
  await user.click(screen.getByRole("button",{name:"Pokaż statystyki"}));
  expect(onSummary.mock.calls[0][0].commits).toBe(4);
});`;
const header=String.raw`import {act,createContext,memo,use,useCallback,useEffect,useState,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
const log:string[]=[];
function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);act(()=>root.render(ui));return {host,update:(next:ReactNode)=>act(()=>root.render(next))};}
let bump:()=>void=()=>{};
function Parent({children}:{children:(n:number)=>ReactNode}){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <section data-n={n}>{children(n)}</section>;}
const TAGS=["a","b"];
void createContext;void memo;void use;void useCallback;void useEffect;void log;void TAGS;
`;
const card=String.raw`let renders=0;const Card=memo(function Card(_props:{title:string;count?:number;style?:object;onOpen?:()=>void;tags?:string[];children?:ReactNode}){renders+=1;return null;});`;
const exercises=[
  ['Zadanie 1: zapisz, które komponenty renderują się po kliknięciu „+1” i dlaczego.','Render rodzica renderuje wszystkie elementy z jego JSX.',String.raw`// Po kliknięciu: App, Display, Header, Sidebar, Menu — wszystkie pięć.
// <Menu /> jest tworzony w JSX App, więc render App tworzy nowy element i Menu się renderuje.
// DOM zmieni się tylko w Display — render jest tani, problem dopiero przy kosztownych lub licznych komponentach.
export {};`],
  ['Zadanie 2: owiń ProductRow w memo i ustabilizuj onAdd przez useCallback.','Forma funkcyjna settera nie czyta cart.',products],
  ['Zadanie 2: zmierz Profilerem w wierszu, które wiersze renderują się po dodaniu do koszyka.','Profiler w wierszu zgłasza commit tylko po jego renderze.',tests],
  ['Zadanie 2: sprawdź, że wpisywanie w wyszukiwarkę nie renderuje wierszy, które zostają.','Obiekty produktów mają te same referencje.',tests],
  ['Zadanie 3: zapisz, w których wariantach A–G memo-komponent Card się nie wyrenderuje.','Wartości proste i stabilne referencje.',String.raw`// Nie wyrenderuje się: A (wartości proste), E (stała modułu), G (useCallback z []).
// Wyrenderuje się: B (nowy obiekt style), C (nowa strzałka), D (children to nowy element), F (nowa tablica).
export {};`],
  ['Zadanie 4: napisz filterAndSort bez mutacji wejścia.','toSorted zamiast sort.',board],
  ['Zadanie 4: zapamiętaj wynik filtrowania przez useMemo.','Zależności: tickets, query, sortKey.',board],
  ['Zadanie 4: sprawdź testem, że motyw nie liczy filtra od nowa.','vi.fn(filterAndSort) wstrzyknięty przez props.',tests],
  ['Zadanie 5 (a): ustabilizuj obiekt filtrów w komponencie przez useMemo.','Rozwiązanie po stronie wywołującego.',String.raw`import {useMemo,useState} from "react";
import {useLiveTickets} from "./useLiveTickets";
export function LiveA({connect}:{connect:(f:{status:string;assignee:string})=>()=>void}){
  const [status]=useState("OPEN");
  const filters=useMemo(()=>({status,assignee:"Ala"}),[status]); // każdy wywołujący musi pamiętać
  useLiveTickets(filters,connect);
  return <p>Na żywo: {status}</p>;
}`],
  ['Zadanie 5 (b): zmień hook tak, żeby zależał od pól prostych.','Destrukturyzacja w hooku.',live],
  ['Zadanie 5: sprawdź testem, że niezwiązany licznik nie restartuje połączenia.','Literał obiektu przy każdym renderze.',tests],
  ['Zadanie 6: napraw nieaktualne domknięcie w handleSave.','Zależności: text i onSave.',note],
  ['Zadanie 6: sprawdź, że zapisany jest aktualny tekst i ile razy renderuje się SaveButton.','Przy każdym znaku nowa funkcja.',tests],
  ['Zadanie 6: zdecyduj, czy memoizacja przycisku ma sens.','Zależność zmienia się przy każdym renderze.',String.raw`// Nie ma sensu: handleSave zależy od text, który zmienia się przy każdym znaku,
// więc SaveButton i tak renderuje się przy każdym znaku — memo i useCallback tylko dokładają porównań.
// Memoizacja opłaca się, gdy zależności zmieniają się rzadziej niż renderuje się komponent.
export {};`],
  ['Zadanie 7: zapisz, w których sytuacjach A–D Badge w memo się wyrenderuje.','memo chroni tylko przed renderem rodzica.',String.raw`// A (render rodzica, te same props) — nie; B (własny stan hover) — tak;
// C (zmiana czytanego ThemeContext) — tak; D (inny kontekst, którego nie czyta) — nie.
export {};`],
  ['Zadanie 8: przenieś stan wyszukiwania w dół do SearchPanel.','Dashboard przestaje się renderować przy pisaniu.',dashboard],
  ['Zadanie 8: przekaż wykres jako children do ScrollTracker.','Element tworzy Dashboard, nie opakowanie.',dashboard],
  ['Zadanie 8: sprawdź testem, że pisanie i przewijanie nie renderują wykresu.','Ustaw scrollTop i wyślij zdarzenie scroll.',tests],
  ['Zadanie 9: napisz arePropsEqual porównujące punkty po wartościach.','Długość i każdy element przez Object.is.',spark],
  ['Zadanie 9: uwzględnij w porównaniu wszystkie props, także funkcje.','Pominięty onHover = nieaktualne domknięcie.',spark],
  ['Zadanie 9: sprawdź testem, że nowa tablica z tymi samymi wartościami nie renderuje wykresu.','rerender z [...points].',tests],
  ['Zadanie 9: zasymuluj odświeżanie co sekundę i nowy punkt co 5 sekund.','setInterval w efekcie.',String.raw`import {useEffect,useState} from "react";
import {Sparkline} from "./Sparkline";
export function LiveSparkline(){
  const [points,setPoints]=useState<number[]>([1,3,2]);
  useEffect(()=>{
    let tick=0;
    const timer=setInterval(()=>{
      tick+=1;
      setPoints(p=>tick%5===0?[...p,(p.at(-1)??0)+1]:[...p]); // co sekundę nowa tablica, co 5 s nowy punkt
    },1000);
    return ()=>clearInterval(timer);
  },[]);
  return <Sparkline points={points} color="teal"/>;
}`],
  ['Zadanie 10: ustabilizuj select i remove przez useCallback.','Setter w formie funkcyjnej.',provider],
  ['Zadanie 10: ustabilizuj wartość kontekstu przez useMemo.','Zależności: tickets, selectedId, select, remove.',provider],
  ['Zadanie 10: sprawdź na sztucznych zegarach, że lastSync nie renderuje konsumenta.','vi.useFakeTimers i advanceTimersByTime.',tests],
  ['Zadanie 11: usuń ręczną memoizację z ProductList i zostaw ją kompilatorowi.','Ten sam kod bez memo i useCallback.',productsBare],
  ['Zadanie 11: porównaj liczbę renderów wierszy: ręczne memo, kompilator i brak optymalizacji.','test.each na trzech wariantach.',tests],
  ['Zadanie 11: skonfiguruj Vitest, żeby pliki *.compiled.tsx przechodziły przez React Compiler.','Wtyczka Vite z transformAsync.',config],
  ['Zadanie 12: zbierz commity listy komponentem Profiler i podsumuj je.','Rekordy w refie, podsumowanie po kliknięciu.',stats],
  ['Zadanie 12: zapisz wnioski z pomiaru przed i po.','Próg ~16 ms na klatkę.',String.raw`// Scenariusz: 10 kliknięć „Do koszyka” + 5 znaków w wyszukiwarce, ten sam build.
// Bez memoizacji: każdy commit renderuje 500 wierszy; z memo + useCallback: kliknięcie renderuje tylko rodzica.
// Wniosek zapisz liczbami (commity, suma i średnia actualDuration) i porównaj z progiem 16 ms.
// Liczby z dev są zawyżone — dobre do porównań, nie jako wartości bezwzględne.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Z jakich trzech powodów komponent się renderuje?','Zmiana stanu, zmiana czytanego kontekstu, render rodzica','Zmiana props, CSS i URL','Tylko zmiana props','Tylko setState','memo przerywa tylko trzeci powód.');
q('Przed którym powodem renderu chroni memo?','Przed renderem rodzica przy identycznych props','Przed zmianą stanu','Przed zmianą kontekstu','Przed wszystkimi','I tylko przy Object.is dla każdego propsa.');
q('Ile komponentów z zadania 1 renderuje się po kliknięciu „+1”?','Wszystkie pięć','Tylko App i Display','Tylko Display','App, Display i Header','Menu też — <Menu /> powstaje w JSX App.');
q('Dlaczego Header z tym samym title się renderuje?','Render rodzica renderuje wszystkie dzieci bez memo','Bo title się zmienił','Bo Header ma stan','Nie renderuje się','To nie błąd — render jest tani.');
q('Dlaczego Menu w zadaniu 1 się renderuje, choć jest dzieckiem Sidebar?','Element <Menu /> tworzy App, który się renderuje','Bo Sidebar ma stan','Bo Menu czyta kontekst','Nie renderuje się','Inaczej niż w zadaniu 8.');
q('Gdzie zmieni się DOM w zadaniu 1?','Tylko w Display','We wszystkich komponentach','W Header i Display','Nigdzie','Commit zmienia tylko różnice.');
q('Dlaczego memo na ProductRow nie pomogło przed naprawą?','onAdd było nową strzałką przy każdym renderze','Bo produkty się zmieniały','Bo memo nie działa w listach','Bo brakowało key','Nowa funkcja to zmieniony prop.');
q('Dlaczego useCallback((id) => setCart(c => [...c, id]), []) ma poprawne puste zależności?','Forma funkcyjna nie czyta cart, a setter jest stabilny','Bo zależności są opcjonalne','Bo cart jest w refie','Nie ma poprawnych','Gdyby czytała cart, trzeba by go dodać.');
q('Dlaczego nowa tablica z filter nie psuje memo wierszy?','Zawiera te same obiekty produktów — props product mają te same referencje','Bo memo porównuje tablice','Bo filter nie tworzy nowej tablicy','Psuje','Wiersz dostaje product, nie całą tablicę.');
q('Kiedy mapowanie produktów zepsułoby memo wierszy?','Gdy w renderze tworzysz nowe obiekty: products.map(p => ({ ...p, label }))','Gdy używasz key','Gdy produkty mają id','Nigdy','Wtedy pomaga useMemo na całej przekształconej tablicy.');
q('Co pokazuje Profiler po naprawie zadania 2 przy dodaniu do koszyka?','Wiersze jako „Did not render”','Wszystkie wiersze żółte','Brak commitu','Błąd','Renderuje się tylko rodzic.');
q('Co renderuje wpisywanie w wyszukiwarkę po naprawie?','Rodzica i wiersze, które się pojawiły lub zniknęły','Wszystkie 500 wierszy','Nic','Tylko pole','Pozostałe wiersze mają te same props.');
q('Wariant A: <Card title="Raport" count={3} /> — czy Card się wyrenderuje?','Nie','Tak','Tylko w StrictMode','Tylko przy zmianie kontekstu','Same wartości proste.');
q('Wariant B: style={{ padding: 8 }} — czy Card się wyrenderuje?','Tak','Nie','Tylko w produkcji','Tylko w TSX','Nowy obiekt przy każdym renderze.');
q('Wariant C: onOpen={() => open(1)} — czy Card się wyrenderuje?','Tak','Nie','Tylko gdy open się zmieni','Tylko w dev','Nowa strzałka.');
q('Wariant D: <Card title="Raport"><p>Treść</p></Card> — czy Card się wyrenderuje?','Tak — children to nowy element','Nie','Tylko gdy tekst się zmieni','Tylko w TSX','memo z JSX w children prawie nigdy nie działa.');
q('Wariant E: tags={TAGS}, gdzie TAGS to stała modułu — czy Card się wyrenderuje?','Nie','Tak','Tylko gdy TAGS się zmieni w pliku','Tylko w dev','Ta sama referencja.');
q('Wariant F: tags={["a", "b"]} — czy Card się wyrenderuje?','Tak','Nie','Tylko dla pustej tablicy','Tylko w TSX','Nowa tablica.');
q('Wariant G: onOpen={handleOpen} z useCallback([], …) — czy Card się wyrenderuje?','Nie','Tak','Tylko po zmianie handleOpen w pliku','Tylko w dev','Stabilna funkcja.');
q('Jaki wniosek płynie z zadania 3?','memo działa tylko z props prostymi lub stabilnymi referencjami','memo działa zawsze','memo nigdy nie działa','memo porównuje głęboko','Każdy nowy obiekt/funkcja psuje memo.');
q('Po co w zadaniu 4 sztuczna pętla w filterAndSort?','Żeby koszt obliczenia był widoczny przy pomiarze','Bo filtr tego wymaga','Żeby test był wolniejszy','Bez powodu','Symulacja ciężkiej pracy.');
q('Dlaczego bez useMemo przełącznik motywu się zacina?','Każdy render liczy kosztowny filtr od nowa','Bo motyw zmienia dane','Bo CSS jest wolny','Nie zacina się','Stan motywu jest w tym samym komponencie.');
q('Jakie są poprawne zależności useMemo w zadaniu 4?','[tickets, query, sortKey]','[]','[theme]','[tickets]','Wszystko, czego używa obliczenie.');
q('Czy wpisywanie w filtr po dodaniu useMemo nadal liczy wynik?','Tak — zmienia się zależność query, i to jest poprawne','Nie','Tylko co drugi znak','Tylko w produkcji','useMemo nie pomaga przy zmianie zależności.');
q('Dlaczego literał obiektu w useLiveTickets({ status, assignee }) restartuje połączenie?','Nowy obiekt przy każdym renderze zmienia zależność [filters]','Bo status się zmienia','Bo hook jest async','Nie restartuje','Object.is porównuje referencje.');
q('Która naprawa zadania 5 jest lepsza?','Zależności od pól prostych w hooku — naprawa u źródła','useMemo w każdym komponencie','Usunięcie efektu','Pusta tablica zależności','Wywołujący nie musi pamiętać o useMemo.');
q('Dlaczego naprawa (a) z useMemo jest słabsza?','Przerzuca odpowiedzialność na każdego wywołującego','Bo nie działa','Bo jest wolniejsza','Bo psuje typy','Jeden zapomniany useMemo i problem wraca.');
q('Dlaczego handleSave z useCallback(…, []) zapisuje pusty tekst?','Zapamiętał text z pierwszego renderu — nieaktualne domknięcie','Bo onSave jest async','Bo textarea nie działa','Bo memo blokuje zapis','Zależności jak w efektach.');
q('Ile razy renderuje się SaveButton przy pisaniu po naprawie zależności?','Przy każdym znaku','Raz','Nigdy','Co drugi znak','handleSave zmienia się z text.');
q('Czy memoizacja SaveButton ma sens w zadaniu 6?','Nie — zależność zmienia się przy każdym renderze','Tak, zawsze','Tak, przy długim tekście','Tylko w TSX','Najlepsza optymalizacja: usunąć memo.');
q('Kiedy memoizacja się opłaca?','Gdy zależności zmieniają się rzadziej niż renderuje się komponent','Zawsze','Gdy komponent ma stan','Gdy jest w TSX','Inaczej tylko dokłada porównań.');
q('Której reguły ESLint nie wolno wyciszać w zadaniu 6?','react-hooks/exhaustive-deps','react/jsx-key','no-console','react/prop-types','Zgłasza brakujące zależności.');
q('Zadanie 7, sytuacja A (render rodzica, te same props) — czy Badge się renderuje?','Nie','Tak','Tylko w StrictMode','Tylko z kontekstem','Jedyny przypadek, przed którym chroni memo.');
q('Zadanie 7, sytuacja B (najechanie, własny stan) — czy Badge się renderuje?','Tak','Nie','Tylko bez memo','Tylko w dev','Zmiana własnego stanu zawsze renderuje.');
q('Zadanie 7, sytuacja C (zmiana ThemeContext) — czy Badge się renderuje?','Tak','Nie','Tylko z useMemo','Tylko w TSX','memo nie blokuje kontekstu.');
q('Zadanie 7, sytuacja D (zmiana innego kontekstu) — czy Badge się renderuje?','Nie','Tak','Zawsze','Tylko w produkcji','Kontekst, którego nie czyta, go nie dotyczy.');
q('Jaka technika z zadania 8 eliminuje render wykresu przy pisaniu?','Stan w dół — SearchPanel z własnym query','memo na wykresie','useMemo na wykresie','useCallback na polu','Dashboard przestaje się renderować.');
q('Jaka technika z zadania 8 chroni wykres przy przewijaniu?','Treść jako children opakowania ze stanem','memo na ScrollTracker','useRef na pozycji','Kontekst','Element wykresu tworzy Dashboard.');
q('Ile memoizacji wymaga rozwiązanie zadania 8?','Zero','Jedno memo','useMemo i useCallback','memo na każdym komponencie','Ten sam efekt bez memoizacji.');
q('Dlaczego zmiana stanu ScrollTracker nie renderuje ExpensiveChart?','W JSX ScrollTracker jest ten sam obiekt elementu z children','Bo wykres ma memo','Bo wykres jest w portalu','Bo scroll nie zmienia stanu','Dashboard się nie renderował, więc nie utworzył nowego elementu.');
q('Czym różni się sytuacja Menu (zadanie 1) od ExpensiveChart (zadanie 8)?','Menu tworzył komponent ze stanem (App), wykres — komponent bez zmiany stanu (Dashboard)','Niczym','Menu ma memo','Wykres jest klasą','Liczy się, kto tworzy element.');
q('Po co własna funkcja porównująca w Sparkline?','Rodzic co sekundę przekazuje nową tablicę z tymi samymi wartościami','Bo memo nie działa z SVG','Żeby porównać kolor','Bez powodu','Porównanie po wartościach.');
q('Co musi uwzględniać arePropsEqual?','Wszystkie props wpływające na wygląd i zachowanie, także funkcje','Tylko points','Tylko color','Tylko props proste','Pominięta funkcja = stare domknięcie.');
q('Co się stanie, gdy arePropsEqual pominie onHover?','Komponent zachowa starą funkcję z nieaktualnymi danymi','Nic','Rzuci błąd','Wyrenderuje się dwa razy','Trudny do znalezienia błąd.');
q('Kiedy głębokie porównanie w arePropsEqual jest złym pomysłem?','Gdy struktury są duże — porównanie bywa droższe niż render','Zawsze dobre','Gdy props są proste','Gdy komponent jest tani','Lepiej naprawić źródło nowych tablic.');
q('Jak często renderuje się Sparkline przy odświeżaniu co sekundę i nowym punkcie co 5 s?','Co 5 sekund','Co sekundę','Nigdy','Co 10 sekund','Tylko przy zmianie wartości.');
q('Co zwraca funkcja porównująca memo, gdy render ma być pominięty?','true','false','null','Nowe props','Odwrotnie niż shouldComponentUpdate.');
q('Dlaczego value TicketsProvider musi być w useMemo?','Bez tego każda zmiana lastSync daje nowy obiekt i renderuje wszystkich konsumentów','Bo kontekst tego wymaga','Bo tickets to tablica','Nie musi','memo konsumentów nic nie pomoże.');
q('Dlaczego select i remove są w useCallback?','Żeby value z useMemo miało stabilne zależności','Bo inaczej nie działają','Bo są async','Bez powodu','Nowe funkcje zmieniałyby value.');
q('Czy memo na TicketCount jest potrzebne w zadaniu 10?','Nie — TicketCount jest przekazany jako children','Tak, kluczowe','Tylko w TSX','Tylko z lastSync','Kluczową robotę robi useMemo na value.');
q('Czy zmiana selectedId renderuje TicketCount, który czyta tylko tickets?','Tak — zmienia się value kontekstu','Nie','Tylko z memo','Tylko w dev','Rozwiązanie: rozdzielić konteksty.');
q('Co zrobić, gdy konsument czyta tylko część kontekstu, a renderuje się przy każdej zmianie?','Rozdzielić kontekst na mniejsze','Dodać memo','Usunąć kontekst','Dodać key','Np. osobno dane i akcje.');
q('Co pokazuje porównanie w zadaniu 11?','Kompilator daje tę samą liczbę renderów wierszy co ręczne memo','Kompilator renderuje więcej','Kompilator nie działa z listami','Ręczne memo jest szybsze','Kod prostszy, efekt ten sam.');
q('Ile wierszy renderuje się po dodaniu do koszyka bez żadnej optymalizacji (12 wierszy)?','12','0','1','2','Render rodzica renderuje wszystkie.');
q('Ile wierszy renderuje się po dodaniu do koszyka z React Compiler (12 wierszy)?','0','12','1','6','Zapamiętany JSX listy i stabilny onAdd.');
q('Jak DevTools oznacza komponenty skompilowane?','„Memo ✨”','„Compiled”','Kolor niebieski','Nie oznacza','Zadanie 11, krok 2.');
q('Czego kompilator nie naprawi w treningu?','Nieczystego komponentu (mutacja props, ref w renderze) — pominie go','Braku useCallback','Braku memo','Nowych obiektów w JSX','Linter zgłosi takie miejsca.');
q('Co zostaje po włączeniu kompilatora?','Decyzje o strukturze, czystość i stabilność zależności dla bibliotek','Nic, kompilator robi wszystko','Tylko testy','Tylko typy','Kompilator nie zwalnia z rozumienia renderów.');
q('Jak wyłączyć kompilator w jednym komponencie?','Dyrektywą "use no memo"','Usunięciem import React','memo(Komponent, false)','Nie da się','Tymczasowe obejście.');
q('Gdzie zadanie 12 przechowuje pomiary z onRender?','W refie — zapis nie powoduje renderu','W stanie','W kontekście','W localStorage','Zapis do stanu w onRender wywołałby kolejne rendery.');
q('Dlaczego onRender w MeasuredList jest w useCallback?','Żeby Profiler dostawał stałą funkcję','Bo Profiler tego wymaga','Bez powodu','Bo ref tego wymaga','Mniej zmian props.');
q('Co liczy summarizeCommits?','Liczbę commitów, sumę i średnią actualDuration','Liczbę komponentów','Czas sieci','Rozmiar bundla','Podstawa decyzji o memoizacji.');
q('Ile commitów listy zarejestruje Profiler przy montowaniu i trzech kliknięciach „Do koszyka”?','4','3','1','6','Montowanie + trzy aktualizacje.');
q('Dlaczego liczby z npm run dev są zawyżone?','Tryb deweloperski ma dodatkowe sprawdzenia i StrictMode','Bo dev nie używa DOM','Bo Profiler jest wolny','Nie są','Dobre do porównań, nie jako wartości bezwzględne.');
q('Czy Profiler działa w produkcji?','Tylko z buildem profilującym (react-dom/profiling)','Tak, zawsze','Nie, nigdy','Tylko w Next.js','W dev działa domyślnie.');
q('Ile razy powtarzać pomiar?','Kilka razy na tym samym scenariuszu','Raz','Nigdy','Tylko po wdrożeniu','Pojedynczy pomiar bywa przypadkowy.');
q('Jaki próg czasu interakcji przyjmuje zadanie 12?','Około 16 ms na klatkę','1 s','200 ms na każdy render','1 ms','Przy 60 FPS.');
q('Co jest kryterium zaliczenia treningu?','Poprawne odpowiedzi 1, 3, 7, liczby z Profilera w 2 i 12, zadanie 8 bez memoizacji','Wszystkie komponenty w memo','Brak renderów','Kompilator w trybie all','Decyzje na podstawie liczb.');
q('Co robi forma funkcyjna setCart(c => [...c, id])?','Czyta aktualny stan w momencie aktualizacji, więc callback nie zależy od cart','Mutuje tablicę','Zatrzymuje render','Nic','Dzięki temu puste zależności są prawdziwe.');
q('Co zrobić z memoizacją, która po pomiarze nie dała zysku?','Usunąć ją','Zostawić','Dodać więcej','Przenieść do kontekstu','Kod bez zysku to koszt.');
q('Dlaczego test zadania 2 mierzy Profilerem w wierszu, a nie licznikiem w ciele komponentu?','Profiler raportuje faktyczny render, a kod wiersza zostaje czysty (także dla kompilatora)','Bo licznik nie działa','Bo Profiler jest szybszy','Bez powodu','Zmienna modułu w renderze wyłączyłaby kompilację.');
q('Czy React pomija render wiersza, gdy rodzic zwraca ten sam (zapamiętany) element?','Tak','Nie','Tylko z memo','Tylko w TSX','Na tym polega efekt kompilatora w zadaniu 11.');
q('Dlaczego TicketsProvider w teście używa sztucznych zegarów?','Żeby przesunąć czas o 20 s bez czekania','Bo setInterval nie działa w jsdom','Bo React tego wymaga','Bez powodu','vi.advanceTimersByTime.');
q('Co testuje porównanie wariantów test.each w zadaniu 11?','Ten sam scenariusz na trzech implementacjach listy','Trzy różne scenariusze','Wydajność sieci','Rozmiar bundla','Kontrolowany eksperyment.');
q('Co jest wspólną zasadą całego treningu?','Najpierw zmierz, potem memoizuj, potem zmierz ponownie','Zawsze memoizuj','Nigdy nie memoizuj','Memoizuj tylko w TSX','Pytanie z lekcji 02.');
q('Które narzędzie z treningu pokazuje powód renderu każdego komponentu?','Profiler z opcją „Record why each component rendered”','Zakładka Network','console.time','Lighthouse','Odpowiedź: props, stan, kontekst lub rodzic.');
const probes=[
  ['Zadanie 1: kolejność renderów po „+1”','App,Display,Header,Sidebar,Menu','App,Display','Display','App,Display,Header',String.raw`function Display({value}:{value:number}){log.push("Display");return <p>{value}</p>;}function Header({title}:{title:string}){log.push("Header");return <h1>{title}</h1>;}function Sidebar({children}:{children:ReactNode}){log.push("Sidebar");return <aside>{children}</aside>;}function Menu(){log.push("Menu");return <nav/>;}let inc!:()=>void;function App(){const [count,setCount]=useState(0);inc=()=>setCount(c=>c+1);log.push("App");return <><Display value={count}/><Header title="DevDesk"/><Sidebar><Menu/></Sidebar></>;}mount(<App/>);log.length=0;act(()=>inc());console.log(log.join(","));`],
  ['Zadanie 3 A: wartości proste','nie','tak','błąd','undefined',card+String.raw`mount(<Parent>{()=><Card title="Raport" count={3}/>}</Parent>);act(()=>bump());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 3 B: literał obiektu style','tak','nie','błąd','undefined',card+String.raw`mount(<Parent>{()=><Card title="Raport" style={{padding:8}}/>}</Parent>);act(()=>bump());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 3 C: strzałka w onOpen','tak','nie','błąd','undefined',card+String.raw`mount(<Parent>{()=><Card title="Raport" onOpen={()=>{}}/>}</Parent>);act(()=>bump());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 3 D: JSX w children','tak','nie','błąd','undefined',card+String.raw`mount(<Parent>{()=><Card title="Raport"><p>Treść</p></Card>}</Parent>);act(()=>bump());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 3 E: stała modułu w tags','nie','tak','błąd','undefined',card+String.raw`mount(<Parent>{()=><Card title="Raport" tags={TAGS}/>}</Parent>);act(()=>bump());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 3 F: literał tablicy w tags','tak','nie','błąd','undefined',card+String.raw`mount(<Parent>{()=><Card title="Raport" tags={["a","b"]}/>}</Parent>);act(()=>bump());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 3 G: useCallback w onOpen','nie','tak','błąd','undefined',card+String.raw`function WithCallback(){const handleOpen=useCallback(()=>{},[]);return <Card title="Raport" onOpen={handleOpen}/>;}mount(<Parent>{()=><WithCallback/>}</Parent>);act(()=>bump());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 7 A: render rodzica, te same props','nie','tak','błąd','undefined',String.raw`const Theme=createContext("jasny");let renders=0;const Badge=memo(function Badge({label}:{label:string}){use(Theme);renders+=1;return <span>{label}</span>;});mount(<Parent>{()=><Badge label="Nowy"/>}</Parent>);act(()=>bump());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 7 B: własny stan hover','tak','nie','błąd','undefined',String.raw`let renders=0;let hover!:()=>void;const Badge=memo(function Badge({label}:{label:string}){const [h,setH]=useState(false);hover=()=>setH(true);renders+=1;return <span>{label}{h&&" ✎"}</span>;});mount(<Badge label="Nowy"/>);act(()=>hover());console.log(renders>1?"tak":"nie");`],
  ['Zadanie 7 C: zmiana czytanego kontekstu','tak','nie','błąd','undefined',String.raw`const Theme=createContext("jasny");let renders=0;const Badge=memo(function Badge({label}:{label:string}){use(Theme);renders+=1;return <span>{label}</span>;});let set!:(t:string)=>void;function App(){const [t,setT]=useState("jasny");set=setT;return <Theme value={t}><Badge label="Nowy"/></Theme>;}mount(<App/>);act(()=>set("ciemny"));console.log(renders>1?"tak":"nie");`],
  ['Zadanie 7 D: zmiana innego kontekstu','nie','tak','błąd','undefined',String.raw`const Theme=createContext("jasny");const Lang=createContext("pl");let renders=0;const Badge=memo(function Badge({label}:{label:string}){use(Theme);renders+=1;return <span>{label}</span>;});let set!:(l:string)=>void;function App(){const [l,setL]=useState("pl");set=setL;return <Lang value={l}><Badge label="Nowy"/></Lang>;}mount(<App/>);act(()=>set("en"));console.log(renders>1?"tak":"nie");`],
  ['Zadanie 2: rendery wierszy w memo ze strzałką onAdd (5 wierszy)','5','0','1','10',String.raw`let rows=0;const Row=memo(function Row({id,onAdd}:{id:number;onAdd:(id:number)=>void}){rows+=1;return <li onClick={()=>onAdd(id)}>{id}</li>;});let add!:(id:number)=>void;function List(){const [cart,setCart]=useState<number[]>([]);add=id=>setCart(c=>[...c,id]);return <ul data-cart={cart.length}>{[1,2,3,4,5].map(id=><Row key={id} id={id} onAdd={i=>setCart(c=>[...c,i])}/>)}</ul>;}mount(<List/>);rows=0;act(()=>add(1));console.log(rows);`],
  ['Zadanie 2: rendery wierszy w memo z useCallback (5 wierszy)','0','5','1','10',String.raw`let rows=0;const Row=memo(function Row({id,onAdd}:{id:number;onAdd:(id:number)=>void}){rows+=1;return <li onClick={()=>onAdd(id)}>{id}</li>;});let add!:(id:number)=>void;function List(){const [cart,setCart]=useState<number[]>([]);const onAdd=useCallback((id:number)=>setCart(c=>[...c,id]),[]);add=onAdd;return <ul data-cart={cart.length}>{[1,2,3,4,5].map(id=><Row key={id} id={id} onAdd={onAdd}/>)}</ul>;}mount(<List/>);rows=0;act(()=>add(1));console.log(rows);`],
  ['Zadanie 2: nowe obiekty produktów z map psują memo (3 wiersze)','3','0','1','6',String.raw`const items=[{id:1},{id:2},{id:3}];let rows=0;const Row=memo(function Row({item}:{item:{id:number;label:string}}){rows+=1;return <li>{item.label}</li>;});function List({n}:{n:number}){const labeled=items.map(p=>({...p,label:"P"+p.id}));return <ul data-n={n}>{labeled.map(p=><Row key={p.id} item={p}/>)}</ul>;}const v=mount(<List n={1}/>);rows=0;v.update(<List n={2}/>);console.log(rows);`],
  ['Zadanie 6: co zapisze useCallback z pustą tablicą','""','"ab"','"a"','undefined',String.raw`let save!:()=>string;function Editor({text}:{text:string}){save=useCallback(()=>text,[]);return <p>{text}</p>;}const v=mount(<Editor text=""/>);v.update(<Editor text="ab"/>);console.log(JSON.stringify(save()));`],
  ['Zadanie 8: rendery wykresu przy zmianie stanu opakowania z children','1','2','3','0',String.raw`let chart=0;function Chart(){chart+=1;return <svg/>;}let scroll!:()=>void;function Tracker({children}:{children:ReactNode}){const [y,setY]=useState(0);scroll=()=>setY(v=>v+10);return <div data-y={y}>{children}</div>;}function Dashboard(){return <main><Tracker><Chart/></Tracker></main>;}mount(<Dashboard/>);act(()=>scroll());console.log(chart);`],
  ['Zadanie 8: rendery wykresu, gdy stan jest w Dashboard','2','1','3','0',String.raw`let chart=0;function Chart(){chart+=1;return <svg/>;}let type!:()=>void;function Dashboard(){const [q,setQ]=useState("");type=()=>setQ("a");return <main><input value={q} readOnly/><Chart/></main>;}mount(<Dashboard/>);act(()=>type());console.log(chart);`],
  ['Zadanie 9: memo z porównaniem po wartościach i nową tablicą','1','2','3','0',String.raw`let renders=0;const Spark=memo(function Spark({points}:{points:number[]}){renders+=1;return <p>{points.join()}</p>;},(a,b)=>a.points.length===b.points.length&&a.points.every((p,i)=>p===b.points[i]));const v=mount(<Spark points={[1,2]}/>);v.update(<Spark points={[1,2]}/>);v.update(<Spark points={[1,2]}/>);console.log(renders);`],
  ['Zadanie 9: porównanie pomijające onHover — która funkcja zostaje','stara','nowa','żadna','błąd',String.raw`let current!:()=>string;const Spark=memo(function Spark({onHover}:{points:number[];onHover:()=>string}){current=onHover;return null;},(a,b)=>a.points.length===b.points.length);const v=mount(<Spark points={[1]} onHover={()=>"stara"}/>);v.update(<Spark points={[1]} onHover={()=>"nowa"}/>);console.log(current());`],
  ['Zadanie 10: rendery konsumenta przy nowym obiekcie value','2','1','3','0',String.raw`const Ctx=createContext<{n:number}|null>(null);let renders=0;const Count=memo(function Count(){use(Ctx);renders+=1;return null;});let sync!:()=>void;function Provider({children}:{children:ReactNode}){const [s,setS]=useState(0);sync=()=>setS(x=>x+1);return <Ctx value={{n:1}}><p>{s}</p>{children}</Ctx>;}mount(<Provider><Count/></Provider>);act(()=>sync());console.log(renders);`],
  ['Zadanie 10: rendery konsumenta-children bez kontekstu przy zmianie stanu providera','1','2','3','0',String.raw`let renders=0;function Plain(){renders+=1;return null;}let sync!:()=>void;function Provider({children}:{children:ReactNode}){const [s,setS]=useState(0);sync=()=>setS(x=>x+1);return <section><p>{s}</p>{children}</section>;}mount(<Provider><Plain/></Provider>);act(()=>sync());console.log(renders);`],
  ['Zadanie 5: subskrypcje po niezwiązanej zmianie (zależność od obiektu)','2','1','0','3',String.raw`let subs=0;function useLive(filters:{status:string}){useEffect(()=>{subs+=1;},[filters]);}let inc!:()=>void;function C(){const [n,setN]=useState(0);inc=()=>setN(x=>x+1);useLive({status:"OPEN"});return <p>{n}</p>;}mount(<C/>);act(()=>inc());console.log(subs);`],
  ['Zadanie 5: subskrypcje po niezwiązanej zmianie (zależność od pola)','1','2','0','3',String.raw`let subs=0;function useLive(filters:{status:string}){const {status}=filters;useEffect(()=>{subs+=1;},[status]);}let inc!:()=>void;function C(){const [n,setN]=useState(0);inc=()=>setN(x=>x+1);useLive({status:"OPEN"});return <p>{n}</p>;}mount(<C/>);act(()=>inc());console.log(subs);`],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 w Vitest (createRoot + act w jsdom, bez StrictMode).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'vitest.config.ts':config,'src/ProductList.tsx':products,'src/ProductList.compiled.tsx':productsBare,'src/ProductList.plain.tsx':productsBare,
  'src/TicketBoard.tsx':board,'src/useLiveTickets.ts':live,'src/NoteEditor.tsx':note,'src/Dashboard.tsx':dashboard,'src/Sparkline.tsx':spark,
  'src/TicketsProvider.tsx':provider,'src/MeasuredList.tsx':stats,'src/drill.test.tsx':tests};
const mutants=[
  {file:'src/ProductList.tsx',before:'useCallback((id:number)=>setCart(c=>[...c,id]),[])',after:'((id:number)=>setCart(c=>[...c,id]))'},
  {file:'src/TicketBoard.tsx',before:'[tickets,query,sortKey,compute]',after:'[tickets,query,sortKey,compute,{}]'},
  {file:'src/useLiveTickets.ts',before:'const {status,assignee}=filters;\n  useEffect(()=>connect({status,assignee}),[status,assignee,connect]);',after:'useEffect(()=>connect(filters),[filters,connect]);'},
  {file:'src/NoteEditor.tsx',before:'[text,onSave]',after:'[onSave]'},
  {file:'src/Dashboard.tsx',before:'  return <main>\n    <SearchPanel/>',after:'  const [query,setQuery]=useState("");\n  return <main>\n    <input aria-label="Szukaj" value={query} onChange={e=>setQuery(e.target.value)}/>'},
  {file:'src/Sparkline.tsx',before:'a.onHover===b.onHover&&',after:''},
  {file:'src/TicketsProvider.tsx',before:'useMemo(()=>({tickets,selectedId,select,remove}),[tickets,selectedId,select,remove])',after:'({tickets,selectedId,select,remove})'},
  {file:'vitest.config.ts',before:'/\\.compiled\\.tsx$/',after:'/\\.nigdy$/'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
