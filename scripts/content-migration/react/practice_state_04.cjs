const model='export type Comment=Readonly<{id:number;text:string;likes:number}>;\nexport type Ticket=Readonly<{id:number;title:string;status:"OPEN"|"CLOSED";assignee:string|null;tags:readonly string[];comments:readonly Comment[];createdAt:string}>;';
const helpers='export function closeTicket(tickets:readonly Ticket[],id:number):Ticket[]{return tickets.map(ticket=>ticket.id===id?{...ticket,status:"CLOSED"}:ticket);}\nexport function deleteTicket(tickets:readonly Ticket[],id:number):Ticket[]{return tickets.filter(ticket=>ticket.id!==id);}\nexport function assignTicket(tickets:readonly Ticket[],id:number,login:string):Ticket[]{return tickets.map(ticket=>ticket.id===id?{...ticket,assignee:login}:ticket);}\nexport function addTag(tickets:readonly Ticket[],id:number,tag:string):Ticket[]{return tickets.map(ticket=>ticket.id===id&&!ticket.tags.includes(tag)?{...ticket,tags:[...ticket.tags,tag]}:ticket);}\nexport function likeComment(ticket:Ticket,commentId:number):Ticket{return {...ticket,comments:ticket.comments.map(comment=>comment.id===commentId?{...comment,likes:comment.likes+1}:comment)};}\nexport function nextTicketId(tickets:readonly Ticket[]):number{const max=tickets.reduce((value,ticket)=>Math.max(value,ticket.id),0);if(!Number.isSafeInteger(max)||max>=Number.MAX_SAFE_INTEGER)throw new RangeError("Brak kolejnego bezpiecznego id");return max+1;}\nexport function prependQuickTicket(tickets:readonly Ticket[],createdAt:string):Ticket[]{const ticket:Ticket={id:nextTicketId(tickets),title:"Nowy ticket",status:"OPEN",assignee:null,tags:[],comments:[],createdAt};return [ticket,...tickets];}';
const main='import {useState} from "react";\n'+model+'\n'+helpers+'\nexport function Main({initialTickets}:{initialTickets:readonly Ticket[]}){const [tickets,setTickets]=useState<readonly Ticket[]>(initialTickets);const [selectedId,setSelectedId]=useState<number|null>(null);const selected=tickets.find(ticket=>ticket.id===selectedId)??null;function handleQuick(){const createdAt=new Date().toISOString();setTickets(previous=>prependQuickTicket(previous,createdAt));}return <main><button type="button" onClick={handleQuick}>+ Szybki ticket</button><ul>{tickets.map(ticket=><li key={ticket.id}><button type="button" aria-pressed={ticket.id===selectedId} onClick={()=>setSelectedId(ticket.id)}>{ticket.title} — {ticket.status}</button><button type="button" disabled={ticket.status==="CLOSED"} onClick={()=>setTickets(previous=>closeTicket(previous,ticket.id))}>Zamknij</button><button type="button" onClick={()=>setTickets(previous=>deleteTicket(previous,ticket.id))}>Usuń</button></li>)}</ul>{selected?<aside><h2>{selected.title}</h2><p>{selected.status}</p><p>{selected.assignee??"Nieprzypisany"}</p><button type="button" onClick={()=>setTickets(previous=>assignTicket(previous,selected.id,"anna"))}>Przypisz do mnie</button></aside>:<p>Wybierz ticket z listy</p>}</main>;}';
const exercises=[
 ['Zmień status jednego obiektu przez spread bez mutowania wejścia.','Nowa właściwość po spread nadpisuje poprzednią.','type Ticket={id:number;status:"OPEN"|"CLOSED"};\nexport function close(ticket:Ticket):Ticket{return {...ticket,status:"CLOSED"};}'],
 ['Napisz closeTicket dla tablicy przez map z nowym obiektem tylko dla pasującego id.','Pozostałe referencje mogą zostać współdzielone.',model+'\n'+helpers],
 ['Napisz deleteTicket przez filter.','Nie używaj splice na tablicy stanu.',model+'\n'+helpers],
 ['Napisz assignTicket ustawiający login w wybranym rekordzie.','Kopiuj zmieniany rekord, nie wszystkie rekordy bez potrzeby.',model+'\n'+helpers],
 ['Dodaj tag przez nową tablicę tags i nowy obiekt ticketu. Odrzuć duplikat.','Spread obiektu nie kopiuje zagnieżdżonej tablicy.',model+'\n'+helpers],
 ['Zwiększ likes jednego komentarza bez zmiany oryginalnego ticketu.','Kopiuj ścieżkę do zmienionego elementu.',model+'\n'+helpers],
 ['Dodaj element na końcu listy.','Append tworzy nowy kontener.','export function append<T>(items:readonly T[],item:T):T[]{return [...items,item];}'],
 ['Dodaj element na początku listy.','Kolejność spread decyduje o pozycji.','export function prepend<T>(items:readonly T[],item:T):T[]{return [item,...items];}'],
 ['Wstaw element pod indeksem bez mutacji, używając toSpliced.','Załóż wspierający runtime i poprawny indeks.','export function insert<T>(items:readonly T[],index:number,item:T):T[]{return items.toSpliced(index,0,item);}'],
 ['Zastąp element pod indeksem przez with.','Dla indeksu poza zakresem ta metoda rzuca RangeError.','export function replaceAt<T>(items:readonly T[],index:number,item:T):T[]{return items.with(index,item);}'],
 ['Posortuj tickety po id bez zmiany stanu źródłowego.','ToSorted tworzy nową tablicę.',model+'\nexport function sorted(tickets:readonly Ticket[]):Ticket[]{return tickets.toSorted((a,b)=>a.id-b.id);}'],
 ['Odwróć listę bez reverse na oryginale.','Użyj toReversed albo kopii kontenera.','export function reversed<T>(items:readonly T[]):T[]{return items.toReversed();}'],
 ['Podaj alternatywę dla toSorted w starszym runtime.','Typy ES2023 nie dodają automatycznie implementacji.','export function sorted(values:readonly number[]):number[]{return [...values].sort((a,b)=>a-b);}'],
 ['Usuń opcjonalne pole assignee z obiektu przez destrukturyzację.','Null i nieobecność pola to różne modele.','type Ticket={id:number;assignee?:string};\nexport function withoutAssignee(ticket:Ticket):Omit<Ticket,"assignee">{const {assignee,...rest}=ticket;return rest;}'],
 ['Zmień city w zagnieżdżonym address.','Kopia jest potrzebna na każdym zmienianym poziomie.','type User={id:number;address:{city:string;street:string}};\nexport function move(user:User,city:string):User{return {...user,address:{...user.address,city}};}'],
 ['Wyjaśnij błąd const copy={...ticket};copy.tags.push(tag).','Tags nadal może wskazywać ten sam kontener.','Spread obiektu jest płytki. Copy.tags i ticket.tags wskazują tę samą tablicę, więc push zmieni oryginał. Użyj nowego ticketu z tags:[...ticket.tags,tag].'],
 ['Sprawdź, czy nowa tablica [...tickets] sama czyni bezpiecznym copy[0].status=...','Kopia tablicy nie kopiuje rekordów.','Nie. Elementy nadal wskazują poprzednie obiekty. Aby zmienić jeden status, utwórz nowy obiekt dla tego rekordu, np. przez map i spread.'],
 ['Zaprojektuj test referencji po zamknięciu id=1.','Sprawdź zarówno wynik, jak i niezmienione wejście.','Nowa tablica nie jest stara. Rekord 1 ma nową referencję i CLOSED. Rekord 2 zachowuje referencję. Oryginalny rekord 1 nadal ma OPEN. Zamroź tablicę i rekordy, aby wykrywać zapis w testowanym kodzie.'],
 ['Napisz nextTicketId działające dla pustej i dużej listy bez Math.max(...ids).','Reduce nie wymaga przekazania wszystkich id jako argumentów funkcji.',model+'\n'+helpers],
 ['Obsłuż wyczerpanie bezpiecznych identyfikatorów liczbowych.','Załóż wejściowe dodatnie bezpieczne id.','Gdy maksymalne id jest Number.MAX_SAFE_INTEGER, nie dodawaj jeden bez kontroli. Zgłoś błąd albo użyj innej strategii id zgodnej z domeną. Nie obiecuj globalnej unikalności lokalnego licznika w systemie wieloużytkownikowym.'],
 ['Dodaj szybki ticket z czasem z handlera i id liczonym czysto z previous.','Data nie powinna być losowana lub odczytywana w updaterze.',main],
 ['Wyjaśnij, dlaczego id z previous w czystym updaterze chroni dwa zakolejkowane dodania lokalne.','Oba updatery mają kolejne wersje tablicy.','Każdy updater oblicza maksymalne id z wyniku poprzedniego. Drugie dodanie widzi pierwszy nowy rekord, więc dostaje kolejny numer. Data utworzona w handlerze jest wejściem updatera. To lokalna strategia, nie zamiennik identyfikatorów serwera.'],
 ['Podłącz zamykanie, usuwanie i przypisywanie przez setTickets(previous=>...).','Operacje zależą od poprzedniej listy.',main],
 ['Usuń wybrany ticket i wyjaśnij pustą kartę szczegółów.','Selected jest obliczany z aktualnych tickets.',main],
 ['Zdiagnozuj literówkę {...ticket,stauts:"CLOSED"}, jeśli stare status nadal istnieje.','Strukturalne typowanie nie jest pełnym wykrywaczem dodatkowych pól.','Taki obiekt może nadal pasować strukturalnie do Ticket, bo właściwe pole status pozostało po spread. Nie obiecuj automatycznego błędu każdej literówki. Użyj świadomego typu zwracanego świeżego obiektu lub satisfies oraz testu zachowania statusu.'],
 ['Wykaż błędem TS literówkę stauts w świeżym obiekcie sprawdzanym satisfies.','Excess property checking ma konkretny kontekst.','type Ticket={id:number;status:"OPEN"|"CLOSED"};\nconst ticket:Ticket={id:1,status:"OPEN"};\n// @ts-expect-error Nieznane pole w świeżym obiekcie\nconst next={...ticket,stauts:"CLOSED"} satisfies Ticket;'],
 ['Porównaj readonly typów i Object.freeze danych testowych.','Freeze także jest płytkie.','Readonly ogranicza zapisy w analizie TypeScript. Object.freeze wykonuje ograniczenie w runtime dla danego obiektu. Przy danych zagnieżdżonych zamroź osobno tablice i rekordy, które testujesz; sam freeze kontenera nie zamraża całego grafu.'],
 ['Wyjaśnij ideę draftu Immer bez traktowania zwykłych props jako draft.','Mutujący zapis jest dozwolony tylko w mechanizmie biblioteki.','Produce udostępnia kontrolowany draft i na podstawie zmian tworzy wynik zachowujący oryginał. Zwykłe ticket z useState lub props nie staje się draftem automatycznie. Nie kopiuj mutującej składni poza kontekst biblioteki.'],
 ['Zaprojektuj test no-op i nieznanego id bez narzucania zbędnego renderu.','Niemutowalność nie oznacza, że brak zmiany musi tworzyć nowy obiekt.','Sprawdź niezmienione wartości wejściowe i wynikowe. Implementacja może zwrócić oryginalną referencję przy braku zmiany albo nową równoważną tablicę. Wymaganie zachowania to brak mutacji i poprawne dane; optymalizację referencji opisuj osobno.'],
 ['Oddaj Main z niemutowalnym zamykaniem, usuwaniem, przypisaniem i szybkim dodaniem.','Oryginalne dane startowe pozostają niezmienione.',main],
];
const quiz=`
Co oznacza niemutowalna aktualizacja stanu?|Utworzenie nowej wersji bez zmiany starej|Zakaz zmian UI|Zawsze głęboką kopię wszystkiego|Wyłączenie setterów|Nowe dane powstają bez zapisu do poprzedniej wersji.
Jak React porównuje starą i nową wartość stanu dla pomijania równych wartości?|Object.is|JSON.stringify zawsze|Tylko długością tablicy|Wyłącznie typeof|Tożsamość obiektów ma znaczenie.
Czy setTickets(tickets) po mutacji gwarantuje poprawny widoczny render?|Nie|Tak|Tylko w JSX|Tylko przy CLOSED|Ta sama referencja może zostać uznana za równą.
Czy mutacja stanu jest bezpieczna, jeśli inny render przypadkiem pokaże zmianę?|Nie|Tak|Tylko w TSX|Tylko w produkcji|Narusza kontrakt i stare wersje danych niezależnie od przypadkowego efektu UI.
Czy {...ticket} kopiuje głęboko tags?|Nie|Tak|Tylko w TSX|Tylko dla string[]|Spread obiektu jest płytki.
Czy [...tickets] kopiuje głęboko rekordy?|Nie|Tak|Tylko w JSX|Tylko dla id|Referencje elementów są współdzielone.
Jak zmienić jeden rekord w tablicy?|Map i nowy obiekt dla pasującego id|Mutować znaleziony rekord|Sortować listę|Zmienić key|Nowy kontener i zmieniana ścieżka zachowują niemutowalność.
Czy niezmienione rekordy mogą zachować referencje?|Tak|Nie|Tylko w TSX|Tylko dla null|To strukturalne współdzielenie danych.
Jak usunąć rekord po id bez mutacji?|Filter|Splice na stanie|Pop zawsze|Delete arr[index] zawsze|Filter tworzy nową tablicę dopasowanych elementów.
Czy filter sam kopiuje obiekty elementów?|Nie|Tak|Tylko w JSX|Tylko dla Ticket|Nowy jest kontener, nie każdy rekord.
Jak dopisać na koniec niemutowalnie?|Spread listy przed nowym elementem|Push na props|Unshift na stanie|Przypisanie length|Kolejność tworzonej tablicy zachowuje wcześniejsze elementy.
Jak dopisać na początek niemutowalnie?|Nowy element przed spread listy|Push|Pop|Reverse na stanie|Nowa tablica zaczyna się od dodanego rekordu.
Czy push na oryginalnej tablicy stanu jest poprawnym updaterem?|Nie|Tak|Tylko w TSX|Tylko w useState|Push zmienia kontener w miejscu.
Czy pop zmienia oryginalną tablicę?|Tak|Nie|Tylko w JSX|Tylko dla liczb|To mutująca metoda.
Czy shift zmienia oryginalną tablicę?|Tak|Nie|Tylko w TSX|Tylko dla Ticket|Usuwa pierwszy element w miejscu.
Czy unshift zmienia oryginalną tablicę?|Tak|Nie|Tylko w JSX|Tylko dla liczb|Dodaje na początek w miejscu.
Czy sort zmienia oryginalną tablicę?|Tak|Nie|Tylko w TSX|Tylko z komparatorem|Sort działa w miejscu.
Czy reverse zmienia oryginalną tablicę?|Tak|Nie|Tylko w JSX|Tylko dla stringów|Reverse odwraca kontener w miejscu.
Co robi toSorted?|Zwraca posortowaną kopię tablicy|Sortuje oryginał|Zwraca tylko pierwszy rekord|Modyfikuje id|Nowoczesna metoda nie mutuje kontenera.
Co robi toReversed?|Zwraca odwróconą kopię|Mutuje oryginał|Usuwa pierwszy rekord|Sortuje rosnąco|Kolejność oryginału pozostaje bez zmian.
Co robi toSpliced?|Tworzy tablicę po operacji wstawienia lub usunięcia|Zawsze mutuje stan|Zmienia obiekty głęboko|Zwraca JSX|To niemutujący odpowiednik operacji na fragmencie tablicy.
Co robi with(index,value)?|Tworzy tablicę z zastąpionym elementem|Mutuje obiekt na zawsze|Dodaje tylko na koniec|Tworzy event|Metoda zastępuje element na określonej pozycji.
Czy with dla indeksu poza zakresem może rzucić RangeError?|Tak|Nie|Tylko w TSX|Tylko dla null|Trzeba znać kontrakt indeksu.
Czy ustawienie lib ES2023 samo dodaje metody do starej przeglądarki?|Nie|Tak|Tylko w TSX|Tylko w Vite|Typy i implementacja runtime to różne rzeczy.
Jak zastąpić toSorted w starszym runtime?|Kopia spread i sort na kopii|Sort na stanie bez kopii|Zmiana key|Object.is|Mutacja świeżo utworzonego kontenera nie zmienia źródła.
Czy każdy push w każdym miejscu programu jest zakazany przez React?|Nie|Tak|Tylko w TSX|Tylko w JSX|Problemem jest mutacja danych, które należy zachować, np. props i wcześniejszego stanu.
Jak dodać tag do wybranego ticketu?|Nowy ticket i nowa tablica tags|Spread samego ticketu potem push do tags|Push do props|Zmienić selectedId|Kopiować trzeba każdy zmieniany poziom.
Czy map bez zmiany obiektów wystarczy, gdy później mutujesz ich pola?|Nie|Tak|Tylko w TSX|Tylko dla statusu|Współdzielone rekordy nadal byłyby mutowane.
Jak zmienić city w address?|Skopiować user i address|Skopiować wyłącznie user i zapisać city w starym address|Zmienić key|Użyć sort|Nowa ścieżka do zmienionej wartości chroni oryginał.
Czy trzeba kopiować niezmienione inne adresy i rekordy głęboko?|Nie|Tak|Tylko w JSX|Tylko dla readonly|Strukturalne współdzielenie jest prawidłowe.
Jak zwiększyć likes jednego komentarza?|Nowy ticket, tablica comments i zmieniony komentarz|Mutować znaleziony comment|Skopiować sam kontener tickets|Użyć reverse|Zmieniana ścieżka ma nową wersję.
Czy readonly Ticket[] uniemożliwia push przez ten typ?|Tak|Nie|Tylko w JSX|Tylko po freeze|Typ opisuje niezmienny kontener.
Czy readonly tablicy samo czyni wszystkie zagnieżdżone pola readonly?|Nie|Tak|Tylko w TSX|Tylko dla Comment|Niemutowalność głębszych obiektów trzeba opisać osobno.
Czy Object.freeze jest głębokie?|Nie|Tak|Tylko na Windows|Tylko dla tablic|Zamraża wskazany obiekt na jednym poziomie.
Co testować oprócz wartości wyniku?|Niezmienione wejście i odpowiednie referencje|Wyłącznie nazwę funkcji|Tylko liczbę renderów|Tylko długość JSON|To ujawnia przypadkową mutację.
Czy nieznane id musi zawsze powodować nową referencję?|Nie|Tak|Tylko w TSX|Tylko przy map|No-op może zachować oryginalną wersję.
Czy nowa równoważna tablica przy no-op sama narusza niemutowalność?|Nie|Tak|Tylko w JSX|Tylko z filter|Może być mniej optymalna, ale nie jest mutacją starej wersji.
Czy forma funkcyjna setTickets jest przydatna przy zmianie zależnej od listy?|Tak|Nie|Tylko w TSX|Tylko dla id|Updater korzysta z właściwej poprzedniej wersji.
Czy odczyt daty powinien wykonywać się w czystym updaterze?|Nie|Tak|Tylko w JSX|Tylko dla ISO|Datę oblicz w handlerze i przekaż jako jawne wejście.
Czy nextId liczone czysto z previous może znajdować się w updaterze?|Tak|Nie|Tylko w TSX|Tylko dla zera|Deterministyczne obliczenie z wejścia jest czyste.
Dlaczego Math.max(...ids) wymaga uwagi dla pustej listy?|Pusta lista daje -Infinity|Daje 1 zawsze|Daje null|Rzuca TypeError zawsze|Trzeba jawnie obsłużyć początek numeracji.
Dlaczego reduce bywa lepsze niż spread ogromnej listy do Math.max?|Nie tworzy ogromnej listy argumentów funkcji|Zawsze zmienia id|Mutuje mniej obiektów automatycznie|Nie potrzebuje danych|Silniki mają ograniczenia liczby argumentów.
Czy lokalne max+1 gwarantuje globalne unikalne id na wielu klientach?|Nie|Tak|Tylko w TSX|Tylko z ISO|System wieloużytkownikowy wymaga właściwej strategii identyfikacji.
Czy Number.MAX_SAFE_INTEGER+1 można bezrefleksyjnie traktować jako kolejny bezpieczny integer?|Nie|Tak|Tylko w JSX|Tylko dla ticketów|Precyzja liczb ma granice.
Czy dwa updatery dodające z id obliczanym z previous mogą widzieć kolejne wersje listy?|Tak|Nie|Tylko w TSX|Tylko bez batchingu|Drugi przelicza wynik pierwszego.
Czy usunięcie wybranego rekordu może dać pustą kartę przez find?|Tak|Nie|Tylko w JSX|Tylko z useEffect|Wynik jest wyliczany z bieżącej listy.
Czy trzeba mutować selected object po zamknięciu, jeśli karta korzysta z id i find?|Nie|Tak|Tylko w TSX|Tylko w JSX|Karta odczyta nową wersję rekordu.
Czy spread chroni przed każdą literówką dodatkowego pola TS?|Nie|Tak|Tylko w strict|Tylko przy status|Strukturalne typowanie może dopuścić obiekt z dodatkowymi polami.
Dlaczego stauts może nie zmienić poprawnego status po spread?|Dodaje inne pole, pozostawiając stare status|React naprawia nazwę|TS zawsze naprawia kod|Map usuwa status|Nazwa właściwości ma znaczenie runtime.
Co może pomóc wykryć dodatkowe pole świeżego obiektu?|Satisfies i właściwy oczekiwany typ|Samo any|Wyłącznie sort|Losowy key|Excess property checking działa w konkretnym kontekście.
Czy satisfies jest walidacją danych runtime?|Nie|Tak|Tylko w TSX|Tylko dla Ticket|To sprawdzenie statyczne.
Czy status DONE pasuje do unii OPEN/CLOSED?|Nie|Tak|Tylko w JSX|Tylko z map|Unia ogranicza do obsługiwanych wartości.
Co daje draft Immer?|Kontrolowany szkic do tworzenia niemutowalnego wyniku|Pozwolenie mutowania wszystkich props|Automatyczny backend|Nowe id globalne|Biblioteka rejestruje zmiany szkicu.
Czy zwykły obiekt z useState jest automatycznie draftem Immer?|Nie|Tak|Tylko w TSX|Tylko gdy jest readonly|Draft istnieje w odpowiednim mechanizmie biblioteki.
Czy mutująca składnia w produce oznacza mutację oryginału przy poprawnym użyciu?|Nie|Tak|Tylko w JSX|Tylko dla comments|Immer buduje wynik na podstawie szkicu.
Czy Immer jest obowiązkowe do pojedynczej zmiany płaskiego pola?|Nie|Tak|Tylko w TSX|Tylko dla statusu|Spread i map często wystarczą.
Czy JSON round-trip jest uniwersalną bezpieczną kopią każdego stanu?|Nie|Tak|Tylko w TSX|Tylko dla obiektów|Może gubić typy wartości i nie jest potrzebny do każdej aktualizacji.
Czy usunięcie pola i przypisanie null są tym samym kontraktem?|Nie|Tak|Tylko w JSX|Tylko dla assignee|Nieobecność i jawne null to różne modele.
Czy wolno sortować świeżą lokalną kopię kontenera?|Tak|Nie|Tylko w TSX|Tylko dla id|Oryginalny kontener pozostaje bez zmian.
Co potwierdza ukończenie niemutowalnych aktualizacji?|Poprawne operacje i niezmienione poprzednie dane|Samo nowe key|Wyłącznie widoczny render|Tylko brak błędów składni|Zachowanie starych wersji jest częścią wymagania.
`.trim().split('\n').map(line=>line.split('|'));
const cases=[
 ['Append','[...values,4].join(",")','1,2,3,4','4,1,2,3','1,2,3','4'],
 ['Prepend','[0,...values].join(",")','0,1,2,3','1,2,3,0','1,2,3','0'],
 ['Usunięcie dwójki','values.filter(n=>n!==2).join(",")','1,3','1,2,3','2','3,1'],
 ['Zamiana dwójki','values.map(n=>n===2?20:n).join(",")','1,20,3','20','1,2,3','20,1,3'],
 ['Wstawienie w środek','values.toSpliced(1,0,9).join(",")','1,9,2,3','9,1,2,3','1,2,9,3','1,9,3'],
 ['Usunięcie fragmentu','values.toSpliced(1,1).join(",")','1,3','1,2,3','2','3,2,1'],
 ['Zastąpienie przez with','values.with(1,9).join(",")','1,9,3','1,2,3,9','9,2,3','1,2,9'],
 ['Ujemny indeks with','values.with(-1,9).join(",")','1,2,9','9,2,3','1,9,3','1,2,3'],
 ['Odwrócenie kopii','values.toReversed().join(",")','3,2,1','1,2,3','3,1,2','2,1,3'],
 ['Sort malejący','values.toSorted((a,b)=>b-a).join(",")','3,2,1','1,2,3','2,3,1','3,1,2'],
 ['Oryginał po kopii','(()=>{values.toReversed();return values.join(",");})()','1,2,3','3,2,1','1,3','2,1,3'],
 ['Oryginał po toSpliced','(()=>{values.toSpliced(0,1);return values.length;})()','3','2','1','0'],
 ['Nowa referencja spread','Object.is([...values],values)','false','true','undefined','null'],
 ['Nowa referencja filter','values.filter(()=>true)===values','false','true','undefined','null'],
 ['Nowa referencja map','values.map(n=>n)===values','false','true','undefined','null'],
 ['Współdzielony rekord w kopii','[...tickets][0]===tickets[0]','true','false','undefined','null'],
 ['Nowy zmieniony rekord','closed[0]===tickets[0]','false','true','undefined','null'],
 ['Niezmieniony rekord','closed[1]===tickets[1]','true','false','undefined','null'],
 ['Status wyniku','closed[0].status','CLOSED','OPEN','DONE','undefined'],
 ['Status oryginału','tickets[0].status','OPEN','CLOSED','DONE','undefined'],
 ['Długość po usunięciu','tickets.filter(t=>t.id!==1).length','1','2','0','3'],
 ['Nieznane id','tickets.filter(t=>t.id!==99).length','2','1','0','3'],
 ['Przypisanie w nowej kopii','({...tickets[0],assignee:"anna"}).assignee','anna','null','undefined','piotr'],
 ['Stary assignee','tickets[0].assignee','null','anna','undefined','piotr'],
 ['Nowa tablica tagów','({...tickets[0],tags:[...tickets[0].tags,"pilne"]}).tags.join(",")','api,pilne','api','pilne','undefined'],
 ['Stare tagi','tickets[0].tags.join(",")','api','api,pilne','pilne','undefined'],
 ['Płytkie współdzielenie tags','({...tickets[0]}).tags===tickets[0].tags','true','false','undefined','null'],
 ['Nowe tags po spread','Object.is([...tickets[0].tags],tickets[0].tags)','false','true','undefined','null'],
 ['Kolejny numer pustej listy','nextTicketId([])','1','0','-Infinity','NaN'],
 ['Kolejny numer dwóch rekordów','nextTicketId(tickets)','3','2','1','0'],
 ['Dodanie na początku','prependQuickTicket(tickets,"czas")[0].id','3','1','2','0'],
 ['Długość po dodaniu','prependQuickTicket(tickets,"czas").length','3','2','1','4'],
 ['Czas jako jawne wejście','prependQuickTicket(tickets,"czas")[0].createdAt','czas','undefined','null','Nowy ticket'],
 ['Podwójne dodanie','prependQuickTicket(prependQuickTicket(tickets,"a"),"b")[0].id','4','3','2','1'],
 ['Kolejność dwóch nowych id','prependQuickTicket(prependQuickTicket(tickets,"a"),"b").map(t=>t.id).join(",")','4,3,1,2','3,3,1,2','1,2,3,4','3,4,1,2'],
 ['Referencja starego rekordu po dodaniu','prependQuickTicket(tickets,"czas")[1]===tickets[0]','true','false','undefined','null'],
 ['Nowy licznik komentarza','likeComment(tickets[0],10).comments[0].likes','2','1','0','10'],
 ['Oryginalny licznik komentarza','tickets[0].comments[0].likes','1','2','0','10'],
 ['Zmiana nieznanego komentarza','likeComment(tickets[0],99).comments[0].likes','1','2','0','99'],
 ['Liczba po mapie stanu','closeTicket(tickets,1).length','2','1','0','3'],
];
const sample='const tickets:readonly Ticket[]=[{id:1,title:"API",status:"OPEN",assignee:null,tags:["api"],comments:[{id:10,text:"Opis",likes:1}],createdAt:"start"},{id:2,title:"Eksport",status:"OPEN",assignee:null,tags:[],comments:[],createdAt:"start"}];\nconst values=[1,2,3];const closed=closeTicket(tickets,1);';
for(const [label,expr,a,b,c,d] of cases)quiz.push([`${label}: co wypisze kod niemutowalnej operacji?`,a,b,c,d,'Wynik powstaje bez zmiany listy wejściowej i jej istniejących rekordów.',model+'\n'+helpers+'\n'+sample+'\nconsole.log('+expr+');']);
module.exports={exercises,quiz,model,helpers,main,sample};
