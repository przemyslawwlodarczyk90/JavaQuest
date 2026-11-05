// Lekcja 15/04: fazy render i commit. Kolejność wywołań sprawdza prawdziwy React 19 w Vitest (jsdom).
const phases=String.raw`import {useEffect,useLayoutEffect,useRef,useState} from "react";
// log wstrzykiwany z zewnątrz, żeby test mógł porównać kolejność wywołań.
export function PhasesDemo({log}:{log:(entry:string)=>void}){
  const [n,setN]=useState(0);
  const ref=useRef<HTMLButtonElement>(null);
  log("1. render — ref: "+(ref.current?ref.current.tagName:"null"));
  useLayoutEffect(()=>{
    log("3. layout — DOM: "+ref.current?.textContent);
    return ()=>log("   sprzątanie layout");
  },[n]);
  useEffect(()=>{
    log("4. effect");
    return ()=>log("   sprzątanie effect");
  },[n]);
  return <button ref={ref} type="button" onClick={()=>{log("0. handler");setN(x=>x+1);}}>Kliknięto {n}</button>;
}`;
const bailout=String.raw`import {useState} from "react";
export function BailoutDemo({onRender}:{onRender:()=>void}){
  const [status,setStatus]=useState<"OPEN"|"CLOSED">("OPEN");
  onRender();
  return <div>
    <p>Status: {status}</p>
    <button type="button" onClick={()=>setStatus("OPEN")}>Ustaw OPEN</button>
    <button type="button" onClick={()=>setStatus("CLOSED")}>Ustaw CLOSED</button>
  </div>;
}`;
const comments=String.raw`import {useLayoutEffect,useRef} from "react";
export type Comment={id:number;text:string};
export function CommentList({comments}:{comments:Comment[]}){
  const endRef=useRef<HTMLLIElement>(null);
  const lastId=comments.at(-1)?.id;
  useLayoutEffect(()=>{
    endRef.current?.scrollIntoView({block:"end"}); // DOM już zawiera nowy komentarz, ekran jeszcze nie odmalowany
  },[lastId]);
  return <ul className="comments">
    {comments.map(c=><li key={c.id}>{c.text}</li>)}
    <li ref={endRef} aria-hidden="true"/>
  </ul>;
}`;
const focus=String.raw`import {useEffect,useRef,useState} from "react";
import {flushSync} from "react-dom";
// ŹLE: w chwili focus() panel jeszcze nie istnieje — render i commit odbędą się po zakończeniu handlera.
export function FocusTooEarly(){
  const [open,setOpen]=useState(false);
  const inputRef=useRef<HTMLInputElement>(null);
  return <div>
    <button type="button" onClick={()=>{setOpen(true);inputRef.current?.focus();}}>Otwórz panel</button>
    {open&&<input ref={inputRef} aria-label="Komentarz"/>}
  </div>;
}
// DOBRZE: efekt reaguje na otwarcie panelu po commicie.
export function FocusWithEffect(){
  const [open,setOpen]=useState(false);
  const inputRef=useRef<HTMLInputElement>(null);
  useEffect(()=>{if(open)inputRef.current?.focus();},[open]);
  return <div>
    <button type="button" onClick={()=>setOpen(true)}>Otwórz panel</button>
    {open&&<input ref={inputRef} aria-label="Komentarz"/>}
  </div>;
}
// WYJĄTEK: flushSync wymusza render i commit wewnątrz handlera.
export function FocusWithFlushSync(){
  const [open,setOpen]=useState(false);
  const inputRef=useRef<HTMLInputElement>(null);
  return <div>
    <button type="button" onClick={()=>{flushSync(()=>setOpen(true));inputRef.current?.focus();}}>Otwórz panel</button>
    {open&&<input ref={inputRef} aria-label="Komentarz"/>}
  </div>;
}`;
const addComment=String.raw`import {useRef,useState} from "react";
import {flushSync} from "react-dom";
// Dodanie i przewinięcie w jednym handlerze dzięki flushSync; seen zapisuje liczbę <li> widzianą zaraz po aktualizacji.
export function AddWithFlushSync({seen}:{seen:(count:number)=>void}){
  const [items,setItems]=useState(["Pierwszy"]);
  const listRef=useRef<HTMLUListElement>(null);
  return <div>
    <ul ref={listRef}>{items.map(text=><li key={text}>{text}</li>)}</ul>
    <button type="button" onClick={()=>{
      flushSync(()=>setItems(list=>[...list,"Nowy "+list.length]));
      seen(listRef.current?.children.length??0);
      listRef.current?.lastElementChild?.scrollIntoView({block:"end"});
    }}>Dodaj</button>
  </div>;
}`;
const phaseTests=String.raw`import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test} from "vitest";
import {PhasesDemo} from "./PhasesDemo";
test("montowanie: render z pustym refem, potem layout z DOM, potem effect",()=>{
  const log:string[]=[];
  render(<PhasesDemo log={entry=>log.push(entry)}/>);
  expect(log).toEqual(["1. render — ref: null","3. layout — DOM: Kliknięto 0","4. effect"]);
});
test("kliknięcie: handler, render, sprzątanie i setup layout, sprzątanie i setup effect",async()=>{
  const log:string[]=[];
  const user=userEvent.setup();
  render(<PhasesDemo log={entry=>log.push(entry)}/>);
  log.length=0;
  await user.click(screen.getByRole("button"));
  expect(log).toEqual(["0. handler","1. render — ref: BUTTON","   sprzątanie layout","3. layout — DOM: Kliknięto 1","   sprzątanie effect","4. effect"]);
});
test("odmontowanie sprząta oba efekty",()=>{
  const log:string[]=[];
  const view=render(<PhasesDemo log={entry=>log.push(entry)}/>);
  log.length=0;
  view.unmount();
  expect(log).toEqual(["   sprzątanie layout","   sprzątanie effect"]);
});`;
const domTests=String.raw`import {memo,useState} from "react";
import {act,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {afterEach,expect,test,vi} from "vitest";
import {BailoutDemo} from "./BailoutDemo";
import {CommentList,type Comment} from "./CommentList";
import {AddWithFlushSync} from "./AddWithFlushSync";
import {FocusTooEarly,FocusWithEffect,FocusWithFlushSync} from "./FocusPanel";
afterEach(()=>{delete (Element.prototype as {scrollIntoView?:unknown}).scrollIntoView;});
test("ustawienie tej samej wartości stanu pomija render",async()=>{
  let renders=0;
  const user=userEvent.setup();
  render(<BailoutDemo onRender={()=>{renders+=1;}}/>);
  await user.click(screen.getByRole("button",{name:"Ustaw OPEN"}));
  await user.click(screen.getByRole("button",{name:"Ustaw OPEN"}));
  expect(renders).toBe(1);
  await user.click(screen.getByRole("button",{name:"Ustaw CLOSED"}));
  expect(renders).toBe(2);
  expect(screen.getByText("Status: CLOSED")).toBeInTheDocument();
});
test("render dziecka bez zmian w elementach nie zmienia DOM",()=>{
  let childRenders=0;
  function Child(){childRenders+=1;return <p className="stały">Lista</p>;}
  function Parent({tick}:{tick:number}){return <section data-tick="stały">{tick>=0&&<Child/>}</section>;}
  const view=render(<Parent tick={1}/>);
  const records:MutationRecord[]=[];
  const observer=new MutationObserver(list=>records.push(...list));
  observer.observe(view.container,{subtree:true,childList:true,attributes:true,characterData:true});
  view.rerender(<Parent tick={2}/>);
  records.push(...observer.takeRecords());
  observer.disconnect();
  expect(childRenders).toBe(2);
  expect(records).toHaveLength(0);
});
test("memo pomija render dziecka przy tych samych props",()=>{
  let renders=0;
  const Child=memo(function Child({label}:{label:string}){renders+=1;return <p>{label}</p>;});
  const view=render(<Child label="a"/>);
  view.rerender(<Child label="a"/>);
  expect(renders).toBe(1);
});
test("przewinięcie następuje po wstawieniu nowego komentarza do DOM",()=>{
  const seenTexts:string[]=[];
  Element.prototype.scrollIntoView=vi.fn(function(this:Element){seenTexts.push(this.parentElement?.textContent??"");});
  const first:Comment[]=[{id:1,text:"Pierwszy"}];
  const view=render(<CommentList comments={first}/>);
  view.rerender(<CommentList comments={[...first,{id:2,text:"Drugi"}]}/>);
  expect(seenTexts).toEqual(["Pierwszy","PierwszyDrugi"]);
});
test("ta sama lista nie przewija ponownie",()=>{
  const scroll=vi.fn();
  Element.prototype.scrollIntoView=scroll;
  const list:Comment[]=[{id:1,text:"Pierwszy"}];
  const view=render(<CommentList comments={list}/>);
  view.rerender(<CommentList comments={[...list]}/>);
  expect(scroll).toHaveBeenCalledTimes(1);
});
test("fokus zaraz po setState w handlerze nie trafia do pola",async()=>{
  const user=userEvent.setup();
  render(<FocusTooEarly/>);
  await user.click(screen.getByRole("button",{name:"Otwórz panel"}));
  expect(screen.getByLabelText("Komentarz")).not.toHaveFocus();
});
test("efekt reagujący na open ustawia fokus po commicie",async()=>{
  const user=userEvent.setup();
  render(<FocusWithEffect/>);
  await user.click(screen.getByRole("button",{name:"Otwórz panel"}));
  expect(screen.getByLabelText("Komentarz")).toHaveFocus();
});
test("flushSync ustawia fokus w tym samym handlerze",async()=>{
  const user=userEvent.setup();
  render(<FocusWithFlushSync/>);
  await user.click(screen.getByRole("button",{name:"Otwórz panel"}));
  expect(screen.getByLabelText("Komentarz")).toHaveFocus();
});
test("po flushSync DOM jest już zaktualizowany wewnątrz handlera",async()=>{
  Element.prototype.scrollIntoView=vi.fn();
  const seen:number[]=[];
  const user=userEvent.setup();
  render(<AddWithFlushSync seen={count=>seen.push(count)}/>);
  await user.click(screen.getByRole("button",{name:"Dodaj"}));
  expect(seen).toEqual([2]);
  await act(async()=>{});
  expect(screen.getAllByRole("listitem")).toHaveLength(2);
  void useState;
});`;
const header=String.raw`import {act,memo,useEffect,useLayoutEffect,useRef,useState,useReducer,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
import {flushSync} from "react-dom";
const log:string[]=[];
function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);act(()=>root.render(ui));return {host,update:(next:ReactNode)=>act(()=>root.render(next)),unmount:()=>act(()=>root.unmount())};}
function click(host:HTMLElement,index=0){act(()=>{host.querySelectorAll("button")[index].click();});}
void memo;void useEffect;void useLayoutEffect;void useRef;void useState;void useReducer;void flushSync;void log;void click;
`;
const exercises=[
  ['Zbuduj PhasesDemo z logami renderu, useLayoutEffect, useEffect i handlera.','Wstrzyknij funkcję log przez props, żeby test mógł porównać kolejność.',phases],
  ['Sprawdź testem kolejność logów przy montowaniu.','Ref w pierwszym renderze jest pusty.',phaseTests],
  ['Sprawdź testem kolejność logów po kliknięciu.','Sprzątanie poprzedniego efektu poprzedza nowy setup.',phaseTests],
  ['Sprawdź kolejność sprzątania przy odmontowaniu.','Najpierw efekty układu, potem zwykłe.',phaseTests],
  ['Zbuduj BailoutDemo z licznikiem renderów przekazanym przez props.','setStatus z tą samą wartością.',bailout],
  ['Udowodnij, że ustawienie tej samej wartości pomija render.','Object.is porównuje poprzedni i nowy stan.',domTests],
  ['Udowodnij MutationObserverem, że render bez różnic nie zmienia DOM.','Licznik renderów rośnie, rekordy mutacji — nie.',domTests],
  ['Pokaż, że memo pomija render dziecka przy tych samych props.','Szczegóły w rozdziale 16.',domTests],
  ['Zbuduj CommentList przewijającą do końca w useLayoutEffect zależnym od ID ostatniego komentarza.','Ref na pustym <li> na końcu listy.',comments],
  ['Sprawdź, że przewinięcie następuje, gdy nowy komentarz jest już w DOM.','Zamockuj scrollIntoView i odczytaj DOM w chwili wywołania.',domTests],
  ['Sprawdź, że ta sama lista nie przewija ponownie.','Zależność to lastId, nie cała tablica.',domTests],
  ['Pokaż błąd fokusu zaraz po setState w handlerze.','W chwili focus() pole nie istnieje.',focus],
  ['Popraw fokus efektem reagującym na otwarcie panelu.','Efekt działa po commicie.',focus],
  ['Popraw fokus przez flushSync w tym samym handlerze.','flushSync wymusza render i commit natychmiast.',focus],
  ['Sprawdź trzy warianty fokusu testami.','toHaveFocus na polu Komentarz.',domTests],
  ['Dodaj element listy i przewiń do niego w tym samym handlerze przez flushSync.','Po flushSync DOM ma już nowy element.',addComment],
  ['Sprawdź, że po flushSync handler widzi zaktualizowany DOM.','Zapisz liczbę <li> zaraz po flushSync.',domTests],
  ['Użyj autoFocus jako najprostszego rozwiązania fokusu nowego pola.','autoFocus działa przy montowaniu elementu.',String.raw`import {useState} from "react";
export function FocusWithAutoFocus(){
  const [open,setOpen]=useState(false);
  return <div>
    <button type="button" onClick={()=>setOpen(true)}>Otwórz panel</button>
    {open&&<input autoFocus aria-label="Komentarz"/>}
  </div>;
}`],
  ['Zmierz wysokość elementu w useLayoutEffect i ustaw pozycję podpowiedzi przed malowaniem.','useEffect pokazałby najpierw złą pozycję (mignięcie).',String.raw`import {useLayoutEffect,useRef,useState,type ReactNode} from "react";
export function Tooltip({anchorTop,children}:{anchorTop:number;children:ReactNode}){
  const ref=useRef<HTMLDivElement>(null);
  const [top,setTop]=useState(anchorTop);
  useLayoutEffect(()=>{
    const height=ref.current?.getBoundingClientRect().height??0;
    setTop(anchorTop-height-8); // ponowny render przed malowaniem — użytkownik nie zobaczy złej pozycji
  },[anchorTop]);
  return <div ref={ref} role="tooltip" style={{position:"absolute",top}}>{children}</div>;
}`],
  ['Wyjaśnij, dlaczego Highlight updates nie jest samo w sobie problemem wydajności.','Render ≠ zmiana DOM; liczy się koszt renderu.',String.raw`// "Highlight updates" pokazuje render, a nie zmianę DOM.
// Render, który zwraca te same elementy, kończy się commitem bez zmian w DOM.
// Problemem jest dopiero render, który kosztuje dużo czasu (mierzysz Profilerem — rozdział 16).
export {};`],
  ['Wypisz pełną oś czasu aktualizacji od setState do useEffect.','Wyzwolenie, render, commit, malowanie, efekty.',String.raw`// setState w handlerze
// → RENDER: wywołanie komponentów, nowe elementy, porównanie, lista zmian (przerywalny, czysty)
// → COMMIT: sprzątanie layout effectów, zmiany DOM, przypisanie refów, useLayoutEffect (synchronicznie)
// → MALOWANIE przez przeglądarkę
// → EFEKTY: sprzątanie poprzednich useEffect, nowe useEffect
export {};`],
  ['Odczytaj ref w efekcie, a nie w renderze.','W renderze ref może być null.',String.raw`import {useEffect,useRef} from "react";
export function MeasureWidth({onWidth}:{onWidth:(width:number)=>void}){
  const ref=useRef<HTMLDivElement>(null);
  useEffect(()=>{onWidth(ref.current?.offsetWidth??0);},[onWidth]);
  return <div ref={ref}>Panel</div>;
}`],
  ['Pokaż, że ref jest null po odmontowaniu elementu.','React czyści ref w commicie usuwającym węzeł.',String.raw`import {useRef,useState} from "react";
export function Toggle(){
  const [shown,setShown]=useState(true);
  const ref=useRef<HTMLParagraphElement>(null);
  return <div>
    <button type="button" onClick={()=>setShown(s=>!s)}>Przełącz</button>
    <button type="button" onClick={()=>console.log(ref.current===null?"brak":"jest")}>Sprawdź</button>
    {shown&&<p ref={ref}>Treść</p>}
  </div>;
}`],
  ['Napisz callback ref logujący przypisanie i zwolnienie węzła.','W React 19 callback ref może zwrócić funkcję sprzątającą.',String.raw`export function LoggedNode({log}:{log:(entry:string)=>void}){
  return <div ref={node=>{
    if(!node)return;
    log("ref: "+node.tagName);
    return ()=>log("ref: sprzątanie");
  }}>Węzeł</div>;
}`],
  ['Uporządkuj kod przewijania z requestAnimationFrame na useLayoutEffect.','useLayoutEffect wykona się po zmianach DOM i przed malowaniem.',comments],
  ['Wyjaśnij, kiedy flushSync jest uzasadniony, a kiedy szkodzi.','Rzadkie przypadki fokusu/przewinięcia; wyłącza grupowanie.',String.raw`// Uzasadniony: zaraz po aktualizacji w tym samym handlerze potrzebujesz DOM (fokus, przewinięcie, pomiar)
// albo integrujesz się z kodem spoza Reacta, który oczekuje gotowego DOM.
// Szkodzi: używany rutynowo wyłącza grupowanie aktualizacji i wymusza dodatkowe synchroniczne commity.
// Domyślnie wybieraj efekt reagujący na stan albo autoFocus.
export {};`],
  ['Pokaż, że kilka setState w jednym handlerze daje jeden render.','Grupowanie aktualizacji.',String.raw`import {useState} from "react";
export function BatchDemo({onRender}:{onRender:()=>void}){
  const [a,setA]=useState(0);
  const [b,setB]=useState(0);
  onRender();
  return <button type="button" onClick={()=>{setA(x=>x+1);setB(x=>x+1);}}>{a}/{b}</button>;
}`],
  ['Pokaż, że dwa flushSync w jednym handlerze dają dwa commity.','Każdy flushSync wymusza osobny render i commit.',String.raw`import {useState} from "react";
import {flushSync} from "react-dom";
export function DoubleFlush({onCommit}:{onCommit:(text:string)=>void}){
  const [n,setN]=useState(0);
  return <button type="button" onClick={e=>{
    const button=e.currentTarget;
    flushSync(()=>setN(x=>x+1));onCommit(button.textContent??"");
    flushSync(()=>setN(x=>x+1));onCommit(button.textContent??"");
  }}>{n}</button>;
}`],
  ['Napisz test kolejności dwóch efektów w jednym komponencie.','Efekty uruchamiają się w kolejności deklaracji.',String.raw`import {useEffect} from "react";
import {render} from "@testing-library/react";
import {expect,test} from "vitest";
test("kolejność deklaracji",()=>{
  const log:string[]=[];
  function C(){useEffect(()=>{log.push("A");},[]);useEffect(()=>{log.push("B");},[]);return null;}
  render(<C/>);
  expect(log).toEqual(["A","B"]);
});`],
  ['Napisz test: efekty dziecka uruchamiają się przed efektami rodzica.','Commit przechodzi drzewo od liści w górę.',String.raw`import {useEffect} from "react";
import {render} from "@testing-library/react";
import {expect,test} from "vitest";
test("dziecko przed rodzicem",()=>{
  const log:string[]=[];
  function Child(){useEffect(()=>{log.push("dziecko");},[]);return null;}
  function Parent(){useEffect(()=>{log.push("rodzic");},[]);return <Child/>;}
  render(<Parent/>);
  expect(log).toEqual(["dziecko","rodzic"]);
});`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Jakie są trzy kroki każdej aktualizacji w Reakcie?','Wyzwolenie, render, commit','Render, malowanie, efekty','Montowanie, aktualizacja, odmontowanie','Pobranie, render, zapis','Po commicie przeglądarka maluje, a potem działają efekty.');
q('Co może wyzwolić render?','Montowanie, zmiana stanu, zmiana kontekstu, render rodzica','Tylko zmiana props','Tylko setState','Tylko zdarzenie DOM','Render rodzica domyślnie renderuje też dzieci.');
q('Co dzieje się na ekranie w fazie render?','Nic — React tylko wylicza zmiany','DOM jest aktualizowany','Przeglądarka maluje','Uruchamiają się efekty','Render jest czystym obliczeniem.');
q('Co dzieje się w fazie commit?','Zmiany DOM, przypisanie refów i useLayoutEffect','Wywołanie komponentów','Malowanie','Pobranie danych','Commit jest synchroniczny.');
q('Kiedy uruchamia się useLayoutEffect?','W commicie, po zmianach DOM, przed malowaniem','Przed renderem','Po malowaniu','W renderze','Dobre miejsce na pomiary i przewijanie.');
q('Kiedy uruchamia się useEffect?','Po malowaniu','Przed commitem','W renderze','Przed useLayoutEffect','Nie blokuje malowania.');
q('Jaka jest wartość ref.current w pierwszym renderze?','null','Węzeł DOM','undefined zawsze','Poprzedni węzeł','Ref jest przypisywany w commicie.');
q('Kiedy React przypisuje ref.current = węzeł?','W commicie, przed useLayoutEffect','W renderze','Po useEffect','Przy tworzeniu elementu','Dlatego efekty widzą ustawiony ref.');
q('Co jest w ref.current po odmontowaniu elementu?','null','Stary węzeł','undefined','Kopia węzła','React czyści ref.');
q('Co robi React przed nowym setupem useLayoutEffect przy aktualizacji?','Uruchamia sprzątanie poprzedniego layout effectu','Nic','Uruchamia useEffect','Odmontowuje komponent','Sprzątanie poprzedza nowy setup.');
q('Która kolejność jest poprawna po kliknięciu w PhasesDemo?','handler, render, sprzątanie layout, layout, sprzątanie effect, effect','render, handler, layout, effect','handler, layout, render, effect','handler, render, effect, layout','Zgodnie z osią czasu.');
q('Czy render oznacza zmianę DOM?','Nie, commit zmienia tylko różnice','Tak, zawsze','Tylko przy zmianie stanu','Tylko w produkcji','Te same elementy → brak zmian w DOM.');
q('Czym jest „bailout” przy setState?','Pominięciem renderu, gdy nowa wartość jest taka sama (Object.is)','Przerwaniem commitu','Odmontowaniem komponentu','Błędem renderu','React może pominąć render komponentu i dzieci.');
q('Jakim porównaniem React sprawdza tę samą wartość stanu?','Object.is','==','JSON.stringify','Głębokim porównaniem','Nowy obiekt o tej samej treści to inna wartość.');
q('Czy setItems([...items]) z tą samą zawartością pominie render?','Nie, to nowa tablica','Tak','Tylko dla pustej tablicy','Tylko w StrictMode','Object.is porównuje referencje.');
q('Czy „Highlight updates” w DevTools oznacza problem wydajności?','Nie samo w sobie — liczy się koszt renderu','Tak, zawsze','Tak, gdy jest więcej niż jeden','Tylko w produkcji','Render może kończyć się commitem bez zmian.');
q('Gdzie przewinąć listę do nowego komentarza?','W useLayoutEffect zależnym od ID ostatniego komentarza','W handlerze zaraz po setState','W renderze','W useMemo','Węzeł jest już w DOM, ekran jeszcze nieodmalowany.');
q('Dlaczego zależność to lastId, a nie cała tablica komentarzy?','Przewijać trzeba tylko po dodaniu nowego komentarza','Bo tablic nie można dawać w zależnościach','Bo lastId jest szybszy','Bo tablica się nie zmienia','Nowa referencja tablicy bez nowego komentarza nie powinna przewijać.');
q('Dlaczego focus() zaraz po setOpen(true) nie działa?','Pole jeszcze nie istnieje — render i commit nastąpią po handlerze','Bo focus jest asynchroniczny','Bo ref nie działa z input','Bo setOpen jest synchroniczny','Aktualizacje są grupowane do końca handlera.');
q('Jak najprościej ustawić fokus na nowym polu?','autoFocus albo efekt reagujący na open','setTimeout 100 ms','focus w renderze','flushSync zawsze','autoFocus działa przy montowaniu.');
q('Co robi flushSync?','Wymusza natychmiastowy render i commit wewnątrz handlera','Opóźnia render','Wyłącza efekty','Grupuje aktualizacje','Po powrocie DOM jest aktualny.');
q('Z którego pakietu pochodzi flushSync?','react-dom','react','react-router','scheduler','To API renderera DOM.');
q('Dlaczego nadużywanie flushSync szkodzi?','Wyłącza grupowanie i wymusza dodatkowe synchroniczne commity','Bo jest niebezpieczny dla bezpieczeństwa','Bo nie działa w React 19','Bo psuje typy','Używaj rzadko, punktowo.');
q('Kiedy flushSync jest uzasadniony?','Gdy w tym samym handlerze potrzebujesz zaktualizowanego DOM (fokus, przewinięcie)','Do każdej aktualizacji','Do pobierania danych','Do animacji CSS','Albo przy integracji z kodem spoza Reacta.');
q('Co zrobi useEffect mierzący wysokość, gdy wynik zmienia układ?','Użytkownik może zobaczyć mignięcie złej pozycji przed korektą','Nic, zawsze działa idealnie','Rzuci błąd','Zablokuje render','useEffect działa po malowaniu.');
q('Dlaczego useLayoutEffect usuwa mignięcie?','Aktualizacja w nim jest renderowana i commitowana przed malowaniem','Bo jest szybszy','Bo działa w renderze','Bo pomija DOM','Przeglądarka maluje dopiero końcowy stan.');
q('Jaki jest koszt useLayoutEffect?','Blokuje malowanie do swojego zakończenia','Nie ma kosztu','Wyłącza efekty','Podwaja render','Używaj tylko do pomiarów i korekt układu.');
q('W jakiej kolejności uruchamiają się efekty rodzica i dziecka przy montowaniu?','Najpierw dziecko, potem rodzic','Najpierw rodzic, potem dziecko','Losowo','Równocześnie','Commit kończy liście przed rodzicami.');
q('W jakiej kolejności uruchamiają się dwa useEffect w jednym komponencie?','W kolejności deklaracji','Odwrotnej','Losowo','Według zależności','Kolejność hooków jest stała.');
q('Czy sprzątanie useEffect uruchamia się przed każdym nowym setupem?','Tak, gdy zmieniły się zależności','Nie, tylko przy odmontowaniu','Tylko w StrictMode','Tylko dla pustych zależności','Sprzątanie poprzedniego setupu poprzedza nowy.');
q('Co uruchamia się przy odmontowaniu komponentu z oboma efektami?','Sprzątanie layout effectu, potem sprzątanie useEffect','Tylko useEffect','Setup obu efektów','Nic','Kolejność zgodna z fazami.');
q('Co robi przeglądarka w kroku „malowanie”?','Rysuje nowy stan ekranu (styl, układ, malowanie)','Wywołuje komponenty','Uruchamia efekty Reacta','Porównuje drzewa','W Performance widać Recalculate Style, Layout, Paint.');
q('Gdzie w profilu Performance widać render Reacta?','W bloku wykonania JavaScriptu','W Paint','W Layout','W Network','Commit i malowanie następują po nim.');
q('Czy można bezpiecznie czytać ref.current w renderze?','Nie, w renderze może być null lub nieaktualny','Tak, zawsze','Tylko w TSX','Tylko przy drugim renderze','Czytaj refy w efektach i handlerach.');
q('Co odzwierciedla typ RefObject<HTMLButtonElement | null>?','Że przed commitem i po odmontowaniu ref jest pusty','Że przycisk jest opcjonalny w HTML','Że ref jest stanem','Nic','Typy pilnują faz.');
q('Co zwróci callback ref w React 19, gdy chcesz posprzątać?','Funkcję sprzątającą','null','Promise','Węzeł','Wcześniej React wołał callback z null.');
q('Czy grupowanie aktualizacji dotyczy kilku setState w jednym handlerze?','Tak, dają jeden render','Nie, każdy daje render','Tylko w klasach','Tylko w React 17','Automatyczny batching (lekcja 5).');
q('Ile commitów dadzą dwa flushSync w jednym handlerze?','Dwa','Jeden','Zero','Trzy','Każdy flushSync wymusza osobny commit.');
q('Czy React pomija render dzieci przy bailout setState?','Tak, cały komponent z dziećmi','Nie, dzieci zawsze się renderują','Tylko dzieci z memo','Tylko w produkcji','Brak zmiany stanu → brak pracy w poddrzewie.');
q('Czy render rodzica renderuje dzieci, które dostały te same props?','Tak, domyślnie','Nie','Tylko gdy mają stan','Tylko w StrictMode','memo pozwala to pominąć.');
q('Czy render dziecka z tymi samymi elementami zmienia DOM?','Nie','Tak, zawsze','Tylko atrybuty','Tylko tekst','MutationObserver nie zarejestruje zmian.');
q('Jakie narzędzie przeglądarki potwierdzi brak zmian DOM w teście?','MutationObserver','IntersectionObserver','ResizeObserver','PerformanceObserver','Zbiera rekordy zmian węzłów i atrybutów.');
q('Dlaczego w teście trzeba zamockować scrollIntoView?','jsdom go nie implementuje','Bo przewija test','Bo jest asynchroniczny','Bo React go blokuje','Mock pozwala też sprawdzić moment wywołania.');
q('Co jest wyzwoleniem przy pierwszym wyświetleniu aplikacji?','root.render','setState','useEffect','Malowanie','Montowanie to pierwszy render.');
q('Czy zmiana kontekstu wyzwala render konsumentów?','Tak','Nie','Tylko z memo','Tylko w klasach','Konsumenci renderują się przy nowej wartości.');
q('Czy commit może być przerwany przez pilniejszą aktualizację?','Nie, jest synchroniczny w całości','Tak','Tylko w transition','Tylko z Suspense','Przerywalny jest tylko render.');
q('Dlaczego log w renderze PhasesDemo jest dopuszczalny tylko do nauki?','Render powinien być czysty; log to efekt uboczny (w StrictMode wypisze się dwa razy)','Bo console.log jest wolny','Bo React go zakazuje','Bo psuje typy','To obserwacja, nie wzorzec.');
q('Co zobaczysz w PhasesDemo przy montowaniu w StrictMode?','Podwójny render i setup→sprzątanie→setup efektów','Pojedynczy render','Brak efektów','Błąd','StrictMode celowo ujawnia problemy.');
q('Czy useLayoutEffect działa przy renderowaniu na serwerze?','Nie — na serwerze nie ma DOM ani malowania','Tak','Tylko w Next.js','Tylko w React 19','Efekty uruchamiają się tylko w przeglądarce.');
q('Dlaczego aktualizacja stanu w useLayoutEffect nie powoduje mignięcia?','React synchronicznie renderuje i commituje ją przed malowaniem','Bo jest pomijana','Bo jest grupowana do useEffect','Bo przeglądarka czeka 100 ms','Użytkownik widzi tylko końcowy stan.');
q('Gdzie umieścić zapis do localStorage po zmianie stanu?','W useEffect','W renderze','W useLayoutEffect obowiązkowo','W stałej modułu','Nie wymaga blokowania malowania.');
q('Co jest prawdą o kolejności: przypisanie refów a useLayoutEffect?','Refy są przypisane przed useLayoutEffect','useLayoutEffect przed refami','Równocześnie','Refy po useEffect','Dlatego layout effect może mierzyć węzeł.');
q('Czy handler widzi nowy stan zaraz po setState?','Nie, zmienna stanu to migawka z bieżącego renderu','Tak','Tylko przy flushSync też nie','Tylko w klasach','Nowa wartość pojawi się w następnym renderze.');
q('Co widzi handler w DOM zaraz po flushSync?','Zaktualizowany DOM','Stary DOM','Pusty DOM','Błąd','Zmienna stanu w domknięciu nadal ma starą wartość.');
q('Co się stanie, gdy reducer zwróci ten sam obiekt stanu?','Komponent wykona render (reducer liczony jest w renderze), ale React pominie dzieci i nie zmieni DOM','Render zostanie pominięty jak przy setState z tą samą wartością','React rzuci błąd','Dzieci zawsze się wyrenderują','useReducer nie ma wczesnego wyjścia przed renderem; bailout następuje po obliczeniu reducera.');
q('Ile razy uruchomi się efekt bez tablicy zależności?','Po każdym commicie komponentu','Raz','Nigdy','Tylko przy odmontowaniu','Brak tablicy = synchronizacja po każdym renderze, który trafił do commitu.');
q('Ile razy uruchomi się efekt z pustą tablicą zależności (bez StrictMode)?','Raz, po zamontowaniu','Po każdym renderze','Nigdy','Dwa razy','W StrictMode (dev) — setup, cleanup, setup.');
q('Czy zmiana props, od których efekt nie zależy, uruchomi go ponownie?','Nie','Tak','Tylko w produkcji','Tylko dla useLayoutEffect','Liczą się wartości z tablicy zależności.');
q('Gdzie wykonać zapis analityczny „otworzono panel” po kliknięciu?','W handlerze kliknięcia','W useLayoutEffect','W renderze','W useMemo','Akcja użytkownika to moment zdarzenia.');
q('Co zobaczy test, który czyta DOM zaraz po wywołaniu setState w act?','Stary DOM w chwili wywołania; nowy dopiero po zakończeniu act','Nowy DOM','Pusty DOM','Błąd','act kończy render i commit na swoim końcu.');
q('Co zapewnia act w testach?','Dokończenie renderu, commitu i efektów przed asercją','Szybsze testy','Wyłączenie efektów','Tryb produkcyjny','Bez act asercje widziałyby niedokończone aktualizacje.');
q('Czy useLayoutEffect wykona się, jeśli komponent zawiesił się w Suspense?','Nie, dopiero po commicie jego treści','Tak','Tylko na serwerze','Tylko w StrictMode','Efekty należą do commitu.');
q('Jak nazywa się efekt, który odpowiada za pomiar przed malowaniem w React?','useLayoutEffect','useInsertionEffect','useEffect','useMemo','useInsertionEffect służy bibliotekom CSS-in-JS do wstrzykiwania stylów.');
q('Do czego służy useInsertionEffect?','Do wstrzykiwania stylów przez biblioteki CSS-in-JS przed layout effectami','Do pomiarów','Do pobierania danych','Do fokusu','W kodzie aplikacji prawie nigdy go nie potrzebujesz.');
q('Co robi React, gdy setState zostanie wywołany w useLayoutEffect?','Synchronicznie renderuje i commituje ponownie przed malowaniem','Odkłada render na później','Ignoruje aktualizację','Rzuca błąd','Dlatego nadaje się do korekt układu.');
q('Czy setState w useEffect może spowodować mignięcie?','Tak, pierwszy stan zostanie odmalowany przed korektą','Nie, nigdy','Tylko na serwerze','Tylko w StrictMode','To powód użycia useLayoutEffect do pomiarów.');
q('Kiedy przeglądarka wykonuje Layout i Paint względem Reacta?','Po commicie, gdy JavaScript odda sterowanie','Przed renderem','W trakcie renderu','Przed useLayoutEffect','Synchroniczny commit kończy się przed malowaniem.');
const probes=[
  ['Kolejność przy montowaniu','render,layout,effect','layout,render,effect','render,effect,layout','effect,layout,render','log.length=0;function C(){log.push("render");useLayoutEffect(()=>{log.push("layout");},[]);useEffect(()=>{log.push("effect");},[]);return null;}mount(<C/>);console.log(log.join(","));'],
  ['Ref w pierwszym renderze','null','DIV','undefined','błąd','let seen="";function C(){const ref=useRef<HTMLDivElement>(null);seen=String(ref.current);return <div ref={ref}/>;}mount(<C/>);console.log(seen);'],
  ['Ref w useLayoutEffect','DIV','null','undefined','SPAN','let seen="";function C(){const ref=useRef<HTMLDivElement>(null);useLayoutEffect(()=>{seen=ref.current?.tagName??"null";},[]);return <div ref={ref}/>;}mount(<C/>);console.log(seen);'],
  ['Ref w drugim renderze','DIV','null','undefined','SPAN','const seen:string[]=[];function C({n}:{n:number}){const ref=useRef<HTMLDivElement>(null);seen.push(ref.current?.tagName??"null");return <div ref={ref}>{n}</div>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);console.log(seen[1]);'],
  ['Ref po odmontowaniu','null','DIV','undefined','SPAN','let ref!:{current:HTMLDivElement|null};function C({show}:{show:boolean}){ref=useRef<HTMLDivElement>(null);return show?<div ref={ref}/>:<p/>;}const v=mount(<C show/>);v.update(<C show={false}/>);console.log(String(ref.current));'],
  ['Tekst DOM w layout effect po aktualizacji','2','1','pusty','undefined','let seen="";function C({n}:{n:number}){const ref=useRef<HTMLParagraphElement>(null);useLayoutEffect(()=>{seen=ref.current?.textContent??"";},[n]);return <p ref={ref}>{n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);console.log(seen);'],
  ['Sprzątanie przed setupem przy aktualizacji','cleanup 1,setup 2','setup 2,cleanup 1','setup 2','cleanup 1','log.length=0;function C({n}:{n:number}){useEffect(()=>{log.push("setup "+n);return ()=>{log.push("cleanup "+n);};},[n]);return null;}const v=mount(<C n={1}/>);log.length=0;v.update(<C n={2}/>);console.log(log.join(","));'],
  ['Kolejność sprzątania przy aktualizacji obu efektów','layout-, layout+, effect-, effect+','effect-, layout-, layout+, effect+','layout+, effect+, layout-, effect-','layout-, effect-, layout+, effect+','log.length=0;function C({n}:{n:number}){useLayoutEffect(()=>{log.push("layout+");return ()=>{log.push("layout-");};},[n]);useEffect(()=>{log.push("effect+");return ()=>{log.push("effect-");};},[n]);return null;}const v=mount(<C n={1}/>);log.length=0;v.update(<C n={2}/>);console.log(log.join(", "));'],
  ['Odmontowanie obu efektów','layout-,effect-','effect-,layout-','layout-','effect-','log.length=0;function C(){useLayoutEffect(()=>()=>{log.push("layout-");},[]);useEffect(()=>()=>{log.push("effect-");},[]);return null;}const v=mount(<C/>);v.unmount();console.log(log.join(","));'],
  ['Efekty dziecka i rodzica','dziecko,rodzic','rodzic,dziecko','dziecko','rodzic','log.length=0;function Child(){useEffect(()=>{log.push("dziecko");},[]);return null;}function Parent(){useEffect(()=>{log.push("rodzic");},[]);return <Child/>;}mount(<Parent/>);console.log(log.join(","));'],
  ['Rendery rodzica i dziecka','rodzic,dziecko','dziecko,rodzic','rodzic','dziecko','log.length=0;function Child(){log.push("dziecko");return null;}function Parent(){log.push("rodzic");return <Child/>;}mount(<Parent/>);console.log(log.join(","));'],
  ['Dwa efekty w kolejności deklaracji','A,B','B,A','A','B','log.length=0;function C(){useEffect(()=>{log.push("A");},[]);useEffect(()=>{log.push("B");},[]);return null;}mount(<C/>);console.log(log.join(","));'],
  ['Layout przed zwykłym efektem mimo kolejności deklaracji','layout,effect','effect,layout','effect','layout','log.length=0;function C(){useEffect(()=>{log.push("effect");},[]);useLayoutEffect(()=>{log.push("layout");},[]);return null;}mount(<C/>);console.log(log.join(","));'],
  ['Rendery po ustawieniu tej samej wartości','1','2','3','0','let renders=0;let set!:(v:string)=>void;function C(){const [v,setV]=useState("OPEN");set=setV;renders+=1;return <p>{v}</p>;}mount(<C/>);act(()=>set("OPEN"));act(()=>set("OPEN"));console.log(renders);'],
  ['Rendery po nowej tablicy o tej samej treści','2','1','3','0','let renders=0;let set!:(v:string[])=>void;function C(){const [v,setV]=useState<string[]>(["a"]);set=setV;renders+=1;return <p>{v.join()}</p>;}mount(<C/>);act(()=>set(["a"]));console.log(renders);'],
  ['Rendery po dwóch setState w jednym act','2','3','1','0','let renders=0;let bump!:()=>void;function C(){const [a,setA]=useState(0),[b,setB]=useState(0);bump=()=>{setA(x=>x+1);setB(x=>x+1);};renders+=1;return <p>{a+b}</p>;}mount(<C/>);act(()=>bump());console.log(renders);'],
  ['Tekst po dwóch funkcyjnych aktualizacjach','2','1','0','11','let bump!:()=>void;function C(){const [n,setN]=useState(0);bump=()=>{setN(x=>x+1);setN(x=>x+1);};return <p>{n}</p>;}const v=mount(<C/>);act(()=>bump());console.log(v.host.textContent);'],
  ['DOM zaraz po setState bez flushSync','0','1','pusty','błąd','let seen="";let bump!:()=>void;function C(){const [n,setN]=useState(0);const ref=useRef<HTMLParagraphElement>(null);bump=()=>{setN(1);seen=ref.current?.textContent??"";};return <p ref={ref}>{n}</p>;}mount(<C/>);act(()=>bump());console.log(seen);'],
  ['DOM zaraz po flushSync','1','0','pusty','błąd','let seen="";let bump!:()=>void;function C(){const [n,setN]=useState(0);const ref=useRef<HTMLParagraphElement>(null);bump=()=>{flushSync(()=>setN(1));seen=ref.current?.textContent??"";};return <p ref={ref}>{n}</p>;}mount(<C/>);act(()=>bump());console.log(seen);'],
  ['Zmienna stanu w handlerze po flushSync','0','1','undefined','2','let seen=-1;let bump!:()=>void;function C(){const [n,setN]=useState(0);bump=()=>{flushSync(()=>setN(1));seen=n;};return <p>{n}</p>;}mount(<C/>);act(()=>bump());console.log(seen);'],
  ['Rendery przy dwóch flushSync','3','2','1','4','let renders=0;let bump!:()=>void;function C(){const [n,setN]=useState(0);renders+=1;bump=()=>{flushSync(()=>setN(x=>x+1));flushSync(()=>setN(x=>x+1));};return <p>{n}</p>;}mount(<C/>);act(()=>bump());console.log(renders);'],
  ['memo przy tych samych props','1','2','0','3','let renders=0;const Child=memo(function Child({label}:{label:string}){renders+=1;return <p>{label}</p>;});const v=mount(<Child label="a"/>);v.update(<Child label="a"/>);console.log(renders);'],
  ['Bez memo przy tych samych props','2','1','0','3','let renders=0;function Child({label}:{label:string}){renders+=1;return <p>{label}</p>;}const v=mount(<Child label="a"/>);v.update(<Child label="a"/>);console.log(renders);'],
  ['Mutacje DOM przy tym samym wyniku renderu','0','1','2','undefined','function Child({tick}:{tick:number}){void tick;return <p className="x">tekst</p>;}const v=mount(<Child tick={1}/>);const observer=new MutationObserver(()=>{});observer.observe(v.host,{subtree:true,childList:true,attributes:true,characterData:true});v.update(<Child tick={2}/>);console.log(observer.takeRecords().length);observer.disconnect();'],
  ['Mutacje DOM przy zmianie tekstu','1','0','2','3','function Child({tick}:{tick:number}){return <p>{tick}</p>;}const v=mount(<Child tick={1}/>);const observer=new MutationObserver(()=>{});observer.observe(v.host,{subtree:true,childList:true,attributes:true,characterData:true});v.update(<Child tick={2}/>);console.log(observer.takeRecords().length);observer.disconnect();'],
  ['Callback ref przy montowaniu','DIV','null','undefined','pusty','let seen="";mount(<div ref={node=>{if(node)seen=node.tagName;}}/>);console.log(seen);'],
  ['Callback ref ze sprzątaniem przy odmontowaniu','+,-','+','-','pusty','log.length=0;const v=mount(<div ref={node=>{if(!node)return;log.push("+");return ()=>{log.push("-");};}}/>);v.unmount();console.log(log.join(","));'],
  ['Stan po aktualizacji w useLayoutEffect','zmierzone','start','pusty','undefined','function C(){const [v,setV]=useState("start");useLayoutEffect(()=>{if(v==="start")setV("zmierzone");},[v]);return <p>{v}</p>;}const view=mount(<C/>);console.log(view.host.textContent);'],
  ['Liczba renderów z korektą w useLayoutEffect','2','1','3','0','let renders=0;function C(){const [v,setV]=useState(0);renders+=1;useLayoutEffect(()=>{if(v===0)setV(1);},[v]);return <p>{v}</p>;}mount(<C/>);console.log(renders);'],
  ['useReducer zwracający ten sam stan','2','1','0','3','let renders=0;let send!:()=>void;function C(){const [s,dispatch]=useReducer((state:{n:number})=>state,{n:0});send=()=>dispatch();renders+=1;return <p>{s.n}</p>;}mount(<C/>);act(()=>send());console.log(renders);'],
  ['Efekt bez tablicy zależności przy trzech renderach','3','1','2','0','let runs=0;function C({n}:{n:number}){useEffect(()=>{runs+=1;});return <p>{n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);v.update(<C n={3}/>);console.log(runs);'],
  ['Efekt z pustą tablicą przy trzech renderach','1','3','2','0','let runs=0;function C({n}:{n:number}){useEffect(()=>{runs+=1;},[]);return <p>{n}</p>;}const v=mount(<C n={1}/>);v.update(<C n={2}/>);v.update(<C n={3}/>);console.log(runs);'],
  ['Efekt zależny od props bez zmiany wartości','1','2','3','0','let runs=0;function C({n,tick}:{n:number;tick:number}){void tick;useEffect(()=>{runs+=1;},[n]);return <p>{n}</p>;}const v=mount(<C n={1} tick={1}/>);v.update(<C n={1} tick={2}/>);console.log(runs);'],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 w Vitest (createRoot + act w jsdom, bez StrictMode).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/PhasesDemo.tsx':phases,'src/BailoutDemo.tsx':bailout,'src/CommentList.tsx':comments,'src/FocusPanel.tsx':focus,'src/AddWithFlushSync.tsx':addComment,'src/phases.test.tsx':phaseTests,'src/dom.test.tsx':domTests};
const mutants=[
  {file:'src/CommentList.tsx',before:'},[lastId]);',after:'},[]);'},
  {file:'src/FocusPanel.tsx',before:'useEffect(()=>{if(open)inputRef.current?.focus();},[open]);',after:'useEffect(()=>{if(open)inputRef.current?.focus();},[]);'},
  {file:'src/AddWithFlushSync.tsx',before:'flushSync(()=>setItems(list=>[...list,"Nowy "+list.length]));',after:'setItems(list=>[...list,"Nowy "+list.length]);'},
  {file:'src/PhasesDemo.tsx',before:'  useLayoutEffect(()=>{\n    log("3. layout',after:'  useEffect(()=>{\n    log("3. layout'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
