const model=`export const STATUSES=["OPEN","IN_PROGRESS","CLOSED"] as const;export type Status=(typeof STATUSES)[number];export type StatusFilter=Status|"ALL";export type View="table"|"cards";export type Ticket={id:number;title:string;status:Status};
export function isStatusFilter(value:string):value is StatusFilter{return value==="ALL"||STATUSES.some(status=>status===value);}
export function filterTickets(tickets:readonly Ticket[],status:StatusFilter):Ticket[]{return tickets.filter(ticket=>status==="ALL"||ticket.status===status);}
export function statusCounts(tickets:readonly Ticket[]):Record<StatusFilter,number>{return {ALL:tickets.length,OPEN:tickets.filter(ticket=>ticket.status==="OPEN").length,IN_PROGRESS:tickets.filter(ticket=>ticket.status==="IN_PROGRESS").length,CLOSED:tickets.filter(ticket=>ticket.status==="CLOSED").length};}`;
const controls=`${model}
export function Toolbar({view,onViewChange}:{view:View;onViewChange:(view:View)=>void}){return <div role="group" aria-label="Widok">{(["table","cards"] as const).map(option=><button type="button" key={option} aria-pressed={view===option} onClick={()=>onViewChange(option)}>{option==="table"?"Tabela":"Karty"}</button>)}</div>;}
export function StatusFilterButtons({value,counts,onChange}:{value:StatusFilter;counts:Record<StatusFilter,number>;onChange:(value:StatusFilter)=>void}){return <div role="group" aria-label="Filtr statusu">{(["ALL",...STATUSES] as const).map(status=><button type="button" key={status} aria-pressed={value===status} onClick={()=>onChange(status)}>{status} ({counts[status]})</button>)}</div>;}
export function StatusSelect({value,onChange}:{value:StatusFilter;onChange:(value:StatusFilter)=>void}){return <label>Status<select value={value} onChange={event=>{const next=event.currentTarget.value;if(isStatusFilter(next))onChange(next);}}>{["ALL",...STATUSES].map(status=><option key={status}>{status}</option>)}</select></label>;}
export function TicketTable({tickets}:{tickets:readonly Ticket[]}){return <table><caption>Tickety</caption><thead><tr><th scope="col">ID</th><th scope="col">Tytuł</th><th scope="col">Status</th></tr></thead><tbody>{tickets.map(ticket=><tr key={ticket.id}><td>{ticket.id}</td><td>{ticket.title}</td><td>{ticket.status}</td></tr>)}</tbody></table>;}
export function TicketCards({tickets}:{tickets:readonly Ticket[]}){return <ul>{tickets.map(ticket=><li key={ticket.id}><article><h3>#{ticket.id} {ticket.title}</h3><p>{ticket.status}</p></article></li>)}</ul>;}`;
const page=`import {useState} from "react";
${controls}
export function TicketPage({tickets}:{tickets:readonly Ticket[]}){const [view,setView]=useState<View>("table");const [status,setStatus]=useState<StatusFilter>("ALL");const visible=filterTickets(tickets,status);const counts=statusCounts(tickets);return <main><h1>Tickety</h1><p>Widoczne: {visible.length}</p><Toolbar view={view} onViewChange={setView}/><StatusFilterButtons value={status} counts={counts} onChange={setStatus}/><StatusSelect value={status} onChange={setStatus}/>{visible.length===0?<p>Brak wyników.</p>:view==="table"?<TicketTable tickets={visible}/>:<TicketCards tickets={visible}/>}<button type="button" onClick={()=>{setStatus("ALL");setView("table");}}>Resetuj ekran</button></main>;}`;
const exercises=[
['Zdefiniuj View, Status i StatusFilter.','Unia jawnie opisuje dozwolone decyzje.',model],
['Przenieś view z Toolbar do TicketPage.','Toolbar ma wyłącznie props i callback.',page],
['Przenieś status z listy do wspólnego rodzica.','Licznik i oba widoki korzystają z tej samej wartości.',page],
['Zbuduj Toolbar kontrolowany przez view i onViewChange.','Bez useState(view) wewnątrz.',controls],
['Zbuduj przyciski statusu z value i onChange.','Nie trzymaj lokalnej kopii filtra.',controls],
['Zsynchronizuj select i przyciski przez jedno status w rodzicu.','Nie dodawaj efektu kopiującego między kontrolkami.',page],
['Zawęź value z select przez isStatusFilter.','Event dostarcza string, nie gotową unię.',controls],
['Wylicz visible w TicketPage.','Nie filtruj drugi raz tylko w jednym dziecku.',page],
['Wylicz count nagłówka z visible.','Nagłówek musi pokazywać tę samą listę.',page],
['Wylicz liczniki wszystkich statusów z pełnej listy.','Nie z już zawężonego visible.',model],
['Zachowaj filtr po przełączeniu tabela/karty.','Zmiana widoku nie dotyka status.',page],
['Zachowaj widok po zmianie statusu.','Stan ma dwie niezależne decyzje.',page],
['Zbuduj semantyczną tabelę z caption i scope=col.','Prezentacja nie potrzebuje hooków filtrów.',controls],
['Zbuduj karty otrzymujące tę samą listę co tabela.','TicketCards nie zna wartości status.',controls],
['Pokaż pusty wynik niezależnie od wybranego widoku.','Pusty wynik nie jest automatycznie loading.',page],
['Dodaj reset obu decyzji w jednym handlerze.','Nie resetuj ich przy każdej zmianie tickets.',page],
['Oznacz aktywne przyciski przez aria-pressed.','To grupa filtrów, nie niekompletny wzorzec tablist.',controls],
['Wyjaśnij różnicę między filtrem przyciskowym a pełnymi zakładkami ARIA.','Tablist wymaga klawiatury oraz paneli.','Przyciski filtrujące listę mogą używać group i aria-pressed. Pełny tablist wymaga zgodnej semantyki tab/tabpanel, powiązań oraz zarządzania klawiaturą i fokusem. Samo dodanie role=tab nie implementuje tego wzorca.'],
['Nadaj przyciskom type=button.','Komponent może zostać użyty w formularzu.',controls],
['Nie mutuj tickets w filtrze ani licznikach.','Props są wejściem tylko do odczytu.',model],
['Obsłuż nieposortowaną listę i ID=0.','Filter zachowuje kolejność wejścia.',model],
['Zmień props tickets i zachowaj aktualne filtry.','Derived data policzą nowy wynik.',page],
['Sprawdź typ brakującego callbacku Toolbar.','Zmiana kontraktu pomaga refaktoryzować wszystkie użycia.',controls+'\nexport function MissingCallback(){\n// @ts-expect-error: brak onViewChange\nreturn <Toolbar view="table"/>;}'],
['Zablokuj nieznany status na granicy DOM.','Nie używaj as StatusFilter zamiast sprawdzenia.',controls],
['Przekazuj zmianę jako wartość domenową, nie całe zdarzenie DOM.','Rodzic nie musi znać HTMLSelectElement.',controls],
['Wyjaśnij nazwę defaultStatus w wariancie niekontrolowanym.','Wartość początkowa i bieżąca wartość to różne kontrakty.','DefaultStatus jest inicjalizatorem lokalnego stanu. Status w komponencie kontrolowanym opisuje bieżącą decyzję właściciela. Późniejsza zmiana defaultStatus nie musi synchronizować lokalnego stanu; nazwa powinna to komunikować.'],
['Nie przenoś do Page lokalnych detali niewspółdzielonych.','Najbliższy wspólny rodzic, nie zawsze korzeń aplikacji.','Jeśli stan podpowiedzi należy tylko do Toolbar, zostaw go w Toolbar. Podnoś tylko dane wymagające współpracy. Nie każdy useState w dziecku oznacza błąd architektury.'],
['Sprawdź równość ALL z sumą liczników statusów.','Każdy Ticket ma dokładnie jeden status.',model+'\nexport function countsConsistent(tickets:readonly Ticket[]){const counts=statusCounts(tickets);return counts.ALL===counts.OPEN+counts.IN_PROGRESS+counts.CLOSED;}'],
['Opisz refaktoryzację usunięcia kopii props ze stanu.','Usuń drugi setter i efekt synchronizacji.','Wybierz właściciela w rodzicu, usuń useState(status) w dziecku, dodaj status oraz onStatusChange do props, korzystaj bezpośrednio z props i skieruj kontrolkę do callbacku. Następnie wylicz nagłówek i oba widoki z tej samej listy.'],
['Przetestuj zmianę statusu w obu kontrolkach i obu widokach.','Sprawdź też reset oraz nową listę props.',page],
];
const concepts=[
['Czym jest lifting state up?','Przeniesieniem współdzielonego stanu do wspólnego rodzica','Kopiowaniem stanu do każdego dziecka','Przenoszeniem wszystkiego na serwer','Dodaniem useEffect'],
['Kto jest właścicielem view w przykładzie?','TicketPage','TicketTable i TicketCards osobno','Każdy przycisk','DOM bez React'],
['Co otrzymuje kontrolowany Toolbar?','View i onViewChange','Tylko defaultView','Własny globalny store','Kopię całej aplikacji'],
['Dlaczego nie useState(status) w kontrolowanym dziecku?','Powstaje druga niesynchronizowana wartość','Hook jest zabroniony w dzieciach','Status nie może być stringiem','UseState mutuje props'],
['Jak dziecko prosi o zmianę?','Callbackiem z nową wartością','Mutuje props','Wywołuje setter przez import bez kontraktu','Zmienia key'],
['Z czego wyliczamy widoczną listę?','Tickets i wspólnego status','Tylko z view','Z osobnej kopii w tabeli','Z count nagłówka'],
['Z czego liczymy statusCounts?','Z pełnej listy wejściowej','Z listy po wybraniu jednego statusu','Z liczby przycisków','Z DOM'],
['Czy zmiana cards musi resetować status?','Nie','Tak','Tylko dla CLOSED','Tylko w TSX'],
['Czy zmiana statusu musi zmienić view?','Nie','Tak','Tylko dla ALL','Tylko w JSX'],
['Czy count jest osobnym stanem?','Nie, to visible.length','Tak zawsze','Tylko dla kart','Tylko po resecie'],
['Gdzie najniżej przenieść wspólny stan?','Do najbliższego wspólnego rodzica odbiorców','Zawsze do window','Zawsze do serwera','Zawsze do komponentu root'],
['Czy lokalny stan dziecka jest zawsze błędem?','Nie, może dotyczyć jego własnego UI','Tak','Tylko w TypeScript','Tylko z callbackiem'],
['Co wnosi kontrakt props podczas refaktoryzacji?','Kompilator wskaże brakujące wymagane pola','Automatyczny zapis bazy','Brak potrzeby testowania UI','Zawsze mniej renderów'],
['Czy as StatusFilter waliduje string?','Nie','Tak','Tylko przy strict','Tylko po select'],
['Po co isStatusFilter?','Zawęża string po sprawdzeniu znanych wartości','Tworzy komponent','Resetuje UI','Liczy wszystkie tickety'],
['Czy ALL jest statusem pojedynczego Ticket?','Nie, to opcja filtra','Tak','Tylko dla ID=0','Tylko w dev'],
['Co opisuje defaultStatus?','Wartość początkową wariantu lokalnego','Bieżącą wartość kontrolowaną zawsze','Zawsze wartość serwera','Nazwa dowolnego handlera'],
['Jak zapewnić spójność select i przycisków?','Jednym status w rodzicu','Dwoma lokalnymi stanami i pollingiem','Samym CSS','Losowym key'],
['Czy TicketCards musi znać status filtra?','Nie, może dostać gotowe visible','Tak zawsze','Tylko z readonly','Tylko w production'],
['Co się stanie przy nowych props tickets?','Wynik i liczniki wyliczą się ponownie','Potrzebny jest efekt setVisible','Filtry znikną obowiązkowo','UseState przejmie props automatycznie'],
['Co oznacza aria-pressed w grupie filtrów?','Aktywny stan przycisku','Gotowy wzorzec tablist','Wymuszenie requestu','Zaznaczony wiersz tabeli'],
['Czy role=tablist samo zapewnia obsługę strzałek?','Nie','Tak','Tylko z button','Tylko w React 19'],
['Dlaczego type=button w Toolbar?','Chroni przed przypadkowym submit w formularzu','Wyłącza kliknięcia','Włącza tablist','Ukrywa przycisk'],
['Czy readonly props oznacza runtime freeze?','Nie','Tak','Tylko w TSX','Tylko po filter'],
['Czy filter zachowuje kolejność rekordów?','Tak','Zawsze sortuje po ID','Zawsze odwraca','Usuwa duplikaty automatycznie'],
['Co ma trafić do callbacku StatusSelect?','Sprawdzony StatusFilter','Surowy File','Cały globalny store','Zawsze undefined'],
['Czy licznik OPEN może być zerem?','Tak','Nie','Tylko dla pustego query','Tylko przy cards'],
['Czy ALL równa się sumie trzech statusów dla poprawnego modelu?','Tak','Nie nigdy','Tylko dla jednej sztuki','Tylko w widoku tabeli'],
['Co zrobić z pustym wynikiem?','Pokazać komunikat','Pobrać stary count ze stanu','Udawać sukces zapisu','Zresetować zawsze status'],
['Gdzie umieścić reset kliknięty przez użytkownika?','W handlerze','W renderze','W efekcie statusCounts','W porównywaniu sort'],
];
const quiz=concepts.map(row=>[...row,'Wspólna decyzja ma jednego właściciela. Dzieci dostają wartości i zgłaszają zmiany przez typowane callbacki.']);quiz.push(...require('./practice_arch_01.cjs').quiz.slice(0,30).map(row=>['Powtórka właściciela stanu: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const status of ['ALL','OPEN','IN_PROGRESS','CLOSED','open','','DONE'])add('Guard '+JSON.stringify(status),`isStatusFilter(${JSON.stringify(status)})`,['ALL','OPEN','IN_PROGRESS','CLOSED'].includes(status));
for(const status of ['ALL','OPEN','IN_PROGRESS','CLOSED'])for(const length of [0,1,4]){const statuses=['OPEN','CLOSED','OPEN','IN_PROGRESS'].slice(0,length);add('Count '+status+' z '+length,`statusCounts(data.slice(0,${length})).${status}`,status==='ALL'?length:statuses.filter(v=>v===status).length);}
for(const status of ['ALL','OPEN','IN_PROGRESS','CLOSED']){const ids={ALL:'0,1,2,3',OPEN:'0,2',IN_PROGRESS:'3',CLOSED:'1'};add('ID po '+status,`filterTickets(data,${JSON.stringify(status)}).map(ticket=>ticket.id).join(",")`,ids[status]);}
for(const [label,expr,a]of [['Nowa tablica ALL','filterTickets(data,"ALL")===data',false],['Oryginał długość','data.length',4],['Referencja pierwszego','filterTickets(data,"OPEN")[0]===data[0]',true],['Pusta lista wynik','filterTickets([],"OPEN").length',0],['Pusta lista ALL','statusCounts([]).ALL',0],['Suma statusów','(()=>{const c=statusCounts(data);return c.OPEN+c.IN_PROGRESS+c.CLOSED;})()',4],['Case sensitive','isStatusFilter("Open")',false],['Trim niejawny brak','isStatusFilter(" OPEN ")',false],['Typ count','typeof statusCounts(data).OPEN','number'],['Liczba kluczy count','Object.keys(statusCounts(data)).length',4],['Brak mutacji','(()=>{statusCounts(data);return data[0].status;})()','OPEN'],['ID zero','filterTickets(data,"OPEN")[0].id',0],['ALL puste sumy','statusCounts([]).OPEN+statusCounts([]).CLOSED+statusCounts([]).IN_PROGRESS',0],['IN_PROGRESS jeden','filterTickets(data,"IN_PROGRESS").length',1],['Widok alternatywny','("table" as View)==="table"?"cards":"table"','cards'],['OPEN bez CLOSED','filterTickets(data,"OPEN").some(ticket=>ticket.status==="CLOSED")',false],['ALL kopia zachowuje element','filterTickets(data,"ALL")[3]===data[3]',true]])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);
const data='const data:readonly Ticket[]=[{id:0,title:"API",status:"OPEN"},{id:1,title:"Archiwum",status:"CLOSED"},{id:2,title:"Sesja",status:"OPEN"},{id:3,title:"Raport",status:"IN_PROGRESS"}];';for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik wspólnego filtra?',a,...wrong,'Filtr operuje na jednej liście i jednym statusie, a liczniki wyliczamy z pełnych danych wejściowych.',model+'\n'+data+'console.log('+expr+');']);}
const project={'src/Page.tsx':page,'src/main.tsx':'import {createRoot} from "react-dom/client";import {TicketPage} from "./Page";import type {Ticket} from "./Page";'+data+'const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<TicketPage tickets={data}/>);'};
module.exports={model,controls,page,exercises,quiz,project};
