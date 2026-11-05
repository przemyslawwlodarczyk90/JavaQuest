// Lekcja 15/01: elementy React i wirtualny DOM. Zachowania sprawdza prawdziwy React 19 w Vitest (jsdom).
const badge=String.raw`export function StatusBadge({status}:{status:string}){
  return <span className="badge">{status}</span>;
}`;
const inspect=String.raw`import {isValidElement,type ReactElement,type ReactNode} from "react";
import {StatusBadge} from "./StatusBadge";
export type ElementSummary={type:string;props:string;key:string|null};
// Zwraca czytelny opis elementu: nazwę typu (tag lub funkcja), klucze props i key.
export function describeElement(node:ReactNode):ElementSummary|null{
  if(!isValidElement(node))return null;
  const element=node as ReactElement<Record<string,unknown>>;
  const type=typeof element.type==="string"?element.type:(element.type as {name?:string}).name??"anonim";
  return {type,props:Object.keys(element.props).sort().join(","),key:element.key};
}
export function inspect(){
  const component=<StatusBadge status="OPEN" key="badge-1"/>;
  const html=<div className="row">tekst</div>;
  return {component,html,sameType:component.type===StatusBadge};
}
export const renderable:ReactNode[]=[<b key="a">tak</b>,"tekst",42,null,false,undefined,true];`;
const panel=String.raw`import type {ReactNode} from "react";
let renders=0;
export const expensiveRenders={get:()=>renders,reset:()=>{renders=0;}};
export function Expensive(){
  renders+=1;
  return <p>Ciężki komponent (render #{renders})</p>;
}
export function Panel({show,extra}:{show:boolean;extra?:ReactNode}){
  const content=<Expensive/>; // tylko obiekt — Expensive jeszcze się nie wykonał
  return <section>{show?content:<p>Ukryte</p>}{extra}</section>;
}`;
const list=String.raw`import {useState} from "react";
export function Row({label}:{label:string}){
  const [open,setOpen]=useState(false);
  return <li><button type="button" onClick={()=>setOpen(o=>!o)}>{label} {open?"▲":"▼"}</button></li>;
}
export function List({labels}:{labels:string[]}){
  return <ul>{labels.map(label=><Row key={label} label={label}/>)}</ul>;
}
// Antywzorzec do porównania: komponent wywołany jak funkcja — hooki Row stają się hookami ListCalled.
export function ListCalled({labels}:{labels:string[]}){
  return <ul>{labels.map(label=>Row({label}))}</ul>;
}`;
const elementTests=String.raw`import {cloneElement,createElement,isValidElement,type ReactNode} from "react";
import {describe,expect,test} from "vitest";
import {describeElement,inspect} from "./inspect";
import {StatusBadge} from "./StatusBadge";
describe("element React to zwykły obiekt",()=>{
  test("element komponentu ma type równy funkcji, props bez key i osobne pole key",()=>{
    const {component,sameType}=inspect();
    expect(sameType).toBe(true);
    expect(component.props).toEqual({status:"OPEN"});
    expect(component.key).toBe("badge-1");
    expect(describeElement(component)).toEqual({type:"StatusBadge",props:"status",key:"badge-1"});
  });
  test("describeElement zwraca null dla tekstu i dla podróbki elementu",()=>{
    expect(describeElement("tekst")).toBeNull();
    expect(describeElement({type:"div",props:{},key:null} as unknown as ReactNode)).toBeNull();
  });
  test("element HTML ma type będący tekstem, a dzieci są w props.children",()=>{
    const {html}=inspect();
    expect(html.type).toBe("div");
    expect(html.props).toEqual({className:"row",children:"tekst"});
    expect(html.key).toBeNull();
  });
  test("$$typeof to symbol react.transitional.element",()=>{
    const element=<div/> as unknown as {$$typeof:symbol};
    expect(element.$$typeof).toBe(Symbol.for("react.transitional.element"));
  });
  test("JSON.stringify gubi symbol, więc obiekt z JSON nie jest elementem",()=>{
    const parsed:unknown=JSON.parse(JSON.stringify(<div>x</div>));
    expect(isValidElement(parsed)).toBe(false);
    expect(Object.keys(parsed as object)).not.toContain("$$typeof");
  });
  test("w trybie deweloperskim element i jego props są zamrożone",()=>{
    const element=<StatusBadge status="OPEN"/>;
    expect(Object.isFrozen(element)).toBe(true);
    expect(Object.isFrozen(element.props)).toBe(true);
  });
  test("JSX i createElement dają równoważny opis",()=>{
    const fromJsx=<StatusBadge status="CLOSED"/>,fromCall=createElement(StatusBadge,{status:"CLOSED"});
    expect(fromCall.type).toBe(fromJsx.type);
    expect(fromCall.props).toEqual(fromJsx.props);
  });
  test("cloneElement zachowuje typ i scala props, nie zmieniając oryginału",()=>{
    const original=<StatusBadge status="OPEN"/>,copy=cloneElement(original,{status:"CLOSED"});
    expect(copy.type).toBe(StatusBadge);
    expect(copy.props).toEqual({status:"CLOSED"});
    expect(original.props).toEqual({status:"OPEN"});
  });
});`;
const renderTests=String.raw`import {act,useState} from "react";
import {createRoot} from "react-dom/client";
import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {afterEach,beforeEach,expect,test,vi} from "vitest";
import {renderable} from "./inspect";
import {List,ListCalled} from "./List";
import {expensiveRenders,Panel} from "./Panel";
beforeEach(()=>expensiveRenders.reset());
afterEach(()=>vi.restoreAllMocks());
test("utworzenie elementu nie wykonuje komponentu",()=>{
  render(<Panel show={false}/>);
  expect(expensiveRenders.get()).toBe(0);
  expect(screen.getByText("Ukryte")).toBeInTheDocument();
});
test("komponent wykonuje się, gdy element trafi do drzewa",()=>{
  render(<Panel show/>);
  expect(expensiveRenders.get()).toBe(1);
  expect(screen.getByText("Ciężki komponent (render #1)")).toBeInTheDocument();
});
test("null, false, undefined i true nic nie renderują, liczba renderuje się jako tekst",()=>{
  const {container}=render(<p>{renderable}</p>);
  expect(container.textContent).toBe("taktekst42");
});
test("obiekt, który nie jest elementem, rzuca błąd przy renderze",()=>{
  vi.spyOn(console,"error").mockImplementation(()=>{});
  const bad={title:"API timeout"} as unknown as string;
  expect(()=>render(<p>{bad}</p>)).toThrow(/Objects are not valid as a React child/);
});
test("każdy wiersz <Row/> ma własny stan, także po dodaniu wiersza",async()=>{
  const user=userEvent.setup();
  function Host(){const [labels,setLabels]=useState(["A","B"]);return <><List labels={labels}/><button type="button" onClick={()=>setLabels(l=>["Z",...l])}>Dodaj</button></>;}
  render(<Host/>);
  await user.click(screen.getByRole("button",{name:"B ▼"}));
  await user.click(screen.getByRole("button",{name:"Dodaj"}));
  expect(screen.getByRole("button",{name:"B ▲"})).toBeInTheDocument();
  expect(screen.getByRole("button",{name:"Z ▼"})).toBeInTheDocument();
});
test("komponent wywołany jak funkcja psuje się przy zmianie liczby wierszy",()=>{
  vi.spyOn(console,"error").mockImplementation(()=>{});
  const container=document.createElement("div"),root=createRoot(container);
  act(()=>root.render(<ListCalled labels={["A"]}/>));
  expect(()=>act(()=>root.render(<ListCalled labels={["A","B"]}/>))).toThrow(/more hooks/);
  act(()=>root.unmount());
});`;
const header=String.raw`import {cloneElement,createElement,isValidElement,Fragment,act,useEffect,useState,type ReactElement,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
import {renderToStaticMarkup} from "react-dom/server";
import {StatusBadge} from "./StatusBadge";
import {describeElement} from "./inspect";
import {expensiveRenders,Panel} from "./Panel";
`;
const exercises=[
  ['Wypisz type, props i key elementu <StatusBadge status="OPEN" key="badge-1"/> i elementu <div className="row">tekst</div>.','Element komponentu ma type będący funkcją, element HTML — tekst.',inspect],
  ['Napisz funkcję describeElement zwracającą nazwę typu, listę kluczy props i key albo null dla wartości, która nie jest elementem.','isValidElement odróżnia element od zwykłego obiektu.',inspect],
  ['Udowodnij testem, że key nie trafia do props.','Porównaj component.props z {status:"OPEN"}.',elementTests],
  ['Sprawdź, że $$typeof elementu w React 19 to Symbol.for("react.transitional.element").','Odczytaj pole przez rzutowanie typu w teście.',elementTests],
  ['Pokaż, że po JSON.stringify i JSON.parse obiekt przestaje być elementem.','Symbol nie przechodzi przez JSON — to ochrona przed wstrzyknięciem.',elementTests],
  ['Sprawdź, że element i jego props są zamrożone w trybie deweloperskim.','Object.isFrozen na elemencie i na props.',elementTests],
  ['Porównaj element z JSX i z createElement dla tego samego komponentu.','Porównaj type i props.',elementTests],
  ['Użyj cloneElement, aby zmienić status, i sprawdź, że oryginał się nie zmienił.','Elementy są niezmienne — powstaje nowy obiekt.',elementTests],
  ['Zbuduj Panel, w którym utworzenie elementu <Expensive/> nie wykonuje komponentu, gdy show=false.','Licznik renderów w module pokazuje, czy funkcja się wykonała.',panel],
  ['Napisz test: Panel z show=false nie renderuje Expensive, a z show=true — dokładnie raz.','Zresetuj licznik przed każdym testem.',renderTests],
  ['Wyrenderuj tablicę [<b>tak</b>, "tekst", 42, null, false, undefined, true] i sprawdź tekst.','Wartości logiczne, null i undefined nic nie renderują.',renderTests],
  ['Sprawdź błąd „Objects are not valid as a React child” dla zwykłego obiektu w JSX.','Wycisz console.error szpiegiem przywracanym po teście.',renderTests],
  ['Zbuduj listę wierszy z własnym stanem otwarcia i kluczem z etykiety.','Każdy <Row/> ma własny fiber i własny useState.',list],
  ['Udowodnij, że po dodaniu wiersza na początku stan „B” zostaje przy „B”.','Szukaj przycisków po nazwie ze strzałką.',renderTests],
  ['Odtwórz błąd „Rendered more hooks” przez wywołanie komponentu jak funkcji.','Zmień liczbę etykiet z 1 na 2 w kolejnym renderze.',renderTests],
  ['Opisz trzy drzewa: elementów, fiberów i DOM dla listy dwóch wierszy.','Elementy są wyrzucane, fibery trwałe, DOM aktualizowany w commit.',String.raw`// Drzewo elementów (nowe przy każdym renderze):
// { type: List, props: { labels: ["A","B"] } }
//  └ { type: "ul", props: { children: [ {type: Row, key: "A"}, {type: Row, key: "B"} ] } }
// Drzewo fiberów (trwałe): Fiber(List) → Fiber("ul") → Fiber(Row,"A") [useState open], Fiber(Row,"B") [useState open]
// DOM: <ul><li><button>A ▼</button></li><li><button>B ▼</button></li></ul>
// Stan wiersza żyje we fiberze Row o kluczu "A" lub "B", nie w elemencie ani w funkcji Row.
export {};`],
  ['Otypuj funkcję zwracającą element jako ReactElement, a tablicę renderowalnych wartości jako ReactNode[].','ReactNode obejmuje tekst, liczby, null i tablice.',inspect],
  ['Napisz komponent StatusBadge i sprawdź, że element <StatusBadge/> nie jest węzłem DOM.','element instanceof Node jest fałszem.',String.raw`import {expect,test} from "vitest";
import {StatusBadge} from "./StatusBadge";
test("element nie jest węzłem DOM",()=>{
  const element=<StatusBadge status="OPEN"/>;
  expect(element instanceof Node).toBe(false);
  expect(typeof element).toBe("object");
});`],
  ['Zapisz element w zmiennej, przekaż go w props i wyrenderuj w slocie.','Element to wartość — można go przechowywać i przekazywać.',String.raw`import type {ReactNode} from "react";
export function Card({title,actions}:{title:string;actions:ReactNode}){
  return <article><h2>{title}</h2><footer>{actions}</footer></article>;
}
const closeButton=<button type="button">Zamknij</button>;
export function TicketCard(){return <Card title="API timeout" actions={closeButton}/>;}`],
  ['Sprawdź renderToStaticMarkup, że ten sam element można wyrenderować dwa razy.','Element to opis, nie jednorazowy obiekt DOM.',String.raw`import {renderToStaticMarkup} from "react-dom/server";
import {expect,test} from "vitest";
import {StatusBadge} from "./StatusBadge";
test("ten sam element dwa razy",()=>{
  const element=<StatusBadge status="OPEN"/>;
  expect(renderToStaticMarkup(<>{element}{element}</>)).toBe('<span class="badge">OPEN</span><span class="badge">OPEN</span>');
});`],
  ['Pokaż, że funkcja komponentu nie ma instancji — dostęp do zachowania tylko przez props lub ref.','Element nie ma metod ani stanu.',String.raw`import {useImperativeHandle,useRef,useState,type Ref} from "react";
export type CounterHandle={reset:()=>void};
export function Counter({ref}:{ref?:Ref<CounterHandle>}){
  const [count,setCount]=useState(0);
  useImperativeHandle(ref,()=>({reset:()=>setCount(0)}),[]);
  return <button type="button" onClick={()=>setCount(c=>c+1)}>Kliknięto {count}</button>;
}
export function Toolbar(){
  const counter=useRef<CounterHandle>(null);
  return <><Counter ref={counter}/><button type="button" onClick={()=>counter.current?.reset()}>Wyzeruj</button></>;
}`],
  ['Sprawdź, że Fragment z kluczem jest elementem o typie Fragment.','Fragment to też typ elementu.',String.raw`import {Fragment,isValidElement} from "react";
import {expect,test} from "vitest";
test("Fragment z kluczem",()=>{
  const element=<Fragment key="grupa"><dt>Status</dt><dd>OPEN</dd></Fragment>;
  expect(isValidElement(element)).toBe(true);
  expect(element.type).toBe(Fragment);
  expect(element.key).toBe("grupa");
});`],
  ['Wyjaśnij w komentarzu mit „wirtualny DOM jest szybszy od DOM”.','Wirtualny DOM to dodatkowa praca; zaletą jest model programowania.',String.raw`// Wirtualny DOM nie jest szybszy od ręcznie celowanych operacji DOM — to dodatkowa praca
// (tworzenie elementów i porównanie drzew). Jego zaleta: piszesz deklaratywnie "UI dla tych danych",
// a React wylicza minimalne zmiany. Przy złożonym UI ręczne, idealnie celowane operacje są trudne do utrzymania.
// Svelte i Solid dochodzą do podobnego modelu bez wirtualnego DOM (kompilacja, sygnały).
export {};`],
  ['Napisz test, że element z innym kluczem jest innym opisem, choć ma ten sam typ i props.','key należy do elementu, nie do props.',String.raw`import {expect,test} from "vitest";
import {StatusBadge} from "./StatusBadge";
test("klucz odróżnia elementy",()=>{
  const a=<StatusBadge key="a" status="OPEN"/>,b=<StatusBadge key="b" status="OPEN"/>;
  expect(a.type).toBe(b.type);
  expect(a.props).toEqual(b.props);
  expect(a.key).not.toBe(b.key);
});`],
  ['Pokaż, że children może być pojedynczym elementem, tekstem albo tablicą.','props.children nie zawsze jest tablicą.',String.raw`import {expect,test} from "vitest";
test("kształty children",()=>{
  expect((<ul><li>a</li></ul>).props.children.type).toBe("li");
  expect((<p>tekst</p>).props.children).toBe("tekst");
  expect(Array.isArray((<p>a{"b"}</p>).props.children)).toBe(true);
});`],
  ['Napisz komponent, który przyjmuje element jako props i renderuje go dwukrotnie.','Ten sam element może wystąpić w drzewie wielokrotnie.',String.raw`import type {ReactElement} from "react";
export function Twice({item}:{item:ReactElement}){
  return <div>{item}{item}</div>;
}`],
  ['Napisz test pokazujący, że stan nie żyje w elemencie: dwa renderowania tego samego elementu mają osobny stan.','Każde miejsce w drzewie to osobny fiber.',String.raw`import {render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test} from "vitest";
import {Row} from "./List";
test("ten sam element w dwóch miejscach ma dwa stany",async()=>{
  const row=<Row label="A"/>;
  render(<ul>{row}<li>—</li>{row}</ul>);
  const [first,second]=screen.getAllByRole("button",{name:"A ▼"});
  await userEvent.click(first);
  expect(first).toHaveTextContent("A ▲");
  expect(second).toHaveTextContent("A ▼");
});`],
  ['Zastąp wywołanie Row({label}) poprawnym JSX i wyjaśnij różnicę w komentarzu.','JSX tworzy element; React sam wywoła funkcję we własnym fiberze.',list],
  ['Napisz test: zwykły obiekt z polami type i props nie jest elementem bez $$typeof.','isValidElement sprawdza znacznik.',String.raw`import {isValidElement} from "react";
import {expect,test} from "vitest";
test("podróbka elementu",()=>{
  expect(isValidElement({type:"div",props:{children:"x"},key:null})).toBe(false);
});`],
  ['Zanotuj, które informacje widzisz w React DevTools, a których nie ma w elemencie.','Hooki i stan pochodzą z fibera.',String.raw`// React DevTools dla zaznaczonego TicketRow pokazuje:
// - props (są też w elemencie),
// - hooki i ich aktualne wartości (tylko we fiberze),
// - "rendered by" — rodzica, który wyrenderował komponent (struktura fiberów),
// - klucz (z elementu, zapamiętany we fiberze).
// Element nie zawiera stanu, hooków ani informacji o renderach.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Czym jest element React?','Zwykłym, niezmiennym obiektem opisującym, co narysować','Węzłem DOM','Instancją komponentu z metodami','Funkcją komponentu','JSX kompiluje się do wywołania jsx(), które zwraca obiekt {type, props, key}.');
q('Co jest w polu type elementu <StatusBadge/>?','Funkcja komponentu StatusBadge','Tekst "StatusBadge"','Węzeł span','Obiekt props','Dla komponentów type to funkcja; dla tagów HTML — tekst.');
q('Co jest w polu type elementu <div/>?','Tekst "div"','Funkcja div','HTMLDivElement','null','Elementy HTML mają type będący nazwą tagu.');
q('Gdzie trafia key podany w JSX?','Do osobnego pola key elementu, nie do props','Do props.key','Do atrybutu DOM','Do stanu komponentu','Komponent nie odczyta key z props.');
q('Do czego służy $$typeof?','Znacznik bezpieczeństwa: React nie wyrenderuje obiektu bez właściwego symbolu','Do określenia typu props','Do debugowania w DevTools','Do serializacji JSON','Symbol nie przechodzi przez JSON, więc dane z serwera nie udadzą elementu.');
q('Jaki symbol ma $$typeof w React 19?','Symbol.for("react.transitional.element")','Symbol.for("react.element")','Symbol("jsx")','Brak symbolu','React 19 zmienił znacznik na react.transitional.element.');
q('Co robi kompilator z <StatusBadge status="OPEN"/>?','Zamienia na wywołanie jsx(StatusBadge, {status: "OPEN"})','Wywołuje StatusBadge od razu','Tworzy węzeł DOM','Zamienia na HTML','Automatyczny runtime JSX importuje jsx z react/jsx-runtime.');
q('Czy utworzenie elementu <Expensive/> wykonuje funkcję Expensive?','Nie, wykona ją React, gdy element trafi do drzewa','Tak, zawsze','Tak, ale tylko raz','Tylko w StrictMode','Element to tylko opis.');
q('Czym jest „wirtualny DOM”?','Drzewem elementów porównywanym z poprzednim, by wyliczyć zmiany w DOM','Kopią DOM w przeglądarce','Szybszą wersją DOM','Silnikiem renderowania CSS','To potoczna nazwa drzewa lekkich obiektów.');
q('Czy wirtualny DOM jest szybszy od DOM?','Nie, to dodatkowa praca; zaletą jest model programowania','Tak, zawsze','Tak, dla małych list','Tylko w produkcji','Ręcznie celowane operacje DOM są szybsze, ale trudne w utrzymaniu.');
q('Gdzie żyje stan komponentu?','W trwałym fiberze w drzewie Reacta','W elemencie','W funkcji komponentu','W DOM','Dlatego stan zależy od pozycji i klucza w drzewie.');
q('Które drzewo jest tworzone od nowa przy każdym renderze?','Drzewo elementów','Drzewo fiberów','DOM','Drzewo stanu','Fibery są trwałe, elementy jednorazowe.');
q('Czym różni się ReactElement od ReactNode?','ReactElement to obiekt elementu, ReactNode — wszystko renderowalne','Niczym','ReactNode to węzeł DOM','ReactElement obejmuje tekst','children typujesz jako ReactNode.');
q('Co wyrenderuje {false} w JSX?','Nic','Tekst "false"','Pusty span','Błąd','Wartości logiczne, null i undefined nic nie renderują.');
q('Co wyrenderuje {0} w JSX?','Tekst "0"','Nic','Błąd','Pusty element','Liczba renderuje się jako tekst — stąd pułapka {count && <X/>}.');
q('Co się stanie, gdy w JSX wstawisz zwykły obiekt {title:"x"}?','Błąd „Objects are not valid as a React child”','Wyrenderuje [object Object]','Wyrenderuje JSON','Nic','Obiekt nie jest renderowalny.');
q('Dlaczego nie wywołuje się komponentu jak funkcji Row({label})?','Hooki Row stają się hookami rodzica, nie działa key ani osobny fiber','Bo jest wolniej','Bo TypeScript zabrania','Bo nie zwraca elementu','React nie widzi komponentu, tylko jego wynik.');
q('Jaki błąd pojawi się po zwiększeniu liczby wierszy przy Row({label})?','Rendered more hooks than during the previous render','Objects are not valid as a React child','Maximum update depth exceeded','Invalid hook call','Liczba hooków rodzica zmienia się między renderami.');
q('Czy element ma metody lub stan?','Nie, to opis bez instancji','Tak, metody klasy','Tak, stan useState','Tak, ref','Dostęp do zachowania daje props lub ref z useImperativeHandle.');
q('Jak uzyskać dostęp do zachowania komponentu funkcyjnego z zewnątrz?','Przez props (dane w dół, callbacki w górę) albo ref z useImperativeHandle','Przez element.instance','Przez element.props.state','Przez document.querySelector','Komponenty funkcyjne nie mają instancji.');
q('Czy elementy są zamrożone?','Tak, w trybie deweloperskim element i props są zamrożone','Nie, można je modyfikować','Tylko props','Tylko w produkcji','Niezmienność pozwala bezpiecznie je porównywać i przekazywać.');
q('Jak zmienić props istniejącego elementu?','Utworzyć nowy, np. cloneElement(el, {...})','Przypisać el.props.status = ...','Wywołać el.setProps','Nie da się w żaden sposób','cloneElement tworzy nowy element, oryginał zostaje.');
q('Co robi isValidElement(value)?','Sprawdza, czy wartość jest elementem React','Sprawdza poprawność props','Renderuje element','Sprawdza typy TS','Sprawdza m.in. znacznik $$typeof.');
q('Dlaczego JSON.stringify(element) gubi $$typeof?','Symbole nie są serializowane do JSON','Bo React go usuwa','Bo pole jest prywatne','Bo to getter','Dlatego obiekt z JSON nie zostanie wyrenderowany jako element.');
q('Co daje ochrona $$typeof przed atakiem?','Dane JSON od użytkownika nie mogą udawać elementu i wstrzyknąć struktury','Szyfruje props','Blokuje XSS w atrybutach','Usuwa skrypty z tekstu','Atakujący nie umieści symbolu w JSON.');
q('Kiedy React wywołuje funkcję komponentu?','Gdy element komponentu jest w renderowanym drzewie','Przy imporcie modułu','Przy tworzeniu elementu','Po commit','Render to wywołanie funkcji przez React.');
q('Czy ten sam obiekt elementu można wyrenderować w dwóch miejscach?','Tak, każde miejsce dostanie własny fiber i stan','Nie, rzuci błąd','Tak, ale ze wspólnym stanem','Tylko z kluczem','Element to opis; miejsce w drzewie decyduje o fiberze.');
q('Co pokazuje React DevTools dla komponentu, czego nie ma w elemencie?','Hooki i ich wartości','Props','Typ komponentu','Klucz','Dane hooków pochodzą z fibera.');
q('Co to jest render w Reacie?','Wywołanie funkcji komponentu i wyliczenie nowego drzewa elementów','Zmiana DOM','Malowanie przez przeglądarkę','Pobranie danych','Render nie oznacza zmiany DOM — tę robi commit.');
q('Co robi faza commit?','Nanosi wyliczone różnice na prawdziwy DOM','Wywołuje komponenty','Tworzy elementy','Pobiera dane','Szczegóły w lekcji o fazach.');
q('Co jest zaletą deklaratywnego modelu?','Opisujesz UI dla danych, a React wylicza zmiany','Zawsze najszybsze operacje DOM','Brak potrzeby stanu','Brak renderów','Złożony UI łatwiej utrzymać.');
q('Jak frameworki bez wirtualnego DOM osiągają podobny model?','Kompilacją (Svelte) lub sygnałami (Solid)','Nie osiągają','Kopiowaniem DOM','Przez Web Workers','To inna droga do deklaratywnego UI.');
q('Co zawiera props.children elementu <p>tekst</p>?','Tekst "tekst"','Tablicę ["tekst"]','Element tekstowy','null','Jedno dziecko nie jest opakowywane w tablicę.');
q('Co zawiera props.children elementu <p>a{"b"}</p>?','Tablicę dwóch tekstów','Tekst "ab"','Jeden tekst "a"','null','Kilka dzieci tworzy tablicę.');
q('Czym jest Fragment w drzewie elementów?','Elementem o typie Fragment, grupującym dzieci bez węzła DOM','Węzłem DOM','Komponentem z stanem','Tablicą','Fragment z kluczem przydaje się w listach.');
q('Czy ReactNode obejmuje undefined?','Tak','Nie','Tylko w React 18','Tylko w JSX','Komponent w React 18+ może zwrócić undefined.');
q('Co zwraca createElement("div", {id:"x"}, "a")?','Element div z props {id:"x", children:"a"}','HTMLDivElement','Tekst HTML','Fiber','To API pod JSX.');
q('Kiedy powstaje fiber dla elementu?','Gdy React przetwarza element w renderowanym drzewie','Przy tworzeniu elementu','Przy imporcie','W commit','Fibery tworzy i aktualizuje rekoncyliacja.');
q('Dlaczego elementy są tanie?','To małe obiekty opisu, tworzone i wyrzucane przy renderze','Bo są cache’owane','Bo są w DOM','Bo nie mają props','Koszt leży w wywołaniu komponentów i commit, nie w samym obiekcie.');
q('Co jest „opisem”, a co „wykonaniem” w Reacie?','Element to opis, render komponentu to wykonanie','Element to wykonanie','Fiber to opis','DOM to opis','Rozróżnienie tłumaczy, dlaczego element nie wywołuje komponentu.');
q('Czy można przekazać element jako props?','Tak, element to wartość (sloty)','Nie','Tylko jako children','Tylko z kluczem','Tak działają np. props actions, icon.');
q('Czym jest slot w komponencie?','Props przyjmujący element do wyrenderowania w określonym miejscu','Hook','Fiber','Atrybut DOM','Rozdział 02: kompozycja przez sloty.');
q('Co wypisze el.type === StatusBadge dla <StatusBadge/>?','true','false','undefined','Błąd','type wskazuje funkcję komponentu.');
q('Dlaczego komponent wywołany jak funkcja nie pojawia się w DevTools?','Nie ma własnego fibera','Bo DevTools go ukrywa','Bo nie ma nazwy','Bo jest w produkcji','React widzi tylko zwrócone elementy.');
q('Dlaczego key nie zadziała przy Row({label, key})?','key ma znaczenie tylko dla elementu, a tu nie ma elementu Row','Bo key jest liczbą','Bo key musi być w JSX','Bo Row nie ma props','Zwrócony li nie dostaje klucza Row.');
q('Czy React porównuje drzewa elementów, czy DOM?','Drzewa elementów z fiberami poprzedniego renderu','DOM z DOM','HTML z HTML','JSON z JSON','DOM jest tylko odczytywany w commit.');
q('Po co React tworzy nowe elementy przy każdym renderze zamiast modyfikować stare?','Niezmienność upraszcza porównanie i model','Bo stare są usuwane z pamięci natychmiast','Bo DOM tego wymaga','Bo TypeScript tego wymaga','Nowy opis porównuje się z poprzednim stanem fiberów.');
q('Co będzie w props dla <input value="a" onChange={f}/>?','{value:"a", onChange:f}','{value:"a"}','{onChange:f}','{}','Wszystkie atrybuty JSX poza key (i ref w starszych wersjach) trafiają do props.');
q('Gdzie jest ref w React 19 dla komponentów funkcyjnych?','W props jako zwykły props ref','W osobnym polu ref elementu','Nie istnieje','W stanie','React 19 przekazuje ref jako props.');
q('Jak nazywa się mechanizm porównywania drzew?','Rekoncyliacja','Hydratacja','Batching','Memoizacja','Temat następnej lekcji.');
q('Czy element można wyrenderować bez DOM?','Tak, np. renderToStaticMarkup tworzy tekst HTML','Nie','Tylko w przeglądarce','Tylko w React Native','Element to opis niezależny od platformy.');
q('Dlaczego ten sam element działa w React DOM i React Native?','Element jest opisem; renderer platformy decyduje, co z nim zrobić','Bo React Native ma DOM','Bo elementy są HTML','Nie działa','Renderery tłumaczą opis na platformę.');
q('Co jest typem zwracanym przez funkcję komponentu w TS?','ReactNode (najczęściej element JSX)','HTMLElement','Fiber','string zawsze','Komponent może zwrócić element, tekst, null.');
q('Co robi automatyczny runtime JSX (react/jsx-runtime)?','Kompilator sam importuje funkcję jsx, więc nie trzeba importować React w każdym pliku','Wykonuje komponenty w czasie kompilacji','Zamienia JSX na HTML','Wyłącza createElement','Od React 17 import React nie jest potrzebny dla JSX.');
q('Czym różni się jsx() od createElement()?','jsx() przyjmuje key osobno i jest używany przez kompilator; createElement to publiczne API','Niczym','jsx() renderuje do DOM','createElement nie istnieje w React 19','Oba tworzą element; różnią się sygnaturą.');
q('Dlaczego element zawiera key, a nie komponent?','Klucz służy rodzicowi do dopasowania elementów w liście, nie komponentowi','Bo komponent nie ma props','Bo key jest prywatny dla DOM','Bo tak wymaga TypeScript','Komponent nie powinien zależeć od własnego klucza.');
q('Co się stanie, jeśli komponent potrzebuje wartości klucza?','Trzeba przekazać ją osobnym props, np. id','Odczyta ją z props.key','Odczyta z this.key','Odczyta z element.key wewnątrz','key nie trafia do props; w trybie deweloperskim props ma tylko nieenumerowalny getter key, który przy odczycie ostrzega i zwraca undefined.');
q('Czy element można bezpiecznie współdzielić między renderami?','Tak, jest niezmienny','Nie, React go modyfikuje','Tylko w produkcji','Tylko bez dzieci','Niezmienność pozwala go zapisać w zmiennej i użyć ponownie.');
q('Dlaczego render komponentu nie musi oznaczać zmiany DOM?','Jeśli nowe drzewo nie różni się od poprzedniego, commit nic nie zmienia','Bo DOM jest zamrożony','Bo render nie wywołuje funkcji','Bo React zawsze pomija render','Render liczy opis; commit nanosi tylko różnice.');
q('Gdzie React trzyma informację o tym, który komponent wyrenderował inny?','W strukturze fiberów (relacje rodzic–dziecko)','W elemencie','W DOM','W props','DevTools pokazuje „rendered by” z fiberów.');
q('Co oznacza, że element jest „opisem”?','Mówi, co ma się pojawić, a nie jak to zrobić','Zawiera gotowy DOM','Jest kodem CSS','Jest instrukcją imperatywną','Deklaratywny opis wyniku.');
q('Jaką rolę pełni renderer (react-dom, react-native)?','Zamienia opis elementów na operacje danej platformy','Tworzy elementy','Kompiluje JSX','Przechowuje stan','Rdzeń Reacta jest niezależny od platformy.');
q('Czy można wyrenderować komponent przekazując jego funkcję jako dziecko {StatusBadge}?','Nie, funkcja nie jest renderowalnym dzieckiem','Tak, React go wywoła','Tak, ale bez props','Tylko w JSX','Potrzebny element <StatusBadge/>; funkcja jako dziecko wywoła ostrzeżenie.');
q('Co zrobić, by komponent w liście zachował swój stan po zmianie kolejności?','Nadać stabilny key z identyfikatora danych','Wywołać go jak funkcję','Użyć indeksu jako klucza','Przechować stan w elemencie','Klucz dopasowuje element do właściwego fibera.');
q('Co zawiera props.children dla <List>{a}{b}</List>?','Tablicę dwóch wartości','Tylko a','Tylko b','Obiekt {a,b}','Wiele dzieci tworzy tablicę.');
q('Czy elementy zawierają funkcje obsługi zdarzeń?','Tak, jako zwykłe wartości props (np. onClick)','Nie, zdarzenia są w DOM','Tylko w klasach','Tylko po commit','React podpina obsługę podczas commit.');
q('Dlaczego porównanie elementów jest tanie, a wywołanie komponentu może być drogie?','Element to mały obiekt; komponent może wykonywać kosztowne obliczenia i renderować poddrzewo','Bo elementy są cache’owane w DOM','Bo komponenty są asynchroniczne','Nie ma różnicy','Optymalizacje skupiają się na unikaniu zbędnych renderów poddrzew.');
q('Jakie trzy drzewa warto rozróżniać, myśląc o Reakcie?','Elementów, fiberów i DOM','Komponentów, klas i stylów','HTML, CSS i JS','Props, stanu i efektów','Każde ma inny czas życia i rolę.');
q('Które drzewo jest trwałe między renderami?','Drzewo fiberów','Drzewo elementów','Drzewo props','Żadne','Fibery przechowują stan i hooki.');
q('Co wypisze console.log dla elementu w przeglądarce?','Obiekt z polami $$typeof, type, key, props','Wyrenderowany HTML','Węzeł DOM','Funkcję komponentu','To zwykły obiekt.');
const probes=[
  ['type elementu div','div','DIV','HTMLDivElement','undefined','console.log((<div/>).type);'],
  ['Nazwa typu komponentu','StatusBadge','span','badge','anonim','console.log(describeElement(<StatusBadge status="OPEN"/>)?.type);'],
  ['key wśród kluczy props','false','true','badge-1','undefined','const el=<StatusBadge status="OPEN" key="badge-1"/>;console.log(Object.keys(el.props).includes("key"));'],
  ['Wartość key','badge-1','null','undefined','OPEN','console.log((<StatusBadge status="OPEN" key="badge-1"/>).key);'],
  ['key bez podania','null','undefined','""','0','console.log(String((<StatusBadge status="OPEN"/>).key));'],
  ['Liczba kluczy props','1','2','0','3','console.log(Object.keys((<StatusBadge status="OPEN" key="k"/>).props).length);'],
  ['children pojedynczego tekstu','tekst','["tekst"]','undefined','null','console.log((<p>tekst</p>).props.children);'],
  ['children dwóch dzieci','true','false','undefined','2','console.log(Array.isArray((<p>a{"b"}</p>).props.children));'],
  ['Symbol elementu','true','false','undefined','null','console.log((<div/> as unknown as {$$typeof:symbol}).$$typeof===Symbol.for("react.transitional.element"));'],
  ['Element po JSON','false','true','null','undefined','console.log(isValidElement(JSON.parse(JSON.stringify(<div>x</div>))));'],
  ['Podróbka elementu','false','true','null','Błąd','console.log(isValidElement({type:"div",props:{},key:null}));'],
  ['Zamrożony element','true','false','undefined','null','console.log(Object.isFrozen(<div/>));'],
  ['Oryginał po cloneElement','OPEN','CLOSED','undefined','null','const original=<StatusBadge status="OPEN"/>;cloneElement(original,{status:"CLOSED"});console.log(original.props.status);'],
  ['Kopia po cloneElement','CLOSED','OPEN','undefined','null','console.log(cloneElement(<StatusBadge status="OPEN"/>,{status:"CLOSED"}).props.status);'],
  ['createElement z dzieckiem','a','undefined','["a"]','null','console.log((createElement("div",{id:"x"},"a").props as {children?:unknown}).children);'],
  ['HTML z renderToStaticMarkup','<span class="badge">OPEN</span>','<StatusBadge status="OPEN"/>','<span>OPEN</span>','OPEN','console.log(renderToStaticMarkup(<StatusBadge status="OPEN"/>));'],
  ['Wartości logiczne w JSX','ab','atrueb','afalseb','a b','console.log(renderToStaticMarkup(<p>a{true}{false}{null}b</p>).replace("<p>","").replace("</p>",""));'],
  ['Zero w JSX','0','""','false','null','const count=0;console.log(renderToStaticMarkup(<p>{count&&<b>x</b>}</p>).replace("<p>","").replace("</p>",""));'],
  ['Rendery Expensive przy show=false','0','1','2','undefined','expensiveRenders.reset();renderToStaticMarkup(<Panel show={false}/>);console.log(expensiveRenders.get());'],
  ['Rendery Expensive przy show=true','1','0','2','undefined','expensiveRenders.reset();renderToStaticMarkup(<Panel show/>);console.log(expensiveRenders.get());'],
  ['Utworzenie elementu bez renderu','0','1','2','undefined','expensiveRenders.reset();const elements=[<Panel show key="1"/>,<Panel show key="2"/>];void elements;console.log(expensiveRenders.get());'],
  ['Ten sam element dwa razy','2','1','0','undefined','expensiveRenders.reset();const panel=<Panel show/>;renderToStaticMarkup(<>{panel}{panel}</>);console.log(expensiveRenders.get());'],
  ['typeof elementu','object','function','string','symbol','console.log(typeof <div/>);'],
  ['Element a węzeł DOM','false','true','undefined','null','console.log((<div/> as unknown) instanceof Node);'],
  ['Typ Fragmentu','true','false','undefined','null','console.log((<Fragment key="g"><b/></Fragment>).type===Fragment);'],
  ['Liczba dzieci listy','3','1','0','2','console.log(((<ul>{["a","b","c"].map(x=><li key={x}>{x}</li>)}</ul>).props.children as unknown[]).length);'],
  ['Klucz dziecka listy','b','1','null','li','const items=(<ul>{["a","b"].map(x=><li key={x}>{x}</li>)}</ul>).props.children as ReactElement[];console.log(items[1].key);'],
  ['Stan ustawiony w efekcie po act','tak','nie','undefined','null','function Probe(){const [v,setV]=useState("nie");useEffect(()=>{setV("tak");},[]);return <p>{v}</p>;}const host=document.createElement("div"),root=createRoot(host);await act(async()=>{root.render(<Probe/>);});console.log(host.textContent);act(()=>root.unmount());'],
  ['Tekst po renderze createRoot','OPEN','<span>OPEN</span>','StatusBadge','pusty','const host=document.createElement("div"),root=createRoot(host);act(()=>root.render(<StatusBadge status="OPEN"/>));console.log(host.textContent);act(()=>root.unmount());'],
  ['Klasa po renderze','badge','className','status','null','const host=document.createElement("div"),root=createRoot(host);act(()=>root.render(<StatusBadge status="OPEN"/>));console.log(host.firstElementChild?.className);act(()=>root.unmount());'],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia kodu na React 19 w Vitest (jsdom).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/StatusBadge.tsx':badge,'src/inspect.tsx':inspect,'src/Panel.tsx':panel,'src/List.tsx':list,'src/elements.test.tsx':elementTests,'src/render.test.tsx':renderTests};
const mutants=[
  {file:'src/Panel.tsx',before:'const content=<Expensive/>;',after:'const content=Expensive();'},
  {file:'src/List.tsx',before:'{labels.map(label=><Row key={label} label={label}/>)}</ul>;\n}\n// Antywzorzec',after:'{labels.map((label,index)=><Row key={index} label={label}/>)}</ul>;\n}\n// Antywzorzec'},
  {file:'src/inspect.tsx',before:'if(!isValidElement(node))return null;',after:'if(!node||typeof node!=="object")return null;'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
