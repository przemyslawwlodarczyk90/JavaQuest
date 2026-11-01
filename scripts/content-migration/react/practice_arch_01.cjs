const model=`export const ORDER=["CRITICAL","HIGH","MEDIUM","LOW"] as const;export type Priority=(typeof ORDER)[number];export type Ticket={id:number;title:string;assignee:string|null;priority:Priority;status:"OPEN"|"CLOSED"};
export function visibleTickets(tickets:readonly Ticket[],login:string,query:string,hideClosed:boolean):Ticket[]{const q=query.trim().toLocaleLowerCase("pl-PL");return tickets.filter(ticket=>ticket.assignee===login&&(!hideClosed||ticket.status!=="CLOSED")&&ticket.title.toLocaleLowerCase("pl-PL").includes(q));}
export function priorityGroups(tickets:readonly Ticket[]):Array<{priority:Priority;tickets:Ticket[]}>{return ORDER.map(priority=>({priority,tickets:tickets.filter(ticket=>ticket.priority===priority)})).filter(group=>group.tickets.length>0);}`;
const view=`${model}
export function TicketItem({ticket}:{ticket:Ticket}){return <li>#{ticket.id} {ticket.title}</li>;}
export function PriorityGroups({tickets}:{tickets:readonly Ticket[]}){const groups=priorityGroups(tickets);return groups.length===0?<p>Brak pasujących ticketów.</p>:<div>{groups.map(group=><section key={group.priority}><h3>{group.priority} ({group.tickets.length})</h3><ul>{group.tickets.map(ticket=><TicketItem key={ticket.id} ticket={ticket}/>)}</ul></section>)}</div>;}
export function MyDayHeader({login,count}:{login:string;count:number}){return <h2>Dzień dobry, {login} — wyników: {count}</h2>;}
export function FilterBar({query,hideClosed,onQueryChange,onHideClosedChange,onReset}:{query:string;hideClosed:boolean;onQueryChange:(query:string)=>void;onHideClosedChange:(checked:boolean)=>void;onReset:()=>void}){return <div><label>Szukaj<input value={query} onChange={event=>onQueryChange(event.currentTarget.value)}/></label><label><input type="checkbox" checked={hideClosed} onChange={event=>onHideClosedChange(event.currentTarget.checked)}/>Ukryj zamknięte</label><button type="button" onClick={onReset}>Resetuj filtry</button></div>;}`;
const page=`import {useState} from "react";
${view}
export function MyDayPage({tickets,login}:{tickets:readonly Ticket[];login:string}){const [query,setQuery]=useState("");const [hideClosed,setHideClosed]=useState(true);const visible=visibleTickets(tickets,login,query,hideClosed);return <main><MyDayHeader login={login} count={visible.length}/><FilterBar query={query} hideClosed={hideClosed} onQueryChange={setQuery} onHideClosedChange={setHideClosed} onReset={()=>{setQuery("");setHideClosed(true);}}/><PriorityGroups tickets={visible}/></main>;}`;
const exercises=[
['Zaprojektuj hierarchię MyDayPage, Header, FilterBar i PriorityGroups.','Odpowiedzialność komponentu wynika z UI.','MyDayPage posiada filtry i wylicza widoczne dane. Header prezentuje login i liczbę wyników. FilterBar wyświetla kontrolki i wywołuje callbacki. PriorityGroups grupuje otrzymane dane, a TicketItem prezentuje jeden ticket.'],
['Zdefiniuj Ticket i kolejność priorytetów jako dane.','Kolejność grup nie powinna zależeć od wejściowej listy.',model],
['Zbuduj TicketItem jako czystą prezentację props.','Bez kopii ticket w useState.',view],
['Zbuduj statyczne PriorityGroups bez handlerów i stanu.','Najpierw sprawdź renderowanie danych.',view],
['Pomiń puste grupy i dodaj czytelny pusty wynik.','Brak danych też jest stanem widoku.',view],
['Przekaż Header gotowy count i login.','Header nie musi znać filtrów.',view],
['Wskaż minimalny nowy stan ekranu.','Usuń dane przychodzące z props i dane wyliczane.','Nowym stanem są query i hideClosed. Tickets i login przychodzą z props. Widoczne tickety, grupy oraz count są pochodnymi props i filtrów.'],
['Umieść query i hideClosed w najbliższym wspólnym rodzicu.','Kontrolki oraz lista korzystają z ich skutków.',page],
['Nie kopiuj tickets do useState bez potrzeby lokalnej edycji.','Nowe props powinny od razu wpływać na wynik.',page],
['Filtruj po dokładnym assignee=login.','Nie używaj includes dla tożsamości użytkownika.',model],
['Obsłuż nieprzypisany ticket z assignee=null.','Null nie pasuje do loginu anna.',model],
['Normalizuj query przez trim i małe litery.','Zachowaj surowy tekst pola do edycji.',model],
['Normalizuj także tytuł po tej samej stronie porównania.','Samo query.toLowerCase nie wystarczy.',model],
['Ukrywaj CLOSED tylko przy włączonym hideClosed.','OPEN zostaje widoczne przy obu ustawieniach.',model],
['Zachowaj kolejność rekordów wewnątrz grupy.','Filter nie sortuje wejścia.',model],
['Nadaj key=ticket.id elementom listy.','ID=0 jest poprawną tożsamością.',view],
['Nadaj grupom klucze priorytetów.','Key ma identyfikować grupę.',view],
['Wylicz licznik z tego samego visible co lista.','Nie utrzymuj drugiego setCount.',page],
['Zbuduj kontrolowany FilterBar bez lokalnej kopii query.','Rodzic jest właścicielem wartości.',view],
['Zmieniaj query rodzica przez callback.','Dziecko nie mutuje props.',view],
['Odczytaj hideClosed z checked, nie value.','Callback przyjmuje boolean.',view],
['Dodaj reset obu filtrów w handlerze.','To skutek kliknięcia, nie useEffect.',page],
['Obsłuż zmianę loginu w props bez synchronizującego efektu.','Derived data liczą się ponownie podczas renderu.',page],
['Obsłuż usunięcie ticketu z listy wejściowej.','Nie kopiuj listy wynikowej do stanu.',page],
['Sprawdź brak mutacji zamrożonej listy.','Filter i map tworzą nowe kontenery.',model],
['Nie dodawaj useMemo jako warunku poprawności.','Najpierw jasny przepływ, potem pomiar.',page],
['Wyjaśnij granice dopasowania wyszukiwarki.','Includes to nie tokenizacja ani automatyczne usuwanie diakrytyków.','Query po trim i zamianie liter jest jednym fragmentem. Wiele słów nie oznacza warunku AND w dowolnej kolejności. Litera ł nie jest automatycznie zamieniana na l; jeśli produkt tego wymaga, zaprojektuj osobną normalizację.'],
['Sprawdź typ count przekazywany do Header.','Props opisują kontrakt prezentacji.',view+'\nexport function WrongHeader(){\n// @ts-expect-error: count wymaga number\nreturn <MyDayHeader login="anna" count="3"/>;}'],
['Zdecyduj, gdzie trzymać rozwinięcie pojedynczej grupy.','Stan lokalnego UI nie musi trafiać na szczyt.','Jeśli rozwinięcie dotyczy tylko jednej grupy, może być lokalne w jej komponencie. Gdy nagłówek ma sterować rozwiń wszystkie, przenieś tę decyzję do wspólnego właściciela. Nie dodawaj globalnego kontekstu wyłącznie dlatego, że stan istnieje.'],
['Zbuduj ekran z dokładnie dwoma nowymi wartościami stanu.','Sprawdź licznik, filtry i puste wyniki.',page],
];
const concepts=[
['Od czego zacząć Thinking in React?','Od hierarchii komponentów','Od useEffect dla każdego pola','Od globalnego store','Od optymalizacji memo'],
['Co budujesz przed interakcjami?','Wersję statyczną z props','Gotowe API obowiązkowo','Reducer każdego label','Cache localStorage'],
['Co jest minimalnym stanem tego ekranu?','Query i hideClosed','Visible, count i każda grupa','Kopia całej aplikacji','Tylko count'],
['Gdzie trzymać stan używany przez filtr i listę?','W najbliższym wspólnym rodzicu','W obu dzieciach osobno','W module globalnym zawsze','W DOM bez właściciela'],
['Jak dziecko zgłasza zmianę rodzicowi?','Wywołuje callback z props','Mutuje props','Modyfikuje cudzy useState bezpośrednio','Zmienia key rodzica'],
['Co oznacza count w Header?','Liczbę widocznych wyników','Niezależny licznik kliknięć','Liczbę znaków query','Zawsze wszystkie rekordy'],
['Dlaczego nie kopiować count do stanu?','Da się go wyliczyć z visible','Nie wolno używać liczb w stanie','React nie renderuje count','TypeScript zabrania'],
['Dlaczego nie kopiować tickets do stanu tego ekranu?','Już są danymi wejściowymi z props','Props są zawsze mutable','Filter wymaga useState','UseState nigdy nie przyjmuje tablic'],
['Co robi zmiana props login?','Zmienia wyliczone wyniki przy kolejnym renderze','Wymaga setVisible w efekcie','Automatycznie resetuje każdą instancję','Zapisuje tickets'],
['Co ma robić FilterBar?','Prezentować wartości i wywoływać callbacki','Przechowywać drugi stan tych samych filtrów','Pobierać inne tickety bez uzgodnienia','Mutować listę'],
['Czy wersja statyczna musi mieć useState?','Nie','Tak','Tylko w TSX','Tylko dla list'],
['Czy każdy komponent ma mieć jedną linię kodu?','Nie, liczy się spójna odpowiedzialność','Tak','Tylko Header','Tylko w production'],
['Co jest danymi pochodnymi?','Grupy i liczniki z listy oraz filtrów','Wpisywane query','Decyzja użytkownika hideClosed','Wszystkie eventy'],
['Czy useMemo jest wymagane do poprawnego grupowania?','Nie','Tak zawsze','Tylko przy readonly','Tylko w JSX'],
['Po co typy props?','Opisują kontrakt przepływu danych','Walidują serwer runtime','Gwarantują dobry UX','Zastępują callbacki'],
['Jak sprawdzamy własność ticketu?','Assignee===login','Assignee.includes(login)','Id===login','Title===login'],
['Jak traktujemy assignee=null?','Nie pasuje do konkretnego loginu','Pasuje każdemu','Rzuca wyjątek w filter','Zawsze pasuje annie'],
['Co daje trim query?','Usuwa spacje brzegowe wyszukiwania','Usuwa spacje wewnętrzne','Usuwa polskie znaki','Sortuje listę'],
['Czy lowercase usuwa diakrytyki?','Nie','Tak','Tylko dla ł','Tylko w React'],
['Czy includes realizuje automatyczne AND słów?','Nie','Tak','Tylko przy spacji','Tylko po trim'],
['Gdzie wykonać reset po kliknięciu?','W handlerze resetu','W efekcie obserwującym tickets','W renderze dziecka','W funkcji porównującej sort'],
['Czy filter mutuje tablicę?','Nie','Tak','Tylko przy readonly','Tylko dla pustej'],
['Czy readonly jest zamrożeniem runtime?','Nie','Tak','Tylko z props','Tylko dla Ticket'],
['Jaka jest kolejność grup?','Jawne ORDER priorytetów','Losowa co render','Zawsze alfabetyczna','Według ID pierwszego ticketu'],
['Jak obsłużyć brak wyników?','Wyświetlić czytelny pusty widok','Udawać ładowanie na zawsze','Zwrócić stary wynik','Dodać nowy state obowiązkowo'],
['Czy ID=0 może być key?','Tak','Nie','Tylko jako false','Tylko w tablicy pustej'],
['Co robi Header z count?','Prezentuje przekazaną liczbę','Sam zapisuje tickety','Generuje ID','Kontroluje checkbox'],
['Gdzie może żyć stan rozwinięcia jednej grupy?','W komponencie grupy, gdy nie jest współdzielony','Zawsze w kontekście globalnym','Zawsze w API','W count nagłówka'],
['Czy złożony ekran wymaga wielu stanów dla każdego widoku danych?','Nie, wystarczą źródła i wyliczenia','Tak zawsze','Tylko w TSX','Tylko z listą'],
['Co sprawdzić przy zmianie wejściowych tickets?','Czy licznik i lista używają aktualnych danych','Tylko className','Tylko liczbę hooków','Wyłącznie tytuł strony'],
];
const quiz=concepts.map(row=>[...row,'Projektuj przepływ danych od źródeł do widoków. Stan przechowuje decyzje, których nie można odtworzyć z props i pozostałych wartości.']);
quiz.push(...require('./practice_state_06.cjs').quiz.slice(0,30).map(row=>['Powtórka danych pochodnych: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const login of ['anna','piotr','kasia',''])for(const hide of [false,true])add('Wyniki login '+JSON.stringify(login)+' hide '+hide,`visibleTickets(data,${JSON.stringify(login)},"",${hide}).length`,login==='anna'?(hide?2:3):login==='piotr'?1:0);
for(const query of ['',' ','api','API',' api ','log','timeout','brak'])for(const hide of [false,true]){const q=query.trim().toLowerCase();const records=[['API timeout','OPEN'],['API archiwum','CLOSED'],['Logowanie','OPEN']];add('Filtr '+JSON.stringify(query)+' hide '+hide,`visibleTickets(data,"anna",${JSON.stringify(query)},${hide}).length`,records.filter(([title,status])=>(!hide||status!=='CLOSED')&&title.toLowerCase().includes(q)).length);}
for(const [label,expr,a]of [
['Kolejność grup','priorityGroups(data).map(group=>group.priority).join(",")','CRITICAL,HIGH,MEDIUM,LOW'],['Liczba grup','priorityGroups(data).length',4],['Puste grupy','priorityGroups([]).length',0],['Kolejność moich ID','visibleTickets(data,"anna","",false).map(ticket=>ticket.id).join(",")','0,2,3'],['ID zero','visibleTickets(data,"anna","timeout",true)[0]?.id',0],['Oryginalna długość','data.length',4],['Nowa tablica','visibleTickets(data,"anna","",false)===data',false],['Ta sama referencja rekordu','visibleTickets(data,"anna","",false)[0]===data[0]',true],['Pusta grupa z samego LOW','priorityGroups([data[3]]).length',1],['Grupa LOW','priorityGroups([data[3]])[0].priority','LOW'],['Brak właściciela','visibleTickets([{...data[0],assignee:null}],"anna","",false).length',0],['Bez tokenizacji','visibleTickets(data,"anna","timeout API",false).length',0],['Jedna zgodna fraza','visibleTickets(data,"anna","api timeout",false).length',1],['Grupy nie mutują','(()=>{priorityGroups(data);return data[0].id;})()',0],['Liczba widocznych grup','priorityGroups(visibleTickets(data,"anna","",true)).length',2],['Pusty query pasuje tytułowi','"abc".includes("")',true],
])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);
const data='const data:readonly Ticket[]=[{id:0,title:"API timeout",assignee:"anna",priority:"HIGH",status:"OPEN"},{id:1,title:"Inny",assignee:"piotr",priority:"CRITICAL",status:"OPEN"},{id:2,title:"API archiwum",assignee:"anna",priority:"MEDIUM",status:"CLOSED"},{id:3,title:"Logowanie",assignee:"anna",priority:"LOW",status:"OPEN"}];';
for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1','2'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik ekranu Mój dzień?',a,...wrong,'Najpierw sprawdź właściciela, status i dopasowanie frazy. Grupy wynikają z tej samej przefiltrowanej listy.',model+'\n'+data+'\nconsole.log('+expr+');']);}
const project={'src/MyDay.tsx':page,'src/main.tsx':'import {createRoot} from "react-dom/client";import {MyDayPage} from "./MyDay";'+'import type {Ticket} from "./MyDay";'+data+'const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<MyDayPage tickets={data} login="anna"/>);'};
module.exports={model,view,page,exercises,quiz,project};
