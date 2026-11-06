// Lekcja 16/02: memoizacja. memo, useMemo i useCallback mierzone licznikami renderów w React 19 (Vitest, jsdom).
const types=String.raw`export type Status="OPEN"|"IN_PROGRESS"|"CLOSED";
export type Ticket={id:number;title:string;status:Status;assignee?:string};
const statuses:Status[]=["OPEN","IN_PROGRESS","CLOSED"];
const people=["Ala","Bartek","Celina"];
export function makeTickets(count:number):Ticket[]{
  return Array.from({length:count},(_,i)=>({id:i+1,title:"Zgłoszenie "+(i+1),status:statuses[i%3],assignee:i%4===0?undefined:people[i%3]}));
}`;
const shallow=String.raw`// Tak memo porównuje props: ta sama liczba kluczy i Object.is dla każdego z nich.
export function shallowEqual(a:Record<string,unknown>,b:Record<string,unknown>):boolean{
  const keys=Object.keys(a);
  if(keys.length!==Object.keys(b).length)return false;
  return keys.every(key=>Object.prototype.hasOwnProperty.call(b,key)&&Object.is(a[key],b[key]));
}`;
const table=String.raw`import {memo,useCallback,useState} from "react";
import type {Ticket} from "./types";
type RowProps={ticket:Ticket;selected:boolean;onSelect:(id:number)=>void;onRowRender?:(id:number)=>void};
// Nazwana funkcja w memo — czytelna nazwa w DevTools. Pomiar: 2000 wierszy, kliknięcie renderuje 2 zamiast 2000.
const TicketRow=memo(function TicketRow({ticket,selected,onSelect,onRowRender}:RowProps){
  onRowRender?.(ticket.id);
  return <tr className={selected?"row row--selected":"row"} onClick={()=>onSelect(ticket.id)}>
    <td>{ticket.id}</td><td>{ticket.title}</td>
  </tr>;
});
export function TicketTable({tickets,onRowRender}:{tickets:readonly Ticket[];onRowRender?:(id:number)=>void}){
  const [selectedId,setSelectedId]=useState<number|null>(null);
  const [note,setNote]=useState("");
  // stabilna funkcja: setter jest stabilny, więc pusta tablica zależności jest prawdziwa
  const handleSelect=useCallback((id:number)=>setSelectedId(id),[]);
  return <div>
    <input aria-label="Notatka" value={note} onChange={e=>setNote(e.target.value)}/>
    <table><tbody>
      {tickets.map(t=><TicketRow key={t.id} ticket={t} selected={t.id===selectedId} onSelect={handleSelect} onRowRender={onRowRender}/>)}
    </tbody></table>
  </div>;
}`;
const stats=String.raw`import {useMemo} from "react";
import type {Status,Ticket} from "./types";
export type TicketStats={byStatus:Record<Status,number>;topAssignees:[string,number][]};
export function computeStats(tickets:readonly Ticket[]):TicketStats{
  const byStatus:Record<Status,number>={OPEN:0,IN_PROGRESS:0,CLOSED:0};
  const byAssignee=new Map<string,number>();
  for(const t of tickets){
    byStatus[t.status]+=1;
    if(t.assignee)byAssignee.set(t.assignee,(byAssignee.get(t.assignee)??0)+1);
  }
  return {byStatus,topAssignees:[...byAssignee.entries()].sort((a,b)=>b[1]-a[1]||a[0].localeCompare(b[0])).slice(0,5)};
}
// compute wstrzykiwany, żeby test mógł policzyć wywołania.
export function useTicketStats(tickets:readonly Ticket[],compute:(tickets:readonly Ticket[])=>TicketStats=computeStats){
  return useMemo(()=>compute(tickets),[tickets,compute]);
}`;
const prefs=String.raw`import {createContext,use,useCallback,useMemo,useState,type ReactNode} from "react";
type Density="compact"|"comfortable";
type PrefsApi={prefs:{density:Density};setDensity:(d:Density)=>void};
export const PrefsContext=createContext<PrefsApi|null>(null);
export function PrefsProvider({children}:{children:ReactNode}){
  const [prefs,setPrefs]=useState<{density:Density}>({density:"comfortable"});
  const setDensity=useCallback((density:Density)=>setPrefs(p=>({...p,density})),[]);
  // stabilna wartość: render providera bez zmiany preferencji nie renderuje czytelników
  const value=useMemo(()=>({prefs,setDensity}),[prefs,setDensity]);
  return <PrefsContext value={value}>{children}</PrefsContext>;
}
export function usePrefs(){
  const api=use(PrefsContext);
  if(!api)throw new Error("usePrefs poza PrefsProvider");
  return api;
}`;
const draft=String.raw`import {useCallback,useState} from "react";
// Zależności useCallback jak w efektach: funkcja czyta draft i onSave, więc oba są w tablicy.
export function DraftEditor({onSave}:{onSave:(text:string)=>void}){
  const [draft,setDraft]=useState("");
  const save=useCallback(()=>onSave(draft),[draft,onSave]);
  return <div>
    <textarea aria-label="Szkic" value={draft} onChange={e=>setDraft(e.target.value)}/>
    <SaveButton onSave={save}/>
  </div>;
}
function SaveButton({onSave}:{onSave:()=>void}){return <button type="button" onClick={onSave}>Zapisz</button>;}`;
const tests=String.raw`import {memo,useState} from "react";
import {fireEvent,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test,vi} from "vitest";
import {DraftEditor} from "./DraftEditor";
import {PrefsProvider,usePrefs} from "./Prefs";
import {shallowEqual} from "./shallowEqual";
import {TicketTable} from "./TicketTable";
import {makeTickets,type Ticket} from "./types";
import {computeStats,useTicketStats} from "./useTicketStats";
const tickets=makeTickets(20);
function counter(){const renders=new Map<number,number>();return {renders,onRowRender:(id:number)=>renders.set(id,(renders.get(id)??0)+1)};}
test("memo + useCallback: kliknięcie renderuje tylko wiersz, którego selected się zmienił",async()=>{
  const {renders,onRowRender}=counter();
  const user=userEvent.setup();
  render(<TicketTable tickets={tickets} onRowRender={onRowRender}/>);
  renders.clear();
  await user.click(screen.getByText("Zgłoszenie 3"));
  expect([...renders.keys()]).toEqual([3]);
  await user.click(screen.getByText("Zgłoszenie 7"));
  expect([...renders.entries()].sort((a,b)=>a[0]-b[0])).toEqual([[3,2],[7,1]]);
  expect(screen.getByText("Zgłoszenie 7").closest("tr")).toHaveClass("row--selected");
});
test("pisanie w niezwiązanym polu nie renderuje wierszy",async()=>{
  const {renders,onRowRender}=counter();
  const user=userEvent.setup();
  render(<TicketTable tickets={tickets} onRowRender={onRowRender}/>);
  renders.clear();
  await user.type(screen.getByLabelText("Notatka"),"abc");
  expect(renders.size).toBe(0);
});
test("computeStats liczy statusy i najaktywniejsze osoby",()=>{
  const stats=computeStats(makeTickets(12));
  expect(stats.byStatus).toEqual({OPEN:4,IN_PROGRESS:4,CLOSED:4});
  expect(stats.topAssignees).toEqual([["Ala",3],["Bartek",3],["Celina",3]]);
});
test("useTicketStats przelicza tylko po zmianie listy",()=>{
  const compute=vi.fn(computeStats);
  function Panel({list,note}:{list:readonly Ticket[];note:string}){const stats=useTicketStats(list,compute);return <p>{note}:{stats.byStatus.OPEN}</p>;}
  const list=makeTickets(9);
  const view=render(<Panel list={list} note="a"/>);
  view.rerender(<Panel list={list} note="b"/>);
  view.rerender(<Panel list={list} note="c"/>);
  expect(compute).toHaveBeenCalledTimes(1);
  view.rerender(<Panel list={makeTickets(3)} note="c"/>);
  expect(compute).toHaveBeenCalledTimes(2);
  expect(screen.getByText("c:1")).toBeInTheDocument();
});
test("stabilna wartość PrefsProvider: render providera nie renderuje czytelników",async()=>{
  let readerRenders=0;
  const Reader=memo(function Reader(){const {prefs,setDensity}=usePrefs();readerRenders+=1;return <button type="button" onClick={()=>setDensity("compact")}>{prefs.density}</button>;});
  function App(){const [n,setN]=useState(0);return <PrefsProvider><span onClick={()=>setN(n+1)}>tik {n}</span><Reader/></PrefsProvider>;}
  const user=userEvent.setup();
  render(<App/>);
  await user.click(screen.getByText("tik 0"));
  expect(readerRenders).toBe(1);
  await user.click(screen.getByRole("button"));
  expect(readerRenders).toBe(2);
  expect(screen.getByRole("button")).toHaveTextContent("compact");
});
test("useCallback z prawdziwymi zależnościami zapisuje aktualny szkic",()=>{
  const onSave=vi.fn();
  render(<DraftEditor onSave={onSave}/>);
  fireEvent.change(screen.getByLabelText("Szkic"),{target:{value:"pierwszy"}});
  fireEvent.change(screen.getByLabelText("Szkic"),{target:{value:"drugi"}});
  fireEvent.click(screen.getByRole("button",{name:"Zapisz"}));
  expect(onSave).toHaveBeenCalledWith("drugi");
});
test("shallowEqual porównuje jak memo",()=>{
  const fn=()=>{};
  expect(shallowEqual({a:1,fn},{a:1,fn})).toBe(true);
  expect(shallowEqual({a:NaN},{a:NaN})).toBe(true);
  expect(shallowEqual({style:{color:"red"}},{style:{color:"red"}})).toBe(false);
  expect(shallowEqual({a:1},{a:1,b:undefined})).toBe(false);
  expect(shallowEqual({a:1,b:undefined},{a:1,c:undefined})).toBe(false);
});`;
const header=String.raw`import {act,createContext,memo,use,useCallback,useMemo,useState,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);act(()=>root.render(ui));return {host,update:(next:ReactNode)=>act(()=>root.render(next))};}
void createContext;void memo;void use;void useCallback;void useMemo;void useState;
`;
const exercises=[
  ['Owiń wiersz tabeli w memo z nazwaną funkcją.','Nazwa funkcji pojawi się w DevTools.',table],
  ['Ustabilizuj onSelect przez useCallback z pustą tablicą zależności.','Setter z useState jest stabilny.',table],
  ['Udowodnij testem, że kliknięcie renderuje tylko wiersze ze zmienionym selected.','Licznik renderów per id.',tests],
  ['Sprawdź testem, że pisanie w niezwiązanym polu nie renderuje wierszy.','Pole Notatka ma stan w tabeli.',tests],
  ['Pokaż, że samo memo bez useCallback nic nie daje.','Nowa strzałka w każdym renderze = zmieniony prop.',String.raw`import {memo,useState} from "react";
const Row=memo(function Row({id,onSelect}:{id:number;onSelect:(id:number)=>void}){
  return <li onClick={()=>onSelect(id)}>{id}</li>;
});
export function List({ids}:{ids:number[]}){
  const [selected,setSelected]=useState<number|null>(null);
  // ŹLE: id => setSelected(id) to nowa funkcja przy każdym renderze — memo zawsze widzi zmianę
  return <ul data-selected={String(selected)}>{ids.map(id=><Row key={id} id={id} onSelect={i=>setSelected(i)}/>)}</ul>;
}`],
  ['Napisz shallowEqual działające jak porównanie props w memo.','Ta sama liczba kluczy i Object.is.',shallow],
  ['Przetestuj shallowEqual dla funkcji, NaN, obiektów i brakujących kluczy.','Obiekt literal to zawsze nowa referencja.',tests],
  ['Napisz computeStats liczące statusy i pięć najaktywniejszych osób.','Mapa osoba → liczba.',stats],
  ['Zapamiętaj statystyki przez useMemo zależne od listy.','Wstrzyknij compute do testu.',stats],
  ['Sprawdź testem, że statystyki nie przeliczają się przy zmianie niezwiązanego props.','vi.fn(computeStats).',tests],
  ['Ustabilizuj wartość kontekstu preferencji przez useMemo i useCallback.','value zależy od prefs i setDensity.',prefs],
  ['Sprawdź testem, że render providera bez zmiany preferencji nie renderuje czytelnika.','Czytelnik w memo, żeby render rodzica go nie dotyczył.',tests],
  ['Napraw kłamiące zależności useCallback w edytorze szkicu.','Funkcja czyta draft i onSave.',draft],
  ['Sprawdź testem, że zapisany jest aktualny szkic.','Dwie zmiany pola, potem klik Zapisz.',tests],
  ['Napisz memo z własnym porównaniem props (arePropsEqual).','Porównuj tylko id ticketu i selected.',String.raw`import {memo} from "react";
import type {Ticket} from "./types";
type RowProps={ticket:Ticket;selected:boolean;onSelect:(id:number)=>void};
// Własne porównanie: true = pomiń render. Ryzyko: zmiana tytułu przy tym samym id nie zostanie pokazana.
export const CompactRow=memo(function CompactRow({ticket,selected,onSelect}:RowProps){
  return <li className={selected?"on":""} onClick={()=>onSelect(ticket.id)}>{ticket.title}</li>;
},(prev,next)=>prev.ticket.id===next.ticket.id&&prev.selected===next.selected);`],
  ['Zamiast memo przenieś stan niżej, do komponentu, który go używa.','Wtedy render dotyczy tylko małego poddrzewa.',String.raw`import {useState} from "react";
function NoteField(){
  const [note,setNote]=useState(""); // stan przeniesiony z tabeli — pisanie nie renderuje wierszy
  return <input aria-label="Notatka" value={note} onChange={e=>setNote(e.target.value)}/>;
}
export function Toolbar(){return <div className="toolbar"><NoteField/></div>;}`],
  ['Podnieś ciężką treść w górę jako children komponentu ze stanem.','Elementy children powstają u rodzica, który się nie renderuje.',String.raw`import {useState,type ReactNode} from "react";
export function Collapsible({children}:{children:ReactNode}){
  const [open,setOpen]=useState(true);
  return <section>
    <button type="button" onClick={()=>setOpen(o=>!o)}>{open?"Zwiń":"Rozwiń"}</button>
    <div hidden={!open}>{children}</div>
  </section>;
}
// <Collapsible><HeavyChart/></Collapsible> — przełączanie nie renderuje HeavyChart.`],
  ['Ustabilizuj obiekt style przekazywany do komponentu w memo.','Stała poza komponentem albo useMemo.',String.raw`import {memo} from "react";
const Badge=memo(function Badge({style,label}:{style:{color:string};label:string}){return <span style={style}>{label}</span>;});
const openStyle={color:"green"}; // stała modułu: ta sama referencja w każdym renderze
export function Status({label}:{label:string}){return <Badge style={openStyle} label={label}/>;}`],
  ['Ustabilizuj przefiltrowaną tablicę przekazywaną do listy w memo.','filter zawsze tworzy nową tablicę.',String.raw`import {memo,useMemo} from "react";
import type {Ticket} from "./types";
const List=memo(function List({items}:{items:readonly Ticket[]}){return <ul>{items.map(t=><li key={t.id}>{t.title}</li>)}</ul>;});
export function OpenTickets({tickets,note}:{tickets:readonly Ticket[];note:string}){
  const open=useMemo(()=>tickets.filter(t=>t.status==="OPEN"),[tickets]);
  return <div><p>{note}</p><List items={open}/></div>;
}`],
  ['Pokaż, że memo nie chroni przed zmianą kontekstu.','Komponent w memo czytający kontekst i tak się renderuje.',String.raw`import {createContext,memo,use} from "react";
export const ThemeContext=createContext("jasny");
// memo porównuje props, ale odczyt kontekstu rejestruje zależność — nowa wartość = render.
export const ThemedLabel=memo(function ThemedLabel({text}:{text:string}){
  const theme=use(ThemeContext);
  return <span className={theme}>{text}</span>;
});`],
  ['Pokaż, że memo nie chroni przed zmianą własnego stanu komponentu.','memo dotyczy tylko renderu wywołanego przez rodzica.',String.raw`import {memo,useState} from "react";
export const Counter=memo(function Counter(){
  const [n,setN]=useState(0); // setN renderuje komponent mimo memo
  return <button type="button" onClick={()=>setN(n+1)}>{n}</button>;
});`],
  ['Nie mutuj tablicy w miejscu — memo nie zauważy zmiany.','Nowa tablica = nowa referencja.',String.raw`import type {Ticket} from "./types";
// ŹLE: tickets.push(t); setTickets(tickets) — ta sama referencja, memo i bailout nic nie zauważą.
export function addTicket(tickets:readonly Ticket[],t:Ticket):readonly Ticket[]{
  return [...tickets,t]; // readonly w typie nie pozwala na push
}`],
  ['Napisz test: useMemo z obiektem tworzonym w renderze w zależnościach liczy się za każdym razem.','Nowy obiekt = zmiana zależności.',String.raw`import {useMemo} from "react";
import {render} from "@testing-library/react";
import {expect,test} from "vitest";
test("obiekt w zależnościach",()=>{
  let computes=0;
  function C({status}:{status:string}){
    const filter={status};
    const label=useMemo(()=>{computes+=1;return filter.status.toLowerCase();},[filter]);
    return <p>{label}</p>;
  }
  const view=render(<C status="OPEN"/>);
  view.rerender(<C status="OPEN"/>);
  expect(computes).toBe(2);
});`],
  ['Zastąp useMemo dla prostego obliczenia zwykłym wyrażeniem.','Narzut większy niż zysk.',String.raw`import type {Ticket} from "./types";
export function Counter({tickets}:{tickets:readonly Ticket[]}){
  const count=tickets.length; // useMemo(() => tickets.length, [tickets]) nic tu nie daje
  return <span>{count}</span>;
}`],
  ['Dodaj komentarz z wynikiem pomiaru przy pozostawionej memoizacji.','Komentarz uzasadnia istnienie kodu.',String.raw`import {useMemo} from "react";
import {computeStats} from "./useTicketStats";
import type {Ticket} from "./types";
export function StatsPanel({tickets}:{tickets:readonly Ticket[]}){
  // Pomiar 2026-11: 5000 ticketów, computeStats 14 ms; bez useMemo każde pisanie w notatce > 16 ms.
  const stats=useMemo(()=>computeStats(tickets),[tickets]);
  return <p>Otwarte: {stats.byStatus.OPEN}</p>;
}`],
  ['Ustabilizuj callback zależny od props przez useCallback z tym props w zależnościach.','onClose(id) czyta ticketId.',String.raw`import {memo,useCallback} from "react";
const CloseButton=memo(function CloseButton({onClose}:{onClose:()=>void}){return <button type="button" onClick={onClose}>Zamknij</button>;});
export function TicketHeader({ticketId,onClose}:{ticketId:number;onClose:(id:number)=>void}){
  const close=useCallback(()=>onClose(ticketId),[onClose,ticketId]);
  return <header><h2>#{ticketId}</h2><CloseButton onClose={close}/></header>;
}`],
  ['Pokaż zamianę useCallback na reducer z dispatch, który jest zawsze stabilny.','dispatch ma stałą tożsamość.',String.raw`import {memo,useReducer} from "react";
type Action={type:"select";id:number}|{type:"clear"};
function reducer(state:number|null,action:Action):number|null{return action.type==="select"?action.id:null;}
const Row=memo(function Row({id,dispatch}:{id:number;dispatch:(a:Action)=>void}){
  return <li onClick={()=>dispatch({type:"select",id})}>{id}</li>;
});
export function List({ids}:{ids:number[]}){
  const [selected,dispatch]=useReducer(reducer,null);
  return <ul data-selected={String(selected)}>{ids.map(id=><Row key={id} id={id} dispatch={dispatch}/>)}</ul>;
}`],
  ['Rozdziel kontekst danych i kontekst akcji, żeby komponenty z samymi akcjami nie renderowały się przy zmianie danych.','Akcje oparte na stabilnym setterze nie zmieniają się nigdy.',String.raw`import {createContext,useMemo,useState,type ReactNode} from "react";
type Density="compact"|"comfortable";
export const DensityContext=createContext<Density>("comfortable");
export const DensityActionsContext=createContext<{setDensity:(d:Density)=>void}|null>(null);
export function DensityProvider({children}:{children:ReactNode}){
  const [density,setDensity]=useState<Density>("comfortable");
  const actions=useMemo(()=>({setDensity}),[]); // setter stabilny → obiekt akcji stały przez całe życie
  return <DensityActionsContext value={actions}><DensityContext value={density}>{children}</DensityContext></DensityActionsContext>;
}`],
  ['Wyjaśnij różnicę useMemo i useCallback.','useCallback(fn, deps) = useMemo(() => fn, deps).',String.raw`// useMemo zapamiętuje WYNIK wywołania funkcji (np. obiekt statystyk),
// useCallback zapamiętuje SAMĄ funkcję (np. handler przekazywany do memo-komponentu).
// useCallback(fn, deps) to dokładnie useMemo(() => fn, deps).
export {};`],
  ['Zapisz procedurę: memoizacja po pomiarze.','Profil przed, zmiana, profil po, usunięcie zbędnych.',String.raw`// 1. Profil „przed” (2000 wierszy, kliknięcie ~2000 ms w dev).
// 2. memo na wierszu → pomiar: brak poprawy (nowa lambda onSelect).
// 3. useCallback → pomiar: renderują się 2 wiersze.
// 4. useMemo statystyk → pisanie w notatce ich nie przelicza.
// 5. Stabilna wartość providera.
// 6. Usuń memoizację bez mierzalnego zysku, przy pozostałej zostaw komentarz z wynikiem.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Co robi memo(Komponent)?','Pomija render, gdy props są płytko równe poprzednim','Zapamiętuje wynik obliczenia','Zapamiętuje funkcję','Blokuje zmiany stanu','Porównuje każdy prop przez Object.is.');
q('Co robi useMemo?','Zapamiętuje wynik obliczenia do zmiany zależności','Pomija render komponentu','Zapamiętuje komponent','Tworzy kontekst','Kosztowne obliczenia i stabilne referencje.');
q('Co robi useCallback?','Zapamiętuje funkcję do zmiany zależności','Wywołuje funkcję po renderze','Zapamiętuje wynik funkcji','Pomija render','To useMemo(() => fn, deps).');
q('Jak memo porównuje props?','Płytko — Object.is dla każdego propsa','Głęboko, rekurencyjnie','JSON.stringify','Po typach TypeScript','Nowy obiekt o tej samej treści to zmiana.');
q('Dlaczego samo memo na wierszu nie pomogło w lekcji?','onSelect był nową lambdą przy każdym renderze rodzica','memo nie działa z tabelami','Bo wiersze mają key','Bo memo działa tylko w produkcji','„Why did this render?” wskazał zmieniony onSelect.');
q('Co poprawia dodanie useCallback do handleSelect?','Wiersze dostają tę samą funkcję, więc renderują się tylko te ze zmienionym selected','Nic','Wyłącza render tabeli','Usuwa stan','Przy zmianie zaznaczenia renderują się 2 wiersze.');
q('Dlaczego useCallback((id) => setSelectedId(id), []) ma poprawną pustą tablicę?','Setter z useState jest stabilny i nic innego nie jest czytane','Bo zależności są opcjonalne','Bo useCallback ignoruje zależności','Nie ma poprawnej','Argument funkcji nie jest zależnością.');
q('Czy memo chroni przed renderem po zmianie własnego stanu komponentu?','Nie','Tak','Tylko z useCallback','Tylko w TSX','memo dotyczy renderu wywołanego przez rodzica.');
q('Czy memo chroni przed renderem po zmianie czytanego kontekstu?','Nie','Tak','Tylko dla obiektów','Tylko w StrictMode','Odczyt kontekstu rejestruje zależność fibera.');
q('Kiedy memo tylko dodaje koszt?','Gdy props zmieniają się przy każdym renderze','Gdy komponent jest kosztowny','Gdy props są stabilne','Gdy lista ma 2000 elementów','Porównanie zawsze przegrywa.');
q('Dlaczego style={{ color: "red" }} psuje memo?','Literał obiektu tworzy nową referencję w każdym renderze','Bo style nie jest propsem','Bo memo ignoruje style','Bo CSS jest wolny','Stała modułu albo useMemo.');
q('Co zwraca tickets.filter(...) w każdym renderze?','Nową tablicę, nawet przy tej samej treści','Tę samą tablicę','undefined','Kopię tylko przy zmianie','Dlatego przekazywanie jej do memo wymaga useMemo.');
q('Kiedy warto użyć useMemo?','Dla kosztownego obliczenia lub stabilnej referencji przekazywanej do memo/efektu','Dla każdego wyrażenia','Dla a + b','Dla items.length','Proste obliczenia są tańsze niż narzut.');
q('Co zwraca useMemo przy niezmienionych zależnościach?','Zapamiętany wynik z poprzedniego renderu','Nowy wynik','undefined','Funkcję obliczającą','Porównanie Object.is element po elemencie.');
q('Dlaczego wartość providera warto stabilizować useMemo?','Nowy obiekt value przy każdym renderze renderuje wszystkich czytelników','Bo kontekst wymaga useMemo','Bo value musi być tablicą','Bez znaczenia','Szczególnie gdy provider renderuje się często.');
q('Dlaczego setDensity w PrefsProvider jest owinięty w useCallback?','Żeby value z useMemo miało stabilne zależności','Bo inaczej nie działa','Bo setPrefs nie jest stabilny','Bez powodu','Nowa funkcja zmieniałaby value przy każdym renderze.');
q('Czym grozi useCallback(() => onSave(draft), [])?','Zapisuje stary szkic z pierwszego renderu — nieaktualne domknięcie','Niczym','Nieskończoną pętlą','Błędem kompilacji','Zależności jak w efektach.');
q('Która reguła ESLint pilnuje zależności useMemo i useCallback?','react-hooks/exhaustive-deps','react-hooks/rules-of-hooks','react/no-memo','no-closure','Ta sama co dla efektów.');
q('Co jest często lepsze od memoizacji?','Zmiana struktury: stan niżej albo treść jako children','Więcej memo','Klasy','Wyłączenie StrictMode','Mniej kodu, brak porównań.');
q('Na czym polega „przeniesienie stanu niżej”?','Stan trafia do komponentu, który go używa, więc render dotyczy mniejszego poddrzewa','Stan trafia do kontekstu','Stan trafia do localStorage','Stan trafia do rodzica','Np. pole notatki z własnym stanem.');
q('Na czym polega „podniesienie treści w górę”?','Ciężką część przekazuje się jako children z komponentu, który się nie renderuje','Przeniesienie CSS do góry pliku','Import na początku','Hoisting zmiennych','Elementy children zachowują referencję.');
q('Czy <Panel><Heavy /></Panel> chroni Heavy, gdy renderuje się rodzic Panelu?','Nie — rodzic tworzy nowy element <Heavy />','Tak, zawsze','Tylko z memo na Panelu','Tylko w produkcji','Chroni tylko przed renderem samego Panelu.');
q('Co oznacza drugi argument memo(Komponent, arePropsEqual)?','Własną funkcję porównania; true pomija render','Listę zależności','Nazwę komponentu','Domyślne props','Ryzyko: pominięcie potrzebnej aktualizacji.');
q('Jakie ryzyko niesie arePropsEqual porównujące tylko id ticketu?','Zmiana tytułu przy tym samym id nie zostanie pokazana','Żadne','Podwójny render','Błąd TS','Własne porównanie musi uwzględniać wszystko, co komponent pokazuje.');
q('Dlaczego mutacja tablicy w miejscu psuje memo?','Referencja się nie zmienia, więc memo uznaje props za równe i pokazuje stare dane','Bo memo kopiuje tablice','Bo mutacja jest wolna','Nie psuje','readonly w typie chroni przed push.');
q('Jak readonly Ticket[] w props pomaga memoizacji?','Kompilator nie pozwoli na push i inne mutacje w miejscu','Przyspiesza render','Wymusza useMemo','Zamraża tablicę w czasie działania','To ochrona w czasie kompilacji.');
q('Po co nazwana funkcja w memo(function TicketRow ...)?','Czytelna nazwa w DevTools zamiast Anonymous','Wymóg memo','Szybszy render','Lepsze typy','Działa tak samo w JSX i TSX.');
q('Czy memo zachowuje typ props w TSX?','Tak, użycie komponentu jest dalej sprawdzane','Nie, props stają się any','Tylko z generykiem','Tylko dla klas','memo jest generyczne.');
q('Jaka jest relacja useCallback do useMemo?','useCallback(fn, deps) to useMemo(() => fn, deps)','To różne mechanizmy','useMemo to useCallback z wynikiem','Brak relacji','Zapamiętanie funkcji vs wyniku.');
q('Czy dispatch z useReducer trzeba owijać w useCallback?','Nie, ma stałą tożsamość','Tak, zawsze','Tylko w TSX','Tylko w kontekście','Dobra alternatywa dla wielu callbacków.');
q('Czy React gwarantuje, że useMemo nigdy nie odrzuci zapamiętanej wartości?','Nie — to optymalizacja, kod musi działać także bez niej','Tak, zawsze','Tylko w produkcji','Tylko dla obiektów','Nie przechowuj w useMemo stanu.');
q('Gdzie trzymać obiekt stałej konfiguracji przekazywanej do memo-komponentu?','Jako stałą modułu poza komponentem','W useState','W każdym renderze jako literał','W localStorage','Ta sama referencja bez hooków.');
q('Ile wierszy renderuje się po zmianie zaznaczenia z wiersza 3 na 7 przy poprawnej memoizacji?','Dwa — 3 i 7','Jeden','Wszystkie','Żaden','Tylko tym zmienia się selected.');
q('Ile wierszy renderuje się przy pierwszym zaznaczeniu (wcześniej nic nie było zaznaczone)?','Jeden — nowo zaznaczony','Dwa','Wszystkie','Żaden','Żaden inny wiersz nie zmienia selected.');
q('Co pokaże Profiler po poprawnej memoizacji tabeli?','Szare wiersze poza dwoma, których selected się zmienił','Wszystkie wiersze żółte','Brak commitu','Błąd','Szary — nie renderował się.');
q('Kiedy memo na komponencie listy jest uzasadnione?','Gdy wierszy jest dużo albo są kosztowne, a props stabilne','Zawsze','Nigdy','Gdy lista ma 3 elementy','Zmierz przed i po.');
q('Co zrobić z memoizacją, która nie dała mierzalnej poprawy?','Usunąć ją','Zostawić na wszelki wypadek','Dodać więcej','Przenieść do kontekstu','Kod bez zysku to koszt utrzymania.');
q('Po co komentarz z wynikiem pomiaru przy memoizacji?','Uzasadnia istnienie kodu i ułatwia późniejszą decyzję o usunięciu','Bo wymaga tego React','Dla lintera','Nie ma sensu','Kryterium akceptacji lekcji.');
q('Co się stanie, gdy w zależnościach useMemo jest obiekt tworzony w renderze?','useMemo przeliczy wynik przy każdym renderze','Nigdy nie przeliczy','Rzuci błąd','Przeliczy raz','Nowy obiekt to zmiana zależności.');
q('Czy memo porównuje children?','Tak, children to zwykły prop — nowy element to zmiana','Nie, children są ignorowane','Tylko tekst','Tylko w TSX','<Memo><span/></Memo> renderuje się przy każdym renderze rodzica.');
q('Czy przekazanie settera z useState do memo-wiersza wymaga useCallback?','Nie, setter jest stabilny','Tak','Tylko w TSX','Tylko w StrictMode','Ale strzałka id => setX(id) już nie jest.');
q('Co oznacza „stabilna referencja”?','Ta sama tożsamość obiektu/funkcji między renderami','Wartość, która nie jest null','Obiekt zamrożony','Wartość w localStorage','Object.is(a, b) === true.');
q('Dlaczego useMemo statystyk pomaga przy pisaniu w polu notatki?','Render wywołany zmianą notatki nie przelicza statystyk, bo lista się nie zmieniła','Bo notatka jest w kontekście','Bo blokuje render','Nie pomaga','Zależność to tickets.');
q('Co zrobi useTicketStats po otrzymaniu nowej tablicy o tej samej treści?','Przeliczy statystyki — nowa referencja','Zwróci stary wynik','Rzuci błąd','Zwróci undefined','Stabilizuj tablicę wyżej.');
q('Dlaczego porównanie props może kosztować tyle co render?','Dla taniego komponentu z wieloma props porównanie i render są podobnie lekkie','Bo porównanie jest głębokie','Bo memo kopiuje props','Nie może','Memo opłaca się dla kosztownych renderów.');
q('Co zrobi memo, gdy rodzic przekazuje props ...ticket rozpakowane z tego samego obiektu?','Pominie render, jeśli wartości pól są te same','Zawsze wyrenderuje','Rzuci błąd','Porówna obiekt ticket','Porównywane są pojedyncze wartości.');
q('Czy React Compiler zmienia potrzebę ręcznej memoizacji?','Tak — automatycznie memoizuje komponenty i wartości (lekcja 3)','Nie','Usuwa memo z kodu','Wymaga useMemo wszędzie','Ręczna memoizacja staje się rzadsza.');
q('Czy useMemo może zawierać efekty uboczne, np. zapis do API?','Nie — obliczenie powinno być czyste','Tak','Tylko w dev','Tylko z useCallback','Efekty należą do useEffect.');
q('Jak przekazać do memo-wiersza callback zależny od id wiersza bez nowej funkcji na wiersz?','Przekazać stabilny onSelect(id), a id podać osobnym propem','Tworzyć onSelect={() => select(id)} dla każdego','Użyć useMemo w pętli','Nie da się','Wiersz sam wywołuje onSelect(ticket.id).');
q('Dlaczego useCallback w pętli map jest błędem?','Hooki nie mogą być wywoływane w pętlach','Bo jest wolny','Bo zwraca undefined','Nie jest błędem','Zasady hooków z lekcji 15/06.');
q('Co da memo na komponencie, który zawsze dostaje nowy obiekt w props data?','Nic poza kosztem porównania','Pominie render','Przyspieszy commit','Usunie obiekt','Ustabilizuj data wyżej.');
q('Jak zmierzyć efekt memoizacji?','Profiler przed i po tym samym scenariuszem','Liczba linii kodu','Czas budowania','Rozmiar bundla','Porównaj czasy commitów i liczbę renderów.');
q('Co zrobi Profiler z wierszem w memo, którego props się nie zmieniły?','Pokaże go na szaro (nie renderował się)','Pokaże na żółto','Ukryje','Zgłosi błąd','actualDuration rodzica spadnie.');
q('Kiedy useCallback jest potrzebny bez memo-dziecka?','Gdy funkcja jest zależnością efektu lub innego hooka','Nigdy','Zawsze','Tylko w klasach','Stabilna funkcja nie restartuje efektu.');
q('Co jest typowym błędem przy memo + kontekst?','Oczekiwanie, że memo zatrzyma render po zmianie kontekstu','Używanie memo z kontekstem','Brak key','Brak useCallback','Konsument kontekstu i tak się wyrenderuje.');
q('Jak rozdzielić kontekst, żeby rzadziej renderować czytelników?','Osobne konteksty dla danych i dla funkcji (API)','Jeden duży kontekst','Kontekst w useMemo każdego konsumenta','Nie da się','Komponenty używające tylko akcji nie renderują się przy zmianie danych.');
q('Czy kolejność kluczy w props wpływa na wynik shallowEqual?','Nie — liczy się zestaw kluczy i wartości','Tak','Tylko dla funkcji','Tylko w TSX','Porównujemy po nazwach kluczy.');
q('Co zwraca shallowEqual({ a: 1 }, { a: 1, b: undefined })?','false — różna liczba kluczy','true','undefined','Rzuca błąd','Dodatkowy klucz to zmiana.');
q('Co zwraca shallowEqual({ a: NaN }, { a: NaN })?','true','false','undefined','Rzuca błąd','Object.is(NaN, NaN) === true.');
q('Jaka jest kolejność działań według lekcji?','Pomiar → memo → pomiar → useCallback → pomiar → usunięcie zbędnych','memo wszędzie → pomiar','useMemo → wdrożenie','Pomiar tylko na końcu','Każdy krok potwierdzony Profilerem.');
q('Co jest kryterium akceptacji tej lekcji?','Kliknięcie renderuje dwa wiersze, statystyki nie przeliczają się bez potrzeby, memoizacja ma komentarz z pomiarem','Każdy komponent w memo','Brak renderów w ogóle','Usunięcie Profilera','Potwierdzone w Profilerze.');
q('Czy wartość zwrócona przez useMemo może być mutowana bezpiecznie?','Nie — mutacja zepsuje inne miejsca korzystające z tej referencji','Tak','Tylko tablice','Tylko w dev','Traktuj ją jak niezmienną.');
q('Co robi React z funkcją arePropsEqual zwracającą zawsze true?','Nigdy nie renderuje komponentu z powodu renderu rodzica','Renderuje zawsze','Rzuca błąd','Ignoruje ją','Pokazuje nieaktualne dane.');
q('Czy memo pomija render przy zmianie tylko jednego z wielu props?','Nie, wystarczy jedna różnica','Tak','Tylko dla funkcji','Tylko dla children','Porównanie każdego propsa.');
q('Co robi useMemo przy pierwszym renderze?','Oblicza wynik i zapamiętuje go z zależnościami','Zwraca undefined','Nic','Planuje obliczenie po commicie','Kolejne rendery porównują zależności.');
q('Czy useCallback przyspiesza samo tworzenie funkcji?','Nie — funkcja i tak jest tworzona; zysk to stabilna referencja','Tak, znacząco','Tylko w produkcji','Tak, eliminuje alokację','Liczy się tożsamość dla memo i efektów.');
q('Kiedy podział komponentu na mniejsze pomaga bez memo?','Gdy stan zmienia się w małym fragmencie, który można wydzielić','Nigdy','Zawsze','Tylko z kontekstem','Render obejmuje tylko komponent ze stanem i jego dzieci.');
const probes=[
  ['memo: rendery po zmianie stanu rodzica, props bez zmian','1','2','3','0',String.raw`let renders=0;const Child=memo(function Child({label}:{label:string}){renders+=1;return <p>{label}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child label="a"/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z lambdą w props','2','1','3','0',String.raw`let renders=0;const Child=memo(function Child({onClick}:{onClick:()=>void}){renders+=1;return <button onClick={onClick}/>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child onClick={()=>{}}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z useCallback w props','1','2','3','0',String.raw`let renders=0;const Child=memo(function Child({onClick}:{onClick:()=>void}){renders+=1;return <button onClick={onClick}/>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);const handle=useCallback(()=>{},[]);return <div data-n={n}><Child onClick={handle}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z setterem w props','1','2','3','0',String.raw`let renders=0;const Child=memo(function Child({onSet}:{onSet:(v:number)=>void}){renders+=1;return <button onClick={()=>onSet(1)}/>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child onSet={setN}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z literałem obiektu style','2','1','3','0',String.raw`let renders=0;const Child=memo(function Child({style}:{style:{color:string}}){renders+=1;return <p style={style}/>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child style={{color:"red"}}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo ze stałą modułu style','1','2','3','0',String.raw`const red={color:"red"};let renders=0;const Child=memo(function Child({style}:{style:{color:string}}){renders+=1;return <p style={style}/>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child style={red}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z children jako elementem','2','1','3','0',String.raw`let renders=0;const Box=memo(function Box({children}:{children:ReactNode}){renders+=1;return <div>{children}</div>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <section data-n={n}><Box><span>treść</span></Box></section>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z children jako tekstem','1','2','3','0',String.raw`let renders=0;const Box=memo(function Box({children}:{children:ReactNode}){renders+=1;return <div>{children}</div>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <section data-n={n}><Box>treść</Box></section>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo i własny stan','2','1','3','0',String.raw`let renders=0;let bump!:()=>void;const Counter=memo(function Counter(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);renders+=1;return <p>{n}</p>;});mount(<Counter/>);act(()=>bump());console.log(renders);`],
  ['memo i zmiana kontekstu','2','1','3','0',String.raw`const Ctx=createContext("jasny");let renders=0;const Label=memo(function Label(){use(Ctx);renders+=1;return null;});let toggle!:()=>void;function App(){const [t,setT]=useState("jasny");toggle=()=>setT("ciemny");return <Ctx value={t}><Label/></Ctx>;}mount(<App/>);act(()=>toggle());console.log(renders);`],
  ['memo z własnym porównaniem zawsze true','1','2','3','0',String.raw`let renders=0;const Child=memo(function Child({n}:{n:number}){renders+=1;return <p>{n}</p>;},()=>true);let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <Child n={n}/>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z własnym porównaniem zawsze true — tekst w DOM','0','1','pusty','undefined',String.raw`const Child=memo(function Child({n}:{n:number}){return <p>{n}</p>;},()=>true);let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <Child n={n}/>;}const v=mount(<Parent/>);act(()=>bump());console.log(v.host.textContent);`],
  ['memo z NaN w props','1','2','3','0',String.raw`let renders=0;const Child=memo(function Child({value}:{value:number}){renders+=1;return <p>{value}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child value={NaN}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z tablicą z filter','2','1','3','0',String.raw`const all=[1,2,3];let renders=0;const List=memo(function List({items}:{items:number[]}){renders+=1;return <p>{items.join()}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><List items={all.filter(x=>x>1)}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z tablicą z useMemo','1','2','3','0',String.raw`const all=[1,2,3];let renders=0;const List=memo(function List({items}:{items:number[]}){renders+=1;return <p>{items.join()}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);const items=useMemo(()=>all.filter(x=>x>1),[]);return <div data-n={n}><List items={items}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['useMemo przy niezmienionej zależności (3 rendery)','1','3','2','0',String.raw`let computes=0;function C({list,tick}:{list:number[];tick:number}){const sum=useMemo(()=>{computes+=1;return list.reduce((a,b)=>a+b,0);},[list]);return <p>{sum+tick}</p>;}const list=[1,2];const v=mount(<C list={list} tick={1}/>);v.update(<C list={list} tick={2}/>);v.update(<C list={list} tick={3}/>);console.log(computes);`],
  ['useMemo z nową tablicą o tej samej treści','2','1','3','0',String.raw`let computes=0;function C({list}:{list:number[]}){const sum=useMemo(()=>{computes+=1;return list.reduce((a,b)=>a+b,0);},[list]);return <p>{sum}</p>;}const v=mount(<C list={[1,2]}/>);v.update(<C list={[1,2]}/>);console.log(computes);`],
  ['useCallback: ta sama funkcja przy tych samych zależnościach','true','false','undefined','błąd',String.raw`const fns:unknown[]=[];function C({id,tick}:{id:number;tick:number}){fns.push(useCallback(()=>id,[id]));return <p>{tick}</p>;}const v=mount(<C id={1} tick={1}/>);v.update(<C id={1} tick={2}/>);console.log(String(fns[0]===fns[1]));`],
  ['Nieaktualne domknięcie w useCallback z pustą tablicą','pierwszy','drugi','pusty','undefined',String.raw`let save!:()=>string;function Editor({draft}:{draft:string}){save=useCallback(()=>draft,[]);return <p>{draft}</p>;}const v=mount(<Editor draft="pierwszy"/>);v.update(<Editor draft="drugi"/>);console.log(save());`],
  ['useCallback z prawdziwą zależnością','drugi','pierwszy','pusty','undefined',String.raw`let save!:()=>string;function Editor({draft}:{draft:string}){save=useCallback(()=>draft,[draft]);return <p>{draft}</p>;}const v=mount(<Editor draft="pierwszy"/>);v.update(<Editor draft="drugi"/>);console.log(save());`],
  ['Wiersze w memo po pierwszym zaznaczeniu (5 wierszy)','6','10','7','5',String.raw`let rows=0;const Row=memo(function Row({id,selected,onSelect}:{id:number;selected:boolean;onSelect:(id:number)=>void}){rows+=1;return <li className={selected?"on":""} onClick={()=>onSelect(id)}>{id}</li>;});let select!:(id:number)=>void;function List(){const [sel,setSel]=useState<number|null>(null);const handle=useCallback((id:number)=>setSel(id),[]);select=handle;return <ul>{[1,2,3,4,5].map(id=><Row key={id} id={id} selected={id===sel} onSelect={handle}/>)}</ul>;}mount(<List/>);act(()=>select(3));console.log(rows);`],
  ['Wiersze w memo po zmianie zaznaczenia (5 wierszy)','7','6','10','5',String.raw`let rows=0;const Row=memo(function Row({id,selected,onSelect}:{id:number;selected:boolean;onSelect:(id:number)=>void}){rows+=1;return <li className={selected?"on":""} onClick={()=>onSelect(id)}>{id}</li>;});let select!:(id:number)=>void;function List(){const [sel,setSel]=useState<number|null>(1);const handle=useCallback((id:number)=>setSel(id),[]);select=handle;return <ul>{[1,2,3,4,5].map(id=><Row key={id} id={id} selected={id===sel} onSelect={handle}/>)}</ul>;}mount(<List/>);act(()=>select(3));console.log(rows);`],
  ['Wartość kontekstu jako nowy obiekt: rendery czytelnika','2','1','3','0',String.raw`const Ctx=createContext<{n:number}|null>(null);let renders=0;const Reader=memo(function Reader(){use(Ctx);renders+=1;return null;});let bump!:()=>void;function App(){const [tick,setTick]=useState(0);bump=()=>setTick(t=>t+1);return <div data-t={tick}><Ctx value={{n:1}}><Reader/></Ctx></div>;}mount(<App/>);act(()=>bump());console.log(renders);`],
  ['Wartość kontekstu z useMemo: rendery czytelnika','1','2','3','0',String.raw`const Ctx=createContext<{n:number}|null>(null);let renders=0;const Reader=memo(function Reader(){use(Ctx);renders+=1;return null;});let bump!:()=>void;function App(){const [tick,setTick]=useState(0);bump=()=>setTick(t=>t+1);const value=useMemo(()=>({n:1}),[]);return <div data-t={tick}><Ctx value={value}><Reader/></Ctx></div>;}mount(<App/>);act(()=>bump());console.log(renders);`],
  ['Mutacja tablicy w miejscu a memo','a','a,b','b','pusty',String.raw`const items=["a"];const List=memo(function List({items}:{items:string[]}){return <p>{items.join(",")}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>{items.push("b");setN(x=>x+1);};return <div data-n={n}><List items={items}/></div>;}const v=mount(<Parent/>);act(()=>bump());console.log(v.host.textContent);`],
  ['Nowa tablica zamiast mutacji a memo','a,b','a','b','pusty',String.raw`let items=["a"];const List=memo(function List({items}:{items:string[]}){return <p>{items.join(",")}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>{items=[...items,"b"];setN(x=>x+1);};return <div data-n={n}><List items={items}/></div>;}const v=mount(<Parent/>);act(()=>bump());console.log(v.host.textContent);`],
  ['Typ wyniku memo(...)','object','function','string','undefined',String.raw`const Row=memo(function Row(){return null;});mount(<Row/>);console.log(typeof Row);`],
  ['Stan niżej: rendery ciężkiego rodzeństwa','1','2','3','0',String.raw`let heavy=0;function Heavy(){heavy+=1;return <p>ciężki</p>;}let type!:(v:string)=>void;function Note(){const [v,setV]=useState("");type=setV;return <input value={v} readOnly/>;}mount(<div><Note/><Heavy/></div>);act(()=>type("x"));console.log(heavy);`],
  ['Treść jako children: rendery ciężkiego dziecka','1','2','3','0',String.raw`let heavy=0;function Heavy(){heavy+=1;return <p>ciężki</p>;}let toggle!:()=>void;function Collapsible({children}:{children:ReactNode}){const [open,setOpen]=useState(true);toggle=()=>setOpen(o=>!o);return <div hidden={!open}>{children}</div>;}mount(<Collapsible><Heavy/></Collapsible>);act(()=>toggle());console.log(heavy);`],
  ['dispatch z useReducer jako props memo','1','2','3','0',String.raw`let renders=0;const Child=memo(function Child({dispatch}:{dispatch:(a:number)=>void}){renders+=1;return <button onClick={()=>dispatch(1)}/>;});let bump!:()=>void;function Parent(){const [n,dispatch]=useReducer((s:number,a:number)=>s+a,0);bump=()=>dispatch(1);return <div data-n={n}><Child dispatch={dispatch}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z rozpakowanymi polami obiektu','1','2','3','0',String.raw`const ticket={id:1,title:"API"};let renders=0;const Row=memo(function Row({id,title}:{id:number;title:string}){renders+=1;return <p>{id}{title}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Row {...{...ticket}}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['memo z jednym zmienionym z trzech props','2','1','3','0',String.raw`let renders=0;const Row=memo(function Row({a,b,c}:{a:number;b:number;c:number}){renders+=1;return <p>{a+b+c}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <Row a={1} b={2} c={n}/>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['useMemo z obiektem z renderu w zależnościach','2','1','3','0',String.raw`let computes=0;function C({status}:{status:string}){const filter={status};const label=useMemo(()=>{computes+=1;return filter.status;},[filter]);return <p>{label}</p>;}const v=mount(<C status="OPEN"/>);v.update(<C status="OPEN"/>);console.log(computes);`],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 w Vitest (createRoot + act w jsdom, bez StrictMode).',header.replace('useMemo,useState','useMemo,useReducer,useState').replace('void useState;','void useState;void useReducer;')+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/types.ts':types,'src/shallowEqual.ts':shallow,'src/TicketTable.tsx':table,'src/useTicketStats.ts':stats,'src/Prefs.tsx':prefs,'src/DraftEditor.tsx':draft,'src/memo.test.tsx':tests};
const mutants=[
  {file:'src/TicketTable.tsx',before:'useCallback((id:number)=>setSelectedId(id),[])',after:'((id:number)=>setSelectedId(id))'},
  {file:'src/TicketTable.tsx',before:'const TicketRow=memo(function TicketRow',after:'const TicketRow=(function TicketRow'},
  {file:'src/useTicketStats.ts',before:'[tickets,compute]',after:'[tickets,compute,{}]'},
  {file:'src/Prefs.tsx',before:'useMemo(()=>({prefs,setDensity}),[prefs,setDensity])',after:'({prefs,setDensity})'},
  {file:'src/DraftEditor.tsx',before:'[draft,onSave]',after:'[onSave]'},
  {file:'src/shallowEqual.ts',before:'Object.is(a[key],b[key])',after:'a[key]===b[key]'},
  {file:'src/shallowEqual.ts',before:'if(keys.length!==Object.keys(b).length)return false;',after:''},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
