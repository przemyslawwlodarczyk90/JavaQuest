const {lesson}=require('./practice_kit.cjs');
const animationsCss=String.raw`/* animations.css */
.filters-panel {
  transform: translateX(-100%);
  opacity: 0;
  transition: transform 220ms ease-out, opacity 220ms ease-out;
}
.filters-panel--open {
  transform: translateX(0);
  opacity: 1;
}
.collapse {
  display: grid;
  grid-template-rows: 0fr;
  transition: grid-template-rows 250ms ease;
}
.collapse--open {
  grid-template-rows: 1fr;
}
.collapse > .collapse__inner {
  overflow: hidden;
}
@media (prefers-reduced-motion: reduce) {
  .filters-panel,
  .collapse {
    transition: none;
  }
}`;
const toastCss=String.raw`/* toast.css */
@keyframes toast-in {
  from { transform: translateY(16px); opacity: 0; }
  to { transform: translateY(0); opacity: 1; }
}
@keyframes toast-out {
  to { transform: translateX(110%); opacity: 0; }
}
.toast {
  animation: toast-in 200ms ease-out;
}
.toast--leaving {
  animation: toast-out 180ms ease-in forwards;
}
@media (prefers-reduced-motion: reduce) {
  .toast,
  .toast--leaving {
    animation: none;
  }
}`;
const dialogCss=String.raw`/* dialog.css */
dialog {
  opacity: 0;
  transform: scale(0.96);
  transition: opacity 200ms ease, transform 200ms ease, overlay 200ms allow-discrete, display 200ms allow-discrete;
}
dialog[open] {
  opacity: 1;
  transform: scale(1);
}
@starting-style {
  dialog[open] {
    opacity: 0;
    transform: scale(0.96);
  }
}
@media (prefers-reduced-motion: reduce) {
  dialog {
    transition: none;
  }
}`;
const filters=String.raw`import {useState,type ReactNode} from "react";

export function TicketFilters({children}:{children:ReactNode}){
  const [open,setOpen]=useState(false);
  return (
    <>
      <button type="button" aria-expanded={open} aria-controls="filters" onClick={()=>setOpen(value=>!value)}>
        Filtry
      </button>
      <aside id="filters" className={open?"filters-panel filters-panel--open":"filters-panel"} inert={!open}>
        {children}
      </aside>
    </>
  );
}

export function TicketDescription({text}:{text:string}){
  const [expanded,setExpanded]=useState(false);
  return (
    <div>
      <div className={expanded?"collapse collapse--open":"collapse"}>
        <div className="collapse__inner" inert={!expanded}><p>{text}</p></div>
      </div>
      <button type="button" aria-expanded={expanded} onClick={()=>setExpanded(value=>!value)}>
        {expanded?"Zwiń opis":"Pokaż opis"}
      </button>
    </div>
  );
}`;
const toast=String.raw`import {useEffect,useRef,useState,type AnimationEvent} from "react";

type Phase="visible"|"leaving";
type ToastProps={message:string;onDismiss:()=>void;duration?:number};

const reducedMotion=()=>window.matchMedia("(prefers-reduced-motion: reduce)").matches;

export function Toast({message,onDismiss,duration=3000}:ToastProps){
  const [phase,setPhase]=useState<Phase>("visible");
  const dismiss=useRef(onDismiss);
  useEffect(()=>{dismiss.current=onDismiss;});

  function startLeaving(){
    if(reducedMotion())dismiss.current();
    else setPhase("leaving");
  }

  useEffect(()=>{
    const id=setTimeout(startLeaving,duration);
    return ()=>clearTimeout(id);
    // eslint-disable-next-line react-hooks/exhaustive-deps -- licznik startuje raz, przy pojawieniu się.
  },[duration]);

  function handleAnimationEnd(event:AnimationEvent<HTMLDivElement>){
    if(event.animationName==="toast-out")onDismiss();
  }

  return (
    <div role="status" className={phase==="leaving"?"toast toast--leaving":"toast"} onAnimationEnd={handleAnimationEnd}>
      {message}
      <button type="button" aria-label="Zamknij" onClick={startLeaving}>×</button>
    </div>
  );
}`;
const hook=String.raw`import {useSyncExternalStore} from "react";

const QUERY="(prefers-reduced-motion: reduce)";

function subscribe(onChange:()=>void){
  const media=window.matchMedia(QUERY);
  media.addEventListener("change",onChange);
  return ()=>media.removeEventListener("change",onChange);
}

export function usePrefersReducedMotion(){
  return useSyncExternalStore(subscribe,()=>window.matchMedia(QUERY).matches,()=>false);
}

export function scrollBehavior(reduced:boolean):ScrollBehavior{
  return reduced?"auto":"smooth";
}`;
const audit=String.raw`// Prosty przegląd CSS: wyłapuje typowe błędy, ale nie zastępuje zakładki Performance.
const LAYOUT=["width","height","top","left","right","bottom","margin","padding"];
const LIMIT_MS=300;

function durations(css:string){
  return [...css.matchAll(/(\d*\.?\d+)(ms|s)\b/g)].map(match=>Number(match[1])*(match[2]==="s"?1000:1));
}

function transitionedProperties(css:string){
  const lists=[...css.matchAll(/transition\s*:\s*([^;]+);/g)].map(match=>match[1]);
  return lists.flatMap(list=>list.split(",").map(part=>part.trim().split(/\s+/)[0])).filter(name=>name!=="none");
}

function keyframeProperties(css:string){
  const blocks=[...css.matchAll(/@keyframes[^{]+\{((?:[^{}]*\{[^{}]*\})*)\s*\}/g)].map(match=>match[1]);
  return blocks.flatMap(block=>[...block.matchAll(/([a-z-]+)\s*:/g)].map(match=>match[1]));
}

export function auditCss(css:string){
  const problems:string[]=[];
  const properties=[...transitionedProperties(css),...keyframeProperties(css)];
  for(const name of new Set(properties))if(LAYOUT.includes(name))problems.push("animacja układu: "+name);
  if(properties.length&&!/prefers-reduced-motion/.test(css))problems.push("brak prefers-reduced-motion");
  if(durations(css).some(value=>value>LIMIT_MS))problems.push("animacja dłuższa niż "+LIMIT_MS+" ms");
  return problems;
}`;
const lab=String.raw`import {fireEvent,render} from "@testing-library/react";
import {TicketDescription,TicketFilters} from "./TicketFilters";

export function setReducedMotion(matches:boolean){
  const listeners=new Set<()=>void>();
  window.matchMedia=((query:string)=>({
    matches,media:query,onchange:null,
    addEventListener:(_:string,listener:()=>void)=>listeners.add(listener),
    removeEventListener:(_:string,listener:()=>void)=>listeners.delete(listener),
    addListener:()=>{},removeListener:()=>{},dispatchEvent:()=>false,
  })) as unknown as typeof window.matchMedia;
  return listeners;
}

// jsdom nie ma AnimationEvent, a React nasłuchuje wtedy nazwy z prefiksem.
export function endAnimation(element:Element,name:string){
  for(const type of ["animationend","webkitAnimationEnd"]){
    const event=new Event(type,{bubbles:true});
    Object.defineProperty(event,"animationName",{value:name});
    fireEvent(element,event);
  }
}

export function observeFilters(clicks:number){
  const view=render(<TicketFilters><a href="/x">Tylko otwarte</a></TicketFilters>);
  try{
    const button=view.getByRole("button",{name:"Filtry"});
    for(let i=0;i<clicks;i++)fireEvent.click(button);
    const panel=view.container.querySelector("aside")!;
    return {className:panel.className,inert:panel.hasAttribute("inert"),expanded:button.getAttribute("aria-expanded"),inDom:view.container.contains(panel)};
  }finally{view.unmount();}
}

export function observeDescription(clicks:number){
  const view=render(<TicketDescription text="Treść"/>);
  try{
    const button=view.getByRole("button");
    for(let i=0;i<clicks;i++)fireEvent.click(button);
    return {label:button.textContent,open:view.container.querySelector(".collapse")!.classList.contains("collapse--open"),text:view.container.textContent!.includes("Treść")};
  }finally{view.unmount();}
}`;
const tests=String.raw`import {afterEach,beforeEach,expect,test,vi} from "vitest";
import {act,fireEvent,render,renderHook,screen} from "@testing-library/react";
import {TicketDescription,TicketFilters} from "./TicketFilters";
import {Toast} from "./Toast";
import {scrollBehavior,usePrefersReducedMotion} from "./usePrefersReducedMotion";
import {auditCss} from "./cssAudit";
import {endAnimation,setReducedMotion} from "./animationLab";
import {readFileSync} from "node:fs";
import {resolve} from "node:path";

const css=(file:string)=>readFileSync(resolve(process.cwd(),"src",file),"utf8");

beforeEach(()=>{vi.useFakeTimers();setReducedMotion(false);});
afterEach(()=>{vi.useRealTimers();});

test("panel zostaje w DOM, zmienia klasę i wypada z nawigacji przez inert",()=>{
  render(<TicketFilters><a href="/x">Tylko otwarte</a></TicketFilters>);
  const button=screen.getByRole("button",{name:"Filtry"});
  const panel=document.getElementById("filters")!;
  expect(panel).toHaveAttribute("inert");
  expect(button).toHaveAttribute("aria-expanded","false");
  fireEvent.click(button);
  expect(panel).toHaveClass("filters-panel--open");
  expect(panel).not.toHaveAttribute("inert");
  expect(button).toHaveAttribute("aria-expanded","true");
  fireEvent.click(button);
  expect(panel).toBeInTheDocument();
  expect(panel).toHaveAttribute("inert");
});

test("opis przełącza klasę i etykietę, a zwinięta treść jest inert",()=>{
  const view=render(<TicketDescription text="Opis"/>);
  const button=screen.getByRole("button",{name:"Pokaż opis"});
  expect(view.container.querySelector(".collapse__inner")).toHaveAttribute("inert");
  fireEvent.click(button);
  expect(view.container.querySelector(".collapse")).toHaveClass("collapse--open");
  expect(screen.getByRole("button",{name:"Zwiń opis"})).toHaveAttribute("aria-expanded","true");
});

test("toast po czasie przechodzi w fazę wyjścia i znika po końcu animacji",()=>{
  const onDismiss=vi.fn();
  render(<Toast message="Zapisano" onDismiss={onDismiss} duration={1000}/>);
  const toast=screen.getByRole("status");
  act(()=>{vi.advanceTimersByTime(999);});
  expect(toast).not.toHaveClass("toast--leaving");
  act(()=>{vi.advanceTimersByTime(1);});
  expect(toast).toHaveClass("toast--leaving");
  expect(onDismiss).not.toHaveBeenCalled();
  endAnimation(toast,"toast-out");
  expect(onDismiss).toHaveBeenCalledOnce();
});

test("koniec animacji wejścia nie usuwa toastu",()=>{
  const onDismiss=vi.fn();
  render(<Toast message="Zapisano" onDismiss={onDismiss}/>);
  endAnimation(screen.getByRole("status"),"toast-in");
  expect(onDismiss).not.toHaveBeenCalled();
});

test("kliknięcie zamknięcia też animuje wyjście",()=>{
  const onDismiss=vi.fn();
  render(<Toast message="Zapisano" onDismiss={onDismiss}/>);
  fireEvent.click(screen.getByRole("button",{name:"Zamknij"}));
  expect(screen.getByRole("status")).toHaveClass("toast--leaving");
  expect(onDismiss).not.toHaveBeenCalled();
});

test("przy ograniczonym ruchu toast znika od razu i nic nie zostaje",()=>{
  setReducedMotion(true);
  const onDismiss=vi.fn();
  render(<Toast message="Zapisano" onDismiss={onDismiss} duration={1000}/>);
  act(()=>{vi.advanceTimersByTime(1000);});
  expect(onDismiss).toHaveBeenCalledOnce();
});

test("odmontowanie przed końcem czasu czyści licznik",()=>{
  const onDismiss=vi.fn();
  setReducedMotion(true);
  const view=render(<Toast message="Zapisano" onDismiss={onDismiss} duration={1000}/>);
  view.unmount();
  vi.advanceTimersByTime(2000);
  expect(onDismiss).not.toHaveBeenCalled();
});

test("hook czyta ustawienie i reaguje na zmianę",()=>{
  const listeners=setReducedMotion(true);
  const {result,unmount}=renderHook(()=>usePrefersReducedMotion());
  expect(result.current).toBe(true);
  expect(listeners.size).toBe(1);
  setReducedMotion(false);
  unmount();
  expect(listeners.size).toBe(0);
  expect(scrollBehavior(true)).toBe("auto");
  expect(scrollBehavior(false)).toBe("smooth");
});

test("pliki CSS lekcji przechodzą przegląd",()=>{
  expect(auditCss(css("animations.css"))).toEqual([]);
  expect(auditCss(css("toast.css"))).toEqual([]);
  expect(auditCss(css("dialog.css"))).toEqual([]);
});

test("przegląd wyłapuje animowanie układu, brak ograniczenia i długi czas",()=>{
  expect(auditCss(".a{transition:height 200ms;}")).toEqual(["animacja układu: height","brak prefers-reduced-motion"]);
  expect(auditCss(".a{transition:opacity 500ms;}@media (prefers-reduced-motion: reduce){.a{transition:none;}}")).toEqual(["animacja dłuższa niż 300 ms"]);
  expect(auditCss("@keyframes x{from{top:0;}to{top:10px;}}.a{animation:x 100ms;}@media (prefers-reduced-motion: reduce){.a{animation:none;}}")).toEqual(["animacja układu: top"]);
  expect(auditCss(".a{color:red;}")).toEqual([]);
  expect(auditCss(".a{transition:opacity 1s;}@media (prefers-reduced-motion: reduce){.a{transition:none;}}")).toEqual(["animacja dłuższa niż 300 ms"]);
});`;
const exercises=[
['Wysuń panel filtrów przez transform i transition.','Stan początkowy i końcowy muszą istnieć, zanim klasa się zmieni.',animationsCss],
['Steruj panelem przez klasę ze stanu Reacta.','Komponent zmienia klasę, a resztę robi CSS.',filters],
['Wyłącz ukryty panel z nawigacji przez inert.','Panel jest wciąż w DOM, ale nie powinien przyjmować fokusu.',filters],
['Dodaj aria-expanded i aria-controls do przycisku panelu.','Stan ARIA odzwierciedla stan Reacta.',filters],
['Rozwiń opis techniką grid-template-rows.','height:auto nie animuje się, a 0fr na 1fr tak.',animationsCss],
['Wyjaśnij, dlaczego opis wymaga overflow:hidden.','Co dzieje się z treścią przy wysokości 0fr?','Wiersz 0fr zwija się tylko wtedy, gdy jego zawartość może mieć wysokość mniejszą niż treść. overflow:hidden na wewnętrznym elemencie to umożliwia i ukrywa nadmiar w trakcie animacji. Bez niego treść wystawałaby poza element.'],
['Zablokuj interakcję w zwiniętym opisie.','Treść nadal jest w DOM, więc link mógłby dostać fokus.',filters],
['Dodaj animację wejścia toastu przez keyframes.','Animacja działa także przy pojawieniu się elementu.',toastCss],
['Dodaj animację wyjścia z forwards.','Bez forwards element wróciłby przed usunięciem.',toastCss],
['Napisz stan pośredni leaving w toaście.','Usunięcie następuje dopiero po animacji.',toast],
['Sprawdź animationName w onAnimationEnd.','Zdarzenie przychodzi też dla wejścia i dzieci.',toast],
['Zapewnij ścieżkę bez animacji przy ograniczonym ruchu.','Gdy animacji nie ma, zdarzenie nie przyjdzie.',toast],
['Zachowaj aktualny onDismiss w liczniku czasu.','Licznik startuje raz, a funkcja może się zmienić.',toast],
['Przetestuj toast fałszywymi licznikami.','Sprawdź stan tuż przed i tuż po czasie.',tests],
['Napisz w teście zdarzenie animacji dla jsdom.','jsdom nie ma AnimationEvent.',lab],
['Napisz hook usePrefersReducedMotion.','useSyncExternalStore subskrybuje zmianę ustawienia.',hook],
['Dobierz behavior przewijania do ustawienia.','Ograniczenie ruchu dotyczy też kodu JS.',hook],
['Animuj dialog samym CSS.','@starting-style i allow-discrete obsługują wejście i display.',dialogCss],
['Dodaj blok ograniczonego ruchu do każdego pliku.','Zamiast przesuwania przenikanie lub brak animacji.',animationsCss],
['Dodaj podświetlenie nowego wiersza tabeli.','Klasa z keyframes na tle, krótkie i niezależne od logiki.','@keyframes highlight { from { background: #fef3c7; } to { background: transparent; } }\n.row--new { animation: highlight 300ms ease-out; }\n@media (prefers-reduced-motion: reduce) { .row--new { animation: none; background: #fef3c7; } }\n/* Bez ruchu stan nadal jest widoczny: tło zostaje wyróżnione. */'],
['Napisz przegląd CSS wykrywający animację układu.','Lista właściwości powodujących przeliczenie layoutu.',audit],
['Wyszukaj brak prefers-reduced-motion i zbyt długi czas.','To tylko prosty filtr na tekście.',audit],
['Przepuść pliki lekcji przez przegląd w teście.','Plik CSS czytaj z dysku, nie kopiuj go.',tests],
['Oceń granice przeglądu tekstowego.','Co widzi Performance, a czego nie widzi tekst?','Przegląd tekstu nie wie o animacjach z JS, specyficzności, zmiennych CSS ani rzeczywistym koszcie. Sam transform też może być drogi przy dużych warstwach. Jest przydatnym filtrem, ale sprawdzenie w Performance i na słabszym urządzeniu wciąż należy do Ciebie.'],
['Sprawdź animacje w zakładce Performance.','Szukaj pasków Layout w trakcie animacji.','Nagraj otwarcie panelu i rozwinięcie opisu. Fioletowe paski Layout w każdej klatce wskazują animację układu. grid-template-rows przelicza układ, więc sprawdź go na słabszym urządzeniu: to kompromis uzasadniony brakiem innej metody dla wysokości auto.'],
['Zasymuluj ograniczony ruch w DevTools.','Rendering pozwala emulować media feature.','DevTools, Rendering, Emulate CSS media feature prefers-reduced-motion: reduce. Przejdź toast, panel i dialog: nic nie może zostać na ekranie, a stan ma być widoczny bez przesunięć. Zapisz ten scenariusz także jako test Playwright z emulacją reducedMotion.'],
['Dobierz czasy i krzywe animacji DevDesk.','Częste akcje wymagają krótkich animacji.','Małe elementy: 150–250 ms. ease-out przy wejściu, ease-in przy wyjściu. Modal około 200 ms. Interfejs reaguje w trakcie animacji, a stan logiczny zmienia się natychmiast. Nie blokuj kliknięć do końca ruchu.'],
['Popraw kod opóźniający usunięcie danych.','Co się stanie, gdy animacja nie wystartuje?','Nie uzależniaj logiki danych od zdarzenia animacji. Usuń ticket ze stanu natychmiast lub zapewnij ścieżkę bez animacji. onAnimationEnd nie przyjdzie przy ograniczonym ruchu, display:none lub usunięciu elementu, więc taki kod może zostawić element na zawsze.'],
['Rozważ View Transitions zamiast stanu leaving.','Porównaj z rozdziałem 17.','View Transitions robią zdjęcie starego stanu, więc usunięcie nie wymaga stanu pośredniego, ale dotyczą przejścia całego układu i wymagają przeglądarki z obsługą. Stan leaving działa wszędzie i kontroluje pojedynczy element. Dla małego toastu wystarczy CSS.'],
['Sprawdź kryterium akceptacji lekcji.','Płynność, wyjście i ograniczony ruch.','Panel, opis, toasty i modal animują się bez przeliczania layoutu poza świadomym wyjątkiem wysokości. Toast znika z animacją także po kliknięciu ×. Przy ograniczonym ruchu nie ma przesunięć i nic nie zostaje na ekranie. Testy i przegląd CSS przechodzą.'],
];
const concepts=[
['Co robi transition?','Animuje zmianę wartości między dwoma stanami','Uruchamia animację bez zmiany stanu','Usuwa element','Zmienia kolejność DOM','Element musi istnieć przed zmianą wartości.'],
['Kiedy transition nie zadziała przy wejściu?','Gdy element dopiero pojawia się w DOM bez stanu początkowego','Gdy animuje opacity','Gdy trwa 200 ms','Gdy element ma klasę','Przeglądarka nie ma wartości wyjściowej.'],
['Do czego nadaje się @keyframes?','Do animacji w czasie, także przy pojawieniu się','Tylko do hover','Tylko do tekstu','Do fetch','Animacja startuje po nadaniu animation.'],
['Co robi @starting-style?','Określa wartości sprzed pierwszego renderu elementu','Wyłącza animacje','Tworzy nowy motyw','Wczytuje czcionki','Pozwala zwykłemu transition działać przy wejściu.'],
['Co robi allow-discrete?','Pozwala przejściu obejmować dyskretne właściwości, np. display','Włącza sprężyny','Wyłącza opacity','Zmienia krzywą na liniową','Dzięki temu element pozostaje widoczny podczas zamykania.'],
['Które właściwości animują się najtaniej?','transform i opacity','width i height','top i left','margin i padding','Nie wymagają przeliczania układu.'],
['Dlaczego animowanie height jest kosztowne?','Przelicza układ w każdej klatce','Bo wymaga JS','Bo nie działa w Chrome','Bo blokuje fokus','Na słabszych urządzeniach to zacięcia.'],
['Jak rozwinąć element do wysokości auto?','grid-template-rows z 0fr na 1fr','transition:height auto','top:auto','display:auto','height:auto sam nie jest animowalny w ten sposób.'],
['Dlaczego wyjście jest trudne?','Warunkowy render usuwa element z DOM od razu','Bo opacity nie działa','Bo React blokuje CSS','Bo brakuje keyframes','Nie ma już czego animować.'],
['Który sposób wyjścia używa stanu pośredniego?','leaving i onAnimationEnd','Sam display:none','Sam key','Sam useMemo','Element zostaje do końca animacji.'],
['Czego wymaga ukrywanie klasą?','Wyłączenia interakcji, np. inert','Usuwania z DOM','Dodatkowego API','Bazy danych','Ukryty element nadal może przyjąć fokus.'],
['Co robi atrybut inert?','Wyłącza fokus, kliknięcia i drzewo dostępności','Tylko zmienia kolor','Usuwa element','Włącza animację','Działa dla całego poddrzewa.'],
['Jaki typ ma inert w React 19?','Boolean','Tylko pusty tekst','Liczba','Funkcja','Starsze typy wymagały obejść.'],
['Po co sprawdzać animationName?','Zdarzenie przychodzi też dla innych animacji','Bo to wymaga TS','Aby zmierzyć czas','Aby włączyć forwards','Inaczej toast mógłby zniknąć po wejściu.'],
['Czy onAnimationEnd bąbelkuje z dzieci?','Tak, jak większość zdarzeń','Nie','Tylko w Firefox','Tylko z SVG','Kolejny powód, by sprawdzać nazwę animacji.'],
['Co robi forwards?','Zostawia styl końcowy po animacji','Zapętla animację','Odtwarza wstecz','Wyłącza opóźnienie','Bez tego element wraca na chwilę przed usunięciem.'],
['Kiedy onAnimationEnd nie przyjdzie?','Gdy animacji nie ma, np. przy ograniczonym ruchu','Zawsze przyjdzie','Tylko po wejściu','Tylko w TSX','Dlatego potrzebna jest ścieżka bez animacji.'],
['Co oznacza prefers-reduced-motion?','Ustawienie systemowe ograniczenia ruchu','Słabe łącze','Tryb ciemny','Brak baterii','Dla części osób to kwestia zdrowia.'],
['Czy ograniczenie ruchu oznacza brak zmian?','Nie, stan ma być widoczny bez dużego przemieszczania','Tak','Tak, strona ma być statyczna','Tak, ale tylko w Safari','Przenikanie zwykle jest akceptowalne.'],
['Jak obsłużyć ograniczenie w CSS?','@media (prefers-reduced-motion: reduce)','@supports reduce','@layer motion','@font-face','To poziom deklaratywny.'],
['Jak obsłużyć ograniczenie w JS?','window.matchMedia i hook','navigator.onLine','localStorage','document.title','Potrzebne dla behavior przewijania i bibliotek.'],
['Co zwraca scrollBehavior przy ograniczeniu?','auto','smooth','instant-slow','none','Płynne przewijanie to ruch.'],
['Jaki czas jest typowy dla małych elementów?','150–250 ms','1–2 s','10 ms','5 s','Częste akcje męczą przy dłuższych animacjach.'],
['Która krzywa pasuje do wejścia?','ease-out','ease-in','linear zawsze','step-end','Szybki start i łagodne zatrzymanie.'],
['Która krzywa pasuje do wyjścia?','ease-in','ease-out','steps(4)','Żadna','Element przyspiesza i znika.'],
['Czy stan logiczny powinien czekać na animację?','Nie, zmienia się natychmiast','Tak, zawsze','Tak, gdy jest modal','Tak, dla toastów','Animacja to warstwa prezentacji.'],
['Co jest błędem przy blokowaniu kliknięć w animacji?','Spowalnia pracę użytkownika','Zmniejsza paczkę','Poprawia dostępność','Niczym nie grozi','Interfejs ma reagować również w trakcie.'],
['Jak sprawdzić koszt animacji?','Zakładka Performance i paski Layout','npm audit','tsc','Lighthouse SEO','Nagraj i przeanalizuj klatki.'],
['Jak zasymulować ograniczenie ruchu?','DevTools, Rendering, emulacja media feature','Wyłączyć GPU','Zmienić język','Włączyć tryb offline','Można to też zrobić w Playwright.'],
['Czy TSX wyłapie style={{transfrom:"x"}}?','Tak, CSSProperties znają nazwy','Nie','Tylko w runtime','Tylko z ESLint','W JSX literówka po prostu nic nie zrobi.'],
['Jak w TSX przekazać zmienną --delay?','Z rzutowaniem as React.CSSProperties','Nie da się','Przez aria','Przez data bez stylu','Zmiennych CSS nie ma w typie.'],
['Jaki typ zdarzenia opisuje koniec animacji?','AnimationEvent<HTMLDivElement>','TransitionEvent zawsze','MouseEvent','ChangeEvent','Daje dostęp do animationName.'],
['Jakie zdarzenie obsłuży koniec transition?','onTransitionEnd','onAnimationEnd','onChange','onLoad','Dla przejść jest osobne zdarzenie.'],
['Czy dialog można animować samym CSS?','Tak, z @starting-style i allow-discrete','Nie','Tylko z Motion','Tylko w JS','Otwieranie i zamykanie bez kodu React.'],
['Dlaczego panel zostaje w DOM?','Transition potrzebuje stanu wyjściowego','Bo React go nie usunie','Bo inert tego wymaga','Bo tak szybciej ładuje dane','Warunkowy render zabrałby punkt startowy.'],
['Jaką wadę ma ukryty element w DOM?','Zajmuje pamięć i wymaga inert','Blokuje sieć','Psuje JSON','Psuje typy','Przy wielu elementach warto to rozważyć.'],
['Jaką przewagę daje CSS nad JS w animacji?','Mniej kodu i często praca poza głównym wątkiem','Zawsze działa bez przeglądarki','Brak problemów z dostępnością','Nie wymaga stylów','transform i opacity mogą działać na warstwie GPU.'],
['Kiedy CSS nie wystarcza?','Zmiana układu, sprężyny, gesty','Hover','Fade','Rotacja ikony','Wtedy warto rozważyć bibliotekę.'],
['Co jest ryzykiem animacji w tle karty?','Licznik i animacje mogą się wstrzymywać','Karta się zamyka','Znika DOM','Nic','Nie opieraj logiki na tym, że animacja się skończy.'],
['Jak nie zostawić toastu na zawsze?','Zapewnić ścieżkę bez animacji','Wydłużyć duration','Dodać pętlę','Usunąć przycisk','Brak zdarzenia końca nie może blokować usunięcia.'],
['Co robi clearTimeout w sprzątaniu efektu?','Zapobiega wywołaniu po odmontowaniu','Przyspiesza licznik','Resetuje stan','Wyłącza animację','Inaczej callback działa na nieistniejącym elemencie.'],
['Po co fałszywe liczniki w teście?','Aby kontrolować czas bez czekania','Aby sprawdzić CSS','Aby wyłączyć React','Aby zmierzyć wydajność','Test jest szybki i powtarzalny.'],
['Czy jsdom odtwarza animacje CSS?','Nie','Tak, w całości','Tylko keyframes','Tylko transition','Test sprawdza klasy i logikę, a ruch ogląda się w przeglądarce.'],
['Co sprawdzi Playwright, czego nie sprawdzi jsdom?','Rzeczywiste animacje i emulację ruchu','Składnię JSX','Nazwy zmiennych','Treść JSON','To prawdziwa przeglądarka.'],
['Czy animacja zastępuje komunikat?','Nie, zmiana musi być zrozumiała także bez ruchu','Tak','Tak, w modalu','Tak, jeśli trwa długo','Toast wymaga tekstu i ról.'],
['Jaką rolę ma toast z potwierdzeniem?','status','alert zawsze','dialog','tooltip','Spokojne ogłoszenie stanu.'],
['Co oznacza stale widoczny element z opacity:0?','Może być nadal klikalny','Znika z DOM','Nie przyjmuje fokusu','Jest usunięty z układu','opacity nie wyłącza interakcji.'],
['Czym różni się visibility:hidden od opacity:0?','visibility wyłącza interakcję i fokus','Niczym','opacity usuwa z DOM','visibility działa tylko w SVG','visibility też da się animować dyskretnie.'],
['Co animuje grid-template-rows?','Wysokość wiersza, także do treści','Tylko kolor','Tylko pozycję X','Kolejność kolumn','Technika obsługiwana w nowoczesnych przeglądarkach.'],
['Dlaczego treść inert w zwiniętym opisie?','Bo jest w DOM, a nie powinna przyjmować fokusu','Bo tak działa transform','Bo inaczej nie ma animacji','Bo CSS tego wymaga','Inaczej Tab trafiłby na niewidoczny link.'],
['Jakie animacje szczególnie szkodzą przy zaburzeniach?','Duże ruchy i paralaksa','Krótkie przenikanie','Zmiana koloru','Zmiana tekstu','Mogą wywoływać zawroty głowy.'],
['Co oznacza „animacja jest warstwą prezentacji”?','Logika działa tak samo bez niej','Animacja zmienia dane','Wymaga backendu','Nie można jej wyłączyć','To dobra zasada projektowa.'],
['Co jest dobrym kryterium dla krótkiej animacji?','Nie spowalnia powtarzanej akcji','Jest widoczna przez sekundę','Zawsze ma sprężynę','Działa bez CSS','Użytkownik wykonuje akcje wiele razy dziennie.'],
['Kiedy keyframes podświetlenia są lepsze niż transition?','Gdy efekt ma zadziałać raz po dodaniu klasy','Nigdy','Dla wszystkich hover','Tylko w dialogu','Nie ma tu stanu końcowego do utrzymania.'],
['Czym grozi will-change na wszystkim?','Nadmiernym zużyciem pamięci','Brakiem animacji','Błędem składni','Wyłączeniem fokusu','Używaj go oszczędnie i tylko gdy trzeba.'],
['Czym grozi animowanie box-shadow?','Kosztownym malowaniem','Błędem JSON','Utratą fokusu','Niczym','Lepiej animować opacity warstwy z cieniem.'],
['Kiedy useSyncExternalStore jest dobrym wyborem?','Gdy subskrybujesz zewnętrzne źródło, np. matchMedia','Do pobierania danych z API zawsze','Do stylów','Do form','Unika rozjazdów stanu i wymaga funkcji dla serwera.'],
['Co zwraca hook na serwerze?','Wartość z trzeciego argumentu, tu false','Wyjątek','window','null zawsze','Na serwerze nie ma matchMedia.'],
['Co robi removeEventListener w subskrypcji?','Sprząta nasłuch przy odmontowaniu','Wyłącza media query','Zmienia ustawienie systemu','Nic','Bez tego powstaje wyciek.'],
['Czy własny przegląd CSS zastępuje profiler?','Nie, to filtr na tekście','Tak','Tak, po CI','Tak, jeśli brak błędów','Nie widzi kosztu rzeczywistego.'],
];
const header=String.raw`import {auditCss} from "./cssAudit";
import {observeDescription,observeFilters} from "./animationLab";
import {scrollBehavior} from "./usePrefersReducedMotion";
const reduce="@media (prefers-reduced-motion: reduce){.a{transition:none;}}";`;
const probeRows=[
['Jaką klasę ma zamknięty panel?','observeFilters(0).className','filters-panel','Domyślnie tylko klasa bazowa.',['filters-panel filters-panel--open','pusty tekst','open']],
['Jaką klasę ma otwarty panel?','observeFilters(1).className','filters-panel filters-panel--open','Stan dokłada klasę.',['filters-panel','filters-panel--open','pusty tekst']],
['Czy zamknięty panel jest inert?','observeFilters(0).inert',true,'Ukryty panel nie przyjmuje fokusu.'],
['Czy otwarty panel jest inert?','observeFilters(1).inert',false,'Otwarty panel działa normalnie.'],
['Czy po dwóch kliknięciach panel jest inert?','observeFilters(2).inert',true,'Powrót do stanu zamkniętego.'],
['Czy zamknięty panel jest w DOM?','observeFilters(0).inDom',true,'Transition potrzebuje istniejącego elementu.'],
['Jakie aria-expanded ma otwarty panel?','observeFilters(1).expanded','true','Atrybut odzwierciedla stan.',['false','brak','open']],
['Jaką etykietę ma zwinięty opis?','observeDescription(0).label','Pokaż opis','Etykieta mówi, co zrobi kliknięcie.',['Zwiń opis','Opis','brak']],
['Jaką etykietę ma rozwinięty opis?','observeDescription(1).label','Zwiń opis','Stan zmienia etykietę.',['Pokaż opis','Opis','brak']],
['Czy opis jest rozwinięty po kliknięciu?','observeDescription(1).open',true,'Klasa collapse--open.'],
['Czy treść zwiniętego opisu jest w DOM?','observeDescription(0).text',true,'Zwinięcie robi CSS, a nie warunkowy render.'],
['Ile problemów ma poprawny transform?','auditCss(".a{transition:transform 200ms;}"+reduce).length',0,'To tania właściwość, a ruch ma ograniczenie.'],
['Ile problemów ma opacity bez bloku ruchu?','auditCss(".a{transition:opacity 200ms;}").length',1,'Brakuje prefers-reduced-motion.'],
['Jaki problem ma brak bloku ruchu?','auditCss(".a{transition:opacity 200ms;}")[0]','brak prefers-reduced-motion','Każdy plik z ruchem powinien mieć blok.',['animacja układu: opacity','animacja dłuższa niż 300 ms','brak']],
['Jaki problem ma animowanie width?','auditCss(".a{transition:width 200ms;}"+reduce)[0]','animacja układu: width','Width przelicza układ.',['brak prefers-reduced-motion','animacja dłuższa niż 300 ms','brak']],
['Jaki problem ma animowanie margin?','auditCss(".a{transition:margin 200ms;}"+reduce)[0]','animacja układu: margin','Margines też wpływa na układ.',['animacja układu: width','brak prefers-reduced-motion','brak']],
['Jaki problem ma animacja 500 ms?','auditCss(".a{transition:opacity 500ms;}"+reduce)[0]','animacja dłuższa niż 300 ms','Przekroczono ustalony próg.',['brak prefers-reduced-motion','animacja układu: opacity','brak']],
['Czy 0.5s jest zbyt długie?','auditCss(".a{transition:opacity 0.5s;}"+reduce).length',1,'Sekundy są przeliczane na milisekundy.'],
['Czy 300 ms przekracza próg?','auditCss(".a{transition:opacity 300ms;}"+reduce).length',0,'Limit nie jest przekroczony.'],
['Czy 0.2s jest w limicie?','auditCss(".a{transition:opacity 0.2s;}"+reduce).length',0,'To 200 ms.'],
['Ile problemów ma CSS bez animacji?','auditCss(".a{color:red;}").length',0,'Bez ruchu nie ma wymogu bloku.'],
['Ile problemów ma transition:none?','auditCss(".a{transition:none;}").length',0,'none to brak animacji.'],
['Ile problemów ma height i brak bloku?','auditCss(".a{transition:height 200ms;}").length',2,'Dwa niezależne problemy.'],
['Czy dwie właściwości są czytane osobno?','auditCss(".a{transition:opacity 200ms, height 200ms;}"+reduce)[0]','animacja układu: height','Lista przecinkowa jest dzielona.',['animacja układu: opacity','brak prefers-reduced-motion','brak']],
['Czy keyframes z top są wykrywane?','auditCss("@keyframes x{from{top:0;}to{top:1px;}}"+reduce)[0]','animacja układu: top','Właściwości w klatkach też się liczą.',['brak prefers-reduced-motion','animacja dłuższa niż 300 ms','brak']],
['Czy keyframes z transform są czyste?','auditCss("@keyframes x{from{transform:scale(0);}to{transform:scale(1);}}"+reduce).length',0,'Transform jest bezpieczny.'],
['Czy powtórzona właściwość daje jeden problem?','auditCss(".a{transition:top 1ms;}.b{transition:top 2ms;}"+reduce).length',1,'Zbiór usuwa duplikaty.'],
['Czy padding jest animacją układu?','auditCss(".a{transition:padding 1ms;}"+reduce).length',1,'Zmienia rozmiar pola.'],
['Czy color jest animacją układu w tym przeglądzie?','auditCss(".a{transition:color 1ms;}"+reduce).length',0,'Kolor nie przelicza układu.'],
['Czy własność margin-left złapie przegląd?','auditCss(".a{transition:margin-left 1ms;}"+reduce).length',0,'Lista jest prosta i nie obejmuje wariantów. To ograniczenie przeglądu tekstowego.'],
['Czy animacja all jest wykrywana?','auditCss(".a{transition:all 200ms;}"+reduce).length',0,'all może obejmować układ, a filtr tego nie widzi. Unikaj all, bo ukrywa koszt.'],
['Jaki behavior przy ograniczeniu?','scrollBehavior(true)','auto','Bez płynnego przewijania.',['smooth','none','instant']],
['Jaki behavior bez ograniczenia?','scrollBehavior(false)','smooth','Płynne przewijanie jest dopuszczalne.',['auto','none','instant']],
['Jakie aria-expanded ma zwinięty opis po dwóch kliknięciach?','observeDescription(2).open',false,'Powrót do stanu początkowego.'],
['Jaką etykietę ma opis po dwóch kliknięciach?','observeDescription(2).label','Pokaż opis','Etykieta wraca razem ze stanem.',['Zwiń opis','Opis','brak']],
['Ile plików kontroli wymaga 1000 ms?','auditCss(".a{transition:opacity 1000ms;}"+reduce).length',1,'Próg przekroczony.'],
['Czy ms bez wartości jest pomijane?','auditCss(".a{transition:opacity;}"+reduce).length',0,'Brak czasu to brak dopasowania.'],
['Czy 301 ms przekracza próg?','auditCss(".a{transition:opacity 301ms;}"+reduce).length',1,'Więcej niż limit.'],
['Czy bottom jest animacją układu?','auditCss(".a{transition:bottom 1ms;}"+reduce).length',1,'Zmiana pozycji układu.'],
['Czy opacity w keyframes wymaga bloku ruchu?','auditCss("@keyframes x{to{opacity:0;}}")[0]','brak prefers-reduced-motion','Ten model wymaga bloku przy każdym ruchu.',['animacja układu: opacity','animacja dłuższa niż 300 ms','brak']],
];
const project={
'src/animations.css':animationsCss,'src/toast.css':toastCss,'src/dialog.css':dialogCss,'src/TicketFilters.tsx':filters,'src/Toast.tsx':toast,'src/usePrefersReducedMotion.ts':hook,'src/cssAudit.ts':audit,'src/animationLab.tsx':lab,'src/animations.test.tsx':tests,
'README.md':'DevDesk: animacje CSS. npm install, npm test. jsdom nie odtwarza animacji: testy sprawdzają klasy, stany i logikę usuwania, a koniec animacji jest symulowany zdarzeniem. Płynność i brak przeliczeń układu sprawdź w zakładce Performance prawdziwej przeglądarki.\n',
};
const mutants=[
{file:'src/TicketFilters.tsx',before:'className={open?"filters-panel filters-panel--open":"filters-panel"} inert={!open}',after:'className={open?"filters-panel filters-panel--open":"filters-panel"}'},
{file:'src/TicketFilters.tsx',before:'aria-expanded={open} ',after:''},
{file:'src/TicketFilters.tsx',before:'className="collapse__inner" inert={!expanded}',after:'className="collapse__inner"'},
{file:'src/Toast.tsx',before:'if(event.animationName==="toast-out")onDismiss();',after:'onDismiss();'},
{file:'src/Toast.tsx',before:'if(reducedMotion())dismiss.current();\n    else setPhase("leaving");',after:'setPhase("leaving");'},
{file:'src/Toast.tsx',before:'return ()=>clearTimeout(id);',after:''},
{file:'src/Toast.tsx',before:'onClick={startLeaving}',after:'onClick={onDismiss}'},
{file:'src/usePrefersReducedMotion.ts',before:'return ()=>media.removeEventListener("change",onChange);',after:'return ()=>{};'},
{file:'src/cssAudit.ts',before:'match[2]==="s"?1000:1',after:'1'},
{file:'src/toast.css',before:'@media (prefers-reduced-motion: reduce)',after:'@media (min-width: 1px)'},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
