const model=require('./practice_arch_05.cjs').model;
const context=`import {createContext,useContext,useReducer,useState} from "react";import type {Dispatch,ReactNode} from "react";
${model}
export const TicketsContext=createContext<State|null>(null);export const DispatchContext=createContext<Dispatch<Action>|null>(null);
export function TicketsProvider({children,initialTickets}:{children:ReactNode;initialTickets:readonly Ticket[]}){const [state,dispatch]=useReducer(reducer,initialTickets,init);return <TicketsContext value={state}><DispatchContext value={dispatch}>{children}</DispatchContext></TicketsContext>;}
export function useTickets():State{const state=useContext(TicketsContext);if(state===null)throw Error("useTickets wymaga TicketsProvider");return state;}
export function useTicketsDispatch():Dispatch<Action>{const dispatch=useContext(DispatchContext);if(dispatch===null)throw Error("useTicketsDispatch wymaga TicketsProvider");return dispatch;}`;
const consumers=`${context}
export function CloseButton({id}:{id:number}){const dispatch=useTicketsDispatch();return <button type="button" onClick={()=>dispatch({type:"closed",id})}>Zamknij #{id}</button>;}
export function SelectButton({id,title}:{id:number;title:string}){const dispatch=useTicketsDispatch();return <button type="button" onClick={()=>dispatch({type:"selected",id})}>{title}</button>;}
export function SelectedCard(){const state=useTickets();const selected=state.tickets.find(ticket=>ticket.id===state.selectedId);return selected?<article><h2>{selected.title}</h2><p>{selected.status}</p><CloseButton id={selected.id}/></article>:<p>Wybierz ticket.</p>;}
export function TicketList(){const {tickets}=useTickets();const [query,setQuery]=useState("");const q=query.trim().toLowerCase();const visible=tickets.filter(ticket=>ticket.title.toLowerCase().includes(q));return <section><label>Szukaj<input value={query} onChange={event=>setQuery(event.currentTarget.value)}/></label><p>Wyników: {visible.length}</p><ul>{visible.map(ticket=><li key={ticket.id}><SelectButton id={ticket.id} title={ticket.title}/><span>{ticket.status}</span><CloseButton id={ticket.id}/></li>)}</ul></section>;}
function Layout(){return <main><h1>Tickety</h1><TicketList/><SelectedCard/></main>;}
export function TicketApp({tickets}:{tickets:readonly Ticket[]}){return <TicketsProvider initialTickets={tickets}><Layout/></TicketsProvider>;}`;
const exercises=[
['Utwórz dwa konteksty: State i Dispatch<Action>.','Rozdziel dane od stabilnej funkcji zmian.',context],
['Przenieś useReducer do TicketsProvider.','Komponent Main nie powinien tworzyć drugiego stanu tych samych ticketów.',context],
['Użyj null jako fallback obu kontekstów.','Hooki sprawdzają brak providera.',context],
['Napisz useTickets zwracające pełny State.','Po guardzie nie ma null.',context],
['Napisz useTicketsDispatch z Dispatch<Action>.','Każdy konsument korzysta z tej samej unii.',context],
['Podłącz init jako trzeci argument useReducer.','Inicjalizacja nie jest synchronizacją props co render.',context],
['Umieść oba providery nad całym Layout.','Lista i karta muszą mieć wspólne źródło.',consumers],
['Zbuduj CloseButton czytający tylko dispatch.','Nie odczytuj stanu, którego nie używasz.',consumers],
['Zbuduj SelectButton wysyłający selected.','Przekaż jedynie potrzebne props id i title.',consumers],
['Zbuduj SelectedCard czytającą aktualny stan.','Find wylicza rekord po ID.',consumers],
['Zachowaj query lokalnie w TicketList.','Nie dodawaj każdego znaku do kontekstu danych.',consumers],
['Wylicz widoczną listę bez nowego setVisible.','Context udostępnia źródło, filtr jest danymi pochodnymi.',consumers],
['Nie przekazuj tickets i dispatch przez pośredni Layout.','Layout jest czystą kompozycją.',consumers],
['Przekaż dispatch jako value bez opakowania w nowy obiekt.','Dispatch z useReducer ma stabilną tożsamość.',context],
['Nie obiecuj, że dispatch-only komponent nigdy nie renderuje ponownie.','Aktualizacja rodzica i props to inne przyczyny renderu.','Osobny kontekst dispatch eliminuje subskrypcję zmian kontekstu State w takim komponencie. Nie blokuje renderowania z powodu rodzica, zmiany props ani własnego stanu. Ewentualną optymalizację oceniaj pomiarem.'],
['Wyjaśnij zakres jednego providera.','Konsument musi być potomkiem w drzewie React.','Provider obejmuje jego poddrzewo. Komponent renderowany obok providera nie odczyta jego wartości. Umieść wspólnego providera ponad listą, kartą i pozostałymi konsumentami, a nie tylko wokół jednego fragmentu.'],
['Zademonstruj dwa niezależne TicketsProvider.','Nie są dwoma oknami na jeden automatyczny store.',consumers+'\nexport function TwoBoards({a,b}:{a:readonly Ticket[];b:readonly Ticket[]}){return <><TicketApp tickets={a}/><TicketApp tickets={b}/></>;}'],
['Zademonstruj najbliższy provider w zagnieżdżeniu.','Wewnętrzna instancja przesłania odpowiedni kontekst.',consumers+'\nexport function NestedBoards({a,b}:{a:readonly Ticket[];b:readonly Ticket[]}){return <TicketsProvider initialTickets={a}><TicketList/><TicketsProvider initialTickets={b}><TicketList/></TicketsProvider></TicketsProvider>;}'],
['Sprawdź hook useTickets poza providerem.','Błąd jest częścią kontraktu.',consumers],
['Sprawdź hook dispatch poza providerem.','Sam provider State nie dostarcza funkcji Dispatch.',consumers],
['Sprawdź brak payloadu assigned w odległym konsumencie.','Typ z unii reducera dotarł do przycisku.',context+'\nexport function BadAction(){const dispatch=useTicketsDispatch();\n// @ts-expect-error: brak assignee\ndispatch({type:"assigned",id:0});return null;}'],
['Nie twórz context wewnątrz TicketsProvider.','Provider i hook muszą używać tej samej tożsamości kontekstu.',context],
['Zachowaj reducer jako osobną czystą odpowiedzialność.','Context zmienia dostęp, nie zasady przejść.',context],
['Skomponuj provider preferencji i provider ticketów na poziomie aplikacji.','Nie łącz wszystkich wartości w jeden worek.','AppProviders może otaczać children przez PrefsProvider i TicketsProvider. Każda instancja posiada własną odpowiedzialność. Kolejność ma znaczenie, gdy provider korzysta z kontekstu drugiego; umieść zależność powyżej konsumenta.'],
['Wyjaśnij różnicę między context a mechanizmem cache żądań.','To transport wartości poddrzewa.','Context nie dodaje automatycznie pobierania, cache, deduplikacji, odświeżania ani synchronizacji między kartami. Reducer opisuje przejścia stanu lokalnej instancji. Pozostałe mechanizmy trzeba zaprojektować osobno.'],
['Zostaw filtry i paginację blisko ich ekranu.','Nie wszyscy konsumenci danych potrzebują tej samej wyszukiwarki.',consumers],
['Sprawdź wybór ID=0 w SelectedCard.','Sprawdzaj znaleziony obiekt, nie truthiness liczby.',consumers],
['Nie synchronizuj dwóch providerów wzajemnymi efektami.','Jeśli potrzebujesz wspólnego stanu, użyj wspólnego właściciela.','Dwie niezależne instancje providera celowo tworzą dwa stany. Gdy ekran wymaga jednego współdzielonego źródła, umieść jeden provider ponad oboma konsumentami zamiast kopiować stan między instancjami.'],
['Przetestuj zgodność listy i karty po closed oraz deleted.','Obie czytają ten sam reducer.',consumers],
['Zbuduj ekran bez prop drillingu dla danych i dispatch.','Sprawdź kontrakt błędów oraz lokalność query.',consumers],
];
const concepts=[
['Dlaczego dwa konteksty?','Oddzielają zmienny stan od stabilnego dispatch','Automatycznie tworzą dwa store','Wyłączają renderowanie','Zastępują reducer'],
['Czy dispatch z useReducer jest stabilny?','Tak','Nie, zawsze nowy','Tylko po memo','Tylko w TSX'],
['Co powinien czytać CloseButton?','Tylko dispatch','Cały State obowiązkowo','Wszystkie preferencje','Kopię ticketów'],
['Czy dispatch-only konsument nigdy nie renderuje?','Może renderować z innych powodów','Nigdy nie renderuje ponownie','Tylko pierwszy raz na zawsze','Tylko przy CSS'],
['Gdzie ma być useReducer tego źródła?','W TicketsProvider','W każdym konsumencie osobno','W createContext','W pliku CSS'],
['Co jest wartością TicketsContext?','State','Dispatch<Action>','Zawsze null','Tylko query'],
['Co jest wartością DispatchContext?','Dispatch<Action>','Cała lista ticketów','Każdy handler DOM','Wynik fetch'],
['Czy context zmienia czystość reducera?','Nie','Tak, pozwala fetch w reducer','Tak, pozwala mutacje','Tylko w JSX'],
['Co robi hook poza providerem w przykładzie?','Rzuca czytelny błąd','Tworzy automatyczny store','Zawsze zwraca pustą listę','Wysyła HTTP'],
['Czy provider obok komponentu go obejmuje?','Nie','Tak','Tylko w React 19','Tylko przy tym samym key'],
['Ile stanów mają dwa niezależne TicketsProvider?','Dwa','Jeden automatycznie','Zero','Zależy od className'],
['Jak mieć wspólny stan dla listy i karty?','Jeden provider ponad oboma','Dwa providery synchronizowane CSS','Import dwóch reducerów do każdego','Losowe key'],
['Gdzie pozostało query?','Lokalnie w TicketList','W DispatchContext','W każdej akcji reducer','W modelu Ticket'],
['Po co Dispatch<Action> w TS?','Przenosi kontrakt akcji do konsumenta','Waliduje dowolny JSON','Generuje callback','Gwarantuje HTTP 200'],
['Czy useTicketsDispatch wymaga odczytu State?','Nie','Tak','Tylko dla closed','Tylko w SSR'],
['Co wylicza SelectedCard?','Aktualny rekord z listy i selectedId','Osobną kopię stanu','Nowe ID','Wszystkie filtry'],
['Czy ID=0 jest poprawnym wyborem?','Tak, gdy istnieje','Nie','Tylko przy null','Tylko w Map'],
['Czy zmiana initialTickets co render automatycznie resetuje reducer?','Nie','Tak','Tylko przy init','Tylko w TSX'],
['Jak dodać wspólną obsługę akcji?','Rozszerzyć reducer i unię Action','Edytować każdą kopię listy','Zmienić fallback context','Dodać className'],
['Czy Context jest cache danych sieciowych sam w sobie?','Nie','Tak','Tylko z reducer','Tylko z async'],
['Czy dwa konteksty są automatyczną selekcją pojedynczych pól State?','Nie','Tak','Tylko dla selectedId','Tylko z readonly'],
['Co daje podział preferencji i ticketów?','Oddzielne zakresy odpowiedzialności i aktualizacji','Zawsze zero renderów','Wspólny globalny count','Automatyczne persistence'],
['Co określa kolejność AppProviders?','Zależności i zakres konsumentów','Zawsze alfabet','Liczba importów','Długość nazw'],
['Jak porównywane jest value kontekstu?','Object.is','Deep equal wszystkich rekordów','Wyłącznie JSON.stringify','Według długości listy'],
['Czy nowy obiekt value={{dispatch}} jest tym samym co dispatch jako value?','Nie, obiekt ma nową tożsamość','Tak zawsze','Tylko w dev','Tylko przy null'],
['Czy memo blokuje aktualizację odczytywanego State context?','Nie','Tak zawsze','Tylko dla tabeli','Tylko w TSX'],
['Co robi pośredni Layout?','Komponuje dzieci bez przekazywania danych ticketów','Przechowuje drugą listę','Generuje ID','Waliduje wszystkie akcje'],
['Czy wszystkie props trzeba usunąć po dodaniu context?','Nie, lokalne dane nadal mogą być props','Tak','Tylko id','Tylko title'],
['Czy kolejna instancja context o tej samej nazwie jest tą samą tożsamością?','Nie','Tak','Tylko w TypeScript','Tylko jeśli fallback=null'],
['Jak testować zakres providera?','Renderować konsumentów wewnątrz, poza i w zagnieżdżeniu','Wyłącznie liczyć importy','Tylko sprawdzić CSS','Dodać useEffect w reducer'],
];
const quiz=concepts.map(row=>[...row,'Provider przechowuje stan reducera, konteksty udostępniają go i dispatch, a konsumenci deklarują tylko potrzebne zależności.']);quiz.push(...require('./practice_arch_06.cjs').quiz.slice(0,30).map(row=>['Powtórka kontraktu Context: '+row[0],...row.slice(1)]));quiz.push(...require('./practice_arch_05.cjs').quiz.filter(row=>row[6]).slice(0,40).map(row=>['Akcja ze współdzielonego dispatch — '+row[0],...row.slice(1)]));
const project={'src/Tickets.tsx':consumers,'src/main.tsx':'import {createRoot} from "react-dom/client";import {TicketApp} from "./Tickets";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<TicketApp tickets={[{id:0,title:"API",status:"OPEN",assignee:null,createdAt:"2026-10-29T10:00:00Z",tags:[]}]}/>);'};
module.exports={model,context,consumers,exercises,quiz,project};
