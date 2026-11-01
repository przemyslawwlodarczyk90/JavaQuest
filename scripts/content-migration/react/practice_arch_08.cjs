const model=`export type Ticket={id:number;title:string;status:"OPEN"|"CLOSED";createdAt:string};export type Status="ALL"|Ticket["status"];export type Sort={key:"title"|"createdAt";direction:"asc"|"desc"};
export function filterAndSort(tickets:readonly Ticket[],query:string,status:Status,sort:Sort):Ticket[]{const q=query.trim().toLowerCase();const filtered=tickets.filter(ticket=>(status==="ALL"||ticket.status===status)&&ticket.title.toLowerCase().includes(q));return filtered.sort((a,b)=>{let result:number;if(sort.key==="title")result=a.title.localeCompare(b.title,"pl");else{const left=Date.parse(a.createdAt),right=Date.parse(b.createdAt);if(!Number.isFinite(left)||!Number.isFinite(right))return Number.isFinite(left)?-1:Number.isFinite(right)?1:0;result=left-right;}return sort.direction==="asc"?result:-result;});}
export function paginate<T>(items:readonly T[],requested:number,size:number){if(!Number.isSafeInteger(size)||size<1)throw RangeError("Rozmiar");if(!Number.isSafeInteger(requested))throw RangeError("Strona");const pageCount=Math.max(1,Math.ceil(items.length/size));const page=Math.min(pageCount,Math.max(1,requested));return {page,pageCount,pageItems:items.slice((page-1)*size,page*size),total:items.length};}
export function readStored<T>(raw:string|null,parse:(input:unknown)=>T|null,fallback:T):T{if(raw===null)return fallback;try{const input:unknown=JSON.parse(raw);return parse(input)??fallback;}catch{return fallback;}}`;
const hook=`import {useState} from "react";
${model}
export function useTicketFilters(tickets:readonly Ticket[],{pageSize=10}:{pageSize?:number}={}){const [query,setQueryState]=useState("");const [status,setStatusState]=useState<Status>("ALL");const [sort,setSortState]=useState<Sort>({key:"createdAt",direction:"desc"});const [requestedPage,setRequestedPage]=useState(1);const result=paginate(filterAndSort(tickets,query,status,sort),requestedPage,pageSize);function setQuery(value:string){setQueryState(value);setRequestedPage(1);}function setStatus(value:Status){setStatusState(value);setRequestedPage(1);}function setSort(value:Sort){setSortState(value);setRequestedPage(1);}function reset(){setQueryState("");setStatusState("ALL");setSortState({key:"createdAt",direction:"desc"});setRequestedPage(1);}return {query,setQuery,status,setStatus,sort,setSort,setPage:setRequestedPage,reset,...result};}
export function useToggle(initial=false){const [on,setOn]=useState(initial);return [on,()=>setOn(value=>!value)] as const;}
export type TicketFilters=ReturnType<typeof useTicketFilters>;`;
const screen=`${hook}
export function FilteredTickets({tickets,title,pageSize=10}:{tickets:readonly Ticket[];title:string;pageSize?:number}){const f=useTicketFilters(tickets,{pageSize});return <section><h2>{title}</h2><label>Szukaj<input value={f.query} onChange={event=>f.setQuery(event.currentTarget.value)}/></label><label>Status<select value={f.status} onChange={event=>{const status=event.currentTarget.value;if(status==="ALL"||status==="OPEN"||status==="CLOSED")f.setStatus(status);}}><option>ALL</option><option>OPEN</option><option>CLOSED</option></select></label><button type="button" onClick={()=>f.setSort({key:"title",direction:f.sort.direction==="asc"?"desc":"asc"})}>Sortuj tytuł</button><p>Wyników: {f.total}; strona {f.page} z {f.pageCount}</p><ul>{f.pageItems.map(ticket=><li key={ticket.id}>{ticket.title}</li>)}</ul>{f.total===0&&<p>Brak wyników</p>}<button type="button" disabled={f.page<=1} onClick={()=>f.setPage(f.page-1)}>Poprzednia</button><button type="button" disabled={f.page>=f.pageCount} onClick={()=>f.setPage(f.page+1)}>Następna</button><button type="button" onClick={f.reset}>Resetuj</button></section>;}
export function TwoScreens({tickets}:{tickets:readonly Ticket[]}){return <main><FilteredTickets tickets={tickets} title="Tickety"/><FilteredTickets tickets={tickets.filter(ticket=>ticket.status==="CLOSED")} title="Archiwum" pageSize={25}/></main>;}
export function ExpandableText({text}:{text:string}){const [expanded,toggle]=useToggle();return <div><button type="button" aria-expanded={expanded} onClick={toggle}>Opis</button>{expanded&&<p>{text}</p>}</div>;}`;
const selection=require('./practice_arch_03.cjs').hook;
const exercises=[
['Wydziel stan i logikę filtrów do useTicketFilters.','Hook nadaje nazwę odpowiedzialności.',hook],
['Przyjmij tickets i opcje pageSize z domyślnym 10.','Opcje obiektowe łatwiej rozszerzać.',hook],
['Trzymaj query, status, sort i requestedPage jako źródła.','Filtered i pageItems są wyliczane.',hook],
['Po zmianie query wróć na stronę 1 w tym samym handlerze.','Bez efektu obserwującego query.',hook],
['Po zmianie status wróć na stronę 1.','Każdy ekran ma tę samą politykę.',hook],
['Po zmianie sort wróć na stronę 1.','To jawna decyzja API tego hooka.',hook],
['Napisz filterAndSort bez mutacji tickets.','Sortuj nową tablicę z filter.',model],
['Sortuj daty jako znaczniki czasu.','Nie porównuj leksykograficznie różnych offsetów.',model],
['Umieść niepoprawne daty na końcu dla obu kierunków.','Sprawdzenie braku poprzedza odwrócenie kierunku.',model],
['Napisz paginate z walidacją size i requestedPage.','Nie przekazuj NaN ani ułamków jako stron.',model],
['Ogranicz stronę do istniejącego zakresu.','Przy pustej liście jest strona 1 z 1.',model],
['Zwróć efektywną page zamiast starej requestedPage.','Zmniejszenie listy nie może pokazać pustej strony poza zakresem.',hook],
['Nie nadpisuj stanu podczas renderu przy zmianie props.','Clamp może być danymi wyliczanymi.',hook],
['Zwróć wynik jako obiekt z nazwanymi polami.','Wiele wartości lepiej czyta się po nazwach.',hook],
['Wyprowadź TicketFilters przez ReturnType.','To alias aktualnego wyniku, nie zamrożona wersja API.',hook],
['Zbuduj FilteredTickets z hookiem i krótkim JSX.','Komponent odpowiada za prezentację.',screen],
['Użyj tego samego hooka dla archiwum CLOSED z pageSize=25.','Logika wspólna, stan każdej instancji osobny.',screen],
['Wyrenderuj oba ekrany jednocześnie i sprawdź niezależność query.','Dwa wywołania nie tworzą wspólnego store.',screen],
['Wydziel useToggle zwracające krotkę as const.','Wartość i callback mają stałe pozycje i typy.',hook],
['Użyj toggle z updaterem funkcyjnym.','Dwa wywołania toggle mają wrócić do pierwotnego stanu.',hook],
['Dodaj ExpandableText z aria-expanded.','Hook nie musi znać JSX.',screen],
['Wydziel useSelection bez łączenia go na siłę z filtrami.','Różne aspekty mają osobne API.',selection],
['Wyjaśnij współdzielenie logiki i współdzielenie stanu.','To dwa różne efekty.','Każde wywołanie hooka zawierającego useState ma własne sloty stanu. Gdy oba komponenty mają używać jednej decyzji, wywołaj hook we wspólnym rodzicu i przekaż wynik albo udostępnij jedną instancję przez kontekst.'],
['Wywołuj hook na najwyższym poziomie komponentu.','Nie warunkuj kolejności wywołań.',screen],
['Nie nazywaj czystego formattera useFormatDate.','Funkcja bez hooków nie potrzebuje ograniczeń hooka.','Nazwij czystą funkcję formatDate i testuj ją na danych. Przedrostek use sygnalizuje reguły hooków. Funkcja korzystająca z useState powinna z kolei być hookiem nazwanym useX.'],
['Napisz readStored walidujące unknown po JSON.parse.','Generyk T nie sprawdza obcych danych.',model],
['Obsłuż uszkodzony JSON oraz null przez fallback.','Parser może odrzucić poprawny składniowo zły kształt.',model],
['Rozróżnij readStored od pełnego hooka localStorage.','Zapis i synchronizacja zewnętrzna to osobny temat.','ReadStored jest czystą funkcją parsującą przekazany tekst. Nie odczytuje ani nie zapisuje localStorage. Pełny hook potrzebuje obsługi wyjątków storage, polityki zmiany klucza i synchronizacji; efekty poznasz w kolejnym rozdziale.'],
['Sprawdź ochronę setStatus przed nieznaną wartością.','Typ API hooka jest wyprowadzony z implementacji.',hook+'\nexport function WrongStatus(){const f=useTicketFilters([]);\n// @ts-expect-error: nieznany status\nf.setStatus("DONE");return null;}'],
['Przetestuj reset strony, zmianę props i niezależność instancji.','Sprawdź też puste wyniki i clamp po usunięciu danych.',screen],
];
const concepts=[
['Co współdzieli własny hook z lokalnym useState?','Logikę, nie automatycznie stan','Zawsze jeden globalny stan','Tylko CSS','Obiekty DOM'],
['Jak nazywamy funkcję wywołującą hooki?','UseX','GetX bez reguł','RenderOnly zawsze','Dowolnie bez znaczenia'],
['Gdzie wywoływać useTicketFilters?','Na najwyższym poziomie komponentu lub hooka','W dowolnej pętli','W onClick','Po warunkowym return zależnym od props'],
['Gdzie reset strony po zmianie filtra?','W funkcji zmiany filtra','W efekcie synchronizującym dwie wartości','W JSX po każdym renderze','W formatterze daty'],
['Co jest stanem hooka?','Query, status, sort i żądana strona','Filtered i count jako duplikaty','Każdy wynik map','Tylko pageItems'],
['Co jest danymi wyliczanymi?','Lista po filtrach i efektywna page','Surowy query wpisany przez użytkownika','Decyzja sortowania','Każdy callback'],
['Jak zwrócić wiele narzędzi hooka?','Obiektem nazwanych pól','Wyłącznie przez globalny obiekt','Zawsze krotką 20 elementów','Przez mutację props'],
['Po co as const w useToggle?','Zachowuje typ krotki','Zamraża runtime hook','Dzieli stan globalnie','Usuwa callback'],
['Co robi ReturnType<typeof useTicketFilters>?','Wyprowadza typ wyniku funkcji','Wykonuje hook','Waliduje storage','Utrwala wersję API niezależnie od zmian'],
['Czy dwa FilteredTickets mają wspólne query?','Nie, każde ma własną instancję hooka','Tak','Tylko gdy ten sam plik','Tylko przy pageSize=10'],
['Jak uzyskać wspólne filtry?','Jedna instancja we wspólnym właścicielu','Dwa useState o tej samej nazwie','Import samego hooka','Losowy key'],
['Czy nowa tablica tickets props resetuje stan filtrów?','Nie automatycznie','Tak zawsze','Tylko przy readonly','Tylko w TSX'],
['Dlaczego zwracamy efektywną page?','Aby UI używało strony po ograniczeniu zakresu','Aby ukryć wszystkie dane','Aby zapisać storage','Aby wyłączyć sort'],
['Jaki zakres stron dla pustej listy w przykładzie?','1 z 1','0 z 0','Infinity','-1 z 1'],
['Czy requestedPage=1.5 jest poprawna?','Nie','Tak','Tylko dla cards','Tylko przy pageSize=10'],
['Czy size=0 jest poprawne?','Nie','Tak','Tylko dla pustej listy','Tylko w dev'],
['Czy filterAndSort mutuje tickets?','Nie, sortuje wynik filter','Tak','Tylko z readonly','Tylko dla dat'],
['Jak traktować niepoprawne daty?','Na końcu zgodnie z jawną polityką','Losowo','Zawsze jako teraz','Tylko w asc'],
['Czy leksykograficzne ISO zawsze porówna różne offsety poprawnie?','Nie','Tak','Tylko dla null','Tylko w TSX'],
['Czy localeCompare musi zwrócić dokładnie -1 lub 1?','Nie, istotny jest znak','Tak','Tylko dla pl','Tylko w Node'],
['Co robi reset hooka?','Przywraca wszystkie źródła filtrów do domyślnych','Usuwa listę props','Zapisuje API','Zmienia key każdego rekordu'],
['Czy useToggle powinno używać setOn(!on) w kolejce wielu zmian?','Updater funkcyjny lepiej zachowuje kolejne przełączenia','Zawsze tylko !on','To bez znaczenia w każdym przypadku','Nie wolno toggle w callbacku'],
['Czy readStored jest hookiem?','Nie, to czysty parser','Tak, bo ma generyk','Tak, bo czyta tekst','Tak, bo zwraca fallback'],
['Czy JSON.parse as T sprawdza dane?','Nie','Tak','Tylko z strict','Tylko w React'],
['Po co parser unknown w readStored?','Sprawdza kształt danych w runtime','Tworzy provider','Zmienia nazwę klucza','Blokuje render'],
['Co przy błędnym JSON?','Fallback zgodnie z kontraktem','Zawsze awaria aplikacji','Automatyczny poprawny Ticket','Zapis do DOM'],
['Czy wydzielenie hooka samo poprawia wydajność?','Nie, przede wszystkim porządkuje logikę','Tak zawsze','Tylko przy useState','Tylko w TSX'],
['Kiedy warto wydzielić hook?','Gdy nazywa spójny aspekt lub usuwa powieloną logikę','Obowiązkowo dla każdego boolean','Tylko gdy ma 100 linii','Wyłącznie dla fetch'],
['Czy hook może korzystać z innego własnego hooka?','Tak','Nie','Tylko z context','Tylko po useEffect'],
['Jak testować niezależność instancji?','Zmienić jedną i sprawdzić drugą','Wyłącznie sprawdzić nazwę use','Policzyć pliki','Zmienić CSS'],
];
const quiz=concepts.map(row=>[...row,'Hook wydziela logikę React, a każde jego wywołanie ma własny stan. Funkcje czyste i walidacja wejścia mogą pozostać poza hookiem.']);quiz.push(...require('./practice_arch_02.cjs').quiz.slice(0,30).map(row=>['Powtórka właściciela instancji: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const size of [1,2,3,10])for(const page of [-1,1,2,99])add('Page '+page+' size '+size,`paginate(data,${page},${size}).page`,Math.min(Math.max(1,Math.ceil(3/size)),Math.max(1,page)));
for(const [label,expr,a]of [['Puste pageCount','paginate([],99,10).pageCount',1],['Puste page','paginate([],99,10).page',1],['Puste items','paginate([],1,10).pageItems.length',0],['Total','paginate(data,1,2).total',3],['Pierwsze ID','paginate(data,2,2).pageItems[0].id',2],['Query trim','filterAndSort(data," API ","ALL",sort).length',1],['Status OPEN','filterAndSort(data,"","OPEN",sort).length',2],['Status CLOSED','filterAndSort(data,"","CLOSED",sort).length',1],['Brak wyników','filterAndSort(data,"brak","ALL",sort).length',0],['Sort tytuł','filterAndSort(data,"","ALL",sort).map(ticket=>ticket.id).join(",")','0,1,2'],['Sort desc','filterAndSort(data,"","ALL",{key:"title",direction:"desc"}).map(ticket=>ticket.id).join(",")','2,1,0'],['Oryginał','data.map(ticket=>ticket.id).join(",")','0,1,2'],['Nowa tablica','filterAndSort(data,"","ALL",sort)===data',false],['Referencja rekordu','filterAndSort(data,"","ALL",sort)[0]===data[0]',true],['Daty desc','filterAndSort(data,"","ALL",{key:"createdAt",direction:"desc"})[0].id',2],['Daty asc','filterAndSort(data,"","ALL",{key:"createdAt",direction:"asc"})[0].id',0],['Fallback null','readStored(null,parseNumber,7)',7],['Fallback JSON','readStored("{",parseNumber,7)',7],['Fallback zły typ','readStored(JSON.stringify("abc"),parseNumber,7)',7],['Poprawny parser','readStored("3",parseNumber,7)',3],['Zero nie fallback','readStored("0",parseNumber,7)',0],['Niepoprawny rozmiar','(()=>{try{paginate(data,1,0);return false;}catch{return true;}})()',true],['Niepoprawna strona','(()=>{try{paginate(data,NaN,2);return false;}catch{return true;}})()',true],['Ułamek strona','(()=>{try{paginate(data,1.5,2);return false;}catch{return true;}})()',true]])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);const data='const data:readonly Ticket[]=[{id:0,title:"API",status:"OPEN",createdAt:"2026-10-27T10:00:00Z"},{id:1,title:"Logowanie",status:"CLOSED",createdAt:"2026-10-28T10:00:00Z"},{id:2,title:"Raport",status:"OPEN",createdAt:"2026-10-29T10:00:00Z"}];const sort:Sort={key:"title",direction:"asc"};const parseNumber=(input:unknown)=>typeof input==="number"?input:null;';for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik narzędzi hooka?',a,...wrong,'Filtruj i sortuj przed paginacją; page jest ograniczona do zakresu. Parser zwraca fallback tylko dla odrzuconego lub nieczytelnego wejścia.',model+'\n'+data+'console.log('+expr+');']);}
const project={'src/Filters.tsx':screen,'src/main.tsx':'import {createRoot} from "react-dom/client";import {TwoScreens} from "./Filters";import type {Ticket,Sort} from "./Filters";'+data+'const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<TwoScreens tickets={data}/>);'};
module.exports={model,hook,screen,selection,exercises,quiz,project};
