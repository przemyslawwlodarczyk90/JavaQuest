const model=`export type Rect={left:number;right:number;top:number;bottom:number};export type Position={left:number;top:number;side:"top"|"bottom"};
export function place(anchor:Rect,size:{width:number;height:number},viewport:{width:number;height:number},gap=8):Position{const margin=8;const width=Math.min(Math.max(0,size.width),Math.max(0,viewport.width-2*margin));const height=Math.min(Math.max(0,size.height),Math.max(0,viewport.height-2*margin));const below=viewport.height-margin-anchor.bottom-gap;const above=anchor.top-gap-margin;const side=below>=height||below>=above?"bottom":"top";const raw=side==="bottom"?anchor.bottom+gap:anchor.top-gap-height;return {left:Math.max(margin,Math.min(anchor.left,viewport.width-margin-width)),top:Math.max(margin,Math.min(raw,viewport.height-margin-height)),side};}
export function nextAction(ids:readonly string[],current:string,key:string):string|null{if(ids.length===0)return null;if(key==="Home")return ids[0];if(key==="End")return ids[ids.length-1];const index=ids.indexOf(current);if(key!=="ArrowDown"&&key!=="ArrowUp")return null;if(index<0)return ids[0];return ids[(index+(key==="ArrowDown"?1:-1)+ids.length)%ids.length];}`;
const panel=`import {useId,useLayoutEffect,useRef,useState} from "react";
${model}
export type Action={id:string;label:string;disabled?:boolean;onSelect:()=>void};
export function RowActions({label,actions}:{label:string;actions:readonly Action[]}){const id=useId();const button=useRef<HTMLButtonElement>(null);const panel=useRef<HTMLDivElement>(null);const [open,setOpen]=useState(false);const [position,setPosition]=useState<Position>({left:8,top:8,side:"bottom"});const [error,setError]=useState<string|null>(null);useLayoutEffect(()=>{if(!open)return;function update(){const anchor=button.current,popup=panel.current;if(!anchor||!popup)return;setPosition(place(anchor.getBoundingClientRect(),{width:popup.offsetWidth,height:popup.offsetHeight},{width:document.documentElement.clientWidth,height:document.documentElement.clientHeight}));}update();window.addEventListener("resize",update);window.addEventListener("scroll",update,true);const observer=new ResizeObserver(update);if(panel.current)observer.observe(panel.current);if(button.current)observer.observe(button.current);return ()=>{window.removeEventListener("resize",update);window.removeEventListener("scroll",update,true);observer.disconnect();};},[open]);function select(action:Action){if(action.disabled)return;try{action.onSelect();setError(null);panel.current?.hidePopover();button.current?.focus();}catch{setError("Nie udało się wykonać akcji.");}}return <div><button ref={button} type="button" popoverTarget={id} aria-expanded={open} aria-controls={id}>{label}</button><div id={id} ref={panel} popover="auto" onToggle={event=>setOpen(event.newState==="open")} className="action-panel" style={{position:"fixed",inset:"auto",margin:0,left:position.left,top:position.top,maxWidth:"calc(100vw - 16px)",maxHeight:"calc(100vh - 16px)",overflow:"auto"}} aria-label={label}>{actions.map(action=><button key={action.id} type="button" disabled={action.disabled} onClick={()=>select(action)}>{action.label}</button>)}</div>{error&&<p role="alert">{error}</p>}</div>;}`;
const info=`import {useId} from "react";export function AssigneeInfo({login,email}:{login:string;email:string}){const id=useId();return <><button type="button" popoverTarget={id}>@{login}</button><div id={id} popover="auto"><strong>{login}</strong><p>{email}</p><a href={"mailto:"+email}>Napisz wiadomość</a><button type="button" popoverTarget={id} popoverTargetAction="hide">Zamknij</button></div></>;}`;
const tooltip=`import {useEffect,useId,useState} from "react";export function PriorityHint(){const id=useId();const [hovered,setHovered]=useState(false);const [focused,setFocused]=useState(false);const [dismissed,setDismissed]=useState(false);const open=(hovered||focused)&&!dismissed;useEffect(()=>{if(!open)return;const dismiss=(event:KeyboardEvent)=>{if(event.key==="Escape")setDismissed(true);};document.addEventListener("keydown",dismiss);return ()=>document.removeEventListener("keydown",dismiss);},[open]);return <span className="hint" onMouseEnter={()=>{setHovered(true);if(!focused)setDismissed(false);}} onMouseLeave={()=>{setHovered(false);if(!focused)setDismissed(false);}}><button type="button" aria-describedby={id} onFocus={()=>{setFocused(true);if(!hovered)setDismissed(false);}} onBlur={()=>{setFocused(false);if(!hovered)setDismissed(false);}}>Jak rozumieć priorytet?</button><span id={id} role="tooltip" hidden={!open}>Wysoki priorytet oznacza pilny wpływ na pracę zespołu.</span></span>;}`;
const exercises=[
['Rozdziel tooltip, panel szczegółów i menu poleceń.','Tooltip zawiera tekst; panel może mieć linki.',info],
['Dodaj unikalne ID popovera przy pomocy useId.','Login nie gwarantuje unikalności dwóch instancji.',info],
['Połącz przycisk z panelem przez popoverTarget.','W React zapis właściwości jest camelCase.',info],
['Dodaj deklaratywny przycisk zamykający popover.','Użyj popoverTargetAction="hide".',info],
['Umieść link e-mail w panelu szczegółów zamiast w tooltipie.','To treść interaktywna.',info],
['Zbuduj panel akcji z natywnymi przyciskami i nawigacją Tab.','To disclosure; nie deklaruj role menu bez pełnego kontraktu.',panel],
['Synchronizuj aria-expanded ze zdarzeniem toggle.','Kliknięcie poza i Escape również zmieniają stan natywny.',panel],
['Zamknij panel dopiero po udanej synchronicznej akcji.','Błąd callbacku pokaż trwale.',panel],
['Przywróć fokus wyzwalacza po wybraniu akcji.','Nie przejmuj fokusu po zwykłym kliknięciu poza.',panel],
['Obsłuż wyłączoną akcję przez disabled.','Nie polegaj wyłącznie na kolorze.',panel],
['Wylicz pozycję panelu dla dolnej krawędzi okna.','Porównaj miejsce nad i pod wyzwalaczem.',model],
['Przesuń panel od prawej krawędzi viewportu.','Ogranicz left po uwzględnieniu szerokości panelu.',model],
['Ogranicz wysokość i szerokość panelu w CSS.','Same obliczenia pozycji nie pomniejszają elementu.',panel],
['Zapewnij przewijanie zbyt długiego panelu.','Użyj overflow auto z maxHeight.',panel],
['Zareaguj na scroll kontenera zagnieżdżonego.','Nasłuchuj scroll w fazie capture.',panel],
['Zareaguj na zmianę rozmiaru okna.','Posprzątaj ten sam listener.',panel],
['Obserwuj zmianę rozmiaru panelu i przycisku.','ResizeObserver odłącz w cleanup.',panel],
['Ogranicz pomiar DOM do otwartego panelu.','Ukryty popover nie ma użytecznego rozmiaru.',panel],
['Zastosuj pomiar po commit przed malowaniem.','useLayoutEffect może blokować malowanie; ogranicz pracę.',panel],
['Obsłuż tooltip po fokusie klawiatury.','Nie używaj wyłącznie hover.',tooltip],
['Połącz opis z przyciskiem przez aria-describedby.','Opis nie zastępuje nazwy przycisku.',tooltip],
['Zachowaj tooltip podczas przejścia kursora na jego tekst.','Wyzwalacz i opis mają wspólny obszar hover.',tooltip],
['Zamknij tooltip klawiszem Escape bez przesuwania fokusu.','Zapamiętaj dismissed do końca bieżącej interakcji.',tooltip],
['Nie otwieraj ponownie tooltipa zaraz po Escape przy zachowanym fokusie.','Rozdziel focused i dismissed.',tooltip],
['Usuń listener Escape po zamknięciu tooltipa.','Setup i cleanup należą do jednego efektu.',tooltip],
['Zaimplementuj model strzałek dla listy aktywnych ID menu.','To tylko model nawigacji, nie kompletny widget ARIA.',model],
['Obsłuż Home i End w modelu listy akcji.','Pusty zbiór zwraca null.',model],
['Wyklucz disabled z ID przekazywanych do modelu menu.','Wylicz ids z actions.filter, nie pomijaj niejawnie.',`export type Action={id:string;disabled?:boolean};export function enabledIds(actions:readonly Action[]){return actions.filter(action=>!action.disabled).map(action=>action.id);}`],
['Dodaj scenariusz ręcznej weryfikacji panelu przy krawędziach.','Sprawdź zoom, scroll, Tab, Escape i dwie instancje.',panel],
['Zastosuj jawny kontrakt wspieranych przeglądarek.','Przykład wymaga Popover API i ResizeObserver; starsze środowisko wymaga fallbacku.',panel],
];
const concepts=[
['Czy popover auto jest modalny?','Nie, nie unieruchamia całej strony','Tak, jak showModal','Tylko z przyciskiem','Tylko po Escape'],
['Co daje top layer?','Warstwę poza zwykłym układaniem z-index','Automatyczne menu ARIA','Walidację e-mail','Zapis do serwera'],
['Czy Popover API samo liczy flip przy krawędzi?','Nie, pozycjonowanie wymaga CSS lub logiki','Zawsze','Tylko dla role menu','Tylko na hover'],
['Co synchronizuje stan przy zamknięciu kliknięciem poza?','Zdarzenie toggle','Wyłącznie onClick przycisku','Render licznika','Zmiana ID'],
['Jaki atrybut React wiąże wyzwalacz z popoverem?','popoverTarget','popover-target','aria-modal','focusTarget'],
['Jak zamknąć deklaratywnie wskazany panel?','popoverTargetAction hide','aria-hidden false','role hide','tabIndex -2'],
['Dlaczego useId zamiast loginu w ID?','Wiele instancji tego samego użytkownika musi mieć różne ID','Login nigdy nie jest stringiem','ID nie wolno renderować','useId nadaje uprawnienia'],
['Czy panel zwykłych przycisków musi mieć role menu?','Nie, może być disclosure z Tab','Tak, każdy panel','Tylko w tabeli','Tylko z popover auto'],
['Co pociąga za sobą role menu?','Pełny kontrakt klawiatury i fokusu menu','Tylko inny kolor','Automatyczne strzałki z ARIA','Automatyczny portal'],
['Czy role menu samo implementuje ArrowDown?','Nie','Tak','Tylko React 19','Tylko w dialogu'],
['Co należy do tooltipa?','Krótki opis tekstowy','Formularz logowania','Przycisk Usuń','Lista linków'],
['Co łączy opis tooltipa z wyzwalaczem?','aria-describedby','aria-modal','popoverTargetAction','aria-checked'],
['Gdzie pozostaje fokus przy tooltipie?','Na wyzwalaczu','Na tekście tooltipa','Zawsze na body','Na pierwszym linku'],
['Jak obsłużyć tooltip dla klawiatury?','Pokazać po fokusie i umożliwić Escape','Wyłącznie mouseenter','Wyłącznie timeout','Wyłącznie kliknięcie poza'],
['Czy klikane linki powinny być tooltipem?','Nie, użyj interaktywnego panelu','Tak, zawsze','Tylko z role tooltip','Tylko przy hover'],
['Kiedy tooltip powinien pozostać widoczny pod kursorem?','Gdy kursor przeszedł z przycisku na opis','Nigdy','Tylko przy kliknięciu','Tylko po odmontowaniu'],
['Dlaczego przechować dismissed?','Żeby Escape nie otwierał tooltipa ponownie przez zachowany fokus','Żeby zapisać dane','Żeby utworzyć portal','Żeby anulować render'],
['Co należy sprzątnąć w pozycjonowaniu?','Listenery resize/scroll i ResizeObserver','Tylko tekst','Wyłącznie CSS','Nic'],
['Po co scroll capture?','Żeby reagować również na scroll zagnieżdżonych kontenerów','Żeby blokować wszystkie kliknięcia','Żeby zatrzymać Tab','Żeby zmienić role'],
['Co mierzy getBoundingClientRect?','Prostokąt w współrzędnych viewportu','ID React','Wyłącznie padding','Pozycję względem serwera'],
['Czy samo ograniczenie left pomniejsza panel?','Nie, potrzebny jest też limit rozmiaru CSS','Tak','Tylko przy margin 0','Tylko w portalu'],
['Co zrobić z panelem wyższym niż okno?','Ograniczyć wysokość i pozwolić przewijać','Ukryć wszystkie akcje','Zwiększyć z-index','Wyłączyć klawiaturę'],
['Co robi flip w bibliotece pozycjonującej?','Wybiera inną stronę przy braku miejsca','Tworzy focus trap','Anuluje fetch','Ustawia rolę'],
['Co robi shift w pozycjonowaniu?','Przesuwa panel w granice obszaru','Zmienia ID','Tworzy tooltip','Wywołuje onSelect'],
['Co robi offset w pozycjonowaniu?','Dodaje odstęp od wyzwalacza','Usuwa ref','Przewija serwer','Wyłącza Escape'],
['Czy portal sam rozwiązuje kolizje z viewportem?','Nie','Tak zawsze','Tylko z useId','Tylko dla button'],
['Czy popover auto zawsze zamyka wszystkie inne?','Nie, zagnieżdżone popovery są wyjątkiem','Tak bez wyjątków','Nie zamyka żadnych','Zamyka tylko tooltipy'],
['Co sprawdzić przy dwóch panelach jednego użytkownika?','Unikalność ID i poprawność wyzwalaczy','Tylko długość e-maila','Wyłącznie kolor','Liczbę importów'],
['Czy test czystego place dowodzi poprawnego layoutu przeglądarki?','Nie, potrzebny test w przeglądarce','Tak','Tylko TypeScript','Tylko SSR'],
['Czy callback void w tym przykładzie oznacza obsługę async?','Nie, kontrakt akcji jest synchroniczny','Tak, automatycznie await','Tylko w catch','Tylko z disabled'],
];
const quiz=concepts.map(row=>[...row,'Rozdziel semantykę, pozycjonowanie i interakcje. Natywny popover jest niemodalnym panelem; role ARIA nie dostarczają zachowania.']);quiz.push(...require('./practice_ref_06.cjs').quiz.slice(0,30).map(row=>['Powtórka klawiatury paneli: '+row[0],...row.slice(1)]));
const cases=[];function add(label,expression,answer){cases.push([label,expression,String(answer)]);}
const anchor='{left:50,right:90,top:50,bottom:70}',size='{width:100,height:80}',view='{width:300,height:200}';
for(const [label,a,s,v,expected] of [
['Środek',anchor,size,view,[50,78,'bottom']],['Dół','{left:50,right:90,top:170,bottom:190}',size,view,[50,82,'top']],['Prawa','{left:270,right:290,top:50,bottom:70}',size,view,[192,78,'bottom']],['Lewa','{left:-20,right:10,top:50,bottom:70}',size,view,[8,78,'bottom']],['Duży panel',anchor,'{width:500,height:500}',view,[8,8,'bottom']],['Wysoko','{left:10,right:40,top:0,bottom:20}',size,view,[10,28,'bottom']],['Nisko','{left:10,right:40,top:190,bottom:200}',size,view,[10,102,'top']],['Wąsko',anchor,size,'{width:80,height:200}',[8,78,'bottom']]])for(const [index,key]of ['left','top','side'].entries())add(label+' — '+key,`place(${a},${s},${v}).${key}`,expected[index]);
for(const [label,current,key,answer]of [['Dół A','a','ArrowDown','b'],['Dół C','c','ArrowDown','a'],['Góra A','a','ArrowUp','c'],['Góra B','b','ArrowUp','a'],['Home C','c','Home','a'],['End A','a','End','c'],['Enter A','a','Enter','null'],['Obcy dół','x','ArrowDown','a'],['Obcy góra','x','ArrowUp','a'],['Obcy Home','x','Home','a'],['Obcy End','x','End','c'],['Escape','b','Escape','null']])add(label,`nextAction(["a","b","c"],${JSON.stringify(current)},${JSON.stringify(key)})`,answer);
add('Puste ID','nextAction([],"a","Home")','null');add('Jedno ID dół','nextAction(["a"],"a","ArrowDown")','a');add('Jedno ID góra','nextAction(["a"],"a","ArrowUp")','a');add('Pusta spacja','nextAction(["a"],"a"," ")','null');
for(const [label,expr,answer]of cases){const wrong=['null','false','true','0','8','a','top','bottom'].filter(v=>v!==answer).slice(0,3);quiz.push([label+': jaki wynik?',answer,...wrong,'Oblicz ograniczenia rozmiaru i pozycji albo przejdź po aktywnych ID; model nie odtwarza rzeczywistego DOM.',model+'\nconsole.log('+expr+');']);}
const css='.action-panel{box-sizing:border-box;padding:.5rem;border:1px solid #64748b;border-radius:.5rem}.action-panel button{display:block;margin:.25rem}.hint{position:relative;display:inline-block;padding:.5rem}.hint [role=tooltip]{position:absolute;bottom:100%;left:0;width:16rem;max-width:80vw;padding:.5rem;background:#172554;color:white}button:focus-visible,a:focus-visible{outline:3px solid #2563eb;outline-offset:3px}main{max-width:50rem;margin:2rem auto;padding:1rem}';
const project={'src/panel.tsx':panel,'src/info.tsx':info,'src/tooltip.tsx':tooltip,'src/style.css':css,'src/main.tsx':`import {StrictMode,useState} from "react";import {createRoot} from "react-dom/client";import {RowActions} from "./panel";import {AssigneeInfo} from "./info";import {PriorityHint} from "./tooltip";import "./style.css";function App(){const [status,setStatus]=useState("Otwarty");return <main><h1>Elementy pływające</h1><p>Przykład wymaga natywnego Popover API. Panel akcji używa zwykłej nawigacji Tab, bez roli menu.</p><p role="status">Status: {status}</p><RowActions label="Akcje ticketu #0" actions={[{id:"close",label:"Zamknij ticket",onSelect:()=>setStatus("Zamknięty")},{id:"assign",label:"Przypisz",disabled:true,onSelect:()=>{}}]}/><AssigneeInfo login="anna" email="anna@example.test"/><AssigneeInfo login="anna" email="anna@example.test"/><PriorityHint/></main>;}createRoot(document.getElementById("root")!).render(<StrictMode><App/></StrictMode>);`};
module.exports={model,panel,info,tooltip,exercises,quiz,project};
