const model=`export type Theme="light"|"dark";export type Density="comfortable"|"compact";export const parseTheme=(input:unknown):Theme|null=>input==="light"||input==="dark"?input:null;export const parseDensity=(input:unknown):Density|null=>input==="comfortable"||input==="compact"?input:null;
export function readTheme(storage:Pick<Storage,"getItem">|null,system:Theme):Theme{if(!storage)return system;try{return parseTheme(storage.getItem("devdesk-theme"))??system;}catch{return system;}}
export function readJson<T>(raw:string|null,fallback:T,parse:(input:unknown)=>T|null):T{if(raw===null)return fallback;try{const input:unknown=JSON.parse(raw);return parse(input)??fallback;}catch{return fallback;}}
export function browserStorage():Storage|null{try{return typeof window==="undefined"?null:window.localStorage;}catch{return null;}}
export function systemTheme():Theme{try{return typeof window!=="undefined"&&window.matchMedia("(prefers-color-scheme: dark)").matches?"dark":"light";}catch{return "light";}}`;
const theme=`import {useEffect,useState} from "react";
${model}
export function useTheme(){const [theme,setTheme]=useState<Theme>(()=>readTheme(browserStorage(),systemTheme()));const [saveError,setSaveError]=useState<string|null>(null);useEffect(()=>{if(typeof document!=="undefined")document.documentElement.dataset.theme=theme;const storage=browserStorage();if(!storage){setSaveError("Motyw działa lokalnie; magazyn niedostępny.");return;}try{storage.setItem("devdesk-theme",theme);setSaveError(null);}catch{setSaveError("Nie zapisano motywu.");}},[theme]);return {theme,toggle:()=>setTheme(previous=>previous==="light"?"dark":"light"),reset:()=>setTheme(systemTheme()),saveError};}
export function ThemePanel(){const {theme,toggle,reset,saveError}=useTheme();return <section><h2>Motyw</h2><button type="button" onClick={toggle}>Motyw: {theme}</button><button type="button" onClick={reset}>Przywróć bieżący motyw systemowy</button>{saveError&&<p role="status">{saveError}</p>}</section>;}`;
const persistent=`import {useEffect,useRef,useState} from "react";
${model}
export function usePersistentState<T>(key:string,fallback:T,parse:(input:unknown)=>T|null){const firstKey=useRef(key);if(firstKey.current!==key)throw Error("Użyj nowego key komponentu dla innego klucza magazynu");const [value,setValue]=useState<T>(()=>{try{return readJson(browserStorage()?.getItem(key)??null,fallback,parse);}catch{return fallback;}});const [saveError,setSaveError]=useState<string|null>(null);useEffect(()=>{const storage=browserStorage();if(!storage){setSaveError("Magazyn niedostępny.");return;}try{storage.setItem(key,JSON.stringify(value));setSaveError(null);}catch{setSaveError("Nie zapisano ustawienia.");}},[key,value]);return {value,setValue,saveError};}
export function DensityPanel(){const density=usePersistentState<Density>("devdesk-density","comfortable",parseDensity);return <section><h2>Gęstość</h2><label><input type="checkbox" checked={density.value==="compact"} onChange={event=>density.setValue(event.currentTarget.checked?"compact":"comfortable")}/>Kompaktowo</label><button type="button" onClick={()=>density.setValue("comfortable")}>Domyślna gęstość</button><p>Gęstość: {density.value}</p>{density.saveError&&<p role="status">{density.saveError}</p>}</section>;}`;
const chart=`import {useEffect,useRef} from "react";export type Chart={update:(data:readonly number[])=>void;destroy:()=>void};export type Factory=(canvas:HTMLCanvasElement)=>Chart;
export const createChart:Factory=canvas=>{const ctx=canvas.getContext("2d");if(!ctx)throw Error("Brak kontekstu canvas");return {update(data){ctx.clearRect(0,0,canvas.width,canvas.height);ctx.fillStyle="#2563eb";const width=canvas.width/Math.max(1,data.length);data.forEach((value,index)=>{const height=Number.isFinite(value)?Math.min(100,Math.max(0,value))*canvas.height/100:0;ctx.fillRect(index*width,canvas.height-height,Math.max(1,width-2),height);});},destroy(){ctx.clearRect(0,0,canvas.width,canvas.height);}};};
export function TicketsChart({data,factory=createChart}:{data:readonly number[];factory?:Factory}){const canvasRef=useRef<HTMLCanvasElement>(null);const chartRef=useRef<Chart|null>(null);useEffect(()=>{const canvas=canvasRef.current;if(!canvas)return;const chart=factory(canvas);chartRef.current=chart;return ()=>{chart.destroy();if(chartRef.current===chart)chartRef.current=null;};},[factory]);useEffect(()=>{chartRef.current?.update(data);},[data,factory]);return <figure><canvas ref={canvasRef} width={400} height={200} aria-label="Wykres wartości od 0 do 100" role="img"/><figcaption>Wartości: {data.join(", ")||"brak"}</figcaption></figure>;}`;
const exercises=[
['Napisz parseTheme sprawdzające light/dark zamiast rzutowania.','Dane storage to string|null.',model],
['Odczytaj zapisany motyw z fallbackiem systemowym.','Nieznany zapis nie jest poprawnym Theme.',model],
['Obsłuż wyjątek getItem i niedostępne localStorage.','Stan UI nadal może działać bez trwałości.',model],
['Zainicjalizuj motyw leniwą funkcją useState.','Nie odczytuj storage jako argumentu wykonywanego co render.',theme],
['Synchronizuj dataset.theme i storage z theme w efekcie.','Każde źródło zmiany stanu ma wspólną ścieżkę synchronizacji.',theme],
['Dodaj toggle bez ręcznego setItem w handlerze.','Handler zmienia stan; efekt zapisuje wynik.',theme],
['Dodaj reset do bieżącego motywu systemowego.','To pojedynczy odczyt, nie ciągła subskrypcja systemu.',theme],
['Pokaż błąd zapisu zamiast obiecywać trwałość.','SaveError nie zmienia samego motywu.',theme],
['Wyjaśnij, dlaczego zapis tego samego theme powinien być idempotentny.','StrictMode może kontrolnie powtarzać setup.','Ponowne setItem z tą samą wartością pozostawia ten sam stan magazynu. Nie traktuj efektu jako miejsca do nieodwracalnej operacji wykonywanej dokładnie raz, np. obciążenia karty lub wysłania zamówienia.'],
['Nie dodawaj cleanup kasującego preferencję ze storage.','Nie każdy efekt potrzebuje cofania zapisu.','Zapisana preferencja ma przetrwać odmontowanie komponentu. Usunięcie jej w cleanup zaprzeczałoby celowi synchronizacji. Cleanup jest konieczny dla zasobów wymagających zakończenia, np. subskrypcji czy obiektu wykresu.'],
['Napisz readJson z unknown oraz parserem runtime.','Poprawny JSON nie oznacza poprawnej gęstości.',model],
['Obsłuż uszkodzony JSON przez fallback.','JSON.parse może rzucić przed walidacją.',model],
['Zbuduj usePersistentState dla ustalonego klucza instancji.','Zmiana klucza bez nowego komponentu mogłaby zapisać stare dane pod nową nazwą.',persistent],
['Waliduj gęstość comfortable/compact.','Generyk T nie zastępuje parse.',persistent],
['Zapisuj JSON.stringify(value) w efekcie z key i value.','Wartości reaktywne odczytane przez efekt należą do zależności.',persistent],
['Resetuj gęstość przez setter do comfortable.','Nie dodawaj oddzielnej ścieżki storage w przycisku.',persistent],
['Rozróżnij surowy zapis theme i JSON zapisu density.','Nie mieszaj formatów serializacji.',model],
['Opisz ograniczenie CSR przykładu motywu.','Dostęp do window w inicjalizacji wymaga polityki SSR.','Przykład jest przeznaczony dla klientowego Vite SPA. Dla SSR/hydratacji serwer i klient potrzebują zgodnego początkowego wyniku lub kontrolowanego bootstrapu motywu. Sam typeof window nie gwarantuje braku rozbieżności jasnego i ciemnego motywu.'],
['Nie udawaj synchronizacji motywu ze zmianami systemu po montowaniu.','Do tego potrzebny jest listener matchMedia.','SystemTheme czyta aktualną preferencję przy inicjalizacji lub kliknięciu resetu. Nie subskrybuje późniejszych zmian systemu. Jeśli potrzebujesz trybu systemowego na żywo, modeluj wybór system/light/dark i dodaj odpowiednią subskrypcję z cleanup.'],
['Zdefiniuj kontrakt Chart z update i destroy.','Integracja ma jawny cykl zasobu.',chart],
['Utwórz wykres dopiero gdy canvasRef ma element.','Ref DOM jest dostępny po commicie.',chart],
['Zapisz instancję zewnętrzną w ref zamiast w stanie.','Nie wymaga renderu samego JSX.',chart],
['Zniszcz konkretny utworzony wykres w cleanup.','Closure przechowuje właściwą instancję.',chart],
['Po destroy wyzeruj chartRef tylko jeśli wskazuje ten sam obiekt.','Nie kasuj omyłkowo nowszego zasobu.',chart],
['Zaktualizuj wykres po zmianie data.','Drugi efekt synchronizuje dane instancji.',chart],
['Uwzględnij zmianę factory w utworzeniu i aktualizacji.','Nowa instancja musi otrzymać także bieżące dane.',chart],
['Nie deklaruj factory jako nowej funkcji w każdym renderze bez potrzeby.','Tożsamość funkcji może restartować efekt.',chart],
['Dodaj tekstową prezentację wartości wykresu.','Canvas sam nie udostępnia znaczenia słupków.',chart],
['Przetestuj setup→cleanup→setup adaptera.','Dwie instancje powinny być poprawnie tworzone i niszczone.',chart],
['Połącz preferencje i wykres, sprawdź zapis po odświeżeniu oraz sprzątanie.','Automatyczny efekt nie jest gwarancją dostępności magazynu.',theme],
];
const concepts=[
['Co synchronizuje useTheme?','Stan theme z atrybutem dokumentu i storage','Wyłącznie count w React','Zawsze serwer','Każdy input na stronie'],
['Dlaczego zapis w efekcie?','Każda zmiana stanu może mieć wspólną synchronizację','Ponieważ handler nie może wywołać setItem','Ponieważ effect działa tylko raz','Aby ominąć walidację'],
['Jakie wartości storage są akceptowane jako Theme?','Light lub dark zgodnie z dokładnymi małymi stringami','Każdy string','Null jako Theme','Dowolny JSON'],
['Co przy nieznanym zapisanym theme?','Fallback do jawnie podanego motywu systemowego','Rzutowanie as Theme','Zawsze awaria','Automatyczny nowy enum'],
['Czy getItem może rzucić?','Tak','Nie','Tylko dla JSON','Tylko po setItem'],
['Czy setItem może się nie udać?','Tak, UI powinno to uwzględnić','Nie','Tylko bez React','Tylko przy null'],
['Czym jest leniwa inicjalizacja?','Przekazaniem funkcji inicjalizatora','Wywołaniem odczytu co render','Zapisem w CSS','Promise useState'],
['Czy inicjalizator jest gwarantowanie wykonany raz w każdym trybie?','Nie, development może go kontrolnie powtórzyć','Tak zawsze','Tylko w TSX','Tylko ze storage'],
['Czy [] oznacza uniwersalnie dokładnie jedno wykonanie effect?','Nie','Tak','Tylko przy cleanup','Tylko w React 19'],
['Co oznacza brak tablicy zależności?','Efekt po kolejnych commitach komponentu','Efekt nigdy','Tylko cleanup','Automatyczne wykrywanie wszystkich deps'],
['Jak porównywane są zależności?','Object.is dla każdej pozycji','Deep equal','Sortowanie JSON','Wyłącznie === dla obiektu props'],
['Czy tablica zależności jest listą życzeń kiedy uruchamiać effect?','Nie, opisuje odczytane wartości reaktywne','Tak, można pomijać niewygodne wartości','Tylko dla storage','Tylko w dev'],
['Czy efekt zapisu storage musi usuwać wpis w cleanup?','Nie, przeczyłoby to trwałej preferencji','Tak zawsze','Tylko dla JSON','Tylko przy reset'],
['Co ma być idempotentne w synchronizacji motywu?','Ponowne ustawienie tej samej wartości','Generowanie nowego losowego theme','Każdy nowy request płatniczy','Zawsze usuwanie klucza'],
['Co robi reset theme przykładu?','Jednorazowo wybiera aktualny motyw systemowy','Włącza listener systemu','Usuwa wszystkie dane','Zawsze wybiera dark'],
['Czy późniejsza zmiana systemu sama zmieni stan tego hooka?','Nie bez subskrypcji','Tak','Tylko po Intl','Tylko w TSX'],
['Czy typeof window wystarcza do zgodności hydratacji?','Nie','Tak','Tylko z storage','Tylko z theme'],
['Po co parser unknown w readJson?','Sprawdza kształt po JSON.parse','Generuje key','Zmienia środowisko na browser','Blokuje commit'],
['Czy poprawny składniowo JSON jest poprawną Density?','Nie musi być','Zawsze tak','Tylko z null','Tylko po stringify'],
['Dlaczego klucz hooka jest stały na instancję?','Chroni przed zapisaniem starych danych pod nowym kluczem','React nie pozwala stringów w props','Storage nie ma wielu kluczy','Effect zabrania key'],
['Gdzie trzymać obiekt wykresu zewnętrznego?','W ref','W oddzielnym DOM globalnym zawsze','W lokalnym stringu title','W count'],
['Kiedy tworzyć obiekt zależny od canvas?','Po commicie w efekcie, gdy ref ma element','Podczas renderu przed DOM','W module przed createRoot','W type alias'],
['Co powinien zrobić cleanup wykresu?','Destroy instancji utworzonej przez setup','Tylko zmienić caption','Zresetować wszystkie props','Wysłać nowe dane'],
['Dlaczego update zależy od factory także?','Po zmianie fabryki nowy obiekt potrzebuje bieżących danych','Aby zmienić theme','Aby usunąć data z zależności','Bo każdy efekt wymaga dwóch deps'],
['Czy factory tworzone na nowo może restartować integrację?','Tak','Nie','Tylko z readonly','Tylko w production'],
['Czy useRef zmiana current sama wywołuje render?','Nie','Tak','Tylko dla Chart','Tylko po destroy'],
['Po co tekst przy canvas?','Przekazuje wartości i znaczenie poza samymi pikselami','Zastępuje cleanup','Zmienia typ Chart','Zapewnia storage'],
['Czy sam test helpera dowodzi działania wykresu w przeglądarce?','Nie','Tak','Tylko z DOM types','Tylko w Node'],
['Co przy błędzie zapisu preferencji?','Stan może działać lokalnie, ale nie deklarujemy trwałości','Zawsze pokazujemy sukces','Kasujemy całą stronę','Zmienia się enum'],
['Czy synchroniczne dane pochodne wymagają useEffect?','Nie','Tak','Tylko dla liczb','Tylko w Context'],
];
const quiz=concepts.map(row=>[...row,'Effect utrzymuje zgodność z zewnętrznym systemem. Walidacja, dostępność storage i cykl zasobów są jawnymi częściami kontraktu.']);quiz.push(...require('./practice_effect_01.cjs').quiz.slice(0,30).map(row=>['Powtórka momentu synchronizacji: '+row[0],...row.slice(1)]));
const cases=[];const add=(label,expr,a)=>cases.push([label,expr,String(a)]);
for(const saved of [null,'light','dark','blue','','"dark"'])for(const system of ['light','dark'])add('Zapis '+JSON.stringify(saved)+' system '+system,`readTheme({getItem:()=>${JSON.stringify(saved)}},${JSON.stringify(system)})`,saved==='light'||saved==='dark'?saved:system);
for(const input of ['light','dark','blue',null,0,true,{},[]])add('Parser theme '+JSON.stringify(input),`parseTheme(${JSON.stringify(input)})??"brak"`,input==='light'||input==='dark'?input:'brak');
for(const input of ['compact','comfortable','tiny',null,1,false])add('Parser density '+JSON.stringify(input),`parseDensity(${JSON.stringify(input)})??"brak"`,input==='compact'||input==='comfortable'?input:'brak');
for(const [label,expr,a]of [['Brak storage','readTheme(null,"dark")','dark'],['Błąd odczytu','readTheme({getItem(){throw Error("Blokada");}},"light")','light'],['JSON brak','readJson(null,"comfortable",parseDensity)','comfortable'],['JSON zły','readJson("{","comfortable",parseDensity)','comfortable'],['JSON inny typ','readJson("3","comfortable",parseDensity)','comfortable'],['JSON compact','readJson(JSON.stringify("compact"),"comfortable",parseDensity)','compact'],['JSON comfortable','readJson(JSON.stringify("comfortable"),"compact",parseDensity)','comfortable'],['JSON null','readJson("null","comfortable",parseDensity)','comfortable'],['JSON obiekt','readJson("{}","comfortable",parseDensity)','comfortable'],['JSON theme','readJson(JSON.stringify("dark"),"light",parseTheme)','dark'],['Surowy theme nie JSON','readJson("dark","light",parseTheme)','light'],['Stringify','JSON.stringify("dark")','"dark"'],['Błąd storage fallback dark','readTheme({getItem(){throw 1;}},"dark")','dark'],['Gęstość nie motyw','parseTheme("compact")??"brak"','brak']])add(label,expr,a);
if(cases.length!==40)throw Error('Przypadki '+cases.length);for(const [label,expr,a]of cases){const wrong=['false','true','null','undefined','0','1'].filter(v=>v!==a).slice(0,3);quiz.push([label+': jaki wynik synchronizacji preferencji?',a,...wrong,'Rozróżnij surowy wpis theme od JSON gęstości. Czytniki walidują wartości i wracają do fallbacku przy braku lub błędzie.',model+'\nconsole.log('+expr+');']);}
const project={'src/Theme.tsx':theme,'src/Persistent.tsx':persistent,'src/Chart.tsx':chart,'src/main.tsx':'import {createRoot} from "react-dom/client";import {ThemePanel} from "./Theme";import {DensityPanel} from "./Persistent";import {TicketsChart} from "./Chart";import "./style.css";const root=document.getElementById("root");if(!root)throw Error("Brak root");createRoot(root).render(<main><h1>Synchronizacja</h1><ThemePanel/><DensityPanel/><TicketsChart data={[20,60,90]}/></main>);','src/style.css':':root[data-theme=light]{color-scheme:light;background:#fff;color:#111}:root[data-theme=dark]{color-scheme:dark;background:#111;color:#fff}button,input{font:inherit}'};
module.exports={model,theme,persistent,chart,exercises,quiz,project};
