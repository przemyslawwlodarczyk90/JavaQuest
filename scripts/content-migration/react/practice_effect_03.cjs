const model=`export function validDelay(delay:number,allowZero=false):boolean{return Number.isSafeInteger(delay)&&delay>=(allowZero?0:1)&&delay<=2147483647;}
export type ShortcutEvent=Pick<KeyboardEvent,"key"|"altKey"|"shiftKey"|"ctrlKey"|"metaKey"|"repeat"|"defaultPrevented"|"isComposing">;export function isNewTicketShortcut(event:ShortcutEvent):boolean{return event.key.toLowerCase()==="k"&&event.altKey&&event.shiftKey&&!event.ctrlKey&&!event.metaKey&&!event.repeat&&!event.defaultPrevented&&!event.isComposing;}
export function runQueue(initial:number,updates:ReadonlyArray<number|((value:number)=>number)>):number{return updates.reduce<number>((value,update)=>typeof update==="function"?update(value):update,initial);}`;
const interval=`import {useEffect,useState} from "react";
${model}
export function TickCounter({intervalMs=10000}:{intervalMs?:number}){const [count,setCount]=useState(0);useEffect(()=>{if(!validDelay(intervalMs))return;const id=setInterval(()=>setCount(previous=>previous+1),intervalMs);return ()=>clearInterval(id);},[intervalMs]);return <section><p>Wywołania interwału: {count}</p>{!validDelay(intervalMs)&&<p role="alert">Podaj dodatni poprawny czas interwału.</p>}</section>;}`;
const debounce=`import {useEffect,useState} from "react";
${model}
export function useDebouncedValue<T>(value:T,delayMs=300):T{if(!validDelay(delayMs,true))throw RangeError("Niepoprawne opóźnienie");const [debounced,setDebounced]=useState<T>(()=>value);useEffect(()=>{const id=setTimeout(()=>setDebounced(()=>value),delayMs);return ()=>clearTimeout(id);},[value,delayMs]);return debounced;}
export function SearchDemo({items}:{items:readonly string[]}){const [query,setQuery]=useState("");const settled=useDebouncedValue(query);const q=settled.trim().toLowerCase();const visible=items.filter(item=>item.toLowerCase().includes(q));return <section><label>Szukaj<input value={query} onChange={event=>setQuery(event.currentTarget.value)}/></label><p>Filtr zastosowany: {settled||"brak"}</p><ul>{visible.map((item,index)=><li key={index}>{item}</li>)}</ul></section>;}`;
const shortcut=`import {useEffect} from "react";
${model}
export function useNewTicketShortcut(onCreate:()=>void){useEffect(()=>{function handle(event:KeyboardEvent){if(!isNewTicketShortcut(event))return;const target=event.target;if(target instanceof HTMLElement&&(target.isContentEditable||target.closest("input,textarea,select")))return;event.preventDefault();onCreate();}window.addEventListener("keydown",handle);return ()=>window.removeEventListener("keydown",handle);},[onCreate]);}
export function ShortcutDemo({onCreate}:{onCreate:()=>void}){useNewTicketShortcut(onCreate);return <p>Skrót demonstracyjny: Alt+Shift+K poza polami edycji. Obsługa zależy także od systemu i przeglądarki.</p>;}`;
const sync=`import {useEffect,useState} from "react";export function FiltersSync({status,query}:{status:string;query:string}){const [error,setError]=useState<string|null>(null);useEffect(()=>{try{localStorage.setItem("devdesk-filters",JSON.stringify({status,query}));setError(null);}catch{setError("Nie zapisano filtrów.");}},[status,query]);return error?<p role="status">{error}</p>:null;}`;
const exercises=[
['Zbuduj TickCounter z updaterem previous=>previous+1.','Nie odczytuj count ze starego renderu w timerze.',interval],
['Dodaj intervalMs jako zależność efektu.','Nowy czas wymaga posprzątania starego interwału.',interval],
['Anuluj dokładnie utworzony interwał przez clearInterval.','Id jest zamknięte w cleanup.',interval],
['Waliduj zakres opóźnienia i nie uruchamiaj niepoprawnego timera.','Przeglądarkowe timery mają ograniczenia liczbowe.',interval],
['Nie nazywaj samego licznika pobieraniem danych.','Timer nie wykonuje requestu.','TickCounter liczy wywołania interwału. Nie pobiera ticketów i nie potwierdza dostępności serwera. Automatyczne odświeżanie danych potrzebuje osobnej warstwy pobierania i kontroli równoległych żądań.'],
['Zademonstruj trzy bezpośrednie setCount(count+1) jako model kolejki.','Wszystkie zamykają ten sam count.',model+'\nconst count=0;console.log(runQueue(count,[count+1,count+1,count+1]));'],
['Zademonstruj trzy updatery dodające po jednym.','Każdy otrzymuje poprzedni wynik.',model+'\nconsole.log(runQueue(0,[value=>value+1,value=>value+1,value=>value+1]));'],
['Wyjaśnij, że dodanie count do deps nie jest tą samą implementacją co updater.','Może naprawić świeżość, lecz restartuje interwał.','Wersja z [count,intervalMs] odtwarza interwał po zmianie count. Updater eliminuje potrzebę czytania count i zachowuje działający interwał, dopóki intervalMs się nie zmieni. Wybór zależy od pożądanej synchronizacji.'],
['Zbuduj generyczne useDebouncedValue.','Wartość początkowa jest dostępna od pierwszego renderu.',debounce],
['Kasuj poprzedni timeout po zmianie value.','Tylko ostatnia oczekująca aktualizacja powinna dotrzeć do stanu.',debounce],
['Uwzględnij delayMs w zależnościach debounce.','Zmiana opóźnienia też zastępuje timer.',debounce],
['Zachowaj natychmiastową edycję inputa przy opóźnionym filtrowaniu.','Input używa query, lista settled.',debounce],
['Obsłuż T będące funkcją jako przechowywaną wartość.','Inicjalizacja i setter wymagają wrapperów.',debounce],
['Sprawdź delay=0 jako dozwolony timeout asynchroniczny.','Zero nie oznacza synchronicznego wywołania.',debounce],
['Nie myl debounce z anulowaniem requestu.','Skasowanie timera nie cofa już wysłanego zapytania.','Debounce opóźnia rozpoczęcie reakcji na wartość. Gdy request już wystartował, jego zakończenie wymaga osobnej obsługi anulowania lub ignorowania nieaktualnego wyniku. Nie obiecuj tego samym clearTimeout.'],
['Napisz isNewTicketShortcut sprawdzające modyfikatory i key.','Przykład używa Alt+Shift+K zamiast polegać na zarezerwowanym Ctrl+N.',model],
['Nie uruchamiaj skrótu dla repeat i isComposing.','Wpisywanie znaków nie powinno wielokrotnie uruchamiać akcji.',model],
['Pomiń zdarzenie już obsłużone przez inny handler.','DefaultPrevented jest częścią kontraktu.',model],
['Nie przechwytuj skrótu podczas edycji input, textarea, select i contenteditable.','Sprawdź aktywny cel zdarzenia.',shortcut],
['Podepnij listener z zależnością onCreate.','Nowy callback ma zastąpić poprzedni.',shortcut],
['Usuń ten sam handler i ten sam typ zdarzenia w cleanup.','Nie twórz nowej anonimowej funkcji do removeEventListener.',shortcut],
['Wyjaśnij restart listenera przy nowej lambdzie rodzica.','To poprawne, choć może być zbędną pracą.','OnCreate utworzone jako nowa funkcja zmienia zależność. React sprząta stary listener i podpina nowy z aktualnym callbackiem. Optymalizacja nie polega na usunięciu zależności; dalsze wzorce poznasz przy useEffectEvent.'],
['Zapisuj filtry jako obiekt utworzony wewnątrz efektu.','Zależy od status oraz query, nie od nowego obiektu filters.',sync],
['Obsłuż wyjątek setItem bez pętli aktualizacji.','Ustawienie tego samego error nie wymaga nowego obiektu co render.',sync],
['Nie twierdź, że sam setItem zawsze tworzy pętlę renderów.','Zapis storage nie jest automatycznie setState.','Nowy obiekt w deps może nadmiernie uruchamiać efekt. Sama operacja localStorage.setItem nie wywołuje automatycznie renderu React. Pętla wymaga także ścieżki aktualizacji stanu lub innego mechanizmu ponawiającego render.'],
['Rozróżnij kontrolę TypeScript od exhaustive-deps.','Tsc nie analizuje kompletności zależności.','Kod z brakującym count w deps może być poprawny typowo. Potrzebna jest reguła lintowania hooków oraz test zachowania. Sam pozytywny wynik tsc nie potwierdza świeżości domknięć.'],
['Nie wyciszaj missing dependency jako naprawy starej wartości.','Zmień implementację lub popraw deps.',interval],
['Przenieś stałą niezależną od renderu na poziom modułu.','Nie musi być zależnością, jeśli jest stabilną definicją modułu.',model],
['Sprawdź model Object.is dla obiektów i funkcji.','Ta sama zawartość nie oznacza tej samej referencji.',model+'\nconsole.log(Object.is({value:1},{value:1}));const object={value:1};console.log(Object.is(object,object));'],
['Przetestuj dwa ticki, zmianę interwału, debounce i wymianę callbacku.','Użyj kontrolowanych timerów i cleanup; interakcje klawiatury sprawdź w DOM.',interval],
];
const concepts=[
['Czym jest stale closure?','Funkcją korzystającą z nieaktualnie zamkniętych wartości','Błędem samego typu number','Brakiem className','Zawsze awarią serwera'],
['Co widzi timer z count z pierwszego renderu?','Wartość zamkniętą w tamtym renderze','Zawsze najnowszy count automatycznie','Tylko null','Cały reducer'],
['Jak updater usuwa zależność od count?','Otrzymuje poprzedni stan jako argument','Magicznie zmienia closure','Czyta DOM','Usuwa stan'],
['Czy intervalMs nadal jest zależnością?','Tak','Nie','Tylko w JSX','Tylko gdy 10000'],
['Czy dodanie count do deps może restartować interwał?','Tak','Nie','Tylko po SSR','Tylko z null'],
['Czy tsc wykrywa missing dependency?','Nie','Tak zawsze','Tylko strict','Tylko w TSX'],
['Co sprawdza exhaustive-deps?','Zależności reaktywne odczytywane przez efekt','Wszystkie uprawnienia API','Każdy styl CSS','Wyłącznie błędy number'],
['Czy nowy obiekt o tej samej zawartości jest Object.is równy staremu?','Nie','Tak','Tylko dla filters','Tylko z readonly'],
['Czy nowa funkcja może zmienić deps?','Tak','Nie','Tylko dla async','Tylko w production'],
['Jak zmniejszyć zbędną zależność filters obiektu?','Utworzyć go wewnątrz efektu z prostych deps','Usunąć wszystkie deps','Wyciszyć lint','Zmienić jego nazwę'],
['Czy sam setItem zawsze powoduje pętlę React?','Nie','Tak','Tylko przy JSON','Tylko z effect'],
['Co zwraca useDebouncedValue początkowo?','Początkową wartość','Zawsze pusty string','Promise','Null obowiązkowo'],
['Co resetuje oczekujący debounce?','Zmiana value lub delay z cleanup','Każde kliknięcie dowolnego button','Sam odczyt settled','Zmiana CSS'],
['Czy debounce anuluje już wysłane żądanie?','Nie','Tak','Tylko z generykiem','Tylko z clearTimeout'],
['Czy delay=0 wykonuje timeout synchronicznie?','Nie','Tak','Tylko w Node','Tylko przy debounce'],
['Po co useState(()=>value) w generycznym hooku?','Przechowuje także value będące funkcją bez jej wywoływania','Zawsze przyspiesza render','Usuwa deps','Zamienia value w Promise'],
['Po co setDebounced(()=>value)?','Pozwala zapisać funkcję jako wartość','Zawsze wykonuje value','Kasuje timer','Wymusza tsc'],
['Dlaczego nie polegać na Ctrl+N strony?','Przeglądarka może zarezerwować to polecenie','React zabrania klawisza N','TypeScript usuwa event','Zawsze jest to mouse event'],
['Kiedy ignorować globalny skrót?','Podczas wpisywania w polu lub kompozycji','Zawsze przy body','Tylko po sukcesie API','Tylko w production'],
['Co chroni repeat guard?','Przed wielokrotną reakcją podczas przytrzymania','Przed wszystkimi duplikatami backendu','Przed błędem storage','Przed renderem rodzica'],
['Co musi zgadzać się przy removeEventListener?','Typ i funkcja oraz odpowiedni tryb capture','Tylko nazwa zmiennej','Tylko tekst handlera','Tylko label'],
['Jak zapewnić aktualny onCreate w prostym hooku?','Wpisać onCreate w deps i wymieniać listener','Usunąć go z deps','Zapisać raz bez dalszej polityki','Zmienić key textarea'],
['Czy nowa lambda rodzica oznacza automatycznie błąd działania?','Nie, może jedynie restartować synchronizację','Tak zawsze','Tylko w TSX','Tylko w React 19'],
['Gdzie użyć efektu zamiast handlera?','Do utrzymywania zewnętrznej zgodności z aktualnym stanem','Do każdego obliczenia','Zawsze do submit','Do zmiany lokalnego highlight po kliknięciu'],
['Czy setCount jest stabilnym setterem React?','Tak','Nie','Tylko po memo','Tylko w JSX'],
['Czy dispatch z useReducer jest stabilny?','Tak','Nie','Tylko po pierwszym fetch','Tylko z Context'],
['Czy pominięcie props z deps aktualizuje stare closure?','Nie','Tak','Tylko z strict','Tylko z as const'],
['Co robi cleanup debounce?','Kasuje oczekujący timeout tej wersji','Resetuje wszystkie query','Zapisuje wynik do serwera','Montuje nowy input'],
['Czy nazwa AutoRefresh oznacza, że kod naprawdę pobiera API?','Nie, trzeba sprawdzić implementację','Tak','Tylko w TSX','Tylko po 10 sekundach'],
['Jak testować poprawkę stale count?','Wykonać kilka callbacków timera i sprawdzić przyrost','Tylko uruchomić tsc','Policzyć deps','Zmienić label'],
];
const quiz=concepts.map(row=>[...row,'Efekt i jego callbacki zamykają konkretne wartości. Zależności oraz updatery określają, kiedy synchronizacja powinna być odtwarzana.']);quiz.push(...require('./practice_effect_02.cjs').quiz.slice(0,30).map(row=>['Powtórka synchronizacji i deps: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const delay of [-1,0,1,300,10000,2147483647,2147483648,NaN,Infinity,1.5])for(const zero of [false,true])add('Delay '+delay+' zero '+zero,`validDelay(${String(delay)},${zero})`,Number.isSafeInteger(delay)&&delay>=(zero?0:1)&&delay<=2147483647);
for(const [label,expr,a]of [['Trzy bezpośrednie','runQueue(0,[1,1,1])',1],['Trzy updatery','runQueue(0,[v=>v+1,v=>v+1,v=>v+1])',3],['Ustawienie potem updater','runQueue(0,[5,v=>v+1])',6],['Updater potem ustawienie','runQueue(0,[v=>v+1,5])',5],['Stare count','(()=>{const count=0;const callback=()=>count+1;return callback()+callback();})()',2],['Nowy obiekt deps','Object.is({query:"a"},{query:"a"})',false],['Ten sam string','Object.is("a","a")',true],['Inny delay','Object.is(300,500)',false],['Nowa funkcja','Object.is(()=>1,()=>1)',false],['Bazowy skrót','isNewTicketShortcut(event)',true],['Inny klawisz','isNewTicketShortcut({...event,key:"n"})',false],['Duże K','isNewTicketShortcut({...event,key:"K"})',true],['Repeat','isNewTicketShortcut({...event,repeat:true})',false],['Composing','isNewTicketShortcut({...event,isComposing:true})',false],['Default prevented','isNewTicketShortcut({...event,defaultPrevented:true})',false],['Ctrl dodatkowy','isNewTicketShortcut({...event,ctrlKey:true})',false],['Brak shift','isNewTicketShortcut({...event,shiftKey:false})',false],['Brak alt','isNewTicketShortcut({...event,altKey:false})',false],['Meta dodatkowy','isNewTicketShortcut({...event,metaKey:true})',false],['Updater krok','runQueue(10,[v=>v+2,v=>v+2])',14]])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik domknięcia lub zależności?',a,...wrong,'To model funkcji JavaScript i walidacji argumentów, nie pomiar harmonogramu React. Updater używa wyniku poprzedniego kroku.',model+'\nconst event:ShortcutEvent={key:"k",altKey:true,shiftKey:true,ctrlKey:false,metaKey:false,repeat:false,defaultPrevented:false,isComposing:false};console.log('+expr+');']);}
const project={'src/Interval.tsx':interval,'src/Debounce.tsx':debounce,'src/Shortcut.tsx':shortcut,'src/FiltersSync.tsx':sync,'src/main.tsx':'import {createRoot} from "react-dom/client";import {TickCounter} from "./Interval";import {SearchDemo} from "./Debounce";import {ShortcutDemo} from "./Shortcut";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<main><h1>Zależności efektów</h1><TickCounter intervalMs={1000}/><SearchDemo items={["API timeout","Logowanie","Raport"]}/><ShortcutDemo onCreate={()=>console.log("Utwórz ticket")}/></main>);'};
module.exports={model,interval,debounce,shortcut,sync,exercises,quiz,project};
