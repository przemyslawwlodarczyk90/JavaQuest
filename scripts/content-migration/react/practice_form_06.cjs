const model=`export const PRIORITIES=["LOW","MEDIUM","HIGH","CRITICAL"] as const;export type Priority=(typeof PRIORITIES)[number];
export type Ticket={id:number;title:string;priority:Priority;description:string;status:"OPEN"|"DONE";createdAt:string};export type Changes=Pick<Ticket,"title"|"priority">;
export function isPriority(value:string):value is Priority{return PRIORITIES.some(priority=>priority===value);}
export function toDraft(ticket:Ticket):Changes{return {title:ticket.title,priority:ticket.priority};}
export function isDirty(draft:Changes,ticket:Ticket):boolean{return draft.title!==ticket.title||draft.priority!==ticket.priority;}
export function applyChanges(tickets:readonly Ticket[],id:number,changes:Changes):Ticket[]{if(changes.title.trim().length<5)throw Error("Minimum 5 znaków");return tickets.map(ticket=>ticket.id===id?{...ticket,title:changes.title.trim(),priority:changes.priority}:ticket);}`;
const form=`import {useId,useState} from "react";
${model}
export function EditTicketForm({ticket,onSave,onCancel}:{ticket:Ticket;onSave:(id:number,changes:Changes)=>void;onCancel:()=>void}){const [draft,setDraft]=useState(()=>toDraft(ticket));const [submitted,setSubmitted]=useState(false);const id=useId();const invalid=draft.title.trim().length<5;const dirty=isDirty(draft,ticket);return <form noValidate onSubmit={event=>{event.preventDefault();setSubmitted(true);if(invalid)return;onSave(ticket.id,{title:draft.title.trim(),priority:draft.priority});}}><h2>Edycja #{ticket.id}</h2><label htmlFor={id}>Tytuł</label><input id={id} value={draft.title} onChange={event=>{const title=event.currentTarget.value;setDraft(previous=>({...previous,title}));}} aria-invalid={submitted&&invalid?true:undefined} aria-describedby={submitted&&invalid?id+"-error":undefined}/>{submitted&&invalid&&<p id={id+"-error"}>Minimum 5 znaków</p>}<label htmlFor={id+"-priority"}>Priorytet</label><select id={id+"-priority"} value={draft.priority} onChange={event=>{const priority=event.currentTarget.value;if(isPriority(priority))setDraft(previous=>({...previous,priority}));}}>{PRIORITIES.map(priority=><option key={priority} value={priority}>{priority}</option>)}</select><p>{dirty?"Niezapisane zmiany":"Brak zmian"}</p><button type="submit">Zapisz</button><button type="button" onClick={onCancel}>Anuluj</button><button type="button" onClick={()=>{setDraft(toDraft(ticket));setSubmitted(false);}}>Przywróć dane</button></form>;}`;
const panel=`import {EditTicketForm} from "./EditTicketForm";import type {Ticket,Changes} from "./EditTicketForm";
export function EditorPanel({ticket,onSave,onCancel}:{ticket:Ticket|null;onSave:(id:number,changes:Changes)=>void;onCancel:()=>void}){return ticket===null?<p>Wybierz ticket</p>:<EditTicketForm key={ticket.id} ticket={ticket} onSave={onSave} onCancel={onCancel}/>;}`;
const standalonePanel=form+'\n'+panel.replace('import {EditTicketForm} from "./EditTicketForm";import type {Ticket,Changes} from "./EditTicketForm";','');
const app=`import {useState} from "react";import {EditorPanel} from "./EditorPanel";import {applyChanges} from "./EditTicketForm";import type {Ticket,Changes} from "./EditTicketForm";
const INITIAL:Ticket[]=[{id:0,title:"Timeout API",priority:"HIGH",description:"Logi",status:"OPEN",createdAt:"2026-10-28T10:00:00Z"},{id:43,title:"Błąd logowania",priority:"MEDIUM",description:"Sesja",status:"OPEN",createdAt:"2026-10-28T10:01:00Z"}];
export default function App(){const [tickets,setTickets]=useState(INITIAL);const [selectedId,setSelectedId]=useState<number|null>(0);const [editing,setEditing]=useState(false);const selected=tickets.find(ticket=>ticket.id===selectedId)??null;function save(id:number,changes:Changes){setTickets(previous=>applyChanges(previous,id,changes));setEditing(false);}return <main><h1>Edycja ticketów</h1><nav aria-label="Wybór ticketu">{tickets.map(ticket=><button type="button" key={ticket.id} aria-pressed={ticket.id===selectedId} onClick={()=>setSelectedId(ticket.id)}>{ticket.title}</button>)}</nav>{editing?<EditorPanel ticket={selected} onSave={save} onCancel={()=>setEditing(false)}/>:selected&&<section><h2>{selected.title}</h2><p>{selected.description}</p><button type="button" onClick={()=>setEditing(true)}>Edytuj</button></section>}</main>;}`;
const exercises=[
['Wyprowadź Changes przez Pick<Ticket,"title"|"priority">.','ID i data nie są edytowalne.',model],
['Zbuduj toDraft, które tworzy osobną kopię pól edytowalnych.','Nie przechowuj całego rekordu w formularzu.',model],
['Zainicjalizuj draft leniwą funkcją używającą ticket.','To inicjalizacja przy montowaniu, nie synchronizacja.',form],
['Dodaj key oparty na ID do formularza w rodzicu.','Key musi być na granicy komponentu przechowującego draft.',standalonePanel],
['Obsłuż ticket=null komunikatem wyboru.','Nie odczytuj ticket.id przed zawężeniem.',standalonePanel],
['Nie gub ticketu o ID=0.','Sprawdzaj null, a nie truthiness ID.',standalonePanel],
['Zapisuj tytuł w nowym obiekcie draft przez updater.','Skopiuj wartość zdarzenia przed updaterem.',form],
['Sprawdź priorytet ze stringa select guardem.','Rzutowanie nie sprawdza wartości runtime.',form],
['Wylicz niezapisane zmiany przez porównanie pól.','Dirty nie wymaga osobnego setDirty.',model],
['Pokaż informację o niezapisanych zmianach.','Wybrano porównanie surowego tytułu, więc same spacje też są zmianą.',form],
['Zablokuj zapis tytułu krótszego niż 5 po trim.','Submitted ujawnia dostępny komunikat.',form],
['Przekaż ID osobno od dozwolonych Changes.','Rodzic wie, który rekord aktualizować.',form],
['Zachowaj pełny opis, status i datę przy zapisie.','applyChanges zmienia jawnie tylko title oraz priority.',model],
['Zaktualizuj tylko wskazany rekord przez map.','Nieedytowane rekordy zachowują referencję.',model],
['Przy nieistniejącym ID nie zmieniaj rekordów.','Map tworzy nową tablicę, ale wszystkie elementy zostają te same.',model],
['Zachowaj listę i rekordy wejściowe bez mutacji.','Testuj Object.freeze na wejściu.',model],
['Dodaj Anuluj, które jedynie wywołuje onCancel.','Nie zapisuj draft w obsłudze anulowania.',form],
['Dodaj jawne Przywróć dane bez zmiany tożsamości komponentu.','Ustaw draft z aktualnego ticket i wyzeruj submitted.',form],
['Przywróć błędy do stanu początkowego przy resecie.','Samo setDraft nie czyści submitted.',form],
['Zademonstruj, że nowy obiekt ticket z tym samym ID nie wymusza resetu przez key.','Key zależy od ID, nie od referencji obiektu.',standalonePanel],
['Wyjaśnij różnicę między useId a identyfikatorem rekordu.','UseId wiąże elementy formularza.','useId daje identyfikator powiązań dostępności instancji. ID ticketu jest trwałą tożsamością danych i może służyć jako key. useId nie służy do generowania kluczy rekordów listy.'],
['Pokaż ograniczenie key jako props komponentu.','React obsługuje key oddzielnie.','Key nie trafia do props jako zwykłe pole. Formularz otrzymuje ticket.id przez ticket lub jawny prop, niezależnie od key. Nie odczytuj props.key, aby ustalić ID zapisu.'],
['Wybierz politykę pozostania w edycji po przełączeniu rekordu.','EditorPanel używa key i inicjalizuje nowy draft.',standalonePanel],
['Opisz alternatywę: zakończ edycję w handlerze wyboru rekordu.','To decyzja produktu.','Handler wyboru może wywołać setSelectedId(id) oraz setEditing(false). Nowy rekord pojawi się wtedy jako karta szczegółów, a edytor zostanie odmontowany. Jeśli trzeba chronić niezapisane zmiany, dodaj jawny proces potwierdzenia opuszczenia edycji.'],
['Usuń efekt kopiujący ticket do draft po każdej zmianie referencji.','Taki efekt potrafi skasować lokalne wpisy.',form],
['Wyjaśnij, dlaczego losowy key niszczy wpisywanie.','Każda zmiana key montuje nową instancję.','Key= Math.random() albo zmienny czas powodują odmontowanie i utratę draft, fokusu oraz lokalnych flag przy kolejnych renderach rodzica. Stabilne ID identyfikuje obiekt, nie render.'],
['Sprawdź typ zmian z niedozwolonym polem status.','Użyj świeżego literału i oczekiwanego błędu.',model+'\n// @ts-expect-error: status nie jest edytowalnym polem\nconst changes:Changes={title:"Gotowy",priority:"LOW",status:"DONE"};'],
['Nie nadpisuj technicznych pól nawet przy nadmiarowym obiekcie runtime.','Typ strukturalny nie usuwa nadmiarowych danych.',model],
['Rozróżnij zmianę rekordu i nową wersję tego samego rekordu.','Key=id nie rozwiązuje konfliktów synchronizacji.','Nowe ID oznacza nowy obiekt i reset draft. Nowa wersja tego samego ID wymaga polityki konfliktów: zachowaj lokalne zmiany, pokaż informację o zewnętrznej aktualizacji albo jawnie przeładuj draft. Sam key=id nie wykrywa ani nie rozwiązuje konfliktu.'],
['Przetestuj edycję #0, przejście na #43, zapis i anulowanie.','Sprawdź rzeczywisty reset i fokus w przeglądarce.',standalonePanel],
];
const concepts=[
['Dlaczego nowe props nie przepisują useState(ticket.title)?','Inicjalizator stanu służy montowaniu','UseState zawsze ignoruje props','TypeScript wyłącza aktualizacje','Formularz ma zły label'],
['Co powoduje zmiana key edytora?','Odmontowanie poprzedniej i montaż nowej instancji','Tylko zmianę CSS','Scalenie obu draftów','Automatyczny zapis'],
['Jaki key wybrać dla różnych ticketów?','Stabilne ticket.id','Math.random()','Date.now() przy każdym renderze','Bieżący tekst inputa'],
['Czy key jest zwykłym propsem?','Nie, React obsługuje go oddzielnie','Tak, zawsze props.key','Tak, tylko w TSX','Tak, jeśli jest liczbą'],
['Czy ten sam key globalnie identyfikuje komponent na całej stronie?','Nie, tożsamość zależy też od rodzica i pozycji','Tak, wszędzie','Tylko dla liczb','Tylko w production'],
['Gdzie dodać key, aby zresetować stan EditTicketForm?','Na EditTicketForm w rodzicu','Tylko na input wewnątrz','Na etykiecie','Na opcji select'],
['Co zrobi nowa referencja ticket z tym samym id i key?','Sama nie wymusi remount','Zawsze usunie draft','Automatycznie zapisze','Zmieni ID'],
['Jak zachowa się key zależny od tytułu?','Edycja tożsamości może resetować formularz','Gwarantuje zachowanie fokusu','Waliduje tytuł','Zapewnia unikalność globalną'],
['Czy TypeScript wykryje stary draft po zmianie props?','Nie, to problem tożsamości i działania UI','Tak, zawsze','Tylko przy strictNullChecks','Tylko w JSX'],
['Dlaczego efekt zależny od ticket bywa problemem?','Nowa referencja może skasować niezapisany draft','Efekt nie może zmieniać stanu','Zawsze zawiesza stronę','Wyłącza props'],
['Co przechowuje lokalny draft?','Edytowalne dane przed zapisem','Nowy ID z serwera','Wyłącznie błędy','Wszystkie inne tickety'],
['Jak porównać dirty w tym przykładzie?','Porównać surowy title i priority','Zawsze true','Tylko długość listy','Tylko ID'],
['Czy sam trim przy zapisie zmienia wejściowy draft?','Nie, trim zwraca nowy string','Tak, mutuje string','Tylko w StrictMode','Tylko dla pustego tekstu'],
['Co robi Anuluj?','Wychodzi bez onSave','Zapisuje draft','Usuwa rekord','Zmienia datę'],
['Co musi zachować applyChanges?','Nieedytowalne pola rekordu','Tylko ID','Tylko nowy tytuł','Wyłącznie key'],
['Dlaczego jawnie przypisać title i priority zamiast ...changes?','Nadmiarowe dane runtime nie nadpiszą pól technicznych','Spread nie działa w TS','Przyspiesza sieć','Włącza walidację HTML'],
['Czy Pick usuwa dodatkowe klucze runtime?','Nie','Tak, zawsze','Tylko w dev','Tylko dla readonly'],
['Czy key=id przechowuje porzucony draft po powrocie do ID?','Nie, odmontowany stan nie jest automatycznym cache','Tak, wszystkie stare stany','Tylko dla ID=0','Tylko po blur'],
['Co zrobić, aby zachować osobne drafty dla wielu rekordów?','Jawnie przechowywać je np. w rodzicu według ID','Użyć losowego key','Zmienić label','Usunąć walidację'],
['Czy display:none odmontowuje komponent?','Nie','Tak','Tylko formularz','Tylko na telefonie'],
['Co robi warunkowe usunięcie komponentu z drzewa?','Usuwa jego lokalny stan','Przechowuje go wiecznie','Zapisuje do localStorage','Wywołuje onSave'],
['Jak traktować ID=0?','Jako poprawne ID, jeśli model je dopuszcza','Zawsze jako brak rekordu','Jako string pusty','Jako NaN'],
['Jak sprawdzić brak selectedId?','selectedId===null','!selectedId','selectedId<1','Boolean(selectedId)'],
['Po co guard isPriority?','Sprawdza string runtime przed użyciem jako Priority','Nadaje key','Zamyka formularz','Zapisuje do API'],
['Co robi reset draft w tej samej instancji?','Ustawia aktualne dane ticket i czyści flagi','Wymaga losowego key','Zmienia rekord w bazie','Zmienia typ komponentu'],
['Czy dwa warunki renderujące ten sam typ na tej samej pozycji zawsze resetują stan?','Nie, stan może być zachowany','Tak','Tylko w TSX','Tylko z useId'],
['Co może zrobić zmiana typu rodzica poddrzewa?','Spowodować utratę stanu poddrzewa','Zawsze zachowa całość','Zapisze draft','Zamieni props w stan'],
['Czy funkcja komponentu deklarowana wewnątrz rodzica jest dobrym sposobem zachowania stanu?','Nie, nowa tożsamość funkcji może powodować reset','Tak, gwarantuje ten sam typ','Tak, zastępuje key','Tak, usuwa potrzebę useState'],
['Czy trzeba aktualizować kopię selected po zapisie?','Nie, selected można wyliczyć z listy i ID','Tak, trzeci stan obowiązkowy','Tylko po blur','Tylko w production'],
['Jak potwierdzić rzeczywistą rekoncyliację key?','Testem renderera lub przeglądarki z kolejnymi renderami','Samą kontrolą typów','Samym JSON.stringify(props)','Liczeniem importów'],
];
const quiz=concepts.map(row=>[...row,'Stan należy do instancji w drzewie. Stabilny key wyznacza tożsamość w obrębie rodzica, a draft oraz dane zapisane mają odrębne role.']);
quiz.push(...require('./practice_form_04.cjs').quiz.slice(0,30).map(row=>['Powtórka walidacji edytora: '+row[0],...row.slice(1)]));
const cases=[];function add(label,expr,a,b,c,d){cases.push([label,expr,String(a),String(b),String(c),String(d)]);}
for(const priority of ['LOW','MEDIUM','HIGH','CRITICAL','high','','URGENT'])add('Guard '+JSON.stringify(priority),`isPriority(${JSON.stringify(priority)})`,['LOW','MEDIUM','HIGH','CRITICAL'].includes(priority),priority==='LOW'?'LOW':'null','undefined',priority==='LOW'?false:(!['LOW','MEDIUM','HIGH','CRITICAL'].includes(priority)));
// Wybory dla guardów mają być różne także dla wartości logicznych.
for(const row of cases){row[3]=row[2]==='true'?'false':'true';row[4]='null';row[5]='undefined';}
for(const [title,priority]of [['Timeout API','HIGH'],['Zmieniony','HIGH'],['Timeout API','LOW'],[' Timeout API ','HIGH'],['Zmieniony','CRITICAL']])add('Dirty '+JSON.stringify([title,priority]),`isDirty({title:${JSON.stringify(title)},priority:${JSON.stringify(priority)}},ticket)`,title!=='Timeout API'||priority!=='HIGH',title==='Timeout API'&&priority==='HIGH','null','undefined');
for(const id of [0,43,999])for(const index of [0,1])add('Zmiana id='+id+' referencja indeksu '+index,`applyChanges(list,${id},changes)[${index}]===list[${index}]`,listId(index)!==id,listId(index)===id,'null','undefined');
function listId(index){return index===0?0:43;}
for(const [label,expr,a,b,c,d]of [
['Nowy tytuł','updated[0].title','Poprawiony','Timeout API','null','undefined'],['Nowy priorytet','updated[0].priority','LOW','HIGH','MEDIUM','undefined'],['Stary tytuł','ticket.title','Timeout API','Poprawiony','null','undefined'],['Stary priorytet','ticket.priority','HIGH','LOW','MEDIUM','undefined'],['Status zachowany','updated[0].status','OPEN','DONE','null','undefined'],['ID zachowane','updated[0].id',0,1,43,'null'],['Opis zachowany','updated[0].description','Logi','null','undefined','Poprawiony'],['Długość listy','updated.length',2,1,0,3],['Nowa tablica','updated===list',false,true,'null','undefined'],['Kopia draft','toDraft(ticket)===ticket',false,true,'null','undefined'],['Klucze draft','Object.keys(toDraft(ticket)).sort().join(",")','priority,title','id,priority,title','title','id'],['Brak ID w draft','"id" in toDraft(ticket)',false,true,'null','undefined'],['ID z nadmiarowej zmiany','applyChanges(list,0,{...changes,...{id:999}})[0].id',0,999,43,'null'],['Status z nadmiarowej zmiany','applyChanges(list,0,{...changes,...{status:"DONE"}})[0].status','OPEN','DONE','null','undefined'],['Tytuł innego rekordu','updated[1].title','Logowanie','Poprawiony','Timeout API','undefined'],['Brak id nie zmienia elementu','applyChanges(list,999,changes)[0]===ticket',true,false,'null','undefined'],['Title trim długość','updated[0].title.length',10,14,8,0],['Date zachowana','updated[0].createdAt===ticket.createdAt',true,false,'null','undefined'],['Nowy draft nie zmienia ticket','(()=>{const draft=toDraft(ticket);draft.title="Lokalny";return ticket.title;})()','Timeout API','Lokalny','null','undefined'],['Równe kopie draft','isDirty({...toDraft(ticket)},ticket)',false,true,'null','undefined'],['Zmiana nieistniejącego rekordu długość','applyChanges(list,999,changes).length',2,0,1,3],['Pierwszy rekord ID=0 istnieje','list.find(item=>item.id===0)?.title','Timeout API','undefined','null','Logowanie'],
])add(label,expr,a,b,c,d);
if(cases.length!==40)throw Error('Liczba przypadków '+cases.length);
const base=model+'\nconst ticket:Ticket={id:0,title:"Timeout API",priority:"HIGH",description:"Logi",status:"OPEN",createdAt:"2026-10-28T10:00:00Z"};const list:readonly Ticket[]=[ticket,{...ticket,id:43,title:"Logowanie"}];const changes:Changes={title:"  Poprawiony  ",priority:"LOW"};const updated=applyChanges(list,0,changes);';
for(const [label,expr,a,b,c,d]of cases)quiz.push([label+': co wypisze logika edycji?',a,b,c,d,'Porównaj konkretne pola oraz referencje. ApplyChanges tworzy nową tablicę i zmienia jedynie pasujący rekord, zachowując jego pola techniczne.',base+'\nconsole.log('+expr+');']);
const project={'src/EditTicketForm.tsx':form,'src/EditorPanel.tsx':panel,'src/App.tsx':app,'src/main.tsx':'import {createRoot} from "react-dom/client";import App from "./App";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<App/>);'};
module.exports={model,form,panel:standalonePanel,exercises,quiz,project};
