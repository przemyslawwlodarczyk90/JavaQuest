const model='export type TicketStatus="OPEN"|"IN_PROGRESS"|"CLOSED";\nexport type Priority="LOW"|"MEDIUM"|"HIGH"|"CRITICAL";\nexport type Ticket=Readonly<{id:number;title:string;status:TicketStatus;priority:Priority;tags:readonly string[];assignee:string|null}>;\nexport const STATUS_OPTIONS=[{value:"ALL",label:"Wszystkie"},{value:"OPEN",label:"Otwarte"},{value:"IN_PROGRESS",label:"W toku"},{value:"CLOSED",label:"Zamknięte"}] as const satisfies readonly {value:TicketStatus|"ALL";label:string}[];\nexport type StatusFilter=(typeof STATUS_OPTIONS)[number]["value"];';
const helpers='export function isStatusFilter(value:string):value is StatusFilter{return STATUS_OPTIONS.some(option=>option.value===value);}\nexport function matchesQuery(ticket:Ticket,query:string):boolean{const q=query.trim().toLowerCase();if(q==="")return true;return ticket.title.toLowerCase().includes(q)||ticket.tags.some(tag=>tag.toLowerCase().includes(q))||(ticket.assignee??"").toLowerCase().includes(q);}\nexport function filterTickets(tickets:readonly Ticket[],query:string,status:StatusFilter):Ticket[]{return tickets.filter(ticket=>(status==="ALL"||ticket.status===status)&&matchesQuery(ticket,query));}\nexport function summarize(tickets:readonly Ticket[]){return {total:tickets.length,open:tickets.filter(ticket=>ticket.status==="OPEN").length,urgent:tickets.filter(ticket=>ticket.status!=="CLOSED"&&(ticket.priority==="HIGH"||ticket.priority==="CRITICAL")).length};}';
const select='export function StatusSelect({value,onChange}:{value:StatusFilter;onChange:(value:StatusFilter)=>void}){return <label>Status<select value={value} onChange={event=>{const next=event.currentTarget.value;if(isStatusFilter(next))onChange(next);}}>{STATUS_OPTIONS.map(option=><option key={option.value} value={option.value}>{option.label}</option>)}</select></label>;}';
const main='import {useState} from "react";\n'+model+'\n'+helpers+'\n'+select+'\nexport function Main({initialTickets}:{initialTickets:readonly Ticket[]}){const [tickets,setTickets]=useState(initialTickets);const [query,setQuery]=useState("");const [status,setStatus]=useState<StatusFilter>("ALL");const visible=filterTickets(tickets,query,status);const stats=summarize(tickets);function clear(){setQuery("");setStatus("ALL");}return <main><label>Szukaj<input value={query} onChange={event=>setQuery(event.currentTarget.value)}/></label><StatusSelect value={status} onChange={setStatus}/><p>Znaleziono: {visible.length} z {tickets.length}</p><p>Otwarte: {stats.open}; pilne niezakończone: {stats.urgent}</p>{visible.length===0?<section><p>Brak ticketów pasujących do filtrów</p><button type="button" onClick={clear}>Wyczyść filtry</button></section>:<ul>{visible.map(ticket=><li key={ticket.id}>{ticket.title} — {ticket.status}<button type="button" disabled={ticket.status==="CLOSED"} onClick={()=>setTickets(previous=>previous.map(item=>item.id===ticket.id?{...item,status:"CLOSED"}:item))}>Zamknij</button></li>)}</ul>}</main>;}';
const sample='export const tickets:readonly Ticket[]=[{id:1,title:"Błąd API",status:"OPEN",priority:"HIGH",tags:["Backend","HTTP"],assignee:"Anna"},{id:2,title:"Eksport CSV",status:"IN_PROGRESS",priority:"MEDIUM",tags:["raport"],assignee:"piotr"},{id:3,title:"Logowanie",status:"CLOSED",priority:"CRITICAL",tags:["api","Auth"],assignee:null},{id:4,title:"Panel UI",status:"OPEN",priority:"LOW",tags:["ui"],assignee:"Ola"},{id:5,title:"Żółć w tytule",status:"OPEN",priority:"CRITICAL",tags:[],assignee:null}];';
const exercises=[
 ['Rozdziel stan i wartości wyliczane w filtrowanej tabeli.','Szukaj niezależnych źródeł danych i decyzji użytkownika.','Stan: edytowalne tickets, query, wybrany status i ewentualne selectedId. Wyliczane: visible, liczniki, selected z find i tekst N z M. Props od rodzica można używać bez kopii, chyba że świadomie projektujesz własny lokalny draft.'],
 ['Napisz matchesQuery: trim i lowercase zapytania oraz wszystkich przeszukiwanych pól.','Nie zakładaj, że tagi i loginy zawsze są już małymi literami.',model+'\n'+helpers],
 ['Obsłuż null assignee jako pusty tekst bez wyjątku.','Fallback poprzedza wywołanie toLowerCase.',model+'\n'+helpers],
 ['Przy pustym lub białym zapytaniu dopasuj każdy ticket.','Nie usuwaj źródłowych rekordów.',model+'\n'+helpers],
 ['Połącz warunek statusu i tekstu przez AND, a pola tekstowe przez OR.','To dwa różne poziomy logiki.',model+'\n'+helpers],
 ['Dodaj ALL jako brak ograniczenia statusu.','ALL jest filtrem, nie nowym statusem ticketu.',model+'\n'+helpers],
 ['Zbuduj STATUS_OPTIONS i StatusFilter z jednego źródła.','Satisfies pilnuje dopuszczalnych wartości opcji.',model],
 ['Napisz isStatusFilter jako guard dla wartości string z select.','Typowanie nie waliduje zewnętrznego tekstu w runtime.',model+'\n'+helpers],
 ['Zbuduj kontrolowany StatusSelect bez ślepego rzutowania dowolnego napisu.','Nieznany tekst nie wywołuje callbacku.',model+'\n'+helpers+'\n'+select],
 ['Wylicz visible bez useEffect i bez oddzielnego setVisible.','Wyliczenie zależy od aktualnych tickets, query i status.',main],
 ['Dodaj licznik Znaleziono N z M.','N pochodzi z widoku, M ze źródła.',main],
 ['Dodaj pusty stan z przyciskiem czyszczącym oba filtry.','Reset to query="" i status=ALL.',main],
 ['Zamknij ticket przy filtrze OPEN i wyjaśnij jego zniknięcie.','Aktualizacja źródła automatycznie zmienia kolejne wyliczenie.',main],
 ['Policz open i urgent z całej listy, niezależnie od filtra. Ustal urgent jako HIGH/CRITICAL z wyłączeniem CLOSED.','Nazwy liczników muszą mieć jasny kontrakt.',model+'\n'+helpers],
 ['Porównaj statystyki globalne i liczbę pasujących wyników.','Nie mieszaj zakresów pomiaru.','Globalne open/urgent liczą tickets. Znaleziono liczy visible. Filtrowanie do jednego rekordu nie powinno samo zmniejszać globalnego total. Jeśli produkt chce statystyki przefiltrowane, nazwij i oblicz je świadomie z visible.'],
 ['Przetestuj wyszukiwanie uppercase w tagu Backend i loginie Anna.','Normalizuj obie strony porównania.',model+'\n'+helpers+'\n'+sample+'\nconsole.log(filterTickets(tickets,"BACKEND","ALL").map(t=>t.id));\nconsole.log(filterTickets(tickets,"ANNA","ALL").map(t=>t.id));'],
 ['Wyjaśnij, czy query API UI wyszukuje dwa niezależne słowa.','Includes sprawdza pełny podciąg.','Nie w tym kontrakcie. Całe znormalizowane query musi wystąpić w co najmniej jednym polu. Rozbicie na słowa i dopasowanie wszystkich to inne wymaganie, które trzeba zaimplementować osobno.'],
 ['Sprawdź ŻÓŁĆ i zolc w tytule Żółć w tytule.','Zmiana wielkości liter nie usuwa znaków diakrytycznych.',model+'\n'+helpers+'\n'+sample+'\nconsole.log(filterTickets(tickets,"ŻÓŁĆ","ALL").map(t=>t.id));\nconsole.log(filterTickets(tickets,"zolc","ALL").map(t=>t.id));'],
 ['Sprawdź, że filter nie mutuje listy i zachowuje referencje rekordów.','Nowy kontener nie oznacza kopii każdego obiektu.','Zamroź listę i rekordy, zapisz ich kolejność. Filtr zwraca nową tablicę, oryginał pozostaje bez zmian, a obiekty pasujących rekordów mogą być identycznymi referencjami.'],
 ['Wyjaśnij ryzyko filteredTickets jako drugiego stanu synchronizowanego ręcznie.','Każde źródło zmian musiałoby pamiętać o drugiej kopii.','Zmiana query, statusu, zamknięcie, usunięcie lub nowe dane mogą rozjechać redundantny stan. Wyliczaj widok z aktualnego źródła zamiast dopisywać kolejne miejsca synchronizacji.'],
 ['Usuń useEffect, który tylko oblicza filteredTickets i wywołuje setter.','To nie synchronizacja z zewnętrznym systemem.','Zachowaj stan tickets i query. Zastąp efekt oraz drugi stan lokalnym const visible=filterTickets(tickets,query,status). Wynik jest dostępny w tym samym renderze, bez osobnego kroku ustawiania po renderze.'],
 ['Zaprojektuj test zamknięcia i usunięcia przy aktywnych filtrach.','Wyliczenie ma reagować na źródłowe dane.','Dla OPEN i query api najpierw widać id=1. Zamknij 1: znika. Zmień status na ALL: pasują 1 i 3. Usuń 1: pozostaje 3. Sprawdź także globalne liczniki zgodnie z ich definicją.'],
 ['Wykaż błędem TS nieznaną wartość opcji DONE.','Sprawdź świeży obiekt przez satisfies.',model+'\n// @ts-expect-error DONE nie jest statusem ani ALL\nconst wrong={value:"DONE",label:"Gotowe"} satisfies {value:StatusFilter;label:string};'],
 ['Wyjaśnij, kiedy rozważyć useMemo dla filtrowania.','Nie zgaduj czasu na podstawie samej liczby rekordów.','Najpierw zmierz reprezentatywną operację i UI. Przy kosztownym wyliczeniu można memoizować wynik z poprawnymi zależnościami. UseMemo nie jest drugim źródłem prawdy ani obowiązkowym warunkiem poprawności filtra.'],
 ['Zaprojektuj wariant z tickets sterowanymi całkowicie przez props.','Nie kopiuj listy do lokalnego stanu bez potrzeby.','Jeśli rodzic jest właścicielem listy, przyjmij tickets i callback onClose(id), a lokalnie trzymaj tylko query i status. Visible wyliczaj z props i filtrów. Lokalny initialTickets jest innym świadomym modelem edytowalnego widoku.'],
 ['Zapisz JSX/JSDoc czystą funkcję dopasowania tytułu.','Funkcja nie potrzebuje hooka.','/** @param {{title:string}} ticket @param {string} query */\nexport function matchesTitle(ticket,query){return ticket.title.toLowerCase().includes(query.trim().toLowerCase());}'],
 ['Ustal, czy ukrycie wybranego rekordu przez filtr powinno automatycznie usuwać selectedId.','To decyzja produktu, nie własność filter.','Filter sam nie zmienia selectedId. Można zachować wybór poza bieżącym widokiem albo zaprojektować jawne czyszczenie. Karta wyliczana ze źródła i karta wyliczana z visible mają różne zachowanie; wybierz zgodnie z wymaganiem.'],
 ['Zaplanuj macierz filtrów: tekst, status, null osoba, brak tagów i brak wyników.','Przypadki brzegowe są częścią kontraktu.','Sprawdź pusty i biały tekst, różną wielkość liter, tytuł/tag/login, null assignee i tags=[], ALL oraz każdy status, brak wyników i reset. Sprawdź, że query i status łączą się przez AND.'],
 ['Wyjaśnij, dlaczego matchesQuery nie powinno zmieniać title na lowercase w rekordzie.','Normalizacja porównania nie jest edycją danych.','Wylicz lokalny znormalizowany napis. Nie przypisuj do ticket.title ani tags. Oryginalna pisownia powinna pozostać dostępna do wyświetlania i dalszej pracy.'],
 ['Oddaj Main z łączonymi filtrami, licznikiem, resetem i zamykaniem.','Nie dodawaj stanu visible ani efektu liczącego listę.',main],
];
const quiz=`
Co powinno być stanem query czy visible?|Query; visible jest wyliczane|Wyłącznie visible|Zawsze oba niezależnie|Żadne nigdy|Tekst edycji jest źródłem, wynik zależy od danych i filtrów.
Czy selectedTicket z id i listy musi być dodatkowym stanem?|Nie|Tak|Tylko w TSX|Tylko przy null|Można wyliczyć find.
Czy openCount z tickets musi być oddzielnym stanem?|Nie|Tak|Tylko w JSX|Tylko przy OPEN|Licznik jest funkcją aktualnych danych.
Dlaczego drugi filteredTickets bywa ryzykowny?|Wymaga synchronizacji ze wszystkimi źródłami zmian|Nie można przechowywać tablic w stanie|React zabrania filter|TypeScript nie zna tablic|Redundantne źródła mogą się rozjechać.
Czy proste filtrowanie wymaga useEffect?|Nie|Tak|Tylko w TSX|Tylko dla statusu|Wyliczenie można wykonać podczas renderu.
Co daje const visible=filterTickets(...) w renderze?|Wynik dla aktualnych wejść|Automatyczny zapis do API|Mutację ticketów|Nowy niezależny stan|Nie trzeba osobnego settera wyniku.
Czy każdy wynik zmieniający się w czasie jest niezależnym stanem?|Nie|Tak|Tylko w JSX|Tylko w TSX|Może być całkowicie wyliczany ze źródeł.
Czy props trzeba zawsze kopiować do lokalnego stanu?|Nie|Tak|Tylko dla Ticket|Tylko w TSX|Bez potrzeby dublowania można użyć props bezpośrednio.
Czy lokalny draft startujący z props bywa świadomym poprawnym modelem?|Tak|Nie|Tylko w JSX|Tylko dla formularzy bez CSS|Trzeba jasno ustalić właściciela i zasady resetu.
Co łączy filtr statusu i tekstu w tej lekcji?|AND|OR zawsze|Dodawanie stringów|Porównanie key|Rekord musi spełniać oba ograniczenia.
Co łączy przeszukiwane pola tekstowe?|OR|AND zawsze|Mnożenie|Sortowanie|Wystarczy dopasowanie tytułu, tagu albo osoby.
Co oznacza ALL?|Brak ograniczenia statusu|Nowy status biznesowy każdego ticketu|Wszystkie rekordy niezależnie od query zawsze|Pustą listę|Tekst nadal może ograniczać wyniki.
Czy ALL wyłącza także wyszukiwanie tekstowe?|Nie|Tak|Tylko w TSX|Tylko dla api|To tylko brak ograniczenia statusu.
Czy pusty query dopasowuje każdy ticket tekstowo?|Tak|Nie|Tylko z assignee|Tylko z tagiem|Ograniczenie statusu nadal może działać.
Po co trim query?|Usuwa skrajne białe znaki wyszukiwania|Mutuje tytuł|Usuwa wszystkie wewnętrzne spacje|Nadaje id|Sam surowy tekst pola może nadal zawierać wpis użytkownika.
Po co lowercase obu stron?|Dopasowanie niezależne od wielkości liter|Usunięcie polskich znaków|Sortowanie|Zmiana statusu|Normalizujemy tekst porównania.
Czy lowercase query bez lowercase tagów wystarczy dla mieszanej pisowni tagów?|Nie|Tak|Tylko w JSX|Tylko w TSX|Normalizacja powinna obejmować przeszukiwane wartości.
Jak obsłużyć null assignee?|Zamienić do porównania na pusty tekst|Wywołać toLowerCase na null|Usunąć ticket|Nadać login admin|Brak osoby nie powinien rzucać wyjątku.
Czy tags=[] powoduje, że some dopasuje dowolny tekst?|Nie|Tak|Tylko w JSX|Tylko dla API|Some na pustej tablicy daje false.
Czy includes szuka całego podciągu query?|Tak|Nie, automatycznie dzieli słowa|Tylko w TSX|Tylko w tytule|Wielowyrazowe query ma tu prosty kontrakt podciągu.
Czy query API UI znaczy automatycznie API w jednym polu i UI w drugim?|Nie|Tak|Tylko w JSX|Tylko dla tagów|To wymagałoby innego algorytmu.
Czy lowercasing usuwa diakrytyki z Żółć?|Nie|Tak|Tylko w TSX|Tylko dla title|Zolc i żółć nie są tym samym podciągiem.
Czy matchesQuery powinno mutować title do małych liter?|Nie|Tak|Tylko w JSX|Tylko dla OPEN|Normalizacja jest lokalnym obliczeniem.
Czy filter mutuje wejściową tablicę?|Nie|Tak|Tylko w TSX|Tylko z readonly|Tworzy nowy kontener.
Czy filter kopiuje głęboko pasujące rekordy?|Nie|Tak|Tylko w JSX|Tylko przy statusie|Referencje mogą pozostać wspólne.
Czy filtrowanie usuwa źródłowe rekordy?|Nie|Tak|Tylko w TSX|Tylko dla CLOSED|Zmienia widok, nie źródło.
Z czego liczyć N w Znaleziono N z M?|Visible.length|Zawsze tickets.length|Długości query|Liczby opcji|N oznacza bieżące wyniki.
Z czego liczyć M?|Tickets.length|Visible.length zawsze|Liczby statusów|Długości tytułu|M oznacza liczbę rekordów źródłowych.
Czy filtr do jednego wyniku musi zmniejszyć globalny total?|Nie|Tak|Tylko w TSX|Tylko w JSX|Globalne statystyki mają inny zakres niż visible.
Jak liczone jest urgent w przyjętym kontrakcie?|HIGH lub CRITICAL z wyłączeniem CLOSED|Każde CLOSED|Wyłącznie OPEN niezależnie od priorytetu|Wszystkie rekordy|Definicja licznika jest jawna.
Czy CLOSED CRITICAL zwiększa urgent w tej lekcji?|Nie|Tak|Tylko w TSX|Tylko w JSX|Kontrakt wyklucza zakończone zgłoszenia.
Czy IN_PROGRESS HIGH może zwiększać urgent?|Tak|Nie|Tylko z assignee|Tylko w JSX|Nie jest CLOSED i ma wysoki priorytet.
Czy zamknięcie ticketu przy filtrze OPEN powinno od razu zmienić visible?|Tak przy kolejnym renderze ze zmienionym źródłem|Nie|Tylko po osobnym setVisible|Tylko po przeładowaniu|Wyliczenie korzysta z aktualnego statusu.
Czy trzeba osobno aktualizować openCount po zamknięciu, gdy jest wyliczany?|Nie|Tak|Tylko w TSX|Tylko dla 1|Licznik policzy nowe źródło.
Co resetuje Wyczyść filtry?|Query do pustego tekstu i status do ALL|Usuwa wszystkie tickety|Zamyka wszystkie zgłoszenia|Zmienia priorytety|Reset dotyczy decyzji widoku.
Czy reset filtrów musi przywracać usunięte rekordy?|Nie|Tak|Tylko w JSX|Tylko dla ALL|Nie jest resetem danych źródłowych.
Czy pusty visible oznacza zawsze pustą bazę danych?|Nie|Tak|Tylko w TSX|Tylko przy ALL|Filtry mogą odrzucać wszystkie rekordy.
Jak nazwać pusty stan przy braku dopasowań?|Brak ticketów pasujących do filtrów|Błąd serwera zawsze|Ładowanie zawsze|Brak Reacta|Komunikat wyjaśnia rzeczywisty stan.
Co daje STATUS_OPTIONS as const?|Literalne wartości wspólnej listy|Automatyczne sprawdzenie DOM runtime|Zamrożenie API|Generację CSS|Typ filtra można wyprowadzić z elementów.
Co daje satisfies dla listy opcji?|Sprawdzenie dopuszczalnego kształtu i wartości|Pobieranie statusów|Mutację listy|Nowy setter|To statyczna kontrola danych.
Czy value select ma automatycznie typ StatusFilter?|Nie|Tak|Tylko w TSX|Tylko przy as const|DOM udostępnia string.
Co robi isStatusFilter?|Zawęża poprawny string do filtra|Rzutuje dowolny tekst bez sprawdzenia|Tworzy CSS|Ustawia stan zawsze|Guard porównuje wartość z listą opcji.
Czy as StatusFilter waliduje napis w runtime?|Nie|Tak|Tylko w strict|Tylko w TSX|Asercja nie wykonuje sprawdzenia.
Czy nieznane value w przygotowanym StatusSelect uruchamia callback?|Nie|Tak|Tylko w JSX|Tylko dla DONE|Guard ignoruje wartości spoza kontraktu.
Czy liczba rekordów sama dowodzi potrzeby useMemo?|Nie|Tak|Tylko od tysiąca|Tylko w TSX|Znaczenie ma koszt rzeczywistego obliczenia i środowisko.
Kiedy rozważyć memoizację?|Po pomiarze kosztownego wyliczenia|Zawsze przed napisaniem filter|Wyłącznie po błędzie TS|Nigdy|To optymalizacja, nie drugie źródło danych.
Czy useMemo jest zamiennikiem poprawnych zależności i czystego wyliczenia?|Nie|Tak|Tylko w JSX|Tylko dla statusu|Memoizacja musi opierać się na właściwych wejściach.
Czy filter plus efekt ustawiający drugi stan zawsze jest szybszy?|Nie|Tak|Tylko w TSX|Tylko z React 19|Dodatkowa synchronizacja może dodawać pracę i ryzyko rozjazdu.
Czy sam brak useEffect dowodzi poprawnego algorytmu filtrowania?|Nie|Tak|Tylko w JSX|Tylko dla ALL|Trzeba jeszcze sprawdzić warunki i dane.
Czy stan źródłowej listy powinien być niemutowalnie aktualizowany?|Tak|Nie|Tylko w TSX|Tylko przy filtrach|Wyliczenia zakładają poprawne nowe wersje danych.
Czy ukrycie rekordu przez filtr automatycznie czyści selectedId?|Nie|Tak|Tylko w JSX|Tylko dla OPEN|To osobna decyzja modelu UI.
Czy karta liczona ze źródła i karta liczona z visible mogą zachować się inaczej?|Tak|Nie|Tylko w TSX|Tylko przy null|Pierwsza może pokazać rekord ukryty przez filtr.
Czy wybór zakresu statystyk powinien być jawny?|Tak|Nie|Tylko w JSX|Tylko dla urgent|Inaczej użytkownik może porównywać niezgodne liczby.
Czy zachowanie pustego query należy testować razem ze statusem?|Tak|Nie|Tylko w TSX|Tylko przy CLOSED|Pusty tekst nie wyłącza filtra statusu.
Czy case-insensitive oznacza to samo co wyszukiwanie bez akcentów?|Nie|Tak|Tylko w JSX|Tylko dla Żółć|To inne reguły normalizacji.
Czy dodanie nowego ticketu do źródła powinno być widoczne w filtrze, gdy pasuje?|Tak|Nie|Tylko po setVisible|Tylko po efekcie|Aktualny render wylicza wynik z nowej listy.
Czy zmiana assignee może zmienić dopasowanie tekstowe?|Tak|Nie|Tylko w TSX|Tylko dla OPEN|Osoba jest jednym z przeszukiwanych pól.
Czy zmiana tagów może zmienić wynik bez zmiany query?|Tak|Nie|Tylko w JSX|Tylko po resecie|Wyliczenie zależy także od danych rekordów.
Czy title/tags/assignee powinny mieć określony kontrakt wejściowy?|Tak|Nie|Tylko w TSX|Tylko przy API|Funkcja nie musi akceptować dowolnego uszkodzonego obiektu bez walidacji.
Co potwierdza ukończenie lekcji danych wyliczanych?|Łączne filtry, aktualne liczniki i brak redundantnego stanu wyników|Samo useState|Wyłącznie select|Tylko brak błędów składni|Wynik musi reagować na wszystkie swoje źródła.
`.trim().split('\n').map(line=>line.split('|'));
const cases=[
 ['Bez tekstu i statusu','ids(filterTickets(tickets,"","ALL"))','1,2,3,4,5','Brak','1,4,5','3'],
 ['Białe query','ids(filterTickets(tickets,"   ","ALL"))','1,2,3,4,5','Brak','1,4,5','2'],
 ['Podciąg w tytule','ids(filterTickets(tickets,"błąd","ALL"))','1','Brak','3','1,3'],
 ['Mieszana pisownia tagu','ids(filterTickets(tickets,"backend","ALL"))','1','Brak','2','3'],
 ['Drugi tag','ids(filterTickets(tickets,"http","ALL"))','1','Brak','3','4'],
 ['Mieszana pisownia osoby','ids(filterTickets(tickets,"ANNA","ALL"))','1','Brak','2','4'],
 ['Tytuł eksportu','ids(filterTickets(tickets,"eksport","ALL"))','2','Brak','1','3'],
 ['Format eksportu','ids(filterTickets(tickets,"csv","ALL"))','2','Brak','1','4'],
 ['Tag raportu','ids(filterTickets(tickets,"raport","ALL"))','2','Brak','3','4'],
 ['Login Piotra','ids(filterTickets(tickets,"piotr","ALL"))','2','Brak','1','5'],
 ['Podciąg logowania','ids(filterTickets(tickets,"log","ALL"))','3','Brak','1','2'],
 ['Tag Auth','ids(filterTickets(tickets,"AUTH","ALL"))','3','Brak','1','2'],
 ['UI w tytule lub tagu','ids(filterTickets(tickets,"ui","ALL"))','4','Brak','1','2'],
 ['Login Oli','ids(filterTickets(tickets,"ola","ALL"))','4','Brak','1','5'],
 ['Polskie litery uppercase','ids(filterTickets(tickets,"ŻÓŁĆ","ALL"))','5','Brak','1','2'],
 ['Brak usuwania diakrytyków','ids(filterTickets(tickets,"zolc","ALL"))','Brak','5','1','1,2,3,4,5'],
 ['Podciąg ze spacją','ids(filterTickets(tickets,"w tytule","ALL"))','5','Brak','1','4'],
 ['Nieznane słowo','ids(filterTickets(tickets,"nieznane","ALL"))','Brak','1,2,3,4,5','1','3'],
 ['Brak dzielenia słów','ids(filterTickets(tickets,"api ui","ALL"))','Brak','1,3,4','1,3','4'],
 ['API z różnych pól','ids(filterTickets(tickets," api ","ALL"))','1,3','1','3','Brak'],
 ['Tylko OPEN','ids(filterTickets(tickets,"","OPEN"))','1,4,5','1,2,3,4,5','3','2'],
 ['Tylko IN_PROGRESS','ids(filterTickets(tickets,"","IN_PROGRESS"))','2','1,4,5','3','Brak'],
 ['Tylko CLOSED','ids(filterTickets(tickets,"","CLOSED"))','3','1,4,5','2','Brak'],
 ['API oraz OPEN','ids(filterTickets(tickets,"api","OPEN"))','1','1,3','3','Brak'],
 ['API oraz CLOSED','ids(filterTickets(tickets,"api","CLOSED"))','3','1,3','1','Brak'],
 ['API oraz IN_PROGRESS','ids(filterTickets(tickets,"api","IN_PROGRESS"))','Brak','2','1','3'],
 ['CSV oraz OPEN','ids(filterTickets(tickets,"csv","OPEN"))','Brak','2','1','1,4,5'],
 ['CSV oraz IN_PROGRESS','ids(filterTickets(tickets,"csv","IN_PROGRESS"))','2','Brak','1','3'],
 ['Wynik pustego źródła','ids(filterTickets([],"api","ALL"))','Brak','1','3','1,3'],
 ['Globalny total','summarize(tickets).total','5','3','2','1'],
 ['Globalne open','summarize(tickets).open','3','5','2','1'],
 ['Pilne niezakończone','summarize(tickets).urgent','2','3','1','5'],
 ['Open po zamknięciu id=1','summarize(closed).open','2','3','1','5'],
 ['Urgent po zamknięciu id=1','summarize(closed).urgent','1','2','3','0'],
 ['API OPEN po zamknięciu','ids(filterTickets(closed,"api","OPEN"))','Brak','1','3','1,3'],
 ['API CLOSED po zamknięciu','ids(filterTickets(closed,"api","CLOSED"))','1,3','1','3','Brak'],
 ['Guard poprawnej opcji','isStatusFilter("OPEN")','true','false','OPEN','undefined'],
 ['Guard nieznanej opcji','isStatusFilter("DONE")','false','true','DONE','undefined'],
 ['Guard filtra ALL','isStatusFilter("ALL")','true','false','ALL','undefined'],
 ['Guard niewłaściwej wielkości liter','isStatusFilter("open")','false','true','OPEN','undefined'],
];
const codeBase=model+'\n'+helpers+'\n'+sample+'\nfunction ids(items:readonly Ticket[]):string{return items.map(ticket=>ticket.id).join(",")||"Brak";}\nconst closed:readonly Ticket[]=tickets.map(ticket=>ticket.id===1?{...ticket,status:"CLOSED"}:ticket);';
for(const [label,expr,a,b,c,d] of cases)quiz.push([`${label}: co wypisze kod filtra?`,a,b,c,d,'Tekst normalizujemy po obu stronach, pola łączymy OR, status i tekst łączymy AND. Liczniki używają jawnie wskazanej listy.',codeBase+'\nconsole.log('+expr+');']);
module.exports={exercises,quiz,model,helpers,select,main,sample};
