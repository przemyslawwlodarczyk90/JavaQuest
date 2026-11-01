const model=`export function nextIndex(count:number,current:number,key:string):number|null{if(!Number.isSafeInteger(count)||count<0)throw RangeError("Liczba elementów");if(count===0)return null;if(key==="Home")return 0;if(key==="End")return count-1;const forward=key==="j"||key==="ArrowDown",back=key==="k"||key==="ArrowUp";if(!forward&&!back)return null;if(!Number.isSafeInteger(current)||current<0||current>=count)return 0;return Math.max(0,Math.min(count-1,current+(forward?1:-1)));}
export function cycleIndex(count:number,current:number,shift:boolean):number|null{if(count<=0)return null;if(current<0||current>=count)return shift?count-1:0;return (current+(shift?-1:1)+count)%count;}`;
const nav=`import {useId,useRef,useState} from "react";
${model}
export type Ticket={id:number;title:string};export function TicketNavigator({tickets,onOpen}:{tickets:readonly Ticket[];onOpen:(id:number)=>void}){const [activeId,setActiveId]=useState<number|null>(tickets[0]?.id??null);const buttons=useRef(new Map<number,HTMLButtonElement>());const help=useId();const active=tickets.find(ticket=>ticket.id===activeId)??tickets[0]??null;return <section aria-label="Lista ticketów" aria-describedby={help} onKeyDown={event=>{if(event.ctrlKey||event.metaKey||event.altKey||event.nativeEvent.isComposing||event.repeat)return;const target=event.target;if(target instanceof HTMLElement&&(target.isContentEditable||target.closest("input,textarea,select")))return;const current=tickets.findIndex(ticket=>ticket.id===active?.id);const next=nextIndex(tickets.length,current,event.key);if(next===null)return;const ticket=tickets[next];event.preventDefault();setActiveId(ticket.id);const node=buttons.current.get(ticket.id);node?.focus();node?.scrollIntoView({block:"nearest",behavior:"auto"});}}><p id={help}>W liście: j/k lub strzałki góra/dół, Home/End. Enter lub Spacja otwierają przycisk ticketu.</p><ul>{tickets.map(ticket=><li key={ticket.id}><button type="button" ref={node=>{if(!node)return;buttons.current.set(ticket.id,node);return ()=>{if(buttons.current.get(ticket.id)===node)buttons.current.delete(ticket.id);};}} tabIndex={ticket.id===active?.id?0:-1} onFocus={()=>setActiveId(ticket.id)} onClick={()=>onOpen(ticket.id)}>{ticket.title}</button></li>)}</ul>{!tickets.length&&<p>Brak ticketów.</p>}</section>;}`;
const modal=require('./practice_ref_05.cjs').modal;
const page='import {useEffect,useId,useRef,useState} from "react";import type {ReactNode} from "react";\n'+modal.replace('import {useEffect,useId,useRef} from "react";import type {ReactNode} from "react";','')+'\n'+nav.replace('import {useId,useRef,useState} from "react";','')+`\nexport function FocusDemo(){const [open,setOpen]=useState(false);const [title,setTitle]=useState("");const [notice,setNotice]=useState("");return <><a className="skip-link" href="#main-content">Przejdź do treści</a><header><h1>Obsługa klawiaturą</h1><button type="button" onClick={()=>setOpen(true)}>Nowy ticket</button></header><main id="main-content" tabIndex={-1}><TicketNavigator tickets={[{id:0,title:"API timeout"},{id:43,title:"Logowanie"}]} onOpen={id=>setNotice("Otworzono ticket #"+id)}/><p role="status">{notice}</p></main><NativeModal open={open} title="Nowy ticket" onClose={()=>setOpen(false)}><form onSubmit={event=>{event.preventDefault();if(!title.trim())return;setNotice("Utworzono: "+title.trim());setTitle("");setOpen(false);}}><label>Tytuł<input data-initial-focus required value={title} onChange={event=>setTitle(event.currentTarget.value)}/></label><button type="submit">Utwórz</button></form></NativeModal></>;}`;
const exercises=[
['Zdefiniuj trzy etapy fokusu modala: wejście, ograniczenie zakresu i powrót.','Każdy etap ma osobny cel.','Po otwarciu fokus trafia do odpowiedniego elementu. Podczas modalności tło jest niedostępne, a nawigacja pozostaje w oknie. Po zamknięciu fokus wraca do sensownego miejsca, zwykle przycisku otwierającego.'],
['Przenieś początkowy fokus przez NativeModal.','ShowModal tworzy natywną modalność, a data-initial-focus wskazuje pole.',modal],
['Użyj natywnego dialogu do ograniczenia interakcji z tłem.','Nie udawaj tego samym aria-modal.',modal],
['Wyjaśnij różnicę inert i aria-modal.','Semantyka nie jest tym samym co działanie przeglądarki.','Inert wyłącza interakcje i fokus poddrzewa. Aria-modal opisuje modalność technologiom asystującym, lecz samo nie blokuje klawiatury ani kliknięć. Własny dialog div wymaga rzeczywistej implementacji; native showModal zapewnia modalną obsługę przeglądarki.'],
['Przywróć fokus istniejącemu wywołującemu po close.','Sprawdź isConnected przed focus.',modal],
['Obsłuż zniknięcie wywołującego w trakcie otwartego okna.','Nie próbuj kierować użytkownika do odłączonego node.',modal],
['Dodaj działający przycisk zamknięcia oraz Escape.','Dostępność nie może zależeć wyłącznie od skrótu.',modal],
['Nie ustawiaj tabindex na samym natywnym dialog.','Wzorzec div z tabIndex=-1 nie jest identyczny z HTML dialog.','Własny kontener div dialogu może potrzebować programowego miejsca fokusu. Dla natywnego dialogu wybierz odpowiedni element wewnątrz i korzystaj z jego mechanizmu; nie kopiuj bezmyślnie wszystkich atrybutów z innego wzorca.'],
['Wyjaśnij ograniczenia prostego selektora focusable.','Hidden, inert, radio, Shadow DOM i iframe wymagają staranności.','Lista kilku selektorów nie jest pełną biblioteką zarządzania fokusem. Dla niestandardowego modala użyj kompletnego, przetestowanego rozwiązania albo natywnego dialogu. Model cyklu indeksów z lekcji służy do nauki, nie zastępuje analizy rzeczywistej kolejności fokusu.'],
['Napisz cycleIndex dla modelu Tab i Shift+Tab.','Pusta kolekcja nie ma celu.',model],
['Napisz nextIndex dla j/k i strzałek.','W tej liście granice są zatrzymywane, a nie zawijane.',model],
['Obsłuż pustą listę bez indeksu -1 jako celu.','NextIndex zwraca null.',model],
['Obsłuż nieaktualny activeId przez wyliczony fallback.','Nie trzymaj kopii aktywnego obiektu.',nav],
['Nie zgub ticketu z ID=0.','Null oznacza brak, a zero jest poprawnym ID.',nav],
['Podepnij keydown do zakresu listy zamiast globalnie do całego okna.','Skrót nie ma otwierać ticketu po Enter w obcym formularzu.',nav],
['Pomiń edytowane pola i contenteditable.','Nie przejmuj wpisywania litery j w tekście.',nav],
['Pomiń ctrl/meta/alt, composing i repeat.','Szanuj inne skróty i kompozycję tekstu.',nav],
['Dodaj Home/End dla skrajów listy.','To inne zachowanie niż j/k na granicy.',nav],
['Przenieś fokus i przewiń wybrany element w handlerze nawigacji.','Zmiana zaznaczenia sama nie wywołuje focus.',nav],
['Użyj natywnego button do otwierania ticketu.','Enter i Spacja mają zachowanie przycisku.',nav],
['Nie obsługuj Enter drugi raz globalnym listenerem nad button.','Unikaj podwójnego onOpen.',nav],
['Zbuduj jeden przystanek Tab dla roving listy.','Aktywny przycisk ma 0, pozostałe -1.',nav],
['Aktualizuj activeId po fokusie uzyskanym inną drogą.','Stan podąża za aktualną kontrolką listy.',nav],
['Posprzątaj mapę przycisków przez callback ref.','Stare cleanup nie usuwa nowego node.',nav],
['Dodaj skip link na początku strony.','Prowadzi do main z identyfikatorem.',page],
['Uczyń main programowo fokusowalnym przez tabIndex=-1.','Nie dodawaj go do zwykłej kolejności Tab bez potrzeby.',page],
['Zadbaj o widoczny focus-visible w CSS.','Nie wyłączaj outline bez równoważnego wskaźnika.',page],
['Nie ustawiaj inert na przodku obejmującym własny modal div.','Można wyłączyć także samo okno.','Przy własnym portalu oznaczaj inert wyłącznie właściwe tło poza modalem. Dla showModal przeglądarka obsługuje modalne tło. Sprawdź rzeczywistą strukturę DOM zamiast dodawać inert do całej strony na ślepo.'],
['Zaplanuj fokus po usunięciu aktywnego elementu dynamicznej listy.','Fallback stanu nie gwarantuje automatycznego przeniesienia DOM focus.','Przykład zakłada stabilną listę podczas nawigacji. Jeśli usuwasz element, wybierz logiczny następny cel i przenieś fokus w odpowiednim zdarzeniu lub po zatwierdzeniu nowego DOM. Nie uznawaj samej zmiany activeId za pełny plan.'],
['Przejdź tworzenie i wybór ticketu bez myszy.','Sprawdź także Shift+Tab, Escape oraz zamknięcie do wywołującego.',page],
];
const concepts=[
['Jakie są trzy fazy zarządzania fokusem modala?','Wejście, utrzymanie zakresu, powrót','Tylko otwarcie','Tylko cleanup','Tylko aria-label'],
['Czy aria-modal samo blokuje fokus tła?','Nie','Tak','Tylko w React 19','Tylko z div'],
['Co robi inert?','Wyłącza interakcje i fokus poddrzewa','Tylko zmienia kolor','Zawsze tworzy dialog','Wywołuje onClose'],
['Co zapewnia showModal?','Natywną modalną obsługę przeglądarki','Zwykły nonmodal open','Portal automatycznie','Dowolny store'],
['Czy można bezmyślnie dodać inert na wspólnego przodka modala div?','Nie, można wyłączyć też modal','Tak zawsze','Tylko dla body','Tylko z ref'],
['Dokąd zwykle wraca fokus po zamknięciu?','Do istniejącego wywołującego lub logicznej alternatywy','Zawsze do body','Do ostatniego elementu strony','Do pierwszego ukrytego pola'],
['Po co isConnected przy przywracaniu?','Cel mógł zostać usunięty','Mierzy sieć','Otwiera modal','Zastępuje tabIndex'],
['Czy prosty selector kilku elementów jest pełnym algorytmem tabbable?','Nie','Tak','Tylko w TSX','Tylko z querySelectorAll'],
['Czy hidden i disabled wpływają na rzeczywisty fokus?','Tak','Nie','Tylko z useRef','Tylko w development'],
['Czy natywnemu dialogowi należy kopiować tabindex z divowego wzorca?','Nie bezrefleksyjnie; użyj właściwego wzorca native','Tak zawsze','Tylko 0','Tylko dodatnie wartości'],
['Gdzie obsługujemy j/k w przykładzie?','W zakresie listy','Na całym window bez ograniczeń','W każdym input formularza','Na serwerze'],
['Dlaczego nie przejmować Enter globalnie?','Może zakłócić formularze i powtórzyć kliknięcie button','Enter nie istnieje w KeyboardEvent','React zabrania','To zawsze klawisz przewijania'],
['Jak Enter działa na button ticketu?','Natywnie aktywuje przycisk','Wymaga drugiego listenera','Zawsze tylko przesuwa fokus','Zmienia tabIndex'],
['Czy Spacja może aktywować natywny button?','Tak','Nie','Tylko w React 19','Tylko z keydown'],
['Kiedy ignorować j/k?','Podczas edycji i kompozycji lub z modyfikatorami','Zawsze gdy lista niepusta','Tylko po API','Tylko w TSX'],
['Co robi nextIndex na ostatnim j?','Zatrzymuje się na ostatnim','Zawija zawsze','Zwraca -1','Usuwa rekord'],
['Co robi k na pierwszym?','Zatrzymuje się na pierwszym','Zawsze wybiera ostatni','Zwraca null mimo niepustej listy','Zamyka modal'],
['Co przy count=0?','Brak celu null','Indeks -1 jako poprawny','Zawsze 0','Infinity'],
['Czy ID=0 jest poprawne?','Tak','Nie','Tylko jako string','Tylko po focus'],
['Co robi roving tabindex?','Ogranicza wejście Tab do aktywnej kontrolki kolekcji','Dodaje każdą kontrolkę do Tab','Blokuje wszystkie przyciski','Zastępuje callback'],
['Czy zmiana activeId sama przenosi DOM focus?','Nie','Tak','Tylko z state','Tylko w React'],
['Po co scrollIntoView po nawigacji?','Ujawnia nowo fokusowany element w widoku','Zmienia selectedId automatycznie','Zapisuje notatkę','Włącza inert'],
['Co robi skip link?','Pozwala ominąć powtarzalną nawigację','Zamyka modal','Przestawia wszystkie tabIndex','Tworzy portal'],
['Po co main tabIndex=-1?','Umożliwia programowy fokus bez zwykłego przystanku Tab','Ukrywa main','Wyłącza czytnik','Nadaje focus trap'],
['Czy outline można usuwać bez zamiennika?','Nie','Tak','Tylko dla button','Tylko w ciemnym motywie'],
['Czy fallback aktywnego rekordu rozwiązuje cały fokus po usunięciu node?','Nie','Tak','Tylko z Map','Tylko w TypeScript'],
['Czy czytnik i klawiaturę potwierdza samo tsc?','Nie','Tak','Tylko strict','Tylko z ReactNode'],
['Czy własna pętla indeksów jest pełnym focus trap modala?','Nie','Tak','Tylko z shift','Tylko dla null'],
['Czy każdy element z querySelectorAll<HTMLElement> jest przez to faktycznie fokusowalny?','Nie, generyk nie waliduje całego DOM runtime','Tak','Tylko przy hidden','Tylko z tabindex'],
['Co jest celem zarządzania fokusem?','Przewidywalny przepływ interakcji bez myszy','Jak najwięcej refów','Usunięcie wszystkich props','Globalne przejęcie każdego klawisza'],
];
const quiz=concepts.map(row=>[...row,'Semantyka ARIA, natywne zachowanie i programowy fokus to różne warstwy. Zakres skrótów oraz właściwy cel muszą odpowiadać przepływowi użytkownika.']);quiz.push(...require('./practice_ref_05.cjs').quiz.slice(0,30).map(row=>['Powtórka modalnego fokusu: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const current of [-1,0,1,2,9])for(const key of ['j','k','Home','End'])add('Lista '+current+' '+key,`nextIndex(3,${current},${JSON.stringify(key)})`,key==='Home'?0:key==='End'?2:current<0||current>=3?0:Math.max(0,Math.min(2,current+(key==='j'?1:-1))));
for(const [label,expr,a]of [['Puste j','nextIndex(0,0,"j")??"brak"','brak'],['Puste k','nextIndex(0,0,"k")??"brak"','brak'],['Enter native','nextIndex(3,0,"Enter")??"brak"','brak'],['Down','nextIndex(3,0,"ArrowDown")',1],['Up','nextIndex(3,2,"ArrowUp")',1],['Jedna j','nextIndex(1,0,"j")',0],['Jedna k','nextIndex(1,0,"k")',0],['Brak obcego klawisza','nextIndex(3,0,"x")??"brak"','brak'],['Cycle next','cycleIndex(3,2,false)',0],['Cycle previous','cycleIndex(3,0,true)',2],['Cycle pusty','cycleIndex(0,0,false)??"brak"','brak'],['Cycle jeden','cycleIndex(1,0,false)',0],['Cycle unknown next','cycleIndex(3,-1,false)',0],['Cycle unknown previous','cycleIndex(3,-1,true)',2],['Cycle środek next','cycleIndex(3,1,false)',2],['Cycle środek previous','cycleIndex(3,1,true)',0],['Count ujemny','(()=>{try{nextIndex(-1,0,"j");return false;}catch{return true;}})()',true],['Count ułamek','(()=>{try{nextIndex(1.5,0,"j");return false;}catch{return true;}})()',true],['Duże J nie skrót','nextIndex(3,0,"J")??"brak"','brak'],['Strzałka pozioma nie skrót','nextIndex(3,0,"ArrowRight")??"brak"','brak']])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);for(const [label,expr,a]of cases){const wrong=['0','1','2','brak','true','false'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki cel modelu klawiatury?',a,...wrong,'Nawigacja listy zatrzymuje się na granicach, a model cyklu Tab zawija. To obliczenie indeksu, nie test faktycznej fokusowalności DOM.',model+'\nconsole.log('+expr+');']);}
const project={'src/Focus.tsx':page,'src/main.tsx':'import {createRoot} from "react-dom/client";import {FocusDemo} from "./Focus";import "./style.css";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<FocusDemo/>);','src/style.css':'.skip-link{position:absolute;left:1rem;top:0;transform:translateY(-130%);background:#fff;color:#111;padding:0.5rem;z-index:100}.skip-link:focus{transform:none}:focus-visible{outline:3px solid #2563eb;outline-offset:3px}body{font-family:system-ui}dialog{border-radius:0.5rem;padding:1rem;max-width:90vw}dialog::backdrop{background:rgb(0 0 0 / .5)}'};
module.exports={model,nav,modal,page,exercises,quiz,project};
