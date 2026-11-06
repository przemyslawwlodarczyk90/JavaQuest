// Lekcja 15/06: hooki od środka. Model listy hooków (runtime) i zachowanie prawdziwego Reacta 19 sprawdza Vitest (jsdom).
const deps=String.raw`// Model porównania zależności efektu/memo: Object.is element po elemencie.
export function depsChanged(prev:readonly unknown[]|undefined,next:readonly unknown[]|undefined):boolean{
  if(prev===undefined||next===undefined)return true; // brak tablicy → po każdym renderze
  if(prev.length!==next.length)return true;
  return next.some((value,i)=>!Object.is(value,prev[i]));
}`;
const model=String.raw`// Uproszczony model z teorii: tablica slotów i indeks resetowany przed każdym „renderem”.
export function createHooksModel(){
  const slots:unknown[]=[];
  let index=0;
  function useSimState<T>(initial:T):[T,(v:T)=>void]{
    const i=index++;
    if(slots.length<=i)slots.push(initial); // montowanie
    return [slots[i] as T,(v:T)=>{slots[i]=v;}];
  }
  function render<R>(component:()=>R):R{index=0;return component();}
  return {useSimState,render,slots};
}
export function ticketFormDemo(){
  const {useSimState,render}=createHooksModel();
  function TicketForm(showDescription:boolean){
    const [title]=useSimState("Błąd eksportu");
    const description=showDescription?useSimState("")[0]:null; // ŹLE: hook w warunku
    const [priority]=useSimState<"LOW"|"HIGH">("HIGH");
    return {title,description,priority};
  }
  return [render(()=>TicketForm(true)),render(()=>TicketForm(false))];
}`;
const runtime=String.raw`import {depsChanged} from "./depsChanged";
// Model listy hooków jednego komponentu: węzły w kolejności wywołań, wskaźnik index resetowany przed renderem.
type Action<T>=T|((prev:T)=>T);
type StateHook={kind:"state";value:unknown;queue:((prev:unknown)=>unknown)[];dispatch:(action:unknown)=>void};
type RefHook={kind:"ref";ref:{current:unknown}};
type MemoHook={kind:"memo";value:unknown;deps:readonly unknown[]};
type EffectHook={kind:"effect";deps:readonly unknown[]|undefined;destroy:(()=>void)|undefined};
export type HookNode=StateHook|RefHook|MemoHook|EffectHook;
type Instance={hooks:HookNode[];index:number;mounting:boolean;dirty:boolean;effects:(()=>void)[]};
let current:Instance|null=null;
function nextHook<K extends HookNode["kind"]>(kind:K,create:(inst:Instance)=>Extract<HookNode,{kind:K}>):Extract<HookNode,{kind:K}>{
  const inst=current;
  if(!inst)throw new Error("Hook wywołany poza renderem komponentu");
  const i=inst.index++;
  if(inst.mounting){const node=create(inst);inst.hooks.push(node);return node;}
  const node=inst.hooks[i];
  if(!node)throw new Error("Rendered more hooks than during the previous render.");
  if(node.kind!==kind)throw new Error("Zmiana kolejności hooków na pozycji "+i+": było "+node.kind+", jest "+kind);
  return node as Extract<HookNode,{kind:K}>;
}
export function useState<T>(initial:T|(()=>T)):[T,(action:Action<T>)=>void]{
  const node=nextHook("state",inst=>{
    const hook:StateHook={kind:"state",value:typeof initial==="function"?(initial as ()=>T)():initial,queue:[],dispatch:()=>{}};
    // dispatch tworzony raz przy montowaniu — stała tożsamość między renderami
    hook.dispatch=action=>{
      if(hook.queue.length===0&&typeof action!=="function"&&Object.is(action,hook.value))return; // wczesne wyjście
      hook.queue.push(typeof action==="function"?action as (prev:unknown)=>unknown:()=>action);
      inst.dirty=true;
    };
    return hook;
  });
  for(const update of node.queue.splice(0))node.value=update(node.value); // kolejka po kolei
  return [node.value as T,node.dispatch as (action:Action<T>)=>void];
}
export function useRef<T>(initial:T):{current:T}{
  return nextHook("ref",()=>({kind:"ref",ref:{current:initial}})).ref as {current:T};
}
export function useMemo<T>(compute:()=>T,deps:readonly unknown[]):T{
  const node=nextHook("memo",()=>({kind:"memo",value:compute(),deps}));
  if(depsChanged(node.deps,deps)){node.value=compute();node.deps=deps;}
  return node.value as T;
}
export function useEffect(setup:()=>void|(()=>void),deps?:readonly unknown[]):void{
  const inst=current;
  let fresh=false;
  const node=nextHook("effect",()=>{fresh=true;return {kind:"effect",deps,destroy:undefined};});
  if(fresh||depsChanged(node.deps,deps)){
    node.deps=deps;
    // „commit”: efekt czeka, aż render się skończy; najpierw sprzątanie poprzedniego setupu
    inst?.effects.push(()=>{node.destroy?.();const destroy=setup();node.destroy=typeof destroy==="function"?destroy:undefined;});
  }
}
export type Mounted<P,R>={readonly output:R;readonly renders:number;readonly hooks:readonly HookNode[];update(props:P):void;flush():boolean;unmount():void};
export function mount<P,R>(component:(props:P)=>R,initialProps:NoInfer<P>):Mounted<P,R>{
  const inst:Instance={hooks:[],index:0,mounting:true,dirty:false,effects:[]};
  let props=initialProps,output!:R,renders=0;
  function render(){
    inst.index=0;current=inst;
    try{output=component(props);}finally{current=null;}
    if(!inst.mounting&&inst.index<inst.hooks.length)throw new Error("Rendered fewer hooks than expected. This may be caused by an accidental early return statement.");
    inst.mounting=false;renders+=1;
    for(const run of inst.effects.splice(0))run();
  }
  render();
  return {
    get output(){return output;},
    get renders(){return renders;},
    hooks:inst.hooks,
    update(next){props=next;inst.dirty=false;render();},
    flush(){if(!inst.dirty)return false;inst.dirty=false;render();return true;},
    unmount(){for(const node of inst.hooks)if(node.kind==="effect")node.destroy?.();},
  };
}`;
const runtimeTests=String.raw`import {expect,test,vi} from "vitest";
import {depsChanged} from "./depsChanged";
import {ticketFormDemo} from "./hooksModel";
import {mount,useEffect,useMemo,useRef,useState} from "./hookRuntime";
test("depsChanged porównuje Object.is element po elemencie",()=>{
  expect(depsChanged([1,"OPEN"],[1,"OPEN"])).toBe(false);
  expect(depsChanged([{status:"OPEN"}],[{status:"OPEN"}])).toBe(true);
  expect(depsChanged([NaN],[NaN])).toBe(false);
  expect(depsChanged([0],[-0])).toBe(true);
  expect(depsChanged([1],[1,2])).toBe(true);
  expect(depsChanged(undefined,undefined)).toBe(true);
});
test("model z teorii po cichu zwraca cudzy slot",()=>{
  const [first,second]=ticketFormDemo();
  expect(first).toEqual({title:"Błąd eksportu",description:"",priority:"HIGH"});
  expect(second).toEqual({title:"Błąd eksportu",description:null,priority:""});
});
test("stany rozpoznawane po kolejności, nie po nazwie",()=>{
  let setSecond!:(v:string)=>void;
  const view=mount(()=>{const [a]=useState("pierwszy");const [b,setB]=useState("drugi");setSecond=setB;return a+"|"+b;},undefined);
  setSecond("zmieniony");
  view.flush();
  expect(view.output).toBe("pierwszy|zmieniony");
});
test("kilka setState przed flush daje jeden render",()=>{
  let bump!:()=>void;
  const view=mount(()=>{const [a,setA]=useState(0);const [b,setB]=useState(0);bump=()=>{setA(x=>x+1);setB(x=>x+1);setA(x=>x+1);};return a+b;},undefined);
  bump();
  expect(view.flush()).toBe(true);
  expect(view.renders).toBe(2);
  expect(view.output).toBe(3);
});
test("kolejka przetwarza aktualizacje po kolei",()=>{
  let run!:()=>void;
  const view=mount(()=>{const [n,setN]=useState(0);run=()=>{setN(5);setN(x=>x+1);};return n;},undefined);
  run();
  view.flush();
  expect(view.output).toBe(6);
});
test("ta sama wartość nie planuje renderu",()=>{
  let set!:(v:string)=>void;
  const view=mount(()=>{const [s,setS]=useState("OPEN");set=setS;return s;},undefined);
  set("OPEN");
  expect(view.flush()).toBe(false);
  expect(view.renders).toBe(1);
  set("CLOSED");
  expect(view.flush()).toBe(true);
  expect(view.output).toBe("CLOSED");
});
test("setter i ref są stałe między renderami",()=>{
  const setters:unknown[]=[],refs:{current:number}[]=[];
  const view=mount((n:number)=>{const [,set]=useState(0);const ref=useRef(n);setters.push(set);refs.push(ref);return n;},1);
  view.update(2);
  expect(setters[0]).toBe(setters[1]);
  expect(refs[0]).toBe(refs[1]);
  expect(refs[1].current).toBe(1);
});
test("useMemo liczy ponownie tylko po zmianie zależności",()=>{
  const compute=vi.fn((n:number)=>n*2);
  const view=mount((p:{n:number;tick:number})=>useMemo(()=>compute(p.n),[p.n])+p.tick,{n:1,tick:0});
  view.update({n:1,tick:1});
  expect(compute).toHaveBeenCalledTimes(1);
  view.update({n:2,tick:1});
  expect(compute).toHaveBeenCalledTimes(2);
  expect(view.output).toBe(5);
});
test("efekt: po renderze, Object.is na zależnościach, sprzątanie przed nowym setupem",()=>{
  const log:string[]=[];
  const view=mount((dep:unknown)=>{
    log.push("render");
    useEffect(()=>{log.push("setup "+String(dep));return ()=>{log.push("cleanup "+String(dep));};},[dep]);
    return null;
  },NaN as unknown);
  view.update(NaN);
  view.update("a");
  view.unmount();
  expect(log).toEqual(["render","setup NaN","render","render","cleanup NaN","setup a","cleanup a"]);
});
test("nowy obiekt w zależnościach uruchamia efekt przy każdym renderze",()=>{
  const setup=vi.fn();
  const view=mount((status:string)=>{const filter={status};useEffect(()=>{setup(filter.status);},[filter]);return null;},"OPEN");
  view.update("OPEN");
  view.update("OPEN");
  expect(setup).toHaveBeenCalledTimes(3);
});
test("hook w warunku: mniej hooków niż poprzednio to błąd",()=>{
  const view=mount((on:boolean)=>{useState("a");if(on)useState("b");return on;},true);
  expect(()=>view.update(false)).toThrow(/fewer hooks/);
});
test("hook w warunku: więcej hooków niż poprzednio to błąd",()=>{
  const view=mount((on:boolean)=>{useState("a");if(on)useState("b");return on;},false);
  expect(()=>view.update(true)).toThrow(/more hooks/);
});
test("zmiana rodzaju hooka na pozycji to błąd",()=>{
  const view=mount((first:boolean)=>{if(first){useState(0);}else{useRef(0);}return first;},true);
  expect(()=>view.update(false)).toThrow(/Zmiana kolejności hooków na pozycji 0/);
});
test("hook poza renderem rzuca",()=>{
  expect(()=>useState(0)).toThrow(/poza renderem/);
});
test("własny hook dopisuje węzły do listy komponentu",()=>{
  function useOnlineSim(){const [online]=useState(true);useEffect(()=>{},[]);return online;}
  const view=mount(()=>{const [collapsed]=useState(false);const online=useOnlineSim();return String(collapsed)+String(online);},undefined);
  expect(view.hooks.map(h=>h.kind)).toEqual(["state","state","effect"]);
});`;
const ticketCard=String.raw`import {useState} from "react";
export type Ticket={id:number;title:string;details:string};
// Wszystkie hooki przed pierwszym warunkowym return — lista hooków ma zawsze tę samą długość.
export function TicketCard({ticket}:{ticket:Ticket|null}){
  const [open,setOpen]=useState(false);
  if(!ticket)return <p>Brak zgłoszenia</p>;
  return <article>
    <h2>{ticket.title}</h2>
    <button type="button" aria-expanded={open} onClick={()=>setOpen(o=>!o)}>Szczegóły</button>
    {open&&<p>{ticket.details}</p>}
  </article>;
}`;
const online=String.raw`import {useEffect,useState} from "react";
// Własny hook: jego dwa wywołania hooków trafiają do listy komponentu, który go wywołał.
export function useOnline(){
  const [online,setOnline]=useState(true);
  useEffect(()=>{
    const update=()=>setOnline(navigator.onLine);
    window.addEventListener("online",update);
    window.addEventListener("offline",update);
    return ()=>{window.removeEventListener("online",update);window.removeEventListener("offline",update);};
  },[]);
  return online;
}`;
const statusBar=String.raw`import {useState} from "react";
import {useOnline} from "./useOnline";
export function StatusBar(){
  const [collapsed,setCollapsed]=useState(false); // węzeł 0
  const online=useOnline();                       // węzły 1 i 2 (z wnętrza hooka)
  return <footer>
    <button type="button" onClick={()=>setCollapsed(c=>!c)}>{collapsed?"…":online?"Online":"Offline"}</button>
  </footer>;
}`;
const ticketForm=String.raw`import {useState} from "react";
// Cztery useState bez nazw i kluczy — React rozróżnia je wyłącznie po kolejności wywołań.
export function TicketForm(){
  const [title,setTitle]=useState("Błąd eksportu");
  const [description,setDescription]=useState("");
  const [priority,setPriority]=useState<"LOW"|"HIGH">("HIGH");
  const [assignee,setAssignee]=useState("");
  return <form>
    <input aria-label="Tytuł" value={title} onChange={e=>setTitle(e.target.value)}/>
    <textarea aria-label="Opis" value={description} onChange={e=>setDescription(e.target.value)}/>
    <select aria-label="Priorytet" value={priority} onChange={e=>setPriority(e.target.value==="LOW"?"LOW":"HIGH")}>
      <option value="LOW">LOW</option>
      <option value="HIGH">HIGH</option>
    </select>
    <input aria-label="Osoba" value={assignee} onChange={e=>setAssignee(e.target.value)}/>
  </form>;
}`;
const reactTests=String.raw`import {useState} from "react";
import {act,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test,vi} from "vitest";
import {StatusBar} from "./StatusBar";
import {TicketCard} from "./TicketCard";
import {TicketForm} from "./TicketForm";
function ConditionalHook({on}:{on:boolean}){
  const [title]=useState("Błąd eksportu");
  const description=on?useState("")[0]:null; // celowe złamanie zasad hooków do testu
  return <p>{title}{description}</p>;
}
test("React wykrywa mniej hooków niż w poprzednim renderze",()=>{
  vi.spyOn(console,"error").mockImplementation(()=>{});
  const view=render(<ConditionalHook on/>);
  expect(()=>view.rerender(<ConditionalHook on={false}/>)).toThrow(/Rendered fewer hooks than expected/);
});
test("hooki przed wczesnym return: zgłoszenie może się pojawiać i znikać",async()=>{
  const user=userEvent.setup();
  const view=render(<TicketCard ticket={null}/>);
  expect(screen.getByText("Brak zgłoszenia")).toBeInTheDocument();
  view.rerender(<TicketCard ticket={{id:1,title:"Błąd eksportu",details:"CSV bez nagłówka"}}/>);
  await user.click(screen.getByRole("button",{name:"Szczegóły"}));
  expect(screen.getByText("CSV bez nagłówka")).toBeInTheDocument();
  view.rerender(<TicketCard ticket={null}/>);
  expect(screen.getByText("Brak zgłoszenia")).toBeInTheDocument();
  // Ten sam węzeł useState przetrwał render bez zgłoszenia — szczegóły nadal rozwinięte.
  view.rerender(<TicketCard ticket={{id:1,title:"Błąd eksportu",details:"CSV bez nagłówka"}}/>);
  expect(screen.getByText("CSV bez nagłówka")).toBeInTheDocument();
});
test("cztery stany formularza są niezależne",async()=>{
  const user=userEvent.setup();
  render(<TicketForm/>);
  await user.selectOptions(screen.getByLabelText("Priorytet"),"LOW");
  await user.type(screen.getByLabelText("Osoba"),"Ala");
  expect(screen.getByLabelText("Tytuł")).toHaveValue("Błąd eksportu");
  expect(screen.getByLabelText("Opis")).toHaveValue("");
  expect(screen.getByLabelText("Priorytet")).toHaveValue("LOW");
  expect(screen.getByLabelText("Osoba")).toHaveValue("Ala");
});
test("dwa StatusBar mają niezależny stan, a zdarzenie offline dociera do obu",async()=>{
  const user=userEvent.setup();
  render(<><StatusBar/><StatusBar/></>);
  expect(screen.getAllByText("Online")).toHaveLength(2);
  await user.click(screen.getAllByRole("button")[0]);
  expect(screen.getAllByText("Online")).toHaveLength(1);
  vi.spyOn(navigator,"onLine","get").mockReturnValue(false);
  act(()=>{window.dispatchEvent(new Event("offline"));});
  expect(screen.getAllByText("Offline")).toHaveLength(1);
  expect(screen.getByText("…")).toBeInTheDocument();
});
test("odmontowanie StatusBar usuwa oba nasłuchy",()=>{
  const remove=vi.spyOn(window,"removeEventListener");
  const view=render(<StatusBar/>);
  view.unmount();
  expect(remove).toHaveBeenCalledWith("online",expect.any(Function));
  expect(remove).toHaveBeenCalledWith("offline",expect.any(Function));
});`;
const header=String.raw`import {act,createContext,use,useCallback,useEffect,useId,useMemo,useReducer,useRef,useState,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
const log:string[]=[];
function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);act(()=>root.render(ui));return {host,update:(next:ReactNode)=>act(()=>root.render(next)),unmount:()=>act(()=>root.unmount())};}
function hookError(run:()=>void):string{try{run();return "brak błędu";}catch(error){const list=error instanceof AggregateError?error.errors:[error];const text=list.map(e=>e instanceof Error?e.message:String(e)).join(" ");return text.includes("fewer hooks")?"fewer hooks":text.includes("more hooks")?"more hooks":"inny błąd";}}
void createContext;void use;void useCallback;void useEffect;void useId;void useMemo;void useReducer;void useRef;void useState;void log;void hookError;
`;
const exercises=[
  ['Zaimplementuj depsChanged porównujące zależności przez Object.is.','Brak tablicy i różna długość to zawsze zmiana.',deps],
  ['Napisz test depsChanged dla liczb, obiektów, NaN, -0 i braku tablicy.','Object.is(NaN, NaN) to true, Object.is(0, -0) to false.',String.raw`import {expect,test} from "vitest";
import {depsChanged} from "./depsChanged";
test("depsChanged",()=>{
  expect(depsChanged([1,"OPEN"],[1,"OPEN"])).toBe(false);
  expect(depsChanged([{status:"OPEN"}],[{status:"OPEN"}])).toBe(true);
  expect(depsChanged([NaN],[NaN])).toBe(false);
  expect(depsChanged([0],[-0])).toBe(true);
  expect(depsChanged(undefined,undefined)).toBe(true);
});`],
  ['Odtwórz model slotów z teorii jako createHooksModel z indeksem resetowanym przed renderem.','Slot dopisywany tylko wtedy, gdy indeks wychodzi poza tablicę.',model],
  ['Udowodnij testem, że hook w warunku w modelu slotów zwraca cudzą wartość.','Drugi render bez opisu: priority dostaje pusty tekst.',String.raw`import {expect,test} from "vitest";
import {ticketFormDemo} from "./hooksModel";
test("cudzy slot",()=>{
  const [, second]=ticketFormDemo();
  expect(second.priority).toBe("");
});`],
  ['Zbuduj nextHook: wskaźnik bieżącego hooka, tworzenie węzła przy montowaniu i błędy przy aktualizacji.','Brak węzła = więcej hooków; inny rodzaj = zmiana kolejności.',runtime],
  ['Dodaj useState z kolejką aktualizacji i dispatch tworzonym raz przy montowaniu.','Kolejkę przetwarzaj w renderze po kolei.',runtime],
  ['Dodaj wczesne wyjście: ta sama wartość przy pustej kolejce nie planuje renderu.','Object.is(action, hook.value).',runtime],
  ['Zaimplementuj useRef jako obiekt { current } tworzony raz.','Zwracaj zawsze ten sam obiekt z węzła.',runtime],
  ['Zaimplementuj useMemo przeliczające wynik tylko po zmianie zależności.','Użyj depsChanged na zapisanych i nowych zależnościach.',runtime],
  ['Zaimplementuj useEffect odkładany do „commitu” ze sprzątaniem poprzedniego setupu.','Kolejka efektów uruchamiana po zakończeniu renderu.',runtime],
  ['Napisz mount z update, flush i błędem „Rendered fewer hooks than expected”.','Po renderze porównaj index z długością listy.',runtime],
  ['Sprawdź testem, że stany są rozpoznawane po kolejności, a nie po nazwach.','Zmień drugi stan i sprawdź oba wyniki.',runtimeTests],
  ['Sprawdź testem grupowanie: trzy setState przed flush dają jeden render.','Licznik renders w obiekcie z mount.',runtimeTests],
  ['Sprawdź testem stałą tożsamość settera i refa między renderami.','toBe porównuje referencje.',runtimeTests],
  ['Sprawdź testem efekt z NaN w zależnościach i efekt z nowym obiektem.','NaN nie uruchomi ponownie, obiekt — za każdym razem.',runtimeTests],
  ['Sprawdź testem trzy błędy: mniej hooków, więcej hooków i zmiana rodzaju.','expect(() => view.update(...)).toThrow(...).',runtimeTests],
  ['Pokaż testem, że własny hook dopisuje węzły do listy komponentu.','Porównaj rodzaje węzłów: state, state, effect.',runtimeTests],
  ['Popraw TicketCard, który miał wczesny return przed useState.','Hook przed pierwszym warunkowym return.',ticketCard],
  ['Rozwiąż wczesny return inaczej: wydziel komponent-dziecko z własną listą hooków.','Rodzic bez hooków może wrócić wcześnie.',String.raw`import {useState} from "react";
import type {Ticket} from "./TicketCard";
export function TicketCardShell({ticket}:{ticket:Ticket|null}){
  if(!ticket)return <p>Brak zgłoszenia</p>; // bezpieczne: ten komponent nie wywołuje hooków
  return <TicketDetails ticket={ticket}/>;
}
function TicketDetails({ticket}:{ticket:Ticket}){
  const [open,setOpen]=useState(false); // własna lista hooków dziecka, montowana razem z nim
  return <article>
    <h2>{ticket.title}</h2>
    <button type="button" onClick={()=>setOpen(o=>!o)}>Szczegóły</button>
    {open&&<p>{ticket.details}</p>}
  </article>;
}`],
  ['Napisz useOnline i StatusBar z teorii, dodając przycisk zamiast klikanej stopki.','Hook zawsze wywołany na najwyższym poziomie.',online+'\n// --- StatusBar.tsx ---\n'+statusBar.replace(/^import[^\n]*\n/gm,'')],
  ['Sprawdź testem, że dwa StatusBar mają niezależny stan i oba reagują na offline.','Zamockuj getter navigator.onLine i wyślij zdarzenie offline w act.',reactTests],
  ['Sprawdź testem, że prawdziwy React rzuca „Rendered fewer hooks than expected”.','Wycisz console.error na czas testu.',reactTests],
  ['Zbuduj TicketForm z czterema useState i udowodnij testem ich niezależność.','Zmień priorytet i osobę, sprawdź wszystkie cztery pola.',ticketForm],
  ['Zastąp useState w pętli o zmiennej długości jednym stanem z obiektem.','Liczba hooków nie może zależeć od danych.',String.raw`import {useState} from "react";
// ŹLE: for (const field of fields) useState("") — liczba hooków zależy od danych.
export function DynamicFields({fields}:{fields:string[]}){
  const [values,setValues]=useState<Record<string,string>>({});
  return <div>{fields.map(field=><input key={field} aria-label={field} value={values[field]??""} onChange={e=>setValues(prev=>({...prev,[field]:e.target.value}))}/>)}</div>;
}`],
  ['Przenieś warunek z if przed useEffect do wnętrza efektu.','Hook zawsze wywołany, warunek w środku.',String.raw`import {useEffect} from "react";
export function useAutosave(enabled:boolean,draft:string,save:(text:string)=>void){
  useEffect(()=>{
    if(!enabled)return; // warunek w środku — węzeł efektu istnieje w każdym renderze
    const id=setTimeout(()=>save(draft),500);
    return ()=>clearTimeout(id);
  },[enabled,draft,save]);
}`],
  ['Odczytaj kontekst warunkowo przez use zamiast useContext.','use wolno wywołać w if i w pętli.',String.raw`import {createContext,use} from "react";
export const ThemeContext=createContext<"jasny"|"ciemny">("jasny");
// use() to jedyne API Reacta, które dokumentacja pozwala wywołać warunkowo.
export function Badge({themed}:{themed:boolean}){
  if(!themed)return <span>Zwykły</span>;
  const theme=use(ThemeContext);
  return <span className={theme}>Motyw: {theme}</span>;
}`],
  ['Skonfiguruj ESLint z regułami rules-of-hooks i exhaustive-deps.','Plugin eslint-plugin-react-hooks, konfiguracja płaska.',String.raw`// eslint.config.js
import reactHooks from "eslint-plugin-react-hooks";
export default [{
  files: ["**/*.{js,jsx,ts,tsx}"],
  plugins: { "react-hooks": reactHooks },
  rules: {
    "react-hooks/rules-of-hooks": "error",   // hook w warunku, pętli lub po wczesnym return
    "react-hooks/exhaustive-deps": "warn",   // brakujące zależności efektów i memo
  },
}];`],
  ['Zastąp useState(parseDraft(...)) leniwym inicjalizatorem.','Wartość początkowa jest używana tylko przy montowaniu.',String.raw`import {useState} from "react";
function parseDraft(raw:string|null):{title:string}{return raw?JSON.parse(raw) as {title:string}:{title:""};}
export function DraftEditor(){
  // useState(parseDraft(...)) parsowałby przy każdym renderze; funkcja wykona się tylko przy montowaniu.
  const [draft,setDraft]=useState(()=>parseDraft(localStorage.getItem("draft")));
  return <input aria-label="Tytuł" value={draft.title} onChange={e=>setDraft({title:e.target.value})}/>;
}`],
  ['Zresetuj stan pola komentarza przy zmianie zgłoszenia przez key.','Nowy key to nowy fiber i nowa lista hooków.',String.raw`import {useState} from "react";
function CommentBox({ticketId}:{ticketId:number}){
  const [text,setText]=useState("");
  return <textarea aria-label={"Komentarz do "+ticketId} value={text} onChange={e=>setText(e.target.value)}/>;
}
// Nowy key = nowy fiber = nowa lista hooków, więc stan zaczyna od wartości początkowej.
export function TicketComments({ticketId}:{ticketId:number}){
  return <CommentBox key={ticketId} ticketId={ticketId}/>;
}`],
  ['Wyjaśnij, dlaczego TypeScript nie wykryje hooka w warunku i co go wykryje.','Mechanizm działa po pozycji w czasie działania.',String.raw`// TypeScript sprawdza typy wartości, a nie kolejność wywołań w czasie działania:
// useState<"LOW" | "HIGH"> obiecuje typ, ale węzeł wybierany jest po pozycji, więc może trafić cudza wartość.
// Wykrywają to: ESLint (react-hooks/rules-of-hooks) w edytorze oraz React w czasie działania
// („Rendered fewer/more hooks…”, w dev ostrzeżenie o zmianie kolejności hooków).
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Gdzie React przechowuje hooki komponentu funkcyjnego?','W liście powiązanej w memoizedState jego fibera','W globalnej mapie według nazw zmiennych','W props komponentu','W obiekcie window','Jeden węzeł na każde wywołanie hooka, w kolejności wywołań.');
q('Po czym React rozpoznaje, który stan zwrócić z useState?','Po kolejności wywołania w renderze','Po nazwie zmiennej','Po typie wartości początkowej','Po numerze linii w pliku','Nazwy zmiennych w ogóle nie docierają do Reacta.');
q('Co robi React z hookami przy pierwszym renderze (montowaniu)?','Tworzy nowe węzły z wartościami początkowymi','Odczytuje istniejące węzły','Pomija wszystkie hooki','Kopiuje węzły z rodzica','Przy kolejnych renderach odczytuje je w tej samej kolejności.');
q('Co robi React z hookami przy kolejnych renderach?','Odczytuje istniejące węzły w tej samej kolejności','Tworzy nowe węzły z wartościami początkowymi','Sortuje hooki alfabetycznie','Usuwa węzły, które nie zmieniły wartości','Dlatego wartość początkowa jest ignorowana po montowaniu.');
q('Co zmieni przemianowanie zmiennej priority na prio w useState?','Nic — liczy się pozycja wywołania','Stan zostanie zresetowany','React rzuci błąd','Powstanie dodatkowy węzeł','Nazwa zmiennej to sprawa kodu, nie mechanizmu.');
q('Dlaczego hook nie może być wywołany w if?','Zmienia liczbę lub kolejność wywołań między renderami','Bo if spowalnia render','Bo TypeScript tego zabrania','Bo hooki są asynchroniczne','Rozjazd pozycji daje cudzy stan albo błąd Reacta.');
q('Jaki błąd rzuci React, gdy w renderze wywołano mniej hooków niż poprzednio?','Rendered fewer hooks than expected','Rendered more hooks than during the previous render','Too many re-renders','Objects are not valid as a React child','Komunikat dodaje: może to być przypadkowy wczesny return.');
q('Jaki błąd rzuci React, gdy wywołano więcej hooków niż poprzednio?','Rendered more hooks than during the previous render','Rendered fewer hooks than expected','Maximum update depth exceeded','Each child in a list should have a unique key','Brakuje węzła dla dodatkowego wywołania.');
q('Na co wskazuje komunikat „Rendered fewer hooks than expected”?','Na przypadkowy wczesny return przed hookiem','Na brak key w liście','Na zbyt głęboką rekurencję','Na brak providera kontekstu','Komunikat wprost wspomina „accidental early return statement”.');
q('Która reguła ESLint pilnuje zasad hooków?','react-hooks/rules-of-hooks','react-hooks/exhaustive-deps','react/jsx-key','no-unused-vars','Działa tak samo w JSX i TSX.');
q('Do czego służy reguła react-hooks/exhaustive-deps?','Ostrzega o brakujących lub zbędnych zależnościach efektów i memo','Pilnuje kolejności hooków','Wymusza pliki TSX','Zakazuje useEffect','Kolejności pilnuje rules-of-hooks.');
q('Czy TypeScript wykryje hook wywołany w warunku?','Nie — to błąd czasu działania, nie typów','Tak, zawsze','Tylko w trybie strict','Tylko w plikach TSX','Chronią ESLint i React.');
q('Co zwraca symulacja z teorii jako priority w TicketForm(false)?','Pusty tekst — wartość ze slotu opisu','HIGH','null','undefined','Drugie wywołanie trafia do slotu, który należał do opisu.');
q('Dlaczego w symulacji TSX typ "LOW" | "HIGH" nie chroni przed złą wartością?','Slot jest unknown rzutowany na T, a wybór slotu odbywa się po pozycji w czasie działania','Bo TypeScript nie obsługuje unii','Bo tablica slotów jest const','Bo typ jest opcjonalny','Rzutowanie as T obiecuje typ bez sprawdzenia.');
q('Gdzie wolno wywoływać hooki?','Na najwyższym poziomie komponentu lub własnego hooka','W pętlach','W handlerach zdarzeń','W dowolnej funkcji pomocniczej','Poza tym — bez warunków i wczesnych return przed nimi.');
q('Czy można wywołać useState w handlerze onClick?','Nie — hooki wywołuje się tylko podczas renderu','Tak','Tylko w TSX','Tylko w useCallback','Handler działa poza renderem, bez bieżącego fibera.');
q('Jak naprawić wczesny return przed hookiem?','Wywołać hooki przed pierwszym warunkowym return albo przenieść resztę do komponentu-dziecka','Dodać key','Owinąć render w try/catch','Użyć useMemo','Lista hooków musi mieć stałą długość.');
q('Dlaczego wydzielenie komponentu-dziecka rozwiązuje problem warunkowego hooka?','Dziecko ma własny fiber i własną listę hooków, montowaną razem z nim','Dziecko renderuje się szybciej','Dzieci nie mogą mieć hooków','Dziecko dzieli listę z rodzicem','Warunek decyduje o montowaniu dziecka, a nie o liczbie hooków.');
q('Co przechowuje węzeł useState?','Wartość i kolejkę aktualizacji','Tylko wartość początkową','Kopię props','Listę dzieci','Kolejka jest przetwarzana w następnym renderze.');
q('Co robi setter z useState?','Dopisuje aktualizację do kolejki i planuje render','Natychmiast zmienia zmienną w bieżącym renderze','Odmontowuje komponent','Kopiuje stan do refa','Zmienna stanu w bieżącym renderze się nie zmienia.');
q('Dlaczego forma prev => … widzi wynik poprzednich aktualizacji?','Kolejka jest przetwarzana po kolei, każda funkcja dostaje wynik poprzedniej','Bo prev to ref','Bo React czeka 0 ms','Bo to wymóg klas','setN(5); setN(x => x + 1) daje 6.');
q('Czy funkcja setState ma stałą tożsamość między renderami?','Tak, React gwarantuje stabilny setter','Nie, jest nowa w każdym renderze','Tylko w StrictMode','Tylko po owinięciu w useCallback','Dlatego linter nie wymaga jej w zależnościach.');
q('Co przechowuje węzeł useEffect?','Funkcję efektu, zależności i funkcję sprzątającą','Wynik renderu','Tylko zależności','Węzeł DOM','Przy renderze React porównuje nowe zależności z zapisanymi.');
q('Jak React porównuje zależności efektu?','Object.is element po elemencie','JSON.stringify całej tablicy','Operatorem ==','Głębokim porównaniem rekurencyjnym','Inna referencja obiektu to zmiana.');
q('Ile wynosi Object.is(NaN, NaN)?','true','false','undefined','Rzuca błąd','W odróżnieniu od NaN === NaN.');
q('Ile wynosi Object.is(0, -0)?','false','true','NaN','Rzuca błąd','W odróżnieniu od 0 === -0.');
q('Co się stanie z efektem, gdy w zależnościach jest obiekt tworzony w renderze?','Uruchomi się po każdym renderze — nowa referencja','Nie uruchomi się nigdy','Uruchomi się raz','React rzuci błąd','Przenieś tworzenie obiektu do efektu albo zależ od pól prostych.');
q('Co zwraca depsChanged, gdy tablica zależności nie została podana?','true — efekt po każdym renderze','false','Rzuca błąd','true tylko przy montowaniu','Brak tablicy to synchronizacja po każdym renderze.');
q('Jak React traktuje zmianę długości tablicy zależności między renderami?','Jako zmianę i ostrzega w trybie deweloperskim','Ignoruje ją','Resetuje stan komponentu','Zgłasza błąd TypeScript','Tablica zależności powinna mieć stałą długość.');
q('Co przechowuje węzeł useRef?','Obiekt { current } utworzony raz','Nową kopię obiektu w każdym renderze','Wartość stanu z kolejką','Funkcję sprzątającą','Zwracany jest zawsze ten sam obiekt.');
q('Czy zmiana ref.current powoduje render?','Nie','Tak','Tylko dla węzłów DOM','Tylko w StrictMode','Ref nie ma kolejki ani planowania renderu.');
q('Co przechowuje węzeł useMemo?','Wynik obliczenia i zależności','Tylko funkcję obliczającą','Kolejkę aktualizacji','Węzeł DOM','Przy niezmienionych zależnościach zwraca zapamiętany wynik.');
q('Kiedy useMemo liczy wynik ponownie?','Gdy zmieniła się któraś zależność (Object.is)','Przy każdym renderze','Nigdy po montowaniu','Co określony czas','To optymalizacja — nie gwarancja semantyczna.');
q('Czym jest useCallback(fn, deps) w modelu węzłów?','Tym samym co useMemo(() => fn, deps)','Nowym rodzajem efektu','Refem do DOM','Kolejką aktualizacji','Zapamiętuje funkcję zamiast wyniku.');
q('Czym różni się useContext od useState w modelu listy hooków?','Odczytuje wartość z najbliższego providera i rejestruje zależność fibera, bez węzła ze stanem','Tworzy węzeł z kopią wartości kontekstu','Tworzy nowego providera','Nie działa w komponentach funkcyjnych','Mimo to zasady hooków dotyczą też useContext.');
q('Które API Reacta dokumentacja pozwala wywołać w if i w pętli?','use','useContext','useState','useEffect','Pozostałe hooki — tylko na najwyższym poziomie.');
q('Skąd useId bierze identyfikator?','Z pozycji komponentu w drzewie — stabilny na serwerze i w przeglądarce','Z Math.random przy każdym renderze','Z globalnego licznika przy każdym renderze','Z key komponentu','Dzięki temu hydratacja dostaje te same ID.');
q('Czy useId zmienia się między renderami tego samego komponentu?','Nie','Tak','Tylko po setState','Tylko w trybie deweloperskim','ID jest zapisane w węźle hooka.');
q('Czy własny hook ma własną listę hooków?','Nie — jego wywołania dopisują się do listy komponentu','Tak','Tylko gdy nazwa zaczyna się od use','Tylko w TSX','Dlatego własny hook też musi być wywoływany bezwarunkowo.');
q('Czy dwa komponenty używające tego samego własnego hooka dzielą stan?','Nie — każdy ma własne węzły w swoim fiberze','Tak','Tylko przy tych samych props','Tylko u tego samego rodzica','Hook to przepis na węzły, nie wspólny magazyn.');
q('Dlaczego nazwa własnego hooka musi zaczynać się od use?','Żeby linter rozpoznał wywołania hooków i pilnował zasad','Bo inaczej kod się nie skompiluje','Bo React wyszukuje hooki po nazwie w czasie działania','Bo tego wymaga JavaScript','Konwencja nazwy jest sygnałem dla narzędzi i ludzi.');
q('Ile węzłów hooków ma StatusBar z teorii?','Trzy: useState oraz useState i useEffect z useOnline','Jeden','Dwa','Cztery','useOnline dopisuje swoje dwa węzły po collapsed.');
q('Gdzie w React DevTools zobaczysz listę hooków komponentu?','W panelu Components, w sekcji hooks zaznaczonego komponentu','W panelu Profiler','W konsoli przeglądarki','W zakładce Network','Hooki są ponumerowane w kolejności wywołań.');
q('Jak DevTools pokazuje hooki pochodzące z własnego hooka?','Zagnieżdżone pod nazwą własnego hooka','Wcale ich nie pokazuje','Jako osobny komponent','Na końcu listy bez nazw','Np. Online → State, Effect.');
q('Co zrobić z hookiem wywoływanym w pętli o zmiennej długości?','Zamienić na jeden stan z tablicą lub obiektem albo wydzielić komponent dla elementu','Dodać key do hooka','Owinąć pętlę w useMemo','Wywołać hooki w setTimeout','Liczba hooków nie może zależeć od danych.');
q('Czy pętla o zawsze tej samej liczbie iteracji z hookiem w środku zadziała?','Technicznie tak, ale łamie zasady i linter ją zgłosi','Nie, zawsze rzuci błąd','Tak, i jest zalecana','Tylko w TSX','Bezpieczna jest tylko dopóki liczba iteracji się nie zmieni.');
q('Jak warunkowo uruchomić efekt?','Wywołać useEffect zawsze, a warunek umieścić w jego środku','Umieścić if przed useEffect','Usunąć tablicę zależności','Wywołać useEffect w handlerze','Węzeł efektu istnieje w każdym renderze.');
q('Co robi zmiana key na komponencie z hookami?','Tworzy nowy fiber z nową listą hooków — stan zaczyna się od nowa','Nic','Przestawia kolejność hooków','Kopiuje stan do nowego komponentu','To zalecany sposób resetu stanu.');
q('Czy wartość przekazana do useState jest używana po montowaniu?','Nie, tylko przy pierwszym renderze','Tak, przy każdym renderze','Tak, gdy się zmieni','Tylko w StrictMode','Później węzeł ma już własną wartość.');
q('Co daje leniwy inicjalizator useState(() => …)?','Funkcja wykona się tylko przy montowaniu','Stan staje się asynchroniczny','Stan jest tylko do odczytu','Komponent nie renderuje się ponownie','useState(heavy()) wywołuje heavy przy każdym renderze.');
q('Co może zrobić React po setState z wartością równą obecnej (Object.is)?','Pominąć render (bailout)','Wyrenderować dwa razy','Rzucić błąd','Odmontować komponent','Nowy obiekt o tej samej treści to jednak zmiana.');
q('Jak useState ma się do useReducer w implementacji Reacta?','useState to useReducer z wbudowanym reduktorem — obie mają kolejkę','Mają zupełnie osobne mechanizmy bez kolejki','useReducer przetwarza tylko jedną akcję na render','useReducer działa tylko w klasach','Reduktor useState: (state, action) => typeof action === "function" ? action(state) : action.');
q('Co zrobi React w trybie deweloperskim, gdy na tej samej pozycji zmieni się rodzaj hooka (useState → useRef)?','Ostrzeże o zmianie kolejności hooków','Nic nie zauważy','Posortuje hooki','Utworzy nowy fiber','Komunikat: „React has detected a change in the order of Hooks”.');
q('Czy hooków można używać w komponentach klasowych?','Nie','Tak','Tylko useRef','Tylko w metodzie render','Klasy mają this.state i metody cyklu życia.');
q('Co oznacza błąd „Invalid hook call”?','Hook wywołany poza renderem komponentu funkcyjnego albo zduplikowany pakiet react','Hook w warunku','Złą tablicę zależności','Brak key w liście','Zasady kolejności to osobne błędy „fewer/more hooks”.');
q('Dlaczego dwa egzemplarze pakietu react w bundlu dają „Invalid hook call”?','Komponent woła hooki z innej kopii niż ta, w której renderer ustawił dyspozytora','Bo pliki się nadpisują','Bo TypeScript się gubi','Bo hooki są zapisane w window','Sprawdź npm ls react.');
q('Czym jest dyspozytor hooków?','Obiektem podmienianym na czas renderu (montowanie lub aktualizacja), przez który przechodzą wywołania hooków','Osobnym wątkiem przeglądarki','Pluginem Babel','Pętlą zdarzeń','Poza renderem dyspozytor zgłasza błąd.');
q('Dlaczego ta sama funkcja useState działa inaczej przy montowaniu i aktualizacji?','React podstawia na czas renderu inny dyspozytor dla montowania i aktualizacji','Bo sprawdza nazwę zmiennej','Bo ma dwa eksporty','Bo czyta props komponentu','Montowanie tworzy węzły, aktualizacja je odczytuje.');
q('W jakiej kolejności uruchamiają się efekty jednego komponentu?','W kolejności węzłów, czyli wywołań','W odwrotnej kolejności','Alfabetycznie','Losowo','Efekty układu przed zwykłymi efektami.');
q('Co dzieje się z węzłami hooków przy odmontowaniu komponentu?','React uruchamia sprzątanie efektów, a fiber z listą jest porzucany','Są zapisywane do localStorage','Przechodzą do rodzica','Zostają do ponownego montowania','Ponowne zamontowanie zaczyna od wartości początkowych.');
q('Czy można wywołać hook w zwykłej funkcji pomocniczej, np. formatTicket?','Nie — tylko w komponencie lub funkcji, której nazwa zaczyna się od use','Tak, zawsze','Tylko gdy zwraca JSX','Tylko w TSX','Zmień ją we własny hook albo przekaż wartość argumentem.');
q('Co pokazuje symulacja z indeksem resetowanym przed renderem?','Że dopasowanie stanu do wywołania zależy wyłącznie od pozycji','Że React używa nazw zmiennych','Że stan jest wspólny dla całej aplikacji','Że hooki są asynchroniczne','Dokładnie dlatego istnieją zasady hooków.');
q('Dlaczego prawdziwy React rzuca błąd, a symulacja zwraca złą wartość po cichu?','React porównuje liczbę (a w dev także rodzaj) hooków między renderami','Bo symulacja jest w TypeScript','Bo React zna nazwy zmiennych','Bo React nie ma kolejki aktualizacji','Symulacja nie ma żadnej kontroli listy.');
q('Czy React Compiler pozwala łamać zasady hooków?','Nie — zakłada ich przestrzeganie i pomija komponenty, które je łamią','Tak','Tylko w produkcji','Tylko z use','Kompilator opiera optymalizacje na zasadach Reacta.');
q('Ile zwróci depsChanged([1, "OPEN"], [1, "OPEN"])?','false','true','undefined','Rzuci błąd','Te same wartości proste — efekt pominięty.');
q('Co się stanie z efektem, gdy w zależnościach jest funkcja zdefiniowana w renderze?','Uruchomi się po każdym renderze — nowa funkcja to nowa referencja','Nie uruchomi się nigdy','Uruchomi się raz','React rzuci błąd','Przenieś funkcję do efektu albo użyj useCallback.');
const probes=[
  ['Dwa stany po kolejności','Błąd eksportu|HIGH','HIGH|Błąd eksportu','Błąd eksportu|Błąd eksportu','|HIGH',String.raw`let seen="";function C(){const [title]=useState("Błąd eksportu");const [priority]=useState("HIGH");seen=title+"|"+priority;return null;}mount(<C/>);console.log(seen);`],
  ['Hook w warunku: true → false','fewer hooks','more hooks','brak błędu','inny błąd',String.raw`function C({on}:{on:boolean}){useState("a");if(on)useState("b");return null;}const v=mount(<C on/>);console.log(hookError(()=>v.update(<C on={false}/>)));`],
  ['Hook w warunku: false → true','more hooks','fewer hooks','brak błędu','inny błąd',String.raw`function C({on}:{on:boolean}){useState("a");if(on)useState("b");return null;}const v=mount(<C on={false}/>);console.log(hookError(()=>v.update(<C on/>)));`],
  ['Wczesny return między hookami','fewer hooks','more hooks','brak błędu','inny błąd',String.raw`function Card({ticket}:{ticket:string|null}){const [seen]=useState(0);if(!ticket)return <p>Brak</p>;const [open]=useState(false);return <p>{ticket+seen+String(open)}</p>;}const v=mount(<Card ticket="T-1"/>);console.log(hookError(()=>v.update(<Card ticket={null}/>)));`],
  ['Pętla z hookiem: 2 → 3 iteracje','more hooks','fewer hooks','brak błędu','inny błąd',String.raw`function C({count}:{count:number}){for(let i=0;i<count;i++)useState(i);return null;}const v=mount(<C count={2}/>);console.log(hookError(()=>v.update(<C count={3}/>)));`],
  ['Pętla z hookiem o stałej długości','brak błędu','more hooks','fewer hooks','inny błąd',String.raw`function C({count}:{count:number}){for(let i=0;i<count;i++)useState(i);return null;}const v=mount(<C count={2}/>);console.log(hookError(()=>v.update(<C count={2}/>)));`],
  ['Tożsamość settera między renderami','true','false','undefined','błąd',String.raw`const setters:unknown[]=[];function C({n}:{n:number}){const [,set]=useState(0);setters.push(set);return <p>{n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);console.log(String(setters[0]===setters[1]));`],
  ['Tożsamość obiektu refa między renderami','true','false','undefined','błąd',String.raw`const refs:unknown[]=[];function C({n}:{n:number}){refs.push(useRef(n));return <p>{n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);console.log(String(refs[0]===refs[1]));`],
  ['useMemo przy tych samych zależnościach','1','3','2','0',String.raw`let computes=0;function C({n,tick}:{n:number;tick:number}){const doubled=useMemo(()=>{computes+=1;return n*2;},[n]);return <p>{doubled+tick}</p>;}const v=mount(<C n={1} tick={1}/>);v.update(<C n={1} tick={2}/>);v.update(<C n={1} tick={3}/>);console.log(computes);`],
  ['useMemo po zmianie zależności','2','1','3','0',String.raw`let computes=0;function C({n}:{n:number}){const doubled=useMemo(()=>{computes+=1;return n*2;},[n]);return <p>{doubled}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);console.log(computes);`],
  ['useCallback przy tych samych zależnościach','true','false','undefined','błąd',String.raw`const fns:unknown[]=[];function C({id,tick}:{id:number;tick:number}){fns.push(useCallback(()=>id,[id]));return <p>{tick}</p>;}const v=mount(<C id={1} tick={1}/>);v.update(<C id={1} tick={2}/>);console.log(String(fns[0]===fns[1]));`],
  ['useCallback po zmianie zależności','false','true','undefined','błąd',String.raw`const fns:unknown[]=[];function C({id}:{id:number}){fns.push(useCallback(()=>id,[id]));return <p>{id}</p>;}const v=mount(<C id={1}/>);v.update(<C id={2}/>);console.log(String(fns[0]===fns[1]));`],
  ['Kolejka: wartość, potem funkcja','8','4','7','6',String.raw`let run!:()=>void;function C(){const [n,setN]=useState(3);run=()=>{setN(n+1);setN(x=>x*2);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>run());console.log(v.host.textContent);`],
  ['Kolejka: 5, potem x + 1','6','5','1','7',String.raw`let run!:()=>void;function C(){const [n,setN]=useState(0);run=()=>{setN(5);setN(x=>x+1);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>run());console.log(v.host.textContent);`],
  ['Kolejka: x + 1, potem 10','10','11','1','0',String.raw`let run!:()=>void;function C(){const [n,setN]=useState(0);run=()=>{setN(x=>x+1);setN(10);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>run());console.log(v.host.textContent);`],
  ['Dwa razy setN(n + 1)','1','2','0','3',String.raw`let run!:()=>void;function C(){const [n,setN]=useState(0);run=()=>{setN(n+1);setN(n+1);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>run());console.log(v.host.textContent);`],
  ['Dwie akcje useReducer w jednym act','2','1','0','3',String.raw`let send!:()=>void;function C(){const [n,dispatch]=useReducer((s:number,a:"inc")=>a==="inc"?s+1:s,0);send=()=>{dispatch("inc");dispatch("inc");};return <p>{n}</p>;}const v=mount(<C/>);act(()=>send());console.log(v.host.textContent);`],
  ['Leniwy inicjalizator przy trzech renderach','1','3','2','0',String.raw`let calls=0;function init(){calls+=1;return "szkic";}function C({n}:{n:number}){const [draft]=useState(init);return <p>{draft+n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);v.update(<C n={3}/>);console.log(calls);`],
  ['Wywołanie init() w useState przy trzech renderach','3','1','2','0',String.raw`let calls=0;function init(){calls+=1;return "szkic";}function C({n}:{n:number}){const [draft]=useState(init());return <p>{draft+n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);v.update(<C n={3}/>);console.log(calls);`],
  ['Efekt z NaN w zależnościach','1','2','0','3',String.raw`let runs=0;function C({value,tick}:{value:number;tick:number}){useEffect(()=>{runs+=1;},[value]);return <p>{tick}</p>;}const v=mount(<C value={NaN} tick={1}/>);v.update(<C value={NaN} tick={2}/>);console.log(runs);`],
  ['Efekt z obiektem z renderu w zależnościach','2','1','0','3',String.raw`let runs=0;function C({status}:{status:string}){const filter={status};useEffect(()=>{runs+=1;},[filter]);return <p>{filter.status}</p>;}const v=mount(<C status="OPEN"/>);v.update(<C status="OPEN"/>);console.log(runs);`],
  ['Efekt z tym samym tekstem w zależnościach','1','2','0','3',String.raw`let runs=0;function C({status,tick}:{status:string;tick:number}){useEffect(()=>{runs+=1;},[status]);return <p>{tick}</p>;}const v=mount(<C status="OPEN" tick={1}/>);v.update(<C status="OPEN" tick={2}/>);console.log(runs);`],
  ['Efekt bez tablicy zależności przy trzech renderach','3','1','2','0',String.raw`let runs=0;function C({n}:{n:number}){useEffect(()=>{runs+=1;});return <p>{n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);v.update(<C n={3}/>);console.log(runs);`],
  ['Zmiana tylko drugiej zależności','B','A,B','A','pusty',String.raw`function C({a,b}:{a:number;b:number}){useEffect(()=>{log.push("A");},[a]);useEffect(()=>{log.push("B");},[b]);return null;}const v=mount(<C a={1} b={1}/>);log.length=0;v.update(<C a={1} b={2}/>);console.log(log.join(",")||"pusty");`],
  ['Własny hook w dwóch komponentach','10','11','00','01',String.raw`function useCounter(){const [n,setN]=useState(0);return [n,()=>setN(x=>x+1)] as const;}let incA!:()=>void;function A(){const [n,inc]=useCounter();incA=inc;return <i>{n}</i>;}function B(){const [n]=useCounter();return <b>{n}</b>;}const v=mount(<><A/><B/></>);act(()=>incA());console.log(v.host.textContent);`],
  ['Własny hook wywołany dwa razy w komponencie','20','22','11','02',String.raw`function useCounter(){const [n,setN]=useState(0);return [n,()=>setN(x=>x+1)] as const;}let inc!:()=>void;function C(){const [a,incA]=useCounter();const [b]=useCounter();inc=incA;return <p>{a}{b}</p>;}const v=mount(<C/>);act(()=>{inc();inc();});console.log(v.host.textContent);`],
  ['useId między renderami','true','false','undefined','błąd',String.raw`const ids:string[]=[];function C({n}:{n:number}){ids.push(useId());return <p>{n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);console.log(String(ids[0]===ids[1]));`],
  ['use(Context) w warunku','OPEN','pusty','more hooks','inny błąd',String.raw`const Ctx=createContext("OPEN");function C({show}:{show:boolean}){if(!show)return null;return <p>{use(Ctx)}</p>;}const v=mount(<C show={false}/>);v.update(<C show/>);console.log(v.host.textContent||"pusty");`],
  ['Rendery po trzech setState w jednym act','2','4','3','1',String.raw`let renders=0;let bump!:()=>void;function C(){const [a,setA]=useState(0);const [b,setB]=useState(0);renders+=1;bump=()=>{setA(x=>x+1);setB(x=>x+1);setA(x=>x+1);};return <p>{a+b}</p>;}mount(<C/>);act(()=>bump());console.log(renders);`],
  ['Nowa wartość początkowa po montowaniu','a','b','pusty','undefined',String.raw`function C({initial}:{initial:string}){const [value]=useState(initial);return <p>{value}</p>;}const v=mount(<C initial="a"/>);v.update(<C initial="b"/>);console.log(v.host.textContent);`],
  ['Nowa wartość początkowa po zmianie key','b','a','pusty','undefined',String.raw`function C({initial}:{initial:string}){const [value]=useState(initial);return <p>{value}</p>;}const v=mount(<C key="1" initial="a"/>);v.update(<C key="2" initial="b"/>);console.log(v.host.textContent);`],
  ['Rendery po zmianie ref.current','1','2','0','3',String.raw`let renders=0;let bump!:()=>void;function C(){const ref=useRef(0);renders+=1;bump=()=>{ref.current+=1;};return <p>{renders}</p>;}mount(<C/>);act(()=>{bump();bump();});console.log(renders);`],
  ['depsChanged dla [NaN] i [NaN]','false','true','undefined','błąd',String.raw`function depsChanged(prev:readonly unknown[]|undefined,next:readonly unknown[]|undefined){if(prev===undefined||next===undefined)return true;if(prev.length!==next.length)return true;return next.some((value,i)=>!Object.is(value,prev[i]));}console.log(String(depsChanged([NaN],[NaN])));`],
  ['Model slotów: priority po pominięciu hooka','""','"HIGH"','null','undefined',String.raw`const slots:unknown[]=[];let index=0;function useSimState<T>(initial:T):[T]{const i=index++;if(slots.length<=i)slots.push(initial);return [slots[i] as T];}function TicketForm(show:boolean){const [title]=useSimState("Błąd eksportu");void title;if(show)useSimState("");const [priority]=useSimState("HIGH");return priority;}index=0;TicketForm(true);index=0;console.log(JSON.stringify(TicketForm(false)));`],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 w Vitest (createRoot + act w jsdom, bez StrictMode).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/depsChanged.ts':deps,'src/hooksModel.ts':model,'src/hookRuntime.ts':runtime,'src/TicketCard.tsx':ticketCard,'src/useOnline.ts':online,'src/StatusBar.tsx':statusBar,'src/TicketForm.tsx':ticketForm,'src/runtime.test.ts':runtimeTests,'src/react.test.tsx':reactTests};
const mutants=[
  {file:'src/depsChanged.ts',before:'!Object.is(value,prev[i])',after:'value!==prev[i]'},
  {file:'src/hookRuntime.ts',before:'if(hook.queue.length===0&&typeof action!=="function"&&Object.is(action,hook.value))return;',after:'if(false)return;'},
  {file:'src/hookRuntime.ts',before:'if(fresh||depsChanged(node.deps,deps)){',after:'if(true){'},
  {file:'src/hookRuntime.ts',before:'if(!inst.mounting&&inst.index<inst.hooks.length)',after:'if(false)'},
  {file:'src/hookRuntime.ts',before:'for(const update of node.queue.splice(0))node.value=update(node.value);',after:'const last=node.queue.splice(0).at(-1);if(last)node.value=last(node.value);'},
  {file:'src/TicketCard.tsx',before:'  const [open,setOpen]=useState(false);\n  if(!ticket)return <p>Brak zgłoszenia</p>;',after:'  if(!ticket)return <p>Brak zgłoszenia</p>;\n  const [open,setOpen]=useState(false);'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
