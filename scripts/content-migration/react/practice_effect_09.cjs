const model=`export type Ticket={id:number;title:string;status:"OPEN"|"CLOSED"};export type StatusFilter="ALL"|Ticket["status"];export function visibleTickets(tickets:readonly Ticket[],query:string,status:StatusFilter):Ticket[]{const q=query.trim().toLowerCase();return tickets.filter(ticket=>(status==="ALL"||ticket.status===status)&&ticket.title.toLowerCase().includes(q));}
export function draftKey(ticketId:number):string{if(!Number.isSafeInteger(ticketId)||ticketId<0)throw RangeError("ID ticketu");return "workbench-note-"+ticketId;}
export function readDraft(storage:Pick<Storage,"getItem">,ticketId:number):{note:string;ok:boolean}{try{return {note:storage.getItem(draftKey(ticketId))??"",ok:true};}catch{return {note:"",ok:false};}}
export function secondsSince(refreshedAt:number,now:number):number{return Number.isFinite(refreshedAt)&&Number.isFinite(now)?Math.max(0,Math.floor((now-refreshedAt)/1000)):0;}`;
const notes=`import {useEffect,useState} from "react";
${model}
export function TicketNote({ticketId}:{ticketId:number}){const [initial]=useState(()=>{try{return readDraft(localStorage,ticketId);}catch{return {note:"",ok:false};}});const [note,setNote]=useState(initial.note);const [saved,setSaved]=useState<{note:string;status:"pending"|"saved"|"error"}>(()=>({note:initial.note,status:initial.ok?"pending":"error"}));useEffect(()=>{if(!initial.ok)return;const key=draftKey(ticketId);const id=setTimeout(()=>{try{localStorage.setItem(key,note);setSaved({note,status:"saved"});}catch{setSaved({note,status:"error"});}},500);return ()=>clearTimeout(id);},[ticketId,note,initial.ok]);const status=!initial.ok?"error":saved.note===note?saved.status:"pending";return <section><label>Notatka do #{ticketId}<textarea value={note} onChange={event=>setNote(event.currentTarget.value)}/></label><p role="status">{status==="pending"?"Oczekiwanie na zapis":status==="saved"?"Zapisano szkic lokalnie":"Nie odczytano lub nie zapisano szkicu"}</p></section>;}`;
const workbench=`import {useEffect,useEffectEvent,useState} from "react";
${model}
export function useDebouncedQuery(value:string,delay=300){const [debounced,setDebounced]=useState(value);useEffect(()=>{const id=setTimeout(()=>setDebounced(value),delay);return ()=>clearTimeout(id);},[value,delay]);return debounced;}
export function useSecondsSince(refreshedAt:number){const [now,setNow]=useState(()=>Date.now());useEffect(()=>{const update=()=>setNow(Date.now());update();const id=setInterval(update,1000);return ()=>clearInterval(id);},[]);return secondsSince(refreshedAt,now);}
import {TicketNote} from "./TicketNote";
export function TicketWorkbench({tickets,onSelectionChange,refreshedAt}:{tickets:readonly Ticket[];onSelectionChange:(id:number|null)=>void;refreshedAt:number}){const [query,setQuery]=useState("");const [status,setStatus]=useState<StatusFilter>("ALL");const [selectedId,setSelectedId]=useState<number|null>(null);const settled=useDebouncedQuery(query);const filtered=visibleTickets(tickets,settled,status);const selected=tickets.find(ticket=>ticket.id===selectedId)??null;const elapsed=useSecondsSince(refreshedAt);const handleEscape=useEffectEvent((event:KeyboardEvent)=>{if(event.key!=="Escape"||event.isComposing||event.defaultPrevented)return;setSelectedId(null);onSelectionChange(null);});useEffect(()=>{const listener=(event:KeyboardEvent)=>handleEscape(event);window.addEventListener("keydown",listener);return ()=>window.removeEventListener("keydown",listener);},[]);useEffect(()=>{const previous=document.title;document.title="DevDesk ("+filtered.length+")";return ()=>{document.title=previous;};},[filtered.length]);return <main><h1>Warsztat ticketów</h1><label>Szukaj<input value={query} onChange={event=>setQuery(event.currentTarget.value)}/></label><label>Status<select value={status} onChange={event=>{const value=event.currentTarget.value;if(value==="ALL"||value==="OPEN"||value==="CLOSED")setStatus(value);}}><option>ALL</option><option>OPEN</option><option>CLOSED</option></select></label><p>Wyników: {filtered.length}</p><ul>{filtered.map(ticket=><li key={ticket.id}><button type="button" aria-pressed={selectedId===ticket.id} onClick={()=>{setSelectedId(ticket.id);onSelectionChange(ticket.id);}}>{ticket.title}</button></li>)}</ul>{filtered.length===0&&<p>Brak wyników.</p>}{selected&&<section><h2>{selected.title}</h2><TicketNote key={selected.id} ticketId={selected.id}/></section>}<small>Od ostatniej aktualizacji danych: {elapsed} s</small></main>;}`;
const standalone=workbench.replace('import {TicketNote} from "./TicketNote";',notes.replace('import {useEffect,useState} from "react";','').replace(model,''));
const exercises=[
['Wypisz co najmniej dziewięć problemów przejętego TicketWorkbench.','Pogrupuj przyczyny, nie tylko policz useEffect.','Brak cleanup debounce; filtered jako stan pochodny; selected jako nieaktualna kopia; brak tickets w zależnościach kopii; powiadomienie rodzica efektem; reset notatki efektem; tytuł bez odpowiednich zależności; błędny klucz zapisu notatki; listener bez cleanup; interwał bez cleanup i count ze starego domknięcia.'],
['Usuń filtered ze stanu i wylicz visibleTickets.','Query po debounce i status są źródłami widoku.',standalone],
['Usuń selected ze stanu i wylicz go z ID oraz aktualnych tickets.','Zmiana tytułu w props ma być od razu widoczna.',standalone],
['Nie traktuj ID=0 jako braku wyboru.','Sprawdź znaleziony obiekt.',standalone],
['Zbuduj debounce z clearTimeout.','Input pokazuje query, lista settled.',standalone],
['Powiadamiaj rodzica w handlerze wyboru.','Nie przez efekt zależny od selectedId.',standalone],
['Powiadamiaj rodzica po Escape w reakcji na to zdarzenie.','Listener uruchamia lokalny Effect Event.',standalone],
['Użyj aktualnego onSelectionChange bez zamrożenia pierwszego props.','UseEffectEvent oddziela reakcję od subskrypcji.',standalone],
['Pomiń Escape w trakcie kompozycji tekstu lub po preventDefault.','Nie przejmuj zdarzenia obsłużonego wcześniej.',standalone],
['Usuń listener tą samą funkcją w cleanup.','Nie używaj nowej lambdy przy remove.',standalone],
['Synchronizuj document.title tylko ze zmieniającą się liczbą wyników.','Licznik jest wyliczany, a tytuł zewnętrzny.',standalone],
['Przywróć tytuł w cleanup dla pojedynczego właściciela.','Nie deklaruj arbitrażu wielu niezależnych tytułów.',standalone],
['Wydziel TicketNote z key=selected.id.','Nowy ticket ma nową instancję szkicu.',standalone],
['Zbuduj draftKey walidujące nieujemny bezpieczny numer.','Klucz nie może zależeć od przypadkowego null.',model],
['Wczytaj notatkę właściwego ticketu w leniwej inicjalizacji.','Nie nadpisuj zapisanej pracy początkowym pustym tekstem.',notes],
['Zablokuj autosave, jeżeli odczyt poprzedniej notatki się nie udał.','Nieznane dane nie powinny być nadpisane pustym fallbackiem.',notes],
['Zapisuj szkic po 500 ms bezczynności.','Nowy znak anuluje oczekujący timeout.',notes],
['Przechwyć klucz i tekst konkretnej instancji do zapisu.','Zmiana ticketu odmontowuje poprzedniego właściciela.',notes],
['Powiąż status saved z konkretnym tekstem.','Nowa edycja nie może wyglądać jak już zapisana.',notes],
['Obsłuż błąd setItem i zachowaj wpisy użytkownika.','Nie deklaruj trwałości po nieudanej operacji.',notes],
['Ustal politykę opuszczenia notatki przed końcem debounce.','Cleanup anuluje oczekujący zapis.','Ten przykład anuluje jeszcze nierozpoczęty zapis po odmontowaniu notatki. Jeśli produkt ma gwarantować zachowanie ostatniego znaku przed przełączeniem, dodaj jawny zapis w handlerze opuszczenia, nadrzędny magazyn szkiców albo inną przetestowaną politykę flush. Nie obiecuj tego samym debounce.'],
['Mierz wiek danych od jawnego refreshedAt zamiast udawać odświeżanie tickami.','Interwał tylko odświeża odczyt czasu.',standalone],
['Posprzątaj interwał zegara.','Nie zależy od zamkniętej pierwszej liczby sekund.',standalone],
['Obsłuż refreshedAt w przyszłości przez zero.','Nie pokazuj ujemnego wieku danych.',model],
['Obsłuż zmienione tickets bez efektu kopiującego całą listę.','Wyniki i selected pochodzą z aktualnych props.',standalone],
['Nie resetuj wyboru wyłącznie dlatego, że query ukryło rekord.','To jawna polityka tego ekranu.','Selected pochodzi z pełnej listy, więc karta może pozostać widoczna przy filtrze ukrywającym jej wiersz. Jeśli produkt wymaga innej polityki, ustal ją w handlerze zmiany filtra zamiast dodawać ukryty łańcuch efektów.'],
['Użyj natywnych button w li zamiast klikalnego li.','Wybór jest osiągalny klawiaturą.',standalone],
['Sprawdź efekty pozostawione po refaktoryzacji.','Każdy ma zewnętrzny system.','Pozostają timer debounce, timer aktualizacji zegara, tytuł dokumentu, listener Escape oraz autosave notatki. Filtered, selected i licznik nie wymagają efektów. Nie oceniaj jakości wyłącznie liczbą hooków.'],
['Sprawdź warianty StrictMode i zmianę callbacku rodzica.','Weryfikacja typu i build nie dowodzi pełnej obsługi klawiatury.',standalone],
['Porównaj działanie przed i po refaktoryzacji na kompletnym scenariuszu.','Wpisywanie, filtrowanie, ID=0, Escape, zapis i zmiana danych.',standalone],
];
const concepts=[
['Co zastępuje stan filtered?','Wyliczenie z props, settled query i status','Nowy efekt setCount','Globalny mutable array','Key listy'],
['Co zastępuje kopię selected?','Find z aktualnych tickets i selectedId','Kopia pierwszego ticketu na zawsze','Stan label','Liczba wyników'],
['Czy ID=0 jest poprawnym wyborem?','Tak','Nie','Tylko jako string','Tylko bez key'],
['Gdzie powiadomić rodzica o kliknięciu wiersza?','W handlerze tego zdarzenia','W efekcie na count','W renderze listy','W cleanup notatki'],
['Jak Escape odczytuje aktualny callback?','Przez lokalny Effect Event','Przez brak wszystkich deps bez uzasadnienia','Zawsze przez pierwszy closure','Z dokument.title'],
['Dlaczego usuwać listener?','Aby zakończyć subskrypcję danej instancji','Aby zapisać notatkę','Aby zmienić key','Aby zwiększyć count'],
['Po co clearTimeout debounce?','Anuluje wcześniejszą oczekującą reakcję','Anuluje każdy request serwera','Resetuje props','Gwarantuje zero renderów'],
['Którą wartość pokazuje input?','Surowe query','Tylko settled query','Liczbę wyników','Snapshot storage'],
['Którą wartość używa filtr?','Settled query po debounce','Zawsze pierwszy query','SelectedId','RefreshedAt'],
['Co restartuje notatkę dla innego ticketu?','Key oparty na jego ID','Zmiana samej klasy CSS','Każdy nowy tytuł','Efekt rodzica na filtered'],
['Kiedy czytać zapisany szkic?','Przed rozpoczęciem autosave nowej instancji','Dopiero po nadpisaniu pustym tekstem','Tylko w cleanup','Nigdy'],
['Co jeśli odczyt szkicu rzuci?','Blokujemy zapis nieznanej poprzedniej pracy w tym przykładzie','Zawsze nadpisujemy pustym stringiem','Udaje się sukces','Usuwamy klucz'],
['Co robi anulowanie debounce przy zmianie ticketu?','Kasuje oczekujący zapis starej instancji','Gwarantuje zapis ostatniej litery','Przenosi tekst pod nowy klucz','Wysyła API'],
['Czy debounce sam gwarantuje zapis przed opuszczeniem?','Nie','Tak','Tylko z key','Tylko przy 500ms'],
['Jak uzyskać gwarancję zachowania ostatnich zmian przed przejściem?','Zaprojektować jawny flush lub nadrzędny magazyn','Usunąć cleanup','Udawać saved','Zwiększyć timeout'],
['Co oznacza saved w modelu notatki?','Zapis tego konkretnego tekstu','Zapis każdego przyszłego znaku','Sukces wszystkich kart','Zapis serwera'],
['Czy błąd setItem powinien usuwać wpisany tekst?','Nie','Tak','Tylko w StrictMode','Tylko z pustą notatką'],
['Co jest zależnością tytułu?','Liczba wyników, którą odczytuje synchronizacja','Każdy cały obiekt App','Wyłącznie selectedId','Zawsze []'],
['Czy tytuł i count to ta sama kategoria danych?','Nie, count jest wyliczany, tytuł jest zewnętrzny','Tak','Tylko w React 19','Tylko z effect'],
['Z czego liczymy wiek danych?','RefreshedAt oraz odczyt czasu','Wyłącznie liczba callbacków timera','Długość query','Liczba effectów'],
['Czy timer co sekundę gwarantuje wykonanie dokładnie co 1000ms?','Nie','Tak','Tylko przy aktywnej karcie','Tylko z TypeScript'],
['Dlaczego nie zamykamy secondsAgo+1 z pierwszego renderu?','Bo daje nieaktualny snapshot','Bo number jest readonly','Bo effect nie obsługuje liczb','Bo Date.now jest typem'],
['Czy useEffectEvent jest callbackiem do przycisku?','Nie','Tak','Tylko po click','Tylko w TSX'],
['Dlaczego klikalny button lepszy od samego onClick na li?','Ma natywną obsługę klawiatury i semantykę','Zawsze szybszy o jeden render','Nie potrzebuje etykiety','Nie podlega focus'],
['Czy selected musi zniknąć tylko dlatego, że filtr ukrył wiersz?','To zależy od jawnej polityki produktu','Zawsze tak','Nigdy nie wolno','Tylko w dev'],
['Czy tsc wykryje wszystkie problemy przejętej wersji?','Nie','Tak','Tylko strict','Tylko dla effects'],
['Czy sama liczba pozostałych efektów dowodzi poprawności?','Nie','Tak','Tylko jeśli pięć','Tylko po build'],
['Co testować przy zmianie props tickets?','Aktualny tytuł, wyniki i wybrany rekord','Wyłącznie CSS','Tylko długość pliku','Tylko importy'],
['Czy StrictMode powinien być wyłączony, aby ukryć duplikaty?','Nie, napraw cleanup','Tak zawsze','Tylko dla notatek','Tylko w TSX'],
['Jakie problemy rozwiązuje refaktoryzacja?','Podział źródeł danych, zdarzeń i zasobów','Usuwa wszystkie efekty','Zastępuje backend','Gwarantuje trwałość bez błędów storage'],
];
const quiz=concepts.map(row=>[...row,'Workbench ma wyliczać lokalne dane i jawnie obsługiwać zdarzenia, a pozostawione efekty zarządzają zewnętrznymi zasobami i zapisem.']);quiz.push(...require('./practice_effect_05.cjs').quiz.slice(0,30).map(row=>['Powtórka audytu efektów: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const seconds of [-1,0,0.5,1,1.9,5,60,3600])add('Wiek '+seconds,`secondsSince(10000,10000+${seconds}*1000)`,Math.max(0,Math.floor(seconds)));
for(const id of [0,1,42,43,1000])add('Klucz '+id,`draftKey(${id})`,'workbench-note-'+id);
for(const query of ['',' API ','api','SESJA','brak'])for(const status of ['ALL','OPEN','CLOSED']){const rows=[['API','OPEN'],['Sesja','CLOSED']];add('Filtr '+JSON.stringify(query)+' '+status,`visibleTickets(data,${JSON.stringify(query)},${JSON.stringify(status)}).length`,rows.filter(([title,current])=>(status==='ALL'||status===current)&&title.toLowerCase().includes(query.trim().toLowerCase())).length);}
for(const [label,expr,a]of [['Pusty szkic','readDraft({getItem:()=>null},0).note.length',0],['Zapisany szkic','readDraft({getItem:()=>"Mój tekst"},0).note','Mój tekst'],['Odczyt poprawny','readDraft({getItem:()=>null},0).ok',true],['Błąd odczytu','readDraft({getItem(){throw Error("Blokada");}},0).ok',false],['Nienaruszony tekst','readDraft({getItem:()=>"  tekst  "},0).note.length',9],['Key właściwego ID','readDraft({getItem:key=>key},43).note','workbench-note-43'],['Ujemny ID','(()=>{try{draftKey(-1);return false;}catch{return true;}})()',true],['Ułamkowy ID','(()=>{try{draftKey(0.5);return false;}catch{return true;}})()',true],['NaN czasu','secondsSince(NaN,0)',0],['Snapshot rekord','visibleTickets(data,"api","ALL")[0]===data[0]',true],['Nowa lista','visibleTickets(data,"","ALL")===data',false],['ID zero','data.find(ticket=>ticket.id===0)?.title','API']])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);const data='const data:readonly Ticket[]=[{id:0,title:"API",status:"OPEN"},{id:43,title:"Sesja",status:"CLOSED"}];';for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik po refaktoryzacji Workbench?',a,...wrong,'Dane widoku są liczone bez efektu, klucz szkicu pochodzi z ID właściciela, a wiek jest nieujemnym odstępem czasu.',model+'\n'+data+'console.log('+expr+');']);}
const project={'src/TicketNote.tsx':notes,'src/Workbench.tsx':workbench,'src/main.tsx':'import {StrictMode,useState} from "react";import {createRoot} from "react-dom/client";import {TicketWorkbench} from "./Workbench";const initial=[{id:0,title:"API timeout",status:"OPEN" as const},{id:43,title:"Błąd logowania",status:"CLOSED" as const}];function App(){const [refreshedAt,setRefreshedAt]=useState(()=>Date.now());const [notice,setNotice]=useState("");return <><button type="button" onClick={()=>setRefreshedAt(Date.now())}>Oznacz aktualizację danych</button><p role="status">{notice}</p><TicketWorkbench tickets={initial} refreshedAt={refreshedAt} onSelectionChange={id=>setNotice(id===null?"Wyczyszczono wybór":"Wybrano #"+id)}/></>;}const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<StrictMode><App/></StrictMode>);'};
module.exports={model,notes,workbench:standalone,exercises,quiz,project};
