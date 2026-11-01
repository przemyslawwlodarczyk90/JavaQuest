// API: https://react.dev/reference/react-dom/createPortal
const model=`export type Box={left:number;right:number;top:number;bottom:number};export function outsideBox(rect:Box,x:number,y:number):boolean{return x<rect.left||x>rect.right||y<rect.top||y>rect.bottom;}
export type OverflowStyle={overflow:string};const locks=new WeakMap<OverflowStyle,{count:number;previous:string}>();export function lockScroll(style:OverflowStyle):()=>void{let entry=locks.get(style);if(!entry){entry={count:0,previous:style.overflow};locks.set(style,entry);}entry.count++;style.overflow="hidden";let released=false;return ()=>{if(released)return;released=true;const current=locks.get(style);if(!current)return;current.count--;if(current.count===0){style.overflow=current.previous;locks.delete(style);}};}`;
const portal=`import {useEffect,useState} from "react";import type {ReactNode} from "react";import {createPortal} from "react-dom";
export function ClientPortal({children}:{children:ReactNode}){const [mounted,setMounted]=useState(false);useEffect(()=>setMounted(true),[]);return mounted?createPortal(children,document.body):null;}`;
const modal=`import {useEffect,useId,useRef} from "react";import type {ReactNode} from "react";
${model}
export function NativeModal({open,title,onClose,children}:{open:boolean;title:string;onClose:()=>void;children:ReactNode}){const dialog=useRef<HTMLDialogElement>(null);const backdropStart=useRef(false);const titleId=useId();useEffect(()=>{const node=dialog.current;if(!node)return;if(!open){if(node.open)node.close();return;}const previous=document.activeElement instanceof HTMLElement?document.activeElement:null;const unlock=lockScroll(document.body.style);try{if(!node.open)node.showModal();node.querySelector<HTMLElement>("[data-initial-focus]")?.focus();}catch(error){unlock();throw error;}return ()=>{if(node.open)node.close();unlock();if(previous?.isConnected)previous.focus();};},[open]);return <dialog ref={dialog} aria-labelledby={titleId} onCancel={event=>{event.preventDefault();onClose();}} onPointerDown={event=>{backdropStart.current=event.target===event.currentTarget&&outsideBox(event.currentTarget.getBoundingClientRect(),event.clientX,event.clientY);}} onClick={event=>{const close=backdropStart.current&&event.target===event.currentTarget&&outsideBox(event.currentTarget.getBoundingClientRect(),event.clientX,event.clientY);backdropStart.current=false;if(close)onClose();}}><h2 id={titleId}>{title}</h2>{children}<button type="button" aria-label="Zamknij okno" onClick={onClose}>Zamknij</button></dialog>;}`;
const demo=`import {useState} from "react";
${modal}
${portal.replace('import {useEffect,useState} from "react";','').replace('import type {ReactNode} from "react";','')}
export function ModalDemo(){const [open,setOpen]=useState(false);const [title,setTitle]=useState("");const [notice,setNotice]=useState("");return <main><h1>Portal i natywny modal</h1><section style={{overflow:"hidden",maxHeight:180,border:"1px solid"}}><h2>Karta ticketu</h2><button type="button" onClick={()=>setOpen(true)}>Nowy ticket</button><ClientPortal><NativeModal open={open} title="Nowy ticket" onClose={()=>setOpen(false)}><form onSubmit={event=>{event.preventDefault();if(title.trim().length<5)return;setNotice("Utworzono: "+title.trim());setTitle("");setOpen(false);}}><label>Tytuł<input data-initial-focus value={title} onChange={event=>setTitle(event.currentTarget.value)} required minLength={5}/></label><button type="submit">Utwórz</button></form></NativeModal></ClientPortal></section><p role="status">{notice}</p><p>Zamknięcie okna zachowuje niewysłany tytuł; poprawne wysłanie go czyści.</p></main>;}`;
const exercises=[
['Zbuduj ClientPortal renderujące dzieci do document.body.','Portal zmienia miejsce DOM, nie pozycję w drzewie React.',portal],
['Nie odczytuj document podczas pierwszego SSR renderu.','Przykład montuje portal dopiero po efekcie klienta.',portal],
['Wyjaśnij, że ClientPortal nie pokazuje treści w HTML serwera.','To jawna strategia client-only.','Pierwszy wynik serwera i klienta to null, a po montowaniu klient dodaje portal. Nie twierdź, że jego treść była renderowana na serwerze ani że ta strategia pasuje do każdej treści wymagającej SSR.'],
['Zbuduj NativeModal sterowany propsem open.','Jednym właścicielem decyzji jest rodzic.',modal],
['Otwieraj przez showModal, a nie przez sam atrybut open.','ShowModal uruchamia modalną warstwę top layer.',modal],
['Zamykaj przez close, gdy prop open jest false.','Ref daje dostęp do API HTMLDialogElement.',modal],
['Powiąż tytuł dialogu przez unikalne aria-labelledby.','UseId zapobiega kolizjom dwóch instancji.',modal],
['Wybierz początkowe pole przez data-initial-focus.','Fokus jest operacją po commicie.',modal],
['Zapamiętaj wcześniejszy aktywny element przed otwarciem.','Po zamknięciu wróć do istniejącego wywołującego.',modal],
['Przywracaj fokus tylko do nadal podłączonego elementu.','Wywołujący mógł zniknąć.',modal],
['Obsłuż cancel przez preventDefault i onClose.','Rodzic nadal kontroluje open.',modal],
['Nie odsyłaj każdego programowego close z cleanup jako nowego żądania użytkownika.','StrictMode testuje setup i cleanup.','Przykład nie podpina onClose do wszystkich natywnych zdarzeń close, ponieważ sam close wywoływany w synchronizacji i cleanup nie jest nowym żądaniem użytkownika. Jawne przyciski i cancel zgłaszają onClose rodzicowi.'],
['Dodaj widoczny przycisk zamknięcia typu button.','Escape nie jest jedyną dostępną drogą.',modal],
['Sprawdzaj początek i koniec kliknięcia tła.','Przeciągnięcie zaznaczenia z treści na tło nie ma zamykać.',modal],
['Napisz outsideBox z uwzględnieniem granic elementu.','Punkt na krawędzi uznajemy za wewnętrzny.',model],
['Nie zamykaj, gdy target jest dzieckiem dialogu.','Kliknięcie formularza nie jest kliknięciem tła.',modal],
['Zablokuj przewijanie strony podczas otwarcia.','Modalność i blokada scroll to osobne aspekty.',modal],
['Zastosuj licznik współdzielonej blokady overflow.','Zamknięcie jednego właściciela nie odblokuje drugiego.',model],
['Przywróć wcześniejszą wartość overflow po ostatnim zwolnieniu.','Nie zakładaj, że zawsze było puste.',model],
['Nie pozwól podwójnemu release zmniejszać licznika dwa razy.','Zwolnienie jest idempotentne.',model],
['Posprzątaj scroll lock także przy błędzie showModal.','Nie zostawiaj hidden po nieudanym setup.',modal],
['Sprawdź setup→cleanup→setup przy otwartym dialogu.','Nie ukrywaj błędu wyłączeniem StrictMode.',modal],
['Umieść wywołanie wewnątrz overflow:hidden i porównaj portal z top layer.','Native dialog nie potrzebuje portalu do samej warstwy.','Portal uczy przeniesienia miejsca w DOM. ShowModal samo przenosi dialog do top layer, więc jego modalna warstwa nie wymaga portalu. W przykładzie oba narzędzia są połączone dydaktycznie, ale nie są równoważne.'],
['Sprawdź bąbelkowanie React przez portal.','Fizyczny rodzic DOM nie zastępuje rodzica React.',portal],
['Nie traktuj samego portalu jako implementacji focus trap.','Portal jest mechanizmem renderowania.','Dostępny modal wymaga także semantyki, fokusu, zamykania i ograniczenia interakcji z tłem. Tutaj modalność i zachowanie fokusu zapewnia natywny dialog otwarty przez showModal; sam createPortal tego nie robi.'],
['Ustal politykę zachowania draftu po zamknięciu.','Zamknięty dialog nadal może mieć zamontowane dzieci.',demo],
['Wyczyść tytuł dopiero po poprawnym wysłaniu formularza.','Zwykłe zamknięcie go zachowuje według komunikatu.',demo],
['Rozróżnij Reactowy handler JSX od natywnego KeyboardEvent listenera.','Typy mają podobne nazwy, lecz inne kontrakty.','OnCancel i onClick w JSX dostają zdarzenia Reacta. Document.addEventListener dostaje natywne zdarzenie DOM. Nie importuj React.KeyboardEvent, aby typować natywny listener dokumentu.'],
['Opisz ograniczenia blokady scroll jako wspólnej własności.','Niezależny kod zmieniający overflow też wymaga uzgodnionego mechanizmu.','LockScroll koordynuje właścicieli korzystających z tej samej funkcji. Nie rozwiązuje arbitralnych zmian stylu przez obcy kod. W aplikacji wybierz wspólny mechanizm blokady i przetestuj zagnieżdżone okna.'],
['Przetestuj Escape, przycisk, tło, zaznaczanie tekstu i przywrócenie fokusu.','Pełne zachowanie natywnego dialogu wymaga przeglądarki.',demo],
];
const concepts=[
['Co zmienia createPortal?','Fizyczne miejsce w DOM','Automatycznie właściciela context','Całe props aplikacji','Typ stanu'],
['Czy context działa przez portal zgodnie z drzewem React?','Tak','Nie','Tylko dla theme','Tylko w SSR'],
['Jak bąbelkują zdarzenia React portalu?','Według drzewa React','Wyłącznie według fizycznego body','Nigdy nie bąbelkują','Tylko po stopPropagation'],
['Czy portal sam zapewnia modalność?','Nie','Tak','Tylko przy body','Tylko z z-index'],
['Co daje showModal?','Natywny modal w top layer','Zwykły div z wysokim z-index','Automatyczny createPortal','Stan useReducer'],
['Czy sam open na dialog oznacza modalność?','Nie','Tak','Tylko w React 19','Tylko w TypeScript'],
['Czy natywny dialog potrzebuje portalu, aby wejść do top layer?','Nie','Tak','Tylko w overflow:hidden','Tylko z ref'],
['Jak właściciel steruje NativeModal?','Propsem open i callbackiem żądania zamknięcia','Mutacją prywatnego refa w renderze','Zawsze globalnym window','Przez key formularza'],
['Co robi cancel handler?','Zapobiega domyślnemu zamknięciu i zgłasza żądanie rodzicowi','Zapisuje każdy formularz','Zawsze usuwa DOM','Zmienia titleId'],
['Czy programowy close w cleanup musi oznaczać żądanie użytkownika?','Nie','Tak','Tylko w StrictMode','Tylko przy empty children'],
['Co wskazuje aria-labelledby dialogu?','Id jego tytułu','Sam tekst tytułu','Id przycisku zawsze','Nazwę komponentu'],
['Dlaczego useId?','Unikalne powiązania instancji','Globalna tożsamość danych','Zamknięcie dialogu','Focus trap'],
['Gdzie przenosić fokus?','Po commicie do odpowiedniego elementu','Podczas renderu','W type alias','W comparatorze'],
['Co po zamknięciu?','Przywrócić fokus właściwemu istniejącemu wywołującemu','Zawsze body','Zawsze pierwszy input aplikacji','Usunąć wszystkie refy'],
['Po co isConnected?','Cel przywrócenia fokusu mógł zniknąć','Potwierdza online API','Otwiera dialog','Tworzy portal'],
['Czy kliknięcie formularza ma zamykać tło?','Nie','Tak','Tylko po focus','Tylko w TSX'],
['Dlaczego śledzić pointerdown na tle?','Aby przeciągnięcie z treści nie udawało kliknięcia tła','Aby zmienić z-index','Aby zapisać draft','Aby wyłączyć cancel'],
['Czy punkt na krawędzi jest outside w helperze?','Nie','Tak','Tylko dla x','Tylko w React'],
['Czy modalność zawsze oznacza brak scroll strony według potrzeb produktu?','Nie, blokadę scroll sprawdzamy osobno','Tak bez sprawdzenia','Tylko w TSX','Tylko przy portal'],
['Po co licznik scroll lock?','Koordynuje wielu właścicieli tej samej blokady','Liczy wszystkie rendery','Tworzy focus trap','Zapisuje storage'],
['Co przywracamy po ostatnim unlock?','Wcześniejsze overflow','Zawsze auto','Zawsze scroll','Zawsze hidden'],
['Czy release ma być bezpieczny przy powtórzeniu?','Tak','Nie','Tylko w development','Tylko dla body'],
['Co jeśli showModal rzuci po nabyciu blokady?','Zwolnić blokadę przed ponownym rzuceniem','Zostawić hidden','Udawać sukces','Usunąć body'],
['Czy klientowy portal ma treść w pierwszym SSR HTML tego przykładu?','Nie','Tak','Tylko z children','Tylko w React 19'],
['Czy closed dialog automatycznie odmontowuje stan dzieci React?','Nie','Tak','Tylko z form','Tylko przy key'],
['Co robi poprawny submit w demo?','Czyści tytuł i zamyka','Tylko ukrywa komunikat','Usuwa wszystkie tickety','Resetuje cały root'],
['Co robi zwykłe zamknięcie w demo?','Zachowuje niewysłany tytuł','Zawsze czyści wszystkie dane','Wysyła API','Usuwa portal na zawsze'],
['Czy role=dialog i aria-modal same tworzą focus trap w div?','Nie','Tak','Tylko przy ref','Tylko z label'],
['Czy test stubu showModal potwierdza zachowanie całej przeglądarki?','Nie','Tak','Tylko w Node','Tylko z TS'],
['Czy portal i natywny dialog rozwiązują dokładnie ten sam problem?','Nie, to różne mechanizmy','Tak zawsze','Tylko w React 19','Tylko bez CSS'],
];
const quiz=concepts.map(row=>[...row,'Portal określa miejsce renderowania DOM. Modalność, fokus, własność stanu i blokada przewijania wymagają osobnych poprawnych mechanizmów.']);quiz.push(...require('./practice_ref_02.cjs').quiz.slice(0,30).map(row=>['Powtórka DOM okna: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const x of [-1,0,5,10,11])for(const y of [-1,0,5,10,11])add('Punkt '+x+','+y,`outsideBox({left:0,right:10,top:0,bottom:10},${x},${y})`,x<0||x>10||y<0||y>10);
for(const [label,expr,a]of [['Lock początek','(()=>{const s={overflow:"auto"};lockScroll(s);return s.overflow;})()','hidden'],['Release przywraca','(()=>{const s={overflow:"auto"};const stop=lockScroll(s);stop();return s.overflow;})()','auto'],['Pusty styl','(()=>{const s={overflow:""};const stop=lockScroll(s);stop();return s.overflow==="";})()',true],['Dwa lock jeden release','(()=>{const s={overflow:"auto"};const a=lockScroll(s);lockScroll(s);a();return s.overflow;})()','hidden'],['Dwa lock oba release','(()=>{const s={overflow:"scroll"};const a=lockScroll(s),b=lockScroll(s);a();b();return s.overflow;})()','scroll'],['Release dwa razy','(()=>{const s={overflow:"auto"};const a=lockScroll(s);a();a();return s.overflow;})()','auto'],['Dwie niezależne strony','(()=>{const a={overflow:"auto"},b={overflow:"scroll"};lockScroll(a);return b.overflow;})()','scroll'],['Drugi właściciel pierwszy wychodzi','(()=>{const s={overflow:"auto"};const a=lockScroll(s),b=lockScroll(s);b();return s.overflow;})()','hidden'],['Nowy cykl','(()=>{const s={overflow:"auto"};lockScroll(s)();const stop=lockScroll(s);stop();return s.overflow;})()','auto'],['Początkowe hidden','(()=>{const s={overflow:"hidden"};lockScroll(s)();return s.overflow;})()','hidden'],['Środek','outsideBox({left:10,right:20,top:10,bottom:20},15,15)',false],['Lewo innego rect','outsideBox({left:10,right:20,top:10,bottom:20},5,15)',true],['Dół innego rect','outsideBox({left:10,right:20,top:10,bottom:20},15,25)',true],['Nie mutuje rect','(()=>{const r={left:0,right:10,top:0,bottom:10};outsideBox(r,-1,-1);return r.left;})()',0],['Release void','(()=>{const s={overflow:"auto"};return lockScroll(s)()===undefined;})()',true]])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','auto','hidden'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik mechanizmu modala?',a,...wrong,'Helper geometrii bada punkt względem rect, a blokada scroll przywraca wcześniejszy styl dopiero po ostatnim właścicielu.',model+'\nconsole.log('+expr+');']);}
const project={'src/Modal.tsx':demo,'src/main.tsx':'import {createRoot} from "react-dom/client";import {ModalDemo} from "./Modal";import "./style.css";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<ModalDemo/>);','src/style.css':'body{font-family:system-ui;margin:1rem}dialog{border:0;border-radius:0.75rem;padding:1.5rem;width:min(30rem,calc(100vw - 3rem));max-height:calc(100vh - 3rem);overflow:auto}dialog::backdrop{background:rgb(0 0 0 / .45)}button,input{font:inherit;margin:0.4rem}:focus-visible{outline:3px solid #2563eb;outline-offset:2px}'};
module.exports={model,portal,modal,demo,exercises,quiz,project};
