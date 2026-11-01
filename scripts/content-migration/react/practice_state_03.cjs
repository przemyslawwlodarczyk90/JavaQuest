const direct='import {useState} from "react";\nexport function DirectCounter(){const [count,setCount]=useState(0);function addThree(){setCount(count+1);setCount(count+1);setCount(count+1);console.log(count);}return <button type="button" onClick={addThree}>Komentarze: {count}</button>;}';
const functional='import {useState} from "react";\nexport function CommentCounter(){const [count,setCount]=useState(0);function addThree(){setCount(prev=>prev+1);setCount(prev=>prev+1);setCount(prev=>prev+1);}return <button type="button" onClick={addThree}>Komentarze: {count}</button>;}';
const watch='import {useState} from "react";\nexport function WatchToggle(){const [watching,setWatching]=useState(false);return <button type="button" aria-pressed={watching} onClick={()=>setWatching(previous=>!previous)}>{watching?"Obserwujesz":"Obserwuj"}</button>;}';
const delayed='import {useState} from "react";\nexport function DelayedReport({onReport}:{onReport:(count:number)=>void}){const [count,setCount]=useState(0);function reportLater(){setTimeout(()=>onReport(count),3000);}return <div><button type="button" onClick={()=>setCount(previous=>previous+1)}>+1 ({count})</button><button type="button" onClick={reportLater}>Raport za 3 s</button></div>;}';
const queue='type Update=number|((previous:number)=>number);\nexport function applyQueue(start:number,updates:readonly Update[]):number{return updates.reduce<number>((value,update)=>typeof update==="function"?update(value):update,start);}';
const exercises=[
 ['Uruchom logiczny przykład DirectCounter i wyjaśnij wynik trzech setterów count+1.','Każde wyrażenie widzi count z tego samego renderu.',direct],
 ['Popraw licznik, aby jedno kliknięcie dodawało trzy przez trzy updatery.','Każdy updater otrzymuje wynik poprzedniej pozycji kolejki.',functional],
 ['Uprość dodawanie trzech do jednego czystego updatera.','Jeśli operacja ma jeden prosty zamiar, nie trzeba trzech wywołań.','import {useState} from "react";\nexport function AddThree(){const [count,setCount]=useState(0);return <button type="button" onClick={()=>setCount(previous=>previous+3)}>{count}</button>;}'],
 ['Wyjaśnij log count tuż po setCount(count+1).','Setter nie przypisuje nowej wartości do lokalnego const.','Log nadal pokazuje count z bieżącego renderu. Kolejny render otrzyma wynik aktualizacji. Stara funkcja handlera i opóźnione funkcje mogą nadal zamykać starą migawkę.'],
 ['Napisz WatchToggle z funkcyjnym odwróceniem boolean.','Nowy stan zależy od poprzedniego.',watch],
 ['Porównaj dwa odwrócenia !watching z dwoma updaterami prev=>!prev w jednym handlerze.','Dwa zastąpienia i dwie transformacje dają różne kolejki.','Dla początkowego false dwa setWatching(!watching) z tej samej migawki ustawiają true. Dwa setWatching(prev=>!prev) przechodzą false→true→false.'],
 ['Zbuduj czysty model kolejki liczb: wartość zastępuje, funkcja przelicza.','To pomoc dydaktyczna, nie implementacja wewnętrznego schedulera React.',queue],
 ['Oblicz kolejkę start=0: 5, +1, ×2, 42.','Późniejsze zastąpienie nadpisuje wcześniejszy wynik.',queue+'\nconsole.log(applyQueue(0,[5,n=>n+1,n=>n*2,42]));'],
 ['Oblicz kolejkę start=2: +1, ×2. Porównaj z ×2, +1.','Kolejność operacji ma znaczenie.',queue+'\nconsole.log(applyQueue(2,[n=>n+1,n=>n*2]));\nconsole.log(applyQueue(2,[n=>n*2,n=>n+1]));'],
 ['Napisz licznik opóźnionego +1 bez utraty przyrostów przez starą migawkę.','Updater odwołuje się do poprzedniego stanu w kolejce.','import {useState} from "react";\nexport function DelayedIncrement(){const [count,setCount]=useState(0);return <button type="button" onClick={()=>setTimeout(()=>setCount(previous=>previous+1),1000)}>Dodaj później: {count}</button>;}'],
 ['Napisz DelayedReport celowo raportujący liczbę z chwili zlecenia.','Stara migawka może być wymaganym zachowaniem.',delayed],
 ['Wyjaśnij wynik raportu zleconego przy count=2 po późniejszym zwiększeniu do 3.','Zamknięcie przechowuje wartość swojego renderu.','Raport pokaże 2, jeżeli kod raportuje count z handlera utworzonego w tamtym renderze. To nie błąd, gdy etykieta i wymaganie mówią o chwili zlecenia.'],
 ['Popraw setQuery(next); onSearch(query), aby onSearch dostało nowe dane w tym samym handlerze.','Użyj lokalnego next.','import {useState} from "react";\nexport function Search({onSearch}:{onSearch:(query:string)=>void}){const [query,setQuery]=useState("");return <input aria-label="Szukaj" value={query} onChange={event=>{const next=event.currentTarget.value;setQuery(next);onSearch(next);}}/>;}'],
 ['Wyjaśnij, czemu nie wolno umieszczać onSearch w updaterze tylko po to, by dostać świeży stan.','Updater ma być czystym obliczeniem.','Updater może być kontrolnie wykonany więcej niż raz, a jego zadaniem jest wyznaczenie nowego stanu. Efekt taki jak request, log biznesowy czy zapis DOM powinien mieć osobne właściwe miejsce.'],
 ['Zapisz props setCount akceptujący pełny setter useState<number>.','Dziecko ma móc użyć aktualizacji funkcyjnej.','import type {Dispatch,SetStateAction} from "react";\nexport function Increment({setCount}:{setCount:Dispatch<SetStateAction<number>>}){return <button type="button" onClick={()=>setCount(previous=>previous+1)}>+1</button>;}'],
 ['Porównaj callback onAdd():void i pełny setter przekazany dziecku.','Preferuj zakres API odpowiadający potrzebie komponentu.','OnAdd ukrywa szczegóły przechowywania stanu i wyraża zamiar. Pełny setter pozwala także zastępować wartość i używać updaterów. Nie przekazuj szerszego API bez potrzeby.'],
 ['Wykaż błędem TS, że updater licznika nie może zwrócić string.','Parametr previous i wynik pozostają liczbowe.','import {useState} from "react";\nexport function BadUpdate(){const [count,setCount]=useState(0);function update(){\n// @ts-expect-error Wynik nie jest number\nsetCount(previous=>previous+"1");\n}return <button type="button" onClick={update}>{count}</button>;}'],
 ['Zapisz czysty initializer i odróżnij go od updatera.','Initializer nie ma argumentu poprzedniego stanu.','import {useState} from "react";\nexport function InitialCounter(){const [count,setCount]=useState(()=>2+3);return <button type="button" onClick={()=>setCount(previous=>previous+1)}>{count}</button>;}'],
 ['Wyjaśnij, dlaczego inicjalizator i updater muszą być czyste także w StrictMode.','Powtórne wywołanie kontrolne nie może wykonać podwójnej operacji biznesowej.','React w trybie developerskim może wywoływać takie funkcje kontrolnie dodatkowo. Zwracaj wynik bez mutacji zewnętrznych danych i efektów, a testuj stan końcowy zamiast zakładać liczbę logów renderu.'],
 ['Oblicz kolejkę mieszaną setCount(count+5), updater +1 dla migawki count=2.','Pierwszy argument jest obliczany zanim trafi do kolejki.',queue+'\nconst snapshot=2;console.log(applyQueue(snapshot,[snapshot+5,n=>n+1]));'],
 ['Oblicz updater +1, potem setCount(count+5) dla migawki count=2.','Ostatnie zastąpienie może usunąć efekt wcześniejszej transformacji.',queue+'\nconst snapshot=2;console.log(applyQueue(snapshot,[n=>n+1,snapshot+5]));'],
 ['Wyjaśnij batching bez obietnicy dokładnej liczby wywołań komponentu w każdym trybie.','Render, commit i licznik console.log to różne obserwacje.','React może grupować wiele aktualizacji, dzięki czemu po zdarzeniu widzisz spójny wynik. StrictMode, pomijanie równych wartości i współbieżne renderowanie sprawiają, że liczba wywołań funkcji nie jest uniwersalnym API do zliczania aktualizacji.'],
 ['Wyjaśnij zakres automatycznego batchingu w nowoczesnym root bez łączenia wszystkich await w jeden pakiet.','Granice asynchroniczne mają znaczenie.','Automatyczne grupowanie obejmuje także wiele aktualizacji w callbackach timerów i obietnic. Nie oznacza to jednej globalnej partii dla całego programu ani gwarancji połączenia kodu przed i po dowolnym await.'],
 ['Napisz aktualizację niezależną od poprzedniego stanu: wyczyść wybór do null.','Forma funkcyjna nie jest obowiązkowym rytuałem.','import {useState} from "react";\nexport function Selection(){const [selectedId,setSelectedId]=useState<number|null>(42);return <button type="button" onClick={()=>setSelectedId(null)}>{selectedId??"Brak"}</button>;}'],
 ['Zaplanuj test +3 bez uzależnienia od liczby developerskich renderów.','Sprawdzaj widoczną wartość po zakończeniu aktualizacji.','Początek 0, jedno kliknięcie daje 3, drugie 6. Nie zakładaj, że jedna linia log render równa się jednemu kliknięciu. W środowisku testowym użyj właściwej synchronizacji aktualizacji React.'],
 ['Zaplanuj test starej migawki raportu z kontrolą timerów.','Nie czekaj niepotrzebnie trzech sekund w każdej próbie.','Ustaw 2, zleć raport, zwiększ do 3 i uruchom zaplanowany timer w środowisku testowym. Raport ma dostać 2. Oddziel test raportu z chwili zlecenia od opóźnionego przyrostu używającego updatera.'],
 ['Zapisz JSX/JSDoc wersję WatchToggle.','Logika funkcyjnego settera nie wymaga składni TS.','import {useState} from "react";\nexport function WatchToggle(){const [watching,setWatching]=useState(false);return <button type="button" aria-pressed={watching} onClick={()=>setWatching(previous=>!previous)}>{watching?"Obserwujesz":"Obserwuj"}</button>;}'],
 ['Wyjaśnij, czy await setCount(count+1) daje świeży count w tej samej funkcji.','Setter nie zwraca obietnicy commitu.','Nie. Setter zwraca void, a count w tej funkcji pozostaje wartością danego renderu. Użyj lokalnej zmiennej dla danych potrzebnych od razu lub poprawnego projektu aktualizacji.'],
 ['Przechowaj w stanie funkcję () => 42 bez potraktowania jej jako initializer/update.','Dodatkowy poziom funkcji zwraca wartość będącą funkcją.','import {useState} from "react";\nexport function FunctionState(){const [compute,setCompute]=useState<()=>number>(()=>()=>42);return <button type="button" onClick={()=>setCompute(()=>()=>7)}>{compute()}</button>;}'],
 ['Oddaj licznik +3, WatchToggle i raport migawki w jednym widoku.','Każdy komponent ma niezależny stan; updatery pozostają czyste.',functional+'\n'+watch.replace('import {useState} from "react";\n','')+'\n'+delayed.replace('import {useState} from "react";\n','')+'\nexport function Demo(){return <main><CommentCounter/><WatchToggle/><DelayedReport onReport={value=>console.log(value)}/></main>;}'],
];
const quiz=`
Co oznacza stan jako migawka?|Wartość widzianą przez konkretny render|Zawsze najnowszą globalną zmienną|Kopię całego DOM|Zamrożenie wszystkich danych|Render i jego handlery mają własny zestaw wartości.
Czy setter zmienia count w aktualnie wykonywanej funkcji?|Nie|Tak|Tylko w TSX|Tylko w StrictMode|Zleca aktualizację, a nie przypisanie do lokalnej zmiennej.
Co pokaże log count po setterze z handlera count=0?|0|1 zawsze|Undefined|Liczbę renderów|To nadal bieżąca migawka.
Dlaczego trzy setCount(count+1) z jednej migawki nie dodają trzech?|Wszystkie obliczają tę samą wartość zastąpienia|React pomija każdą trzecią aktualizację|Nie wolno wywołać settera wiele razy|Count jest stringiem|Argumenty są wyliczane z tego samego count.
Co otrzymuje updater previous=>previous+1?|Wartość wynikającą z wcześniejszych pozycji kolejki|Zawsze wartość początkową|Zawsze event|Props key|Funkcje są przetwarzane kolejno.
Czy kolejność updaterów ma znaczenie?|Tak|Nie|Tylko w JSX|Tylko dla boolean|Dodawanie i mnożenie mogą dawać różne wyniki zależnie od kolejności.
Co oznacza setCount(42) w kolejce?|Zastąpienie wartości przez 42|Dodanie 42|Pomnożenie przez 42|Reset komponentu|Bezpośrednia wartość zastępuje stan.
Czy późniejsze zastąpienie może nadpisać rezultat wcześniejszego updatera?|Tak|Nie|Tylko w TSX|Tylko w StrictMode|Kolejka jest przetwarzana w kolejności.
Czy czysty updater może mutować poprzedni obiekt stanu?|Nie|Tak|Tylko w JSX|Tylko w StrictMode|Zasada czystości dotyczy też danych wejściowych.
Czy updater powinien wykonywać request biznesowy?|Nie|Tak|Tylko w TSX|Tylko przy +3|To efekt uboczny, nie obliczenie nowego stanu.
Czy updater powinien zmieniać dataset.theme?|Nie|Tak|Tylko dla boolean|Tylko w JSX|DOM nie powinien być modyfikowany wewnątrz czystego updatera.
Czy dodatkowe kontrolne wywołanie updatera w StrictMode powinno podwoić biznesowy efekt?|Nie, dlatego updater musi być czysty|Tak zawsze|Tylko przy await|Tylko w produkcji|Dodatkowe wywołania pomagają wykrywać nieczystość.
Co oznacza batching?|Grupowanie aktualizacji|Automatyczny debounce każdego inputa|Usuwanie setterów|Mutowanie props|React może odłożyć przetworzenie kilku aktualizacji do wspólnej pracy.
Czy batching oznacza jedną globalną partię całej aplikacji na zawsze?|Nie|Tak|Tylko w TSX|Tylko z root|Istnieją granice zdarzeń i asynchronicznego kodu.
Czy osobne celowe kliknięcia zawsze traktować jako jedną nierozróżnialną partię?|Nie|Tak|Tylko w JSX|Tylko dla button|React zachowuje semantykę osobnych interakcji.
Czy nowoczesny root może grupować settery w timerze?|Tak|Nie|Tylko w TSX|Tylko dla null|Automatyczny batching obejmuje również takie źródła.
Czy nowoczesny root może grupować settery w callbacku Promise?|Tak|Nie|Tylko w JSX|Tylko bez await|Nie ogranicza się wyłącznie do natywnych handlerów React.
Czy kod przed i po dowolnym await ma gwarancję jednego wspólnego renderu?|Nie|Tak|Tylko w TSX|Tylko przy count|Granice asynchroniczne mają znaczenie.
Czy liczba console.log w renderze jest stabilnym API liczenia kliknięć?|Nie|Tak|Tylko w produkcji|Tylko dla Counter|StrictMode i inne mechanizmy mogą zmieniać liczbę wywołań.
Co lepiej sprawdzać w teście licznika?|Stan widoczny po aktualizacji|Dokładnie jeden log render zawsze|Wyłącznie długość kodu|Tylko nazwę settera|Wymaganie dotyczy wyniku działania.
Czy setCount(previous=>previous+3) jest poprawnym +3?|Tak|Nie|Tylko w JSX|Tylko bez batchingu|Jeden updater może wyrazić całą operację.
Czy trzy updatery +1 dają +3?|Tak|Nie|Tylko w TSX|Tylko bez StrictMode|Każdy operuje na wyniku poprzedniego.
Czy updater jest potrzebny przy ustawieniu niezależnego id=42?|Nie jest konieczny|Zawsze tak|Tylko w JSX|Tylko w TSX|Nowa wartość nie zależy od poprzedniej.
Czy updater jest przydatny w opóźnionym przyroście?|Tak|Nie|Tylko dla boolean|Tylko bez Promise|Nie musi używać starego count z zamknięcia.
Czy opóźniony raport starej migawki zawsze jest błędem?|Nie|Tak|Tylko w JSX|Tylko w TSX|Może celowo raportować moment zlecenia.
Co zamyka funkcja timera odwołująca się do count?|Count ze swojego renderu|Zawsze najnowszy count automatycznie|Obiekt settera|Event DOM|Closure zachowuje środowisko deklaracji.
Czy zmiana count tworzy magicznie nową wartość w już zaplanowanym starym closure?|Nie|Tak|Tylko w TSX|Tylko po 3 sekundach|Stara funkcja zachowuje wcześniejsze powiązanie.
Jak przekazać nowy tekst do onSearch w tym samym handlerze?|Lokalną zmienną next|Starym query po setterze|Przez await setQuery|Przez key inputa|Next pochodzi bezpośrednio ze zdarzenia.
Czy setQuery(next);onSearch(query) zawsze użyje next?|Nie|Tak|Tylko w JSX|Tylko w TSX|Query pozostaje migawką danego renderu.
Czy await setQuery zapewnia aktualizację lokalnego query?|Nie|Tak|Tylko w TSX|Tylko przy Promise|Setter nie jest obietnicą zmiany tej zmiennej.
Co opisuje SetStateAction<number>?|Nową liczbę lub updater liczbowy|Wyłącznie event click|Wyłącznie liczbę zawsze|Obiekt CSS|To typ argumentu settera.
Co opisuje Dispatch<SetStateAction<number>>?|Typ pełnego settera liczbowego|Typ komponentu|Stan formularza|ThemeProvider|Można go użyć dla props przyjmującego setter.
Czy props (value:number)=>void dopuszcza funkcję previous=>previous+1 jako argument?|Nie w takim kontrakcie|Tak zawsze|Tylko w JSX|Tylko przy null|Wymaga szerszego typu pełnego settera.
Czy dziecko zawsze potrzebuje pełnego settera rodzica?|Nie|Tak|Tylko w TSX|Tylko dla +1|Callback onAdd może być węższym i czytelniejszym API.
Czy previous+"1" zwraca number?|Nie|Tak|Tylko w TSX|Tylko dla 0|Dodanie napisu powoduje konkatenację tekstową.
Czy updater number może poprawnie zwrócić string w TS?|Nie|Tak|Tylko w strict|Tylko z React 19|Wynik musi być zgodny z typem stanu.
Czy TypeScript wnioskuje previous z typu useState?|Tak|Nie|Tylko przy any|Tylko w JSX|Setter określa oczekiwany typ funkcji.
Czym initializer różni się od updatera?|Tworzy początek bez argumentu previous|Zawsze dodaje jeden|Wywołuje się po każdym kliknięciu|Musi przyjmować event|UseState może przyjąć leniwą funkcję inicjującą.
Czy initializer powinien być czysty?|Tak|Nie|Tylko w TSX|Tylko dla number|Może być wywoływany kontrolnie więcej niż raz.
Czy useState(()=>expensive()) odracza tworzenie wartości do inicjalizacji?|Tak|Nie|Tylko w JSX|Tylko przy string|Przekazujemy funkcję inicjującą.
Czy useState(expensive()) może obliczać expensive przy każdym wywołaniu komponentu?|Tak|Nie|Tylko w TSX|Tylko przy null|Argument JS jest obliczany przed wywołaniem hooka.
Czy zmiana argumentu początkowego resetuje stan tej samej instancji?|Nie|Tak|Tylko w JSX|Tylko w StrictMode|Stan jest zapamiętany niezależnie od nowego argumentu startowego.
Jak przechować funkcję jako wartość stanu bez jej wykonania jako initializer?|Zwrócić ją z dodatkowej funkcji inicjującej|Przekazać ją bezpośrednio i liczyć na brak wywołania|Wpisać do key|Usunąć typ|Dodatkowa warstwa odróżnia wartość funkcyjną.
Jak ustawić nową funkcję w stanie bez potraktowania jej jako updater?|Przekazać funkcję zwracającą tę funkcję|Zawsze wywołać nową funkcję w renderze|Przekazać event|Zamienić ją na CSS|Setter interpretuje funkcję jako updater.
Czy dwa funkcyjne odwrócenia boolean wracają do początku?|Tak|Nie|Tylko w TSX|Tylko w JSX|Każdy updater odwraca wynik poprzedniego.
Czy dwa setWatching(!watching) z tego samego renderu robią dwa niezależne odwrócenia?|Nie|Tak|Tylko w TSX|Tylko po await|Oba obliczają ten sam zastępujący boolean.
Czy aria-pressed ma opisywać bieżące watching?|Tak|Nie|Tylko w JSX|Tylko w dark|Atrybut opisuje stan, a etykieta może opisywać akcję.
Czy czysty model reduce kolejki odtwarza cały scheduler React?|Nie|Tak|Tylko w TSX|Tylko w produkcji|Wyjaśnia kolejność wartości i updaterów, bez wewnętrznego harmonogramowania.
Czy model kolejki powinien zachowywać kolejność operacji?|Tak|Nie|Tylko dla +1|Tylko przy 0|Kolejność jest istotą ćwiczenia.
Czy można zamieniać +1 i ×2 bez zmiany wyniku dla każdego stanu?|Nie|Tak|Tylko w TSX|Tylko w JSX|Operacje nie są przemienne.
Czy końcowe setCount(0) może wyzerować wcześniejsze +3?|Tak|Nie|Tylko przy StrictMode|Tylko w TSX|Ostatnia wartość zastępująca staje się wynikiem.
Czy późniejszy updater może przeliczyć wynik wcześniejszego zastąpienia?|Tak|Nie|Tylko w JSX|Tylko dla 0|Dostaje wartość wynikającą z wcześniejszej pozycji.
Czy stara migawka jest problemem wyłącznie timerów?|Nie|Tak|Tylko w TSX|Tylko przy 3 sekundach|Dotyczy też innych opóźnionych callbacków i await.
Czy aktualizacja funkcyjna automatycznie zapewnia dostęp do najnowszych wszystkich props?|Nie|Tak|Tylko w JSX|Tylko dla number|Dostaje poprzedni stan tej kolejki, nie nowe wartości całego świata.
Czy można wykonać log biznesowy w handlerze poza updaterem?|Tak|Nie|Tylko w TSX|Tylko bez StrictMode|Handler może wykonywać skutki świadomej akcji.
Czy sam batching gwarantuje poprawność mutujących updaterów?|Nie|Tak|Tylko w TSX|Tylko w produkcji|Grupowanie nie naprawia nieczystej logiki.
Czy test timera może użyć sterowanych timerów środowiska testowego?|Tak|Nie|Tylko w JSX|Tylko w TSX|To umożliwia przewidywalne sprawdzenie opóźnionego kodu.
Czy przed zakończeniem aktualizacji można zawsze oczekiwać nowego DOM natychmiast po setterze?|Nie|Tak|Tylko w TSX|Tylko dla count|Planowanie aktualizacji i commit to odrębne kroki.
Czy flushSync jest konieczny do zwykłego licznika +3?|Nie|Tak|Tylko w TSX|Tylko w React 19|To nie jest wymagane rozwiązanie typowego stanu.
Co potwierdza zrozumienie lekcji?|Poprawne +3, czyste updatery i wyjaśnienie starej migawki|Wyłącznie jeden log render|Tylko znajomość nazwy batching|Samo useState|Trzeba rozumieć zarówno kolejność aktualizacji, jak i odczyty.
`.trim().split('\n').map(line=>line.split('|'));
const cases=[
 ['Trzy zastąpienia z migawki zero',0,'[1,1,1]',1,3,0,2],
 ['Trzy przyrosty od zera',0,'[n=>n+1,n=>n+1,n=>n+1]',3,1,0,6],
 ['Trzy zastąpienia z migawki pięć',5,'[6,6,6]',6,8,5,18],
 ['Trzy przyrosty od pięciu',5,'[n=>n+1,n=>n+1,n=>n+1]',8,6,5,15],
 ['Zastąpienie i przyrost',0,'[5,n=>n+1]',6,5,1,0],
 ['Przyrost i zastąpienie',0,'[n=>n+1,5]',5,6,1,0],
 ['Dwie transformacje',2,'[n=>n+1,n=>n*2]',6,5,4,3],
 ['Transformacje odwrotnie',2,'[n=>n*2,n=>n+1]',5,6,4,3],
 ['Końcowe czterdzieści dwa',0,'[5,n=>n+1,n=>n*2,42]',42,12,6,5],
 ['Transformacja po czterdziestu dwóch',0,'[42,n=>n+1]',43,42,1,0],
 ['Reset po trzech',0,'[n=>n+3,0]',0,3,1,6],
 ['Przyrost po resecie',8,'[0,n=>n+3]',3,11,8,0],
 ['Pusta kolejka',7,'[]',7,0,1,8],
 ['Wyłącznie zastąpienie',7,'[2]',2,9,7,0],
 ['Dwa zastąpienia',7,'[2,9]',9,2,11,7],
 ['Podwojenie wartości startowej',3,'[n=>n*2]',6,3,5,9],
 ['Dwa podwojenia',3,'[n=>n*2,n=>n*2]',12,6,9,3],
 ['Odjęcie od migawki',10,'[9,9]',9,8,10,18],
 ['Dwa odejmowania',10,'[n=>n-1,n=>n-1]',8,9,10,0],
 ['Wartość ujemna',0,'[n=>n-3]',-3,3,0,-1],
 ['Ograniczenie minimum',1,'[n=>Math.max(0,n-5)]',0,-4,1,5],
 ['Ograniczenie maksimum',9,'[n=>Math.min(10,n+5)]',10,14,9,5],
 ['Dwa przyrosty z ograniczeniem',9,'[n=>Math.min(10,n+1),n=>Math.min(10,n+1)]',10,11,9,12],
 ['Reset i mnożenie',9,'[2,n=>n*3]',6,27,2,3],
 ['Mnożenie i reset',9,'[n=>n*3,2]',2,27,6,9],
 ['Zastąpienie zero po mnożeniu',4,'[n=>n*2,0]',0,8,4,2],
 ['Mnożenie zera',4,'[0,n=>n*2]',0,8,4,2],
 ['Jedna funkcja +3',4,'[n=>n+3]',7,3,4,12],
 ['Dwa razy +3',4,'[n=>n+3,n=>n+3]',10,7,4,6],
 ['Zastąpienie przez snapshot+5',2,'[7,n=>n+1]',8,7,3,5],
 ['Snapshot+5 na końcu',2,'[n=>n+1,7]',7,8,3,5],
 ['Tożsamościowy updater',6,'[n=>n]',6,0,1,7],
 ['Przyrost po tożsamości',6,'[n=>n,n=>n+1]',7,6,1,0],
 ['Kwadrat po przyroście',2,'[n=>n+1,n=>n*n]',9,5,4,3],
 ['Przyrost po kwadracie',2,'[n=>n*n,n=>n+1]',5,9,4,3],
 ['Zastąpienie ujemne i wartość bezwzględna',10,'[-4,n=>Math.abs(n)]',4,-4,10,14],
 ['Dzielenie przed dodaniem',8,'[n=>n/2,n=>n+2]',6,5,8,4],
 ['Dodanie przed dzieleniem',8,'[n=>n+2,n=>n/2]',5,6,8,10],
 ['Dwa resety i przyrost',0,'[100,5,n=>n+2]',7,107,102,5],
 ['Przyrost potem dwie wartości',0,'[n=>n+2,5,100]',100,107,7,5],
];
for(const [label,start,updates,a,b,c,d] of cases)quiz.push([`${label}: co wypisze dydaktyczny model kolejki?`,String(a),String(b),String(c),String(d),'Każda liczba zastępuje wynik, każda funkcja przelicza wynik poprzedniej pozycji. Model nie symuluje harmonogramowania React.',queue+`\nconsole.log(applyQueue(${start},${updates}));`]);
module.exports={exercises,quiz,direct,functional,watch,delayed,queue};
