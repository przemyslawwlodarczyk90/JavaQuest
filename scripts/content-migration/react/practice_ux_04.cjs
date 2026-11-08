const {lesson}=require('./practice_kit.cjs');
const stats=String.raw`export type TicketStatus="OPEN"|"IN_PROGRESS"|"CLOSED";
export type TicketPriority="LOW"|"MEDIUM"|"HIGH"|"CRITICAL";
export type StatsTicket={id:number;status:TicketStatus;priority:TicketPriority;createdAt:string;closedAt:string|null};
export type DailyPoint={day:string;opened:number;closed:number;open:number};
export type PriorityRow={priority:TicketPriority}&Record<TicketStatus,number>;

const dayKey=(iso:string)=>iso.slice(0,10);

export function dailySeries(tickets:readonly StatsTicket[],from:Date,days:number):DailyPoint[]{
  const series:DailyPoint[]=Array.from({length:days},(_,index)=>{
    const date=new Date(from);
    date.setUTCDate(date.getUTCDate()+index);
    return {day:date.toISOString().slice(0,10),opened:0,closed:0,open:0};
  });
  if(!series.length)return series;
  const position=new Map(series.map((point,index)=>[point.day,index]));
  for(const ticket of tickets){
    const opened=position.get(dayKey(ticket.createdAt));
    if(opened!==undefined)series[opened].opened+=1;
    if(ticket.closedAt){
      const closed=position.get(dayKey(ticket.closedAt));
      if(closed!==undefined)series[closed].closed+=1;
    }
  }
  const first=series[0].day;
  let running=tickets.filter(ticket=>dayKey(ticket.createdAt)<first&&(!ticket.closedAt||dayKey(ticket.closedAt)>=first)).length;
  for(const point of series){
    running+=point.opened-point.closed;
    point.open=running;
  }
  return series;
}

export function byPriorityAndStatus(tickets:readonly StatsTicket[]):PriorityRow[]{
  const priorities:TicketPriority[]=["LOW","MEDIUM","HIGH","CRITICAL"];
  return priorities.map(priority=>{
    const group=tickets.filter(ticket=>ticket.priority===priority);
    return {
      priority,
      OPEN:group.filter(ticket=>ticket.status==="OPEN").length,
      IN_PROGRESS:group.filter(ticket=>ticket.status==="IN_PROGRESS").length,
      CLOSED:group.filter(ticket=>ticket.status==="CLOSED").length,
    };
  });
}

export function averageCloseHours(tickets:readonly StatsTicket[]){
  const closed=tickets.filter(ticket=>ticket.closedAt);
  if(!closed.length)return null;
  const total=closed.reduce((sum,ticket)=>sum+(Date.parse(ticket.closedAt!)-Date.parse(ticket.createdAt)),0);
  return Math.round(total/closed.length/3600000*10)/10;
}

export function trendSentence(points:readonly DailyPoint[]){
  const opened=points.reduce((sum,point)=>sum+point.opened,0);
  const closed=points.reduce((sum,point)=>sum+point.closed,0);
  if(opened===closed)return "Otwarto tyle samo ticketów, ile zamknięto ("+opened+").";
  return opened>closed
    ?"Otwarto o "+(opened-closed)+" ticketów więcej, niż zamknięto."
    :"Zamknięto o "+(closed-opened)+" ticketów więcej, niż otwarto.";
}

// Tysiące punktów SVG zwalniają przeglądarkę: dni łączymy w tygodnie.
export function aggregateWeeks(points:readonly DailyPoint[]){
  const weeks:DailyPoint[]=[];
  points.forEach((point,index)=>{
    if(index%7===0)weeks.push({...point});
    else{
      const week=weeks[weeks.length-1];
      week.opened+=point.opened;
      week.closed+=point.closed;
      week.open=point.open;
    }
  });
  return weeks;
}`;
const trend=String.raw`import {useMemo} from "react";
import {CartesianGrid,Legend,Line,LineChart,ResponsiveContainer,Tooltip,XAxis,YAxis} from "recharts";
import {dailySeries,trendSentence,type DailyPoint,type StatsTicket} from "./ticketStats";

const shortDate=(day:unknown)=>new Date(String(day)).toLocaleDateString("pl-PL",{day:"numeric",month:"short",timeZone:"UTC"});

export function TrendTable({data}:{data:readonly DailyPoint[]}){
  return (
    <table>
      <caption>Dane wykresu</caption>
      <thead><tr><th scope="col">Dzień</th><th scope="col">Otwarte</th><th scope="col">Zamknięte</th><th scope="col">Stan</th></tr></thead>
      <tbody>{data.map(point=><tr key={point.day}><th scope="row">{point.day}</th><td>{point.opened}</td><td>{point.closed}</td><td>{point.open}</td></tr>)}</tbody>
    </table>
  );
}

type Props={tickets:readonly StatsTicket[];from:Date;days?:number;compute?:typeof dailySeries};

export function TicketsTrendChart({tickets,from,days=30,compute=dailySeries}:Props){
  const data=useMemo(()=>compute(tickets,from,days),[tickets,from,days,compute]);
  return (
    <figure>
      <figcaption>Otwarte i zamknięte tickety: ostatnie {days} dni</figcaption>
      <div style={{height:280}}>
        <ResponsiveContainer width="100%" height="100%">
          <LineChart data={data} margin={{top:8,right:16,bottom:0,left:0}}>
            <CartesianGrid strokeDasharray="3 3"/>
            <XAxis dataKey="day" tickFormatter={shortDate} minTickGap={24}/>
            <YAxis allowDecimals={false} width={32}/>
            <Tooltip labelFormatter={shortDate}/>
            <Legend/>
            <Line type="monotone" dataKey="opened" name="Otwarte" stroke="var(--color-warning)" dot={false} isAnimationActive={false}/>
            <Line type="monotone" dataKey="closed" name="Zamknięte" stroke="var(--color-success)" strokeDasharray="6 3" dot={false} isAnimationActive={false}/>
          </LineChart>
        </ResponsiveContainer>
      </div>
      <p>{trendSentence(data)}</p>
      <details><summary>Dane w tabeli</summary><TrendTable data={data}/></details>
    </figure>
  );
}`;
const priority=String.raw`import {Bar,BarChart,CartesianGrid,Legend,ResponsiveContainer,Tooltip,XAxis,YAxis,type TooltipContentProps} from "recharts";
import type {PriorityRow} from "./ticketStats";

export const PRIORITY_LABEL:Record<string,string>={LOW:"Niski",MEDIUM:"Średni",HIGH:"Wysoki",CRITICAL:"Krytyczny"};

export function PriorityTooltip({active,payload,label}:Pick<TooltipContentProps,"active"|"payload"|"label">){
  if(!active||!payload?.length)return null;
  const total=payload.reduce((sum,entry)=>sum+Number(entry.value??0),0);
  return (
    <div>
      <strong>{PRIORITY_LABEL[String(label)]}: {total}</strong>
      <ul>{payload.map(entry=><li key={String(entry.dataKey)}>{entry.name}: {entry.value}</li>)}</ul>
    </div>
  );
}

export function PriorityChart({data}:{data:readonly PriorityRow[]}){
  return (
    <figure>
      <figcaption>Tickety według priorytetu i statusu</figcaption>
      <div style={{height:260}}>
        <ResponsiveContainer width="100%" height="100%">
          <BarChart data={[...data]}>
            <CartesianGrid vertical={false}/>
            <XAxis dataKey="priority" tickFormatter={value=>PRIORITY_LABEL[String(value)]}/>
            <YAxis allowDecimals={false} width={32}/>
            <Tooltip content={props=><PriorityTooltip {...props}/>}/>
            <Legend/>
            <Bar dataKey="OPEN" name="Otwarte" stackId="status" fill="var(--color-warning)" isAnimationActive={false}/>
            <Bar dataKey="IN_PROGRESS" name="W toku" stackId="status" fill="var(--color-primary)" isAnimationActive={false}/>
            <Bar dataKey="CLOSED" name="Zamknięte" stackId="status" fill="var(--color-success)" isAnimationActive={false}/>
          </BarChart>
        </ResponsiveContainer>
      </div>
    </figure>
  );
}`;
const dashboard=String.raw`import {lazy,Suspense} from "react";

// Lista ticketów nie płaci za kod wykresów.
const Dashboard=lazy(()=>import("./DashboardScreen"));

export function DashboardRoute(){
  return <Suspense fallback={<p role="status">Ładowanie dashboardu…</p>}><Dashboard/></Suspense>;
}`;
const screenFile=String.raw`import {byPriorityAndStatus,type StatsTicket} from "./ticketStats";
import {TicketsTrendChart} from "./TicketsTrendChart";
import {PriorityChart} from "./PriorityChart";

export const demoTickets:StatsTicket[]=[
  {id:1,status:"OPEN",priority:"HIGH",createdAt:"2026-03-01T08:00:00Z",closedAt:null},
  {id:2,status:"CLOSED",priority:"LOW",createdAt:"2026-03-01T08:00:00Z",closedAt:"2026-03-02T08:00:00Z"},
];

const from=new Date("2026-03-01T00:00:00Z");

export default function DashboardScreen(){
  return (
    <main>
      <h1>Dashboard</h1>
      <TicketsTrendChart tickets={demoTickets} from={from} days={7}/>
      <PriorityChart data={byPriorityAndStatus(demoTickets)}/>
    </main>
  );
}`;
const setup=String.raw`import {afterEach,vi} from "vitest";
import {cleanup} from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
(globalThis as {IS_REACT_ACT_ENVIRONMENT?:boolean}).IS_REACT_ACT_ENVIRONMENT=true;
// jsdom nie ma układu: ResponsiveContainer dostaje stały rozmiar z atrapy obserwatora.
class FixedResizeObserver{
  constructor(private callback:ResizeObserverCallback){}
  observe(target:Element){
    const entry={target,contentRect:{width:600,height:280}} as unknown as ResizeObserverEntry;
    this.callback([entry],this as unknown as ResizeObserver);
  }
  unobserve(){}
  disconnect(){}
}
vi.stubGlobal("ResizeObserver",FixedResizeObserver);
// Rozmiar dostaje tylko kontener. Gdyby legenda też miała 280 px, na same serie nie zostałoby miejsca.
Element.prototype.getBoundingClientRect=function(this:Element){
  const size=this.classList.contains("recharts-responsive-container")?{width:600,height:280}:{width:0,height:0};
  return {...size,top:0,left:0,right:size.width,bottom:size.height,x:0,y:0,toJSON(){return {};}} as DOMRect;
};
afterEach(()=>cleanup());`;
const tests=String.raw`import {useState} from "react";
import {expect,test,vi} from "vitest";
import {fireEvent,render,screen,waitFor,within} from "@testing-library/react";
import {aggregateWeeks,averageCloseHours,byPriorityAndStatus,dailySeries,trendSentence,type StatsTicket} from "./ticketStats";
import {TicketsTrendChart,TrendTable} from "./TicketsTrendChart";
import {PriorityChart,PriorityTooltip} from "./PriorityChart";
import {DashboardRoute} from "./DashboardRoute";

const from=new Date("2026-03-01T00:00:00Z");
const ticket=(over:Partial<StatsTicket>):StatsTicket=>({id:1,status:"OPEN",priority:"LOW",createdAt:"2026-03-01T10:00:00Z",closedAt:null,...over});

test("dni bez zdarzeń są obecne jako zera",()=>{
  const series=dailySeries([ticket({createdAt:"2026-03-02T10:00:00Z"})],from,3);
  expect(series.map(point=>point.day)).toEqual(["2026-03-01","2026-03-02","2026-03-03"]);
  expect(series.map(point=>point.opened)).toEqual([0,1,0]);
});

test("ticket zamknięty tego samego dnia zmienia oba liczniki, ale nie stan",()=>{
  const [day]=dailySeries([ticket({closedAt:"2026-03-01T15:00:00Z",status:"CLOSED"})],from,1);
  expect(day).toEqual({day:"2026-03-01",opened:1,closed:1,open:0});
});

test("ticket otwarty przed zakresem liczy się do stanu początkowego",()=>{
  const old=ticket({createdAt:"2026-02-10T10:00:00Z"});
  const closedInRange=ticket({id:2,createdAt:"2026-02-10T10:00:00Z",closedAt:"2026-03-02T10:00:00Z"});
  const closedBefore=ticket({id:3,createdAt:"2026-02-10T10:00:00Z",closedAt:"2026-02-20T10:00:00Z"});
  const series=dailySeries([old,closedInRange,closedBefore],from,2);
  expect(series.map(point=>point.open)).toEqual([2,1]);
});

test("zakres zerowy i przejście przez koniec miesiąca",()=>{
  expect(dailySeries([],from,0)).toEqual([]);
  expect(dailySeries([],new Date("2026-02-28T00:00:00Z"),2).map(point=>point.day)).toEqual(["2026-02-28","2026-03-01"]);
});

test("podział według priorytetu i statusu ma stały kształt",()=>{
  const rows=byPriorityAndStatus([ticket({priority:"HIGH"}),ticket({priority:"HIGH",status:"CLOSED"})]);
  expect(rows.map(row=>row.priority)).toEqual(["LOW","MEDIUM","HIGH","CRITICAL"]);
  expect(rows[2]).toEqual({priority:"HIGH",OPEN:1,IN_PROGRESS:0,CLOSED:1});
  expect(rows[0]).toEqual({priority:"LOW",OPEN:0,IN_PROGRESS:0,CLOSED:0});
});

test("średni czas zamknięcia i brak danych",()=>{
  expect(averageCloseHours([ticket({createdAt:"2026-03-01T00:00:00Z",closedAt:"2026-03-01T02:00:00Z"}),ticket({createdAt:"2026-03-01T00:00:00Z",closedAt:"2026-03-01T04:00:00Z"})])).toBe(3);
  expect(averageCloseHours([ticket({})])).toBeNull();
});

test("zdanie z wnioskiem opisuje trzy przypadki",()=>{
  const point=(opened:number,closed:number)=>({day:"d",opened,closed,open:0});
  expect(trendSentence([point(5,2)])).toBe("Otwarto o 3 ticketów więcej, niż zamknięto.");
  expect(trendSentence([point(1,4)])).toBe("Zamknięto o 3 ticketów więcej, niż otwarto.");
  expect(trendSentence([point(2,2)])).toBe("Otwarto tyle samo ticketów, ile zamknięto (2).");
});

test("agregacja tygodni sumuje zdarzenia i zachowuje końcowy stan",()=>{
  const points=Array.from({length:8},(_,index)=>({day:"d"+index,opened:1,closed:0,open:index+1}));
  const weeks=aggregateWeeks(points);
  expect(weeks).toHaveLength(2);
  expect(weeks[0]).toEqual({day:"d0",opened:7,closed:0,open:7});
  expect(points[0].opened).toBe(1);
});

test("wykres ma podpis, zdanie i tabelę z tymi samymi liczbami",()=>{
  render(<TicketsTrendChart tickets={[ticket({})]} from={from} days={2}/>);
  expect(screen.getByRole("figure")).toHaveTextContent("ostatnie 2 dni");
  expect(screen.getByText("Otwarto o 1 ticketów więcej, niż zamknięto.")).toBeVisible();
  fireEvent.click(screen.getByText("Dane w tabeli"));
  const row=screen.getByRole("row",{name:"2026-03-01 1 0 1"});
  expect(within(row).getByRole("rowheader")).toHaveTextContent("2026-03-01");
});

test("wykres rysuje SVG z dwiema seriami i osiami",async()=>{
  const view=render(<TicketsTrendChart tickets={[ticket({})]} from={from} days={7}/>);
  expect(view.container.querySelector("svg")).not.toBeNull();
  await waitFor(()=>expect(view.container.querySelectorAll(".recharts-line-curve")).toHaveLength(2));
  expect(view.container.querySelector("[stroke-dasharray='6 3']")).not.toBeNull();
});

test("najechanie lub render rodzica nie przelicza danych",()=>{
  const compute=vi.fn(dailySeries);
  const tickets=[ticket({})];
  function Parent(){
    const [clock,setClock]=useState(0);
    return <><button onClick={()=>setClock(value=>value+1)}>Zegar {clock}</button><TicketsTrendChart tickets={tickets} from={from} days={2} compute={compute}/></>;
  }
  render(<Parent/>);
  fireEvent.click(screen.getByRole("button"));
  fireEvent.click(screen.getByRole("button"));
  expect(compute).toHaveBeenCalledTimes(1);
});

test("zmiana zakresu przelicza dane",()=>{
  const compute=vi.fn(dailySeries);
  const tickets=[ticket({})];
  const view=render(<TicketsTrendChart tickets={tickets} from={from} days={7} compute={compute}/>);
  view.rerender(<TicketsTrendChart tickets={tickets} from={from} days={30} compute={compute}/>);
  expect(compute).toHaveBeenCalledTimes(2);
});

test("własny tooltip pokazuje sumę i polskie nazwy, a nieaktywny nic",()=>{
  const payload=[{dataKey:"OPEN",name:"Otwarte",value:2},{dataKey:"CLOSED",name:"Zamknięte",value:3}] as never;
  const view=render(<PriorityTooltip active label="HIGH" payload={payload}/>);
  expect(screen.getByText("Wysoki: 5")).toBeVisible();
  expect(screen.getByText("Otwarte: 2")).toBeVisible();
  view.rerender(<PriorityTooltip active={false} label="HIGH" payload={payload}/>);
  expect(view.container).toBeEmptyDOMElement();
});

test("wykres słupkowy renderuje podpis i trzy serie",async()=>{
  const view=render(<PriorityChart data={byPriorityAndStatus([ticket({})])}/>);
  expect(screen.getByRole("figure")).toHaveTextContent("według priorytetu");
  await waitFor(()=>expect(view.container.querySelectorAll(".recharts-bar")).toHaveLength(3));
});

test("dashboard ładuje się leniwie",async()=>{
  render(<DashboardRoute/>);
  expect(screen.getByRole("status")).toHaveTextContent("Ładowanie");
  expect(await screen.findByRole("heading",{name:"Dashboard"})).toBeVisible();
});

test("tabela ma nagłówki kolumn i wierszy",()=>{
  render(<TrendTable data={[{day:"2026-03-01",opened:1,closed:0,open:1}]}/>);
  expect(screen.getAllByRole("columnheader")).toHaveLength(4);
  expect(screen.getByRole("rowheader",{name:"2026-03-01"})).toBeVisible();
});`;
const exercises=[
['Napisz funkcję dailySeries z dniami bez zdarzeń.','Serię budujesz z zakresu, a nie ze zdarzeń.',stats],
['Policz stan otwartych ticketów na koniec każdego dnia.','Uwzględnij tickety otwarte przed zakresem.',stats],
['Przelicz daty w UTC.','Serwer i przeglądarka mogą mieć inną strefę.',stats],
['Napisz grupowanie według priorytetu i statusu.','Priorytety bez ticketów też mają wiersz.',stats],
['Oblicz średni czas zamknięcia z obsługą braku danych.','Dzielenie przez zero jest ukrytym błędem.',stats],
['Napisz zdanie z wnioskiem pod wykresem.','Wniosek ma czytać także osoba bez wzroku.',stats],
['Zagreguj dni do tygodni przy dużym zakresie.','SVG z tysiącami elementów zwalnia przeglądarkę.',stats],
['Przetestuj ticket zamknięty tego samego dnia.','Otwarte i zamknięte +1, stan bez zmian.',tests],
['Przetestuj ticket otwarty przed zakresem.','Zamknięcie wcześniej nie liczy się do stanu.',tests],
['Zbuduj wykres liniowy z dwiema seriami.','Kontener, serie, osie i pomoce to drzewo komponentów.',trend],
['Dopasuj wykres do rodzica przez ResponsiveContainer.','Rodzic musi mieć wysokość.',trend],
['Zapamiętaj dane przez useMemo.','Render rodzica nie może grupować ponownie 2000 ticketów.',trend],
['Sprawdź testem, że dane nie są przeliczane.','Przekaż atrapę funkcji i policz wywołania.',tests],
['Odróżnij serie stylem, nie tylko kolorem.','strokeDasharray działa przy daltonizmie.',trend],
['Dodaj figure i figcaption.','Podpis mówi, co pokazuje wykres.',trend],
['Dodaj tabelę z tymi samymi danymi.','details daje alternatywę i możliwość skopiowania.',trend],
['Zbuduj wykres skumulowany z stackId.','Ten sam stackId łączy serie w jednym słupku.',priority],
['Napisz własny tooltip z sumą.','Wartości mają ogólny typ: Number i String.',priority],
['Polskie etykiety osi X podaj przez tickFormatter.','Dane zachowują wartości techniczne.',priority],
['Przygotuj wykres do testu w jsdom.','Układ i rozmiar trzeba zastąpić atrapą.',setup],
['Ładuj dashboard leniwie.','Lista ticketów nie płaci za wykresy.',dashboard],
['Złóż ekran dashboardu.','Dane przygotowują funkcje, a ekran je wyświetla.',screenFile],
['Dodaj kafelki liczbowe.','Liczby kluczowe nie wymagają wykresu.','Otwarte teraz: tickets.filter(t=>t.status!=="CLOSED").length. Nowe dziś: porównanie createdAt z kluczem bieżącego dnia. Zamknięte w tygodniu: closedAt w zakresie. Średni czas: averageCloseHours, a gdy brak danych, pokaż myślnik zamiast NaN.'],
['Dodaj przełącznik zakresu 7/30/90 dni w URL.','Zakres ma przetrwać odświeżenie i dać się skopiować.','import {useSearchParams} from "react-router";\n\nexport function useRangeDays(){\n  const [params,setParams]=useSearchParams();\n  const days=[7,30,90].includes(Number(params.get("days")))?Number(params.get("days")):30;\n  // Przyciski wywołują setDays(90), a nieznana wartość w adresie wraca do 30.\n  return {days,setDays:(value:7|30|90)=>setParams({days:String(value)})};\n}'],
['Sprawdź wykres na 360 px.','Etykiety nie mogą na siebie nachodzić.','Ustaw minTickGap i krótszy format daty, ogranicz szerokość osi Y i sprawdź w trybie urządzenia. Legenda może przejść pod wykres. Tabela w details może być przewijana w poziomie wewnątrz własnego kontenera.'],
['Sprawdź motywy i daltonizm.','Rendering pozwala emulować zaburzenia widzenia.','Kolory serii pochodzą ze zmiennych CSS. W DevTools użyj Rendering, Emulate vision deficiencies, a także sprawdź kontrast linii 3:1 z tłem w obu motywach. Styl przerywany i etykiety serii zostają czytelne.'],
['Zweryfikuj brak przeliczeń Profilerem.','Test atrapy to tylko część dowodu.','Nagraj Profilerem najechanie na wykres i tyknięcie zegara. Sprawdź, czy TicketsTrendChart renderuje się bez kosztownego grupowania. Test liczby wywołań chroni przed regresją, a Profiler pokazuje rzeczywisty czas.'],
['Wybierz bibliotekę dla 100 000 punktów na żywo.','SVG a canvas.','SVG tworzy element na każdy punkt, więc przy dużych zbiorach zwalnia. Dla wykresów na żywo rozważ canvas, np. ECharts lub uPlot, oraz agregację. Sprawdź to na realnym rozmiarze danych, a nie według ogólnej opinii.'],
['Popraw wykres wprowadzający w błąd.','Oś Y i liczba serii.','Słupki zaczynaj od zera, bo inaczej różnice wyglądają na większe. Ogranicz serie do 3–5, podaj jednostki w opisie osi i dodaj wniosek słowami. Kołowy wykres z 12 kategoriami zastąp słupkowym.'],
['Sprawdź kryterium akceptacji lekcji.','Lazy, trend, priorytety, tabela, responsywność.','Dashboard ładuje się leniwie, pokazuje trend 7/30/90 dni i podział według priorytetu, ma tabelę pod każdym wykresem, działa na 360 px i w obu motywach, a najechanie nie przelicza danych. Sprawdzone testem i Profilerem.'],
];
const concepts=[
['Co dostaje kontener wykresu Recharts?','Tablicę obiektów w data','Ścieżkę do pliku','Element canvas','Tekst CSV','Jeden obiekt to jeden punkt na osi X.'],
['Co oznacza dataKey?','Nazwę pola danych','Klucz React','Identyfikator DOM','Klucz API','Serie i osie czytają nim wartość.'],
['Czego Recharts nie zrobi za Ciebie?','Grupowania danych','Rysowania osi','Obsługi legendy','Renderowania SVG','Sumy i średnie liczysz wcześniej.'],
['Co robi ResponsiveContainer?','Dopasowuje wykres do rodzica','Pobiera dane','Zmienia kolory','Tworzy tabelę','Mierzy rodzica przez ResizeObserver.'],
['Czego wymaga rodzic ResponsiveContainer?','Określonej wysokości','Pozycji absolute','Flexboxa','Roli figure','Bez tego wykres ma 0 px.'],
['W czym rysuje Recharts?','W SVG','W canvas','W WebGL','W tabeli HTML','Dlatego duże zbiory zwalniają.'],
['Ile punktów zwykle przekazać na wykres?','Dziesiątki lub setki','Miliony','Wszystkie tickety z bazy','Jeden','Reszta pochodzi z agregacji.'],
['Dlaczego dni bez zdarzeń muszą być w serii?','Inaczej oś pominie czas i zniekształci trend','Dla SEO','Dla TS','Bez powodu','Brak punktu nie oznacza zera na wykresie.'],
['Jak obsłużyć ticket otwarty przed zakresem?','Dodać do stanu początkowego, jeśli nie był zamknięty wcześniej','Pominąć','Dodać do pierwszego dnia jako opened','Usunąć','Inaczej stan otwartych jest zaniżony.'],
['Dlaczego daty liczyć w UTC w tym przykładzie?','Aby wynik nie zależał od strefy środowiska','Bo CSS','Bo UTC jest szybsze','Bo to wymóg Recharts','Inaczej dzień może przesunąć się na granicy doby.'],
['Co oznacza stackId?','Serie o tym samym id kumulują się','Klucz listy','Warstwę CSS','Nazwę osi','Pokazują sumę i podział.'],
['Czym różni się Tooltip content od labelFormatter?','content zastępuje cały tooltip, formatter zmienia tekst etykiety','Niczym','content formatuje tylko liczby','formatter tworzy komponent','Wybierz najprostsze rozwiązanie.'],
['Jaki typ opisuje własny tooltip?','TooltipContentProps','ChartProps','SvgProps','FigureProps','Daje active, payload i label.'],
['Czy TS sprawdzi, że dataKey="opend" istnieje?','Nie, dataKey to ogólny typ','Tak','Tak, w trybie strict','Tak, w Recharts 3','Literówka da pustą serię.'],
['Jak uzyskać kontrolę nad dataKey?','Funkcja dataKey lub stała z satisfies','Rzutowanie as any','Komentarz','Nazwa pliku','Pełna kontrola ma swój koszt w czytelności.'],
['Po co useMemo przy danych wykresu?','Render rodzica nie przelicza grupowania','Do zmiany koloru','Do lazy','Do SSR','Agregacja 2000 ticketów może być kosztowna.'],
['Co jeszcze może zapamiętać dane?','select w TanStack Query','useEffect','useRef render','localStorage','select pozwala przeliczać przy zmianie danych.'],
['Jak sprawdzić, że wykres renderuje się rzadziej?','Profilerem','Lighthouse SEO','npm audit','tsc','Mierz zamiast zgadywać.'],
['Co ma największą wartość w figcaption?','Co wykres pokazuje','Nazwa biblioteki','Rozmiar paczki','Numer commita','Podpis to pierwszy kontakt z treścią.'],
['Po co zdanie pod wykresem?','Podaje najważniejszy wniosek słowami','Dla SEO','Zastępuje dane','Wyłącza tooltip','Pomaga wszystkim, nie tylko czytnikom.'],
['Po co tabela pod wykresem?','To dostępna alternatywa i dane do skopiowania','Dla zwiększenia paczki','Do testów','Zastępuje osie','Wykres to obraz.'],
['Co daje accessibilityLayer w Recharts 3?','Fokus i poruszanie strzałkami po punktach','Automatyczny opis','Tłumaczenia','Tabelę','Domyślnie włączone, ale nie zastępuje tabeli.'],
['Jak nie kodować samym kolorem?','Style linii i etykiety przy seriach','Mocniejszym kolorem','Większą legendą bez innej zmiany','Animacją','Daltonizm dotyczy wielu osób.'],
['Jaki kontrast potrzebuje linia wykresu?','Co najmniej 3:1 z tłem','4,5:1 zawsze','1,5:1','21:1','To element graficzny.'],
['Czemu oś Y słupków od zera?','Inaczej różnice wyglądają na większe','Dla SVG','Dla TS','Bez znaczenia','To częsta manipulacja nawet niezamierzona.'],
['Ile serii na jednym wykresie to rozsądny limit?','3–5','20','100','Tylko 1','Więcej zwykle wymaga podziału.'],
['Kiedy wykres kołowy jest zły?','Przy wielu małych kategoriach','Przy 2 kategoriach','Zawsze w SVG','Gdy ma legendę','Słupki łatwiej porównać.'],
['Co pokazuje wykres liniowy?','Zmianę w czasie','Udział w całości','Proporcje jednego dnia','Listę kategorii bez kolejności','Oś X powinna mieć ciągłość.'],
['Co pokazuje wykres słupkowy?','Porównanie kategorii','Tylko trend','Tylko relację dwóch zmiennych','Sieć powiązań','Tu priorytety.'],
['Jak ładować kod wykresów?','Lazy i Suspense','Zawsze w głównym pakiecie','Przez tag script ręcznie','Przez iframe','Biblioteka waży dużo.'],
['Kiedy wyłączyć isAnimationActive?','Przy częstym odświeżaniu i ograniczonym ruchu','Nigdy','Tylko w produkcji','Tylko w Firefox','Animacja bez końca męczy.'],
['Jak zachować czytelność na 360 px?','minTickGap i krótszy format daty','Mniejszy tekst do 6 px','Usunąć wykres','Obracać ekran','Etykiety nie mogą nachodzić.'],
['Skąd brać kolory serii?','Ze zmiennych CSS motywu','Na sztywno w każdym komponencie','Losowo','Z backendu','Oba motywy pozostają spójne.'],
['Kiedy rozważyć canvas?','Przy tysiącach punktów lub danych na żywo','Zawsze','Przy 5 punktach','Dla tooltipu','SVG tworzy element na punkt.'],
['Jak zmniejszyć liczbę punktów?','Agregacja lub próbkowanie','Usunąć oś','Wyłączyć legendę','Zmienić kolor','Dni na tygodnie to najprostszy sposób.'],
['Co traci próbkowanie?','Może pominąć szczyty','Nic','Klucze','Osie','Przy skokach lepiej agregować z maksimum.'],
['Dlaczego nie liczyć sum w tooltipie w każdej klatce z całej listy?','Tooltip dostaje gotowy payload','Bo nie działa','Bo TS blokuje','Bo CSS','Payload ma wartości tego punktu.'],
['Co dostaje tickFormatter?','Wartość osi w ogólnym typie','Cały punkt danych','Element SVG','Nazwę pliku','Stąd String(value).'],
['Czy formatowanie osi zmienia dane?','Nie, tylko wyświetlanie','Tak','Tak, w tooltipie','Tak, po zapisie','Dane pozostają techniczne.'],
['Jak traktować brak danych w wyliczeniu średniej?','Zwrócić null i pokazać myślnik','Zwrócić NaN','Zwrócić 0 po cichu','Rzucić w render','Zero sugerowałoby natychmiastowe zamykanie.'],
['Co jest ryzykiem wykresu z pustymi danymi?','Pusty obszar bez wyjaśnienia','Błąd CSS','Zawieszenie sieci','Brak key','Pokaż stan „Brak danych”.'],
['Jak sprawdzić daltonizm w DevTools?','Rendering, Emulate vision deficiencies','Lighthouse SEO','Network','Sources','To szybki test, ale nie zastępuje projektowania.'],
['Czy jsdom narysuje poprawne położenia?','Nie, nie ma układu','Tak','Tak, po atrapie sieci','Tak, z Vitest','Test sprawdza strukturę i dane.'],
['Po co atrapa ResizeObserver w teście?','Aby ResponsiveContainer dostał rozmiar','Aby włączyć sieć','Aby zmienić motyw','Aby użyć TS','Bez niej wykres może się nie narysować.'],
['Co jest najważniejsze do testowania w dashboardzie?','Funkcje przygotowania danych','Pozycje pikseli','Kolor siatki','Czas animacji','To logika, której błąd zmienia wniosek.'],
['Co oznacza ticket zamknięty w tym samym dniu?','opened i closed +1, stan bez zmian','Tylko closed','Tylko opened','Pomijany','To jeden z przypadków brzegowych.'],
['Co zrobić z zamknięciem przed zakresem?','Nie liczyć do stanu','Liczyć zawsze','Dodać do pierwszego dnia','Rzucić błąd','Ticket nie był otwarty w zakresie.'],
['Jak nazywać dane do osi czasu?','Stabilnym kluczem dnia ISO','Tekstem „wczoraj”','Lokalnym formatem','Indeksem','Format wyświetlania jest osobno.'],
['Dlaczego lazy nie działa bez Suspense?','Potrzebna jest granica z fallbackiem','Działa zawsze','Wymaga serwera','Wymaga Motion','Suspense pokazuje stan ładowania.'],
['Gdzie trzymać zakres dni?','W URL','W module globalnym','W stylach','W DOM ręcznie','Da się skopiować link do widoku.'],
['Jak zabezpieczyć zakres z URL?','Sprawdzić listę dozwolonych wartości','Zaufać parametrowi','Wstawić w fetch bez sprawdzenia','Zamienić na float','?days=999999 mógłby przeciążyć obliczenia.'],
['Co pokazuje Legend?','Nazwy serii','Dane w tabeli','Tytuł strony','Wartości osi','Etykiety przy seriach bywają czytelniejsze.'],
['Co robi CartesianGrid?','Rysuje siatkę pomocniczą','Układa strony','Sortuje dane','Tworzy kolumny','Powinna być subtelna.'],
['Co robi ReferenceLine?','Rysuje linię odniesienia, np. cel','Łączy serie','Tworzy tabelę','Dodaje link','Przydatna do SLA.'],
['Co jest błędem w nowej tablicy data przy każdym renderze?','Wykres dostaje nowe dane i liczy od nowa','Nic','Błąd TS','Brak klucza','Stąd useMemo lub select.'],
['Czego nie mówi średnia?','Rozkładu i wartości skrajnych','Sumy czasu','Liczby zamkniętych','Jednostki','Warto rozważyć też medianę.'],
['Co zrobić z małymi liczbami w procentach?','Pokazać też liczbę bezwzględną','Tylko procent','Zaokrąglić do zera','Ukryć','50% z 2 ticketów to mało informacji.'],
['Co wybrać dla jednej liczby kluczowej?','Kafelek z liczbą','Wykres kołowy','Tabela tysiąca wierszy','Sama animacja','Nie każda informacja wymaga wykresu.'],
['Co powinien zawierać tekst osi Y przy czasie zamknięcia?','Jednostkę, np. godziny','Nazwę biblioteki','Nic, liczba wystarczy','Kolor serii','Bez jednostki liczba 24 nic nie mówi.'],
['Co sprawdzić po dodaniu nowej serii?','Kontrast, styl, legendę i tabelę','Tylko kolor','Tylko typy','Tylko rozmiar SVG','Alternatywy muszą nadal zawierać dane.'],
];
const header=String.raw`import {aggregateWeeks,averageCloseHours,byPriorityAndStatus,dailySeries,trendSentence,type StatsTicket} from "./ticketStats";
const from=new Date("2026-03-01T00:00:00Z");
const t=(over:Partial<StatsTicket>={}):StatsTicket=>({id:1,status:"OPEN",priority:"LOW",createdAt:"2026-03-01T10:00:00Z",closedAt:null,...over});
const pt=(opened:number,closed:number,open=0,day="d")=>({day,opened,closed,open});`;
const probeRows=[
['Ile punktów ma zakres 7 dni?','dailySeries([],from,7).length',7,'Seria powstaje z zakresu.'],
['Ile punktów ma zakres 0 dni?','dailySeries([],from,0).length',0,'Pusta seria jest poprawna.'],
['Jaki jest pierwszy dzień serii?','dailySeries([],from,2)[0].day','2026-03-01','Klucz ISO.',['2026-02-28','03-01','1 mar']],
['Jaki jest drugi dzień serii?','dailySeries([],from,2)[1].day','2026-03-02','Kolejne dni.',['2026-03-01','2026-03-03','2 mar']],
['Jaki dzień następuje po 28 lutego 2026?','dailySeries([],new Date("2026-02-28T00:00:00Z"),2)[1].day','2026-03-01','Rok nie jest przestępny.',['2026-02-29','2026-03-02','2026-02-30']],
['Ile otwartych ma pusty dzień?','dailySeries([],from,1)[0].opened',0,'Zero zamiast braku punktu.'],
['Ile otwartych ma dzień z ticketem?','dailySeries([t()],from,1)[0].opened',1,'Liczenie po dacie utworzenia.'],
['Jaki stan po otwarciu ticketu?','dailySeries([t()],from,1)[0].open',1,'Stan rośnie.'],
['Jaki stan przy zamknięciu tego samego dnia?','dailySeries([t({closedAt:"2026-03-01T15:00:00Z"})],from,1)[0].open',0,'+1 i -1.'],
['Ile zamkniętych przy zamknięciu tego samego dnia?','dailySeries([t({closedAt:"2026-03-01T15:00:00Z"})],from,1)[0].closed',1,'Obie wartości są osobne.'],
['Jaki stan startowy po starym otwartym tickecie?','dailySeries([t({createdAt:"2026-02-01T10:00:00Z"})],from,1)[0].open',1,'Ticket otwarty przed zakresem.'],
['Czy stary ticket zwiększa opened w zakresie?','dailySeries([t({createdAt:"2026-02-01T10:00:00Z"})],from,1)[0].opened',0,'Zdarzenie było wcześniej.'],
['Jaki stan po ticketcie zamkniętym przed zakresem?','dailySeries([t({createdAt:"2026-02-01T10:00:00Z",closedAt:"2026-02-05T10:00:00Z"})],from,1)[0].open',0,'Nie był otwarty w zakresie.'],
['Jaki stan po zamknięciu starego w zakresie (2. dzień)?','dailySeries([t({createdAt:"2026-02-01T10:00:00Z",closedAt:"2026-03-02T10:00:00Z"})],from,2)[1].open',0,'Zamknięcie zmniejsza stan.'],
['Jaki stan pierwszego dnia dla tego ticketu?','dailySeries([t({createdAt:"2026-02-01T10:00:00Z",closedAt:"2026-03-02T10:00:00Z"})],from,2)[0].open',1,'Wciąż otwarty.'],
['Ile wierszy ma podział priorytetów?','byPriorityAndStatus([]).length',4,'Stały kształt.'],
['Jaki jest ostatni priorytet?','byPriorityAndStatus([])[3].priority','CRITICAL','Kolejność znaczeniowa.',['LOW','HIGH','MEDIUM']],
['Ile OPEN ma LOW z jednym ticketem?','byPriorityAndStatus([t()])[0].OPEN',1,'Domyślny ticket.'],
['Ile CLOSED ma LOW z jednym otwartym?','byPriorityAndStatus([t()])[0].CLOSED',0,'Kolumny się nie mieszają.'],
['Ile OPEN ma HIGH przy tickecie LOW?','byPriorityAndStatus([t()])[2].OPEN',0,'Pusty wiersz ma zera.'],
['Średnia z 2 i 4 godzin','averageCloseHours([t({createdAt:"2026-03-01T00:00:00Z",closedAt:"2026-03-01T02:00:00Z"}),t({createdAt:"2026-03-01T00:00:00Z",closedAt:"2026-03-01T04:00:00Z"})])',3,'(2+4)/2.'],
['Średnia z 30 minut','averageCloseHours([t({createdAt:"2026-03-01T00:00:00Z",closedAt:"2026-03-01T00:30:00Z"})])',0.5,'Wynik w godzinach.',['30','1','0']],
['Średnia bez zamkniętych','averageCloseHours([t()])','null','Brak danych to nie zero.',['0','NaN','undefined']],
['Średnia z pustej listy','averageCloseHours([])','null','Bez dzielenia przez zero.',['0','NaN','undefined']],
['Wniosek 5 i 2','trendSentence([pt(5,2)])','Otwarto o 3 ticketów więcej, niż zamknięto.','Tickety przybywają.',['Zamknięto o 3 ticketów więcej, niż otwarto.','Otwarto tyle samo ticketów, ile zamknięto (5).','brak']],
['Wniosek 1 i 4','trendSentence([pt(1,4)])','Zamknięto o 3 ticketów więcej, niż otwarto.','Kolejka maleje.',['Otwarto o 3 ticketów więcej, niż zamknięto.','Otwarto tyle samo ticketów, ile zamknięto (1).','brak']],
['Wniosek 2 i 2','trendSentence([pt(2,2)])','Otwarto tyle samo ticketów, ile zamknięto (2).','Remis.',['Otwarto o 0 ticketów więcej, niż zamknięto.','Zamknięto o 0 ticketów więcej, niż otwarto.','brak']],
['Wniosek z wielu dni','trendSentence([pt(1,0),pt(1,0),pt(0,1)])','Otwarto o 1 ticketów więcej, niż zamknięto.','Suma przez zakres.',['Otwarto o 2 ticketów więcej, niż zamknięto.','Zamknięto o 1 ticketów więcej, niż otwarto.','brak']],
['Wniosek z pustej listy','trendSentence([])','Otwarto tyle samo ticketów, ile zamknięto (0).','Zero po obu stronach.',['brak','Otwarto o 0 ticketów więcej, niż zamknięto.','undefined']],
['Ile tygodni z 7 dni?','aggregateWeeks(Array.from({length:7},()=>pt(1,0))).length',1,'Pełny tydzień.'],
['Ile tygodni z 8 dni?','aggregateWeeks(Array.from({length:8},()=>pt(1,0))).length',2,'Rozpoczęty drugi tydzień.'],
['Ile tygodni z pustej listy?','aggregateWeeks([]).length',0,'Pusty wynik.'],
['Suma opened z tygodnia','aggregateWeeks(Array.from({length:7},()=>pt(2,0)))[0].opened',14,'Zdarzenia się sumują.'],
['Suma closed z tygodnia','aggregateWeeks(Array.from({length:7},()=>pt(0,1)))[0].closed',7,'Zdarzenia się sumują.'],
['Stan tygodnia','aggregateWeeks([pt(1,0,1),pt(1,0,2),pt(1,0,3)])[0].open',3,'Stan to ostatnia wartość, a nie suma.'],
['Dzień tygodnia','aggregateWeeks([pt(1,0,1,"a"),pt(1,0,2,"b")])[0].day','a','Etykietą jest początek.',['b','ab','d']],
['Czy agregacja mutuje wejście?','(()=>{const p=[pt(1,0),pt(1,0)];aggregateWeeks(p);return p[0].opened;})()',1,'Pierwszy punkt jest kopiowany.'],
['Czy dailySeries mutuje from?','(()=>{const f=new Date("2026-03-01T00:00:00Z");dailySeries([],f,3);return f.toISOString().slice(0,10);})()','2026-03-01','Każdy dzień powstaje z kopii.',['2026-03-03','2026-03-04','2026-02-28']],
['Ticket spoza zakresu w przyszłości','dailySeries([t({createdAt:"2027-01-01T00:00:00Z"})],from,1)[0].opened',0,'Brak pozycji w mapie.'],
['Czy przyszły ticket wpływa na stan?','dailySeries([t({createdAt:"2027-01-01T00:00:00Z"})],from,1)[0].open',0,'Nie był utworzony przed zakresem.'],
];
const project={
'src/ticketStats.ts':stats,'src/TicketsTrendChart.tsx':trend,'src/PriorityChart.tsx':priority,'src/DashboardRoute.tsx':dashboard,'src/DashboardScreen.tsx':screenFile,'src/setup.ts':setup,'src/charts.test.tsx':tests,
'README.md':'DevDesk: wykresy. npm install, npm test. Vitest sprawdza czyste funkcje danych, strukturę SVG, tabelę, liczbę przeliczeń i ładowanie leniwe. jsdom nie ma układu, więc rozmiar wykresu jest atrapą. Czytelność na 360 px, motywy, klawiaturę w wykresie i daltonizm sprawdź w przeglądarce.\n',
};
const mutants=[
{file:'src/ticketStats.ts',before:'&&(!ticket.closedAt||dayKey(ticket.closedAt)>=first)',after:''},
{file:'src/ticketStats.ts',before:'running+=point.opened-point.closed;',after:'running+=point.opened;'},
{file:'src/ticketStats.ts',before:'if(!closed.length)return null;',after:''},
{file:'src/ticketStats.ts',before:'week.open=point.open;',after:'week.open+=point.open;'},
{file:'src/ticketStats.ts',before:'weeks.push({...point})',after:'weeks.push(point)'},
{file:'src/TicketsTrendChart.tsx',before:'useMemo(()=>compute(tickets,from,days),[tickets,from,days,compute])',after:'compute(tickets,from,days)'},
{file:'src/TicketsTrendChart.tsx',before:'[tickets,from,days,compute]',after:'[tickets,from,compute]'},
{file:'src/TicketsTrendChart.tsx',before:' strokeDasharray="6 3"',after:''},
{file:'src/TicketsTrendChart.tsx',before:'<details><summary>Dane w tabeli</summary><TrendTable data={data}/></details>',after:''},
{file:'src/PriorityChart.tsx',before:'if(!active||!payload?.length)return null;',after:''},
];
module.exports=lesson({exercises,concepts,probeRows,project,mutants,header});
