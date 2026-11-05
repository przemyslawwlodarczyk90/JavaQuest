// Lekcja 15/02: rekoncyliacja. Zachowania (stan, tożsamość węzłów DOM, montowanie) sprawdza React 19 w Vitest.
const types=String.raw`export type Ticket={id:number;title:string;description:string};
export const sampleTickets:Ticket[]=[
  {id:41,title:"Logowanie",description:"SSO nie przekierowuje"},
  {id:42,title:"API timeout",description:"Lista ładuje się 30 s"},
  {id:43,title:"Eksport CSV",description:"Ucina polskie znaki"},
];`;
const reorder=String.raw`import {useState} from "react";
import type {Ticket} from "./types";
function Row({ticket}:{ticket:Ticket}){
  const [clicks,setClicks]=useState(0);
  return <li>
    #{ticket.id} {ticket.title}
    <button type="button" onClick={()=>setClicks(c=>c+1)}>kliknięto {clicks}</button>
    <input aria-label={"Notatka "+ticket.id} defaultValue=""/>
  </li>;
}
export function ReorderDemo({tickets}:{tickets:readonly Ticket[]}){
  const [reversed,setReversed]=useState(false);
  const [useIndex,setUseIndex]=useState(false);
  const list=reversed?[...tickets].reverse():tickets;
  return <div>
    <button type="button" onClick={()=>setReversed(r=>!r)}>Odwróć kolejność</button>
    <label><input type="checkbox" checked={useIndex} onChange={e=>setUseIndex(e.target.checked)}/> key = index</label>
    <ul>{list.map((t,i)=><Row key={useIndex?i:t.id} ticket={t}/>)}</ul>
  </div>;
}`;
const typeDemo=String.raw`import {useState} from "react";
export function Counter({label}:{label:string}){
  const [n,setN]=useState(0);
  return <button type="button" onClick={()=>setN(x=>x+1)}>{label}: {n}</button>;
}
export function OtherCounter({label}:{label:string}){
  const [n,setN]=useState(0);
  return <button type="button" onClick={()=>setN(x=>x+1)}>{label}: {n}</button>;
}
export function TypeDemo(){
  const [admin,setAdmin]=useState(false);
  return <div>
    <label><input type="checkbox" checked={admin} onChange={e=>setAdmin(e.target.checked)}/> tryb admina</label>
    {/* ten sam typ na tej samej pozycji → licznik ZOSTAJE */}
    {admin?<Counter label="Admin"/>:<Counter label="Agent"/>}
    {/* inny typ → licznik RESETUJE się przy przełączeniu */}
    {admin?<OtherCounter label="B-admin"/>:<Counter label="B-agent"/>}
  </div>;
}`;
const groups=String.raw`import {Fragment} from "react";
import type {Ticket} from "./types";
export function TicketRowsWithDetails({tickets,expandedId}:{tickets:Ticket[];expandedId:number|null}){
  return <table><tbody>
    {tickets.map(t=><Fragment key={t.id}>
      <tr><td>{t.id}</td><td>{t.title}</td></tr>
      {expandedId===t.id&&<tr><td colSpan={2}>{t.description}</td></tr>}
    </Fragment>)}
  </tbody></table>;
}`;
const editor=String.raw`import {useState} from "react";
function NoteEditor({ticketId}:{ticketId:number}){
  const [draft,setDraft]=useState("");
  return <label>Notatka do #{ticketId}<textarea value={draft} onChange={e=>setDraft(e.target.value)}/></label>;
}
// Inny klucz = inny element = świeży stan szkicu przy zmianie ticketu.
export function EditorHost({ticketId}:{ticketId:number}){
  return <NoteEditor key={ticketId} ticketId={ticketId}/>;
}`;
const mountLog=String.raw`import {useEffect} from "react";
export const events:string[]=[];
// Komponent zapisuje montowanie (+) i odmontowanie (-) — widać, kiedy React buduje poddrzewo od nowa.
export function Probe({name}:{name:string}){
  useEffect(()=>{events.push("+"+name);return ()=>{events.push("-"+name);};},[]);
  return <i>{name}</i>;
}`;
const reorderTests=String.raw`import {render,screen,within} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test} from "vitest";
import {ReorderDemo} from "./ReorderDemo";
import {sampleTickets} from "./types";
async function prepare(useIndex=false){
  const user=userEvent.setup();
  render(<ReorderDemo tickets={sampleTickets}/>);
  if(useIndex)await user.click(screen.getByRole("checkbox",{name:"key = index"}));
  const rows=screen.getAllByRole("listitem");
  await user.click(within(rows[0]).getByRole("button"));
  await user.type(screen.getByLabelText("Notatka 41"),"pilne");
  return {user,firstLi:rows[0]};
}
test("z kluczem id licznik, notatka i węzeł DOM jadą razem z ticketem",async()=>{
  const {user,firstLi}=await prepare();
  await user.click(screen.getByRole("button",{name:"Odwróć kolejność"}));
  const rows=screen.getAllByRole("listitem");
  expect(rows[2]).toBe(firstLi);
  expect(rows[2]).toHaveTextContent("#41 Logowanie");
  expect(within(rows[2]).getByRole("button")).toHaveTextContent("kliknięto 1");
  expect(screen.getByLabelText("Notatka 41")).toHaveValue("pilne");
});
test("z kluczem index stan zostaje na pozycji i przechodzi na inny ticket",async()=>{
  const {user,firstLi}=await prepare(true);
  await user.click(screen.getByRole("button",{name:"Odwróć kolejność"}));
  const rows=screen.getAllByRole("listitem");
  expect(rows[0]).toBe(firstLi);
  expect(rows[0]).toHaveTextContent("#43 Eksport CSV");
  expect(within(rows[0]).getByRole("button")).toHaveTextContent("kliknięto 1");
  expect(within(rows[0]).getByRole("textbox")).toHaveValue("pilne");
});
test("zmiana sposobu kluczowania (id → index) montuje wszystkie wiersze od nowa",async()=>{
  const {user,firstLi}=await prepare();
  await user.click(screen.getByRole("checkbox",{name:"key = index"}));
  const rows=screen.getAllByRole("listitem");
  expect(rows[0]).not.toBe(firstLi);
  expect(within(rows[0]).getByRole("button")).toHaveTextContent("kliknięto 0");
  expect(screen.getByLabelText("Notatka 41")).toHaveValue("");
});`;
const typeTests=String.raw`import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test} from "vitest";
import {TypeDemo} from "./TypeDemo";
test("ten sam typ na tej samej pozycji zachowuje licznik, inny typ go resetuje",async()=>{
  const user=userEvent.setup();
  render(<TypeDemo/>);
  await user.click(screen.getByRole("button",{name:"Agent: 0"}));
  await user.click(screen.getByRole("button",{name:"B-agent: 0"}));
  await user.click(screen.getByRole("checkbox",{name:"tryb admina"}));
  expect(screen.getByRole("button",{name:"Admin: 1"})).toBeInTheDocument();
  expect(screen.getByRole("button",{name:"B-admin: 0"})).toBeInTheDocument();
});`;
const structureTests=String.raw`import {act,useState,type ReactNode} from "react";
import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {beforeEach,expect,test} from "vitest";
import {EditorHost} from "./EditorHost";
import {events,Probe} from "./mountLog";
import {TicketRowsWithDetails} from "./TicketRowsWithDetails";
import {sampleTickets} from "./types";
beforeEach(()=>{events.length=0;});
test("zmiana opakowania div → section odmontowuje całe poddrzewo",()=>{
  function Wrapper({section,children}:{section:boolean;children:ReactNode}){return section?<section>{children}</section>:<div>{children}</div>;}
  const view=render(<Wrapper section={false}><Probe name="tabela"/></Wrapper>);
  view.rerender(<Wrapper section><Probe name="tabela"/></Wrapper>);
  expect(events).toEqual(["+tabela","-tabela","+tabela"]);
});
test("komponent zdefiniowany w komponencie jest nowym typem przy każdym renderze",()=>{
  function Parent({tick}:{tick:number}){
    function Inner(){return <Probe name="wewnętrzny"/>;}
    return <div data-tick={tick}><Inner/></div>;
  }
  const view=render(<Parent tick={1}/>);
  view.rerender(<Parent tick={2}/>);
  view.rerender(<Parent tick={3}/>);
  expect(events.filter(e=>e==="+wewnętrzny")).toHaveLength(3);
});
test("losowy klucz montuje element od nowa przy każdym renderze",()=>{
  let seed=0;
  function List({tick}:{tick:number}){return <ul data-tick={tick}>{["a"].map(x=><li key={x+(seed++)}><Probe name={x}/></li>)}</ul>;}
  const view=render(<List tick={1}/>);
  view.rerender(<List tick={2}/>);
  expect(events).toEqual(["+a","-a","+a"]);
});
test("zmiana klucza resetuje szkic notatki, ten sam klucz go zachowuje",async()=>{
  const user=userEvent.setup();
  const view=render(<EditorHost ticketId={41}/>);
  await user.type(screen.getByRole("textbox"),"oddzwonić");
  view.rerender(<EditorHost ticketId={41}/>);
  expect(screen.getByRole("textbox")).toHaveValue("oddzwonić");
  view.rerender(<EditorHost ticketId={42}/>);
  expect(screen.getByRole("textbox")).toHaveValue("");
  expect(screen.getByText(/Notatka do #42/)).toBeInTheDocument();
});
test("rodzeństwo po warunku {flaga && <A/>} zachowuje pozycję i stan",async()=>{
  const user=userEvent.setup();
  function Host(){const [show,setShow]=useState(false);return <div>
    <button type="button" onClick={()=>setShow(s=>!s)}>Przełącz</button>
    {show&&<Probe name="baner"/>}
    <Counter/>
  </div>;}
  function Counter(){const [c,setC]=useState(0);return <button type="button" onClick={()=>setC(x=>x+1)}>licznik {c}</button>;}
  render(<Host/>);
  await user.click(screen.getByRole("button",{name:"licznik 0"}));
  await user.click(screen.getByRole("button",{name:"Przełącz"}));
  expect(screen.getByText("baner")).toBeInTheDocument();
  expect(screen.getByRole("button",{name:"licznik 1"})).toBeInTheDocument();
});
test("Fragment z kluczem przenosi oba wiersze grupy",()=>{
  const view=render(<TicketRowsWithDetails tickets={sampleTickets} expandedId={41}/>);
  const detailsRow=screen.getByText("SSO nie przekierowuje").closest("tr");
  view.rerender(<TicketRowsWithDetails tickets={[...sampleTickets].reverse()} expandedId={41}/>);
  expect(screen.getByText("SSO nie przekierowuje").closest("tr")).toBe(detailsRow);
  expect(screen.getAllByRole("row").map(r=>r.textContent)).toEqual(["43Eksport CSV","42API timeout","41Logowanie","SSO nie przekierowuje"]);
});`;
const header=String.raw`import {Fragment,act,useEffect,useState,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
const log:string[]=[];
function Probe({name}:{name:string}){useEffect(()=>{log.push("+"+name);return ()=>{log.push("-"+name);};},[]);return <i>{name}</i>;}
function Counter({label}:{label:string}){const [n,setN]=useState(0);return <button type="button" onClick={()=>setN(x=>x+1)}>{label}: {n}</button>;}
function Other({label}:{label:string}){const [n,setN]=useState(0);return <button type="button" onClick={()=>setN(x=>x+1)}>{label}: {n}</button>;}
function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);act(()=>root.render(ui));return {host,update:(next:ReactNode)=>act(()=>root.render(next))};}
function click(host:HTMLElement,index=0){act(()=>{host.querySelectorAll("button")[index].click();});}
void Fragment;void Probe;void Counter;void Other;void click;void log;
`;
const exercises=[
  ['Zbuduj ReorderDemo z licznikiem i niekontrolowaną notatką w każdym wierszu oraz przełącznikiem key = index.','Licznik to stan Reacta, notatka — stan DOM (defaultValue).',reorder],
  ['Udowodnij testem, że z kluczem id węzeł <li>, licznik i notatka przechodzą razem z ticketem po odwróceniu.','Porównaj referencję węzła przed i po (toBe).',reorderTests],
  ['Udowodnij testem, że z kluczem index stan zostaje na pozycji i „przeskakuje” na inny ticket.','Pierwszy węzeł <li> zostaje pierwszy, ale ma nowy tytuł.',reorderTests],
  ['Zbuduj TypeDemo: jedna para przełącza ten sam typ, druga — różne typy.','Przy różnych typach React niszczy poddrzewo.',typeDemo],
  ['Sprawdź testem, że Counter→Counter zachowuje stan, a Counter→OtherCounter go resetuje.','Szukaj przycisków po nazwie z wartością licznika.',typeTests],
  ['Napisz komponent zapisujący montowanie i odmontowanie w tablicy zdarzeń.','useEffect z pustą tablicą i funkcją sprzątającą.',mountLog],
  ['Pokaż, że zmiana opakowania div → section odmontowuje dzieci.','Inny typ elementu na tej samej pozycji.',structureTests],
  ['Pokaż, że komponent zdefiniowany wewnątrz komponentu montuje się przy każdym renderze rodzica.','Nowa funkcja = nowy typ.',structureTests],
  ['Pokaż, że losowy klucz generowany w renderze montuje element od nowa.','Klucz musi być stabilny między renderami.',structureTests],
  ['Zresetuj szkic notatki przy zmianie ticketu przez key={ticketId}.','Ten sam klucz zachowuje stan, inny — resetuje.',editor],
  ['Sprawdź reset szkicu testem z rerender dla dwóch ID.','Najpierw ten sam ID (stan zostaje), potem inny (reset).',structureTests],
  ['Sprawdź, że warunkowy baner przed licznikiem nie resetuje licznika.','{flaga && <A/>} zostawia „dziurę” na tej samej pozycji.',structureTests],
  ['Zbuduj listę grup wiersz + szczegóły z kluczem na Fragment.','Skrócony zapis <> nie przyjmuje klucza.',groups],
  ['Udowodnij, że wiersz szczegółów (ten sam węzeł) przechodzi z grupą po odwróceniu.','Porównaj referencję <tr> przed i po.',structureTests],
  ['Zdefiniuj typ Ticket i przykładowe dane testowe.','Stałe ID są źródłem kluczy.',types],
  ['Napraw komponent zdefiniowany w komponencie, przenosząc go na poziom modułu.','Typ musi być tą samą funkcją przy każdym renderze.',String.raw`import {useState} from "react";
function Inner({label}:{label:string}){
  const [open,setOpen]=useState(false);
  return <button type="button" onClick={()=>setOpen(o=>!o)}>{label} {open?"▲":"▼"}</button>;
}
export function Parent({labels}:{labels:string[]}){
  return <div>{labels.map(label=><Inner key={label} label={label}/>)}</div>;
}`],
  ['Zamień warunkową zmianę typu opakowania na stały element z warunkowym atrybutem.','Ten sam typ zachowuje poddrzewo.',String.raw`import type {ReactNode} from "react";
export function Panel({highlighted,children}:{highlighted:boolean;children:ReactNode}){
  // Zamiast highlighted ? <section> : <div> — jeden typ, zmienna klasa.
  return <section className={highlighted?"panel panel-highlight":"panel"}>{children}</section>;
}`],
  ['Zastąp klucz z Math.random() kluczem z danych.','Klucz z ID rekordu jest stabilny i unikalny wśród rodzeństwa.',String.raw`import type {Ticket} from "./types";
export function TicketList({tickets}:{tickets:Ticket[]}){
  return <ul>{tickets.map(t=><li key={t.id}>{t.title}</li>)}</ul>;
}`],
  ['Zachowaj stan formularza przy przełączaniu wariantu przez ten sam typ z innym props.','{a ? <X variant="a"/> : <X variant="b"/>} zachowuje stan.',String.raw`import {useState} from "react";
function Filters({variant}:{variant:"simple"|"advanced"}){
  const [q,setQ]=useState("");
  return <label>{variant==="simple"?"Szukaj":"Szukaj (zaawansowane)"}<input value={q} onChange={e=>setQ(e.target.value)}/></label>;
}
export function FiltersPanel(){
  const [advanced,setAdvanced]=useState(false);
  return <div>
    <button type="button" onClick={()=>setAdvanced(a=>!a)}>Tryb</button>
    {advanced?<Filters variant="advanced"/>:<Filters variant="simple"/>}
  </div>;
}`],
  ['Wymuś świadomy reset wariantu zaawansowanego przez inny klucz.','Różne klucze na tej samej pozycji = różne elementy.',String.raw`import {useState} from "react";
function Filters({variant}:{variant:"simple"|"advanced"}){
  const [q,setQ]=useState("");
  return <label>{variant}<input value={q} onChange={e=>setQ(e.target.value)}/></label>;
}
export function FiltersPanelWithReset(){
  const [advanced,setAdvanced]=useState(false);
  const variant=advanced?"advanced":"simple";
  return <div>
    <button type="button" onClick={()=>setAdvanced(a=>!a)}>Tryb</button>
    <Filters key={variant} variant={variant}/>
  </div>;
}`],
  ['Wyjaśnij w komentarzu, dlaczego porównanie dowolnych drzew jest kosztowne, a rekoncyliacja liniowa.','Heurystyki: typ i klucz.',String.raw`// Optymalne porównanie dwóch dowolnych drzew ma złożoność rzędu n^3 — za drogo dla UI.
// React zakłada: (1) inny typ na tej samej pozycji = inne poddrzewo (nie porównuje dzieci),
// (2) dzieci listy identyfikuje key (bez klucza — pozycja). Dzięki temu przechodzi drzewo raz: O(n).
// Ceną są reguły dla programisty: stabilne klucze, stały typ opakowania, komponenty na poziomie modułu.
export {};`],
  ['Wypisz liczbę operacji DOM przy usunięciu pierwszego elementu z kluczami i bez.','Bez kluczy: aktualizacje pozycji i usunięcie ostatniego.',String.raw`// Lista [#41, #42, #43], usuwamy #41.
// Bez kluczy (pozycje): pozycja 0: zmień tekst 41→42, pozycja 1: 42→43, pozycja 2: usuń → 2 aktualizacje + 1 usunięcie,
//   stan wiersza z pozycji 0 przechodzi na #42.
// Z kluczami (ID): klucz 41 zniknął → usuń jeden <li>; #42 i #43 zostają bez zmian razem ze stanem.
export {};`],
  ['Sprawdź testem, że rerender z tym samym typem i nowymi props aktualizuje tekst bez montowania.','Log montowania nie zmienia się.',String.raw`import {useEffect} from "react";
import {render,screen} from "@testing-library/react";
import {expect,test} from "vitest";
const events:string[]=[];
function Badge({status}:{status:string}){useEffect(()=>{events.push("mount");return ()=>{events.push("unmount");};},[]);return <span>{status}</span>;}
test("ten sam typ, nowe props",()=>{
  const view=render(<Badge status="OPEN"/>);
  view.rerender(<Badge status="CLOSED"/>);
  expect(screen.getByText("CLOSED")).toBeInTheDocument();
  expect(events).toEqual(["mount"]);
});`],
  ['Sprawdź, że zmiana atrybutu tagu HTML aktualizuje ten sam węzeł DOM.','Ten sam typ HTML na tej samej pozycji — aktualizacja atrybutów.',String.raw`import {render,screen} from "@testing-library/react";
import {expect,test} from "vitest";
test("ten sam węzeł, nowa klasa",()=>{
  const view=render(<button type="button" className="a">Zapisz</button>);
  const node=screen.getByRole("button");
  view.rerender(<button type="button" className="b">Zapisz</button>);
  expect(screen.getByRole("button")).toBe(node);
  expect(node).toHaveClass("b");
});`],
  ['Pokaż ostrzeżenie Reacta o zduplikowanym kluczu w teście.','Szpieg na console.error zbiera komunikaty.',String.raw`import {render} from "@testing-library/react";
import {expect,test,vi} from "vitest";
test("zduplikowany klucz",()=>{
  const spy=vi.spyOn(console,"error").mockImplementation(()=>{});
  render(<ul>{[1,1].map(id=><li key={id}>{id}</li>)}</ul>);
  expect(spy.mock.calls.some(call=>String(call[0]).includes("same key"))).toBe(true);
  spy.mockRestore();
});`],
  ['Pokaż ostrzeżenie o braku klucza w liście.','Ostrzeżenie dotyczy elementów z tablicy bez key.',String.raw`import {render} from "@testing-library/react";
import {expect,test,vi} from "vitest";
test("brak klucza",()=>{
  const spy=vi.spyOn(console,"error").mockImplementation(()=>{});
  render(<ul>{["a","b"].map(x=><li>{x}</li>)}</ul>);
  expect(spy.mock.calls.some(call=>String(call[0]).includes("unique \"key\""))).toBe(true);
  spy.mockRestore();
});`],
  ['Włącz regułę ESLint react/no-array-index-key.','Typy nie pilnują stabilności kluczy.',String.raw`// eslint.config.js
import react from "eslint-plugin-react";
export default [{plugins:{react},rules:{"react/no-array-index-key":"warn","react/jsx-key":"error"}}];`],
  ['Wyjaśnij, kiedy klucz z indeksu jest akceptowalny.','Lista statyczna: bez sortowania, filtrowania, wstawiania i stanu w wierszach.',String.raw`// key={index} jest bezpieczny tylko, gdy lista:
// - nigdy nie zmienia kolejności (brak sortowania i wstawiania na początek/środek),
// - nie jest filtrowana,
// - jej elementy nie mają stanu (ani Reacta, ani DOM: pola, fokus).
// Przykład: statyczna lista kroków instrukcji. W DevDesk wszystkie listy rekordów używają ID.
export {};`],
  ['Zbuduj listę, w której klucz łączy typ rekordu i ID, gdy ID mogą się powtarzać między typami.','Klucz musi być unikalny wśród rodzeństwa.',String.raw`type Item={kind:"ticket"|"comment";id:number;text:string};
export function Feed({items}:{items:Item[]}){
  return <ul>{items.map(item=><li key={item.kind+"-"+item.id}>{item.text}</li>)}</ul>;
}`],
  ['Opisz, które reguły z wcześniejszych rozdziałów wynikają z dwóch heurystyk.','Stabilne klucze, reset przez key, stały typ opakowania, komponenty na poziomie modułu.',String.raw`// Heurystyka 1 (typ): nie zmieniaj typu opakowania warunkowo; nie definiuj komponentów w komponentach;
//   {a ? <X/> : <Y/>} resetuje stan, {a ? <X v="a"/> : <X v="b"/>} — zachowuje.
// Heurystyka 2 (klucz): stabilne klucze z ID; key={id} do świadomego resetu formularza;
//   Fragment z kluczem dla grup wierszy; nigdy klucz losowy w renderze.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Czym jest rekoncyliacja?','Porównaniem nowego drzewa elementów z poprzednim, by wyliczyć zmiany','Malowaniem DOM','Pobieraniem danych','Kompilacją JSX','Wynik trafia do fazy commit.');
q('Jaka jest złożoność ogólnego, optymalnego porównania drzew?','Rzędu n³','O(1)','O(log n)','O(n)','Dlatego React używa heurystyk.');
q('Jaką złożoność daje React dzięki heurystykom?','Liniową','Kwadratową','Sześcienną','Wykładniczą','Jedno przejście przez drzewo.');
q('Co robi React, gdy na tej samej pozycji zmienia się typ elementu?','Niszczy stare poddrzewo i buduje nowe od zera','Porównuje dzieci','Zachowuje stan','Zmienia tylko atrybuty','Pierwsza heurystyka.');
q('Co dzieje się ze stanem przy zmianie typu?','Jest tracony, bo poddrzewo jest odmontowane','Przechodzi do nowego komponentu','Zostaje w DOM','Jest zapisywany w localStorage','Odmontowanie usuwa fibery ze stanem.');
q('Co robi React dla tego samego typu na tej samej pozycji?','Aktualizuje: nowe props, zachowany stan, rekurencja do dzieci','Zawsze montuje od nowa','Pomija komponent','Usuwa DOM','To najczęstszy przypadek.');
q('Po czym React dopasowuje dzieci listy z kluczami?','Po key','Po pozycji','Po treści','Po typie DOM','Druga heurystyka.');
q('Po czym React dopasowuje dzieci bez kluczy?','Po pozycji','Po treści','Losowo','Po ID z props','Bez klucza liczy się indeks.');
q('Co się dzieje z elementem o tym samym kluczu, który zmienił pozycję?','Jest przenoszony razem ze stanem i węzłem DOM','Jest montowany od nowa','Traci stan','Jest kopiowany','Klucz to tożsamość.');
q('Co się stanie ze stanem wiersza przy key={index} i odwróceniu listy?','Zostanie na pozycji i „przejdzie” na inny rekord','Pojedzie za rekordem','Zostanie usunięty','Będzie zduplikowany','Klucz pozycyjny wiąże stan z miejscem.');
q('Dlaczego niekontrolowane pole (defaultValue) „przeskakuje” przy key={index}?','Węzeł DOM zostaje na pozycji, a zmienia się tylko tekst etykiety','Bo React kopiuje wartość','Bo przeglądarka sortuje pola','Bo defaultValue jest stanem Reacta','Stan DOM też idzie za kluczem.');
q('Ile operacji wykona React po usunięciu pierwszego z trzech elementów z kluczami ID?','Jedno usunięcie','Dwie aktualizacje i jedno usunięcie','Trzy aktualizacje','Przebudowę listy','Pozostałe elementy są dopasowane po kluczu.');
q('Ile operacji wykona React po usunięciu pierwszego z trzech elementów bez kluczy?','Dwie aktualizacje treści i jedno usunięcie','Jedno usunięcie','Nic','Trzy usunięcia','Pozycje przesuwają treść.');
q('Co robi key={Math.random()}?','Montuje element od nowa przy każdym renderze','Stabilizuje listę','Przyspiesza render','Nic','Każdy render to „nowy” element.');
q('Jakie skutki ma losowy klucz?','Utrata stanu, fokusu, pozycji przewinięcia i dużo pracy DOM','Lepsza wydajność','Mniej renderów','Brak ostrzeżeń','Klucz ma być stabilny.');
q('Skąd brać klucz?','Ze stabilnego ID danych','Z Math.random()','Z Date.now()','Z indeksu zawsze','Klucz musi być stabilny i unikalny wśród rodzeństwa.');
q('Czy klucz musi być unikalny globalnie?','Nie, tylko wśród rodzeństwa','Tak, w całej aplikacji','Tak, w całym komponencie','Tylko w produkcji','Dwie listy mogą mieć te same klucze.');
q('Dlaczego komponent zdefiniowany w komponencie traci stan?','Każdy render tworzy nową funkcję, czyli nowy typ','Bo jest zamknięty w domknięciu','Bo nie ma klucza','Bo hooki są zabronione','Inny typ → przebudowa poddrzewa.');
q('Co się stanie przy {a ? <X/> : <Y/>} po przełączeniu a?','Stan zostanie zresetowany (różne typy)','Stan zostanie zachowany','React rzuci błąd','Nic się nie zmieni','Różne typy na tej samej pozycji.');
q('Co się stanie przy {a ? <X v="a"/> : <X v="b"/>}?','Stan zostanie zachowany (ten sam typ)','Stan zostanie zresetowany','React zgłosi ostrzeżenie','X zamontuje się dwa razy','Zmieniają się tylko props.');
q('Jak świadomie zresetować stan komponentu przy zmianie rekordu?','Nadać mu key zależny od ID rekordu','Wywołać reset w efekcie','Zmienić nazwę komponentu','Odświeżyć stronę','Inny klucz = nowy element.');
q('Dlaczego zmiana div → section w opakowaniu jest niebezpieczna?','Odmontowuje całe poddrzewo ze stanem','Psuje CSS','Zmienia kolejność dzieci','Nic nie zmienia','Inny typ HTML na tej samej pozycji.');
q('Jak zmienić wygląd opakowania bez utraty stanu?','Zachować ten sam typ i zmieniać atrybuty, np. klasę','Zmienić tag','Użyć innego komponentu','Dodać losowy klucz','Ten sam typ → aktualizacja.');
q('Dlaczego {show && <Baner/>} przed <Licznik/> nie resetuje licznika?','false zajmuje tę samą pozycję w tablicy dzieci, więc licznik nie zmienia pozycji','Bo licznik ma klucz','Bo baner jest Fragmentem','Bo React ignoruje warunki','Dziura w tablicy dzieci utrzymuje pozycje.');
q('Gdzie umieścić klucz, gdy element listy to dwa sąsiednie wiersze?','Na Fragment opakowującym grupę','Na pierwszym <tr>','Na obu <tr> ten sam','Na <tbody>','Skrócony zapis <> nie przyjmuje klucza.');
q('Czy <></> przyjmuje atrybut key?','Nie, potrzebny jest <Fragment key>','Tak','Tylko w TSX','Tylko w React 19','Pełny zapis Fragment przyjmuje key.');
q('Czy TypeScript wykryje key={index} w sortowanej liście?','Nie, to poprawne typowo','Tak, błąd kompilacji','Tak, ostrzeżenie TS','Tylko w trybie strict','Pilnują tego ostrzeżenia Reacta i ESLint.');
q('Która reguła ESLint ostrzega przed kluczem z indeksu?','react/no-array-index-key','react/jsx-key','react-hooks/rules-of-hooks','no-unused-vars','react/jsx-key pilnuje braku klucza.');
q('Co mówi ostrzeżenie Reacta „Each child in a list should have a unique key”?','Elementy z tablicy nie mają klucza','Klucz jest za długi','Lista jest pusta','Klucze są liczbami','To ostrzeżenie w trybie deweloperskim.');
q('Co robi React przy zduplikowanych kluczach?','Ostrzega; zachowanie listy może być błędne','Rzuca wyjątek w produkcji','Automatycznie zmienia klucze','Nic','Duplikaty łamią tożsamość elementów.');
q('Kiedy klucz z indeksu jest akceptowalny?','Dla statycznej listy bez zmian kolejności, filtrowania i stanu w wierszach','Zawsze','Nigdy','Dla list dłuższych niż 100','To wyjątek, nie reguła.');
q('Jak zbudować klucz, gdy ID powtarzają się między typami rekordów?','Połączyć typ i ID, np. "ticket-42"','Użyć indeksu','Użyć Math.random()','Pominąć klucz','Klucz musi być unikalny wśród rodzeństwa.');
q('Czy React porównuje dzieci elementów różnych typów?','Nie','Tak, zawsze','Tylko z kluczami','Tylko dla komponentów','To istota pierwszej heurystyki.');
q('Co jest „pozycją” elementu w drzewie?','Miejsce w tablicy dzieci rodzica','Kolejność w pliku','Wartość z-index','Kolejność importów','Pozycja i typ wyznaczają tożsamość bez klucza.');
q('Czy zmiana props elementu HTML tworzy nowy węzeł DOM?','Nie, aktualizuje atrybuty istniejącego węzła','Tak, zawsze','Tylko dla klasy','Tylko dla style','Ten sam typ → ten sam węzeł.');
q('Czy React przy przeniesieniu elementu z kluczem tworzy nowy węzeł DOM?','Nie, przenosi istniejący','Tak','Tylko przy odwróceniu listy','Tylko w StrictMode','Węzeł zachowuje fokus i wartość pola.');
q('Co pokazuje podświetlenie węzłów w zakładce Elements przeglądarki przy eksperymencie?','Które węzły zostały zmienione lub dodane','Rendery komponentów','Stan Reacta','Klucze','Z kluczami ID przy odwróceniu węzły są przenoszone.');
q('Dlaczego stan nie żyje w elemencie, tylko we fiberze?','Elementy są tworzone od nowa przy każdym renderze','Bo elementy są zamrożone','Bo DOM przechowuje stan','Bo props są niezmienne','Fibery są trwałe między renderami.');
q('Jak rekoncyliacja traktuje null i false wśród dzieci?','Jako puste miejsca zachowujące pozycje','Usuwa je i przesuwa pozycje','Jako błąd','Jako tekst','Dzięki temu warunkowe dzieci nie przesuwają rodzeństwa.');
q('Co się stanie, gdy w liście zmienisz kolejność bez kluczy, a wiersze mają fokus?','Fokus zostanie na pozycji, przy innym rekordzie','Pojedzie za rekordem','Zniknie','Przejdzie na body','Węzły są dopasowane po pozycji.');
q('Co jest wspólną przyczyną problemów z key={index} i komponentem w komponencie?','Błędna tożsamość elementów w rekoncyliacji','Brak memoizacji','Zbyt wiele efektów','Brak TypeScriptu','Oba zaburzają dopasowanie fiberów.');
q('Co oznacza „ten sam element” dla Reacta?','Ten sam typ i klucz (lub pozycja) w tym samym rodzicu','Te same props','Ta sama treść','Ten sam obiekt JS','Props mogą się różnić — to aktualizacja.');
q('Jaki skutek ma przeniesienie elementu do innego rodzica?','Odmontowanie i zamontowanie od nowa (klucze działają tylko wśród rodzeństwa)','Przeniesienie ze stanem','Nic','Błąd','Dopasowanie po kluczu nie działa między rodzicami.');
q('Dlaczego reset przez key jest lepszy niż efekt resetujący stan?','Nie ma renderu ze starym stanem i kodu synchronizacji','Bo efekty są zakazane','Bo key jest szybszy w DOM','Bo efekt nie widzi stanu','Rozdział 07: „You might not need an effect”.');
q('Czy rekoncyliacja wywołuje komponenty potomne bez zmian props?','Tak, domyślnie renderuje całe poddrzewo rodzica','Nie, nigdy','Tylko z kluczami','Tylko w StrictMode','Pomijanie to zadanie memo (rozdział 16).');
q('Co sprawia, że rekoncyliacja jest przewidywalna dla programisty?','Proste reguły: typ, pozycja, klucz','Sztuczna inteligencja','Losowość','Analiza CSS','Znając reguły, przewidzisz zachowanie stanu.');
q('Co trzeba zmienić, aby lista TicketRowsWithDetails działała po sortowaniu?','Nic, Fragment ma klucz z ID','Dodać klucz do <tbody>','Użyć indeksu','Usunąć Fragment','Grupa przesuwa się jako całość.');
q('Czy dwa komponenty o identycznym kodzie, ale różnych funkcjach, to ten sam typ?','Nie, typ to referencja do funkcji','Tak, liczy się kod','Tak, jeśli mają tę samą nazwę','Tylko w JSX','Counter i OtherCounter to różne typy.');
q('Co wydarzy się z licznikiem po przełączeniu Counter → OtherCounter i z powrotem?','Za każdym razem licznik startuje od 0','Wróci poprzednia wartość','Licznik się zsumuje','React zapamięta oba stany','Odmontowanie kasuje stan na stałe.');
q('Czy key wpływa na props komponentu?','Nie, key nie trafia do props','Tak, jest w props.key','Tak, zastępuje id','Tak, w TSX','Klucz należy do elementu.');
q('Dlaczego React nie szuka przeniesionych elementów bez kluczy?','Wymagałoby to kosztownego porównania; bez klucza tożsamością jest pozycja','Bo elementy bez kluczy są zakazane','Bo DOM na to nie pozwala','Bo robi to przeglądarka','Klucz to tania wskazówka od programisty.');
q('Co traci użytkownik, gdy lista montuje się od nowa przy każdym renderze?','Fokus, wpisany tekst, pozycję przewinięcia i stan wierszy','Tylko style','Nic','Tylko dane z serwera','Odmontowanie usuwa węzły DOM i fibery.');
q('Jak sprawdzić w teście, że węzeł DOM został przeniesiony, a nie utworzony?','Zapamiętać referencję i porównać toBe po zmianie','Porównać textContent','Sprawdzić klasę','Policzyć elementy','Tożsamość obiektu węzła to dowód przeniesienia.');
q('Jak w teście wykryć montowanie i odmontowanie komponentu?','Efektem z funkcją sprzątającą zapisującym zdarzenia','Przez snapshot','Przez console.log w renderze','Przez CSS','Efekt uruchamia się przy montowaniu, sprzątanie przy odmontowaniu.');
q('Dlaczego log w renderze nie wystarczy do wykrycia montowania?','Render wykonuje się też przy aktualizacjach i może się powtarzać','Bo console.log jest wyłączony','Bo render nie istnieje','Bo React go ukrywa','Montowanie to co innego niż render.');
q('Co jest „rodzeństwem” w sensie kluczy?','Dzieci tego samego rodzica w tej samej tablicy','Wszystkie elementy strony','Komponenty z tego samego pliku','Elementy z tym samym typem','Unikalność klucza dotyczy rodzeństwa.');
q('Czy można użyć tego samego ID jako klucza w dwóch różnych listach?','Tak, klucze są lokalne dla rodzica','Nie','Tylko z prefiksem','Tylko w produkcji','Każda lista ma własną przestrzeń kluczy.');
q('Dlaczego sortowanie listy z kluczami ID nie resetuje stanu wierszy?','Klucze dopasowują te same elementy na nowych pozycjach','Bo sortowanie nie zmienia tablicy','Bo stan jest w DOM','Bo React kopiuje stan','Elementy są przenoszone.');
q('Co się dzieje z efektami przeniesionego elementu z kluczem?','Nie są uruchamiane ponownie — element nie jest montowany','Uruchamiają się od nowa','Są usuwane','Uruchamiają się dwa razy','Przeniesienie to nie montowanie.');
q('Co to znaczy, że rekoncyliacja jest rekurencyjna?','Dla zaktualizowanego elementu React schodzi do jego dzieci','Że wywołuje się w pętli bez końca','Że działa tylko dla list','Że przetwarza CSS','Każdy poziom drzewa stosuje te same reguły.');
q('Kiedy rekoncyliacja nie schodzi do dzieci?','Gdy typ się zmienił — całe poddrzewo jest budowane od nowa','Nigdy','Zawsze','Gdy dzieci są tekstem','Przy innym typie porównywanie dzieci nie ma sensu.');
q('Co jest typem elementu dla komponentu-klasy?','Klasa komponentu','Nazwa klasy jako tekst','Instancja klasy','Metoda render','Reguły rekoncyliacji są takie same jak dla funkcji.');
q('Czy React.memo zmienia reguły rekoncyliacji typu i klucza?','Nie, tylko pozwala pominąć render przy równych props','Tak, wyłącza klucze','Tak, zachowuje stan przy zmianie typu','Tak, zmienia typ elementu','memo to optymalizacja renderu, nie tożsamości.');
q('Co warto sprawdzić w DevDesk po tej lekcji?','Czy wszystkie listy mają stabilne klucze z ID, a grupy wierszy — klucz na Fragment','Czy wszystkie komponenty mają memo','Czy używane są klasy','Czy listy są posortowane','To praktyczne kryterium akceptacji.');
q('Dlaczego klucz z Date.now() jest błędny?','Zmienia się między renderami, więc element jest montowany od nowa','Bo jest liczbą','Bo jest za długi','Bo jest unikalny','Stabilność jest ważniejsza niż unikalność w czasie.');
q('Czy przeniesienie elementu z kluczem zachowuje fokus w polu?','Tak, bo przenoszony jest ten sam węzeł DOM','Nie, fokus zawsze ginie','Tylko w Chrome','Tylko dla przycisków','To jeden z praktycznych zysków stabilnych kluczy.');
q('Jak nazwać operację „inny klucz na tej samej pozycji”?','Zastąpienie elementu: odmontowanie starego i montowanie nowego','Aktualizacja props','Przeniesienie','Hydratacja','Klucz to część tożsamości.');
const probes=[
  ['Odwrócenie z kluczem id: ten sam węzeł na końcu','true','false','undefined','null','const tickets=[1,2,3];const v=mount(<ul>{tickets.map(id=><li key={id}>{id}</li>)}</ul>);const first=v.host.querySelector("li");v.update(<ul>{[...tickets].reverse().map(id=><li key={id}>{id}</li>)}</ul>);console.log(v.host.querySelectorAll("li")[2]===first);'],
  ['Odwrócenie z kluczem index: tekst pierwszego węzła','3','1','2','pusty','const tickets=[1,2,3];const v=mount(<ul>{tickets.map((id,i)=><li key={i}>{id}</li>)}</ul>);const first=v.host.querySelector("li");v.update(<ul>{[...tickets].reverse().map((id,i)=><li key={i}>{id}</li>)}</ul>);console.log(first?.textContent);'],
  ['Notatka po odwróceniu z kluczem id','3:,2:,1:x','1:x,2:,3:','3:x,2:,1:','x,,','const ids=[1,2,3];const ui=(list:number[])=><div>{list.map(id=><input key={id} data-id={id} defaultValue=""/>)}</div>;const v=mount(ui(ids));(v.host.querySelector("input") as HTMLInputElement).value="x";v.update(ui([...ids].reverse()));console.log([...v.host.querySelectorAll("input")].map(i=>i.dataset.id+":"+i.value).join(","));'],
  ['Notatka po odwróceniu z kluczem index','3:x,2:,1:','3:,2:,1:x','1:x,2:,3:','x,,','const ids=[1,2,3];const ui=(list:number[])=><div>{list.map((id,i)=><input key={i} data-id={id} defaultValue=""/>)}</div>;const v=mount(ui(ids));(v.host.querySelector("input") as HTMLInputElement).value="x";v.update(ui([...ids].reverse()));console.log([...v.host.querySelectorAll("input")].map(i=>i.dataset.id+":"+i.value).join(","));'],
  ['Ten sam typ, inne props: licznik','Admin: 1','Admin: 0','Agent: 1','Agent: 0','const v=mount(<Counter label="Agent"/>);click(v.host);v.update(<Counter label="Admin"/>);console.log(v.host.textContent);'],
  ['Inny typ: licznik','B: 0','B: 1','A: 1','A: 0','const v=mount(<Counter label="A"/>);click(v.host);v.update(<Other label="B"/>);console.log(v.host.textContent);'],
  ['Log przy zmianie typu opakowania','+x,-x,+x','+x','+x,+x','-x,+x','log.length=0;const v=mount(<div><Probe name="x"/></div>);v.update(<section><Probe name="x"/></section>);console.log(log.join(","));'],
  ['Log przy zmianie atrybutu opakowania','+x','+x,-x,+x','-x','pusty','log.length=0;const v=mount(<div className="a"><Probe name="x"/></div>);v.update(<div className="b"><Probe name="x"/></div>);console.log(log.join(",")||"pusty");'],
  ['Zmiana klucza pojedynczego elementu','+a,-a,+a','+a','+a,+a','-a','log.length=0;const v=mount(<Probe key="1" name="a"/>);v.update(<Probe key="2" name="a"/>);console.log(log.join(","));'],
  ['Ten sam klucz przy rerender','+a','+a,-a,+a','-a,+a','pusty','log.length=0;const v=mount(<Probe key="1" name="a"/>);v.update(<Probe key="1" name="a"/>);console.log(log.join(","));'],
  ['Usunięcie środkowego elementu z kluczami','-b','-c','-a,-b,-c','+a,+c','const v=mount(<ul>{["a","b","c"].map(x=><Probe key={x} name={x}/>)}</ul>);log.length=0;v.update(<ul>{["a","c"].map(x=><Probe key={x} name={x}/>)}</ul>);console.log(log.join(","));'],
  ['Usunięcie pierwszego elementu bez kluczy (pozycje)','-c','-a','-a,-b,-c','pusty','const v=mount(<ul>{["a","b","c"].map((x,i)=><Probe key={i} name={x}/>)}</ul>);log.length=0;v.update(<ul>{["b","c"].map((x,i)=><Probe key={i} name={x}/>)}</ul>);console.log(log.join(",")||"pusty");'],
  ['Tekst po usunięciu pierwszego bez kluczy','bc','ab','ac','abc','const v=mount(<ul>{["a","b","c"].map((x,i)=><Probe key={i} name={x}/>)}</ul>);v.update(<ul>{["b","c"].map((x,i)=><Probe key={i} name={x}/>)}</ul>);console.log(v.host.textContent);'],
  ['Dodanie na początek z kluczami','+z','+c','-a,-b,+z,+a,+b','+z,+a,+b','const v=mount(<ul>{["a","b"].map(x=><Probe key={x} name={x}/>)}</ul>);log.length=0;v.update(<ul>{["z","a","b"].map(x=><Probe key={x} name={x}/>)}</ul>);console.log(log.join(","));'],
  ['Dodanie na początek z indeksami','+b','+z','+z,+a,+b','pusty','const v=mount(<ul>{["a","b"].map((x,i)=><Probe key={i} name={x}/>)}</ul>);log.length=0;v.update(<ul>{["z","a","b"].map((x,i)=><Probe key={i} name={x}/>)}</ul>);console.log(log.join(",")||"pusty");'],
  ['Komponent zdefiniowany w komponencie: liczba montowań','3','1','0','2','log.length=0;function Parent({tick}:{tick:number}){function Inner(){return <Probe name="i"/>;}return <div data-tick={tick}><Inner/></div>;}const v=mount(<Parent tick={1}/>);v.update(<Parent tick={2}/>);v.update(<Parent tick={3}/>);console.log(log.filter(x=>x==="+i").length);'],
  ['Komponent na poziomie modułu: liczba montowań','1','3','0','2','log.length=0;function Parent({tick}:{tick:number}){return <div data-tick={tick}><Probe name="m"/></div>;}const v=mount(<Parent tick={1}/>);v.update(<Parent tick={2}/>);v.update(<Parent tick={3}/>);console.log(log.filter(x=>x==="+m").length);'],
  ['Losowy klucz: montowania po trzech renderach','3','1','0','2','log.length=0;let n=0;function L({tick}:{tick:number}){return <ul data-tick={tick}><Probe key={"k"+(n++)} name="r"/></ul>;}const v=mount(<L tick={1}/>);v.update(<L tick={2}/>);v.update(<L tick={3}/>);console.log(log.filter(x=>x==="+r").length);'],
  ['Warunek przed licznikiem: stan licznika','L: 1','L: 0','pusty','błąd','function Host({show}:{show:boolean}){return <div>{show&&<b>baner</b>}<Counter label="L"/></div>;}const v=mount(<Host show={false}/>);click(v.host);v.update(<Host show/>);console.log(v.host.querySelector("button")?.textContent);'],
  ['Zmiana pozycji przez fragment: stan licznika','L: 0','L: 1','pusty','błąd','function Host({wrap}:{wrap:boolean}){return wrap?<div><span><Counter label="L"/></span></div>:<div><Counter label="L"/></div>;}const v=mount(<Host wrap={false}/>);click(v.host);v.update(<Host wrap/>);console.log(v.host.querySelector("button")?.textContent);'],
  ['Ternary ten sam typ na tej samej pozycji','Y: 1','Y: 0','X: 1','X: 0','function Host({x}:{x:boolean}){return <div>{x?<Counter label="X"/>:<Counter label="Y"/>}</div>;}const v=mount(<Host x/>);click(v.host);v.update(<Host x={false}/>);console.log(v.host.textContent);'],
  ['Ternary różne typy','Y: 0','Y: 1','X: 1','X: 0','function Host({x}:{x:boolean}){return <div>{x?<Counter label="X"/>:<Other label="Y"/>}</div>;}const v=mount(<Host x/>);click(v.host);v.update(<Host x={false}/>);console.log(v.host.textContent);'],
  ['Ternary z kluczami na tym samym typie','Y: 0','Y: 1','X: 1','X: 0','function Host({x}:{x:boolean}){return <div>{x?<Counter key="x" label="X"/>:<Counter key="y" label="Y"/>}</div>;}const v=mount(<Host x/>);click(v.host);v.update(<Host x={false}/>);console.log(v.host.textContent);'],
  ['Przeniesienie do innego rodzica','+p,-p,+p','+p','-p','+p,+p','log.length=0;function Host({left}:{left:boolean}){return <div><section>{left&&<Probe key="p" name="p"/>}</section><aside>{!left&&<Probe key="p" name="p"/>}</aside></div>;}const v=mount(<Host left/>);v.update(<Host left={false}/>);console.log(log.join(","));'],
  ['Ten sam węzeł przy zmianie klasy','true','false','undefined','null','const v=mount(<button type="button" className="a">x</button>);const node=v.host.querySelector("button");v.update(<button type="button" className="b">x</button>);console.log(v.host.querySelector("button")===node);'],
  ['Nowy węzeł przy zmianie tagu','false','true','undefined','null','const v=mount(<div><p>x</p></div>);const node=v.host.querySelector("div > *");v.update(<div><span>x</span></div>);console.log(v.host.querySelector("div > *")===node);'],
  ['Fragment z kluczem: kolejność po odwróceniu','2,2b,1,1b','1,1b,2,2b','2,1,2b,1b','1b,1,2b,2','const ui=(ids:number[])=><div>{ids.map(id=><Fragment key={id}><p>{id}</p><p>{id+"b"}</p></Fragment>)}</div>;const v=mount(ui([1,2]));v.update(ui([2,1]));console.log([...v.host.querySelectorAll("p")].map(p=>p.textContent).join(","));'],
  ['Fragment z kluczem: ten sam węzeł szczegółów','true','false','undefined','null','const ui=(ids:number[])=><div>{ids.map(id=><Fragment key={id}><p>{id}</p><p>{id+"b"}</p></Fragment>)}</div>;const v=mount(ui([1,2]));const detail=v.host.querySelectorAll("p")[1];v.update(ui([2,1]));console.log(v.host.querySelectorAll("p")[3]===detail);'],
  ['Liczba odmontowań przy zmianie typu rodzica dwóch dzieci','2','1','0','4','const v=mount(<div><Probe name="a"/><Probe name="b"/></div>);log.length=0;v.update(<section><Probe name="a"/><Probe name="b"/></section>);console.log(log.filter(x=>x.startsWith("-")).length);'],
  ['null jako dziecko nie przesuwa rodzeństwa','pusty','-b,+b','+b','-b','const v=mount(<div>{null}<Probe name="b"/></div>);log.length=0;v.update(<div>{<i>nowy</i>}<Probe name="b"/></div>);console.log(log.join(",")||"pusty");'],
  ['Zamiana miejscami dwóch elementów z kluczami','pusty','-a,-b,+b,+a','-a,+a','-b,+b','const v=mount(<div>{["a","b"].map(x=><Probe key={x} name={x}/>)}</div>);log.length=0;v.update(<div>{["b","a"].map(x=><Probe key={x} name={x}/>)}</div>);console.log(log.join(",")||"pusty");'],
  ['Zamiana miejscami bez kluczy (indeksy): montowania','pusty','-a,-b,+b,+a','+b,+a','-a,+a','const v=mount(<div>{["a","b"].map((x,i)=><Probe key={i} name={x}/>)}</div>);log.length=0;v.update(<div>{["b","a"].map((x,i)=><Probe key={i} name={x}/>)}</div>);console.log(log.join(",")||"pusty");'],
  ['Tekst po zamianie bez kluczy','ba','ab','aa','bb','const v=mount(<div>{["a","b"].map((x,i)=><Probe key={i} name={x}/>)}</div>);v.update(<div>{["b","a"].map((x,i)=><Probe key={i} name={x}/>)}</div>);console.log(v.host.textContent);'],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 (createRoot + act w jsdom); log zapisuje montowanie (+) i odmontowanie (−) z useEffect.',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/types.ts':types,'src/ReorderDemo.tsx':reorder,'src/TypeDemo.tsx':typeDemo,'src/TicketRowsWithDetails.tsx':groups,'src/EditorHost.tsx':editor,'src/mountLog.tsx':mountLog,
  'src/reorder.test.tsx':reorderTests,'src/type.test.tsx':typeTests,'src/structure.test.tsx':structureTests};
const mutants=[
  {file:'src/ReorderDemo.tsx',before:'key={useIndex?i:t.id}',after:'key={i}'},
  {file:'src/TypeDemo.tsx',before:'{admin?<Counter label="Admin"/>:<Counter label="Agent"/>}',after:'{admin?<OtherCounter label="Admin"/>:<Counter label="Agent"/>}'},
  {file:'src/EditorHost.tsx',before:'<NoteEditor key={ticketId} ticketId={ticketId}/>',after:'<NoteEditor ticketId={ticketId}/>'},
  {file:'src/TicketRowsWithDetails.tsx',before:'<Fragment key={t.id}>',after:'<Fragment key={tickets.indexOf(t)}>'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
