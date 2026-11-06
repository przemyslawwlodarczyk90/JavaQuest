// Lekcja 15/07: mini-React. Zachowanie mini-Reacta i kontrast z prawdziwym React 19 sprawdza Vitest (jsdom).
const mini=String.raw`// Mini-React z teorii jako fabryka: każda instancja ma własną tablicę hooków (testy są niezależne).
// Opcje: batching — render raz w mikrozadaniu; reconcile — prosta rekoncyliacja zamiast replaceChildren.
export type MiniNode=MiniElement|string|number|null|false|undefined;
export type Props=Record<string,unknown>&{children:MiniNode[]};
export type Component=(props:Props)=>MiniNode;
export type MiniElement={type:string|Component;props:Props};
export function h(type:string|Component,props:Record<string,unknown>|null,...children:(MiniNode|MiniNode[])[]):MiniElement{
  return {type,props:{...(props??{}),children:children.flat()}};
}
type HostNode={tag:string;attrs:[string,unknown][];children:HostNode[]}|{text:string};
export function createMiniReact({batching=false,reconcile=false}:{batching?:boolean;reconcile?:boolean}={}){
  const hooks:unknown[]=[];
  let hookIndex=0,renders=0,scheduled=false;
  let container:HTMLElement|null=null,root:MiniNode=null,tree:HostNode|null=null;
  const listeners=new WeakMap<Element,Map<string,EventListener>>();
  function useState<T>(initial:T):[T,(next:T|((prev:T)=>T))=>void]{
    const i=hookIndex++;
    if(hooks.length<=i)hooks[i]=initial;
    const setState=(next:T|((prev:T)=>T))=>{
      const prev=hooks[i] as T;
      hooks[i]=typeof next==="function"?(next as (p:T)=>T)(prev):next;
      if(batching)schedule();else rerender();
    };
    return [hooks[i] as T,setState];
  }
  function schedule(){
    if(scheduled)return; // jedno mikrozadanie na wiele setterów
    scheduled=true;
    queueMicrotask(()=>{scheduled=false;rerender();});
  }
  // Rozwinięcie komponentów: wywołania funkcji w stałej kolejności, wynik to drzewo samych tagów i tekstów.
  function expand(node:MiniNode):HostNode{
    if(node===null||node===false||node===undefined)return {text:""};
    if(typeof node==="string"||typeof node==="number")return {text:String(node)};
    if(typeof node.type==="function")return expand(node.type(node.props));
    return {tag:node.type,attrs:Object.entries(node.props).filter(([key])=>key!=="children"),children:node.props.children.map(expand)};
  }
  function setAttrs(el:HTMLElement,attrs:[string,unknown][]){
    for(const [type,fn] of listeners.get(el)??[])el.removeEventListener(type,fn); // stare funkcje z poprzedniego renderu
    for(const name of el.getAttributeNames())if(!attrs.some(([key])=>key===name||(key==="className"&&name==="class")))el.removeAttribute(name);
    const next=new Map<string,EventListener>();
    for(const [key,value] of attrs){
      if(key.startsWith("on")&&typeof value==="function"){const type=key.slice(2).toLowerCase();el.addEventListener(type,value as EventListener);next.set(type,value as EventListener);}
      else if(key==="className")el.className=String(value);
      else if(key==="value"&&el instanceof HTMLInputElement)el.value=String(value); // właściwość = bieżąca wartość
      else el.setAttribute(key,String(value));
    }
    listeners.set(el,next);
  }
  function create(host:HostNode):Node{
    if("text" in host)return document.createTextNode(host.text);
    const el=document.createElement(host.tag);
    setAttrs(el,host.attrs);
    for(const child of host.children)el.appendChild(create(child));
    return el;
  }
  function patch(dom:Node,prev:HostNode,next:HostNode):void{
    if("text" in next&&"text" in prev&&dom.nodeType===Node.TEXT_NODE){
      if(prev.text!==next.text)dom.nodeValue=next.text;
      return;
    }
    if("text" in next||"text" in prev||prev.tag!==next.tag){dom.parentNode?.replaceChild(create(next),dom);return;} // inny typ → nowy węzeł
    const el=dom as HTMLElement,kids=Array.from(el.childNodes);
    setAttrs(el,next.attrs);
    next.children.forEach((child,i)=>{if(i<prev.children.length)patch(kids[i],prev.children[i],child);else el.appendChild(create(child));});
    for(let i=next.children.length;i<prev.children.length;i++)el.removeChild(kids[i]);
  }
  function rerender(){
    if(!container)return;
    hookIndex=0; // lista hooków czytana od początku
    renders+=1;
    const next=expand(root);
    if(reconcile&&tree&&container.firstChild)patch(container.firstChild,tree,next);
    else container.replaceChildren(create(next));
    tree=next;
  }
  function render(element:MiniNode,target:HTMLElement){container=target;root=element;tree=null;rerender();}
  return {useState,render,get renders(){return renders;}};
}
export type MiniReact=ReturnType<typeof createMiniReact>;`;
const app=String.raw`import {h,type MiniReact,type Props} from "./miniReact";
export type Ticket={id:number;title:string;status:"OPEN"|"CLOSED"};
export function createTicketApp(mini:MiniReact){
  function TicketItem(props:Props){
    const ticket=props.ticket as Ticket;
    const onClose=props.onClose as (id:number)=>void;
    return h("li",null,"#"+ticket.id+" "+ticket.title+" ",
      ticket.status==="OPEN"?h("button",{onClick:()=>onClose(ticket.id)},"Zamknij"):h("em",null,"zamknięty"));
  }
  function App(){
    const [tickets,setTickets]=mini.useState<Ticket[]>([
      {id:41,title:"Błąd logowania",status:"OPEN"},
      {id:42,title:"API timeout",status:"OPEN"},
    ]);
    const [query,setQuery]=mini.useState("");
    const open=tickets.filter(t=>t.status==="OPEN").length;
    const close=(id:number)=>setTickets(prev=>prev.map(t=>t.id===id?{...t,status:"CLOSED" as const}:t));
    const visible=tickets.filter(t=>t.title.toLowerCase().includes(query.toLowerCase()));
    return h("section",{className:"mini-app"},
      h("input",{"aria-label":"Szukaj",value:query,onInput:(e:Event)=>setQuery((e.target as HTMLInputElement).value)}),
      h("h2",null,"Otwarte: "+open),
      h("ul",null,visible.map(t=>h(TicketItem,{ticket:t,onClose:close}))));
  }
  return App;
}`;
const tests=String.raw`import {useState} from "react";
import {fireEvent,render as renderReact,screen} from "@testing-library/react";
import {expect,test,vi} from "vitest";
import {createMiniReact,h,type Props} from "./miniReact";
import {createTicketApp} from "./TicketApp";
function host(){const el=document.createElement("div");document.body.append(el);return el;}
test("h zwraca element z płaskimi dziećmi",()=>{
  expect(h("ul",null,[h("li",null,"a"),h("li",null,"b")],"c").props.children).toHaveLength(3);
  expect(h("p",null)).toEqual({type:"p",props:{children:[]}});
});
test("render: tekst, liczby, nic, className i zdarzenia",()=>{
  const mini=createMiniReact(),el=host(),click=vi.fn();
  mini.render(h("p",{className:"tag",onClick:click},"x",0,null,false),el);
  expect(el.innerHTML).toBe('<p class="tag">x0</p>');
  el.querySelector("p")!.click();
  expect(click).toHaveBeenCalledTimes(1);
});
test("aplikacja ticketów: zamknięcie zmienia licznik i przycisk",()=>{
  const mini=createMiniReact(),el=host();
  mini.render(h(createTicketApp(mini),null),el);
  expect(el.querySelectorAll("li")).toHaveLength(2);
  expect(el.querySelector("h2")!.textContent).toBe("Otwarte: 2");
  el.querySelector("button")!.click();
  expect(el.querySelector("h2")!.textContent).toBe("Otwarte: 1");
  expect(el.querySelector("li em")!.textContent).toBe("zamknięty");
});
test("replaceChildren: po wpisaniu znaku pole jest nowym węzłem i traci fokus",()=>{
  const mini=createMiniReact(),el=host();
  mini.render(h(createTicketApp(mini),null),el);
  const input=el.querySelector("input")!;
  input.focus();
  fireEvent.input(input,{target:{value:"api"}});
  const next=el.querySelector("input")!;
  expect(next).not.toBe(input);
  expect(next.value).toBe("api");
  expect(document.activeElement).not.toBe(next);
  expect(el.querySelectorAll("li")).toHaveLength(1);
});
test("prosta rekoncyliacja: to samo pole zachowuje fokus",()=>{
  const mini=createMiniReact({reconcile:true}),el=host();
  mini.render(h(createTicketApp(mini),null),el);
  const input=el.querySelector("input")!;
  input.focus();
  fireEvent.input(input,{target:{value:"api"}});
  expect(el.querySelector("input")).toBe(input);
  expect(document.activeElement).toBe(input);
  expect(el.querySelectorAll("li")).toHaveLength(1);
});
test("rekoncyliacja aktualizuje tekst i podmienia węzeł o innym tagu",()=>{
  const mini=createMiniReact({reconcile:true}),el=host();
  mini.render(h(createTicketApp(mini),null),el);
  const heading=el.querySelector("h2")!;
  el.querySelector("button")!.click();
  expect(el.querySelector("h2")).toBe(heading);
  expect(heading.textContent).toBe("Otwarte: 1");
  expect(el.querySelector("li em")!.textContent).toBe("zamknięty");
});
test("rekoncyliacja usuwa stare nasłuchy — handler działa raz na kliknięcie",()=>{
  const mini=createMiniReact({reconcile:true}),el=host(),clicks=vi.fn();
  function Counter(){const [n,setN]=mini.useState(0);return h("button",{onClick:()=>{clicks();setN(x=>x+1);}},String(n));}
  mini.render(h(Counter,null),el);
  el.querySelector("button")!.click();
  el.querySelector("button")!.click();
  expect(clicks).toHaveBeenCalledTimes(2);
  expect(el.textContent).toBe("2");
});
test("bez batchingu: trzy settery to trzy rendery",()=>{
  const mini=createMiniReact(),el=host();
  let bump!:()=>void;
  function C(){const [a,setA]=mini.useState(0);const [b,setB]=mini.useState(0);bump=()=>{setA(x=>x+1);setB(x=>x+1);setA(x=>x+1);};return h("p",null,a+"/"+b);}
  mini.render(h(C,null),el);
  bump();
  expect(mini.renders).toBe(4);
  expect(el.textContent).toBe("2/1");
});
test("batching mikrozadaniem: trzy settery to jeden render po bieżącym kodzie",async()=>{
  const mini=createMiniReact({batching:true}),el=host();
  let bump!:()=>void;
  function C(){const [a,setA]=mini.useState(0);const [b,setB]=mini.useState(0);bump=()=>{setA(x=>x+1);setB(x=>x+1);setA(x=>x+1);};return h("p",null,a+"/"+b);}
  mini.render(h(C,null),el);
  bump();
  expect(mini.renders).toBe(1);
  expect(el.textContent).toBe("0/0");
  await Promise.resolve();
  expect(mini.renders).toBe(2);
  expect(el.textContent).toBe("2/1");
});
test("globalna tablica hooków: zniknięcie komponentu przesuwa stany",()=>{
  const mini=createMiniReact(),el=host();
  let toggle!:()=>void;
  function Label(props:Props){const [text]=mini.useState(String(props.initial));return h("span",null,text);}
  function App(){const [showA,setShowA]=mini.useState(true);toggle=()=>setShowA(s=>!s);return h("p",null,showA?h(Label,{initial:"A"}):null,h(Label,{initial:"B"}));}
  mini.render(h(App,null),el);
  expect(el.textContent).toBe("AB");
  toggle();
  expect(el.textContent).toBe("A"); // B dostał slot, który należał do A
});
test("prawdziwy React: stan jest w fiberze, więc B zachowuje swój stan",()=>{
  function Label({initial}:{initial:string}){const [text]=useState(initial);return <span>{text}</span>;}
  function App(){const [showA,setShowA]=useState(true);return <p onClick={()=>setShowA(s=>!s)}>{showA?<Label initial="A"/>:null}<Label initial="B"/></p>;}
  renderReact(<App/>);
  fireEvent.click(screen.getByText("A"));
  expect(screen.getByText("B").parentElement!.textContent).toBe("B");
});`;
const header=String.raw`import {act,memo,useState,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
type MiniNode=MiniElement|string|number|null|false|undefined;
type Props=Record<string,unknown>&{children:MiniNode[]};
type MiniElement={type:string|((props:Props)=>MiniNode);props:Props};
function h(type:MiniElement["type"],props:Record<string,unknown>|null,...children:(MiniNode|MiniNode[])[]):MiniElement{return {type,props:{...(props??{}),children:children.flat()}};}
function toDom(node:MiniNode):Node{
  if(node===null||node===false||node===undefined)return document.createTextNode("");
  if(typeof node==="string"||typeof node==="number")return document.createTextNode(String(node));
  if(typeof node.type==="function")return toDom(node.type(node.props));
  const el=document.createElement(node.type);
  for(const [key,value] of Object.entries(node.props)){
    if(key==="children")continue;
    if(key.startsWith("on")&&typeof value==="function")el.addEventListener(key.slice(2).toLowerCase(),value as EventListener);
    else if(key==="className")el.className=String(value);
    else el.setAttribute(key,String(value));
  }
  node.props.children.forEach(child=>el.appendChild(toDom(child)));
  return el;
}
function html(node:MiniNode){const el=toDom(node);return el instanceof Element?el.outerHTML:el.nodeName;}
function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);act(()=>root.render(ui));return {host,update:(next:ReactNode)=>act(()=>root.render(next))};}
void act;void memo;void useState;void h;void toDom;void html;void mount;
`;
const exercises=[
  ['Napisz typy MiniNode, Props, Component, MiniElement i funkcję h z płaskimi dziećmi.','children.flat() spłaszcza tablice z map.',String.raw`export type MiniNode=MiniElement|string|number|null|false|undefined;
export type Props=Record<string,unknown>&{children:MiniNode[]};
export type Component=(props:Props)=>MiniNode;
export type MiniElement={type:string|Component;props:Props};
export function h(type:string|Component,props:Record<string,unknown>|null,...children:(MiniNode|MiniNode[])[]):MiniElement{
  return {type,props:{...(props??{}),children:children.flat()}};
}`],
  ['Napisz test h: spłaszczanie tablicy dzieci i element bez props.','toEqual porównuje strukturę obiektu.',String.raw`import {expect,test} from "vitest";
import {h} from "./miniReact";
test("h",()=>{
  expect(h("ul",null,[h("li",null,"a"),h("li",null,"b")],"c").props.children).toHaveLength(3);
  expect(h("p",null)).toEqual({type:"p",props:{children:[]}});
});`],
  ['Zamień tekst, liczby i „nic” na węzły tekstowe, a komponenty — na wynik ich wywołania.','null, false i undefined dają pusty tekst; 0 to tekst "0".',mini],
  ['Ustaw atrybuty, className i nasłuchy zdarzeń on… na tworzonych elementach.','onClick → addEventListener("click").',mini],
  ['Napisz useState z tablicą hooków indeksowaną kolejnością wywołań.','Slot tworzony tylko wtedy, gdy indeks wychodzi poza tablicę.',mini],
  ['Napisz render i rerender, który zeruje hookIndex przed każdym renderem.','Bez zerowania drugi render czytałby nowe sloty.',mini],
  ['Zbuduj aplikację ticketów na h z licznikiem otwartych i przyciskiem Zamknij.','Zamknięcie to mapowanie z nowym statusem.',app],
  ['Sprawdź testem, że zamknięcie ticketu zmienia licznik i zamienia przycisk na „zamknięty”.','Klik na pierwszym przycisku.',tests],
  ['Pokaż testem, że w trybie replaceChildren pole wyszukiwania traci fokus.','Porównaj węzeł input przed i po zdarzeniu input.',tests],
  ['Dodaj batching: setter tylko planuje render w queueMicrotask.','Flaga scheduled blokuje kolejne mikrozadania.',mini],
  ['Sprawdź testem, że trzy settery z batchingiem dają jeden render dopiero po mikrozadaniu.','await Promise.resolve() pozwala wykonać mikrozadanie.',tests],
  ['Uruchom samodzielny scheduleRender z teorii i policz rendery.','Trzy wywołania → jedno mikrozadanie.',String.raw`let scheduled=false;
let renders=0;
function doRender(){renders+=1;console.log("render nr",renders);}
export function scheduleRender(){
  if(scheduled)return;
  scheduled=true;
  queueMicrotask(()=>{scheduled=false;doRender();});
}
scheduleRender();
scheduleRender();
scheduleRender(); // po bieżącym kodzie: "render nr 1"`],
  ['Rozdziel render na rozwinięcie komponentów do drzewa tagów i tekstów oraz tworzenie DOM.','expand wywołuje komponenty w stałej kolejności.',mini],
  ['Napisz patch aktualizujący istniejący węzeł tekstowy przez nodeValue.','Nie twórz nowego węzła, gdy tekst się zmienił.',mini],
  ['W patch podmień węzeł, gdy tag lub rodzaj węzła się zmienił.','replaceChild z nowym węzłem.',mini],
  ['W patch porównuj dzieci po indeksie, dopisuj nowe i usuwaj nadmiarowe.','Iteruj po nowych dzieciach, potem usuń resztę starych.',mini],
  ['Usuwaj w patch nasłuchy z poprzedniego renderu i nieużywane atrybuty.','WeakMap element → mapa typ zdarzenia → funkcja.',mini],
  ['Sprawdź testem, że w trybie rekoncyliacji pole zachowuje fokus.','toBe porównuje tożsamość węzła.',tests],
  ['Sprawdź testem, że po kilku renderach handler wykonuje się raz na kliknięcie.','vi.fn() liczy wywołania.',tests],
  ['Pokaż testem, że globalna tablica hooków przesuwa stany po zniknięciu komponentu, a prawdziwy React nie.','Komponent B dostaje slot komponentu A.',tests],
  ['Dodaj obsługę style jako obiektu zamiast "[object Object]".','style.setProperty z nazwą w kebab-case.',String.raw`export function applyStyle(el:HTMLElement,style:Record<string,string|number>){
  for(const [name,value] of Object.entries(style)){
    el.style.setProperty(name.replace(/[A-Z]/g,m=>"-"+m.toLowerCase()),String(value));
  }
}
// applyStyle(el, { backgroundColor: "red" }) → style="background-color: red;"`],
  ['Napisz applyProp: pomija key, obsługuje atrybuty logiczne, value jako właściwość i style.','disabled=false nie może zostawić atrybutu.',String.raw`export function applyProp(el:HTMLElement,key:string,value:unknown){
  if(key==="children"||key==="key")return; // key służy rekoncyliacji, nie trafia do DOM
  if(key.startsWith("on")&&typeof value==="function"){el.addEventListener(key.slice(2).toLowerCase(),value as EventListener);return;}
  if(key==="className"){el.className=String(value);return;}
  if(key==="value"&&el instanceof HTMLInputElement){el.value=String(value);return;}
  if(key==="style"&&typeof value==="object"&&value!==null){
    for(const [name,v] of Object.entries(value as Record<string,string|number>))el.style.setProperty(name.replace(/[A-Z]/g,m=>"-"+m.toLowerCase()),String(v));
    return;
  }
  if(value===false||value===null||value===undefined){el.removeAttribute(key);return;} // disabled={false}
  el.setAttribute(key,value===true?"":String(value));
}`],
  ['Zastąp nasłuchy na każdym elemencie delegacją zdarzeń na korzeniu.','Szukaj handlera od event.target w górę do korzenia.',String.raw`const handlers=new WeakMap<Element,Map<string,(event:Event)=>void>>();
export function setHandler(el:Element,type:string,fn:(event:Event)=>void){
  const map=handlers.get(el)??new Map<string,(event:Event)=>void>();
  map.set(type,fn);
  handlers.set(el,map);
}
// Jeden nasłuch na typ zdarzenia na korzeniu — jak React 17+ na kontenerze root.
export function delegate(root:HTMLElement,types:string[]){
  for(const type of types)root.addEventListener(type,event=>{
    for(let node:Element|null=event.target instanceof Element?event.target:null;node&&root.contains(node);node=node.parentElement){
      handlers.get(node)?.get(type)?.(event);
    }
  });
}`],
  ['Skonfiguruj TypeScript, żeby JSX kompilował się do Twojej funkcji h.','jsx: "react" z jsxFactory albo pragma w pliku.',String.raw`// tsconfig.json (osobny projekt z mini-Reactem)
// {
//   "compilerOptions": {
//     "jsx": "react",          // klasyczna transformacja: <div /> → h("div", null)
//     "jsxFactory": "h",
//     "jsxFragmentFactory": "Fragment"
//   }
// }
// albo w pojedynczym pliku: /** @jsx h */ na początku.
export {};`],
  ['Porównaj MiniNode z ReactNode i MiniElement z ReactElement.','Typy jako dokumentacja architektury.',String.raw`import type {ReactElement,ReactNode} from "react";
import type {MiniElement,MiniNode} from "./miniReact";
// MiniNode ≈ ReactNode: wszystko, co da się wyrenderować (element, tekst, liczba, „nic”).
// MiniElement ≈ ReactElement: obiekt { type, props } — bez $$typeof, key i ref.
export type Comparison={mini:MiniNode;real:ReactNode;miniElement:MiniElement;realElement:ReactElement};`],
  ['Napisz prosty diff listy z kluczami, który przenosi istniejące <li> zamiast tworzyć nowe.','Mapa key → węzeł, insertBefore na właściwą pozycję.',String.raw`export type Keyed={key:string;text:string};
// Istniejące węzły są przenoszone, więc zachowują stan DOM (fokus, wpisany tekst, przewinięcie).
export function patchKeyedList(ul:HTMLUListElement,items:Keyed[]){
  const existing=new Map<string,HTMLLIElement>();
  for(const li of Array.from(ul.children))if(li instanceof HTMLLIElement&&li.dataset.key)existing.set(li.dataset.key,li);
  items.forEach((item,i)=>{
    let li=existing.get(item.key);
    if(!li){li=document.createElement("li");li.dataset.key=item.key;}
    existing.delete(item.key);
    if(li.textContent!==item.text)li.textContent=item.text;
    const at=ul.children[i];
    if(at!==li)ul.insertBefore(li,at??null);
  });
  for(const li of existing.values())li.remove();
}`],
  ['Dodaj do mini-Reacta szkic useEffect uruchamianego po podmianie DOM.','Efekty zbierane w renderze, uruchamiane w „commicie” ze sprzątaniem.',String.raw`type EffectSlot={deps:unknown[]|undefined;cleanup:(()=>void)|undefined};
const slots:EffectSlot[]=[];
const pending:(()=>void)[]=[];
let effectIndex=0;
export function useEffect(setup:()=>void|(()=>void),deps?:unknown[]){
  const i=effectIndex++;
  const prev=slots[i]?.deps;
  const changed=!deps||!prev||deps.length!==prev.length||deps.some((d,k)=>!Object.is(d,prev[k]));
  if(!changed)return;
  pending.push(()=>{
    slots[i]?.cleanup?.();
    const cleanup=setup();
    slots[i]={deps,cleanup:typeof cleanup==="function"?cleanup:undefined};
  });
}
// Wywołaj po replaceChildren/patch: odpowiednik commitu; zeruje indeks przed następnym renderem.
export function commitEffects(){effectIndex=0;for(const run of pending.splice(0))run();}`],
  ['Wypisz różnice mini-Reacta względem prawdziwego i przypisz każdą lekcję rozdziału.','Co najmniej pięć różnic.',String.raw`// 1. Brak rekoncyliacji (replaceChildren) → lekcja 02: typ, pozycja i klucz decydują o zachowaniu węzła.
// 2. Globalna tablica stanów zamiast fiberów → lekcje 03 i 06: lista hooków w fiberze na pozycji w drzewie.
// 3. Brak kluczy → lekcja 02: przenoszenie elementów listy z zachowaniem stanu.
// 4. Brak batchingu → lekcja 05: wiele aktualizacji, jeden render.
// 5. Brak faz i efektów → lekcja 04: render, commit, layout effect, efekty, sprzątanie.
// 6. Render synchroniczny bez przerw → lekcje 03 i 05: Fiber, pętla pracy, lanes i przejścia.
// 7. Nasłuch na każdym elemencie → delegacja zdarzeń na kontenerze root.
// 8. Elementy bez $$typeof → lekcja 01: ochrona przed obiektami udającymi elementy.
export {};`],
  ['Napisz test prawdziwego Reacta: trzy settery w handlerze dają jeden render.','Porównaj z mini-Reactem bez batchingu (trzy rendery).',String.raw`import {useState} from "react";
import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test} from "vitest";
test("prawdziwy React grupuje aktualizacje",async()=>{
  let renders=0;
  function Counter(){
    const [a,setA]=useState(0);
    const [b,setB]=useState(0);
    renders+=1;
    return <button type="button" onClick={()=>{setA(x=>x+1);setB(x=>x+1);setA(x=>x+1);}}>{a}/{b}</button>;
  }
  const user=userEvent.setup();
  render(<Counter/>);
  await user.click(screen.getByRole("button"));
  expect(renders).toBe(2);
  expect(screen.getByRole("button")).toHaveTextContent("2/1");
});`],
  ['Zapisz mapę rozdziału 15 jako notatkę powtórkową.','Elementy, rekoncyliacja, Fiber, fazy, batching i lanes, hooki, mini-React.',String.raw`// 01 Elementy: zwykłe obiekty { $$typeof, type, props, key }; komponent to funkcja zwracająca elementy.
// 02 Rekoncyliacja: porównanie drzew; ten sam typ na tej samej pozycji (lub ten sam key) zachowuje stan.
// 03 Fiber: render w jednostkach pracy, drzewa current i work-in-progress, możliwość przerwania.
// 04 Fazy: render (czysty, przerywalny) → commit (synchroniczny: DOM, refy, layout) → malowanie → efekty.
// 05 Batching i lanes: wiele aktualizacji = jeden render; pilne vs przejścia; flushSync jako wyjątek.
// 06 Hooki: lista węzłów w fiberze rozpoznawana po kolejności → zasady hooków.
// 07 Mini-React: h, render, useState — i lista braków, które wyjaśniają powyższe mechanizmy.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Co zwraca h(type, props, ...children) w mini-Reakcie?','Obiekt { type, props } z dziećmi w props.children','Węzeł DOM','Ciąg HTML','Nowy komponent','To odpowiednik createElement/jsx.');
q('Po co w h wywołanie children.flat()?','Żeby tablica dzieci z map trafiła jako pojedyncze dzieci','Żeby usunąć null','Żeby posortować dzieci','Żeby skopiować props','flat() spłaszcza jeden poziom zagnieżdżenia.');
q('Co mini-React robi z elementem, którego type jest funkcją?','Wywołuje ją z props i renderuje wynik','Tworzy element o nazwie funkcji','Pomija go','Zapisuje go w tablicy hooków','Komponent to funkcja z props w element.');
q('Co toDom tworzy dla null, false i undefined?','Pusty węzeł tekstowy','Element <null>','Wyjątek','Komentarz HTML','Pusty tekst zajmuje miejsce, ale nic nie pokazuje.');
q('Jak mini-React podpina onClick?','Przez addEventListener("click") na tym elemencie','Przez atrybut onclick w HTML','Delegacją na korzeniu','Przez setAttribute','Nazwa zdarzenia to key.slice(2).toLowerCase().');
q('Jak prawdziwy React obsługuje zdarzenia inaczej niż mini-React?','Deleguje je — nasłuchuje na kontenerze korzenia','Używa atrybutów onclick','Nie obsługuje zdarzeń natywnych','Niczym się nie różni','Zdarzenia bąbelkują do korzenia, a React wywołuje właściwe handlery.');
q('Gdzie mini-React przechowuje stan?','W jednej tablicy indeksowanej kolejnością wywołań','W fiberze komponentu','W localStorage','W props','To uproszczenie z lekcji 06 bez fiberów.');
q('Po co zerować hookIndex przed każdym renderem?','Żeby lista hooków była czytana od początku w tej samej kolejności','Żeby wyczyścić stan','Żeby przyspieszyć render','Żeby wyczyścić DOM','Bez zerowania drugi render czytałby nowe sloty.');
q('Co robi setter w podstawowym mini-Reakcie?','Zmienia wartość i od razu renderuje całą aplikację','Planuje render na później','Zmienia tylko DOM','Nic, czeka na kolejne kliknięcie','Dlatego trzy settery to trzy rendery.');
q('Ile renderów dadzą trzy settery w jednym handlerze mini-Reacta bez batchingu?','Trzy','Jeden','Zero','Dwa','Każdy setter wywołuje rerender od razu.');
q('Co robi replaceChildren przy każdym renderze mini-Reacta?','Podmienia cały DOM na zbudowany od nowa','Aktualizuje tylko różnice','Przenosi istniejące węzły','Nic, gdy nic się nie zmieniło','Nie ma porównania z poprzednim drzewem.');
q('Dlaczego pole tekstowe traci fokus w mini-Reakcie?','Po każdym renderze jest to nowy element input','Bo onInput blokuje fokus','Bo jsdom nie obsługuje fokusu','Bo brakuje atrybutu value','Stary, aktywny element został usunięty z DOM.');
q('Co jeszcze przepada przy pełnej podmianie DOM?','Zaznaczenie tekstu i pozycja przewinięcia','Stan w tablicy hooków','Props komponentów','Nic więcej','Te informacje żyją w węzłach DOM.');
q('Co rozwiązuje utratę fokusu w mini-Reakcie?','Rekoncyliacja — aktualizacja istniejącego węzła, gdy typ się nie zmienił','Atrybut autofocus','setTimeout przed renderem','Klucze w tablicy hooków','Ten sam węzeł input zostaje w dokumencie.');
q('Jaki problem daje jedna globalna tablica stanów?','Komponenty warunkowe lub w innej kolejności dostają cudze stany','Żaden','Zbyt duże zużycie pamięci','Wolniejsze tworzenie elementów','Zniknięcie komponentu przesuwa sloty następnych.');
q('Jak prawdziwy React wiąże stan z komponentem?','Stan jest w fiberze na pozycji komponentu w drzewie','W globalnej tablicy','Po nazwie komponentu','W props','Każdy fiber ma własną listę hooków.');
q('Czego mini-React nie potrafi zrobić z elementami listy?','Przenieść elementu z zachowaniem jego stanu — brak kluczy','Wyrenderować listy','Spłaszczyć tablicy dzieci','Wyświetlić liczb','Klucze pozwalają dopasować elementy niezależnie od pozycji.');
q('Co mini-React zrobi z props key?','Ustawi go w DOM jako zwykły atrybut key','Użyje go do rekoncyliacji','Pominie go','Rzuci błąd','Prawdziwy React traktuje key specjalnie i nie przekazuje go dalej.');
q('Jak działa batching przez mikrozadanie?','Settery tylko oznaczają potrzebę renderu, a render odbywa się raz w queueMicrotask','Settery renderują od razu','Render odbywa się co 16 ms','Render zawsze w setTimeout 0','To uproszczony automatyczny batching.');
q('Po co flaga scheduled w scheduleRender?','Żeby zaplanować jedno mikrozadanie mimo wielu setterów','Żeby blokować settery','Żeby liczyć rendery','Żeby wyłączyć render na stałe','Flaga jest zerowana w samym mikrozadaniu.');
q('Kiedy wykona się funkcja z queueMicrotask?','Po zakończeniu bieżącego kodu synchronicznego, przed zadaniami setTimeout','Natychmiast','Po setTimeout 0','W następnej klatce animacji','Kolejka mikrozadań opróżnia się przed kolejnym zadaniem.');
q('Czy batching mikrozadaniem przypomina automatyczny batching React 18?','Tak, w uproszczeniu: wiele aktualizacji daje jeden render','Nie, daje więcej renderów','Tylko w handlerach kliknięć','Tylko w klasach','React ma przy tym priorytety, których mini-React nie zna.');
q('Czego mini-React nie ma względem faz Reacta?','Commitu, efektów układu, efektów i sprzątania','Renderu','Elementów','Stanu','Wszystko dzieje się w jednym przebiegu.');
q('Dlaczego renderu mini-Reacta nie da się przerwać?','To synchroniczna rekurencja bez jednostek pracy','Bo używa mikrozadań','Bo nie ma stanu','Bo działa na DOM','Nie ma miejsca, w którym można oddać sterowanie.');
q('Co w prawdziwym Reakcie umożliwia przerywanie renderu?','Fiber — praca w jednostkach i pętla oddająca sterowanie','Mikrozadania','Web Workery','requestAnimationFrame w każdym komponencie','Lekcja 03.');
q('Czemu odpowiada typ MiniNode?','Uproszczonemu ReactNode','ReactElement','HTMLElement','Komponentowi klasowemu','To wszystko, co da się wyrenderować.');
q('Czemu odpowiada typ MiniElement?','Elementowi Reacta { type, props }','Węzłowi DOM','Fiberowi','Komponentowi klasowemu','Bez $$typeof, key i ref.');
q('Co pokazują rzutowania hooks[i] as T w mini-Reakcie?','Że poprawność stanu opiera się na kolejności wywołań, nie na typach','Że TypeScript jest zbędny','Że stan zawsze jest tekstem','Że hooki są typowane osobno dla każdego komponentu','Tablica przechowuje unknown.');
q('Jak pisać JSX dla własnej funkcji h?','Ustawić jsx: "react" z jsxFactory: "h" albo dać pragmę /** @jsx h */','Nie da się','Tylko przez webpack','Ustawić jsx: "preserve"','Klasyczna transformacja zamienia <div/> na h("div", null).');
q('Co robi uproszczony patch, gdy tag elementu się zmienił?','Tworzy nowy węzeł i podmienia stary','Aktualizuje tylko atrybuty','Nic','Przenosi dzieci do nowego','Inny typ → nowe poddrzewo, jak w Reakcie.');
q('Co robi patch dla elementu o tym samym tagu?','Aktualizuje atrybuty i rekurencyjnie dzieci istniejącego węzła','Tworzy nowy węzeł','Usuwa węzeł','Kopiuje cały HTML','Węzeł zachowuje fokus i wpisaną wartość.');
q('Jak patch aktualizuje zmieniony tekst?','Zmienia nodeValue istniejącego węzła tekstowego','Zawsze tworzy nowy węzeł','Zmienia innerHTML rodzica','Nie aktualizuje','Węzeł pozostaje ten sam.');
q('Dlaczego patch musi usuwać nasłuchy z poprzedniego renderu?','Każdy render tworzy nowe funkcje — bez usunięcia handler wykona się wielokrotnie','Bo przeglądarka nie pozwala na dwa nasłuchy','Bo nasłuchy spowalniają DOM','Nie musi','Stara funkcja ma też nieaktualne domknięcie.');
q('Co patch robi z nadmiarowymi starymi dziećmi?','Usuwa je z DOM','Zostawia je','Ukrywa je','Przenosi na koniec','Nowa lista jest krótsza od poprzedniej.');
q('Dlaczego porównywanie dzieci po indeksie jest słabe dla list?','Wstawienie na początek przesuwa wszystkie pozycje — potrzebne są klucze','Bo jest zbyt szybkie','Bo DOM go nie wspiera','Bo gubi tekst','Każdy element zostałby zaktualizowany treścią sąsiada.');
q('Co robi setAttribute("disabled", "false")?','Wyłącza element — liczy się obecność atrybutu, nie wartość','Włącza element','Nic','Rzuca błąd','Atrybuty logiczne trzeba usuwać.');
q('Jak React obsługuje disabled={false}?','Ustawia właściwość disabled na false, bez atrybutu','Ustawia disabled="false"','Rzuca błąd','Pomija cały element','React zna atrybuty logiczne.');
q('Co mini-React z teorii zrobi z style={{ color: "red" }}?','Ustawi style="[object Object]"','Ustawi czerwony kolor','Rzuci błąd','Pominie atrybut','String(obiekt) daje [object Object].');
q('Dlaczego value pola lepiej ustawiać właściwością niż atrybutem?','Atrybut value to wartość domyślna, właściwość — bieżąca','Bo atrybut nie istnieje','Bo właściwość jest wolniejsza','Nie ma różnicy','Po wpisaniu tekstu atrybut już nie zmienia widocznej wartości.');
q('Na czym polega delegacja zdarzeń?','Jeden nasłuch na korzeniu obsługuje zdarzenia potomków dzięki bąbelkowaniu','Każdy element ma własny nasłuch','Zdarzenia trafiają do workerów','Handler jest zapisany w atrybucie','Mniej nasłuchów i obsługa węzłów dodawanych później.');
q('Gdzie React 17+ podpina nasłuchy zdarzeń?','Na kontenerze korzenia (root)','Na document','Na window','Na każdym elemencie','Wcześniej (React 16) — na document.');
q('Który wniosek z mini-Reacta jest BŁĘDNY dla prawdziwego Reacta?','Każdy setState przebudowuje całą stronę','Elementy to zwykłe obiekty','Hooki są rozpoznawane po kolejności','Komponent to funkcja zwracająca elementy','To było celowe uproszczenie.');
q('Po co budować mini-Reacta?','Żeby zrozumieć, dlaczego React ma rekoncyliację, klucze i fibery','Żeby zastąpić Reacta w projekcie','Żeby przyspieszyć aplikację','Żeby uniknąć TypeScriptu','To narzędzie do nauki, nie wzór kodu.');
q('Co obejmuje „render” w mini-Reakcie?','Wywołanie komponentów i zbudowanie DOM w jednym przebiegu','Tylko wywołanie komponentów','Tylko commit','Tylko efekty','React rozdziela render i commit.');
q('Czym w prawdziwym Reakcie jest „wirtualny DOM”?','Drzewem elementów porównywanym z poprzednim','Kopią DOM trzymaną przez przeglądarkę','Shadow DOM','Ukrytym iframe','Lekcja 01.');
q('Co odróżnia element Reacta od zwykłego obiektu z type i props?','Pole $$typeof z symbolem','Pole type','Metoda render','Pole key','Symbol nie przechodzi przez JSON.');
q('Czy mini-React chroni przed obiektem z JSON udającym element?','Nie — każdy obiekt z type i props zostanie wyrenderowany','Tak, sprawdza $$typeof','Tak, dzięki TypeScriptowi','Tak, przez JSON.parse','Dlatego React wprowadził $$typeof.');
q('Na podstawie czego rekoncyliacja Reacta decyduje o zachowaniu stanu?','Typu, pozycji i klucza','Nazwy zmiennej','Treści tekstu','Kolejności importów','Lekcja 02.');
q('Czym jest drzewo work-in-progress?','Drzewem fiberów budowanym w renderze obok bieżącego (current)','Kopią DOM','Listą hooków','Kolejką mikrozadań','Po commicie staje się bieżącym.');
q('Czy commit w Reakcie można przerwać?','Nie, jest synchroniczny','Tak','Tylko w przejściu','Tylko z Suspense','Przerywalny jest render.');
q('Co oznaczają lanes w Reakcie?','Priorytety aktualizacji (pilne, przejścia…)','Wątki przeglądarki','Kolejki DOM','Rodzaje komponentów','Lekcja 05.');
q('Czego brakuje mini-Reactowi, żeby stan był per komponent?','Listy hooków w fiberze każdego komponentu','Globalnej tablicy','Kluczy w h','Mikrozadań','Lekcje 03 i 06.');
q('Co się stanie w mini-Reakcie TS po wywołaniu settera przed render()?','Nic się nie wyrenderuje — rerender sprawdza brak kontenera','Zawsze rzuci błąd','Wyrenderuje do body','Zapisze stan w localStorage','Wartość w tablicy jednak się zmieni.');
q('Ile razy wykonuje się każdy komponent mini-Reacta po jednym setState?','Raz — cała aplikacja renderuje się od korzenia','Tylko komponent ze stanem','Ani razu','Dwa razy','Mini-React nie wie, którego komponentu dotyczy stan.');
q('Jak prawdziwy React zawęża render po setState?','Renderuje od komponentu, którego stan się zmienił, w dół (z bailoutami)','Zawsze od korzenia całej aplikacji','Tylko rodzica','Tylko liście drzewa','Stan jest przypisany do fibera.');
q('Czym jest renderowanie współbieżne?','Renderem, który można przerwać, wznowić lub porzucić','Renderem w wielu wątkach','Renderem w workerze','Dwoma renderami naraz zawsze','Commit pozostaje synchroniczny.');
q('Jak dodać efekty do mini-Reacta?','Zbierać je w renderze i uruchamiać po podmianie DOM, ze sprzątaniem','Wywoływać je w h','Uruchamiać je przed renderem','Nie da się','To odpowiednik fazy commit.');
q('Gdzie teoria zaleca zbudować mini-Reacta?','W osobnym katalogu, poza DevDesk','W src/components DevDesk','W node_modules','W katalogu public','To eksperyment, nie część aplikacji.');
q('Czy mini-React wyrenderuje liczbę 0 jako dziecko?','Tak, jako tekst "0"','Nie, pominie ją','Rzuci błąd','Wyrenderuje pusty tekst','Pomijane są tylko null, false i undefined.');
q('Co się stanie z tablicą zagnieżdżoną na dwóch poziomach w dzieciach h?','flat() spłaszczy jeden poziom — wewnętrzna tablica nie jest poprawnym węzłem','Zostanie spłaszczona całkowicie','Zostanie pominięta','Zamieni się w tekst','Prawdziwy React obsługuje dowolne zagnieżdżenie.');
q('Jaki typ mają props komponentu mini-Reacta w TS i jaki to ma koszt?','Record<string, unknown> — komponenty muszą rzutować props','Dokładne typy — brak rzutowań','any — bez kosztu','never','Np. props.ticket as Ticket.');
q('Jak prawdziwy React typuje props komponentu?','Parametr funkcji ma własny typ props, sprawdzany w JSX','Zawsze Record<string, unknown>','Nie typuje props','Tylko w klasach','Brak rzutowań w komponentach.');
q('Co pokazuje porównanie trzech setterów w mini-Reakcie bez batchingu i w Reakcie?','Mini: trzy rendery, React: jeden','Oba po trzy','Oba po jednym','Mini: jeden, React: trzy','Automatyczny batching z lekcji 05.');
q('Którą lekcję rozdziału wiążesz z brakiem kluczy w mini-Reakcie?','Rekoncyliację (lekcja 02)','Hooki (lekcja 06)','Batching (lekcja 05)','Fazy (lekcja 04)','Klucze dopasowują elementy listy.');
q('Które lekcje wyjaśniają problem globalnej tablicy stanów?','Fiber (03) i hooki od środka (06)','Elementy (01)','Batching (05)','Żadna','Lista hooków w fiberze.');
q('Jakie jest kryterium akceptacji tej lekcji?','Mini-React z listą ticketów, batching mikrozadaniem i pięć różnic wyjaśnionych wiedzą z rozdziału','Zastąpienie Reacta w DevDesk','Napisanie Fibera z priorytetami','Sam test jednostkowy funkcji h','Plus lista różnic z odniesieniami do lekcji.');
const probes=[
  ['Dzieci po h z dwoma tekstami','["a","b"]','["ab"]','[["a","b"]]','[]',String.raw`console.log(JSON.stringify(h("p",null,"a","b").props.children));`],
  ['Spłaszczanie tablicy dzieci','[1,2,3]','[[1,2],3]','[3]','[1,2]',String.raw`console.log(JSON.stringify(h("ul",null,[1,2],3).props.children));`],
  ['Props elementu bez dzieci','{"children":[]}','{}','null','{"children":null}',String.raw`console.log(JSON.stringify(h("p",null).props));`],
  ['Dzieci podane jako props i jako argument','["y"]','["x"]','["x","y"]','[]',String.raw`console.log(JSON.stringify(h("p",{children:["x"]},"y").props.children));`],
  ['Typ elementu z komponentem','function','string','object','undefined',String.raw`function Badge(){return h("b",null,"A");}console.log(typeof h(Badge,null).type);`],
  ['HTML z className','<p class="tag">x</p>','<p classname="tag">x</p>','<p>x</p>','<p className="tag">x</p>',String.raw`console.log(html(h("p",{className:"tag"},"x")));`],
  ['toDom dla null','#text','#comment','NULL','DIV',String.raw`console.log(html(null));`],
  ['false wśród dzieci','<p>a</p>','<p>falsea</p>','<p>false a</p>','<p></p>',String.raw`console.log(html(h("p",null,false,"a")));`],
  ['Liczba 0 jako dziecko','<p>0</p>','<p></p>','<p>false</p>','<p>null</p>',String.raw`console.log(html(h("p",null,0)));`],
  ['Komponent w toDom','<b>A</b>','<Badge>A</Badge>','<badge></badge>','A',String.raw`function Badge(props:Props){return h("b",null,String(props.label));}console.log(html(h(Badge,{label:"A"})));`],
  ['disabled={false} w mini-Reakcie','true','false','undefined','błąd',String.raw`const el=toDom(h("input",{disabled:false})) as HTMLInputElement;console.log(String(el.disabled));`],
  ['disabled={false} w React','false','true','undefined','błąd',String.raw`const v=mount(<input disabled={false}/>);console.log(String((v.host.firstChild as HTMLInputElement).disabled));`],
  ['Atrybut data-* z liczbą','<div data-id="7"></div>','<div data-id=7></div>','<div></div>','<div dataId="7"></div>',String.raw`console.log(html(h("div",{"data-id":7})));`],
  ['style jako obiekt w mini-Reakcie','<p style="[object Object]"></p>','<p style="color: red;"></p>','<p></p>','błąd',String.raw`console.log(html(h("p",{style:{color:"red"}})));`],
  ['style jako obiekt w React','<p style="color: red;"></p>','<p style="[object Object]"></p>','<p></p>','<p color="red"></p>',String.raw`const v=mount(<p style={{color:"red"}}/>);console.log(v.host.innerHTML);`],
  ['key w mini-Reakcie','<li key="1"></li>','<li></li>','<li data-key="1"></li>','błąd',String.raw`console.log(html(h("li",{key:1})));`],
  ['key w React','<li></li>','<li key="1"></li>','<li data-key="1"></li>','pusty',String.raw`const v=mount(<li key={1}/>);console.log(v.host.innerHTML);`],
  ['onClick w HTML mini-Reacta','<button>x</button>','<button onclick="x">x</button>','<button onClick>x</button>','błąd',String.raw`console.log(html(h("button",{onClick:()=>{}},"x")));`],
  ['Kliknięcia z mini-Reacta','1','0','2','błąd',String.raw`let clicks=0;const el=toDom(h("button",{onClick:()=>{clicks+=1;}},"x")) as HTMLButtonElement;el.click();console.log(clicks);`],
  ['Dwa poziomy zagnieżdżenia tablic','TypeError','<p>a</p>','<p></p>','<p><undefined></undefined></p>',String.raw`try{console.log(html(h("p",null,[["a"]] as unknown as MiniNode[])));}catch(error){console.log(error instanceof TypeError?"TypeError":"inny błąd");}`],
  ['Tożsamość węzła po replaceChildren','false','true','undefined','błąd',String.raw`const box=document.createElement("div");box.replaceChildren(toDom(h("p",null,"1")));const first=box.firstChild;box.replaceChildren(toDom(h("p",null,"2")));console.log(String(first===box.firstChild));`],
  ['Tożsamość węzła po rerenderze w React','true','false','undefined','błąd',String.raw`const v=mount(<p>1</p>);const first=v.host.firstChild;v.update(<p>2</p>);console.log(String(first===v.host.firstChild));`],
  ['Fokus po replaceChildren','BODY','INPUT','DIV','null',String.raw`const box=document.createElement("div");document.body.append(box);box.replaceChildren(toDom(h("input",null)));(box.firstChild as HTMLInputElement).focus();box.replaceChildren(toDom(h("input",null)));console.log(document.activeElement?.tagName);`],
  ['Fokus po rerenderze w React','INPUT','BODY','DIV','null',String.raw`const v=mount(<input data-n="1"/>);(v.host.firstChild as HTMLInputElement).focus();v.update(<input data-n="2"/>);console.log(document.activeElement?.tagName);`],
  ['scheduleRender trzy razy','render nr 1','render nr 3','render nr 1 render nr 2 render nr 3','brak',String.raw`let scheduled=false,renders=0;function scheduleRender(){if(scheduled)return;scheduled=true;queueMicrotask(()=>{scheduled=false;renders+=1;console.log("render nr "+renders);});}scheduleRender();scheduleRender();scheduleRender();await Promise.resolve();`],
  ['Rendery zaraz po scheduleRender','0','1','3','błąd',String.raw`let scheduled=false,renders=0;function scheduleRender(){if(scheduled)return;scheduled=true;queueMicrotask(()=>{scheduled=false;renders+=1;});}scheduleRender();scheduleRender();console.log(renders);`],
  ['Rendery bez batchingu','3','1','0','2',String.raw`let renders=0;function rerender(){renders+=1;}rerender();rerender();rerender();console.log(renders);`],
  ['Mikrozadanie a setTimeout','microtask,timeout','timeout,microtask','microtask','timeout',String.raw`const order:string[]=[];setTimeout(()=>order.push("timeout"),0);queueMicrotask(()=>order.push("microtask"));await new Promise(resolve=>setTimeout(resolve,5));console.log(order.join(","));`],
  ['Globalna tablica: komponent A znika','A','B','pusty','undefined',String.raw`const hooks:unknown[]=[];let hookIndex=0;function useSlot<T>(initial:T):T{const i=hookIndex++;if(hooks.length<=i)hooks[i]=initial;return hooks[i] as T;}function A(){return useSlot("A");}function B(){return useSlot("B");}hookIndex=0;A();B();hookIndex=0;console.log(B());`],
  ['React: komponent A znika, stan B','B','A','pusty','undefined',String.raw`function Label({initial}:{initial:string}){const [text]=useState(initial);return <span>{text}</span>;}const v=mount(<p><Label key="a" initial="A"/><Label key="b" initial="B"/></p>);v.update(<p><Label key="b" initial="X"/></p>);console.log(v.host.textContent);`],
  ['Rendery w React po trzech setterach w handlerze','2','4','3','1',String.raw`let renders=0;function C(){const [a,setA]=useState(0);const [b,setB]=useState(0);renders+=1;return <button onClick={()=>{setA(x=>x+1);setB(x=>x+1);setA(x=>x+1);}}>{a+b}</button>;}const v=mount(<C/>);act(()=>{(v.host.firstChild as HTMLButtonElement).click();});console.log(renders);`],
  ['Rendery dzieci w React bez memo','4','2','3','1',String.raw`let childRenders=0;function Item({id}:{id:number}){childRenders+=1;return <li>{id}</li>;}let bump!:()=>void;function List(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <ul data-n={n}><Item id={1}/><Item id={2}/></ul>;}mount(<List/>);act(()=>bump());console.log(childRenders);`],
  ['Rendery dzieci w React z memo','2','4','3','1',String.raw`let childRenders=0;const Item=memo(function Item({id}:{id:number}){childRenders+=1;return <li>{id}</li>;});let bump!:()=>void;function List(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <ul data-n={n}><Item id={1}/><Item id={2}/></ul>;}mount(<List/>);act(()=>bump());console.log(childRenders);`],
  ['Rendery mini-Reacta po jednym setterze','2','1','0','3',String.raw`const slots:unknown[]=[];let index=0,renders=0;const box=document.createElement("div");function useSlot<T>(initial:T):[T,(v:T)=>void]{const i=index++;if(slots.length<=i)slots[i]=initial;return [slots[i] as T,v=>{slots[i]=v;rerender();}];}let set!:(v:number)=>void;function App(){const [n,setN]=useSlot(0);set=setN;return h("p",null,n);}function rerender(){index=0;renders+=1;box.replaceChildren(toDom(h(App,null)));}rerender();set(1);console.log(renders);`],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia w Vitest (jsdom); fragmenty z React — React 19, createRoot + act, bez StrictMode.',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/miniReact.ts':mini,'src/TicketApp.ts':app,'src/mini.test.tsx':tests};
const mutants=[
  {file:'src/miniReact.ts',before:'if(scheduled)return; // jedno',after:'if(false)return; // jedno'},
  {file:'src/miniReact.ts',before:'hookIndex=0; // lista',after:'void 0; // lista'},
  {file:'src/miniReact.ts',before:'if(prev.text!==next.text)dom.nodeValue=next.text;',after:'void prev;'},
  {file:'src/miniReact.ts',before:'for(const [type,fn] of listeners.get(el)??[])el.removeEventListener(type,fn);',after:''},
  {file:'src/miniReact.ts',before:'children:children.flat()}};',after:'children:children as MiniNode[]}};'},
  {file:'src/miniReact.ts',before:'if(reconcile&&tree&&container.firstChild)',after:'if(false)'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
