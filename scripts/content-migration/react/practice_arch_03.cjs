const model=`export type Ticket={id:number;title:string;status:"OPEN"|"CLOSED";assignee:string|null};export type Board={tickets:readonly Ticket[];selectedIds:ReadonlySet<number>};
export function toggleId(selected:ReadonlySet<number>,id:number):Set<number>{const next=new Set(selected);if(next.has(id))next.delete(id);else next.add(id);return next;}
export function setIds(selected:ReadonlySet<number>,ids:readonly number[],checked:boolean):Set<number>{const next=new Set(selected);for(const id of ids){if(checked)next.add(id);else next.delete(id);}return next;}
export function selectionInfo(all:readonly Ticket[],visible:readonly Ticket[],selected:ReadonlySet<number>){const selectedTickets=all.filter(ticket=>selected.has(ticket.id));const selectedVisible=visible.filter(ticket=>selected.has(ticket.id));return {count:selectedTickets.length,visibleCount:selectedVisible.length,allVisible:visible.length>0&&selectedVisible.length===visible.length,someVisible:selectedVisible.length>0&&selectedVisible.length<visible.length,selectedTickets};}
export function closeSelected(board:Board):Board{return {...board,tickets:board.tickets.map(ticket=>board.selectedIds.has(ticket.id)?{...ticket,status:"CLOSED"}:ticket),selectedIds:new Set<number>()};}
export function assignSelected(board:Board,login:string):Board{return {...board,tickets:board.tickets.map(ticket=>board.selectedIds.has(ticket.id)?{...ticket,assignee:login}:ticket),selectedIds:new Set<number>()};}
export function deleteTicket(board:Board,id:number):Board{const selectedIds=new Set(board.selectedIds);selectedIds.delete(id);return {...board,tickets:board.tickets.filter(ticket=>ticket.id!==id),selectedIds};}`;
const selectAll=`export function SelectAll({all,some,onChange}:{all:boolean;some:boolean;onChange:(checked:boolean)=>void}){return <label><input type="checkbox" checked={all} ref={element=>{if(element)element.indeterminate=some;}} onChange={event=>onChange(event.currentTarget.checked)}/>Zaznacz wszystkie widoczne</label>;}`;
const page=`import {useState} from "react";
${model}
${selectAll}
export function SelectionPage({initialTickets,login}:{initialTickets:readonly Ticket[];login:string}){const [board,setBoard]=useState<Board>(()=>({tickets:[...initialTickets],selectedIds:new Set<number>()}));const [hideClosed,setHideClosed]=useState(false);const visible=board.tickets.filter(ticket=>!hideClosed||ticket.status!=="CLOSED");const info=selectionInfo(board.tickets,visible,board.selectedIds);return <main><h1>Zaznaczanie ticketów</h1><label><input type="checkbox" checked={hideClosed} onChange={event=>setHideClosed(event.currentTarget.checked)}/>Ukryj zamknięte</label><p>Zaznaczenie pozostaje między filtrami. Akcje dotyczą wszystkich zaznaczonych istniejących ticketów.</p><SelectAll all={info.allVisible} some={info.someVisible} onChange={checked=>setBoard(previous=>({...previous,selectedIds:setIds(previous.selectedIds,visible.map(ticket=>ticket.id),checked)}))}/>{info.count>0&&<section aria-label="Akcje zbiorcze"><p>Zaznaczono: {info.count}; widoczne zaznaczone: {info.visibleCount}</p><button type="button" onClick={()=>setBoard(closeSelected)}>Zamknij zaznaczone</button><button type="button" onClick={()=>setBoard(previous=>assignSelected(previous,login))}>Przypisz do mnie</button><button type="button" onClick={()=>setBoard(previous=>({...previous,selectedIds:new Set<number>()}))}>Wyczyść zaznaczenie</button></section>}<ul>{visible.map(ticket=><li key={ticket.id}><label><input type="checkbox" checked={board.selectedIds.has(ticket.id)} onChange={()=>setBoard(previous=>({...previous,selectedIds:toggleId(previous.selectedIds,ticket.id)}))}/>{ticket.title}</label><span>{ticket.status} — {ticket.assignee??"Nieprzypisany"}</span><button type="button" onClick={()=>setBoard(previous=>deleteTicket(previous,ticket.id))}>Usuń {ticket.title}</button></li>)}</ul>{visible.length===0&&<p>Brak widocznych ticketów.</p>}</main>;}`;
const hook=`import {useState} from "react";
${model}
export function useSelection(){const [selectedIds,setSelectedIds]=useState<ReadonlySet<number>>(()=>new Set<number>());return {selectedIds,toggle:(id:number)=>setSelectedIds(previous=>toggleId(previous,id)),setAll:(ids:readonly number[],checked:boolean)=>setSelectedIds(previous=>setIds(previous,ids,checked)),clear:()=>setSelectedIds(new Set<number>())};}`;
const exercises=[
['Zapisz zaznaczenie jako zbiór ID zamiast kopii rekordów.','Dane rekordów pochodzą z głównej listy.',model],
['Napisz toggleId tworzące nowy Set.','Nie mutuj zbioru przechowywanego w stanie.',model],
['Napisz setIds dodające lub usuwające wiele ID.','Zachowaj zaznaczenie spoza przekazanego zakresu.',model],
['Obsłuż duplikaty w ids bez podwójnego zaznaczenia.','Set zapewnia unikalność wartości.',model],
['Obsłuż ID=0 bez testu truthiness.','Has(0) jest poprawnym sprawdzeniem.',model],
['Wylicz selectedTickets z aktualnej pełnej listy.','Nie przechowuj kopii obiektów w zaznaczeniu.',model],
['Wylicz globalny count z istniejących zaznaczonych rekordów.','SelectedIds.size może obejmować osierocone ID.',model],
['Oddziel visibleCount od globalnego count.','Zaznaczenie może obejmować ukryte rekordy.',model],
['Wylicz allVisible z warunkiem niepustej listy.','Every na pustej liście samo dałoby true.',model],
['Wylicz someVisible tylko dla częściowego zaznaczenia.','Nie jest true, gdy zaznaczono wszystkie.',model],
['Zbuduj SelectAll z callbackiem boolean.','Checked pochodzi z wyliczonego all.',selectAll],
['Ustaw DOM indeterminate przez ref callback.','To właściwość DOM, nie atrybut HTML.',selectAll],
['Nie zwracaj przypadkiem boolean z callbacku ref.','Użyj bloku instrukcji, nie samego przypisania.',selectAll],
['Zaznaczaj tylko widoczny zakres po kliknięciu SelectAll.','Nie usuwaj ukrytych zaznaczeń.',page],
['Opisz politykę zaznaczenia między filtrami.','Użytkownik musi rozumieć zakres akcji.',page],
['Zamknij wszystkie istniejące zaznaczone tickety przez map.','Nieedytowane rekordy zachowaj.',model],
['Po zamknięciu wyczyść zaznaczenie atomowo z listą.','Board zawiera powiązane źródła stanu.',model],
['Przypisz zaznaczone do loginu i wyczyść wybór.','Ukryte zaznaczenia też należą do akcji według opisanej polityki.',model],
['Usuń rekord i jego ID w jednym handlerze.','Nie potrzeba efektu sprzątającego.',model],
['Przy usunięciu nieistniejącego ID nie zmieniaj innych rekordów.','Nowe kontenery mogą nadal zawierać te same elementy.',model],
['Zbuduj hook useSelection z funkcjonalnymi updaterami.','Dwa toggle przed renderem mają zachować oba wyniki.',hook],
['Czyść zaznaczenie przez nowy pusty Set.','Clear na starym Set mutowałoby stan.',hook],
['Nie przechowuj selectedCount jako osobnego useState.','Licznik wynika ze źródeł.',page],
['Nie dodawaj isClosed obok status.','Status wystarcza do wyliczenia flagi.','Jeśli isClosed zawsze znaczy status===CLOSED, wyliczaj je. ClosedAt może być odrębną informacją, bo status nie zawiera daty zamknięcia. Zasada jednego źródła nie każe usuwać niezależnych danych.'],
['Rozróżnij ReadonlySet od zamrożenia runtime.','Readonly to kontrakt kompilatora.','ReadonlySet ukrywa mutujące metody w typie. Nie blokuje mutacji przez inny alias runtime. Object.freeze(new Set()) też nie zamraża jego wewnętrznych wpisów; bezpieczeństwo aktualizacji wynika z kopiowania i nieużywania mutujących aliasów.'],
['Sprawdź typ readonly zbioru.','Has jest dostępne, add nie.',model+'\nconst selected:ReadonlySet<number>=new Set<number>();selected.has(0);\n// @ts-expect-error: mutacja nie jest częścią ReadonlySet\nselected.add(1);'],
['Sprawdź zachowanie licznika po zewnętrznym usunięciu rekordu.','Derivacja z all ignoruje osierocone ID.',model],
['Nie przenoś formularza akcji zbiorczej do każdego wiersza.','Wspólny wybór należy do wspólnego rodzica.',page],
['Zachowaj aktualne obiekty po zmianie tytułu zaznaczonego ticketu.','SelectionInfo filtruje najnowsze tickets.',model],
['Przetestuj zaznaczenie, zmianę filtra, zamknięcie i usunięcie.','Sprawdź indeterminate w prawdziwym DOM.',page],
];
const concepts=[
['Co przechowujemy w zaznaczeniu?','ID rekordów','Kopie całych ticketów','Osobny count dla każdego kliknięcia','Tekst label'],
['Dlaczego Set?','Zapewnia unikalne wartości ID','Zamraża rekordy','Sortuje zawsze liczby','Automatycznie renderuje DOM'],
['Jak aktualizować Set w stanie?','Utworzyć nową kopię i zmienić kopię','Mutować poprzedni i zwrócić go','Użyć Object.assign tylko na Set','Zapisać jego size osobno'],
['Z czego wyliczać aktualne selectedTickets?','Z głównej listy i selectedIds','Ze starych kopii przy kliknięciu','Z label','Z count'],
['Co grozi liczeniu selectedIds.size po usunięciu danych?','Może obejmować osierocone ID','Zawsze zwróci zero','Zmutuje listę','Zmieni status'],
['Jak liczymy globalny count w przykładzie?','Istniejące zaznaczone rekordy z pełnej listy','Tylko widoczne zaznaczone','Każdy wpis historyczny','Długość query'],
['Co oznacza visibleCount?','Ile widocznych rekordów jest zaznaczonych','Cały globalny wybór zawsze','Liczbę statusów','Liczbę filtrów'],
['Czy allVisible ma być true dla pustej listy?','Nie','Tak','Tylko z pustym Set','Tylko w TSX'],
['Co oznacza someVisible?','Niepusty częściowy wybór widocznych','Zawsze wszystkie','Tylko globalny count','Ładowanie'],
['Jak ustawiać indeterminate?','Przez właściwość elementu DOM','Jako standardowy atrybut HTML','Przez value=mixed','Przez className bez semantyki'],
['Czy checked i indeterminate to to samo?','Nie','Tak','Tylko przy false','Tylko w React 19'],
['Co robi SelectAll w tym przykładzie?','Zmienia zaznaczenie widocznego zakresu','Zawsze czyści wszystko','Zawsze zaznacza całą bazę','Zmienia filtr'],
['Czy ukryty wybór jest zachowany?','Tak, zgodnie z komunikatem zakresu','Nie','Tylko z ID=0','Tylko bez Set'],
['Jakie rekordy zamknie akcja zbiorcza?','Wszystkie istniejące zaznaczone, także ukryte','Tylko pierwszy widoczny','Wszystkie bez względu na wybór','Wyłącznie CLOSED'],
['Kiedy sprzątać zaznaczenie po delete?','W tej samej transformacji zdarzenia','W timerze podczas renderu','Dopiero po odświeżeniu','Nigdy'],
['Czy ReadonlySet gwarantuje niezmienność runtime?','Nie','Tak','Tylko dla liczb','Tylko z useState'],
['Czy Object.freeze(Set) blokuje set.add?','Nie zamraża wewnętrznych wpisów','Tak zawsze','Tylko dla pustego','Tylko po renderze'],
['Jak czyścić zaznaczenie niemutowalnie?','Nowy pusty Set','SelectedIds.clear na stanie','SelectedIds.size=0','Delete selectedIds'],
['Dlaczego updater funkcyjny przy toggle?','Kolejne aktualizacje korzystają z poprzedniego wyniku','Zmienia props na mutable','Zapisuje bazę','Przyspiesza sieć'],
['Czy ID=0 można zaznaczyć?','Tak','Nie','Tylko przez string','Tylko w tabeli pustej'],
['Czy status oraz isClosed są zawsze niezależną informacją?','Nie, isClosed może wynikać ze status','Tak','Tylko w TSX','Tylko z datą'],
['Czy closedAt wynika dokładnie ze status?','Nie, data to osobna informacja','Tak','Tylko dla OPEN','Tylko po filter'],
['Po co jeden Board dla usuwania i wyboru?','Ułatwia spójną transformację powiązanych danych','Zastępuje wszystkie hooki','Wymusza context','Usuwa potrzebę callbacków'],
['Czy przechowywanie ID jest cache obiektu?','Nie, obiekt wyliczamy z aktualnej listy','Tak','Tylko dla Set','Tylko po zamknięciu'],
['Co stanie się z tytułem selectedTickets po zmianie głównej listy?','Derivacja pokaże aktualny tytuł','Zawsze pozostanie stary','Wymaga osobnego setSelectedTickets','Zamieni się w ID'],
['Czy Set jest automatycznie głęboko skopiowany przez new Set?','Nie, kopiuje wpisy; ID liczbowe są wartościami','Tak, wszystkie obiekty','Tylko w React','Tylko w strict'],
['Czy wybór między stronami wymaga decyzji produktu?','Tak, zakres trzeba jasno opisać','Nie','Tylko przy jednym rekordzie','Tylko bez paginacji'],
['Czy licznik powinien być kolejnym źródłem stanu?','Nie, wyliczamy go','Tak','Tylko po usuwaniu','Tylko z checkbox'],
['Co sprawdzić w DOM?','Stan pośredni oraz obsługę checkboxa klawiaturą','Tylko typ Set','Wyłącznie JSON','Liczbę importów'],
['Czy brak mutacji kontenerów oznacza klonowanie wszystkich rekordów?','Nie, niezmienione rekordy mogą zachować referencję','Tak','Tylko przy map','Tylko w JSX'],
];
const quiz=concepts.map(row=>[...row,'Wyliczenia odwołują się do aktualnych danych. Nowy Set i nowe kontenery chronią poprzedni stan, a zakres akcji jest jawnie określony.']);quiz.push(...require('./practice_arch_02.cjs').quiz.slice(0,30).map(row=>['Powtórka wspólnego źródła: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const ids of [[],[0],[1],[0,1],[0,1,2],[99],[0,99]])for(const visibleCount of [0,2]){const n=ids.filter(id=>[0,1,2].includes(id)).length;add('Global count '+JSON.stringify(ids)+' visible '+visibleCount,`selectionInfo(data,data.slice(0,${visibleCount}),new Set(${JSON.stringify(ids)})).count`,n);}
for(const [label,expr,a]of [['Toggle dodaje','toggleId(new Set(),0).has(0)',true],['Toggle usuwa','toggleId(new Set([0]),0).has(0)',false],['Stary Set zachowany','(()=>{const old=new Set([0]);toggleId(old,0);return old.has(0);})()',true],['Nowy Set','(()=>{const old=new Set([0]);return toggleId(old,1)===old;})()',false],['Duplikaty zakresu','setIds(new Set(),[0,0,1],true).size',2],['Ukryte zachowane','setIds(new Set([2]),[0,1],true).has(2)',true],['Ukryte po odznaczeniu','setIds(new Set([0,1,2]),[0,1],false).has(2)',true],['All pusty','selectionInfo(data,[],new Set()).allVisible',false],['Some pusty','selectionInfo(data,[],new Set([0])).someVisible',false],['All pełny','selectionInfo(data,data,new Set([0,1,2])).allVisible',true],['Some pełny','selectionInfo(data,data,new Set([0,1,2])).someVisible',false],['Some część','selectionInfo(data,data,new Set([0])).someVisible',true],['Widoczny count','selectionInfo(data,data.slice(0,1),new Set([0,2])).visibleCount',1],['Zamknięcie status','closeSelected(board).tickets[0].status','CLOSED'],['Zamknięcie czyści','closeSelected(board).selectedIds.size',0],['Inny rekord ten sam','closeSelected(board).tickets[1]===data[1]',true],['Przypisanie','assignSelected(board,"anna").tickets[0].assignee','anna'],['Przypisanie czyści','assignSelected(board,"anna").selectedIds.size',0],['Delete długość','deleteTicket(board,0).tickets.length',2],['Delete usuwa ID','deleteTicket(board,0).selectedIds.has(0)',false],['Stare ID po delete','(()=>{deleteTicket(board,0);return board.selectedIds.has(0);})()',true],['Osierocone ignorowane','selectionInfo([],[],new Set([99])).count',0],['Nie zmienia wejścia','data[0].status','OPEN'],['Brak ID delete','deleteTicket(board,99).tickets.length',3],['Zbiór zakres pusty','setIds(new Set([0]),[],false).size',1],['Nowe rekordy do wyboru','selectionInfo([{...data[0],title:"Nowy"}],[],new Set([0])).selectedTickets[0].title','Nowy']])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);const data='const data:readonly Ticket[]=[{id:0,title:"API",status:"OPEN",assignee:null},{id:1,title:"Sesja",status:"OPEN",assignee:"piotr"},{id:2,title:"Raport",status:"CLOSED",assignee:null}];const board:Board={tickets:data,selectedIds:new Set([0])};';for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik zaznaczenia?',a,...wrong,'Globalny count uwzględnia istniejące rekordy, zakres visible służy checkboxowi zbiorczemu. Transformacje nie mutują źródła.',model+'\n'+data+'console.log('+expr+');']);}
const project={'src/Selection.tsx':page,'src/main.tsx':'import {createRoot} from "react-dom/client";import {SelectionPage} from "./Selection";import type {Ticket,Board} from "./Selection";'+data+'const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<SelectionPage initialTickets={data} login="anna"/>);'};
module.exports={model,selectAll,page,hook,exercises,quiz,project};
