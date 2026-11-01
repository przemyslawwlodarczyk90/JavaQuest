const counter='import {useState} from "react";\nexport function Counter(){const [count,setCount]=useState(0);return <button type="button" onClick={()=>setCount(count+1)}>Licznik: {count}</button>;}';
const description='import {useState} from "react";\nexport function TicketDescription({text}:{text:string}){const [expanded,setExpanded]=useState(false);const isLong=text.length>120;const shown=expanded||!isLong?text:`${text.slice(0,120)}…`;return <div><p>{shown}</p>{isLong&&<button type="button" aria-expanded={expanded} onClick={()=>setExpanded(!expanded)}>{expanded?"Zwiń":"Pokaż całość"}</button>}</div>;}';
const dashboard='import {useState} from "react";\ntype Ticket={id:number;title:string;description:string};\nexport function TicketTable({tickets,selectedId,onSelect}:{tickets:readonly Ticket[];selectedId:number|null;onSelect:(id:number)=>void}){return <table><thead><tr><th scope="col">Id</th><th scope="col">Tytuł</th></tr></thead><tbody>{tickets.length===0?<tr><td colSpan={2}>Brak ticketów</td></tr>:tickets.map(ticket=><tr key={ticket.id} className={ticket.id===selectedId?"row row--selected":"row"}><td>{ticket.id}</td><td><button type="button" aria-pressed={ticket.id===selectedId} onClick={()=>onSelect(ticket.id)}>{ticket.title}</button></td></tr>)}</tbody></table>;}\nexport function TicketDescription({text}:{text:string}){const [expanded,setExpanded]=useState(false);const isLong=text.length>120;return <div><p>{expanded||!isLong?text:`${text.slice(0,120)}…`}</p>{isLong&&<button type="button" aria-expanded={expanded} onClick={()=>setExpanded(!expanded)}>{expanded?"Zwiń":"Pokaż całość"}</button>}</div>;}\nexport function TicketCard({ticket,onClear}:{ticket:Ticket|null;onClear:()=>void}){if(ticket===null)return <p>Wybierz ticket z listy</p>;return <aside><h2>{ticket.title}</h2><TicketDescription key={ticket.id} text={ticket.description}/><button type="button" onClick={onClear}>Odznacz</button></aside>;}\nexport function Main({tickets}:{tickets:readonly Ticket[]}){const [selectedId,setSelectedId]=useState<number|null>(null);const selected=tickets.find(ticket=>ticket.id===selectedId)??null;return <main><TicketTable tickets={tickets} selectedId={selectedId} onSelect={setSelectedId}/><TicketCard ticket={selected} onClear={()=>setSelectedId(null)}/></main>;}';
const exercises=[
 ['Napisz Counter z początkowym zero i przyciskiem +1.','Stan aktualizuje setter, nie przypisanie lokalnej zmiennej.',counter],
 ['Wyjaśnij, dlaczego let count=0 wewnątrz Counter nie zapewnia pamięci React.','Zmiana zmiennej i render to dwa różne mechanizmy.','Zmienna lokalna powstaje od nowa przy kolejnym wywołaniu funkcji. Jej przypisanie nie informuje Reacta o potrzebie aktualizacji UI. UseState przechowuje wartość dla instancji i dostarcza setter.'],
 ['Utwórz selectedId jako number|null, początkowo null.','Samo null nie opisuje późniejszej liczby.','import {useState} from "react";\nexport function Selection(){const [selectedId,setSelectedId]=useState<number|null>(null);return <div><p>{selectedId??"Brak wyboru"}</p><button type="button" onClick={()=>setSelectedId(42)}>Wybierz</button><button type="button" onClick={()=>setSelectedId(null)}>Odznacz</button></div>;}'],
 ['Wylicz selected przez find na aktualnych props, nie przez drugi stan obiektu.','Brak dopasowania zamień na null.',dashboard],
 ['Zbuduj tabelę oznaczającą wyłącznie rekord o selectedId.','Użyj ścisłego porównania id, także gdy wynosi 0.',dashboard],
 ['Udostępnij wybór przez natywny przycisk tytułu z aria-pressed.','Zwykły tr nie daje automatycznej obsługi klawiatury.',dashboard],
 ['Przekaż setSelectedId do onSelect:(id:number)=>void bez zbędnego opakowania.','Setter dopuszcza ustawienie liczby.',dashboard],
 ['Zbuduj TicketCard z pustym stanem dla null i akcją Odznacz.','Nie dereferencjuj null przed sprawdzeniem.',dashboard],
 ['Umieść stan wyboru w Main, wspólnym rodzicu tabeli i karty.','Oba widoki potrzebują jednej wartości źródłowej.',dashboard],
 ['Napisz TicketDescription z progiem 120 znaków i przyciskiem rozwijania.','Stan expanded pozostaje lokalny.',description],
 ['Podaj zachowanie opisu dla długości 0, 120 i 121 znaków.','Granica to >120, nie >=120.','0 i 120: pełny tekst, bez przycisku rozwijania. 121: pierwsze 120 znaków i wielokropek, przycisk Pokaż całość. Po rozwinięciu pełny tekst oraz Zwiń. Liczymy jednostki string.length zgodnie z założeniem ćwiczenia.'],
 ['Dodaj aria-expanded odpowiadające stanowi opisu.','Atrybut powinien opisywać bieżący stan, nie następną akcję.',description],
 ['Ustal, jak resetować expanded przy wyborze innego ticketu.','Zmiana props nie resetuje automatycznie stanu tej samej instancji.',dashboard],
 ['Wyjaśnij rolę key={ticket.id} na TicketDescription w rozwiązaniu.','Nowa tożsamość dziecka oznacza nowy stan początkowy.','Przy zmianie id React traktuje opis jako inną instancję, więc expanded wraca do false. Zmiana tekstu przy tym samym id sama w sobie tego nie robi. Key wynika z oczekiwanego zachowania, nie jest losowy.'],
 ['Popraw hook wywołany po warunkowym return w karcie.','Hook musi być osiągany w tej samej kolejności.','import {useState} from "react";\nexport function Card({title}:{title:string|null}){const [expanded,setExpanded]=useState(false);if(title===null)return <p>Wybierz ticket</p>;return <button type="button" aria-expanded={expanded} onClick={()=>setExpanded(!expanded)}>{title}</button>;}'],
 ['Zamiast hooka w pętli utwórz osobny komponent dla każdego niezależnego licznika.','Każda instancja ma własny stan.','import {useState} from "react";\nfunction Counter({label}:{label:string}){const [count,setCount]=useState(0);return <button type="button" onClick={()=>setCount(count+1)}>{label}: {count}</button>;}\nexport function Counters(){return <div>{["A","B"].map(label=><Counter key={label} label={label}/>)}</div>;}'],
 ['Zapisz typ useState dla pustej tablicy Ticket[].','Wartość [] nie zawiera przykładowego elementu.','import {useState} from "react";\ntype Ticket={id:number;title:string};\nexport function Queue(){const [tickets]=useState<Ticket[]>([]);return <p>Ticketów: {tickets.length}</p>;}'],
 ['Zapisz stan Status jako unia OPEN/CLOSED z początkiem OPEN.','Nie rozszerzaj API do dowolnego tekstu.','import {useState} from "react";\ntype Status="OPEN"|"CLOSED";\nexport function StatusSwitch(){const [status,setStatus]=useState<Status>("OPEN");return <button type="button" onClick={()=>setStatus(status==="OPEN"?"CLOSED":"OPEN")}>{status}</button>;}'],
 ['Wykaż błędem TS, że useState(null) bez generyka nie dopuszcza 42 przy strictNullChecks.','To test brakującego typu, nie obejście przez any.','import {useState} from "react";\nexport function Example(){const [value,setValue]=useState(null);function select(){\n// @ts-expect-error Wywnioskowano wyłącznie null\nsetValue(42);\n}return <button type="button" onClick={select}>{value??"Brak"}</button>;}'],
 ['Zdiagnozuj inicjalizację useState(initialId), gdy initialId zmieni się w props.','Argument początkowy nie jest ciągłą synchronizacją.','Przy tej samej instancji nowa wartość initialId nie zastępuje automatycznie zapamiętanego stanu. Jeśli rodzic ma stale sterować wyborem, użyj kontrolowanego props. Reset instancji przez key to inne świadome zachowanie.'],
 ['Wyjaśnij, czemu stan wyboru nie powinien być globalną zmienną modułu.','Dwie instancje widoku mają być niezależne.','Zmienna modułu byłaby współdzielona i nie uruchamiałaby sama renderów. UseState wiąże dane z konkretną instancją w drzewie. Dwie instancje Main mogą mieć niezależny wybór.'],
 ['Przygotuj plan testu dwóch Counter obok siebie.','Kliknięcie jednego nie powinno zmieniać drugiego.','Początek A=0 i B=0. Kliknij A dwa razy: A=2, B=0. Kliknij B raz: A=2, B=1. Następnie zresetuj jedną instancję przez świadomy remount i sprawdź jej wartość początkową.'],
 ['Zaprojektuj test zaktualizowanych danych po wybraniu id=42.','Find powinno pobrać aktualny rekord z nowych props.','Wybierz 42, następnie zastąp dane rekordem 42 z nowym tytułem. Karta powinna pokazać nowy tytuł bez ponownego klikania. To przewaga przechowywania id zamiast niezależnej starej referencji obiektu.'],
 ['Obsłuż usunięcie wybranego rekordu z props bez dereferencji undefined.','Find może nie znaleźć danych.',dashboard],
 ['Wyjaśnij różnicę brakującej karty a samoczynnego wyczyszczenia selectedId.','Wynik wyliczenia nie musi modyfikować źródłowego stanu.','Jeśli find nic nie zwróci, selected ma null i karta pokazuje pusty stan. SelectedId może nadal mieć poprzednią liczbę. To nie jest automatyczny setter. Decyzję o czyszczeniu przy usuwaniu danych projektuje się osobno.'],
 ['Zbuduj wybór obsługujący id=0 bez pomylenia go z brakiem wyboru.','Porównuj z null zamiast używać truthy.',dashboard+'\nexport function Example(){return <Main tickets={[{id:0,title:"Zero",description:"Opis"}]}/>;}'],
 ['Zapisz JSX/JSDoc licznik z useState.','Typ stanu liczbowego narzędzia mogą wywnioskować z 0.','import {useState} from "react";\nexport function Counter(){const [count,setCount]=useState(0);return <button type="button" onClick={()=>setCount(count+1)}>{count}</button>;}'],
 ['Zaplanuj sprawdzenie Rules of Hooks bez traktowania tsc jako pełnego walidatora.','Analiza hooków jest zadaniem odpowiednich reguł lint.','Uruchom skonfigurowane reguły react-hooks, sprawdź brak warunkowych wywołań useState, pętli i hooków po zmiennych early return. TypeScript sam nie dowodzi poprawnej kolejności hooków.'],
 ['Wyjaśnij, co zwraca setter i czy można await setSelectedId(42), aby otrzymać nowy stan.','Setter nie jest obietnicą ukończenia renderu.','Setter useState zwraca void. Await nie jest mechanizmem czekania na commit React. Bieżąca zmienna selectedId nadal opisuje dany render; szczegóły migawki są tematem następnej lekcji.'],
 ['Oddaj Main: jeden wybrany wiersz, aktualna karta, odznaczanie i lokalnie rozwijany opis.','Zachowaj minimalny stan i dostępne akcje.',dashboard],
];
const quiz=`
Co zwraca useState?|Parę bieżącej wartości i settera|Sam setter|Zawsze obiekt Ticket|Element JSX|Destrukturyzacja odczytuje stan i funkcję zmiany.
Co oznacza argument useState(null)?|Wartość początkową|Zawsze bieżący props|Polecenie resetu przy każdym renderze|Wartość DOM|Początek dotyczy danej instancji.
Czy zmiana zwykłej zmiennej lokalnej automatycznie zleca render React?|Nie|Tak|Tylko w TSX|Tylko przy kliknięciu|React potrzebuje mechanizmu aktualizacji stanu.
Czy lokalne let selectedId=null zachowuje pamięć kolejnych wywołań komponentu?|Nie|Tak|Tylko w JSX|Tylko w tabeli|Funkcja tworzy nową zmienną lokalną.
Co robi setSelectedId(42)?|Zleca aktualizację stanu do 42|Mutuje natychmiast lokalny const|Zapisuje rekord do backendu|Zmienia props rodzica|Setter dotyczy stanu instancji.
Czy setter bezwarunkowo zmienia bieżącą zmienną tego renderu?|Nie|Tak|Tylko w TSX|Tylko dla null|Stan danego renderu jest migawką.
Czy ustawienie wartości równej poprzedniej zawsze wymaga nowego widocznego wyniku?|Nie|Tak|Tylko dla number|Tylko w JSX|React może pominąć aktualizację równą według Object.is.
Gdzie umieścić stan wyboru potrzebny tabeli i karcie?|We wspólnym rodzicu|W każdej komórce osobno|W CSS|W module globalnym|Jedno źródło prawdy spina widoki.
Co przechowywać jako minimalny wybór ticketu?|Id lub null|Zawsze niezależną kopię całego rekordu|Tytuł i opis osobno obowiązkowo|Cały DOM tr|Aktualny rekord można wyliczyć z danych.
Jak pobrać aktualny wybrany rekord?|Find po id w bieżących danych|Zamrozić stary props na zawsze|Odczytać key z DOM|Pobrać tekst CSS|Wyliczenie korzysta z najnowszej listy.
Co daje find bez dopasowania?|undefined|null zawsze|Pierwszy rekord|Pusty obiekt|Można zamienić wynik na null przez ??.
Po co ??null po find?|Ujednolica kontrakt pustej karty|Zmienia id w zero|Sortuje dane|Mutuje tablicę|Karta przyjmuje Ticket lub null.
Czy przechowywanie obiektu w stanie jest zawsze zakazane?|Nie|Tak|Tylko w TSX|Tylko dla Ticket|Problem dotyczy zbędnego dublowania danych i starej referencji w tym modelu.
Dlaczego osobny selected object może pokazać stare dane po wymianie listy?|Nie aktualizuje się automatycznie z nowymi props|React usuwa pola|Find mutuje obiekt|CSS blokuje zmianę|Zapamiętana referencja pozostaje poprzednim obiektem.
Czy dwa Main obok siebie mają z definicji wspólne selectedId?|Nie|Tak|Tylko w TSX|Tylko z tym samym tytułem|Każda instancja ma własny stan.
Z czym React wiąże stan komponentu?|Z tożsamością instancji w drzewie|Wyłącznie z nazwą zmiennej|Z klasą CSS|Wyłącznie z plikiem|Miejsce, typ i key wpływają na zachowanie stanu.
Co dzieje się ze stanem przy odmontowaniu instancji?|Jest usuwany|Zawsze zapisuje się w localStorage|Przechodzi do rodzeństwa|Staje się propsem|Ponowny mount tworzy nowy początek.
Czy zmiana key może zresetować lokalny stan?|Tak|Nie|Tylko w JSX|Tylko dla licznika|Nowa tożsamość oznacza nową instancję.
Czy losowe key są dobrym sposobem zwykłej aktualizacji wyglądu?|Nie|Tak|Tylko w TSX|Tylko dla opisu|Mogą niepotrzebnie resetować poddrzewo.
Co ma resetować key ticket.id na TicketDescription?|Expanded przy zmianie wybranego ticketu|Wszystkie dane aplikacji|Status backendu|Wybór rodzica zawsze|Reset jest celowo lokalny do opisu.
Czy zmiana text przy tym samym key zawsze resetuje expanded?|Nie|Tak|Tylko w TSX|Tylko przy 121 znakach|Aktualizacja props zachowuje tę samą instancję.
Gdzie wywoływać useState?|Na najwyższym poziomie komponentu lub własnego hooka|W dowolnym handlerze|W map po rekordach|W warunkowym if zależnym od props|Kolejność wywołań musi pozostać poprawna.
Czy useState może zależeć od warunku zmieniającego się między renderami?|Nie|Tak|Tylko w TSX|Tylko dla null|To łamie reguły tego hooka.
Czy useState po early return osiąganym tylko czasem jest poprawny?|Nie|Tak|Tylko w JSX|Tylko dla Card|Kolejność hooków może się zmienić.
Jak rozdzielić stan dla elementów listy bez hooka w pętli?|Osobny komponent dla elementu|Wywoływać useState w map|Użyć losowego key|Zapisać do CSS|Każdy komponent ma własną sekwencję hooków.
Czy tsc samo gwarantuje Rules of Hooks?|Nie|Tak|Tylko w strict|Tylko przy generics|Potrzebne są odpowiednie reguły lint i poprawny projekt.
Czy funkcja zaczynająca się od handle jest miejscem deklarowania useState?|Nie|Tak|Tylko w TSX|Tylko przy kliknięciu|Handler może wywołać setter, ale nie hook deklarujący stan.
Czy useState wolno wywołać w zwykłej funkcji formatującej datę?|Nie|Tak|Tylko w JSX|Tylko dla string|Hook należy do komponentu lub poprawnego własnego hooka.
Jaki typ inferred ma useState(0)?|number|null|string|boolean|Liczbowy początek określa stan liczbowy.
Jaki typ inferred ma useState(false)?|boolean|number|string|null|Początek określa stan logiczny.
Jaki typ inferred ma useState("")?|string|Wyłącznie pusty literał zawsze|number|boolean|Stan może przyjmować inne napisy.
Jaki typ daje useState(null) w strict bez generyka?|null|number lub null automatycznie|Ticket|null[]|Brak informacji o przyszłych wartościach.
Jak opisać wybór liczbowy lub brak?|Jawny generyk obejmujący number i null|useState(null) i dowolne settery|useState<boolean>|useState<never>|Trzeba jawnie opisać przyszłą liczbę i null.
Jaki typ może wywnioskować useState([]) w strict?|never[]|Ticket[] automatycznie|number[] zawsze|ReactNode[] zawsze|Pusta tablica nie określa typu elementów.
Jak zadeklarować pustą listę Ticket w stanie?|useState<Ticket[]>([])|useState(null) wyłącznie|useState(0)|useState<boolean>([])|Generyk opisuje elementy przyszłej tablicy.
Czy useState<Status>(OPEN) ogranicza setter do Status?|Tak przy poprawnym zapisie literału|Nie|Tylko w JSX|Tylko dla null|Jawny typ zamyka zbiór wartości.
Czy useState("OPEN") samo musi dać unię OPEN/CLOSED?|Nie|Tak|Tylko w strict|Tylko w TSX|Nie zna drugiego dozwolonego literału.
Czy setter stanu obejmującego number lub null można przekazać do onSelect(number)?|Tak|Nie|Tylko w JSX|Tylko z any|Akceptuje wszystkie liczby potrzebne callbackowi.
Czy setter useState zwraca Promise ukończenia renderu?|Nie|Tak|Tylko w TSX|Tylko z React 19|Zwraca void.
Czy await setCount jest mechanizmem oczekiwania na nowy commit?|Nie|Tak|Tylko w JSX|Tylko dla number|Setter nie udostępnia takiej obietnicy.
Czy initialId w useState(initialId) stale synchronizuje props?|Nie|Tak|Tylko w TSX|Tylko dla null|Argument początkowy nie jest bieżącą kontrolą stanu.
Co rozważyć, gdy rodzic ma stale sterować wyborem?|Kontrolowane props|Kopiowanie props do stanu w każdym renderze|Losowy key przy każdym kliknięciu|Globalną zmienną|Nie trzeba dublować jednego źródła prawdy.
Czy globalna zmienna modułu jest równoważna stanowi instancji?|Nie|Tak|Tylko w JSX|Tylko dla id|Jest współdzielona i nie ma tego samego mechanizmu renderów.
Czy selectedId=0 oznacza brak, jeśli kontrakt używa null?|Nie|Tak|Tylko w TSX|Tylko przy pustej liście|Zero może być poprawnym id.
Jak sprawdzać brak wyboru zamiast ogólnego falsy?|Porównać z null|Użyć !selectedId zawsze|Zamienić zero na null|Sprawdzić title|Kontrakt jawnie opisuje pusty stan.
Czy wyliczenie selected=null automatycznie wywoła setSelectedId(null)?|Nie|Tak|Tylko w JSX|Tylko po usunięciu rekordu|Wyliczenie i aktualizacja stanu to różne operacje.
Co zobaczy karta, gdy wybrany rekord zniknie z props?|Pusty stan dzięki find i fallbackowi|Zawsze TypeError|Automatycznie pierwszy rekord|Wiersz losowy|Kod sprawdza wynik przed użyciem pól.
Jaki próg ma długi opis w przykładzie?|Więcej niż 120 znaków|Co najmniej 120|Zawsze 120|Więcej niż 12|Warunek to text.length>120.
Czy opis długości dokładnie 120 ma przycisk rozwijania?|Nie|Tak|Tylko w TSX|Tylko w dark|Nie przekracza progu.
Czy opis długości 121 startuje skrócony?|Tak|Nie|Tylko po kliknięciu|Tylko w JSX|Początek expanded=false i isLong=true.
Co oznacza expanded=false dla krótkiego opisu?|Cały tekst nadal widoczny|Tekst znika|Zawsze 120 znaków|Błąd|Krótki tekst nie potrzebuje skracania.
Co opisuje aria-expanded?|Bieżący stan rozwinięcia|Nazwę przyszłej akcji|Długość tekstu|Stan wyboru tabeli|Atrybut ma odpowiadać widocznemu stanowi.
Jaka etykieta pasuje do zwiniętego długiego opisu?|Pokaż całość|Zwiń|Usuń|Odznacz zawsze|Tekst przycisku opisuje następną akcję.
Jaka etykieta pasuje do rozwiniętego opisu?|Zwiń|Pokaż całość|Usuń|Wybierz ticket|Akcja składa opis.
Czy expanded musi być w Main, gdy tylko opis go używa?|Nie|Tak|Tylko w TSX|Tylko dla długiego tekstu|Stan może pozostać lokalny blisko użycia.
Czy Main powinien przechowywać też kopię tekstu wybranego ticketu?|Zwykle nie w tym modelu|Zawsze tak|Tylko w JSX|Tylko dla 42|Tekst można pobrać z wybranego rekordu.
Czy aria-pressed na przycisku wyboru może pokazać aktywną pozycję?|Tak|Nie|Tylko w JSX|Tylko dla button submit|Semantyczny przycisk może mieć stan wciśnięcia.
Czy selectedId jako jedna liczba gwarantuje najwyżej jedno dopasowanie przy unikalnych id?|Tak|Nie|Tylko w TSX|Tylko z clsx|Każdy rekord porównuje własne unikalne id.
Czy powtarzające się id danych naruszają założenie pojedynczego wyboru?|Tak|Nie|Tylko w JSX|Tylko dla null|Kilka rekordów mogłoby pasować do tej samej wartości.
Co potwierdza ukończenie podstaw useState?|Wybór, karta, odznaczanie i rozwijanie opisu działają|Sama deklaracja hooka|Wyłącznie log w konsoli|Tylko brak błędów składni|Stan musi sterować widocznym wynikiem.
`.trim().split('\n').map(line=>line.split('|'));
const cases=[
 ['Wybór pierwszego','tickets.find(t=>t.id===1)?.title??"Brak"','API','Eksport','Brak','null'],
 ['Wybór drugiego','tickets.find(t=>t.id===2)?.title??"Brak"','Eksport','API','Brak','null'],
 ['Nieistniejący wybór','tickets.find(t=>t.id===99)?.title??"Brak"','Brak','API','Eksport','undefined'],
 ['Id zero','tickets.find(t=>t.id===0)?.title??"Brak"','Zero','Brak','API','0'],
 ['Pusta lista','[].find(t=>false)??null','null','undefined','0','Brak'],
 ['Brak wyboru','tickets.find(t=>t.id===selectedId)??null','null','undefined','API','0'],
 ['Liczba wybranych dla id=2','tickets.filter(t=>t.id===2).length','1','2','0','3'],
 ['Wszystkie rekordy przy braku wyboru','tickets.filter(t=>t.id===selectedId).length','0','1','2','3'],
 ['Porównanie id=0 z null','0===selectedId','false','true','null','undefined'],
 ['Bezpieczny fallback zera','tickets[0]?.id??"Brak"','0','Brak','null','undefined'],
 ['Próg długości 120','"a".repeat(120).length>120','false','true','120','undefined'],
 ['Próg długości 121','"a".repeat(121).length>120','true','false','121','undefined'],
 ['Długość skrótu ze znakiem wielokropka','("a".repeat(121).slice(0,120)+"…").length','121','120','122','3'],
 ['Etykieta dla expanded=false','expanded?"Zwiń":"Pokaż całość"','Pokaż całość','Zwiń','false','true'],
 ['Przełączenie false','!expanded','true','false','null','0'],
 ['Dwa przełączenia false','!!expanded','false','true','null','1'],
 ['Krótki tekst mimo expanded=false','expanded||shortText.length<=120?shortText:"skrót"','Krótki','skrót','false','undefined'],
 ['Dopasowanie po zmianie listy','updated.find(t=>t.id===1)?.title??"Brak"','Nowe API','API','Brak','Eksport'],
 ['Usunięty rekord','removed.find(t=>t.id===1)?.title??"Brak"','Brak','API','Eksport','Zero'],
 ['Wybranie nie zmienia długości danych','tickets.length','3','2','1','0'],
];
const codeBase='const tickets=[{id:0,title:"Zero"},{id:1,title:"API"},{id:2,title:"Eksport"}];\nconst selectedId:number|null=null;\nconst expanded=false;const shortText="Krótki";\nconst updated=[{id:1,title:"Nowe API"}];const removed=tickets.filter(t=>t.id!==1);';
for(const [label,expr,a,b,c,d] of cases)quiz.push([`${label}: jaki wynik wypisze czyste wyliczenie używane w widoku?`,a,b,c,d,'Wyrażenie analizuje dane bieżącego renderu bez mutowania listy.',codeBase+'\nconsole.log('+expr+');']);
const typeQuestions=`
Czy setCount("1") pasuje do useState(0)?|Nie|Tak|Tylko w JSX|Tylko w strict|Stan został wywnioskowany jako number.
Czy setExpanded(1) pasuje do useState(false)?|Nie|Tak|Tylko w TSX|Tylko przy długim opisie|Liczba nie jest boolean.
Czy setQuery("react") pasuje do useState("")?|Tak|Nie|Tylko po rzutowaniu any|Tylko w JSX|Stan ma typ string.
Czy setSelectedId(null) pasuje do unii number oraz null?|Tak|Nie|Tylko przy pustej liście|Tylko po remouncie|Null jest dozwolonym pustym wyborem.
Czy setSelectedId(0) pasuje do unii number oraz null?|Tak|Nie|Tylko po rzutowaniu|Tylko w JSX|Zero jest liczbą.
Czy undefined automatycznie należy do unii number oraz null?|Nie|Tak|Tylko w strict|Tylko dla selected|Unia nie zawiera undefined.
Czy setStatus("PAUSED") pasuje do OPEN/CLOSED?|Nie|Tak|Tylko w JSX|Tylko bez CSS|Unia ogranicza wartości.
Czy Ticket[] opisuje tablicę z polami id i title zgodnymi z Ticket?|Tak|Nie|Tylko w JSX|Tylko bez strict|Typ elementu dotyczy każdego rekordu.
Czy samo [] mówi TypeScriptowi, że elementy będą Ticket?|Nie|Tak|Tylko w strict|Tylko przy useState|Brakuje informacji o przyszłych elementach.
Czy setter może przyjmować funkcję aktualizującą poprzednią wartość?|Tak|Nie|Tylko w JSX|Tylko w useReducer|To druga forma SetStateAction, rozwijana w kolejnej lekcji.
Czy setter liczbowy może poprawnie zwrócić ze swojego updatera tekst?|Nie|Tak|Tylko w TSX|Tylko przy await|Wynik updatera ma być zgodny ze stanem.
Czy nazwy selectedId i setSelectedId są narzucone przez runtime React?|Nie|Tak|Tylko w TSX|Tylko dla null|To czytelna konwencja, nie obowiązkowe identyfikatory.
Czy destrukturyzacja useState jest destrukturyzacją tablicy?|Tak|Nie|Tylko w JSX|Tylko dla number|Hook zwraca parę w ustalonej kolejności.
Czy wolno zamienić kolejność wartości i settera przy odczycie?|Nie bez zmiany znaczenia|Tak zawsze|Tylko w TSX|Tylko przy const|Pierwsza pozycja to stan, druga funkcja.
Czy setter od innej instancji Counter zmieni automatycznie oba liczniki?|Nie|Tak|Tylko w JSX|Tylko z taką samą etykietą|Setter jest związany z konkretnym stanem.
Czy aktualizacja props może odświeżyć wyliczony selected bez zmiany selectedId?|Tak|Nie|Tylko w JSX|Tylko w StrictMode|Find pracuje na aktualnej liście przy renderze.
Czy opis długości 0 wymaga specjalnego stanu expanded=true, aby się wyrenderować?|Nie|Tak|Tylko w TSX|Tylko dla null|Krótki tekst renderuje się bez skracania.
Czy ukrycie komponentu wyłącznie CSS display:none musi usuwać jego stan?|Nie|Tak|Tylko w JSX|Tylko dla Counter|Ukrycie DOM stylem różni się od odmontowania komponentu.
Czy warunkowe pominięcie komponentu w JSX może usunąć jego stan?|Tak|Nie|Tylko w TSX|Tylko dla null|Odmontowana instancja nie zachowuje lokalnego stanu.
Czy odznaczenie przez setSelectedId(null) wymaga zmiany tickets?|Nie|Tak|Tylko w JSX|Tylko dla 42|Wybór jest niezależnym stanem interfejsu.
`.trim().split('\n').map(line=>line.split('|'));
quiz.push(...typeQuestions);
module.exports={exercises,quiz,counter,description,dashboard};
