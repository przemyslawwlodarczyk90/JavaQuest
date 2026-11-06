// Lekcja 16/01: model renderowania i Profiler. Liczniki renderów, <Profiler> i powody renderów sprawdza React 19 w Vitest (jsdom).
const types=String.raw`export type Ticket={id:number;title:string};
export function makeTickets(count:number):Ticket[]{return Array.from({length:count},(_,i)=>({id:i+1,title:"Zgłoszenie "+(i+1)}));}`;
const table=String.raw`import {useState} from "react";
import type {Ticket} from "./types";
type RowProps={ticket:Ticket;selected:boolean;onSelect:(id:number)=>void;onRowRender?:(id:number)=>void};
export function TicketRow({ticket,selected,onSelect,onRowRender}:RowProps){
  onRowRender?.(ticket.id); // licznik renderów do pomiaru (tylko do nauki)
  return <tr className={selected?"row row--selected":"row"} onClick={()=>onSelect(ticket.id)}>
    <td>{ticket.id}</td><td>{ticket.title}</td>
  </tr>;
}
// Stan zaznaczenia i wyszukiwania w tabeli: każda zmiana renderuje WSZYSTKIE wiersze (render rodzica).
export function TicketTable({tickets,onRowRender}:{tickets:readonly Ticket[];onRowRender?:(id:number)=>void}){
  const [selectedId,setSelectedId]=useState<number|null>(null);
  const [query,setQuery]=useState("");
  const visible=tickets.filter(t=>t.title.toLowerCase().includes(query.toLowerCase()));
  return <div>
    <input aria-label="Szukaj" value={query} onChange={e=>setQuery(e.target.value)}/>
    <table><tbody>
      {visible.map(t=><TicketRow key={t.id} ticket={t} selected={t.id===selectedId} onSelect={id=>setSelectedId(id)} onRowRender={onRowRender}/>)}
    </tbody></table>
  </div>;
}`;
const theme=String.raw`import {createContext,use,useState,type ReactNode} from "react";
type Theme="jasny"|"ciemny";
const ThemeContext=createContext<{theme:Theme;toggle:()=>void}>({theme:"jasny",toggle:()=>{}});
// Stan w providerze, a dzieci przychodzą jako props: zmiana motywu renderuje tylko konsumentów kontekstu.
export function ThemeProvider({children}:{children:ReactNode}){
  const [theme,setTheme]=useState<Theme>("jasny");
  return <ThemeContext value={{theme,toggle:()=>setTheme(t=>t==="jasny"?"ciemny":"jasny")}}>{children}</ThemeContext>;
}
export function ThemeButton({onRender}:{onRender:(name:string)=>void}){
  const {theme,toggle}=use(ThemeContext);
  onRender("ThemeButton");
  return <button type="button" onClick={toggle}>Motyw: {theme}</button>;
}
export function StaticPanel({onRender}:{onRender:(name:string)=>void}){
  onRender("StaticPanel");
  return <p>Panel bez kontekstu</p>;
}`;
const why=String.raw`import {useEffect,useRef} from "react";
// Które props zmieniły referencję między renderami (Object.is) — odpowiednik „Why did this render?”.
export function changedProps(prev:Record<string,unknown>,next:Record<string,unknown>):string[]{
  const keys=new Set([...Object.keys(prev),...Object.keys(next)]);
  return [...keys].filter(key=>!Object.is(prev[key],next[key])).sort();
}
export function useWhyRender(name:string,props:Record<string,unknown>,report:(name:string,keys:string[])=>void){
  const previous=useRef<Record<string,unknown>|null>(null);
  useEffect(()=>{
    if(previous.current)report(name,changedProps(previous.current,props));
    previous.current=props;
  });
}`;
const stats=String.raw`export type ProfileRecord={id:string;phase:"mount"|"update"|"nested-update";actualDuration:number;baseDuration:number};
export type Summary={commits:number;total:number;max:number};
// Zbiorcze statystyki z wielu wywołań onRender — do porównania „przed” i „po” optymalizacji.
export function summarize(records:readonly ProfileRecord[]):Record<string,Summary>{
  const result:Record<string,Summary>={};
  for(const r of records){
    const entry=result[r.id]??{commits:0,total:0,max:0};
    entry.commits+=1;
    entry.total+=r.actualDuration;
    entry.max=Math.max(entry.max,r.actualDuration);
    result[r.id]=entry;
  }
  return result;
}
export function overBudget(records:readonly ProfileRecord[],budget=16):ProfileRecord[]{
  return records.filter(r=>r.actualDuration>budget);
}`;
const slow=String.raw`// Sztuczny koszt: zegar wstrzykiwany, żeby test był deterministyczny.
export function slowFormat(text:string,costMs=1,now:()=>number=()=>performance.now()):string{
  const start=now();
  while(now()-start<costMs){/* symulacja ciężkiego formatowania */}
  return text.toUpperCase();
}`;
const measured=String.raw`import {Profiler,type ProfilerOnRenderCallback,type ReactNode} from "react";
// Pomiar fragmentu drzewa poza DevTools: ostrzeżenie, gdy commit trwa dłużej niż próg.
export function Measured({id,threshold=8,report=console.warn,children}:{id:string;threshold?:number;report?:(message:string)=>void;children:ReactNode}){
  const onRender:ProfilerOnRenderCallback=(profilerId,phase,actualDuration,baseDuration)=>{
    if(actualDuration>threshold)report("["+profilerId+"] "+phase+": "+actualDuration.toFixed(1)+" ms (bez memo: "+baseDuration.toFixed(1)+" ms)");
  };
  return <Profiler id={id} onRender={onRender}>{children}</Profiler>;
}`;
const tests=String.raw`import {Profiler,useState} from "react";
import {act,fireEvent,render,screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import {expect,test,vi} from "vitest";
import {Measured} from "./Measured";
import {overBudget,summarize,type ProfileRecord} from "./profilerStats";
import {slowFormat} from "./slowFormat";
import {StaticPanel,ThemeButton,ThemeProvider} from "./ThemeShell";
import {TicketTable} from "./TicketTable";
import {makeTickets} from "./types";
import {changedProps,useWhyRender} from "./whyRender";
test("kliknięcie wiersza renderuje ponownie wszystkie wiersze",async()=>{
  const renders=new Map<number,number>();
  const user=userEvent.setup();
  render(<TicketTable tickets={makeTickets(5)} onRowRender={id=>renders.set(id,(renders.get(id)??0)+1)}/>);
  await user.click(screen.getByText("Zgłoszenie 3"));
  expect([...renders.values()]).toEqual([2,2,2,2,2]);
  expect(screen.getByText("Zgłoszenie 3").closest("tr")).toHaveClass("row--selected");
});
test("wpisanie znaku w wyszukiwarce renderuje wiersze, które zostają",async()=>{
  let total=0;
  const user=userEvent.setup();
  render(<TicketTable tickets={makeTickets(12)} onRowRender={()=>{total+=1;}}/>);
  total=0;
  await user.type(screen.getByLabelText("Szukaj"),"1");
  expect(screen.getAllByRole("row")).toHaveLength(4); // 1, 10, 11, 12
  expect(total).toBe(4);
});
test("changedProps wskazuje zmienione referencje, także usunięte klucze",()=>{
  const onSelect=()=>{};
  expect(changedProps({id:1,onSelect},{id:1,onSelect})).toEqual([]);
  expect(changedProps({id:1,onSelect},{id:1,onSelect:()=>{}})).toEqual(["onSelect"]);
  expect(changedProps({id:1,extra:true},{id:1})).toEqual(["extra"]);
  expect(changedProps({filter:{s:"OPEN"}},{filter:{s:"OPEN"}})).toEqual(["filter"]);
});
test("useWhyRender: zaznaczony wiersz zmienia selected i onSelect, pozostałe tylko onSelect",()=>{
  const reports:string[]=[];
  function Row({id,selected,onSelect}:{id:number;selected:boolean;onSelect:()=>void}){
    useWhyRender("Row "+id,{selected,onSelect},(name,keys)=>reports.push(name+": "+keys.join(",")));
    return <li onClick={onSelect}>{id}</li>;
  }
  function List(){
    const [selected,setSelected]=useState<number|null>(null);
    return <ul>{[1,2].map(id=><Row key={id} id={id} selected={id===selected} onSelect={()=>setSelected(id)}/>)}</ul>;
  }
  render(<List/>);
  fireEvent.click(screen.getByText("1"));
  expect(reports).toEqual(["Row 1: onSelect,selected","Row 2: onSelect"]);
});
test("dzieci jako props: zmiana motywu renderuje tylko konsumenta kontekstu",async()=>{
  const log:string[]=[];
  const user=userEvent.setup();
  render(<ThemeProvider><ThemeButton onRender={n=>log.push(n)}/><StaticPanel onRender={n=>log.push(n)}/></ThemeProvider>);
  log.length=0;
  await user.click(screen.getByRole("button"));
  expect(log).toEqual(["ThemeButton"]);
  expect(screen.getByRole("button")).toHaveTextContent("Motyw: ciemny");
});
test("Profiler zgłasza fazę mount, potem update",()=>{
  const phases:string[]=[];
  const view=render(<Profiler id="t" onRender={(_id,phase)=>phases.push(phase)}><p>1</p></Profiler>);
  view.rerender(<Profiler id="t" onRender={(_id,phase)=>phases.push(phase)}><p>2</p></Profiler>);
  expect(phases).toEqual(["mount","update"]);
});
test("Measured ostrzega, gdy commit przekracza próg",()=>{
  const report=vi.fn();
  function SlowRow({title}:{title:string}){return <li>{slowFormat(title,2)}</li>;}
  render(<Measured id="Lista" threshold={5} report={report}><ul>{["a","b","c","d"].map(t=><SlowRow key={t} title={t}/>)}</ul></Measured>);
  expect(report).toHaveBeenCalledTimes(1);
  expect(report.mock.calls[0][0]).toMatch(/^\[Lista\] mount: \d+\.\d ms \(bez memo: \d+\.\d ms\)$/);
  expect(screen.getByText("D")).toBeInTheDocument();
});
test("Measured milczy przy wysokim progu",()=>{
  const report=vi.fn();
  const view=render(<Measured id="Lista" threshold={1000} report={report}><p>tanio</p></Measured>);
  act(()=>view.rerender(<Measured id="Lista" threshold={1000} report={report}><p>nadal tanio</p></Measured>));
  expect(report).not.toHaveBeenCalled();
});
test("summarize i overBudget liczą statystyki commitów",()=>{
  const records:ProfileRecord[]=[
    {id:"Tabela",phase:"mount",actualDuration:40,baseDuration:40},
    {id:"Tabela",phase:"update",actualDuration:12,baseDuration:40},
    {id:"Menu",phase:"update",actualDuration:16,baseDuration:16},
  ];
  expect(summarize(records)).toEqual({Tabela:{commits:2,total:52,max:40},Menu:{commits:1,total:16,max:16}});
  expect(overBudget(records).map(r=>r.actualDuration)).toEqual([40]);
});
test("slowFormat kręci się do upływu kosztu i zwraca wielkie litery",()=>{
  let t=0;
  const now=vi.fn(()=>(t+=0.25));
  expect(slowFormat("api",1,now)).toBe("API");
  expect(now.mock.calls.length).toBeGreaterThanOrEqual(5);
});`;
const header=String.raw`import {act,Profiler,createContext,memo,use,useState,type ReactNode} from "react";
import {createRoot} from "react-dom/client";
const log:string[]=[];
function mount(ui:ReactNode){const host=document.createElement("div");document.body.append(host);const root=createRoot(host);act(()=>root.render(ui));return {host,update:(next:ReactNode)=>act(()=>root.render(next))};}
void Profiler;void createContext;void memo;void use;void useState;void log;
`;
const exercises=[
  ['Zbuduj TicketTable ze stanem zaznaczenia i wyszukiwania oraz licznikiem renderów wierszy.','onRowRender przekazany przez props.',table],
  ['Udowodnij testem, że kliknięcie jednego wiersza renderuje wszystkie wiersze.','Mapa id → liczba renderów.',tests],
  ['Udowodnij testem, że pisanie w wyszukiwarce renderuje wszystkie widoczne wiersze.','Wyzeruj licznik po montowaniu.',tests],
  ['Napisz changedProps porównujące props przez Object.is, łącznie z usuniętymi kluczami.','Suma kluczy z obu obiektów.',why],
  ['Napisz hook useWhyRender raportujący zmienione props po każdym renderze.','Poprzednie props w useRef, porównanie w efekcie.',why],
  ['Sprawdź testem, że niezaznaczone wiersze zmieniają tylko onSelect.','Nowa funkcja strzałkowa w każdym renderze rodzica.',tests],
  ['Przenieś stan motywu do providera przyjmującego children, żeby panel bez kontekstu się nie renderował.','Elementy children powstały u rodzica providera.',theme],
  ['Sprawdź testem, że zmiana motywu renderuje tylko konsumenta kontekstu.','Licznik renderów w obu komponentach.',tests],
  ['Owiń fragment drzewa komponentem Profiler i zapisuj fazy.','onRender(id, phase, actualDuration, baseDuration).',String.raw`import {Profiler,type ProfilerOnRenderCallback,type ReactNode} from "react";
const phases:string[]=[];
const onRender:ProfilerOnRenderCallback=(_id,phase)=>{phases.push(phase);};
export function Recorded({children}:{children:ReactNode}){
  return <Profiler id="Recorded" onRender={onRender}>{children}</Profiler>;
}
export function recordedPhases(){return [...phases];}`],
  ['Zbuduj Measured ostrzegający, gdy commit przekracza próg.','Funkcja report wstrzykiwana — domyślnie console.warn.',measured],
  ['Sprawdź testem ostrzeżenie Measured dla wolnych wierszy.','Wiersze z kosztem 2 ms i próg 5 ms.',tests],
  ['Napisz slowFormat z wstrzykiwanym zegarem.','Pętla do upływu costMs.',slow],
  ['Przetestuj slowFormat sztucznym zegarem.','Zegar zwiększa czas o 0,25 ms na wywołanie.',tests],
  ['Napisz summarize zbierające commits, total i max dla każdego id Profilera.','Jedna pętla po rekordach.',stats],
  ['Napisz overBudget wybierający commity powyżej budżetu klatki.','Budżet domyślnie 16 ms.',stats],
  ['Zapisz rekordy Profilera do tablicy i wypisz podsumowanie w konsoli.','Połącz onRender z summarize.',String.raw`import {Profiler,type ProfilerOnRenderCallback,type ReactNode} from "react";
import {summarize,type ProfileRecord} from "./profilerStats";
const records:ProfileRecord[]=[];
const onRender:ProfilerOnRenderCallback=(id,phase,actualDuration,baseDuration)=>{records.push({id,phase,actualDuration,baseDuration});};
export function Recorder({id,children}:{id:string;children:ReactNode}){
  return <Profiler id={id} onRender={onRender}>{children}</Profiler>;
}
export function printSummary(){console.table(summarize(records));}`],
  ['Napisz SlowTicketRow z teorii, używając slowFormat.','1 ms na wiersz × 2000 wierszy = 2 s.',String.raw`import {slowFormat} from "./slowFormat";
import type {Ticket} from "./types";
export function SlowTicketRow({ticket,selected,onSelect}:{ticket:Ticket;selected:boolean;onSelect:(id:number)=>void}){
  return <tr className={selected?"row row--selected":"row"} onClick={()=>onSelect(ticket.id)}>
    <td>{ticket.id}</td>
    <td>{slowFormat(ticket.title)}</td>
  </tr>;
}`],
  ['Przygotuj generator 2000 ticketów do eksperymentu.','Array.from z długością.',types],
  ['Zapisz listę kontrolną pomiaru przed optymalizacją.','Build produkcyjny, Profiler, Ranked, powody, liczba, Performance.',String.raw`// 1. npm run build && npm run preview — czy problem istnieje w produkcji?
// 2. Profiler → najwyższy słupek commitu.
// 3. Ranked → komponenty dominujące w commicie.
// 4. „Why did this render?” → props / stan / kontekst / rodzic.
// 5. Ile ich jest? 500 × 1 ms = 500 ms.
// 6. Performance → Scripting czy Rendering/Painting?
// 7. Zapisz wynik PRZED zmianą.
export {};`],
  ['Wyjaśnij, dlaczego render bez zmian w DOM zwykle nie jest problemem.','Render vs commit.',String.raw`// Render to wywołanie funkcji komponentów i porównanie elementów; commit zmienia tylko różnice.
// Problemem jest dopiero render kosztowny (ciężkie obliczenie) albo bardzo liczny (tysiące komponentów),
// bo wtedy sam JavaScript przekracza budżet klatki ~16 ms.
export {};`],
  ['Przenieś kosztowne pole wyszukiwania do osobnego komponentu, żeby pisanie nie renderowało tabeli.','Stan bliżej miejsca użycia; tabela dostaje gotowy wynik.',String.raw`import {useState} from "react";
// Pole ma własny stan i zgłasza tylko zatwierdzone zapytanie — wpisywanie znaków nie renderuje rodzica.
export function SearchBox({onSearch}:{onSearch:(query:string)=>void}){
  const [draft,setDraft]=useState("");
  return <form onSubmit={e=>{e.preventDefault();onSearch(draft);}}>
    <input aria-label="Szukaj" value={draft} onChange={e=>setDraft(e.target.value)}/>
    <button type="submit">Szukaj</button>
  </form>;
}`],
  ['Pokaż wzorzec „stan w dół”: zaznaczenie w wierszu zamiast w tabeli, gdy rodzic go nie potrzebuje.','Zmiana stanu renderuje tylko ten wiersz.',String.raw`import {useState} from "react";
import type {Ticket} from "./types";
export function CheckableRow({ticket}:{ticket:Ticket}){
  const [checked,setChecked]=useState(false); // tylko ten wiersz renderuje się po kliknięciu
  return <li><label><input type="checkbox" checked={checked} onChange={e=>setChecked(e.target.checked)}/>{ticket.title}</label></li>;
}`],
  ['Napisz test: komponent poza Profilerem nie wywołuje onRender przy swojej aktualizacji.','Profiler raportuje tylko commity, w których jego poddrzewo się renderowało.',String.raw`import {Profiler,useState} from "react";
import {fireEvent,render,screen} from "@testing-library/react";
import {expect,test} from "vitest";
test("aktualizacja poza Profilerem",()=>{
  const phases:string[]=[];
  function Counter(){const [n,setN]=useState(0);return <button onClick={()=>setN(n+1)}>{n}</button>;}
  render(<div><Counter/><Profiler id="p" onRender={(_id,phase)=>phases.push(phase)}><p>stałe</p></Profiler></div>);
  fireEvent.click(screen.getByRole("button"));
  expect(phases).toEqual(["mount"]);
});`],
  ['Napisz test: render rodzica renderuje dziecko, nawet gdy props dziecka się nie zmieniły.','Licznik renderów dziecka bez memo.',String.raw`import {useState} from "react";
import {fireEvent,render,screen} from "@testing-library/react";
import {expect,test} from "vitest";
test("render rodzica",()=>{
  let childRenders=0;
  function Child({label}:{label:string}){childRenders+=1;return <p>{label}</p>;}
  function Parent(){const [n,setN]=useState(0);return <div><button onClick={()=>setN(n+1)}>{n}</button><Child label="stały"/></div>;}
  render(<Parent/>);
  fireEvent.click(screen.getByRole("button"));
  expect(childRenders).toBe(2);
});`],
  ['Napisz test: ustawienie tej samej wartości stanu nie wywołuje onRender Profilera.','Bailout przy Object.is.',String.raw`import {Profiler,useState} from "react";
import {fireEvent,render,screen} from "@testing-library/react";
import {expect,test} from "vitest";
test("bailout",()=>{
  const phases:string[]=[];
  function Toggle(){const [on,setOn]=useState(false);return <button onClick={()=>setOn(false)}>{String(on)}</button>;}
  render(<Profiler id="p" onRender={(_id,phase)=>phases.push(phase)}><Toggle/></Profiler>);
  fireEvent.click(screen.getByRole("button"));
  expect(phases).toEqual(["mount"]);
});`],
  ['Opisz, jak odczytać flamegraph i widok Ranked w Profilerze.','Szary — nie renderował się; szerokość/kolor — czas.',String.raw`// Flamegraph: drzewo komponentów jednego commitu; szary = nie renderował się,
// kolor/szerokość = czas renderu (żółty dłużej, niebieski krócej).
// Ranked: komponenty commitu posortowane od najdłuższego renderu.
// Oś commitów u góry: wysokość słupka = czas commitu — zacznij od najwyższego.
export {};`],
  ['Wyjaśnij różnicę actualDuration i baseDuration.','baseDuration — szacunek renderu bez memoizacji.',String.raw`// actualDuration: czas renderu poddrzewa Profilera w tym commicie (po pominięciach memo/bailout).
// baseDuration: szacowany czas pełnego renderu całego poddrzewa, gdyby nic nie zostało pominięte.
// Duża różnica = memoizacja działa; podobne wartości = wszystko się renderuje.
export {};`],
  ['Zmierz koszt obliczenia w kodzie przez performance.now i porównaj z budżetem klatki.','16 ms przy 60 FPS na cały JavaScript, style, układ i malowanie.',String.raw`export function measure<T>(label:string,work:()=>T,budget=16):T{
  const start=performance.now();
  const result=work();
  const ms=performance.now()-start;
  if(ms>budget)console.warn(label+": "+ms.toFixed(1)+" ms — powyżej budżetu klatki");
  return result;
}`],
  ['Zapisz plan pomiaru „przed” dla DevDesk z 2000 wierszy.','Kliknięcie, pisanie, zmiana motywu, powody renderów.',String.raw`// Pomiar referencyjny (build produkcyjny + Profiler, „Record why each component rendered”):
// - kliknięcie wiersza: czas commitu, liczba renderowanych wierszy, powód: Parent component rendered;
// - wpisanie 3 znaków: czas każdego commitu, czy przekracza 16 ms;
// - zmiana motywu: które komponenty czytają kontekst, czy renderuje się cała tabela.
// Wyniki zapisz w tabeli — porównasz po memo (lekcja 2) i przejściach (lekcja 6).
export {};`],
  ['Wyjaśnij, dlaczego wydajność nie zależy od JSX czy TSX.','Typy znikają przy budowaniu.',String.raw`// TypeScript jest usuwany przy budowaniu — wynikowy JavaScript dla .jsx i .tsx jest taki sam.
// Pośrednio TS pomaga: readonly chroni przed mutacjami psującymi memoizację,
// a typy hooków pokazują, które wartości są stabilne (setter, dispatch), a które powstają w każdym renderze.
export {};`],
];
const quiz=[];function q(...row){quiz.push(row);}
q('Od czego zacząć, gdy „aplikacja jest wolna”?','Od pomiaru: które komponenty się renderują, dlaczego i jak długo','Od owinięcia wszystkiego w memo','Od przepisania na klasy','Od wyłączenia StrictMode','Optymalizacja bez pomiaru to zgadywanie.');
q('Jakie są trzy powody renderu komponentu?','Zmiana stanu, zmiana czytanego kontekstu, render rodzica','Zmiana props, zmiana CSS, zmiana URL','Tylko zmiana props','Tylko setState','Render rodzica domyślnie renderuje wszystkie dzieci.');
q('Czy render rodzica renderuje dziecko, którego props się nie zmieniły?','Tak, domyślnie wszystkie dzieci','Nie','Tylko dzieci z kontekstem','Tylko w StrictMode','memo pozwala to pominąć.');
q('Kiedy częste rendery stają się problemem?','Gdy render jest kosztowny albo komponentów jest bardzo dużo','Zawsze','Nigdy','Tylko w produkcji','Commit i tak zmienia tylko różnice w DOM.');
q('Dlaczego nie oceniać wydajności w trybie deweloperskim „na oko”?','Tryb dev jest wielokrotnie wolniejszy (StrictMode, sprawdzenia)','Bo dev nie renderuje','Bo dev nie ma DOM','Bo Profiler nie działa w dev','Mierz na buildzie produkcyjnym.');
q('Jak uruchomić build produkcyjny lokalnie w Vite?','npm run build && npm run preview','npm run dev -- --prod','npm start','vite --production','preview serwuje zbudowane pliki.');
q('Co pokazuje oś commitów w Profilerze?','Każdy commit sesji; wysokość słupka to czas commitu','Liczbę żądań sieciowych','Zużycie pamięci','Kolejność importów','Zacznij od najwyższego słupka.');
q('Co oznacza szary komponent we flamegraphie?','Nie renderował się w tym commicie','Renderował się najdłużej','Ma błąd','Jest leniwie ładowany','Kolor i szerokość pokazują czas renderu.');
q('Co pokazuje widok Ranked?','Komponenty commitu posortowane według czasu renderu','Komponenty według liczby props','Kolejność montowania','Rozmiar bundla','Szybko wskazuje dominujący koszt.');
q('Jak włączyć powody renderów w Profilerze?','Ustawienie „Record why each component rendered”','Komponentem <Why>','Flagą --why w Vite','Nie da się','Pokazuje props, stan, hooki, kontekst lub render rodzica.');
q('Co oznacza powód „Parent component rendered”?','Komponent renderował się, bo renderował się rodzic','Zmienił się jego stan','Zmienił się kontekst','Zmieniły się jego props','Najczęstszy powód w tabelach.');
q('Do czego służy „Highlight updates when components render”?','Podświetla na żywo renderowane komponenty','Pokazuje błędy','Mierzy pamięć','Włącza StrictMode','Podświetlenie to render, nie zmiana DOM.');
q('Do czego służy komponent <Profiler> z Reacta?','Mierzy czas renderu fragmentu drzewa także poza DevTools','Wyłącza render','Zastępuje memo','Ładuje kod leniwie','Wywołuje onRender po każdym commicie poddrzewa.');
q('Jakie wartości phase zgłasza onRender Profilera?','mount, update lub nested-update','start i end','render i commit','before i after','nested-update — aktualizacja zlecona w efekcie układu.');
q('Co oznacza actualDuration?','Czas renderu poddrzewa w tym commicie','Czas od montowania','Czas malowania','Czas sieci','Uwzględnia pominięcia dzięki memoizacji.');
q('Co oznacza baseDuration?','Szacowany czas pełnego renderu poddrzewa bez memoizacji','Czas pierwszego renderu','Minimalny czas renderu','Czas commitu','Porównanie z actualDuration pokazuje skuteczność memo.');
q('Jak nazywa się typ funkcji onRender w TypeScript?','ProfilerOnRenderCallback','ProfilerProps','RenderCallback','OnCommit','Importowany z react.');
q('Czy Profiler wywoła onRender, gdy renderował się tylko komponent poza nim?','Nie','Tak, zawsze','Tylko w produkcji','Tylko przy montowaniu','Raportuje commity obejmujące jego poddrzewo.');
q('Ile wynosi budżet klatki przy 60 FPS?','Około 16 ms','100 ms','1 ms','200 ms','Na JavaScript, style, układ i malowanie.');
q('Co mierzy INP?','Czas od interakcji do następnego malowania','Czas pierwszego bajtu','Rozmiar bundla','Liczbę renderów','Dobra wartość to poniżej 200 ms.');
q('Ile potrwa render 500 wierszy po 1 ms każdy?','Około 500 ms','1 ms','16 ms','Zależy od memo w DOM','Liczba komponentów mnoży koszt.');
q('Jak sprawdzić, czy koszt jest w renderze, czy w układzie/malowaniu?','W przeglądarce: Performance → Scripting vs Rendering/Painting','W konsoli','W Network','W Profilerze Reacta tylko','Profiler Reacta mierzy tylko render.');
q('Dlaczego warto zapisać pomiar „przed” zmianą?','Żeby porównać wynik po optymalizacji','Bo Profiler kasuje dane','Bo wymaga tego React','Nie warto','Zmierz, popraw, zmierz ponownie.');
q('Co jest błędem w podejściu „owińmy wszystko w memo i useMemo”?','Dodaje kod, porównania i błędy, a często nie trafia w prawdziwy koszt','Nic, to dobra praktyka','memo nie działa','useMemo jest zakazane','Prawdziwy koszt bywa gdzie indziej.');
q('Dlaczego niezaznaczony wiersz ma zmienione props po kliknięciu innego wiersza?','Dostaje nową funkcję onSelect utworzoną w renderze rodzica','Bo zmienia się jego ticket','Bo zmienia się key','Nie ma zmienionych props','Nowa strzałka to nowa referencja.');
q('Co porównuje changedProps z tej lekcji?','Referencje props przez Object.is, także usunięte klucze','Treść obiektów głęboko','Tylko liczbę props','Typy TypeScript','Odpowiednik „Why did this render?”.');
q('Dlaczego useWhyRender porównuje props w efekcie, a nie w renderze?','Render powinien być czysty; zapis do refa i raport to efekty uboczne','Bo w renderze nie ma props','Bo efekt jest szybszy','Bez różnicy','W StrictMode render wykonuje się dwa razy.');
q('Dlaczego zmiana motywu w providerze z children nie renderuje StaticPanel?','Elementy children powstały u rodzica providera i mają tę samą referencję','Bo StaticPanel ma memo','Bo kontekst blokuje render','Bo StaticPanel jest poza drzewem','Renderują się tylko konsumenci kontekstu.');
q('Kto renderuje się po zmianie wartości kontekstu?','Komponenty, które czytają ten kontekst (i ich dzieci)','Wszystkie komponenty aplikacji','Tylko provider','Nikt','useContext/use rejestruje zależność.');
q('Czy nowy obiekt value w providerze przy każdym renderze ma znaczenie?','Tak — każdy render providera zmienia wartość i renderuje konsumentów','Nie','Tylko w TSX','Tylko z memo','Rozważ useMemo dla value, gdy provider renderuje się często.');
q('Jak „stan w dół” poprawia wydajność?','Stan przeniesiony bliżej użycia renderuje mniejszy fragment drzewa','Zwiększa liczbę renderów','Wyłącza render rodzica na zawsze','Zastępuje kontekst','Np. zaznaczenie w wierszu zamiast w tabeli.');
q('Jak wydzielenie pola wyszukiwania z własnym stanem pomaga?','Wpisywanie znaków renderuje tylko pole, a nie całą tabelę','Nie pomaga','Wyłącza walidację','Przyspiesza sieć','Tabela dostaje dopiero zatwierdzone zapytanie.');
q('Czy ustawienie tej samej wartości stanu powoduje commit w Profilerze?','Nie, React pomija render (bailout)','Tak, zawsze','Tylko w StrictMode','Tylko dla obiektów','Object.is z poprzednią wartością.');
q('Czy wydajność renderowania zależy od JSX czy TSX?','Nie — typy znikają przy budowaniu','Tak, TSX jest wolniejszy','Tak, JSX jest wolniejszy','Tylko w dev','Wynikowy JavaScript jest taki sam.');
q('Jak TypeScript pośrednio pomaga wydajności?','readonly chroni przed mutacjami psującymi memoizację, typy pokazują stabilne wartości','Przyspiesza render','Kompiluje do WebAssembly','Wyłącza zbędne rendery','Setter i dispatch mają stałą tożsamość.');
q('Czy podświetlenie w „Highlight updates” oznacza zmianę DOM?','Nie, oznacza render — DOM mógł się nie zmienić','Tak, zawsze','Tylko w produkcji','Tylko dla tekstu','Render bez różnic kończy się pustym commitem.');
q('Dlaczego licznik renderów w ciele komponentu służy tylko do nauki?','To efekt uboczny w renderze; w StrictMode policzy podwójnie','Bo spowalnia aplikację','Bo React go blokuje','Bo psuje typy','W produkcji używaj Profilera.');
q('Co pokaże Profiler dla wpisania znaku w wyszukiwarkę stanu tabeli?','Commit z renderem tabeli i wszystkich widocznych wierszy','Tylko render pola','Brak commitu','Tylko render nagłówka','Stan zapytania jest w tabeli.');
q('Kiedy optymalizacja liczby renderów ma sens?','Gdy pomiar wykazał wolny commit i jego przyczynę','Zawsze przed wdrożeniem','Nigdy','Gdy komponent ma więcej niż 3 props','Najpierw dane, potem zmiana.');
q('Co mierzy actualDuration przy fazie mount?','Czas pierwszego renderu poddrzewa','Zawsze 0','Czas pobierania danych','Czas malowania','Przy montowaniu nic nie jest pomijane.');
q('Czy <Profiler> działa w buildzie produkcyjnym domyślnie?','Nie — potrzebny jest specjalny build profilujący (react-dom/profiling)','Tak, zawsze','Tylko w Next.js','Tylko z DevTools','W dev działa bez konfiguracji.');
q('Co robi „Record why each component rendered”?','Zapisuje powód renderu każdego komponentu w nagraniu','Wyłącza memo','Przyspiesza nagrywanie','Zapisuje stan do pliku','Widoczne po zaznaczeniu komponentu w commicie.');
q('Co jest typowym, „tanim” renderem?','Komponent zwracający kilka elementów bez ciężkich obliczeń','Sortowanie 100 000 elementów','Parsowanie dużego JSON','Formatowanie dat w pętli 10 000 razy','Tani render × duża liczba wciąż może być drogi.');
q('Gdzie umieścić ciężkie obliczenie, które nie zależy od zmienionego stanu?','Policzyć raz poza renderem albo zapamiętać (useMemo) po pomiarze','W każdym renderze wiersza','W useLayoutEffect','W setTimeout','Szczegóły w lekcji 2.');
q('Dlaczego nie warto optymalizować kodu, który w Profilerze jest szary?','Nie renderował się w danym commicie — nie kosztuje','Bo jest uszkodzony','Bo jest leniwy','Bo to błąd DevTools','Skup się na żółtych, szerokich paskach.');
q('Co oznacza wysoki słupek po kliknięciu wiersza w tabeli 2000 wierszy?','Kliknięcie wywołało drogi commit — zwykle render wszystkich wierszy','Problem z siecią','Wyciek pamięci','Brak kluczy w liście na pewno','Sprawdź powody renderów.');
q('Czy React renderuje dzieci, jeśli rodzic zwrócił te same elementy (te same referencje)?','Nie — te same elementy pozwalają pominąć ich render','Tak, zawsze','Tylko z key','Tylko w StrictMode','Dlatego działa wzorzec children jako props.');
q('Jak MSW pomaga w profilowaniu DevDesk?','Generuje duże, powtarzalne dane (np. 2000 ticketów) bez backendu','Mierzy czas renderu','Wyłącza render','Zastępuje Profiler','Powtarzalne dane = porównywalne pomiary.');
q('Co robi Profiler z zagnieżdżonymi komponentami Profiler?','Każdy raportuje swój id i czas swojego poddrzewa','Wyłącza wewnętrzne','Sumuje do jednego','Rzuca błąd','Można mierzyć kilka fragmentów jednocześnie.');
q('Czy measure z performance.now nadaje się do pomiaru renderu komponentu?','Tylko do pomiaru obliczeń w kodzie — render mierz Profilerem','Tak, idealnie','Nie działa w przeglądarce','Tylko w Node','Render jest rozproszony po wielu funkcjach.');
q('Co zrobić, jeśli problem nie występuje w buildzie produkcyjnym?','Zwykle nie optymalizować — koszt wynika z trybu dev','Optymalizować dalej','Wyłączyć StrictMode w produkcji','Przepisać na klasy','Dev ma dodatkowe sprawdzenia.');
q('Która zmiana NIE zmniejsza liczby renderów wierszy po kliknięciu?','Zmiana nazwy zmiennej selectedId','memo na wierszu ze stabilnym onSelect','Przeniesienie zaznaczenia do wiersza','Podział listy na mniejsze komponenty ze stanem','Nazwy nie mają znaczenia.');
q('Dlaczego render tabeli przy zmianie motywu może być niepotrzebny?','Tabela nie czyta motywu, a renderuje się tylko przez render rodzica','Bo motyw zmienia dane','Bo tabela czyta kontekst','Bo CSS wymaga renderu','Zastosuj children jako props lub kontekst bliżej konsumentów.');
q('Co jest wynikiem kryterium akceptacji tej lekcji?','Zapisany profil DevDesk z czasami commitów i powodami renderów','Szybsza aplikacja','Usunięcie wszystkich renderów','Włączony StrictMode','To punkt odniesienia dla kolejnych lekcji.');
q('Co zrobi useWhyRender przy pierwszym renderze?','Nic nie zgłosi — zapisze tylko props','Zgłosi wszystkie props','Rzuci błąd','Zgłosi pustą listę','Nie ma jeszcze poprzednich props.');
q('Jakie props zgłosi changedProps({ filter: { s: "OPEN" } }, { filter: { s: "OPEN" } })?','filter — nowy obiekt to nowa referencja','Nic','s','Błąd','Object.is nie porównuje treści.');
q('Dlaczego setter z useState nie powoduje zmienionych props, gdy przekazujesz go bezpośrednio?','Ma stałą tożsamość między renderami','Bo jest async','Bo jest w kontekście','Bo React go kopiuje','W przeciwieństwie do strzałki id => setSelected(id).');
q('Ile commitów pokaże Profiler po trzech setState w jednym handlerze?','Jeden','Trzy','Zero','Dwa','Automatyczny batching.');
q('Co oznacza actualDuration dużo mniejsze od baseDuration?','Wiele komponentów poddrzewa zostało pominiętych (memo/bailout)','Błąd pomiaru','Brak renderu','Render na serwerze','Memoizacja działa.');
q('Jaki jest koszt licznika renderów przekazanego jako props onRowRender?','Kolejny props; nowa funkcja w każdym renderze też zmienia props','Żaden','Wyłącza memo całkowicie','Spowalnia DOM','Do pomiarów trzymaj go stabilnym.');
q('Czy przeglądarkowy panel Performance pokazuje nazwy komponentów Reacta?','W dev React dodaje znaczniki (Components track), ale główne źródło to Profiler','Nigdy','Zawsze w produkcji','Tylko w Firefox','React 19.2 dodał ścieżki Scheduler i Components w Performance.');
q('Kiedy pomiar Profilerem jest mylący?','Gdy nagrywasz w dev i oceniasz bezwzględne czasy jak w produkcji','Nigdy','Gdy aplikacja ma kontekst','Gdy używasz TSX','Porównuj względnie albo użyj buildu profilującego.');
q('Co zrobić po optymalizacji?','Zmierzyć ponownie tym samym scenariuszem i porównać','Usunąć pomiary bez porównania','Wdrożyć bez sprawdzania','Dodać więcej memo','Tylko wtedy wiesz, czy zmiana pomogła.');
q('Co powoduje, że cała tabela renderuje się przy zaznaczeniu wiersza?','Stan zaznaczenia jest w komponencie tabeli','Brak CSS','Brak key','Tryb produkcyjny','Render rodzica renderuje wszystkie wiersze.');
q('Jak nazywa się commit, w którym aktualizacja pochodzi z efektu układu?','nested-update','layout-update','sync-update','re-mount','Zgłaszany przez onRender jako phase.');
q('Gdzie najlepiej przechowywać stan, którego potrzebuje tylko jeden wiersz tabeli?','W samym wierszu','W korzeniu aplikacji','W globalnym kontekście','W localStorage','Stan bliżej użycia — mniejszy zakres renderu.');
const probes=[
  ['Rendery dziecka po zmianie stanu rodzica','2','1','3','0',String.raw`let childRenders=0;function Child(){childRenders+=1;return <p>stałe</p>;}let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child/></div>;}mount(<Parent/>);act(()=>bump());console.log(childRenders);`],
  ['Rendery dziecka z memo po zmianie stanu rodzica','1','2','3','0',String.raw`let childRenders=0;const Child=memo(function Child(){childRenders+=1;return <p>stałe</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child/></div>;}mount(<Parent/>);act(()=>bump());console.log(childRenders);`],
  ['Rendery dziecka przekazanego jako children','1','2','3','0',String.raw`let childRenders=0;function Child(){childRenders+=1;return <p>stałe</p>;}let bump!:()=>void;function Shell({children}:{children:ReactNode}){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}>{children}</div>;}mount(<Shell><Child/></Shell>);act(()=>bump());console.log(childRenders);`],
  ['Rendery wnuka po zmianie stanu dziadka','2','1','3','0',String.raw`let renders=0;function Grandchild(){renders+=1;return null;}function Child(){return <Grandchild/>;}let bump!:()=>void;function Root(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child/></div>;}mount(<Root/>);act(()=>bump());console.log(renders);`],
  ['Rendery rodzeństwa po zmianie stanu w jednym z dzieci','1','2','3','0',String.raw`let siblingRenders=0;function Sibling(){siblingRenders+=1;return null;}let bump!:()=>void;function Counter(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <p>{n}</p>;}mount(<div><Counter/><Sibling/></div>);act(()=>bump());console.log(siblingRenders);`],
  ['Rendery konsumenta po zmianie kontekstu','2','1','3','0',String.raw`const Ctx=createContext(0);let renders=0;function Consumer(){use(Ctx);renders+=1;return null;}const Inner=memo(function Inner(){return <Consumer/>;});let bump!:()=>void;function App(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <Ctx value={n}><Inner/></Ctx>;}mount(<App/>);act(()=>bump());console.log(renders);`],
  ['Rendery nie-konsumenta za memo po zmianie kontekstu','1','2','3','0',String.raw`const Ctx=createContext(0);let renders=0;function Plain(){renders+=1;return null;}const Inner=memo(function Inner(){return <Plain/>;});let bump!:()=>void;function App(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <Ctx value={n}><Inner/></Ctx>;}mount(<App/>);act(()=>bump());console.log(renders);`],
  ['Fazy Profilera przy montowaniu i aktualizacji','mount,update','mount','update,update','mount,mount',String.raw`const phases:string[]=[];const cb=(_id:string,phase:string)=>{phases.push(phase);};const v=mount(<Profiler id="p" onRender={cb}><p>1</p></Profiler>);v.update(<Profiler id="p" onRender={cb}><p>2</p></Profiler>);console.log(phases.join(","));`],
  ['onRender przy aktualizacji poza Profilerem','mount','mount,update','update','pusty',String.raw`const phases:string[]=[];let bump!:()=>void;function Counter(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <p>{n}</p>;}mount(<div><Counter/><Profiler id="p" onRender={(_id,phase)=>{phases.push(phase);}}><p>stałe</p></Profiler></div>);act(()=>bump());console.log(phases.join(","));`],
  ['onRender przy aktualizacji wewnątrz Profilera','mount,update','mount','update','mount,mount',String.raw`const phases:string[]=[];let bump!:()=>void;function Counter(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <p>{n}</p>;}mount(<Profiler id="p" onRender={(_id,phase)=>{phases.push(phase);}}><Counter/></Profiler>);act(()=>bump());console.log(phases.join(","));`],
  ['id przekazany do onRender','Tabela','p','undefined','Profiler',String.raw`let seen="";mount(<Profiler id="Tabela" onRender={id=>{seen=id;}}><p/></Profiler>);console.log(seen);`],
  ['Kolejność onRender zagnieżdżonych Profilerów','wewnętrzny,zewnętrzny','zewnętrzny,wewnętrzny','wewnętrzny','zewnętrzny',String.raw`const ids:string[]=[];const cb=(id:string)=>{ids.push(id);};mount(<Profiler id="zewnętrzny" onRender={cb}><Profiler id="wewnętrzny" onRender={cb}><p/></Profiler></Profiler>);console.log(ids.join(","));`],
  ['Liczba commitów po trzech setState w jednym act','2','4','3','1',String.raw`let commits=0;let bump!:()=>void;function C(){const [a,setA]=useState(0);bump=()=>{setA(x=>x+1);setA(x=>x+1);setA(x=>x+1);};return <p>{a}</p>;}mount(<Profiler id="p" onRender={()=>{commits+=1;}}><C/></Profiler>);act(()=>bump());console.log(commits);`],
  ['Commit po ustawieniu tej samej wartości','1','2','0','3',String.raw`let commits=0;let set!:(v:string)=>void;function C(){const [s,setS]=useState("OPEN");set=setS;return <p>{s}</p>;}mount(<Profiler id="p" onRender={()=>{commits+=1;}}><C/></Profiler>);act(()=>set("OPEN"));console.log(commits);`],
  ['actualDuration jako liczba','number','string','undefined','object',String.raw`let kind="";mount(<Profiler id="p" onRender={(_id,_phase,actual)=>{kind=typeof actual;}}><p/></Profiler>);console.log(kind);`],
  ['baseDuration nie mniejsze od zera','true','false','undefined','NaN',String.raw`let ok=false;mount(<Profiler id="p" onRender={(_id,_phase,_actual,base)=>{ok=base>=0;}}><p/></Profiler>);console.log(String(ok));`],
  ['Rendery wierszy po kliknięciu jednego (5 wierszy)','10','6','5','2',String.raw`let rows=0;function Row({id,onSelect}:{id:number;onSelect:(id:number)=>void}){rows+=1;return <li onClick={()=>onSelect(id)}>{id}</li>;}let select!:(id:number)=>void;function List(){const [sel,setSel]=useState<number|null>(null);select=setSel;return <ul data-sel={String(sel)}>{[1,2,3,4,5].map(id=><Row key={id} id={id} onSelect={setSel}/>)}</ul>;}mount(<List/>);act(()=>select(3));console.log(rows);`],
  ['Rendery wierszy z memo i stabilnym onSelect','5','10','6','0',String.raw`let rows=0;const Row=memo(function Row({id,onSelect}:{id:number;onSelect:(id:number)=>void}){rows+=1;return <li onClick={()=>onSelect(id)}>{id}</li>;});let select!:(id:number)=>void;function List(){const [sel,setSel]=useState<number|null>(null);select=setSel;return <ul data-sel={String(sel)}>{[1,2,3,4,5].map(id=><Row key={id} id={id} onSelect={setSel}/>)}</ul>;}mount(<List/>);act(()=>select(3));console.log(rows);`],
  ['Rendery wierszy z memo i nową strzałką onSelect','10','5','6','0',String.raw`let rows=0;const Row=memo(function Row({id,onSelect}:{id:number;onSelect:(id:number)=>void}){rows+=1;return <li onClick={()=>onSelect(id)}>{id}</li>;});let select!:(id:number)=>void;function List(){const [sel,setSel]=useState<number|null>(null);select=setSel;return <ul data-sel={String(sel)}>{[1,2,3,4,5].map(id=><Row key={id} id={id} onSelect={i=>setSel(i)}/>)}</ul>;}mount(<List/>);act(()=>select(3));console.log(rows);`],
  ['Rendery wierszy z memo i zmienionym selected','7','5','10','6',String.raw`let rows=0;const Row=memo(function Row({id,selected,onSelect}:{id:number;selected:boolean;onSelect:(id:number)=>void}){rows+=1;return <li className={selected?"on":""} onClick={()=>onSelect(id)}>{id}</li>;});let select!:(id:number)=>void;function List(){const [sel,setSel]=useState<number|null>(1);select=setSel;return <ul>{[1,2,3,4,5].map(id=><Row key={id} id={id} selected={id===sel} onSelect={setSel}/>)}</ul>;}mount(<List/>);act(()=>select(3));console.log(rows);`],
  ['Stan w wierszu: łączna liczba renderów trzech wierszy','4','6','3','0',String.raw`let rows=0;let toggle!:()=>void;function Row({id}:{id:number}){const [on,setOn]=useState(false);if(id===1)toggle=()=>setOn(x=>!x);rows+=1;return <li>{id}{String(on)}</li>;}mount(<ul>{[1,2,3].map(id=><Row key={id} id={id}/>)}</ul>);act(()=>toggle());console.log(rows);`],
  ['Rendery providera z children po zmianie motywu','2','1','3','0',String.raw`let providerRenders=0;let toggle!:()=>void;function Provider({children}:{children:ReactNode}){const [t,setT]=useState("jasny");toggle=()=>setT("ciemny");providerRenders+=1;return <section data-t={t}>{children}</section>;}mount(<Provider><p>treść</p></Provider>);act(()=>toggle());console.log(providerRenders);`],
  ['Rendery przy montowaniu bez StrictMode','1','2','0','4',String.raw`let renders=0;function C(){renders+=1;return null;}mount(<C/>);console.log(renders);`],
  ['Render rodzica z tymi samymi elementami w zmiennej','1','2','3','0',String.raw`let childRenders=0;function Child(){childRenders+=1;return null;}const child=<Child/>;let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}>{child}</div>;}mount(<Parent/>);act(()=>bump());console.log(childRenders);`],
  ['Rendery po zmianie key dziecka','2','1','3','0',String.raw`let renders=0;function Child(){renders+=1;return null;}const v=mount(<Child key="a"/>);v.update(<Child key="b"/>);console.log(renders);`],
  ['Fazy Profilera po zmianie key dziecka','mount,update','mount,mount','mount','update',String.raw`const phases:string[]=[];const cb=(_id:string,phase:string)=>{phases.push(phase);};const v=mount(<Profiler id="p" onRender={cb}><p key="a"/></Profiler>);v.update(<Profiler id="p" onRender={cb}><p key="b"/></Profiler>);console.log(phases.join(","));`],
  ['Fazy Profilera po ponownym zamontowaniu z innym key','mount,mount','mount,update','mount','update',String.raw`const phases:string[]=[];const cb=(_id:string,phase:string)=>{phases.push(phase);};const v=mount(<Profiler key="a" id="p" onRender={cb}><p/></Profiler>);v.update(<Profiler key="b" id="p" onRender={cb}><p/></Profiler>);console.log(phases.join(","));`],
  ['Rendery konsumenta przy tej samej wartości kontekstu','1','2','0','3',String.raw`const Ctx=createContext("jasny");let renders=0;function Consumer(){use(Ctx);renders+=1;return null;}const Inner=memo(function Inner(){return <Consumer/>;});let bump!:()=>void;function App(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Ctx value="jasny"><Inner/></Ctx></div>;}mount(<App/>);act(()=>bump());console.log(renders);`],
  ['Rendery konsumenta przy nowym obiekcie value','2','1','0','3',String.raw`const Ctx=createContext({theme:"jasny"});let renders=0;function Consumer(){use(Ctx);renders+=1;return null;}const Inner=memo(function Inner(){return <Consumer/>;});let bump!:()=>void;function App(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Ctx value={{theme:"jasny"}}><Inner/></Ctx></div>;}mount(<App/>);act(()=>bump());console.log(renders);`],
  ['Liczba commitów po dwóch osobnych act','3','2','1','4',String.raw`let commits=0;let bump!:()=>void;function C(){const [a,setA]=useState(0);bump=()=>setA(x=>x+1);return <p>{a}</p>;}mount(<Profiler id="p" onRender={()=>{commits+=1;}}><C/></Profiler>);act(()=>bump());act(()=>bump());console.log(commits);`],
  ['Rendery memo z obiektem w props','2','1','3','0',String.raw`let renders=0;const Child=memo(function Child({filter}:{filter:{status:string}}){renders+=1;return <p>{filter.status}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child filter={{status:"OPEN"}}/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['Rendery memo z tekstem w props','1','2','3','0',String.raw`let renders=0;const Child=memo(function Child({status}:{status:string}){renders+=1;return <p>{status}</p>;});let bump!:()=>void;function Parent(){const [n,setN]=useState(0);bump=()=>setN(x=>x+1);return <div data-n={n}><Child status="OPEN"/></div>;}mount(<Parent/>);act(()=>bump());console.log(renders);`],
  ['Rendery dziecka po przeniesieniu stanu niżej','1','2','3','0',String.raw`let heavyRenders=0;function Heavy(){heavyRenders+=1;return <p>ciężki</p>;}let type!:(v:string)=>void;function Search(){const [q,setQ]=useState("");type=setQ;return <input value={q} readOnly/>;}function Page(){return <div><Search/><Heavy/></div>;}mount(<Page/>);act(()=>type("a"));console.log(heavyRenders);`],
  ['Rendery dziecka, gdy stan jest w rodzicu','2','1','3','0',String.raw`let heavyRenders=0;function Heavy(){heavyRenders+=1;return <p>ciężki</p>;}let type!:(v:string)=>void;function Page(){const [q,setQ]=useState("");type=setQ;return <div><input value={q} readOnly/><Heavy/></div>;}mount(<Page/>);act(()=>type("a"));console.log(heavyRenders);`],
];
for(const [label,answer,b,c,d,body] of probes)q(label+' — co wypisze kod?',answer,b,c,d,'Wynik pochodzi z uruchomienia na React 19 w Vitest (createRoot + act w jsdom, bez StrictMode).',header+body);
if(exercises.length!==30||quiz.length!==100)throw Error('Niepełny zestaw '+exercises.length+'/'+quiz.length);
const project={'src/types.ts':types,'src/TicketTable.tsx':table,'src/ThemeShell.tsx':theme,'src/whyRender.ts':why,'src/profilerStats.ts':stats,'src/slowFormat.ts':slow,'src/Measured.tsx':measured,'src/perf.test.tsx':tests};
const mutants=[
  {file:'src/whyRender.ts',before:'new Set([...Object.keys(prev),...Object.keys(next)])',after:'new Set(Object.keys(next))'},
  {file:'src/profilerStats.ts',before:'entry.max=Math.max(entry.max,r.actualDuration);',after:'entry.max=r.actualDuration;'},
  {file:'src/profilerStats.ts',before:'r.actualDuration>budget',after:'r.actualDuration>=budget'},
  {file:'src/Measured.tsx',before:'if(actualDuration>threshold)',after:'if(actualDuration>threshold*1000)'},
  {file:'src/slowFormat.ts',before:'while(now()-start<costMs)',after:'while(now()-start<0)'},
  {file:'src/TicketTable.tsx',before:'selected={t.id===selectedId}',after:'selected={false}'},
  {file:'src/ThemeShell.tsx',before:'setTheme(t=>t==="jasny"?"ciemny":"jasny")',after:'setTheme(t=>t)'},
];
module.exports={exercises,quiz,project,mutants,probeCount:probes.length};
