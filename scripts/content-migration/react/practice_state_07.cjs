const model='export type Priority="LOW"|"MEDIUM"|"HIGH"|"CRITICAL";\nexport type Ticket=Readonly<{id:number;title:string;priority:Priority;createdAt:string;assignee:string|null}>;\nexport type SortKey="id"|"title"|"priority"|"createdAt";\nexport type Direction="asc"|"desc";\nexport type SortState=Readonly<{key:SortKey;direction:Direction}>;';
const helpers='export const PRIORITY_RANK:Record<Priority,number>={LOW:0,MEDIUM:1,HIGH:2,CRITICAL:3};\ntype Comparator=(a:Ticket,b:Ticket)=>number;\nconst COMPARATORS:Record<SortKey,Comparator>={id:(a,b)=>a.id-b.id,title:(a,b)=>a.title.localeCompare(b.title,"pl"),priority:(a,b)=>PRIORITY_RANK[a.priority]-PRIORITY_RANK[b.priority],createdAt:(a,b)=>Date.parse(a.createdAt)-Date.parse(b.createdAt)};\nexport function sortTickets(tickets:readonly Ticket[],sort:SortState):Ticket[]{const sign=sort.direction==="asc"?1:-1;return tickets.toSorted((a,b)=>COMPARATORS[sort.key](a,b)*sign);}\nexport function nextSort(current:SortState,key:SortKey):SortState{return current.key===key?{key,direction:current.direction==="asc"?"desc":"asc"}:{key,direction:"asc"};}\nexport function compareNullableText(a:string|null,b:string|null,direction:Direction):number{if(a===null&&b===null)return 0;if(a===null)return 1;if(b===null)return -1;return a.localeCompare(b,"pl")*(direction==="asc"?1:-1);}';
const header='export function SortHeader({label,column,sort,onSort}:{label:string;column:SortKey;sort:SortState;onSort:(key:SortKey)=>void}){const active=sort.key===column;return <th scope="col" aria-sort={active?(sort.direction==="asc"?"ascending":"descending"):undefined}><button type="button" onClick={()=>onSort(column)}>{label} <span aria-hidden="true">{active?(sort.direction==="asc"?"▲":"▼"):"↕"}</span></button></th>;}';
const main='import {useState} from "react";\n'+model+'\n'+helpers+'\n'+header+'\nexport function Main({tickets}:{tickets:readonly Ticket[]}){const [query,setQuery]=useState("");const [sort,setSort]=useState<SortState>({key:"createdAt",direction:"desc"});const filtered=tickets.filter(ticket=>ticket.title.toLowerCase().includes(query.trim().toLowerCase()));const visible=sortTickets(filtered,sort);const columns=[{key:"id",label:"Id"},{key:"title",label:"Tytuł"},{key:"priority",label:"Priorytet"},{key:"createdAt",label:"Utworzono"}] as const;return <main><label>Szukaj w tytule<input value={query} onChange={event=>setQuery(event.currentTarget.value)}/></label><table><thead><tr>{columns.map(column=><SortHeader key={column.key} label={column.label} column={column.key} sort={sort} onSort={key=>setSort(current=>nextSort(current,key))}/>)}</tr></thead><tbody>{visible.length===0?<tr><td colSpan={4}>Brak wyników</td></tr>:visible.map(ticket=><tr key={ticket.id}><td>{ticket.id}</td><td>{ticket.title}</td><td>{ticket.priority}</td><td><time dateTime={ticket.createdAt}>{new Date(ticket.createdAt).toLocaleDateString("pl-PL",{timeZone:"UTC"})}</time></td></tr>)}</tbody></table></main>;}';
const sample='export const tickets:readonly Ticket[]=[{id:3,title:"Łódź",priority:"HIGH",createdAt:"2026-10-01T12:00:00.000Z",assignee:null},{id:1,title:"Ala",priority:"CRITICAL",createdAt:"2026-10-03T12:00:00.000Z",assignee:"Anna"},{id:4,title:"Śląsk",priority:"LOW",createdAt:"2026-10-02T12:00:00.000Z",assignee:"Ola"},{id:2,title:"Las",priority:"MEDIUM",createdAt:"2026-10-04T12:00:00.000Z",assignee:"Piotr"}];';
const exercises=[
 ['Zaprojektuj SortKey, Direction i SortState jako opis kolejności.','Posortowana lista nie jest drugim stanem.',model],
 ['Zdefiniuj mapę rang LOW=0, MEDIUM=1, HIGH=2, CRITICAL=3.','Porządek biznesowy różni się od alfabetu.',model+'\n'+helpers],
 ['Napisz komparator liczbowych id.','Nie porównuj ich jak tekstu.','export function compareId(a:{id:number},b:{id:number}):number{return a.id-b.id;}'],
 ['Napisz komparator polskich tytułów.','Użyj localeCompare z pl; nie zakładaj zawsze wyniku dokładnie -1 lub 1.','export function compareTitle(a:{title:string},b:{title:string}):number{return a.title.localeCompare(b.title,"pl");}'],
 ['Porównuj priorytety według rang, a nie nazw.','Mapa powinna wymagać wszystkich priorytetów.',model+'\n'+helpers],
 ['Porównuj poprawne daty ISO jako momenty czasu przez Date.parse.','To obsłuży także różne offsety strefowe.',model+'\n'+helpers],
 ['Wyjaśnij warunek bezpiecznego leksykograficznego sortowania dat.','Samo słowo ISO nie gwarantuje identycznego zapisu.','Porównanie napisów odpowiada chronologii dla spójnego znormalizowanego formatu, strefy i precyzji. Różne offsety albo formaty mogą naruszyć tę własność. Dla poprawnych różnych zapisów momentu porównuj wartości czasu.'],
 ['Utwórz Record<SortKey,Comparator> z funkcjami każdej kolumny.','Nowa kolumna sortowania powinna wymagać komparatora.',model+'\n'+helpers],
 ['Napisz sortTickets na readonly Ticket[] z toSorted.','Nie zmieniaj props ani stanu wejściowego.',model+'\n'+helpers],
 ['Dodaj kierunek desc przez znak wyniku komparatora.','Znak stosuj do wyniku porównania.',model+'\n'+helpers],
 ['Napisz nextSort: nowa kolumna asc, ta sama zmienia kierunek.','Zwróć nowy opis zamiast mutować current.',model+'\n'+helpers],
 ['Wyjaśnij pierwszy klik w aktualnie aktywną createdAt desc.','Reguła nowej kolumny nie dotyczy już aktywnej.','Klik w createdAt zmieni kierunek na asc. Pierwszy klik w inną kolumnę, np. title, ustawi title asc. Testy powinny odróżniać te sytuacje.'],
 ['Zbuduj SortHeader z prawdziwym button wewnątrz th.','Klawiatura ma dostać natywną akcję.',model+'\n'+header],
 ['Dodaj aria-sort do aktywnego th, a strzałkę oznacz jako dekoracyjną.','Stan należy do nagłówka, nie do samego znaku.',model+'\n'+header],
 ['Połącz onSort z funkcyjnym setSort(current=>nextSort(...)).','Nowy opis zależy od poprzedniego.',main],
 ['Zacznij widok od createdAt desc i nie przechowuj sorted w stanie.','Najnowsze dane startują na początku.',main],
 ['Połącz filtrowanie tytułów, potem sortowanie całego wyniku.','Nie sortuj wyłącznie arbitralnego fragmentu źródła.',main],
 ['Dodaj pusty stan poprawnej tabeli czterokolumnowej.','Użyj td z colSpan=4.',main],
 ['Wyświetl datę z jawną polityką strefy czasowej.','Sortowanie momentów i formatowanie do wyświetlenia to odrębne kroki.',main],
 ['Zaprojektuj nullable comparator, gdzie null jest na końcu w obu kierunkach.','Nie odwracaj decyzji null-last razem ze znakiem tekstu.',model+'\n'+helpers],
 ['Przetestuj null vs tekst dla asc i desc.','Dla obu null zachowaj remis.',model+'\n'+helpers+'\nconsole.log(compareNullableText(null,"Anna","asc"));\nconsole.log(compareNullableText(null,"Anna","desc"));\nconsole.log(compareNullableText(null,null,"asc"));'],
 ['Wyjaśnij, co zrobić z niepoprawną datą w danych.','NaN nie daje sensownej reguły porządku.','Ustal walidację wejścia lub jawny porządek brakujących/niepoprawnych dat. Helper tej lekcji przyjmuje poprawne daty. Nie zakładaj, że Date.parse dowolnego tekstu da poprawny moment.'],
 ['Zaplanuj test stabilnego remisu przy równych priorytetach.','ToSorted jest stabilne w obsługującym je współczesnym JS.','Dwa rekordy o tej samej randze zachowują względną kolejność wejściową, jeśli komparator daje 0. Jeśli potrzebujesz niezależnego deterministycznego tie-breakera, dodaj jawnie porównanie id.'],
 ['Dodaj opcjonalny tie-breaker id dla tego samego priorytetu.','Zdecyduj, czy id ma zachowywać własny kierunek.','type Ticket={id:number;rank:number};\nexport function compare(a:Ticket,b:Ticket):number{return a.rank-b.rank||a.id-b.id;}\n// W tym wariancie oba porównania są rosnące.'],
 ['Wykaż błędem TS niedozwoloną kolumnę assignee w czterokolumnowym SortKey.','Dodatkowy helper nullable nie rozszerza automatycznie API sortowania.',model+'\n// @ts-expect-error Assignee nie jest częścią SortKey\nconst key:SortKey="assignee";'],
 ['Wykaż błędem TS brak rangi nowego priorytetu w Record.','Mapa wymaga wpisu każdej wartości.','type Priority="LOW"|"HIGH"|"URGENT";\n// @ts-expect-error Brak URGENT\nconst ranks:Record<Priority,number>={LOW:0,HIGH:1};'],
 ['Zapisz JSX/JSDoc sortowanie liczb na kopii.','Nie wymaga mutacji otrzymanej tablicy.','/** @param {readonly number[]} values */\nexport function sorted(values){return [...values].sort((a,b)=>a-b);}'],
 ['Zaplanuj test klawiatury i aria-sort po dwóch kliknięciach.','Sprawdź stan aktywnej kolumny i kolejność danych.','Tab do przycisku nieaktywnej kolumny, aktywacja: asc i rosnąca kolejność. Druga aktywacja: desc. Aria-sort jest na odpowiednim th, a fokus pozostaje rozpoznawalny. Sprawdź osobno aktywną startowo datę.'],
 ['Zaplanuj test bez mutacji na zamrożonej liście i rekordach.','Sam nowy napis sort nie dowodzi bezpieczeństwa danych.','Zamroź wejście, posortuj w kilku kierunkach i porównaj kolejność oryginału. Tablica wyniku jest nowa, ale rekordy zachowują referencje. Powtórz wywołanie z tymi samymi wejściami.'],
 ['Oddaj Main z polskimi tytułami, rangami, datami i dostępnymi nagłówkami.','W stanie jest opis sortowania; kolejność jest wyliczana.',main],
];
const quiz=`
Co przechowywać jako stan sortowania?|Opis key i direction|Zawsze osobną kopię całej posortowanej listy|Wyłącznie pierwszy rekord|Wszystkie klasy CSS|Lista może być wyliczana z danych i opisu.
Czy posortowana lista musi mieć własny setter?|Nie|Tak|Tylko w TSX|Tylko dla priority|To dane pochodne.
Co robi klik w nową kolumnę według przyjętej reguły?|Ustawia ją rosnąco|Zawsze malejąco|Usuwa sort|Zachowuje poprzednią kolumnę|Reguła jest jawna.
Co robi klik w tę samą kolumnę?|Odwraca kierunek|Zawsze ustawia asc|Usuwa dane|Zmienia query|Aktualizacja zależy od dotychczasowego opisu.
Co zrobi klik w startowe createdAt desc?|Ustawi createdAt asc|Pozostawi desc|Ustawi title asc|Usunie daty|Kolumna już jest aktywna.
Czy funkcja nextSort powinna mutować current?|Nie|Tak|Tylko w JSX|Tylko dla desc|Tworzy nowy stan.
Czy setSort(current=>nextSort(current,key)) jest właściwe dla zmiany zależnej od stanu?|Tak|Nie|Tylko w TSX|Tylko z useMemo|Updater otrzymuje właściwy poprzedni opis.
Jak porównywać liczbowe id?|Różnicą liczb|Zawsze jak teksty|Długością stringa|Kolorem wiersza|Komparator liczbowy daje właściwy porządek.
Czy alfabetyczne porównanie stringów 10 i 2 daje zawsze kolejność liczbową?|Nie|Tak|Tylko w TSX|Tylko w JSX|Tekst i liczba mają inne reguły.
Jak porównywać polskie tytuły?|LocaleCompare z pl|Wyłącznie operatorem < bez decyzji|Po liczbie liter|Po id zawsze|Porządek językowy uwzględnia lokalne reguły.
Czy localeCompare musi zwrócić dokładnie -1 lub 1?|Nie|Tak|Tylko w TSX|Tylko dla polskiego|Kontrakt mówi o znaku, a nie konkretnej wartości.
Co oznacza ujemny wynik komparatora?|A przed B w porządku rosnącym|A po B|Zawsze błąd|Remis|Znak opisuje względny porządek.
Co oznacza zero wyniku?|Remis według kryterium|Usunięcie rekordu|Błąd zawsze|Brak danych|Stabilne sortowanie zachowa względny porządek remisujących.
Dlaczego priorytety potrzebują mapy rang?|Kolejność biznesowa nie jest alfabetem|Nie można sortować tekstów|React wymaga rang|CSS wymaga liczb|LOW→MEDIUM→HIGH→CRITICAL jest jawnym porządkiem.
Jaka ranga ma CRITICAL w rozwiązaniu?|3|0|1|2|To najwyższy priorytet.
Jaka ranga ma LOW?|0|3|1|2|To początek porządku.
Czy desc można otrzymać odwróceniem znaku komparatora?|Tak dla standardowego porównania|Nie|Tylko w JSX|Tylko dla id|Mnożenie przez -1 odwraca porządek.
Czy null-last w obu kierunkach można bezmyślnie odwrócić razem z tekstem?|Nie|Tak|Tylko w TSX|Tylko przy assignee|Decyzja o null powinna poprzedzać znak części tekstowej.
Co zwraca comparator dwóch null w rozwiązaniu?|0|1|-1|NaN|Oba braki są remisem.
Czy Date.parse porównuje momenty przy poprawnych różnych offsetach?|Tak|Nie|Tylko w TSX|Tylko przy Z|Wynik jest liczbą czasu.
Czy każdy dowolny napis ISO można sortować tekstowo i gwarantować chronologię?|Nie|Tak|Tylko w JSX|Tylko z localeCompare|Liczy się spójny format, strefa i precyzja.
Czy niepoprawny tekst daty może dać NaN?|Tak|Nie|Tylko w TSX|Tylko przy null|Trzeba ustalić walidację danych.
Czy NaN jest dobrym zamiennikiem przemyślanego porządku błędnych dat?|Nie|Tak|Tylko w JSX|Tylko dla desc|Potrzebna jest jawna polityka.
Czy formatowanie daty dla użytkownika i sortowanie momentu to jedno zadanie?|Nie|Tak|Tylko w TSX|Tylko w Polsce|Wyświetlany tekst może zależeć od strefy i locale.
Po co jawne timeZone UTC w przykładzie daty?|Dla przewidywalnego wyświetlania zgodnego z przyjętą polityką|By zmienić dane źródłowe|By przyspieszyć każdą listę|By wyłączyć sort|To decyzja prezentacji.
Czy toSorted mutuje wejściowy kontener?|Nie|Tak|Tylko w TSX|Tylko dla id|Zwraca kopię z inną kolejnością.
Czy sort na props jest bezpieczne w renderze?|Nie|Tak|Tylko w JSX|Tylko z komparatorem|Mutuje dane rodzica.
Jak użyć sort bez zmiany źródła?|Najpierw skopiować kontener|Dodać key|Zmienić nazwę props|Uruchomić dwa razy|Świeża kopia może być lokalnie sortowana.
Czy toSorted kopiuje rekordy głęboko?|Nie|Tak|Tylko w TSX|Tylko dla dat|Nowa jest tablica, nie każdy obiekt.
Czy nowy porządek wymaga nowych id rekordów?|Nie|Tak|Tylko w JSX|Tylko przy desc|Tożsamość danych nie zmienia się z pozycją.
Jaki key zachować po sortowaniu?|Ticket.id|Nowy indeks pozycji|Losowy|Tekst kierunku|Stabilna tożsamość chroni powiązania instancji.
Co daje Record<SortKey,Comparator>?|Komparator dla każdej dozwolonej kolumny|Automatyczną walidację wszystkich dat|Nowy stan|CSS sortowania|Mapa jest kompletna względem unii.
Co daje Record<Priority,number>?|Rangę dla każdego priorytetu|Automatyczny porządek z alfabetu|Nowe id|Stan query|Dodanie priorytetu wymaga uzupełnienia mapy.
Czy osobny compareNullableText rozszerza automatycznie SortKey?|Nie|Tak|Tylko w TSX|Tylko przy assignee|API kolumn jest osobną jawną listą.
Gdzie umieścić aria-sort?|Na th aktywnej kolumny|Wyłącznie na span ze strzałką|Na body|Na key wiersza|Atrybut opisuje stan nagłówka kolumny.
Czy zwykłe th z onClick daje natywną obsługę klawiatury button?|Nie|Tak|Tylko w TSX|Tylko przy scope|Wewnątrz powinien być semantyczny przycisk.
Po co scope=col?|Wskazuje nagłówek kolumny|Zmienia kierunek|Włącza focus|Tworzy key|Semantyka tabeli pomaga interpretować dane.
Czy dekoracyjna strzałka powinna dublować nazwę akcji w czytniku?|Nie musi; może być aria-hidden|Zawsze musi być czytana|Tylko w TSX|Tylko dla asc|Stan jest opisany semantycznie przez nagłówek.
Jakie wartości odpowiadają aria-sort kierunkom?|Ascending i descending w poprawnej pisowni atrybutu|Asc i desc jako jedyne wartości ARIA|Up i down|Rosnąco i malejąco jako tokeny|W JSX używa się wartości ascending/descending.
Czy kierunek w danych SortState i token aria-sort są identycznymi napisami?|Nie|Tak|Tylko w JSX|Tylko w TSX|Stan asc tłumaczy się na ascending.
Czy filtr powinien działać przed sortowaniem widoku?|Tak w przyjętym pipeline|Nigdy|Tylko w TSX|Tylko dla priority|Sortujemy zbiór dopasowanych rekordów.
Czy filtr i sort muszą zmieniać źródłową listę?|Nie|Tak|Tylko w JSX|Tylko dla dat|To przekształcenia widoku.
Czy dodanie nowego pasującego rekordu powinno wpłynąć na wyliczoną kolejność?|Tak|Nie|Tylko po setSorted|Tylko po efekcie|Lista przelicza się z aktualnego źródła.
Czy posortowana lista w drugim stanie wymagałaby dodatkowej synchronizacji?|Tak|Nie|Tylko w TSX|Tylko z filtrem|To zbędne dublowanie wyniku.
Czy remisy muszą zawsze być rozwiązywane id?|Nie|Tak|Tylko w JSX|Tylko dla priorytetów|Stabilny porządek wejścia może być właściwym wymaganiem.
Kiedy dodać tie-breaker id?|Gdy wymagany jest taki deterministyczny porządek remisów|Zawsze bez decyzji|Nigdy|Tylko przy błędzie TS|To część kontraktu sortowania.
Czy tie-breaker też powinien mieć świadomie ustalony kierunek?|Tak|Nie|Tylko w TSX|Tylko przy null|Może podlegać wspólnemu znakowi albo osobnej polityce.
Czy komparator powinien być czystą funkcją?|Tak|Nie|Tylko w JSX|Tylko w StrictMode|Sortowanie może wywoływać go wielokrotnie.
Czy wolno mutować ticket w komparatorze?|Nie|Tak|Tylko w TSX|Tylko dla priority|Porównywanie nie jest edycją danych.
Czy komparator musi być wywołany dokładnie raz na rekord?|Nie|Tak|Tylko w JSX|Tylko z toSorted|Algorytm może porównywać wiele razy.
Czy log biznesowy w komparatorze jest dobrym efektem jednej akcji sortowania?|Nie|Tak|Tylko w TSX|Tylko przy click|Liczba porównań nie jest liczbą akcji użytkownika.
Czy test zamrożonych danych pomaga wykryć sort na źródle?|Tak|Nie|Tylko w JSX|Tylko dla liczb|Mutacja kontenera jest łatwiejsza do ujawnienia.
Czy test samych nagłówków dowodzi poprawnego sortowania priorytetów?|Nie|Tak|Tylko w TSX|Tylko z Record|Należy sprawdzić rzeczywistą kolejność rekordów.
Czy test kierunku powinien objąć dwie aktywacje tej samej kolumny?|Tak|Nie|Tylko dla id|Tylko w JSX|To weryfikuje regułę przełączania.
Czy startowa data desc jest tym samym scenariuszem co nieaktywna kolumna?|Nie|Tak|Tylko w TSX|Tylko w Vite|Pierwszy klik zachowuje się inaczej zgodnie z nextSort.
Czy readonly parameter pomoże odrzucić tickets.sort w TS?|Tak|Nie|Tylko w JSX|Tylko po freeze|Typ kontenera nie udostępnia mutującej metody.
Czy typy ES2023 zapewniają toSorted w każdym starym runtime?|Nie|Tak|Tylko w TSX|Tylko z React|Potrzebna jest obsługa środowiska lub alternatywa.
Czy JSX i TSX zmieniają algorytm sortowania przez same typy?|Nie|Tak|Tylko dla pl|Tylko przy priority|Typowanie nie zmienia runtime logiki.
Czy zero wyników powinno zachować poprawną strukturę tabeli?|Tak|Nie|Tylko w TSX|Tylko przy sort|Pusty stan też jest częścią UI.
Co potwierdza ukończenie sortowania?|Dwa kierunki, poprawne porządki domeny, dostępne nagłówki i brak mutacji|Samo kliknięcie|Wyłącznie strzałka|Tylko brak błędów TS|Wymagania obejmują dane i obsługę.
`.trim().split('\n').map(line=>line.split('|'));
const cases=[
 ['Id rosnąco','ids(sortTickets(tickets,{key:"id",direction:"asc"}))','1,2,3,4','4,3,2,1','3,1,4,2','1,3,2,4'],
 ['Id malejąco','ids(sortTickets(tickets,{key:"id",direction:"desc"}))','4,3,2,1','1,2,3,4','3,1,4,2','2,1,4,3'],
 ['Tytuł polski asc','ids(sortTickets(tickets,{key:"title",direction:"asc"}))','1,2,3,4','4,3,2,1','3,1,4,2','1,3,2,4'],
 ['Tytuł polski desc','ids(sortTickets(tickets,{key:"title",direction:"desc"}))','4,3,2,1','1,2,3,4','3,1,4,2','2,4,1,3'],
 ['Priorytet asc','ids(sortTickets(tickets,{key:"priority",direction:"asc"}))','4,2,3,1','1,3,2,4','1,2,3,4','3,1,4,2'],
 ['Priorytet desc','ids(sortTickets(tickets,{key:"priority",direction:"desc"}))','1,3,2,4','4,2,3,1','4,3,2,1','3,1,4,2'],
 ['Daty asc','ids(sortTickets(tickets,{key:"createdAt",direction:"asc"}))','3,4,1,2','2,1,4,3','1,2,3,4','3,1,4,2'],
 ['Daty desc','ids(sortTickets(tickets,{key:"createdAt",direction:"desc"}))','2,1,4,3','3,4,1,2','4,3,2,1','3,1,4,2'],
 ['Nieaktywna title','nextSort(current,"title").direction','asc','desc','ascending','undefined'],
 ['Ta sama id asc','nextSort(current,"id").direction','desc','asc','descending','undefined'],
 ['Ta sama id desc','nextSort({key:"id",direction:"desc"},"id").direction','asc','desc','none','undefined'],
 ['Nowy priorytet','nextSort(current,"priority").key','priority','id','title','createdAt'],
 ['Nowa data','nextSort(current,"createdAt").direction','asc','desc','none','undefined'],
 ['Dwa kliki id','nextSort(nextSort(current,"id"),"id").direction','asc','desc','none','undefined'],
 ['Dwa kliki nowej title','nextSort(nextSort(current,"title"),"title").direction','desc','asc','none','undefined'],
 ['Stary opis pozostaje','current.direction','asc','desc','none','undefined'],
 ['Ranga HIGH','PRIORITY_RANK.HIGH','2','3','1','0'],
 ['Ranga MEDIUM','PRIORITY_RANK.MEDIUM','1','2','3','0'],
 ['Porównanie rang LOW i CRITICAL','PRIORITY_RANK.LOW-PRIORITY_RANK.CRITICAL','-3','3','0','1'],
 ['Porównanie rang HIGH i MEDIUM','PRIORITY_RANK.HIGH-PRIORITY_RANK.MEDIUM','1','-1','0','2'],
 ['Null ostatni asc','Math.sign(compareNullableText(null,"Anna","asc"))','1','-1','0','NaN'],
 ['Null ostatni desc','Math.sign(compareNullableText(null,"Anna","desc"))','1','-1','0','NaN'],
 ['Tekst przed null asc','Math.sign(compareNullableText("Anna",null,"asc"))','-1','1','0','NaN'],
 ['Tekst przed null desc','Math.sign(compareNullableText("Anna",null,"desc"))','-1','1','0','NaN'],
 ['Dwa braki','compareNullableText(null,null,"desc")','0','1','-1','NaN'],
 ['Polskie L i Ł','Math.sign("Las".localeCompare("Łódź","pl"))','-1','1','0','NaN'],
 ['Polskie Ł i Ś','Math.sign("Łódź".localeCompare("Śląsk","pl"))','-1','1','0','NaN'],
 ['Ta sama nazwa','"API".localeCompare("API","pl")','0','1','-1','NaN'],
 ['Wcześniejszy moment z offsetem','Math.sign(Date.parse("2026-01-01T10:00:00+02:00")-Date.parse("2026-01-01T09:00:00Z"))','-1','1','0','NaN'],
 ['Ten sam moment w dwóch zapisach','Date.parse("2026-01-01T10:00:00+01:00")===Date.parse("2026-01-01T09:00:00Z")','true','false','undefined','NaN'],
 ['Oryginalna kolejność po wywołaniu','(()=>{sortTickets(tickets,{key:"id",direction:"asc"});return ids(tickets);})()','3,1,4,2','1,2,3,4','4,3,2,1','2,1,4,3'],
 ['Nowy kontener','Object.is(sortTickets(tickets,current),tickets)','false','true','undefined','null'],
 ['Współdzielony pierwszy rekord po id asc','sortTickets(tickets,current)[0]===tickets[1]','true','false','undefined','null'],
 ['Pusta lista','sortTickets([],current).length','0','1','4','NaN'],
 ['Najwyższy priorytet pierwszy','sortTickets(tickets,{key:"priority",direction:"desc"})[0].priority','CRITICAL','HIGH','MEDIUM','LOW'],
 ['Najniższy priorytet pierwszy','sortTickets(tickets,{key:"priority",direction:"asc"})[0].priority','LOW','MEDIUM','HIGH','CRITICAL'],
 ['Filtr potem sort','ids(sortTickets(tickets.filter(t=>t.priority!=="LOW"),current))','1,2,3','1,2,3,4','3,1,2','4,2,3'],
 ['Filtr tytułu','ids(sortTickets(tickets.filter(t=>t.title.toLowerCase().includes("l")),current))','1,2,4','1,2,3,4','3','2,3'],
 ['Zmiana kolumny po desc','nextSort({key:"title",direction:"desc"},"priority").direction','asc','desc','none','undefined'],
 ['Klik aktywnej daty startowej','nextSort({key:"createdAt",direction:"desc"},"createdAt").direction','asc','desc','none','undefined'],
];
const codeBase=model+'\n'+helpers+'\n'+sample+'\nconst current:SortState={key:"id",direction:"asc"};function ids(items:readonly Ticket[]):string{return items.map(ticket=>ticket.id).join(",");}';
for(const [label,expr,a,b,c,d] of cases)quiz.push([`${label}: co wypisze sortowanie lub zmiana opisu?`,a,b,c,d,'Porządek wyznacza właściwy komparator; nextSort przełącza tę samą kolumnę i zaczyna nową od asc. Źródło pozostaje bez zmian.',codeBase+'\nconsole.log('+expr+');']);
module.exports={exercises,quiz,model,helpers,header,main,sample};
