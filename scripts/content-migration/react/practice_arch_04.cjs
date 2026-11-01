const model=`export type Ticket={id:number;title:string};export type Notes=ReadonlyMap<number,string>;
export function noteFor(notes:Notes,id:number):string{return notes.get(id)??"";}
export function changeNote(notes:Notes,id:number,value:string):Map<number,string>{const next=new Map(notes);next.set(id,value);return next;}
export function removeNote(notes:Notes,id:number):Map<number,string>{const next=new Map(notes);next.delete(id);return next;}`;
const local=`import {useState} from "react";
${model}
export function QuickNote({ticketTitle}:{ticketTitle:string}){const [note,setNote]=useState("");return <label>Notatka do {ticketTitle}<textarea value={note} onChange={event=>setNote(event.currentTarget.value)}/></label>;}
export function FreshNotePanel({ticket}:{ticket:Ticket}){return <QuickNote key={ticket.id} ticketTitle={ticket.title}/>;}
export function StableDetails({ticket,compact}:{ticket:Ticket;compact:boolean}){return <section className={compact?"details details--compact":"details"}><h2>{ticket.title}</h2><QuickNote ticketTitle={ticket.title}/></section>;}`;
const controlled=`${model}
export function NoteField({ticket,value,onChange}:{ticket:Ticket;value:string;onChange:(value:string)=>void}){return <label>Notatka do {ticket.title}<textarea value={value} onChange={event=>onChange(event.currentTarget.value)}/></label>;}`;
const page=`import {useState} from "react";
${controlled}
export function NotesPage({tickets}:{tickets:readonly Ticket[]}){const [selectedId,setSelectedId]=useState<number|null>(()=>tickets[0]?.id??null);const [notes,setNotes]=useState<Notes>(()=>new Map<number,string>());const [compact,setCompact]=useState(false);const [showNote,setShowNote]=useState(true);const selected=tickets.find(ticket=>ticket.id===selectedId)??null;return <main><h1>Notatki ticketów</h1><nav aria-label="Wybierz ticket">{tickets.map(ticket=><button type="button" key={ticket.id} aria-pressed={selectedId===ticket.id} onClick={()=>setSelectedId(ticket.id)}>{ticket.title}</button>)}</nav><label><input type="checkbox" checked={compact} onChange={event=>setCompact(event.currentTarget.checked)}/>Kompaktowo</label><button type="button" onClick={()=>setShowNote(previous=>!previous)}>{showNote?"Ukryj":"Pokaż"} pole notatki</button><section className={compact?"details details--compact":"details"}>{selected?<><h2>{selected.title}</h2>{showNote&&<NoteField ticket={selected} value={noteFor(notes,selected.id)} onChange={value=>setNotes(previous=>changeNote(previous,selected.id,value))}/>}<button type="button" onClick={()=>setNotes(previous=>removeNote(previous,selected.id))}>Usuń notatkę</button></>:<p>Wybierz istniejący ticket.</p>}</section></main>;}`;
const hidden=`import {useState} from "react";
export function HiddenPanel(){const [shown,setShown]=useState(true);const [text,setText]=useState("");return <div><button type="button" onClick={()=>setShown(value=>!value)}>Pokaż/ukryj</button><section hidden={!shown}><label>Tekst<textarea value={text} onChange={event=>setText(event.currentTarget.value)}/></label></section></div>;}`;
const exercises=[
['Odtwórz lokalną QuickNote z kontrolowaną textarea.','Stan jest własnością instancji komponentu.',local],
['Zresetuj notatkę po wyborze innego ticketu przez key=id.','To reset, nie magazyn dawnych notatek.',local],
['Zachowaj stan przy compact przez stały typ section.','Zmieniaj klasę, nie typ opakowania.',local],
['Wyjaśnij utratę stanu po zmianie div na section.','Typ przodka wpływa na tożsamość poddrzewa.','Zmiana typu elementu w tej samej pozycji powoduje przebudowę jego poddrzewa. Stan potomnego pola może zostać utracony mimo tego samego JSX QuickNote. Utrzymaj stałą strukturę, jeśli zmienia się jedynie prezentacja.'],
['Przechowuj notatki wielu ticketów wyżej według ID.','ReadonlyMap<number,string> opisuje stan.',model],
['Napisz noteFor zwracające pusty string dla braku wpisu.','Kontrolowana textarea nie dostaje undefined.',model],
['Napisz changeNote bez mutacji poprzedniej Map.','Nowa Map kopiuje wpisy.',model],
['Napisz removeNote bez usuwania innych notatek.','Delete wykonaj na kopii.',model],
['Zbuduj kontrolowane NoteField bez lokalnej kopii value.','Właścicielem notatek jest rodzic.',controlled],
['Obsłuż przełączanie ticketów zachowujące ich notatki.','Pole odczytuje mapę dla selected.id.',page],
['Nie zgub wyboru ticketu o ID=0.','Nullish coalescing zamiast ||.',page],
['Gdy wybrany rekord zniknie z props, pokaż pusty wybór.','Nie wyświetlaj starej kopii ticketu.',page],
['Ukryj i odmontuj NoteField, ale zachowaj wartość w rodzicu.','Stan został podniesiony ponad znikający komponent.',page],
['Porównaj hidden z warunkowym usunięciem elementu.','Hidden nie odmontowuje.',hidden],
['Nie używaj losowego key dla wymuszenia odświeżania.','Key nie jest licznikiem renderów.','Losowy key tworzy nową tożsamość i resetuje lokalny stan przy zmianach. Stabilne ID opisuje obiekt; jawny reset wartości wykonaj w handlerze, jeśli nie chcesz resetować całego poddrzewa.'],
['Zdefiniuj komponenty na poziomie modułu.','Funkcja komponentu zagnieżdżona w renderze ma nową tożsamość.',local],
['Wyjaśnij różnicę między miejscem na ekranie a pozycją w drzewie.','CSS nie określa tożsamości React.','Przesunięcie elementu CSS-em nie jest przeniesieniem komponentu między rodzicami React. O zachowaniu stanu decyduje struktura drzewa, typ i key w danym rodzicu, nie współrzędne na ekranie.'],
['Sprawdź powrót do poprzedniego ticketu w wariancie FreshNotePanel.','Odmontowany lokalny stan nie jest cache.','Po A→B→A wariant key=id tworzy świeżą notatkę przy każdym ponownym montowaniu. Aby zachować draft A, użyj stanu Notes w rodzicu lub innego jawnego magazynu.'],
['Zachowaj pustą notatkę jako poprawną wartość mapy.','Brak wpisu i pusty wpis mogą mieć ten sam widok, ale różny kontrakt.',model],
['Usuń notatkę przyciskiem bez resetowania całego ekranu.','Usunięcie jednego klucza pozostawia filtry i wybór.',page],
['Nie zmieniaj key przy zmianie tytułu tego samego ticketu.','Tożsamość danych i aktualna etykieta to różne rzeczy.',local],
['Wybierz politykę aktualizacji props tego samego rekordu.','Stabilny key nie rozwiązuje konfliktów serwera.','Nowy obiekt z tym samym ID nie oznacza nowej instancji. Jeśli serwer zmienił dane, zdecyduj, czy zachować lokalny draft, ostrzec o konflikcie czy jawnie go przywrócić. Nie resetuj każdej zmiany props bez rozstrzygnięcia utraty pracy.'],
['Sprawdź niemutowalność przy zmianie notatki ID=0.','Oryginalna mapa zachowuje starą treść.',model+'\nconst old=new Map([[0,"Stara"]]);const next=changeNote(old,0,"Nowa");console.log(old.get(0),next.get(0));'],
['Wyprowadź brak osobnego stanu selectedTicket.','Selected wynika z tickets oraz selectedId.',page],
['Sprawdź reset przy zmianie key zamiast tylko zmianie props.','Surowy element React pokazuje key, ale nie dowodzi zachowania hooków.',local],
['Nie traktuj useId jako trwałego ID danych.','Id dostępności i key rekordu mają inne cele.','UseId służy powiązaniom etykiet i opisów instancji. Id ticketu pochodzi z danych. Nie generuj key list przez useId ani przez pozycję edytowalnego wiersza.'],
['Opisz zakres trwałości Notes w rodzicu.','Pamięć komponentu nie jest zapisem na dysku.','Mapa w NotesPage przetrwa zmiany wyboru i odmontowanie pola, dopóki sam NotesPage zachowuje instancję. Po odmontowaniu całego rodzica lokalny stan przepada. Trwałość między sesjami wymaga osobnego, bezpiecznego zapisu.'],
['Przetestuj zmianę kompaktowości bez utraty wpisu.','Utrzymaj stabilny kontener.',page],
['Przetestuj ukrycie pola i ponowne pokazanie w obu wariantach.','Lokalny draft i draft w rodzicu mają inne czasy życia.',hidden],
['Zbuduj kompletny ekran notatek oddzielnych dla każdego ticketu.','Testuj A→B→A, ID=0, ukrycie i usunięcie rekordu.',page],
];
const concepts=[
['Co określa zachowanie stanu instancji?','Typ, pozycja w rodzicu i key','Współrzędne CSS','Nazwa zmiennej props','Kolor pola'],
['Czy zmiana samego className zwykle resetuje lokalny stan?','Nie','Tak','Tylko dla section','Tylko z textarea'],
['Czy zmiana typu opakowania może zresetować potomków?','Tak','Nie','Tylko dla button','Tylko w TSX'],
['Co dzieje się z lokalnym stanem odmontowanej instancji?','Jest usuwany','Zawsze trafia do cache','Zapisuje się do pliku','Przechodzi do najbliższego label'],
['Czy key=id zachowuje wszystkie dawne drafty po powrocie?','Nie','Tak','Tylko dla liczb','Tylko po blur'],
['Gdzie przechować wiele draftów między przełączeniami?','W stabilnym właścicielu według ID','W losowym key','W className','W callbacku ref jako cache zawsze'],
['Czy pole kontrolowane potrzebuje lokalnej kopii value?','Nie','Tak','Tylko textarea','Tylko z Map'],
['Jak czytać brak notatki dla kontrolowanego pola?','Notes.get(id)?? pusty string','Zawsze undefined','Zawsze null jako value','Rzucić wyjątek'],
['Czy ID=0 powinno stać się brakiem wyboru?','Nie','Tak','Tylko dla key','Tylko w JSX'],
['Dlaczego komponent definiujemy poza rodzicem?','Aby nie tworzyć nowego typu przy każdym renderze','Aby usunąć props','Aby wyłączyć hooks','Aby zapisać Map'],
['Czy TS wykrywa utratę draftu przez zmianę typu rodzica?','Nie','Tak zawsze','Tylko strict','Tylko dla textarea'],
['Czy hidden usuwa instancję z drzewa?','Nie','Tak','Tylko na telefonie','Tylko dla section'],
['Czy ukryty panel automatycznie przestaje wykonywać wszystkie efekty?','Nie, samo hidden nie odmontowuje ani nie zarządza efektami','Tak','Tylko w TSX','Tylko po blur'],
['Czy przeniesienie pola do innego rodzica zawsze zachowuje jego stan przez ten sam key?','Nie','Tak','Tylko dla number','Tylko z Map'],
['Jaki zakres ma key w dopasowaniu rodzeństwa?','Lokalny w obrębie rodzica','Globalny dla całej aplikacji','Tylko CSS','Tylko API'],
['Co oznacza nowe ticket props z tym samym ID?','Nie musi wymusić remount','Zawsze usuwa draft','Zmienia typ komponentu','Zawsze anuluje fetch'],
['Po co jawny przycisk resetu?','Pozwala ustawić wybrane wartości bez losowego key','Zawsze tworzy nową bazę','Zamyka aplikację','Usuwa potrzebę callbacku'],
['Jak zmienić Map w stanie?','Kopia Map i zmiana kopii','Mutować starą w renderze','Object.assign({},map) i używać get','Ustawić map.length'],
['Czy nowa Map klonuje głęboko wpisy?','Nie','Tak','Tylko dla string','Tylko w React'],
['Dlaczego stringi jako wartości Notes są wygodne?','Są wartościami niemutowalnymi','Zamrażają Map','Automatycznie zapisują plik','Zapewniają unikalność ID'],
['Czy trzymanie Notes w rodzicu zapewnia trwałość po odmontowaniu rodzica?','Nie','Tak','Tylko z useState','Tylko z readonly'],
['Czy zmiana tytułu powinna zmieniać key rekordu?','Zwykle nie, ID nadal identyfikuje obiekt','Zawsze tak','Tylko w compact','Tylko bez CSS'],
['Co przechowuje selectedId?','Tożsamość wybranego rekordu','Kopię całego ticketu','Wszystkie notatki','Typ komponentu'],
['Co jeśli selectedId nie istnieje w tickets?','Wyliczony selected jest null','Zawsze użyj starego obiektu','Automatycznie wybiera ostatni','Zmienia key całej strony'],
['Czy reset i synchronizacja zmian serwera są tym samym problemem?','Nie','Tak','Tylko dla string','Tylko w React 19'],
['Co przetestować dla cache draftów?','A→B→A i odmontowanie odpowiedniego właściciela','Tylko pierwsze SSR','Tylko długość kodu','Wyłącznie kolory'],
['Czy surowy element z key dowodzi rekoncyliacji w DOM?','Nie','Tak','Tylko dla fragmentu','Tylko po stringify'],
['Czy pozycja JSX to miejsce na ekranie?','Nie','Tak','Tylko z grid','Tylko z flex'],
['Czy komponenty tego samego typu w gałęziach warunku mogą zachować stan?','Tak, jeśli to ta sama pozycja i tożsamość','Nie nigdy','Tylko z className','Tylko z textarea'],
['Jak chronić draft przed zmianą prezentacji?','Zachować stabilną strukturę i właściciela','Nadać nowy key co render','Przenieść wszystko do renderu','Resetować w efekcie compact'],
];
const quiz=concepts.map(row=>[...row,'Tożsamość instancji i miejsce przechowywania draftu wyznaczają czas życia danych. Zmiana prezentacji nie powinna przypadkowo zmieniać tej tożsamości.']);quiz.push(...require('./practice_form_06.cjs').quiz.slice(0,30).map(row=>['Powtórka resetu edytora: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const id of [0,1,2,43,99])for(const value of ['A','B',''])add('Zapis '+id+' '+JSON.stringify(value),`changeNote(notes,${id},${JSON.stringify(value)}).get(${id})??"brak"`,value||'Pusty napis');
for(const id of [0,1,2,43,99])add('Odczyt '+id,`noteFor(notes,${id})||"Pusty napis"`,id===0?'Zero':id===43?'Drugi':'Pusty napis');
for(const id of [0,1,2,43,99])add('Usunięcie '+id,`removeNote(notes,${id}).size`,id===0||id===43?1:2);
for(const [label,expr,a]of [['Mapa nie ta sama','changeNote(notes,1,"A")===notes',false],['Wejściowy rozmiar','notes.size',2],['Stara wartość','(()=>{changeNote(notes,0,"Nowa");return notes.get(0);})()','Zero'],['Stary wpis po delete','(()=>{removeNote(notes,0);return notes.has(0);})()',true],['Pusty wpis jest obecny','changeNote(notes,1,"").has(1)',true],['Brak po delete','removeNote(notes,0).has(0)',false],['Zachowany inny wpis','removeNote(notes,0).get(43)','Drugi'],['Nowy rozmiar','changeNote(notes,99,"A").size',3],['Zastąpienie rozmiar','changeNote(notes,0,"A").size',2],['Dwa zapisy','noteFor(changeNote(changeNote(notes,0,"A"),43,"B"),0)','A'],['Powrót ID','noteFor(changeNote(notes,43,"B"),0)','Zero'],['Nieistniejący odczyt','noteFor(notes,999).length',0],['Typ treści','typeof noteFor(notes,0)','string'],['Kolejność kluczy','Array.from(changeNote(notes,1,"A").keys()).join(",")','0,43,1'],['Ponowny zapis','noteFor(changeNote(changeNote(notes,0,"A"),0,"B"),0)','B']])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik magazynu notatek?',a,...wrong,'Map przechowuje tekst pod konkretnym ID. Kopia zachowuje pozostałe wpisy i nie mutuje wejścia; brak odczytujemy jako pusty string.',model+'\nconst notes:Notes=new Map([[0,"Zero"],[43,"Drugi"]]);console.log('+expr+');']);}
const project={'src/Notes.tsx':page,'src/Local.tsx':local,'src/Hidden.tsx':hidden,'src/main.tsx':'import {createRoot} from "react-dom/client";import {NotesPage} from "./Notes";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<NotesPage tickets={[{id:0,title:"API"},{id:43,title:"Logowanie"}]}/>);'};
module.exports={model,local,controlled,page,hidden,exercises,quiz,project};
